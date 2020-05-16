package renderer;

import geometries.Intersectable.GeoPoint;

import elements.*;
import geometries.*;
import primitives.*;

import scene.Scene;


import java.util.List;

import static primitives.Util.alignZero;

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
     * Method that gets list of points and return the closest point to the camera
     * @param intersectionPoints List<GeoPoint> list of intersections points
     * @return GeoPoint the closest point to the camera
     */
    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {
        GeoPoint result = null;
        double mindist = Double.MAX_VALUE;

        Point3D p0 = this._scene.getCamera().getP0();

        for (GeoPoint geo : intersectionPoints) {
            Point3D pt = geo.getPoint();
            double distance = p0.distance(pt);
            if (distance < mindist) {
                mindist = distance;
                result = geo;
            }
        }
        return result;
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


    /**
     * Calculating the color by a Point
     * @param p Point3D the point that in the calculation
     * @return Color new color after calculation
     */
    public Color calcColor(GeoPoint p){
        //return _scene.getAmbientLight().getIntensity().add(p.getGeometry().getEmission());
        Color result = _scene.getAmbientLight().getIntensity();
        result = result.add(p.getGeometry().getEmission());
        List<LightSource> lights = _scene.getLights();

        Vector v = p.getPoint().subtract(_scene.getCamera().getP0()).normalize();
        Vector n = p.getGeometry().getNormal(p.getPoint());

        Material material = p.getGeometry().getMaterial();
        int nShininess = material.getnShininess();
        double kd = material.getkD();
        double ks = material.getkS();
        if (_scene.getLights() != null) {
            for (LightSource lightSource : lights) {

                Vector l = lightSource.getL(p.getPoint());
                double nl = alignZero(n.dotProduct(l));
                double nv = alignZero(n.dotProduct(v));

                if (sign(nl) == sign(nv)) {
                    Color ip = lightSource.getIntensity(p.getPoint());
                    result = result.add(
                            calcDiffusive(kd, nl, ip),
                            calcSpecular(ks, l, n, nl, v, nShininess, ip)
                    );
                }
            }
        }

        return result;
    }

    private boolean sign(double val) {
        return (val > 0d);
    }

    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component coef
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param nl         dot-product n*l
     * @param v          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point
     * @author Dan Zilberstein
     * <p>
     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface.
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color ip) {
        double p = nShininess;

        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        return ip.scale(ks * Math.pow(minusVR, p));
    }

    /**
     * Calculate Diffusive component of light reflection.
     *
     * @param kd diffusive component coef
     * @param nl dot-product n*l
     * @param ip light intensity at the point
     * @return diffusive component of light reflection
     * @author Dan Zilberstein
     * <p>
     * The diffuse component is that dot product n•L that we discussed in class. It approximates light, originally
     * from light source L, reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossy
     * surface is paper. In general, you'll also want this to have a non-gray color value, so this term would in general
     * be a color defined as: [rd,gd,bd](n•L)
     */
    private Color calcDiffusive(double kd, double nl, Color ip) {
        if (nl < 0) nl = -nl;
        return ip.scale(nl * kd);
    }




}
