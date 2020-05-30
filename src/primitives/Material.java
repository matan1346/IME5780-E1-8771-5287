package primitives;

/**
 * represents the material
 */
public class Material {

    /**
     * kT - transparency value,kR - reflection value
     */
    double kT, kR;

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
        this(kD, kS, nShininess, 0 , 0);
    }

    /**
     * Constructor that gets 2 numbers to scale with and shininess, and transparency and reflection, and sets them
     * @param kD double number one st scale
     * @param kS double number two st scale
     * @param nShininess int number
     * @param kT double transparency value
     * @param kR double reflection value
     */
    public Material(double kD, double kS, int nShininess, double kT, double kR) {
        this.kD = kD;
        this.kS = kS;
        this.nShininess = nShininess;
        this.kT = kT;
        this.kR = kR;
    }
    /**
     * Getter that return the number of kD
     * @return double kD
     */
    public double getKD() {
        return kD;
    }

    /**
     * Getter that return the number of kS
     * @return double kS
     */
    public double getKS() {
        return kS;
    }

    /**
     * Getter that return the number shininess
     * @return int shininess
     */
    public int getnShininess() {
        return nShininess;
    }

    /**
     * Getter that return the transparency value
     * @return double transparency
     */
    public double getKT() {
        return kT;
    }

    /**
     * Getter that return the reflection value
     * @return double reflection
     */
    public double getKR() {
        return kR;
    }
}
