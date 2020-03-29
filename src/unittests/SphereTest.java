package unittests;

import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;

import static org.junit.Assert.*;

public class SphereTest {

    @Test
    public void testGetNormal() {
        Sphere sp = new Sphere(new Point3D(1,0,0), 30);

    }
}