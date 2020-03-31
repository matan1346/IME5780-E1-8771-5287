package unittests;

import org.junit.Test;

import primitives.*;
import geometries.*;

import static org.junit.Assert.*;

public class TubeTest {

    @Test
    public void getNormalTest() {

        // ============ Equivalence Partitions Tests ==============
        Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1));
        Tube tube = new Tube(ray, 40);
        Point3D point = new Point3D(0, 0, 2);
        Vector tNormal = tube.getNormal(point);

        assertTrue("Error: Tube getNormal not returning correct value",
                tNormal.equals(new Vector(0, 0, 1)));


    }
}