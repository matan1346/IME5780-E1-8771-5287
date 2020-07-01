package unittests;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.*;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import static org.junit.Assert.*;

public class MiniProject2Test {


    @Test
    public void WallsTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                /**CENTER WALL**/
                /*new Triangle(new Color(java.awt.Color.BLACK), new Material(0, 0.8, 60, 0,1), //
                        new Point3D(300, -100, 2000),
                        new Point3D(-300, -100, 2000),
                        new Point3D(300, 100, 2000)),
                new Triangle(new Color(java.awt.Color.BLACK), new Material(0, 0.8, 60, 0,1), //
                        new Point3D(-300, 100, 2000),
                        new Point3D(-300, -100, 2000),
                        new Point3D(300, 100, 2000)),
*/
                /**FLOOR**/
                new Plane(new Color(java.awt.Color.DARK_GRAY),new Material(0.2, 0.2, 60,0 , 0.7),
                        new Point3D(50, 10, 700),
                        new Point3D(0, 10, 500),
                        new Point3D(10, 10, 700)),

                /**Diamond**/
                /*new Triangle(new Color(java.awt.Color.RED), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(-40,70, 900),
                        new Point3D(-20, 100, 1200/),
                        new Point3D(0, 70, 1200)),
                new Triangle(new Color(java.awt.Color.GREEN), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(-10,75, 1500),
                        new Point3D(-20, 100, 1200),
                        new Point3D(0, 70, 1200)),
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(-10,75, 1500),
                        new Point3D(-20, 100, 1200),
                        new Point3D(-60, 85, 1500)),
                new Triangle(new Color(java.awt.Color.CYAN), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(-20, 100, 1200),
                        new Point3D(-40,70, 900),
                        new Point3D(-60, 85, 1500)),*/
                /**End Diamond**/


                //GREEN TUBE (INTERSECTS A BLUE BALL)
                /*new Tube(new Color(java.awt.Color.GREEN), new Material(0,0.8,60, 0, 1),
                        1,new Ray(new Point3D(80,80,700), new Vector(-27,-20,50))),
                //BLUE TUBE (INTERSECTS A YELLOW CUBE)
                new Tube(new Color(java.awt.Color.BLUE), new Material(0,0.8,60, 0.6, 1),
                        3,new Ray(new Point3D(-75,85,700), new Vector(10,-1,-70))),*/
                //ORANGE CUBE (SLICED BY BLUE TUBE)
                new Cube(new Color(java.awt.Color.ORANGE),
                        new Material(0, 0.8, 60,0,0),
                        new Point3D(-70,-10,150), new Point3D(0,10,200))
                //WHITE TUBE (INSIDE THE TRANSPARENT RED SPHERE)
                /*new Cube(new Color(java.awt.Color.WHITE),
                        new Material(0, 0.8, 60,0,1),
                        new Point3D(-105,85,700), new Point3D(-95,90,720)),*/
                //RED SPHERE - RIGHT
                /*new Sphere(new Color(java.awt.Color.RED), new Material(0, 0.8, 30,0,1), // )
                        new Point3D(70, 80, 800),15),
                //RED SPHERE - LEFT (ORANGE BOX INSIDE)
                new Sphere(new Color(java.awt.Color.RED), new Material(1, 0.8, 30,0.8,0), // )
                        new Point3D(-100, 80, 700),15),
                //BLUE SPHERE (SLICED BY GREEN TUBE)
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0, 0.8, 30,0,1), // )
                        new Point3D(80, 80, 700),5),
                //GREEN TUBE - RIGHT
                new Sphere(new Color(java.awt.Color.GREEN), new Material(0, 0.8, 30,0,1), // )
                        new Point3D(140, 60, 1000),35),
*/
                /*new Pyramid(new Color(210,105,30),new Material(0.5,0.5,60),
                        new Ray(new Point3D(-35,100,725), new Vector(0,-5,0)), 70,250,20)
                /*new Pyramid(new Color(0,128,128),new Material(0.5,0.5,60),
                        new Ray(new Point3D(100,100,300), new Vector(0,-5,0)), 70,30,20)*/
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

        ImageWriter imageWriter = new ImageWriter("MiniProject2", 200, 200, 1200, 1200);
        Render render = new Render(imageWriter, scene);


        render.setSuperSamplingActive(false).setSuperSamplingSizeRays(50)
                .setSoftShadowActive(false).setSoftShadowRadius(1).setSoftShadowSizeRays(50).setMultithreading(3) //
                .setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }

}