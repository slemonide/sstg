package model;

import com.sun.istack.internal.NotNull;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.*;

/**
 * A plane class that can be set up with predefined edges and
 * the starting position
 */
public class Plane {
    private static final Paint EDGE_COLOR = Color.RED;
    private static final Paint POINT_COLOR = Color.AQUAMARINE;
    private static final int FILL_RADIUS = 1;
    private static final Random RANDOM = new Random();
    private static final double EDGE_RADIUS = 2;
    private static final double FACTOR = 0.5;

    private Group group;
    private List<List<Point2D>> edges;
    private List<Point2D> activePoints;
    private int activeEdgeGroup;
    private int selectedEdgeGroup;

    /**
     * Construct a new empty plane associated with the given canvas
     */
    public Plane(@NotNull Group group) {
        this.group = group;

        edges = new ArrayList<>();
        edges.add(new ArrayList<>());
        activePoints = new ArrayList<>();
        activeEdgeGroup = 0;
        selectedEdgeGroup = 0;
    }

    /**
     * Adds a new edge with the given position
     * @param position position of the edge to be added
     */
    public void addEdge(@NotNull Point2D position) {
        group.getChildren().add(new Circle(position.getX(), position.getY(), EDGE_RADIUS, EDGE_COLOR));

        edges.get(selectedEdgeGroup).add(position);
    }

    /**
     * Adds a new starting point with the given position
     * @param position position of the starting point to be added
     */
    public void addStartingPoint(@NotNull Point2D position) {
        group.getChildren().add(new Circle(position.getX(), position.getY(), EDGE_RADIUS, POINT_COLOR));

        activePoints.add(position);
    }

    /**
     * Generate the next state of the plane
     */
    @NotNull
    public void tick() {
        List<Point2D> nextActivePoints = new ArrayList<>();

        for (Point2D position : activePoints) {
            nextActivePoints.add(nextPoint(position));

            group.getChildren().add(new Circle(position.getX(), position.getY(), EDGE_RADIUS, POINT_COLOR));
        }

        activePoints = nextActivePoints;
    }

    /**
     * Produce the next active point
     * @param point previous active point
     * @return next active point
     */
    private Point2D nextPoint(Point2D point) {
        Optional<Point2D> randomEdge = pickRandomEdge();

        while (!randomEdge.isPresent()) {
            randomEdge = pickRandomEdge();
        }

        return (point.add(randomEdge.get()).multiply(FACTOR));
    }

    /**
     * Pick a random edge, if edge is present
     * @return a random edge
     */
    private Optional<Point2D> pickRandomEdge() {
        List<Point2D> edgeGroup = getNextEdgeGroup();

        if (edgeGroup.size() > 0) {
            return Optional.of(edgeGroup.get(RANDOM.nextInt(edgeGroup.size())));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Completely clears the plane
     */
    public void clear() {
        edges.clear();
        selectedEdgeGroup = 0;
        edges.add(new ArrayList<>());
        activePoints.clear();
        group.getChildren().clear();
    }

    /**
     * Produce the next edge group, loop back when needed
     * @return next edge group, loop back when needed
     */
    private List<Point2D> getNextEdgeGroup() {
        assert (edges.size() > 0);
        activeEdgeGroup = (activeEdgeGroup + 1) % edges.size();

        return edges.get(activeEdgeGroup);
    }

    /**
     * Select/create next edge group
     */
    public void nextEdgeGroup() {
        edges.add(new ArrayList<>());
        selectedEdgeGroup++;
    }

    /**
     * If possible, switch to previous edge group
     */
    public void prevEdgeGroup() {
        selectedEdgeGroup = Math.max(selectedEdgeGroup - 1, 0);
    }
}
