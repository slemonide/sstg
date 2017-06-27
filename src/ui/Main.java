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
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Plane plane;

    @Override public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);
        stage.setMaximized(true);

        Group circles = new Group();
        plane = new Plane(circles);

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

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                plane.tick();
            }
        };

        timer.start();

        scene.setOnKeyTyped(event -> {
            switch (event.getCharacter()) {
                case "a":
                    for (int i = 0; i < POINTS_TO_ADD; i++) {
                        plane.addStartingPoint(new Point2D(0, 0));
                    }
                    break;
                case "c":
                    plane.clear();
                    break;
                case "2":
                    plane.nextEdgeGroup();
                    break;
                case "1":
                    plane.prevEdgeGroup();
                    break;
            }
        });

        root.getChildren().add(circles);

        stage.setTitle("Stochastic Sierpinski Triangle Generator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
