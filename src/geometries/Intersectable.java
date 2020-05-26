package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

/**
 * Intersectable interface that represents the geometries that have intersections, or could have intersections with given ray
 */
public interface Intersectable {

    /**
     * This class represents the the geometry point
     */
    public static class GeoPoint{
        protected final Geometry geometry;
        protected final Point3D point;

        /**
         * Constructor that gets a geometry and point, and sets them
         * @param geometry Geometry the geometry to set
         * @param point Point3D the point to set
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = new Point3D(point);
        }

        public Point3D getPoint() {
            return point;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) &&
                    point.equals(geoPoint.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }
    }

    /**
     * calculate the points of the intersections with the given ray to the geometry
     * @param ray Ray which should intersect with the geometry
     * @return List<GeoPoint> which should return null on none point, or list of points that intersect the geometry
     */
    default List<GeoPoint> findIntersections(Ray ray){
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * calculate the points of the intersections with the given ray to the geometry
     * @param ray Ray which should intersect with the geometry
     * @param max double the maximum distance
     * @return List<GeoPoint> which should return null on none point, or list of points that intersect the geometry
     */
    List<GeoPoint> findIntersections(Ray ray, double max);

}
