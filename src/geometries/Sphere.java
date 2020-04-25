package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;

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
     * @param p point object
     * @return new vector that is normal to that sphere
     */
    public Vector getNormal(Point3D p)
    {
        Vector orthogonal = new Vector(p.subtract(_center));

        return orthogonal.normalized();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Sphere sphere = (Sphere) o;
        return _center.equals(sphere._center);
    }
    //
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

    /**
     * calculate the points of the intersections with the given ray to the sphere
     * @param ray Ray which should intersect with the sphere
     * @return List<Point3D> which should return null on none point, or list of points that intersect the sphere
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D p0 = ray.get_p();
        Vector v = ray.get_dir();
        Vector u;
        try {
            u = _center.subtract(p0); // p0 == _center
        } catch (IllegalArgumentException e) {
            return List.of(ray.getTargetPoint(_radius));
        }
        double tm = alignZero(v.dotProduct(u));
        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(_radius * _radius - dSquared);

        if (thSquared <= 0) return null;

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0) return List.of(ray.getTargetPoint(t1), ray.getTargetPoint(t2)); //P1 , P2
        if (t1 > 0)
            return List.of(ray.getTargetPoint(t1));
        else
            return List.of(ray.getTargetPoint(t2));
    }
}
