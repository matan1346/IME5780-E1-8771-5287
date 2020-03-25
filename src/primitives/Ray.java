package primitives;

import java.util.Objects;

/**
 * Ray class that represents a ray in a 3D Dimension
 */
public class Ray {
    /**
     * Point header of the Ray
     */
    private Point3D _p;
    /**
     * Vector direction of the Ray
     */
    private Vector _dir;

    /**
     * Constructor that gets a point and a vector and sets them
     * @param _p point object represents the header of the Ray
     * @param _dir vector object represents the vector of the Ray
     * @throws IllegalArgumentException throw exception when vector is not normalized
     */
    public Ray(Point3D _p, Vector _dir) throws IllegalArgumentException {

        if(_dir.length() != 1.0)
            throw new IllegalArgumentException("Vector is not normalized");

        this._p = new Point3D(_p);
        this._dir = new Vector(_dir);
    }

    /**
     * Copy constructor
     * @param r Ray object to be copied
     */
    public Ray(Ray r) {
        this(r._p, r._dir);
    }

    /**
     * returns the point header of the ray
     * @return Point3D point header
     */
    public Point3D get_p() {
        return _p;
    }

    /**
     * returns the vector direction of the ray
     * @return Vector vector direction
     */
    public Vector get_dir() {
        return _dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p.equals(ray._p) &&
                _dir.equals(ray._dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_p, _dir);
    }

    @Override
    public String toString() {
        return "Ray [" +
                "Point: " + _p +
                ", Direction " + _dir +
                "]";
    }
}
