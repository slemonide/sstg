package model;

import com.sun.istack.internal.NotNull;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
    private static final double EDGE_RADIUS = 3;

    private GraphicsContext gc;
    private List<Point2D> edges;
    private List<Point2D> activePoints;

    /**
     * Construct a new empty plane associated with the given canvas
     */
    public Plane(@NotNull Canvas canvas) {
        gc = canvas.getGraphicsContext2D();

        edges = new ArrayList<>();
        activePoints = new ArrayList<>();
    }

    /**
     * Adds a new edge with the given position
     * @param position position of the edge to be added
     */
    public void addEdge(@NotNull Point2D position) {
        gc.setFill(EDGE_COLOR);
        gc.fillOval(position.getX(), position.getY(), EDGE_RADIUS, EDGE_RADIUS);

        edges.add(position);
    }

    /**
     * Adds a new starting point with the given position
     * @param position position of the starting point to be added
     */
    public void addStartingPoint(@NotNull Point2D position) {
        gc.setFill(POINT_COLOR);
        gc.fillOval(position.getX(), position.getY(), FILL_RADIUS, FILL_RADIUS);

        activePoints.add(position);
    }

    /**
     * Generate the next state of the plane
     */
    @NotNull
    public void tick() {
        List<Point2D> nextActivePoints = new ArrayList<>();

        for (Point2D point : activePoints) {
            nextActivePoints.add(nextPoint(point));

            gc.setFill(POINT_COLOR);
            gc.fillOval(point.getX(), point.getY(), FILL_RADIUS, FILL_RADIUS);
        }

        activePoints = nextActivePoints;
    }

    /**
     * Produce the next active point
     * @param point previous active point
     * @return next active point
     */
    private Point2D nextPoint(Point2D point) {
        Point2D randomEdge = pickRandomEdge();

        return (point.add(randomEdge).multiply(0.5));
    }

    /**
     * Pick a random edge
     * @return a random edge
     */
    private Point2D pickRandomEdge() {
        if (edges.size() > 0) {
            return edges.get(RANDOM.nextInt(edges.size()));
        } else {
            return new Point2D(0,0);
        }
    }

    /**
     * Completely clears the plane
     */
    public void clear() {
        // TODO: finish
    }
}
