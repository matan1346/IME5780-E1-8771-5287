package geometries;

import primitives.*;

/**
 * Geometry interface that represents the actions and stuff Geometries are responsible to supply
 */
public interface Geometry {
    /**
     * Calculating the normal vector of the geometry
     * @param p point object
     * @return new vector that is normal to that geometry
     */
    Vector getNormal(Point3D p);

}
