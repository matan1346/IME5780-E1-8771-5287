package unittests;

import org.junit.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector;

import static java.lang.System.out;
import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Matan and Shimon
 */
public class VectorTest {

    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //Tc01:
        try { // test zero vector
            Coordinate coord = new Coordinate(0);
            new Vector(coord, coord, coord);
            fail("ERROR: zero vector does not throw an exception");
        } catch (Exception e) {}

        //Tc02:
        try { // test zero vector
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception");
        } catch (Exception e) {}

        //Tc03:
        try { // test zero vector
            new Vector(Point3D.ZERO);
            fail("ERROR: zero vector does not throw an exception");
        } catch (Exception e) {}
    }

    @Test
    public void testAdd() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(2,3,4);
        Vector v3 = new Vector(-1,-2,-3);

        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(3, 5, 7), v1.add(v2));

        // =============== Boundary Values Tests ==================
        // test zero vector from add
        try {
            v1.add(v3);
            fail("add() if given zero does not throw an exception");
        }catch (Exception s){}
    }

    @Test
    public void testSubtract() {

        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(1,2,3.5);
        Vector v3 = new Vector(1,2,3);


        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(0, 0, 0.5), v1.subtract(v2));

        // =============== Boundary Values Tests ==================
        // test zero vector from Subtract
        try {
            v1.subtract(v3);
            fail("subtract() if given zero does not throw an exception");
        } catch (Exception e) {}

    }

    @Test
    public void testScale() {
        Vector v1 = new Vector(1,-2,3);

        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(-1,2,-3), v1.scale(-1));

        // =============== Boundary Values Tests ==================
        // test zero vector from Scale
        try {
            v1.scale(0);
            fail("scale() If multiplied by zero does not throw an exception");
        } catch (Exception e) {}
    }

    @Test
    public void testDotProduct() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(-5,7,-8);
        Vector v3 = new Vector(-16,2,4);// orthogonal for v1

        // ============ Equivalence Partitions Tests ==============
        assertEquals(-15d , v1.dotProduct(v2), 0.3);

        // =============== Boundary Values Tests ==================
        // test orthogonal vectors from Dot product return Zero
        assertEquals("DotProduct() should return zero orthogonal vectors", 0, v1.dotProduct(v3), 0.3);
    }

    @Test
    public void testCrossProduct() {
        /*Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(-5,7,-8);

        assertEquals(new Vector(-37, -7, 17), v1.crossProduct(v2));
        */

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

    }

    @Test
    public void testLengthSquared() {

        Vector v1 = new Vector(2,3,4);
        Vector v2 = new Vector(1,1,1);

        // ============ Equivalence Partitions Tests ==============
        assertEquals(29, v1.lengthSquared(), 0.3);
        assertEquals(3, v2.lengthSquared(), 0.3);

    }

    @Test
    public void testLength() {
        Vector v1 = new Vector(2,3,4);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(5.385, v1.length(), 0.0003);
    }

    @Test
    public void testNormalize() {
        Vector v1 = new Vector(6,6,3);

        v1.normalize();
        // ============ Equivalence Partitions Tests ==============
        assertTrue("ERROR: Normalize() function not create vector with length of value 1", v1.length() == 1);
        assertEquals(new Vector(6/9d, 6/9d, 3/9d), v1);

    }

    @Test
    public void testNormalized() {

        Vector v1 = new Vector(6,6,3);
        Vector v1Old = new Vector(v1);
        Vector v2 = v1.normalized();
        // ============ Equivalence Partitions Tests ==============
        assertEquals("ERROR: Normalized() function not create new vector",v1Old, v1);
        assertTrue("ERROR: Normalized() function not create vector with length of value 1", v2.length() == 1);
    }
}