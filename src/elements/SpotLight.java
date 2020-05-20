package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * represents the point light
 */
public class SpotLight extends PointLight {
    /**
     * direction vector of the point light
     */
    private Vector _direction;

    /**
     * Constructor that gets intensity color, position, 3 numbers to scale with and direction vector
     * and sets them
     * @param _intensity Color intensity color
     * @param _position Point3D position point
     * @param kC double number one st scale
     * @param kL double number two st scale
     * @param kQ double number three st scale
     * @param _direction Vector direction vector
     */
    public SpotLight(Color _intensity, Point3D _position, double kC, double kL, double kQ, Vector _direction) {
        super(_intensity, _position, kC, kL, kQ);
        this._direction = _direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double projection = _direction.dotProduct(getL(p));
        if(isZero(projection)) return Color.BLACK;

        double factor = Math.max(0, projection);
        Color pointLightIntensity = super.getIntensity(p);

        return pointLightIntensity.scale(factor);
    }


}
