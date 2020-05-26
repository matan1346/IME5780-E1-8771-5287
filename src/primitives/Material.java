package primitives;

/**
 * represents the material
 */
public class Material {

    /**
     * kT - shkifut,kR - ishtakfut
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
     * Constructor that gets 2 numbers to scale with and shininess, and shkifut and eshtakfut, and sets them
     * @param kD double number one st scale
     * @param kS double number two st scale
     * @param nShininess int number
     * @param kT double shkifut value
     * @param kR double eshtakfut value
     */
    public Material(double kD, double kS, int nShininess, double kT, double kR) {
        this.kD = kD;
        this.kS = kS;
        this.nShininess = nShininess;
        this.kT = kT;
        this.kR = kR;
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

    /**
     * Getter that return the shkifut value
     * @return double shkifut
     */
    public double getKT() {
        return kT;
    }

    /**
     * Getter that return the ehstakfut value
     * @return double ehstakfut
     */
    public double getKR() {
        return kR;
    }
}
