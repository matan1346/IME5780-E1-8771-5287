package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.util.Objects;

/**
 * Sphere class represents a Sphere in 3D dimension
 */
public class Sphere extends RadialGeometry {

    /**
     * Point that represents the middle of the sphere
     */
    Point3D _center;

    /**
     * Constructor that gets a point and a radius value and sets them
     * @param _p point object
     * @param r radius value
     */
    public Sphere(Point3D _p, double r)
    {
        super(r);
        this._center = _p;
    }

    /**
     * Calculating the normal vector of the Sphere in specific point
     * @param p1 point object
     * @return new vector that is normal to that sphere
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
        Sphere sphere = (Sphere) o;
        return _center.equals(sphere._center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _center);
    }

    @Override
    public String toString() {
        return "Sphere {" +
                "Center Point: " + _center +
                '}';
    }
}
