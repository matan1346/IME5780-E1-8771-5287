package unittests;

import elements.*;
import geometries.*;
import geometries.Intersectable.*;
import primitives.*;
import org.junit.Test;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.util.List;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

public class PyramidTest {

    @Test
    public void  PyramidIntersectionTest()
    {
        Ray baseRay = new Ray(new Point3D(0,0,0), new Vector(0,-1,0));

        Pyramid pyramid = new Pyramid(
                baseRay,
                20, 40, 50);


        //FROM BAST TO TOP OF PYRAMID - NO INTERSECTIONS
        assertEquals("ERROR MUST BE NO INTERSECTIONS - BASE TO TOP", null, pyramid.findIntersections(baseRay));


        //FROM BASE TO NEAR TOP - 1 INTERSECTION
        Ray ray2 = new Ray(new Point3D(1,0,0), new Vector(0,-1,0));

        List<GeoPoint> intersections = pyramid.findIntersections(ray2);


        assertEquals("ERROR MUST BE 1 INTERSECTION - from base to near top", 1, intersections.size());

        //FROM UNDER BASE TO TOP - 1 INTERSECTION
        Ray ray3 = new Ray(new Point3D(0,25,0), new Vector(0,-1,0));

        List<GeoPoint> intersections3 = pyramid.findIntersections(ray3);


        assertEquals("ERROR MUST BE 1 INTERSECTION - from UNDER base to near top", 1, intersections3.size());


        //FROM UNDER BASE TO NEAR TOP - 2 INTERSECTIONS
        Ray ray4 = new Ray(new Point3D(1,25,0), new Vector(0,-1,0));
        List<GeoPoint> intersections4 = pyramid.findIntersections(ray4);


        assertEquals("ERROR MUST BE 2 INTERSECTION", 2, intersections4.size());


        //FROM WALL TO WALL - 2 INTERSECTIONS
        Ray ray5 = new Ray(new Point3D(35,-5,5), new Vector(-40,-5,0));
        List<GeoPoint> intersections5 = pyramid.findIntersections(ray5);

        assertEquals("ERROR MUST BE 2 INTERSECTION - from wall to wall", 2, intersections5.size());

    }

    @Test
    public void PyramidTest()
    {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, -30, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(new Color(java.awt.Color.BLACK), new Material(0, 0.8, 60, 0,1), //
                        new Point3D(300, -100, 2000),
                        new Point3D(-300, -100, 2000),
                        new Point3D(300, 100, 2000)),
                new Triangle(new Color(java.awt.Color.BLACK), new Material(0, 0.8, 60, 0,1), //
                        new Point3D(-300, 100, 2000),
                        new Point3D(-300, -100, 2000),
                        new Point3D(300, 100, 2000)),

                /*FLOOR*/
                //DARK GRAY TRIANGLE - RIGHT
                new Triangle(new Color(java.awt.Color.DARK_GRAY), new Material(0.5, 0.8, 60, 0,0), //
                        new Point3D(-100,100, 0),
                        new Point3D(300, 100, 2000),
                        new Point3D(100, 100, 0)),
                //DARK GRAY TRIANGLE - LEFT
                new Triangle(new Color(java.awt.Color.DARK_GRAY), new Material(0.5, 0.8, 60, 0,0), //
                        new Point3D(-100,100, 0),
                        new Point3D(300, 100, 2000),
                        new Point3D(-300, 100, 2000)),
                new Pyramid(new Color(java.awt.Color.blue),new Material(0.5,0.5,60, 1, 0),
                        new Ray(new Point3D(0,100,500), new Vector(0,-1,0)), 70,30,45),
                new Pyramid(new Color(java.awt.Color.RED),new Material(0.5,0.5,60),
                        new Ray(new Point3D(35,95,510), new Vector(-40,-10,0)),
                        70, 30, 45)
        );


        scene.addLights(
                //WHITE LIGHT (NEAR THE DIAMOND)
                new SpotLight(new Color(java.awt.Color.WHITE),new Point3D(20, -100, 1000),
                        1, 4E-4, 2E-5,new Vector(-1,1,8)),
                //PINK LIGHT - LEFT
                new SpotLight(new Color(700, 400, 400),new Point3D(-100, 50, 1000),
                        1, 4E-4, 2E-5,new Vector(0,0,-1)), //
                //WHITE LIGHT - RIGHT
                new PointLight(new Color(java.awt.Color.WHITE),new Point3D(100, 50, 1000),1, 4E-4, 2E-5)
        );

        ImageWriter imageWriter = new ImageWriter("Pyramid", 200, 200, 1200, 1200);
        Render render = new Render(imageWriter, scene);


        render.setSoftShadowActive(false).setSoftShadowSizeRays(20);

        render.renderImage();
        render.writeToImage();
    }

}