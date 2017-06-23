package ui;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Plane;

import java.util.Timer;

/**
 * Press left mouse button to add an edge, press right mouse button to add a starting point
 */
public class Main extends Application {
    private static final int POINTS_TO_ADD = 50;
    public int WIDTH = 1200;
    public int HEIGHT = 800;

    private Plane plane;

    @Override public void start(Stage stage) {Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);

        final Canvas canvas = new Canvas(scene.getWidth(),scene.getHeight());
        plane = new Plane(canvas);

        scene.setOnMouseClicked(event -> {
            Point2D position = new Point2D(
                    event.getX(),
                    event.getY()
            );

            if (event.getButton() == MouseButton.PRIMARY) {
                plane.addEdge(position);
            } else {
                plane.addStartingPoint(position);
            }
        });

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                plane.tick();
            }
        };

        timer.start();

        scene.setOnKeyTyped(event -> {
            if (event.getCharacter().equals("a")) {
                for (int i = 0; i < POINTS_TO_ADD; i++) {
                    plane.addStartingPoint(new Point2D(0, 0));
                }
            } else if (event.getCharacter().equals("c")) {
                plane.clear();
            }
        });

        root.getChildren().add(canvas);

        stage.setTitle("SSTG Sierpinski Triangle Generator");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
