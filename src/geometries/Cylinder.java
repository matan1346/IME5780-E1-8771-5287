package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

import static primitives.Util.*;

/**
 * Cylinder class represents the cylinder in 3D  dimension
 */
public class Cylinder extends Tube {
    /**
     * the height of the cylinder
     */
    double height;

    /**
     * Constructor that gets a ray, height and radius and sets them
     * @param _ray axis ray
     * @param r radius value
     * @param height height value
     */
    public Cylinder(Ray _ray, double r, double height)
    {
        super(_ray, r);
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
     * @param p point object
     * @return new vector that is normal to that cylinder
     */
    public Vector getNormal(Point3D p)
    {
        Point3D o = _axisRay.get_p();
        Vector v = _axisRay.get_dir();

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(p.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (t == 0 || isZero(height - t)) // if it's close to 0, we'll get ZERO vector exception
            return v;

        o = o.add(v.scale(t));
        return p.subtract(o).normalize();


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

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}