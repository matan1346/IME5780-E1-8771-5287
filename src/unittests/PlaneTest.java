package unittests;

import geometries.*;
import primitives.*;
import org.junit.Test;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.*;

public class PlaneTest {

    @Test
    public void findIntersections() {

        Plane p = new Plane(new Point3D(1,0,0), new Point3D(0,1,0), new Point3D(0,0,1));

        // ============ Equivalence Partitions Tests ==============
        Ray r = new Ray(new Point3D(0,-1,0), (new Vector(0,1,1)).normalized());

        List<Point3D> intersections = p.findIntersections(r);

        //EP
        assertEquals("Error - finding intersection with ray and plane",1, intersections.size());


        //EP
        Ray r2 = new Ray(new Point3D(2,0,0), new Vector(4,-2,0).normalize());

        List<Point3D> no_intersections = p.findIntersections(r2);

        assertNull("Error -  no intersection with ray and plane", no_intersections);


        // =============== Boundary Values Tests ==================
        //BVA
        Ray r3 = new Ray(new Point3D(2.32,-1.33,0), new Vector(-3.62, -3.71, 7.33).normalize());

        List<Point3D> no_intersections2 = p.findIntersections(r3);

        assertNull("Error  found intersection with parallel ray", no_intersections2);

        //BVA - Orthogonal ray after plane
        Ray r4 = new Ray(new Point3D(2,2,2), new Vector(1,1,1).normalize());

        List<Point3D> no_intersections3 = p.findIntersections(r4);
        assertEquals("Error  found intersection with orthogonal ray after plane",null, no_intersections3);

        //BVA - Orthogonal ray before plane
        Ray r5 = new Ray(new Point3D(-1,-1,-1), new Vector(-2,-2,-2).normalize());

        List<Point3D> no_intersections4 = p.findIntersections(r5);
        assertEquals("Error  found intersection with orthogonal ray before plane",null, no_intersections4);

        //BVA - ray start in plane point as normal of plane
        Ray r6 = new Ray(p.get_p(), p.getNormal().scale(-1));

        List<Point3D> no_intersections5 = p.findIntersections(r6);

        assertEquals("Error  found intersection with ray start in plane point as normal of plane",null, no_intersections5);


        //BVA intersection with ray start in plane, outside plane no same point
        Ray r8 = new Ray(new Point3D(0,1,0), new Vector(2,3,1).normalize());

        List<Point3D> no_intersections7 = p.findIntersections(r8);

        assertEquals("Error  found intersection with ray start in plane  but outside the plane with no same point",null, no_intersections7);



        //VBA - ray start in plane  but outside the plane
        Ray r7 = new Ray(new Point3D(1,0,0), new Vector(1,1,3).normalize());

        List<Point3D> no_intersections6 = p.findIntersections(r7);
        assertEquals("Error  found intersection with ray start in plane  but outside the plane with same point",null, no_intersections6);

    }
}