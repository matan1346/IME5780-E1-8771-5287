
package geometries;

import primitives.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class of Cube
 */
public class Cube extends Geometry {
    /**
     * two Point3D that represent the minimum and the maximum points of the cube
     */
    private Point3D minPoint, maxPoint;

    /**
     * Constructor that gets a minimum and maximum point and sets them
     * @param min Point3D represents the minimum point of the cube
     * @param max Point3D represents the maximum point of the cube
     */
    public Cube(Point3D min, Point3D max) {
        super(Color.BLACK, new Material(0,0,0));
        minPoint = min;
        maxPoint = max;
    }
    /**
     * Constructor that gets emission color, material, min point and max point and sets them
     * @param color Color emission color of the cube
     * @param material Material material value of the cube
     * @param min Point3D represents the minimum point of the cube
     * @param max Point3D represents the maximum point of the cube
     */
    public Cube(Color color, Material material, Point3D min, Point3D max) {
        super(color, material);
        minPoint = min;
        maxPoint = max;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        return findIntersections(ray, 0);
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        List<GeoPoint> list = new ArrayList<>();
        Point3D p0 = ray.get_p();
        Vector dir = ray.get_dir();

        double maxPointX = maxPoint.getX().get();
        double maxPointY = maxPoint.getY().get();
        double maxPointZ = maxPoint.getZ().get();

        double minPointX = minPoint.getX().get();
        double minPointY = minPoint.getY().get();
        double minPointZ = minPoint.getZ().get();

        double p0X = p0.getX().get();
        double p0Y = p0.getY().get();
        double p0Z = p0.getZ().get();

        double dirX = 1 / dir.getHeader().getX().get();
        double dirY = 1 / dir.getHeader().getY().get();
        double dirZ = 1 / dir.getHeader().getZ().get();

        double minX, maxX, minY, maxY, minZ, maxZ;

        if (dirX < 0) {
            minX = (maxPointX - p0X) * dirX;
            maxX = (minPointX - p0X) * dirX;

        } else {
            maxX = (maxPointX - p0X) * dirX;
            minX = (minPointX - p0X) * dirX;
        }

        if (dirY < 0) {
            minY = (maxPointY - p0Y) * dirY;
            maxY = (minPointY - p0Y) * dirY;

        } else {
            maxY = (maxPointY - p0Y) * dirY;
            minY = (minPointY - p0Y) * dirY;
        }

        if (Double.isNaN(minX))
            minX = Double.NEGATIVE_INFINITY;
        if (Double.isNaN(maxX))
            minX = Double.POSITIVE_INFINITY;
        if (Double.isNaN(minY))
            minX = Double.NEGATIVE_INFINITY;
        if (Double.isNaN(maxY))
            minX = Double.POSITIVE_INFINITY;

        if ((minX > maxY) || (minY > maxX))
            return list;
        if (minY > minX)
            minX = minY;
        if (maxY < maxX)
            maxX = maxY;

        if (dirZ < 0) {
            minZ = (maxPointZ - p0Z) * dirZ;
            maxZ = (minPointZ - p0Z) * dirZ;

        } else {
            maxZ = (maxPointZ - p0Z) * dirZ;
            minZ = (minPointZ - p0Z) * dirZ;
        }

        if (Double.isNaN(minZ))
            minX = Double.NEGATIVE_INFINITY;

        if (Double.isNaN(maxZ))
            minX = Double.POSITIVE_INFINITY;

        if ((minX > maxZ) || (minZ > maxX))
            return list;
        if (minZ > minX)
            minX = minZ;
        if (maxZ < maxX)
            maxX = maxZ;

        if (!Util.isZero(minX) && minX > 0 && Double.isFinite(minX))
            list.add(new GeoPoint(this, p0.add(dir.scale(minX))));

        if (!Util.isZero(maxX) && maxX > 0 && Double.isFinite(maxX))
            list.add(new GeoPoint(this, p0.add(dir.scale(maxX))));

        return list;
    }

    /**
     * Getter for the max point
     * @return Point3D max point
     */
    public Point3D getMax() {
        return maxPoint;
    }

    /**
     * Getter for min point
     * @return Point3D min point
     */
    public Point3D getMin() {
        return minPoint;
    }

    @Override
    public Vector getNormal(Point3D point) {
        Point3D center = new Point3D((minPoint.getX().get() + maxPoint.getX().get()) / 2,
                (minPoint.getY().get() + maxPoint.getY().get()) / 2,
                (minPoint.getZ().get() + maxPoint.getZ().get()) / 2);
        return point.subtract(center).normalized();
    }
}