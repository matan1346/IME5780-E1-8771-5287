package elements;

import primitives.Color;

/**
 * Represents the light
 */
abstract class Light {

    /**
     * intensity of the light
     */
    protected Color _intensity;

    /**
     * Constructor that gets intensity color and set that
     * @param _intensity Color intensity color of the light
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    /**
     * Getter that return the intensity of the color
     * @return Color the intensity of the color
     */
    public Color getIntensity() {
        return _intensity;
    }
}
