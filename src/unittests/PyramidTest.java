package unittests;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.*;
import primitives.*;
import org.junit.Test;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

public class PyramidTest {

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
                new Pyramid(new Color(java.awt.Color.blue),new Material(0.5,0.5,60),
                        new Ray(new Point3D(-50,100,500), new Vector(0,-5,0)), 70,30,45)
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


        render.setSoftShadowActive(true).setSoftShadowSizeRays(20);

        render.renderImage();
        render.writeToImage();
    }

}