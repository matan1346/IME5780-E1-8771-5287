package geometries;

import java.util.Objects;

/**
 * RadialGeometry abstract class is represents objects with radius
 */
public abstract class RadialGeometry implements Geometry {

    /**
     * the value of the radius
     */
    private double _radius;

    /**
     * Constructor that gets radius value and sets it
     * @param r
     */
    public RadialGeometry(double r)
    {
        _radius = r;
    }

    /**
     * Copy Constructor that gets RadialGeometry object and creates new object
     * @param rg
     */
    public RadialGeometry(RadialGeometry rg)
    {
        _radius = rg._radius;
    }

    /**
     * returns the radius value
     * @return double radius value
     */
    public double getRadius()
    {
        return _radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RadialGeometry that = (RadialGeometry) o;
        return Double.compare(that._radius, _radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_radius);
    }

    @Override
    public String toString() {
        return "RadialGeometry{" +
                "_radius=" + _radius +
                '}';
    }
}
