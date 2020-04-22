package unittests;

import geometries.Polygon;
import org.junit.Test;
import primitives.*;

import java.util.List;

import static org.junit.Assert.*;

public class PolygonTest {

    /**
     * Test method for
     * {@link geometries.Polygon#Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    public void constructorTest() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertix on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }


    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void getNormalTest() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = -Math.sqrt(1d / 3);
        assertEquals("Bad normal to polygon",
                new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }

    @Test
    public void findIntersections() {

        Polygon p = new Polygon(new Point3D(0,0,5), new Point3D(10.52,5,0), new Point3D(15,-5,-12.5), new Point3D(15,0,-8));


        //Box
        Polygon p2 = new Polygon(
                new Point3D(0,-3,0),
                new Point3D(0,0,4),

                new Point3D(0,0,-1),
        new Point3D(0,4,0)
                );


        //EP - intersection inside the polygon
        Ray r2 = new Ray(new Point3D(-6.16,0.96,0), new Vector(0, -1.22, 1.39).normalize());

        List<Point3D> intersection1 = p2.findIntersections(r2);

        assertEquals("ERROR - 1 intersection must be with ray inside a polygon", 1, intersection1.size());


        //VBA - no intersection outside the polygon
        Ray r3 = new Ray(new Point3D(-5.71,1.81,0), new Vector(1.57, 5.14, 0).normalize());

        List<Point3D> no_intersections1 = p2.findIntersections(r3);

        assertEquals("ERROR - no intersection must be with ray outside a polygon", null, no_intersections1);


        //VBA - no intersection on edge
        Ray r4 = new Ray(new Point3D(-5.84,2.77,-0.26), new Vector(0,2,2).normalize());

        List<Point3D> no_intersections2 = p2.findIntersections(r4);

        assertEquals("ERROR - no intersection must be with ray on edge of polygon", null, no_intersections2);


        //VBA - no intersection on the vertex
        Ray r5 = new Ray(new Point3D(-5.74,-1.74,0), new Vector(0, -3, 0).normalize());

        List<Point3D> no_intersections3 = p2.findIntersections(r5);

        assertEquals("ERROR - no intersection must be with ray on vertex of polygon", null, no_intersections3);












    }
}