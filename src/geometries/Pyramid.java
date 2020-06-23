package geometries;

import primitives.*;

import java.util.Objects;


/**
 * Pyramid class that represents a pyramid in 3D, includes 4 triangles and a polygon as a base
 */
public class Pyramid extends Geometries {

    private Ray _axisRay;
    private double _widthX;
    private double _widthY;
    private double _height;

    private Point3D _topPyramid;
    private Point3D _pyLeftTop;
    private Point3D _pyRightTop;
    private Point3D _pyLeftBottom;
    private Point3D _pyRightBottom;

    /**
     * Constructor gets a Ray, widthX, widthY and height and sets them
     * @param axisRay Ray axis ray direction of the pyramid
     * @param widthX double widthX length of the pyramid
     * @param widthY double widthY length of the pyramid
     * @param height double height value of the pyramid
     */
    public Pyramid(Ray axisRay, double widthX, double widthY, double height){
        this(Color.BLACK, new Material(0,0,0), axisRay, widthX, widthY, height);
    }

    /**
     * Constructor gets an emission color, material, Ray, widthX, widthY and height and sets them
     * @param emission Color emission color of the pyramid
     * @param material Material the material of the pyramid
     * @param axisRay Ray axis ray direction of the pyramid
     * @param widthX double widthX length of the pyramid
     * @param widthY double widthY length of the pyramid
     * @param height double height value of the pyramid
     */
    public Pyramid(Color emission,Material material,Ray axisRay, double widthX, double widthY, double height){
        Point3D center = new Point3D(axisRay.get_p());
        Vector direction = new Vector(axisRay.get_dir());
        double halfWidthX = widthX/2d;
        double halfWidthY = widthY/2d;

        //Setting private fields
        _axisRay = new Ray(axisRay);
        _widthX = widthX;
        _widthY = widthY;
        _height = height;




        //Point3D pyramid - top point
        _topPyramid = center.add(direction.scale(height));

        //close point to center for making vectors directions on the base polygon
        Point3D nearPCenter = center.add(new Vector(1,0,0));

        //Left and right vector
        Vector proj = direction.crossProduct(nearPCenter.subtract(center));

        //back and and forward vector
        Vector projBack = proj.crossProduct(direction);

        //base top left and to right points
        _pyLeftTop = center.add(projBack.scale(halfWidthY)).add(proj.scale(-halfWidthX));
        _pyRightTop = center.add(projBack.scale(halfWidthY)).add(proj.scale(halfWidthX));

        //base bottom left and bottom right points
        _pyLeftBottom = center.add(projBack.scale(-halfWidthY)).add(proj.scale(-halfWidthX));
        _pyRightBottom = center.add(projBack.scale(-halfWidthY)).add(proj.scale(halfWidthX));

        //adding the geometries that creates the pyramid to the list of geometries
        add(new Triangle(new Color(emission), material, _pyRightTop, _topPyramid, _pyRightBottom),
                new Triangle(new Color(emission), material, _pyRightBottom, _topPyramid, _pyLeftBottom),
                new Triangle(new Color(emission), material, _pyLeftBottom, _topPyramid, _pyLeftTop),
                new Triangle(new Color(emission), material, _pyLeftTop, _topPyramid, _pyRightTop),
                new Polygon(new Color(emission), material, _pyRightTop, _pyRightBottom, _pyLeftBottom, _pyLeftTop));
    }

    /**
     * Getter the axis ray of the Pyramid
     * @return Ray axis ray of pyramid
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     * Getter the widthX length of the Pyramid
     * @return double widthX
     */
    public double getWidthX() {
        return _widthX;
    }

    /**
     * Getter the widthY length of the Pyramid
     * @return double widthY
     */
    public double getWidthY() {
        return _widthY;
    }

    /**
     * Getter the height of the Pyramid
     * @return double height
     */
    public double getHeight() {
        return _height;
    }

    /**
     * Getter the top Point3D of the Pyramid
     * @return Point3D top point of pyramid
     */
    public Point3D getTopPyramid() {
        return _topPyramid;
    }

    /**
     * Getter the left top Point3D in the base of the pyramid
     * @return Point3D left top base point in the pyramid
     */
    public Point3D getPyLeftTop() {
        return _pyLeftTop;
    }

    /**
     * Getter the right top Point3D in the base of the pyramid
     * @return Point3D right top base point in the pyramid
     */
    public Point3D getPyRightTop() {
        return _pyRightTop;
    }

    /**
     * Getter the left bottom Point3D in the base of the pyramid
     * @return Point3D left bottom base point in the pyramid
     */
    public Point3D getPyLeftBottom() {
        return _pyLeftBottom;
    }

    /**
     * Getter the right bottom Point3D in the base of the pyramid
     * @return Point3D right bottom base point in the pyramid
     */
    public Point3D getPyRightBottom() {
        return _pyRightBottom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pyramid pyramid = (Pyramid) o;
        return Double.compare(pyramid._widthX, _widthX) == 0 &&
                Double.compare(pyramid._widthY, _widthY) == 0 &&
                Double.compare(pyramid._height, _height) == 0 &&
                _axisRay.equals(pyramid._axisRay) &&
                _topPyramid.equals(pyramid._topPyramid) &&
                _pyLeftTop.equals(pyramid._pyLeftTop) &&
                _pyRightTop.equals(pyramid._pyRightTop) &&
                _pyLeftBottom.equals(pyramid._pyLeftBottom) &&
                _pyRightBottom.equals(pyramid._pyRightBottom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_axisRay, _widthX, _widthY, _height, _topPyramid, _pyLeftTop, _pyRightTop, _pyLeftBottom, _pyRightBottom);
    }
}