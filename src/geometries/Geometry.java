package geometries;

import primitives.*;

/**
 * Geometry interface that represents the actions and stuff Geometries are responsible to supply
 */
public abstract class Geometry implements Intersectable {
    /**
     * Color emission of the geometry
     */
    protected Color _emission;

    /**
     * material of the geometry
     */
    protected Material _material;

    /**
     * Constructor that gets color emission of the geometry, and material and sets that.
     * @param _emission Color emission color of the geometry
     * @param _material material of the geometry
     */
    public Geometry(Color _emission, Material _material) {
        this._emission = new Color(_emission);
        this._material = _material;
    }

    /**
     * Constructor that gets color emission of the geometry and sets that.
     * @param _emission Color emission color of the geometry
     */
    public Geometry(Color _emission) {
        this(_emission, new Material(0,0,0));
    }

    /**
     * Default constructor that sets the color emission to black
     */
    public Geometry() {
        this(Color.BLACK, new Material(0,0,0));
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

    /**
     * Getter that return the material of the geometry
     * @return Material the material of the geometry
     */
    public Material getMaterial() {
        return _material;
    }
}
