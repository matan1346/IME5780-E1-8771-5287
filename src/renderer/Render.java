package renderer;

import elements.*;
import geometries.Intersectable;
import primitives.*;

import scene.Scene;

import java.util.List;

public class Render {
    ImageWriter _imageWriter;
    Scene _scene;

    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }

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

                List<Point3D> intersectionPoints = geometries.findIntersections(ray);
                if(intersectionPoints == null)
                    _imageWriter.writePixel(j,i,background);
                else
                {
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(j,i,calcColor(closestPoint).getColor());
                }
            }
        }

    }

    public Color calcColor(Point3D p){
        return _scene.getAmbientLight().getIntensity();
    }

    public Point3D getClosestPoint(List<Point3D> points)
    {
        double minDistance = Double.MAX_VALUE;
        double distance;
        Point3D minPoint = null;
        Camera camera = _scene.getCamera();

        for (Point3D p: points) {
            distance = camera.getP0().distance(p);
            if(distance < minDistance)
            {
                minDistance = distance;
                minPoint = new Point3D(p);
            }
        }
        return minPoint;
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

    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}
