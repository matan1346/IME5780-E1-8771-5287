package unittests;

import geometries.Triangle;
import org.junit.Test;
import primitives.*;

import java.util.List;

import static org.junit.Assert.*;

public class TriangleTest {

//    @Test
//    public void testConstructor() {
//        // ============ Equivalence Partitions Tests ==============
//
//        try {
//            new Triangle(new Point3D(1,0,0), new Point3D(0,1,0), new Point3D(0,0,1));
//        }catch (Exception e){ }
//
//        try {
//            new Triangle(new Point3D(1,0,0), new Point3D(5,1,0), new Point3D(0,0,1));
//            fail("יצרנו משולש לא חוקי סכום שני צלעות לא גדול מצלע שלישית");
//        }catch (Exception e){ }
//
//
//    }

    @Test
    public void getNormalTest() {
        // ============ Equivalence Partitions Tests ==============
        Triangle t1 = new Triangle(new Point3D(1,0,0), new Point3D(0,1,0), new Point3D(0,0,1));
        double sqrt3 = -Math.sqrt(1d / 3);
        //System.out.println(t1.getNormal(new Point3D(0, 0, 1)));
        assertEquals("Bad normal to triangle",
                new Vector(sqrt3, sqrt3, sqrt3), t1.getNormal(new Point3D(0, 0, 1)));
    }

    @Test
    public void findIntersections() {
        //Triangle t = new Triangle(new Point3D(2,0,0),new Point3D(0,0,2),new Point3D(0,2,0));
        Triangle t = new Triangle(new Point3D(0,2,0),new Point3D(0,0,2),new Point3D(2,0,0));
        //EP - Ray intersect triangle in the middle
        Ray r1 = new Ray(new Point3D(3.03,3.26,0), new Vector(-2.71,-2.59,1.01).normalize());


        List<Point3D> intersection = t.findIntersections(r1);

        assertEquals("Error, no intersection with ray inside triangle", 1, intersection.size());

        //EP - Ray no intersect triangle - outside edge
        Ray r2 = new Ray(new Point3D(-1,-2,1), new Vector(3,10,3).normalize());

        List<Point3D> no_intersections1 = t.findIntersections(r2);

        assertEquals("Error, intersection with ray outside triangle with edge", null, no_intersections1);


        //EP - Ray no intersect triangle - outside vertex
        Ray r3 = new Ray(new Point3D(2.01,2.82,0), new Vector(0,0,2.15).normalize());

        List<Point3D> no_intersections2 = t.findIntersections(r3);

        assertEquals("Error, intersection with ray outside triangle with vertex", null, no_intersections2);



        /***VBA***/

        //VBA - no intersection with ray on edge triangle
        Ray r4 = new Ray(new Point3D(2.57, 2.48, 0), new Vector(1.14,0,0.86).normalize());

        List<Point3D> no_intersections3 = t.findIntersections(r4);


        assertEquals("Error, intersection with ray on edge triangle", null, no_intersections3);

        //VBA  - no intersection with ray on vertex triangle
        Ray r5 = new Ray(new Point3D(3.53,1.25,0), new Vector(2,0,0).normalize());

        List<Point3D> no_intersections4 = t.findIntersections(r5);

        assertEquals("Error, intersection with ray on vertex triangle", null, no_intersections4);

        //VBA - no intersect, ray outside triangle, continue of edge..
        Ray r6 = new Ray(new Point3D(3.46,1.65,0), new Vector(0, -0.5, 2.5).normalize());


        List<Point3D> no_intersections5 = t.findIntersections(r6);

        assertEquals("Error, intersection with ray outside triangle continues edge", null, no_intersections5);

    }
}