package unittests;

import elements.*;
import geometries.*;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import static org.junit.Assert.*;

public class MiniProject1Test {

    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere producing a shading
     */
    @Test
    public void trianglesSphere() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                /*new Triangle(new Color(java.awt.Color.RED), new Material(0, 0.8, 60), //
                        new Point3D(-70, 70, -200),
                        new Point3D(150, 150, 50),
                        new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0, 0.8, 60), //
                        new Point3D(-70, 70, -200),
                        new Point3D(-70, -70, -200),
                        new Point3D(75, -75, 150)) ,*/
                new Triangle(new Color(java.awt.Color.RED), new Material(0, 0.8, 60), //
                                new Point3D(50, -50, 200),
                        new Point3D(50, 50, 50),
                        new Point3D(-50, 50, -50)),
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0, 0.8, 60), //
                        new Point3D(50, -50, 200),
                        new Point3D(-100, 50, 500),
                        new Point3D(-50, 50, -50))//

                //
                /*new Plane(new Color(java.awt.Color.CYAN), new Material(0,0.5,60), new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)),*/
                /*new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), // )
                        new Point3D(0, 0, 115),30),
                new Tube(new Color(java.awt.Color.RED),
                        new Material(0.5,0.8, 45),
                        5, new Ray(new Point3D(3,4,1), new Vector(1,1,1)))*/);

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(40, -40, -115), 1, 4E-4, 2E-5,new Vector(-1, 1, 4)));

        ImageWriter imageWriter = new ImageWriter("MiniProject1.2", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere producing a shading
     */
    @Test
    public void WallsTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                /**CENTER WALL**/
                new Triangle(new Color(java.awt.Color.BLACK), new Material(0, 0.8, 60, 0,1), //
                        new Point3D(300, -100, 2000),
                        new Point3D(-300, -100, 2000),
                        new Point3D(300, 100, 2000)),
                new Triangle(new Color(java.awt.Color.BLACK), new Material(0, 0.8, 60, 0,1), //
                        new Point3D(-300, 100, 2000),
                        new Point3D(-300, -100, 2000),
                        new Point3D(300, 100, 2000)),

                /**FLOOR**/
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
                new Tube(new Color(java.awt.Color.GREEN), new Material(0,0.8,60, 0, 1),
                         1,new Ray(new Point3D(80,80,700), new Vector(-27,-20,50))),
                //BLUE TUBE (INTERSECTS A YELLOW CUBE)
                new Tube(new Color(java.awt.Color.BLUE), new Material(0,0.8,60, 0.6, 1),
                         3,new Ray(new Point3D(-75,85,700), new Vector(10,-1,-70))),
                //ORANGE CUBE (SLICED BY BLUE TUBE)
                new Cube(new Color(java.awt.Color.ORANGE),
                        new Material(0, 0.8, 60,0,1),
                        new Point3D(-70,80,600), new Point3D(-60,90,750)),
                //WHITE TUBE (INSIDE THE TRANSPARENT RED SPHERE)
                new Cube(new Color(java.awt.Color.WHITE),
                        new Material(0, 0.8, 60,0,1),
                        new Point3D(-105,85,700), new Point3D(-95,90,720)),
                //RED SPHERE - RIGHT
                new Sphere(new Color(java.awt.Color.RED), new Material(0, 0.8, 30,0,1), // )
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

                new Pyramid(new Color(210,105,30),new Material(0.5,0.5,60),
                        new Ray(new Point3D(-60,100,300), new Vector(0,-5,0)), 70,10,20),
                new Pyramid(new Color(0,128,128),new Material(0.5,0.5,60),
                        new Ray(new Point3D(100,100,300), new Vector(0,-5,0)), 70,30,20)
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

        ImageWriter imageWriter = new ImageWriter("MiniProject2_Threads_Walls2", 200, 200, 1200, 1200);
        Render render = new Render(imageWriter, scene);


        scene.getCamera().setSuperSamplingActive(true).setSuperSamplingSizeRays(50);
        render.setSoftShadowActive(true).setSoftShadowRadius(1).setSoftShadowSizeRays(50).setMultithreading(3) //
                .setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void trianglesSphere3() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(

//                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30,0,0), // )
//                        new Point3D(0, 0, 0),10),

                new Triangle(new Color(java.awt.Color.BLUE), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(0, 0, 0),
                        new Point3D(20, 0, 0),
                        new Point3D(0, 0, 30)),
                new Triangle(new Color(java.awt.Color.RED), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(20, 0, 30),
                        new Point3D(20, 0, 0),
                        new Point3D(0, 0, 30)),
                /*   new Polygon(new Color(java.awt.Color.BLUE),new Material(0,0.5,60),
                        new Point3D(0, 0, 0),
                        new Point3D(20, 0, 0),
                        new Point3D(20, 0, 30),
                        new Point3D(0, 0, 30)),חזית*/
                new Triangle(new Color(java.awt.Color.GREEN), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(0, 0, 30),
                        new Point3D(20, 0, 30),
                        new Point3D(0, 40, 30)),
                new Triangle(new Color(java.awt.Color.GREEN), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(20, 40, 30),
                        new Point3D(20, 0, 30),
                        new Point3D(0, 40, 30)),
                /*new Polygon(new Color(java.awt.Color.BLUE),new Material(0,0.5,60),
                        new Point3D(2, 0, 3),
                        new Point3D(0, 4, 3),
                        new Point3D(2, 4, 3),
                        new Point3D(0, 0, 3)) עליון*/
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(0, 0, 30),
                        new Point3D(0, 0, 0),
                        new Point3D(0, 40, 30)),
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(0, 40, 0),
                        new Point3D(0, 0, 0),
                        new Point3D(0, 40, 30)),
                /*new Polygon(new Color(java.awt.Color.BLUE),new Material(0,0.5,60),
                        new Point3D(2, 0, 0),
                        new Point3D(2, 4, 3),
                        new Point3D(2, 0, 3),
                        new Point3D(2, 4, 0)),צד שמאל*/

                new Triangle(new Color(java.awt.Color.BLUE), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(20, 40, 30),
                        new Point3D(20, 0, 0),
                        new Point3D(20, 0, 30)),
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(20, 40, 30),
                        new Point3D(20, 0, 0),
                        new Point3D(20, 40, 0)),
                /*new Polygon(new Color(java.awt.Color.BLUE),new Material(0,0.5,60),
                        new Point3D(0, 4, 0),
                        new Point3D(2, 4, 3),
                        new Point3D(0, 4, 3),
                        new Point3D(2, 4, 0))צד ימין */

                new Triangle(new Color(java.awt.Color.WHITE), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(20, 40, 0),
                        new Point3D(20, 0, 0),
                        new Point3D(0, 40, 0)),
                new Triangle(new Color(java.awt.Color.DARK_GRAY), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(0, 0, 0),
                        new Point3D(20, 0, 0),
                        new Point3D(0, 40, 0)),
                /*new Polygon(new Color(java.awt.Color.BLUE),new Material(0,0.5,60),
                        new Point3D(0, 0, 0),
                        new Point3D(2, 4, 0),
                        new Point3D(0, 4, 0),
                        new Point3D(2, 0, 0)), ריצפה*/

                new Triangle(new Color(java.awt.Color.RED), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(20, 40, 0),
                        new Point3D(20, 40, 30),
                        new Point3D(0, 40, 30)),
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(20, 40, 0),
                        new Point3D(0, 40, 0),
                        new Point3D(0, 40, 30))
                /*new Polygon(new Color(java.awt.Color.BLUE),new Material(0,0.5,60),
                        new Point3D(0, 0, 3),
                        new Point3D(2, 4, 3),
                        new Point3D(0, 4, 3),
                        new Point3D(2, 0, 3))קיר אחורי */

