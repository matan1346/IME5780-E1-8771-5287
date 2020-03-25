package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.Objects;

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
     * @param p1 point object
     * @return new vector that is normal to that tube
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


        /*
        * Tube{_axisRay=Ray{_p=[1.0, 2.0, 3.0], _dir=Vector{_header=[0.2672612419124244, 0.5345224838248488, 0.8017837257372732]}}}
        *
        * */
    }
}
