package elements;

import primitives.Color;

/**
 * AmbientLight class that represent the light with intensity and Ka
 */
public class AmbientLight {

    /**
     * Intensity color
     */
    Color _intensity;

    /**
     * Ka
     */
    double Ka;

    /**
     * Constructor that gets intensity and Ka and sets then
     * @param _intensity Color intensity color
     * @param ka double the number to multiply with color
     */
    public AmbientLight(Color _intensity, double ka) {
        this._intensity = _intensity;
        Ka = ka;
    }

    /**
     * Getter that return the intensity of the color
     * @return Color the intensity of the color
     */
    public Color getIntensity()
    {
        return _intensity;
    }


}
