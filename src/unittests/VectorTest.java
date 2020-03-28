package unittests;

import org.junit.Test;
import primitives.Vector;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Matan and Shimon
 */
public class VectorTest {

    @Test
    public void testAdd() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(2,3,4);

        assertEquals(new Vector(3, 5, 7), v1.add(v2));

    }

    @Test
    public void testSubtract() {

        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(1,2,3.5);

        assertEquals(new Vector(0, 0, 0.5), v1.subtract(v2));
    }

    @Test
    public void testScale() {
        Vector v1 = new Vector(1,-2,3);

        assertEquals(new Vector(-1,2,-3), v1.scale(-1));
    }

    @Test
    public void testDotProduct() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(-5,7,-8);

        assertEquals(-15d , v1.dotProduct(v2), 0.3);
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

        assertEquals(29, v1.lengthSquared(), 0.3);
    }

    @Test
    public void testLength() {
        Vector v1 = new Vector(2,3,4);



        assertEquals(5.385, v1.length(), 0.0003);
    }

    @Test
    public void testNormalize() {
        Vector v1 = new Vector(6,6,3);

        v1.normalize();

        assertTrue("ERROR: Normalize() function not create vector with length of value 1", v1.length() == 1);
        //assertEquals(new Vector(6/9d, 6/9d, 3/9d), v1);

    }

    @Test
    public void testNormalized() {

        Vector v1 = new Vector(6,6,3);
        Vector v1Old = new Vector(v1);
        Vector v2 = v1.normalized();

        assertEquals("ERROR: Normalized() function not create new vector",v1Old, v1);
        assertTrue("ERROR: Normalized() function not create vector with length of value 1", v2.length() == 1);
    }
}