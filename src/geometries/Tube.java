package geometries;


import primitives.*;

import java.util.List;
import java.util.Objects;


import static primitives.Util.isZero;

/**
 * Tube class represents a tube in 3D dimension
 */
public class Tube extends RadialGeometry {
    /**
     * Ray that represents the start position and the direction of the tube
     */
    Ray _axisRay;

    /**
     * Constructor that gets a ray and radius value and sets them
     * @param _ray ray object
     * @param r radius value
     */
    public Tube(Ray _ray, double r) {
        super(r);
        this._axisRay = _ray;
    }

    /**
     * returns the axis ray of the tube
     * @return Ray axis ray of the tube
     */
    public Ray get_ray() {
        return _axisRay;
    }

    /**
     * Calculating the normal vector of the Tube in specific point
     * @param p point object
     * @return new vector that is normal to that tube
     */
    public Vector getNormal(Point3D p) {
        //The vector from the point of the cylinder to the given point
        Point3D o = _axisRay.get_p();
        Vector v = _axisRay.get_dir();

        Vector vector1 = p.subtract(o);

        //We need the projection to multiply the _direction unit vector
        double projection = vector1.dotProduct(v);
        if (!isZero(projection)) {
            // projection of P-O on the ray:
            o.add(v.scale(projection));
        }

        //This vector is orthogonal to the _direction vector.
        Vector check = p.subtract(o);
        return check.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tube tube = (Tube) o;
        return _axisRay.equals(tube._axisRay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _axisRay);
    }

    @Override
    public String toString() {
        return "Tube {\n" +
                "\t" + _axisRay +
                "\n\t" + super.toString() +
                "\n}";
    }

    /**
     * calculate the points of the intersections with the given ray to the tube
     * @param ray Ray which should intersect with the tube
     * @return List<Point3D> which should return null on none point, or list of points that intersect the tube
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
