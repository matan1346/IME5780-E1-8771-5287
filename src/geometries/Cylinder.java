package geometries;

import primitives.*;


import java.util.ArrayList;
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
     * @param _ray Ray axis ray
     * @param r double radius value
     * @param height double height value
     */
    public Cylinder(Ray _ray, double r, double height)
    {
        super(r, _ray);
        this.height = height;
    }

    /**
     * Constructor that gets a color, ray, radius and height and sets them
     * @param _emission Color emission color of the cylinder
     * @param _ray Ray axis ray
     * @param r double radius value
     * @param height double height value
     */
    public Cylinder(Color _emission, Material _material, Ray _ray, double r, double height) {
        super(_emission, _material,r, _ray);
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

    /**
     * calculate the points of the intersections with the given ray to the cylinder
     * @param ray Ray which should intersect with the cylinder
     * @param max double max distance value
     * @return List<Point3D> which should return null on none point, or list of points that intersect the cylinder
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        List<GeoPoint> gps =  super.findIntersections(ray, max);

        if(gps != null)
        {
            List<GeoPoint> n_gps = new ArrayList<>();
            for(GeoPoint gp: gps){
                n_gps.add(new GeoPoint(this, gp.getPoint()));
            }
            return n_gps;
        }
        return null;
    }
}