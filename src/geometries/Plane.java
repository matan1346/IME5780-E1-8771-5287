package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.util.Objects;

/**
 * Plane class that represents a plan in the 3D dimension
 */
public class Plane implements Geometry {

    /**
     * Point3D that represents the position
     */
    Point3D _p;

    /**
     * Vector that is normal to the plane
     */
    Vector normal;

    /**
     * Constructor that gets three points and sets them point and normal vector
     * Using 3 points to calculate the normal vector
     * The first point is selected to represents the point position of plane
     * @param _p1 point object represents a point in the plane
     * @param _p2 point object represents a point in the plane
     * @param _p3 point object represents a point in the plane
     */
    public Plane(Point3D _p1,Point3D _p2,Point3D _p3) {

        this._p = _p1;
        normal = null;

    }

    /**
     * Constructor that gets a point and a vector normal
     * @param _p point object
     * @param _normal normal vector
     */
    public Plane(Point3D _p, Vector _normal) {
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
        return normal;
    }

    /**
     * Calculating the normal vector of the plane in specific point
     * @param p1 point object
     * @return new vector that is normal to that plane
     */
    public Vector getNormal(Point3D p1)
    {
        return null;
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
}
