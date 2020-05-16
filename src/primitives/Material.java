package primitives;

/**
 * represents the material
 */
public class Material {

    /**
     * two numbers that can be scaled
     */
    double kD, kS;

    /**
     * represents the shine
     */
    int nShininess;

    /**
     * Constructor that gets 2 numbers to scale with and shininess, and sets them
     * @param kD double number one st scale
     * @param kS double number two st scale
     * @param nShininess int number
     */
    public Material(double kD, double kS, int nShininess) {
        this.kD = kD;
        this.kS = kS;
        this.nShininess = nShininess;
    }

    /**
     * Getter thatreturn the number of kD
     * @return double kD
     */
    public double getkD() {
        return kD;
    }

    /**
     * Getter that return the number of kS
     * @return double kS
     */
    public double getkS() {
        return kS;
    }

    /**
     * Getter that return the number shininess
     * @return int shininess
     */
    public int getnShininess() {
        return nShininess;
    }
}
