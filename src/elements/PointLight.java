package elements;

import primitives.*;

/**
 * represent the point light
 */
public class PointLight extends Light implements LightSource {
    /**
     * position of the point light
     */
    protected Point3D _position;

    /**
     * 3 of numbers for scales
     */
    protected double kC, kL, kQ;

    /**
     * Constructor that gets intensity color, position, and 3 numbers to scale with and sets them
     * @param _intensity Color intensity color
     * @param _position Point3D position point
     * @param kC double number one st scale
     * @param kL double number two st scale
     * @param kQ double number three st scale
     */
    public PointLight(Color _intensity, Point3D _position, double kC, double kL, double kQ) {
        super(_intensity);
        this._position = _position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double dist = p.distance(_position);
        double distSqr = p.distanceSquared(_position);

        return (_intensity.reduce(kC + kL*dist + kQ*distSqr));
    }

    @Override
    public Vector getL(Point3D p) {
        if(p.equals(_position)) return null;

        return p.subtract(_position).normalized();
    }

    @Override
    public double getDistance(Point3D p) {
        return _position.distance(p);
    }
}
