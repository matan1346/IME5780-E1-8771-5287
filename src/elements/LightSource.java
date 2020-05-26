package elements;

import primitives.*;

/**
 * LightSource interface that represents the source of the light
 */
public interface LightSource {

    /**
     * Getter that return the intensity of the light with the given point
     * @param p Point3D point
     * @return Color the intensity of the light with the given point
     */
    public Color getIntensity(Point3D p);

    /**
     * Getter that return the vector with the given point
     * @param p Point3D
     * @return Vector the vector with te given point
     */
    public Vector getL(Point3D p);


    /**
     * Calculate the distance of point with light source
     * @param p Point3D point to calculate the distance with
     * @return double distance of point with light source
     */
    public double getDistance(Point3D p);

}
