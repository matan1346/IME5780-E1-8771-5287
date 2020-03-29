package unittests;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class Point3DTest {
    Point3D p1 = new Point3D(1, 2, 3);
    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals("add() does not return point Zero", Point3D.ZERO, p1.add(new Vector(-1, -2, -3)));
    }

    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals("subtract() does not return point Zero", Point3D.ZERO, p1.add(new Vector(-1, -2, -3)));
    }

    @Test
    public void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals("distanceSquared() does not work correctly", 14, p1.distanceSquared(new Point3D(2,5,1)), 0.2);
    }


    @Test
    public void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals("distance() does not work correctly", 3, p1.distance(new Point3D(3,4,2)), 0.2);
    }
}