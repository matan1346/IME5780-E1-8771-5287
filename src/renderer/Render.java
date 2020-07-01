package renderer;

import geometries.Intersectable.GeoPoint;
import elements.*;
import primitives.*;
import scene.Scene;
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
     * Stopping condition of refraction
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * Stopping condition of reflection
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    //Super sampling            - Mini Project 1
    private boolean     SUPER_SAMPLING_ACTIVE = false; // active or not
    private int         SUPER_SAMPLING_SIZE_RAYS = 50; // num of rays

    //Soft shadow               - Mini Project 1
    private boolean     SOFT_SHADOW_ACTIVE = false; // active or not
    private double      SOFT_SHADOW_RADIUS = 0.1; // radius value
    private int         SOFT_SHADOW_SIZE_RAYS = 300; //num of rays

    //Adaptive Super sampling   - Mini Project 2
    private boolean     ADAPTIVE_SUPER_SAMPLING_ACTIVE = false; //active or not
    private int         ADAPTIVE_SUPER_SAMPLING_SIZE_RAYS = 50; // num of rays

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
     * @param scene       Scene scene object
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }

    /**
     * Setter for super sampling system active or not
     * @param active boolean true/false
     * @return Render this object
     */
    public Render setSuperSamplingActive(boolean active) {
        this.SUPER_SAMPLING_ACTIVE = active;
        return this;
    }

    /**
     * Setter for size of rays to generate for the super sampling feature
     * @param size_rays int size of rays to generate
     * @return Render this object
     */
    public Render setSuperSamplingSizeRays(int size_rays) {
        this.SUPER_SAMPLING_SIZE_RAYS = size_rays;
        return this;
    }

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
     * Getter that return if adaptive super sampling is active or not
     * @return boolean active or not
     */

    /**
     * Setter for adaptive super sampling system active or not
     * @param active boolean true/false
     * @return Render this object
     */
    public Render setAdaptiveSuperSamplingActive(boolean active) {
        this.ADAPTIVE_SUPER_SAMPLING_ACTIVE = active;
        return this;
    }

    /**
     * Setter for size of rays to generate for the adaptive super sampling feature
     * @param size_rays int size of rays to generate
     * @return Render this object
     */
    public Render setAdaptiveSuperSamplingSizeRays(int size_rays) {
        this.ADAPTIVE_SUPER_SAMPLING_SIZE_RAYS = size_rays;
        return this;
    }

    /**
     * Find intersections of a ray with the scene geometries and get the
     * intersection point that is closest to the ray head. If there are no
     * intersections, null will be returned.
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
        double mindist = Double.MAX_VALUE; // double max value

        //Point of camera
        Point3D p0 = this._scene.getCamera().getP0();

        //get the Point with the minimum distance to the camera point - closest
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
     * Calculate the average color of the rays
     * @param rays List<Ray> list of rays to calculate the average color
     * @return Color average color
     */
    private Color calcColor(List<Ray> rays)
    {
        int size_rays = rays.size();
        Color tempColor = new Color(0,0,0); // color to add
        for(Ray ray : rays){
            GeoPoint closestPoint = findClosestIntersection(ray);
            //if closest point not exists - than continue with next ray
            if(closestPoint == null )
            {
                size_rays--;
                continue;
            }
            //Add color
            tempColor = tempColor.add(new Color(calcColor(closestPoint, ray).getColor()));
        }
        //if size is lower or equal to 1 - return old color, otherwise return average color
        return (size_rays <= 1 || !SUPER_SAMPLING_ACTIVE) ? tempColor : tempColor.reduce(size_rays);
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

    /**
     * Check if the number has a sign + or not
     * @param val double value number
     * @return boolean true if has + sign, false otherwise
     */
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
     * @param l Vector light source vector
     * @param n Vector normalized vector of the geo point
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
     * return a list of rays that belongs to the shadow
     * @param l Vector from lightsource to the geometry
     * @param n Vector normalized vector of the geo point
     * @param gp the geometry point of the transparency
     * @param lightSource LightSource object
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
     * @param p Point3D point start point of the vector
     * @param ray Ray Ray for the direction of the ray
     * @param n Vector vector normalized vector
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

    /**
     * Calculate the color of adaptive super sampling using slices into 4 quarters
     * @param nX number of pixel in axis x
     * @param nY number of pixel in axis y
     * @param j location of current pixel in the row
     * @param i location of current pixel in the column
     * @param screenDistance the distance between the camera and view plane
     * @param screenWidth the width plane in cm
     * @param screenHeight the height plane in cm
     * @param numOfRays max rays we can create from one pixel
     * @return Color color value
     */
    public Color calcColorAdaptive(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight, int numOfRays) {
        Camera camera = _scene.getCamera();
        Vector Vright = camera.getVright();
        Vector Vup = camera.getVup();
        //start point in camera
        Point3D cameraLoc = camera.getP0();
        int numOfRaysInRowCol = (int)Math.ceil(Math.sqrt(numOfRays));
        //if the max ray == 1, call to constructRayThroughPixel that received one ray, and send this ray to calcolor
        if(numOfRaysInRowCol == 1)
            return calcColor(camera.constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth,screenHeight));
        Point3D Pc;
        //we want point of center pixel in the view plane
        if (screenDistance != 0)
            Pc = cameraLoc.add(camera.getVto().scale(screenDistance));
        else
            Pc = cameraLoc;
        Point3D Pij = Pc;
        //the length of the height for ont pixel
        double Ry = screenHeight/nY;
        //the length of the width for ont pixel
        double Rx = screenWidth/nX;

        double Yi= (i - (nY/2d))*Ry + Ry/2d;
        double Xj= (j - (nX/2d))*Rx + Rx/2d;

        if(Xj != 0) Pij = Pij.add(Vright.scale(Xj)) ;
        if(Yi != 0) Pij = Pij.add(Vup.scale(-Yi));

        double minRy = Ry/numOfRaysInRowCol;
        double minRx = Rx/numOfRaysInRowCol;
        return calcColorAdaptiveRec(Pij, Rx, Ry, minRx, minRy,cameraLoc,Vright, Vup);
    }

    /**
     * Calculate the color of adaptive super sampling using slices into 4 quarters
     * @param nX number of pixel in axis x
     * @param nY number of pixel in axis y
     * @param j location of current pixel in the row
     * @param i location of current pixel in the column
     * @param screenDistance the distance between the camera and view plane
     * @param screenWidth the width plane in cm
     * @param screenHeight the height plane in cm
     * @return the pixel color
     */
    public Color calcColorAdaptive(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight) {
        return calcColorAdaptive(nX, nY, j,i,screenDistance,screenWidth,screenHeight, 80);
    }



    /**
     *
     * @param centerP the point in (j,i), the center point in the pixel
     * @param Width the width of pixel
     * @param Height the height of pixel
     * @param minWidth the min pixel width, when width < min width we need to stop the recursion
     * @param minHeight the min pixel height, when width < min height we need to stop the recursion
     * @param cameraLoc the camera point
     * @param Vright vector v- right
     * @param Vup vector v- up
     * @return the "tat" pixel color
     */
    private Color calcColorAdaptiveRec(Point3D centerP, double Width, double Height, double minWidth, double minHeight, Point3D cameraLoc,Vector Vright,Vector Vup)  {
        //4 point "קצוות" of the pixel quarters
        Point3D corner1 = centerP.add(Vright.scale(Width / 2)).add(Vup.scale(-Height / 2)),//top right  corner
                corner2 = centerP.add(Vright.scale(Width / 2)).add(Vup.scale(Height / 2)), //bottom right corner
                corner3 = centerP.add(Vright.scale(-Width / 2)).add(Vup.scale(-Height / 2)),//top left corner
                corner4 = centerP.add(Vright.scale(-Width / 2)).add(Vup.scale(Height / 2)); //bottom left corner

        //create a ray from the camera point, the direction is the vector from the center of quarter to camera point
        Ray     ray1 = new Ray(cameraLoc, corner1.subtract(cameraLoc)),
                ray2 = new Ray(cameraLoc, corner2.subtract(cameraLoc)),
                ray3 = new Ray(cameraLoc, corner3.subtract(cameraLoc)),
                ray4 = new Ray(cameraLoc, corner4.subtract(cameraLoc));

        //calculate the color of all the ray
        Color color1 = calcColor(ray1),
                color2 = calcColor(ray2),
                color3 = calcColor(ray3),
                color4 = calcColor(ray4);

        //Calculate the average of the color
        Color averageColor = color1.add(color2, color3, color4).reduce(4);

        //checks the Recursion stop conditions, if the recursion is to be stopped we will return an average of the colors
        if(Width <= minWidth || Height <= minHeight)
            return averageColor;

        //Checks if the 4 rays on the edge have a same color. if the answer is true- we will return an average of the colors
        //Why don't we return one of the colors? that is our secret
        if (color1.equals(color2)&& color1.equals(color3)&& color1.equals(color4))
            return averageColor;

        //if the color of rays are not same- we calculate the center quarters point
        Point3D centerP1 = centerP.add(Vright.scale(Width / 4)).add(Vup.scale(-Height / 4)),
                centerP2 = centerP.add(Vright.scale(Width / 4)).add(Vup.scale(Height / 4)),
                centerP3 = centerP.add(Vright.scale(-Width / 4)).add(Vup.scale(-Height / 4)),
                centerP4 = centerP.add(Vright.scale(-Width / 4)).add(Vup.scale(Height / 4));



        //Recursive call 4 all 4 quarters to calculate the average of all of them and return the averaged color
        return calcColorAdaptiveRec(centerP1, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup).add(
                calcColorAdaptiveRec(centerP2, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup),
                calcColorAdaptiveRec(centerP3, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup),
                calcColorAdaptiveRec(centerP4, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup)
        ).reduce(4);
    }

    /**
     * Calculate the color of a ray
     * @param ray Ray to calculate the color from
     * @return Color color value
     */
    private Color calcColor(Ray ray)  {
        GeoPoint gp;
        gp = findClosestIntersection(ray);
        if(gp == null) // if geo point not exists - than return background color
            return this._scene.getBackground();
        else
            return calcColor(gp, ray);
    }


    /**
     * This function return color by selected mode ADAPTIVE SAMPLING/SUPER SAMPLING/ REGULAR MODE
     * @param camera Camera camera object
     * @param nX int num of pixels of width
     * @param nY int num of pixels of height
     * @param col int column value
     * @param row int row value
     * @param distance double distance of camera
     * @param width double width of view plane
     * @param height double height of view plane
     * @return Color the color value withing the targeted mode
     */
    private Color getColorByMode(Camera camera,int nX, int nY, int col, int row, double distance, double width, double height)
    {
        //Calculate color value with adaptive super sampling system
        if(ADAPTIVE_SUPER_SAMPLING_ACTIVE)
        {
            return calcColorAdaptive(nX, nY,col, row,
                    distance, width, height);
        }
        else//Calculate color value with super sampling or regular system
        {
            List<Ray> rays = camera.constructRaysThroughPixel(nX, nY, col, row, distance, width, height,
                    SUPER_SAMPLING_ACTIVE ? SUPER_SAMPLING_SIZE_RAYS : 0);
            return calcColor(rays);
        }
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
                     _imageWriter.writePixel(pixel.col, pixel.row,
                            getColorByMode(camera, nX, nY,pixel.col, pixel.row,
                                    dist, width, height).getColor()
                                    );
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
