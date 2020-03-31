package unittests;

import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

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
}