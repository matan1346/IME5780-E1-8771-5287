package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * represents the direction light
 */
public class DirectionalLight extends Light implements LightSource {
    /**
     * Vector of the direction of the light
     */
    private Vector _direction;

    /**
     * Constructor that gets intensity color and direction vector and sets them
     * @param _intensity Color intensity color
     * @param _direction Vector direction vector
     */
    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        this._direction = _direction.normalized();
    }


    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
