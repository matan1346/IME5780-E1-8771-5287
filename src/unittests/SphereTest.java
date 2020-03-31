package unittests;

import geometries.*;
import org.junit.Test;
import primitives.*;

import static org.junit.Assert.*;

public class SphereTest {

    /*
    @Test
    public void testGetNormal() {
        Sphere sp = new Sphere(new Point3D(1,0,0), 30);

    }*/

    @Test
    public void getNormalTest1() {
        Sphere sp = new Sphere(new Point3D(0, 0, 1), 1.0);
        assertEquals(new Vector(0,0,1),sp.getNormal(new Point3D(0,0,2)));
    }

    @Test
    public void getNormalTest2() {
        Sphere sp = new Sphere(new Point3D(0,0,1), 1.0);
        assertNotEquals(new Vector(0,0,1),sp.getNormal(new Point3D(0,1,1)));
        //System.out.println(sp.getNormal(new Point3D(0,1,1)));
    }


    @Test
    public void getNormalTest() {
        Sphere s1 = new Sphere(new Point3D(0,0,0), 4);
        Sphere s2 = new Sphere(new Point3D(1,1,1), 1);


        assertTrue(s1.getNormal(new Point3D(0,0,4)).equals(new Vector(new Point3D(0,0,1))));
        assertTrue(s1.getNormal(new Point3D(0,0,-4)).equals(new Vector(new Point3D(0,0,-1))));
        assertTrue(s1.getNormal(new Point3D(0,4,0)).equals(new Vector(new Point3D(0,1,0))));
        assertTrue(s1.getNormal(new Point3D(0,-4,0)).equals(new Vector(new Point3D(0,-1,0))));
        assertTrue(s1.getNormal(new Point3D(4,0,0)).equals(new Vector(new Point3D(1,0,0))));
        assertTrue(s1.getNormal(new Point3D(-4,0,0)).equals(new Vector(new Point3D(-1,0,0))));

        assertTrue(s2.getNormal(new Point3D(1,1,0)).equals(new Vector(new Point3D(0,0,-1))));
        assertTrue(s2.getNormal(new Point3D(0,1,1)).equals(new Vector(new Point3D(-1,0,0))));
        assertTrue(s2.getNormal(new Point3D(1,0,1)).equals(new Vector(new Point3D(0,-1,0))));

        //assertEquals(s1.getNormal(new Point3D(7,7,7)),null);

    }
}



