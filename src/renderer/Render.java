package renderer;

import geometries.Intersectable.GeoPoint;

import elements.*;
import geometries.*;
import primitives.*;

import scene.Scene;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;

/**
 * Render class that set the scene and the image writer and rendering
 */
public class Render {

    /**
     * delta value
     */
    //private static final double DELTA = 0.1;

    /**
     * Stopping condition of shkifut
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * Stopping condition of eshtakfut
     */
    private static final double MIN_CALC_COLOR_K = 0.001;


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
     *
     * @param imageWriter ImageWriter image writer object
     * @param scene       Scene scene object
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }

    /**
     * This method is making image with the camera and image, and renders it
     */
    public void renderImage() {
        double sumColorR, sumColorG, sumColorB;// sum rgb colors to do averages
        double colorR, colorG, colorB; //rgb colors

        Camera camera = _scene.getCamera();
        double screenDistance = _scene.getDistance();
        double screenWidth = _imageWriter.getWidth();
        double screenHeight = _imageWriter.getHeight();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();


        for (int j = 0; j < nY; j++) {
            for (int i = 0; i < nX; i++) {
                sumColorR = sumColorG = sumColorB = 0.0;
                int count_rays = 50;//numbers of rays per pixel

                List<Ray> rays = new ArrayList<>();
                rays.add(camera.constructRayThroughPixel(nX, nY, j, i,
                        screenDistance, screenWidth, screenHeight));


                    for(int k= 0;k < count_rays-1;k++)
                        rays.add(camera.constructRayThroughPixel(nX, nY, j, i,
                                screenDistance, screenWidth, screenHeight, true));

                    Color tempColor;
                    for(Ray ray : rays){
                        GeoPoint closestPoint = findClosestIntersection(ray);
                        if(closestPoint == null)
                        {
                            //_imageWriter.writePixel(j, i, background);
                            count_rays--;
                            continue;
                        }

                        tempColor = new Color(calcColor(closestPoint, ray).getColor());

                        colorR = tempColor.getColor().getRed();
                        colorG = tempColor.getColor().getGreen();
                        colorB = tempColor.getColor().getBlue();

                        sumColorR += colorR;// * colorR;
                        sumColorG += colorG;// * colorG;
                        sumColorB += colorB;// * colorB;


                    }
                    _imageWriter.writePixel(j, i, new Color((sumColorR/count_rays), (sumColorG/count_rays), (sumColorB/count_rays)).getColor());



            }
        }
    }

    /**
     * Find intersections of a ray with the scene geometries and get the
     * intersection point that is closest to the ray head. If there are no
     * intersections, null will be returned.
     *
     * @param ray intersecting the scene
     * @return the closest point
     */
    private GeoPoint findClosestIntersection(Ray ray) {

        if (ray == null) {
            return null;
        }

        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.get_p();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.getPoint());
            if (distance < closestDistance) {
                closestPoint = geoPoint;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }


    /**
     * Method that gets list of points and return the closest point to the camera
     *
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
     *
     * @param gp  Point3D the point that in the calculation
     * @param ray Ray ray
     * @return Color the calculated color
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().getIntensity());
    }


    /**
     * Calculating the color by a Point
     *
     * @param gp    Point3D the point that in the calculation
     * @param inRay Ray ray
     * @param level int the level of the color
     * @param k     double value to scale
     * @return Color the calculated color
     */
    private Color calcColor(GeoPoint gp, Ray inRay, int level, double k) {

        if (level == 1 || k < MIN_CALC_COLOR_K) return Color.BLACK;

        Color result = gp.getGeometry().getEmission();//_scene.getAmbientLight().getIntensity();
        List<LightSource> lights = _scene.getLights();

        Vector v = gp.getPoint().subtract(_scene.getCamera().getP0()).normalize();
        Vector n = gp.getGeometry().getNormal(gp.getPoint());

        Material material = gp.getGeometry().getMaterial();
        int nShininess = material.getnShininess();
        double kd = material.getKD();
        double ks = material.getKS();
        if (_scene.getLights() != null) {
            for (LightSource lightSource : lights) {

                Vector l = lightSource.getL(gp.getPoint());
                double nl = alignZero(n.dotProduct(l));
                double nv = alignZero(n.dotProduct(v));

                if (sign(nl) == sign(nv)) {
                    double ktr = transparency(l,n,gp,lightSource);
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color ip = lightSource.getIntensity(gp.getPoint()).scale(ktr);
                        result = result.add(
                                calcDiffusive(kd, nl, ip),
                                calcSpecular(ks, l, n, nl, v, nShininess, ip)
                        );
                    }
                }
            }

            double kR = gp.getGeometry().getMaterial().getKR();
            double kkR = k * kR;
            if (kkR > MIN_CALC_COLOR_K) {
                Ray reflectedRay = constructReflectedRay(gp.getPoint(), inRay, n);
                GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
                if (reflectedPoint != null) result = result.add(
                        calcColor(reflectedPoint,
                                reflectedRay,
                                level - 1, kkR).scale(kR));
            }

            double kT = gp.getGeometry().getMaterial().getKT();
            double kkT = k * kT;
            if (kkT > MIN_CALC_COLOR_K) {
                Ray refractedRay = constructRefractedRay(gp.getPoint(), inRay, n);
                GeoPoint refractedPoint = findClosestIntersection(refractedRay);
                if (refractedPoint != null) result = result.add(
                        calcColor(refractedPoint,
                                refractedRay,
                                level - 1, kkT).scale(kT));
            }
        }

        return result;
    }


    /*public Color calcColor(GeoPoint gp) {
        Color result = _scene.getAmbientLight().getIntensity();
        result = result.add(gp.getGeometry().getEmission());
        List<LightSource> lights = _scene.getLights();

        Vector v = gp.getPoint().subtract(_scene.getCamera().getP0()).normalize();
        Vector n = gp.getGeometry().getNormal(gp.getPoint());

        Material material = gp.getGeometry().getMaterial();
        int nShininess = material.getnShininess();
        double kd = material.getkD();
        double ks = material.getkS();
        if (_scene.getLights() != null) {
            for (LightSource lightSource : lights) {

                Vector l = lightSource.getL(gp.getPoint());
                double nl = alignZero(n.dotProduct(l));
                double nv = alignZero(n.dotProduct(v));

                if (sign(nl) == sign(nv)) {
                    if (unshaded(l, n, gp, lightSource)) {
                        Color ip = lightSource.getIntensity(gp.getPoint());
                        result = result.add(
                                calcDiffusive(kd, nl, ip),
                                calcSpecular(ks, l, n, nl, v, nShininess, ip)
                        );
                    }
                }
            }
        }

        return result;
    }*/

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


    /**
     * Check if given vectors with geo point is unshaded
     *
     * @param l  Vector light source vector
     * @param n  Vector normalized vector of the geo point
     * @param gp GeoPoint the geo point
     * @return boolean true if unshaded, else otherwise
     */
    private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.getPoint(), lightDirection, n);

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);

        if(intersections == null) return true;

        double distance = lightSource.getDistance(gp.getPoint());
        for(GeoPoint geoP : intersections){
            if(alignZero(geoP.getPoint().distance(gp.getPoint()) - distance) <= 0 &&
                geoP.getGeometry().getMaterial().getKT() == 0) return false;

        }

        return true;

    }

    /**
     *
     * @param l
     * @param n
     * @param gp
     * @param lightSource
     * @return
     */
    private double transparency(Vector l, Vector n, GeoPoint gp, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Point3D point = gp.getPoint();// get one for fast performance

        Ray lightRay = new Ray(point, lightDirection, n);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) return 1.0;
        double lightDistance = lightSource.getDistance(point);
        double ktr = 1.0;
        for (GeoPoint geoP : intersections) {
            if (alignZero(geoP.getPoint().distance(point) - lightDistance) <= 0) {
                ktr *= geoP.getGeometry().getMaterial().getKT();
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;

    }

    /**
     * this function gets a point, a ray and a vector and return the reflected ray
     *
     * @param p   Point3D point
     * @param ray Ray Ray
     * @param n   Vector vector
     * @return Ray reflected ray
     */
    private Ray constructReflectedRay(Point3D p, Ray ray, Vector n) {

        Vector v = ray.get_dir();
        double vn = v.dotProduct(n);

        if (vn == 0) return null;

        Vector r = n.scale(2 * vn).subtract(v);
        return new Ray(p, r, n);
    }

    /**
     * this function gets a point and a ray and return the refracted ray
     *
     * @param p     Point3D point
     * @param inRay Ray ray
     * @return Ray the refracted ray
     */
    private Ray constructRefractedRay(Point3D p, Ray inRay, Vector n) {
        return new Ray(p, inRay.get_dir(), n);
    }
}
