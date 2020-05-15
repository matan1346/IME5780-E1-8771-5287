package renderer;

import geometries.Intersectable.GeoPoint;

import elements.*;
import geometries.Intersectable;
import primitives.*;

import scene.Scene;

import java.util.List;

/**
 * Render class that set the scene and the image writer and rendering
 */
public class Render {
    /**
     * ImageWrite object that represents the image
     */
    ImageWriter _imageWriter;

    /**
     * Scene object that represents the scene
     */
    Scene _scene;

    /**
     * Constructor that gets imageWriter and a scene, and sets them
     * @param imageWriter ImageWriter image writer object
     * @param scene Scene scene object
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }

    /**
     * This method is making image with the camera and image, and renders it
     */
    public void renderImage(){
        Camera camera = _scene.getCamera();

        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();


        for (int j = 0;j < nY;j++)
        {
            for (int i = 0;i < nX;i++)
            {
                Ray ray = camera.constructRayThroughPixel(nX, nY, j, i,
                        _scene.getDistance(), _imageWriter.getWidth(), _imageWriter.getHeight());

                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                if(intersectionPoints == null)
                    _imageWriter.writePixel(j,i,background);
                else
                {
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(j,i,calcColor(closestPoint).getColor());
                }
            }
        }

    }

    /**
     * Calculating the color by a Point
     * @param p Point3D the point that in the calculation
     * @return Color new color after calculation
     */
    public Color calcColor(GeoPoint p){
        return _scene.getAmbientLight().getIntensity().add(p.geometry.getEmission());
    }

    /**
     * Method that gets list of points and return the closest point to the camera
     * @param points List<Point3D> list of points
     * @return Point3D the closest point to the camera
     */
    private GeoPoint getClosestPoint(List<GeoPoint> points)
    {
        double minDistance = Double.MAX_VALUE;
        double distance;
        GeoPoint minGeoPoint = null;
        Camera camera = _scene.getCamera();

        for (GeoPoint p: points) {
            distance = camera.getP0().distance(p.point);
            if(distance < minDistance)
            {
                minDistance = distance;
                minGeoPoint = new GeoPoint(p.geometry, p.point);
            }
        }
        return minGeoPoint;
    }

    /**
     * Printing the grid with a fixed interval between lines
     *
     * @param interval The interval between the lines.
     */
    public void printGrid(int interval, java.awt.Color color) {
        double rows = this._imageWriter.getNx();
        double columns = _imageWriter.getNy();
        //Writing the lines.
        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                if (col % interval == 0 || row % interval == 0) {
                    _imageWriter.writePixel(row, col, color);
                }
            }
        }
    }

    /**
     * This method is calling the writerToImage method
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}
