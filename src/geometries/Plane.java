package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class that represents a plan in the 3D dimension
 */
public class Plane extends Geometry {

    /**
     * Point3D that represents the position
     */
    Point3D _p;

    /**
     * Vector that is normal to the plane
     */
    Vector normal;

    /**
     * Constructor that gets emission color, point1, point2 and point3, and sets them
     * @param emission Color emission color of plane
     * @param p1 Point3D point 1 object represents a point in the plane
     * @param p2 Point3D point 2 object represents a point in the plane
     * @param p3 Point3D point 3 object represents a point in the plane
     */
    public Plane(Color emission, Material material, Point3D p1, Point3D p2, Point3D p3)
    {
        super(emission, material);
        //System.ou
        _p = new Point3D(p1);

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);
        Vector N = U.crossProduct(V);
        N.normalize();

        normal = N.scale(-1);
    }

    /**
     * Constructor that gets three points and sets them point and normal vector
     * Using 3 points to calculate the normal vector
     * The first point is selected to represents the point position of plane
     * @param p1 point object represents a point in the plane
     * @param p2 point object represents a point in the plane
     * @param p3 point object represents a point in the plane
     */
    public Plane(Point3D p1,Point3D p2,Point3D p3) {
        this(Color.BLACK, new Material(0,0,0), p1, p2, p3);
    }

    /**
     * Constructor that gets a point and a vector normal
     * @param _p point object
     * @param _normal normal vector
     */
    public Plane(Point3D _p, Vector _normal) {
        //TODO maybe we have to fix this constructor to add material as well
        this._p = _p;
        this.normal = _normal;
    }

    /**
     * return the point object of plane
     * @return Point3D point object
     */
    public Point3D get_p() {
        return _p;
    }

    /**
     * Calculating the normal of the plane and returns normal vector
     * @return Vector normal vector
     */
    public Vector getNormal() {
        return getNormal(null);
    }

    /**
     * Calculating the normal vector of the plane in specific point
     * @param p point object
     * @return new vector that is normal to that plane
     */
    public Vector getNormal(Point3D p)
    {
        return normal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return _p.equals(plane._p) &&
                normal.equals(plane.normal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_p, normal);
    }

    @Override
    public String toString() {
        return "Plane {" +
                "Point: " + _p +
                ", Normal " + normal +
                '}';
    }

    /**
     * calculate the points of the intersections with the given ray to the plane
     * @param ray Ray which should intersect with the plane
     * @return List<GeoPoint> which should return null on none point, intersect of 1 point which intersect the plane
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        Vector p0Q;
        try {
            p0Q = _p.subtract(ray.get_p());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }

        double nv = normal.dotProduct(ray.get_dir());
        if (isZero(nv)) // ray is parallel to the plane - no intersections
            return null;

        double t = alignZero(normal.dotProduct(p0Q) / nv);

        if (t <= 0) {
            return null;
        }

        GeoPoint geo = new GeoPoint(this, ray.getTargetPoint(t));
        return List.of(geo);
    }
}
