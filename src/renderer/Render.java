package renderer;

import geometries.Intersectable.GeoPoint;

import elements.*;
import geometries.*;
import primitives.*;

import scene.Scene;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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



    //Soft shadow
    private boolean     SOFT_SHADOW_ACTIVE = false;
    private double      SOFT_SHADOW_RADIUS = 0.1;
    private int         SOFT_SHADOW_SIZE_RAYS = 300;


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


    /*private Color calcColorWithRays(Camera camera, int nX, int nY, int i, int j, double screenDistance, double screenWidth, double screenHeight,
                                    java.awt.Color background){
        int count_rays = SUPER_SAMPLING_SIZE_RAYS;//numbers of rays per pixel צריך להוסיףף אותו לזימון של הפונקציה כדי שיוכלו לשנות מייד דרך הטסט
        Color tempColor = Color.BLACK;
        List<Ray> rays = camera.constructRaysThroughPixel(nX, nY, j, i,
                screenDistance, screenWidth, screenHeight);

        for(Ray ray : rays){
            GeoPoint closestPoint = findClosestIntersection(ray);
            if(closestPoint == null )
            {
                _imageWriter.writePixel(j, i, background);
                count_rays--;
                continue;
            }
            tempColor = tempColor.add(new Color(calcColor(closestPoint, ray).getColor()));
        }
        return (count_rays <= 1 || !SUPER_SAMPLING_ACTIVE) ? tempColor : tempColor.reduce(count_rays);
    }*/



    /**
     * This method is making image with the camera and image, and renders it
     */
    /*public void renderImage() {
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
                _imageWriter.writePixel(j, i, calcColorWithRays(camera, nX,  nY,  i,  j,  screenDistance,  screenWidth, screenHeight, background).getColor());
            }
        }
    }*/



    /**
     * Setter for soft shadow system - active or not
     * @param active boolean true/false
     * @return Render this object
     */
    public Render setSoftShadowActive(boolean active) {
        this.SOFT_SHADOW_ACTIVE = active;
        return this;
    }

    /**
     * Setter for soft shadow system - radius value
     * @param radius double value of radius
     * @return Render this object
     */
    public Render setSoftShadowRadius(double radius) {
        this.SOFT_SHADOW_RADIUS = radius;
        return this;
    }

    /**
     * Setter for size of rays to generate for the soft shadow feature
     * @param size_rays int size of rays to generate
     * @return Render this object
     */
    public Render setSoftShadowSizeRays(int size_rays) {
        this.SOFT_SHADOW_SIZE_RAYS = size_rays;
        return this;
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


    private Color calcColor(List<Ray> rays)
    {
        int size_rays = rays.size();
        Color tempColor = new Color(0,0,0);
        for(Ray ray : rays){
            GeoPoint closestPoint = findClosestIntersection(ray);
            if(closestPoint == null )
            {
                size_rays--;
                continue;
            }
            tempColor = tempColor.add(new Color(calcColor(closestPoint, ray).getColor()));
        }
        return (size_rays <= 1 || !_scene.getCamera().getSuperSamplingActive()) ? tempColor : tempColor.reduce(size_rays);
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
     * Calculate the shadow of the geometry by list of rays
     * @param l Vector
     * @param n Vector
     * @param gp GeoPoint the geometry point of the transparency
     * @param lightSource LightSource object
     * @return
     */
    private double transparency(Vector l, Vector n, GeoPoint gp, LightSource lightSource) {
        double ktr = 0.0;
        List<Ray> rayList = transparencyGetListOfRay(l,n,gp,lightSource);
        for (Ray r : rayList) {
            ktr += transparencyRay(r,gp,lightSource);
        }
        return ktr / rayList.size();
    }

    /**
     * Calculate the shadow of the geometry by single ray
     * @param ray Ray object
     * @param gp GeoPoint the geometry point of the transparency
     * @param lightSource LightSource object
     * @return
     */
    private double transparencyRay(Ray ray, GeoPoint gp, LightSource lightSource) {
        double ktr = 1.0;
        Point3D point = gp.getPoint();// get one for fast performance
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (intersections == null) return ktr;
        double lightDistance = lightSource.getDistance(point);

        for (GeoPoint geoP : intersections) {
            if (alignZero(geoP.getPoint().distance(point) - lightDistance) <= 0) {
                ktr *= geoP.getGeometry().getMaterial().getKT();
                if (ktr < MIN_CALC_COLOR_K) return  0.0;
            }
        }
        return ktr;
    }

    /**
     *
     * @param l
     * @param n
     * @param gp
     * @param lightSource
     * @return
     */
    private List<Ray> transparencyGetListOfRay(Vector l, Vector n, GeoPoint gp, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Point3D point = gp.getPoint();// get one for fast performance
        Ray lightRay = new Ray(point, lightDirection, n);
        List<Ray> list = new LinkedList(Collections.singletonList(lightRay));

        if (SOFT_SHADOW_ACTIVE && SOFT_SHADOW_SIZE_RAYS > 0 && !isZero(SOFT_SHADOW_RADIUS)) {
            int counter = SOFT_SHADOW_SIZE_RAYS;
            Vector v = lightRay.get_dir();
            Point3D p0 = lightRay.get_p();
            double x0 = p0.getX().get();
            double y0 = p0.getY().get();
            double z0 = p0.getZ().get();
            Vector vX;
            if (x0 <= y0 && x0 <= z0) {
                vX = (new Vector(0.0D, -z0, y0)).normalize();
            } else if (y0 <= x0 && y0 <= z0) {
                vX = (new Vector(z0, 0.0D, -x0)).normalize();
            } else if (y0 == 0.0D && x0 == 0.0D) {
                vX = (new Vector(1.0D, 1.0D, 0.0D)).normalize();
            } else {
                vX = (new Vector(y0, -x0, 0.0D)).normalize();
            }
            Vector vY = v.crossProduct(vX).normalize();
            Point3D pc = p0.add(v);
            while (counter > 0) {
                double xFactor = ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D);
                double yFactor = Math.sqrt(1.0D - xFactor * xFactor);
                double scale;
                for (scale = 0.0D; isZero(scale); scale = ThreadLocalRandom.current().nextDouble(-SOFT_SHADOW_RADIUS, SOFT_SHADOW_RADIUS));
                xFactor = scale * xFactor;
                yFactor = scale * yFactor;
                Point3D p = pc;
                if (!isZero(xFactor)) {
                    p = p.add(vX.scale(xFactor));
                }

                if (!isZero(yFactor)) {
                    p = p.add(vY.scale(yFactor));
                }
                Vector v1 = (p.subtract(p0)).normalized();
                if (v.dotProduct(n) * v1.dotProduct(n) > 0.0D) {
                    --counter;
                    list.add(new Ray(p0, v1));
                }
            }
        }
        return list;
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


    /**
     * IMPROVEMENTS
     */
    private int _threads = 1;
    private final int SPARE_THREADS = 2;
    private boolean _print = false;

    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     * @author Dan
     *
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         *  Default constructor for secondary Pixel objects
         */
        public Pixel() {}

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) System.out.printf("\r %02d%%", percents);
            if (percents >= 0)
                return true;
            if (Render.this._print) System.out.printf("\r %02d%%", 100);
            return false;
        }
    }


    private Color calcColorAdaptiveRays(List<Ray> rays)
    {
        Color tempColor;
        Color oldColor = new Color(0,0,0);
        boolean savedColor = false;
        for(Ray ray : rays){
            GeoPoint closestPoint = findClosestIntersection(ray);
            if(closestPoint == null )
                continue;

            tempColor = new Color(calcColor(closestPoint, ray).getColor());
            if(savedColor && !tempColor.equals(oldColor))
                return null;
            else
                oldColor = new Color(tempColor);
            savedColor = true;
        }
        return oldColor;
    }

    private Color calcColorAdaptive(Camera camera,int nX,int nY,int col,int row,double distance,
                                   double width, double height, int recursion)
    {
        List<Ray> rays = camera.constructRaysThroughPixel(nX, nY, col, row, distance, width, height);
        Color color = calcColorAdaptiveRays(rays);

        if(color == null && recursion > 0) {
            double Rx = width / nX;
            double Ry = height / nY;
            int numPixel = 2;
            recursion--;


            int count = 0;
            Color tempColor = new Color(0,0,0);
            Color currColor;
            for(int i =0;i < numPixel;i++ )
                for(int j = 0;j < numPixel;j++)
                {
                    currColor = calcColorAdaptive(camera, numPixel, numPixel, j, i,
                            distance, Rx, Ry, recursion);
                    if(currColor != null)
                    {
                        tempColor = tempColor.add(currColor);
                        count++;
                    }
                }

            if(count > 0)
                System.out.println("How much? - " + count);


            color = (count > 0) ? tempColor.reduce(count) : new Color(_scene.getBackground().getColor());
        }
        return color;
    }

    public Color calcColorAdaptive(Camera camera,int nX,int nY,int col,int row,double distance,
        double width, double height)
    {

        return calcColorAdaptive(camera,nX, nY,col, row, distance, width, height, 1);
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final double dist = _scene.getDistance();
        final double width = _imageWriter.getWidth();
        final double height = _imageWriter.getHeight();
        final Camera camera = _scene.getCamera();

        final Pixel thePixel = new Pixel(nY, nX);

        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    //List<Ray> rays = camera.constructRaysThroughPixel(nX, nY, pixel.col, pixel.row, dist, width, height);
                    _imageWriter.writePixel(pixel.col, pixel.row,
                            calcColorAdaptive(camera,nX, nY,pixel.col, pixel.row,
                                    dist, width, height)
                                    .getColor());
                }
            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
        if (_print) System.out.printf("\r100%%\n");
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }

}
