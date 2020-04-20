
package primitives;
import java.util.*;

import static java.lang.Math.sqrt;

/**
 * class Point3D that representing a point in 3d with 3 lines
 */
public class Point3D {
    /**
     * Coordinate that represents the x point
     */
    private Coordinate coord_X;
    /**
     * Coordinate that represents the y point
     */
    private Coordinate coord_Y;
    /**
     * Coordinate that represents the z point
     */
    private Coordinate coord_Z;
    /**
     * Default point with values (0,0,0)
     */
    public static final Point3D ZERO = new Point3D(0,0,0);

    /**
     * Constructor that gets 3 coordinates and sets them
     * @param coord1 x coordinate
     * @param coord2 y coordinate
     * @param coord3 z coordinate
     */
    public Point3D(Coordinate coord1, Coordinate coord2, Coordinate coord3) {
        this.coord_X = coord1;
        this.coord_Y = coord2;
        this.coord_Z = coord3;
    }

    /**
     * Constructor that gets 3 numbers (double) and sets the coordinates
     * @param coord1 x coordinate number
     * @param coord2 y coordinate number
     * @param coord3 z coordinate number
     */
    public Point3D(double coord1, double coord2, double coord3) {
        this(new Coordinate(coord1), new Coordinate(coord2), new Coordinate(coord3));
    }

    /**
     * Constructor that gets Point and set the coordinates according to that point
     * @param point3D point object
     */
    public Point3D(Point3D point3D) {
        this(new Coordinate(point3D.coord_X), new Coordinate(point3D.coord_Y), new Coordinate(point3D.coord_Z));
    }

    /**
     * return the x coordinate of the point
     * @return Coordinate x coordinate
     */
    public Coordinate getX() {
        return coord_X;
    }

    /**
     * return the y coordinate of the point
     * @return Coordinate y coordinate
     */
    public Coordinate getY() {
        return coord_Y;
    }

    /**
     * return the z coordinate of the point
     * @return Coordinate z coordinate
     */
    public Coordinate getZ() {
        return coord_Z;
    }

    /**
     * Subtract point from a point and returns a new vector
     * @param p1 point object
     * @return Vector new vector after subtraction of a point
     */
    public Vector subtract(Point3D p1)
    {
        return new Vector(this.getX().get() - p1.getX().get(),
                this.getY().get() - p1.getY().get(),
                this.getZ().get() - p1.getZ().get());
    }

    /**
     * Adding point to vector and return new vector
     * @param v1 vector object
     * @return Point3D new point after adding vector
     */
    public Point3D add(Vector v1){
        return new Point3D(v1.getHeader().getX().get() + coord_X.get(),
                v1.getHeader().getY().get() + coord_Y.get(),
                v1.getHeader().getZ().get() + coord_Z.get());
    }

    /**
     * getting the distance squared between two points
     * @param p target point object
     * @return double squared distance
     */
    public double distanceSquared(Point3D p){
        return  (this.coord_X._coord - p.coord_X._coord) * (this.coord_X._coord - p.coord_X._coord) +
                (this.coord_Y._coord - p.coord_Y._coord) * (this.coord_Y._coord - p.coord_Y._coord) +
                (this.coord_Z._coord - p.coord_Z._coord) * (this.coord_Z._coord - p.coord_Z._coord);
    }

    /**
     * Return the distance between two points
     * @param p target point object
     * @return double distance
     */
    public double distance(Point3D p){
        return sqrt(distanceSquared(p));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return coord_X.equals(point3D.coord_X) &&
                coord_Y.equals(point3D.coord_Y) &&
                coord_Z.equals(point3D.coord_Z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coord_X, coord_Y, coord_Z);
    }

    @Override
    public String toString() {
        return "[" + coord_X + ", " + coord_Y + ", " + coord_Z + "]";
    }
}
