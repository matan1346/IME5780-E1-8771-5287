package elements;

import java.util.Random;
import primitives.*;

import java.util.Map;

import static primitives.Util.isZero;

/**
 * Camera class for represents a class
 */
public class Camera {
    /**
     * Point3D the center point of the camera
     */
    private Point3D _p0;

    /**
     * Vector the towards vector of the camera
     */
    private Vector _Vto;

    /**
     * Vector the up vector of the camera
     */
    private Vector _Vup;

    /**
     * Vector the right vector of the camera
     */
    private Vector _Vright;

    private Random rand = new Random();

    /**
     * Constructor that gets a point, and 2 vectors of to and up and sets them
     * @param p0 Point3D a point that sets the position of the camera
     * @param vto Vector that represent the direction towards
     * @param vup Vector that represents the direction up
     * @throws IllegalArgumentException throws an exception when Vector up and Vector to are not orthogonal
     */
    public Camera(Point3D p0, Vector vto, Vector vup) throws IllegalArgumentException {

        if(!isZero(vto.dotProduct(vup)))
            throw new IllegalArgumentException("Vto and Vup are not orthogonal");

        this._p0 = p0;
        _Vto = vto.normalized();
        _Vup = vup.normalized();
        _Vright = vto.crossProduct(vup).normalize();
    }

    /**
     * Private constructor that gets params of points from xml and sets them
     * @param p0params String[] params of point0
     * @param vToParams String[] params of point1
     * @param vUpParams String[] params of point2
     */
    private Camera(String[] p0params, String[] vToParams, String[] vUpParams)
    {
        this(new Point3D(Double.valueOf(p0params[0]), Double.valueOf(p0params[1]), Double.valueOf(p0params[2])),
                new Vector(Double.valueOf(vToParams[0]), Double.valueOf(vToParams[1]), Double.valueOf(vToParams[2])),
                new Vector(Double.valueOf(vUpParams[0]), Double.valueOf(vUpParams[1]), Double.valueOf(vUpParams[2])));

    }

    /**
     * Constructor that sets the attributes of this object from xml
     * @param attributes Map<String, String> attributes to set
     */
    public Camera(Map<String, String> attributes) {
        this(((String)attributes.get("P0")).split("\\s+"),
                ((String)attributes.get("Vto")).split("\\s+"),
                ((String)attributes.get("Vup")).split("\\s+"));
        }

    /**
     * Gets the layout of the view plane, with units of width, height and positions
     * @param nX int width per item
     * @param nY int height per item
     * @param j int the position of the column
     * @param i int the position of thr row
     * @param screenDistance double screen distance
     * @param screenWidth double screen width
     * @param screenHeight double screen height
     * @param isRandom boolean true of random, else center
     * @return Ray the construct ray in the position i,j with this view plane
     */
    public Ray constructRayThroughPixel (int nX, int nY,
                                         int j, int i, double screenDistance,
                                         double screenWidth, double screenHeight, boolean isRandom)
    {
        Point3D Pc = _p0.add(_Vto.scale(screenDistance));
        double Ry = screenHeight / nY;
        double Rx = screenWidth / nX;

        int intRy = (int)Ry;
        int intRx = (int)Rx;

        //if isRandom is true, than calculate random Ry,Rx, else calculate center
        double calcRy = (isRandom ? rand.nextDouble() + rand.nextInt(intRy <= 0 ? 1 : intRy ) : Ry/2d);
        double calcRx = (isRandom ? rand.nextDouble() + rand.nextInt(intRx <= 0 ? 1 : intRx ) : Rx/2d);
        double Yi = (i - nY/2d)*Ry + calcRy;
        double Xj = (j - nX/2d)*Rx + calcRx;


        Point3D Pij = Pc;

        if(!isZero(Xj))
            Pij = Pij.add(_Vright.scale(Xj));

        if(!isZero(Yi))
            Pij = Pij.add(_Vup.scale(-Yi));

        Vector Vij = Pij.subtract(_p0);

        return new Ray(_p0, Vij.normalize());
    }


    /**
     * Gets the layout of the view plane, with units of width, height and positions
     * @param nX int width per item
     * @param nY int height per item
     * @param j int the position of the column
     * @param i int the position of thr row
     * @param screenDistance double screen distance
     * @param screenWidth double screen width
     * @param screenHeight double screen height
     * @return Ray the construct ray in the position i,j with this view plane
     */
    public Ray constructRayThroughPixel (int nX, int nY,
                                         int j, int i, double screenDistance,
                                         double screenWidth, double screenHeight) {
        return constructRayThroughPixel(nX,nY,j,i,screenDistance,screenWidth,screenHeight, false);
    }









    /**
     * Getter that gets the center point of the camera
     * @return Point3D center point of camera
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * Getter that gets the towards vector of camera
     * @return Vector towards vector of camera
     */
    public Vector getVto() {
        return _Vto;
    }

    /**
     * Getter that gets the vector that points up of camera
     * @return Vector up vector of camera
     */
    public Vector getVup() {
        return _Vup;
    }

    /**
     * Getter that gets the vector that points right of camera
     * @return Vector right vector of camera
     */
    public Vector getVright() {
        return _Vright;
    }
}
