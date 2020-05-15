package geometries;

import primitives.*;

/**
 * Geometry interface that represents the actions and stuff Geometries are responsible to supply
 */
public abstract class Geometry implements Intersectable {
    protected Color _emission;

    public Geometry(Color _emission) {
        this._emission = _emission;
    }

    public Geometry() {
        this(Color.BLACK);
    }

    /**
     * Calculating the normal vector of the geometry
     * @param p point object
     * @return new vector that is normal to that geometry
     */
    public abstract Vector getNormal(Point3D p);


    /**
     * Getter that return the emission color of the geometry
     * @return Color the emission color of the geometry
     */
    public Color getEmission() {
        return _emission;
    }
}
