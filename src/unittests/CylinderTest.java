package unittests;

import geometries.*;
import primitives.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CylinderTest {

    @Test
    public void getNormalTest() {

        //Cylinder c1 = new Cylinder(new Ray(3,), 20,60);

        //Point3D p = new Point3D(0,0,2);





    }

    @Test
    public void findIntersections() {

        Cylinder c = new Cylinder(new Ray(new Point3D(0,0,0), new Vector(0,0,4)),
                2, 4);


        //EP - 2 intersections from down to top
        Ray r1 = new Ray(new Point3D(-8,-2,0), new Vector(0,0,2));

        List<Point3D> intersections1 = c.findIntersections(r1);


        //EP -
        Ray r2 = new Ray(new Point3D(-8,-2,0), new Vector(0,0,2));



    }
}