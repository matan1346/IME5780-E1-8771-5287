package unittests;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class Point3DTest {
    Point3D p1 = new Point3D(1, 2, 3);
    @Test
    public void subtractTest() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals("add() does not return point Zero",
                Point3D.ZERO, p1.add(new Vector(-1, -2, -3)));
    }

    @Test
    public void addTest() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals("subtract() does not return point Zero",
                Point3D.ZERO, p1.add(new Vector(-1, -2, -3)));


        Point3D p2 = new Point3D(2,3,4);
        assertEquals("add() function [positive+positive] not adding correctly",
                new Point3D(3,5,7), p1.add(new Vector(p2)));

        assertEquals("add() function [positive+negative] not adding correctly",
                new Point3D(-1,-1,-1), p1.add(new Vector(p2).scale(-1)));

        Point3D pMinus = new Point3D(-7,8,-3);
        assertEquals("add() function [negative+positive] subtract positive",
                new Point3D(-5, 11, 1), pMinus.add(new Vector(p2)));

        assertEquals("add() function [negative+negative] subtract positive",
                new Point3D(-9, 5, -7), pMinus.add(new Vector(p2).scale(-1)));


    }

    @Test
    public void distanceSquaredTest() {

        Point3D p2 = new Point3D(2,5,1);
        Point3D p2Minus = new Point3D(-2,-5,-1);

        // ============ Equivalence Partitions Tests ==============
        assertEquals("distanceSquared() [positive->positive] does not work correctly",
                14, p1.distanceSquared(p2), 0.2);

        assertEquals("distanceSquared() [positive->negative] does not work correctly",
                74, p1.distanceSquared(p2Minus), 0.2);

        Point3D pMinus = new Point3D(-3,-6,8);
        assertEquals("distanceSquared() [negative->positive] does not work correctly",
                195, pMinus.distanceSquared(p2), 0.2);

        assertEquals("distanceSquared() [negative->negative] does not work correctly",
                83, pMinus.distanceSquared(p2Minus), 0.2);

        assertEquals("distanceSquared() ZERO does not work correctly",
                0, Point3D.ZERO.distanceSquared(Point3D.ZERO), 0.2);
    }


    @Test
    public void distanceTest() {


        Point3D p2 = new Point3D(2,5,1);
        Point3D p2Minus = new Point3D(-2,-5,-1);

        // ============ Equivalence Partitions Tests ==============
        assertEquals("distance() [positive->positive] does not work correctly",
                3.74, p1.distance(p2), 0.2);

        assertEquals("distance() [positive->negative] does not work correctly",
                8.6, p1.distance(p2Minus), 0.2);

        Point3D pMinus = new Point3D(-3,-6,8);
        assertEquals("distance() [negative->positive] does not work correctly",
                13.96, pMinus.distance(p2), 0.2);

        assertEquals("distance() [negative->negative] does not work correctly",
                9.11, pMinus.distance(p2Minus), 0.2);

        assertEquals("distance() ZERO does not work correctly",
                0, Point3D.ZERO.distance(Point3D.ZERO), 0.2);

    }
}