package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.util.Objects;

/**
 * Cylinder class represents the cylinder in 3D  dimension
 */
public class Cylinder extends RadialGeometry {
    /**
     * the height of the cylinder
     */
    double height;

    /**
     * Constructor that gets an height and radius value and sets them
     * @param height height value
     * @param r radius value
     */
    public Cylinder(double height, double r) {
        super(r);
        this.height = height;
    }

    /**
     * returns the cylinder height value
     * @return height value
     */
    public double getHeight() {
        return height;
    }

    /**
     * Calculating the normal vector of the Cylinder in specific point
     * @param p1 point object
     * @return new vector that is normal to that cylinder
     */
    public Vector getNormal(Point3D p1)
    {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cylinder cylinder = (Cylinder) o;
        return Double.compare(cylinder.height, height) == 0;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height);
    }
}