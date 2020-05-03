package elements;

import primitives.Color;

public class AmbientLight {

    Color _intensity;
    double Ka;

    public AmbientLight(Color _intensity, double ka) {
        this._intensity = _intensity;
        Ka = ka;
    }

    public Color getIntensity()
    {
        return _intensity;
    }


}
