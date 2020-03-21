
package primitives;
import java.util.*;
import java.math.*;
import java.lang.Math;

import static java.lang.Math.sqrt;

/**
 * class Point3D that representing a point in 3d with 3 lines
 */
public class Point3D {
    private Coordinate coord1;
    private Coordinate coord2;
    private Coordinate coord3;
    private static Point3D ZERO = new Point3D(0,0,0);

    public Point3D(Coordinate coord1, Coordinate coord2, Coordinate coord3) {
        this.coord1 = coord1;
        this.coord2 = coord2;
        this.coord3 = coord3;
    }

    public Point3D(double coord1, double coord2, double coord3) {
        this(new Coordinate(coord1), new Coordinate(coord2), new Coordinate(coord3));
    }

    public Point3D(Point3D point3D) {
        this(new Coordinate(point3D.coord1), new Coordinate(point3D.coord2), new Coordinate(point3D.coord3));
    }


    public Coordinate getCoord1() {
        return coord1;
    }

    public Coordinate getCoord2() {
        return coord2;
    }

    public Coordinate getCoord3() {
        return coord3;
    }

    public static Point3D getZERO() {
        return ZERO;
    }

    public Vector subtract(Point3D p1)
    {
        Point3D p2 = new Point3D(this.getCoord1().get() - p1.getCoord1().get(),
                this.getCoord2().get() - p1.getCoord2().get(),
                this.getCoord3().get() - p1.getCoord3().get());


        return new Vector(p1);

    }

    //לבדוק מה הכוונה
    public Point3D add(Vector v1){
        return new Point3D(v1.getHeader().getCoord1().get() + coord1.get(),
                v1.getHeader().getCoord2().get() + coord2.get(),
                v1.getHeader().getCoord3().get() + coord3.get());
    }

    public double distanceSquared(Point3D p){
        return  (this.coord1._coord - p.coord1._coord) * (this.coord1._coord - p.coord1._coord) +
                (this.coord2._coord - p.coord2._coord) * (this.coord2._coord - p.coord2._coord) +
                (this.coord3._coord - p.coord3._coord) * (this.coord3._coord - p.coord3._coord);
    }

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
}