//                        new Point3D(2, 4, 0),
//                        new Point3D(0, 4, 0),
//                        new Point3D(0, 0, 3),
//                        new Point3D(2, 0, 3),
//                        new Point3D(2, 4, 3),
//                        new Point3D(0, 4, 3)
//                        )
        );
        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                        new Point3D(40, -40, -900), 1, 4E-4, 2E-5,new Vector(-1, 1, 4)),
                new SpotLight(new Color(700, 400, 400), //
                        new Point3D(40, -100, -800), 1, 4E-4, 2E-5,new Vector(1, 1, -4)),
                new PointLight(new Color(java.awt.Color.WHITE),new Point3D(50, -100, -200),1, 4E-4, 2E-5),
                new PointLight(new Color(java.awt.Color.WHITE),new Point3D(70, 80, -200),1, 4E-4, 2E-5));

        ImageWriter imageWriter = new ImageWriter("MiniProject1Box", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void xYz() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Cylinder(new Color(java.awt.Color.RED),new Material(0,0.8,60, 0, 1),
                        new Ray(new Point3D(1, 1, 1), new Vector(2, 0, 0)),0.5,0),
                new Cylinder(new Color(java.awt.Color.BLUE),new Material(0,0.8,60, 0, 1),
                        new Ray(new Point3D(1, 1, 1), new Vector(0,2,0)),0.5,0),
                new Cylinder(new Color(java.awt.Color.GREEN),new Material(0,0.8,60, 0, 1),
                        new Ray(new Point3D(1, 1, 1), new Vector(0,0,2)),0.5,0)
        );

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                        new Point3D(40, -40, -900), 1, 4E-4, 2E-5,new Vector(-1, 1, 4)),
                new SpotLight(new Color(700, 400, 400), //
                        new Point3D(40, -100, -800), 1, 4E-4, 2E-5,new Vector(1, 1, -4)),
                new PointLight(new Color(java.awt.Color.WHITE),new Point3D(50, -100, -200),1, 4E-4, 2E-5),
                new PointLight(new Color(java.awt.Color.WHITE),new Point3D(70, 80, -700),1, 4E-4, 2E-5));

        ImageWriter imageWriter = new ImageWriter("XYZ", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void trianglesSpherePlane2() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, -80, -1500), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Plane(new Color(java.awt.Color.DARK_GRAY),new Material(0.2, 0.2, 60,0 , 0.7),
                        new Point3D(50, 10, 700),
                        new Point3D(0, 10, 500),
                        new Point3D(10, 10, 700)),
                new Sphere(new Color(java.awt.Color.red), new Material(0.2, 0.5, 30), // )
                        new Point3D(40, -31, 100),40),
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.5, 30), // )
                        new Point3D(20, -21, -100),30),
                new Sphere(new Color(java.awt.Color.YELLOW), new Material(0.2, 0.5, 30), // )
                        new Point3D(0, -11, -300),20)

//
        );

        scene.addLights(
                new SpotLight(new Color(700, 400, 400),
                        new Point3D(70, -90,100), 1, 0.000001, 0.00005,new Vector(-40, 30,-30)),
                new SpotLight(new Color(700, 400, 400),
                        new Point3D(50, -70,-100), 1, 0.000001, 0.00005,new Vector(-40, 30,-30)),
                new SpotLight(new Color(700, 400, 400),
                        new Point3D(30, -50,-300), 1, 0.000001, 0.00005,new Vector(-80, 30,-30))


        );

        ImageWriter imageWriter = new ImageWriter("trianglesSpherePlaneWithShadow3", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);


        render.setSoftShadowSizeRays(50).setSoftShadowRadius(0.05).setSoftShadowActive(true)
        .setMultithreading(1).setDebugPrint();
        render.renderImage();
        render.writeToImage();
    }

}