package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * Triangle class represents triangle in 3D dimension
 */
public class Triangle extends Polygon {

    /**
     * Constructor that gets 3 points and sets them
     * @param p1 point object
     * @param p2 point object
     * @param p3 point object
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3)
    {
        super(p1, p2, p3);
    }

    @Override
    public String toString() {
        return "Triangle {\n" +
                "\tVertices: " + _vertices +
                ",\n\t" + _plane +
                "\n}";
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
