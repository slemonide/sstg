package tests;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import model.Plane;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the Plane class
 */
public class PlaneTest {
    // forget about canvas for now
    private Canvas testCanvas;
    private Plane testPlane;

    @Before
    public void runBefore() {
        testCanvas = new Canvas();
        testPlane = new Plane(testCanvas);
    }
}
