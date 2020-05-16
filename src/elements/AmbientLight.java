package elements;

import primitives.Color;

/**
 * AmbientLight class that represent the light with intensity and Ka
 */
public class AmbientLight extends Light {



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
        super(_intensity.scale(ka));
        Ka = ka;
    }




}
