
package primitives;
import java.util.*;
import java.math.*;
import java.lang.Math;

import static java.lang.Math.sqrt;

/**
 * class Point3D that representing a point in 3d with 3 lines
 */
public class Point3D {
    /**
     * Coordinate that represents the x point
     */
    private Coordinate coord1;
    /**
     * Coordinate that represents the y point
     */
    private Coordinate coord2;
    /**
     * Coordinate that represents the z point
     */
    private Coordinate coord3;
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
        this.coord1 = coord1;
        this.coord2 = coord2;
        this.coord3 = coord3;
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
        this(new Coordinate(point3D.coord1), new Coordinate(point3D.coord2), new Coordinate(point3D.coord3));
    }

    /**
     * return the x coordinate of the point
     * @return Coordinate x coordinate
     */
    public Coordinate getCoord1() {
        return coord1;
    }

    /**
     * return the y coordinate of the point
     * @return Coordinate y coordinate
     */
    public Coordinate getCoord2() {
        return coord2;
    }

    /**
     * return the z coordinate of the point
     * @return Coordinate z coordinate
     */
    public Coordinate getCoord3() {
        return coord3;
    }

    /**
     * Subtract point from a point and returns a new vector
     * @param p1 point object
     * @return Vector new vector after subtraction of a point
     */
    public Vector subtract(Point3D p1)
    {
        return new Vector(this.getCoord1().get() - p1.getCoord1().get(),
                this.getCoord2().get() - p1.getCoord2().get(),
                this.getCoord3().get() - p1.getCoord3().get());
    }

    /**
     * Adding point to vector and return new vector
     * @param v1 vector object
     * @return Point3D new point after adding vector
     */
    public Point3D add(Vector v1){
        return new Point3D(v1.getHeader().getCoord1().get() + coord1.get(),
                v1.getHeader().getCoord2().get() + coord2.get(),
                v1.getHeader().getCoord3().get() + coord3.get());
    }

    /**
     * getting the distance squared between two points
     * @param p target point object
     * @return double squared distance
     */
    public double distanceSquared(Point3D p){
        return  (this.coord1._coord - p.coord1._coord) * (this.coord1._coord - p.coord1._coord) +
                (this.coord2._coord - p.coord2._coord) * (this.coord2._coord - p.coord2._coord) +
                (this.coord3._coord - p.coord3._coord) * (this.coord3._coord - p.coord3._coord);
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
        return coord1.equals(point3D.coord1) &&
                coord2.equals(point3D.coord2) &&
                coord3.equals(point3D.coord3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coord1, coord2, coord3);
    }

    @Override
    public String toString() {
        return "[" + coord1 + ", " + coord2 + ", " + coord3 + "]";
    }
}
