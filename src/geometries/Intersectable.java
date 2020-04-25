package geometries;

import primitives.*;

import java.util.List;

/**
 * Intersectable interface that represents the geometries that have intersections, or could have intersections with given ray
 */
public interface Intersectable {

    /**
     * calculate the points of the intersections with the given ray to the geometry
     * @param ray Ray which should intersect with the geometry
     * @return List<Point3D> which should return null on none point, or list of points that intersect the geometry
     */
    List<Point3D> findIntersections(Ray ray);


}
