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
    public void MiniProject2Test() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, -20, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));


        Point3D houseAdding = new Point3D(0,0,0);

        double houseX = houseAdding.getX().get();
        double houseY = houseAdding.getY().get();
        double houseZ = houseAdding.getZ().get();

        Point3D flowerAdding = new Point3D(25,0,-200);

        double flowerX = flowerAdding.getX().get();
        double flowerY = flowerAdding.getY().get();
        double flowerZ = flowerAdding.getZ().get();


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
                /**SKY**/
                new Plane(new Color(102,178,255),new Material(0, 0, 0,0 , 0),
                        new Point3D(50, -10, 700),
                        new Point3D(0, 10, 500),
                        new Point3D(10, -10, 700)),

                /**FLOOR**/
                new Plane(new Color(java.awt.Color.DARK_GRAY),new Material(0.2, 0.2, 60,0 , 0),
                        new Point3D(50, 10, 700),
                        new Point3D(0, 10, 500),
                        new Point3D(10, 10, 700)),



                //SUN
                new Sphere(new Color(java.awt.Color.YELLOW), new Material(0,0.5,30,0,0),
                        new Point3D(20, -100, 400), 10),

                //ROOFS - FRONT
                new Polygon(new Color(153,0,0), new Material(0, 0.8, 60,0,0),
                        new Point3D(-52 + houseX, -9 + houseY, -265 + houseZ),
                        new Point3D(-18 + houseX, -9 + houseY,-265 + houseZ),
                        new Point3D(-18 + houseX, -25 + houseY, -125 + houseZ),
                        new Point3D(-52 + houseX, -25 + houseY, -125 + houseZ)),
                //ROOFS - BACK
                new Polygon(new Color(153,0,0), new Material(0, 0.8, 60,0,0),
                        new Point3D(-52 + houseX, -25 + houseY, -125 + houseZ),
                        new Point3D(-18 + houseX, -25 + houseY,-125 + houseZ),
                        new Point3D(-18 + houseX, -9 + houseY, 15 + houseZ),
                        new Point3D(-52 + houseX, -9 + houseY, 15 + houseZ)),

                //ROOF LEFT SIDE
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0, 0.8, 60,0,0),
                        new Point3D(-50 + houseX, -10 + houseY, -250 + houseZ),
                        new Point3D(-50 + houseX, -25 + houseY, -125 + houseZ),
                        new Point3D(-50 + houseX, -10 + houseY, 0 + houseZ)),

                //ROOF RIGHT SIDE
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0, 0.8, 60,0,0),
                        new Point3D(-20 + houseX, -10 + houseY, -250 + houseZ),
                        new Point3D(-20 + houseX, -25 + houseY, -125 + houseZ),
                        new Point3D(-20 + houseX, -10 + houseY, 0 + houseZ)),



                //ARUBA
                new Cube(new Color(192,192,192),
                        new Material(0, 0, 0,0,0),
                        new Point3D(-45+ houseX,-30 + houseY,-200 + houseZ),
                        new Point3D(-40+ houseX,-20 + houseY,-190 + houseZ)),



                //HOUSE CUBE
                new Cube(new Color(java.awt.Color.WHITE),
                        new Material(0, 0.8, 60,0,0),
                        new Point3D(-50+ houseX,-10 + houseY,-250 + houseZ),
                        new Point3D(-20+ houseX,10 + houseY,0 + houseZ)),

                //HOUSE BOTTOM LINE
                new Cube(new Color(java.awt.Color.GRAY),
                        new Material(0, 0.8, 60,0,0),
                        new Point3D(-50.0001 + houseX,8 + houseY,-250.0001 + houseZ),
                        new Point3D(-19.9999 + houseX,10 + houseY,0.0001 + houseZ)),
                //HOUSE FIRST STAIRS
                new Cube(new Color(java.awt.Color.GRAY),
                        new Material(0, 0.8, 60,0,0),
                        new Point3D(-40 + houseX,8 + houseY,-252 + houseZ),
                        new Point3D(-30 + houseX,10 + houseY,-250 + houseZ)),

                //HOUSE SECOND STAIRS
                new Cube(new Color(java.awt.Color.GRAY),
                        new Material(0, 0.8, 60,0,0),
                        new Point3D(-40 + houseX,9 + houseY,-255 + houseZ),
                        new Point3D(-30 + houseX,10 + houseY,-250 + houseZ)),

                //YADIT DOOR
                new Sphere(new Color(224,224,224),
                        new Material(0, 0.8, 60,0,0),
                        new Point3D(-38 + houseX, 0 + houseY, -250.0001 + houseZ), 0.5),

                //DOOR
                new Polygon(new Color(51,25,0), new Material(0, 0.8, 60,0,0),
                        new Point3D(-39 + houseX, 8 + houseY, -250.0001 + houseZ),
                        new Point3D(-31 + houseX, 8 + houseY,-250.0001 + houseZ),
                        new Point3D(-31 + houseX, -7 + houseY, -250.0001 + houseZ),
                        new Point3D(-39 + houseX, -7 + houseY, -250.0001 + houseZ)),
        //top left -door
        new Polygon(new Color(153,76,0), new Material(0, 0.8, 60,0,0),
                new Point3D(-38 + houseX, -1 + houseY, -250.1001 + houseZ),
                new Point3D(-35.5 + houseX, -1 + houseY,-250.1001 + houseZ),
                new Point3D(-35.5 + houseX, -6 + houseY, -250.1001 + houseZ),
                new Point3D(-38 + houseX, -6 + houseY, -250.1001 + houseZ)),
        //bottom left door
        new Polygon(new Color(153,76,0), new Material(0, 0.8, 60,0,1),
                new Point3D(-38 + houseX, 7 + houseY, -250.1001 + houseZ),
                new Point3D(-35.5 + houseX, 7 + houseY,-250.1001 + houseZ),
                new Point3D(-35.5 + houseX, 0 + houseY, -250.1001 + houseZ),
                new Point3D(-38 + houseX, 0 + houseY, -250.1001 + houseZ)),
                //top right door
                new Polygon(new Color(153,76,0), new Material(0, 0.8, 60,0,0),
                        new Point3D(-35 + houseX, -1 + houseY, -250.1001 + houseZ),
                        new Point3D(-32 + houseX, -1 + houseY,-250.1001 + houseZ),
                        new Point3D(-32 + houseX, -6 + houseY, -250.1001 + houseZ),
                        new Point3D(-35 + houseX, -6 + houseY, -250.1001 + houseZ)),
                //bottom right door
                new Polygon(new Color(153,76,0), new Material(0, 0.8, 60,0,1),
                        new Point3D(-35 + houseX, 7 + houseY, -250.1001 + houseZ),
                        new Point3D(-32 + houseX, 7 + houseY,-250.1001 + houseZ),
                        new Point3D(-32 + houseX, 0 + houseY, -250.1001 + houseZ),
                        new Point3D(-35 + houseX, 0 + houseY, -250.1001 + houseZ)),


                //WINDOWS
        new Polygon(new Color(51,25,0), new Material(0, 0.8, 60,0,0),
                new Point3D(-49 + houseX, 0 + houseY, -250.0001 + houseZ),
                new Point3D(-41 + houseX, 0 + houseY,-250.0001 + houseZ),
                new Point3D(-41 + houseX, -7 + houseY, -250.0001 + houseZ),
                new Point3D(-49 + houseX, -7 + houseY, -250.0001 + houseZ)),
                //LEFT TOP WINDOW
                new Polygon(new Color(192,192,192), new Material(0, 0.8, 60,0.8,0),
                new Point3D(-48.5 + houseX, -0.5 + houseY, -250.0002 + houseZ),
                new Point3D(-45.2 + houseX, -0.5 + houseY,-250.0002 + houseZ),
                new Point3D(-45.2 + houseX, -3.3 + houseY, -250.0002 + houseZ),
                new Point3D(-48.5 + houseX, -3.3 + houseY, -250.0002 + houseZ)),
                //LEFT BOTTOM WINDOW
                new Polygon(new Color(192,192,192), new Material(0, 0.8, 60,0.8,0),
                        new Point3D(-48.5 + houseX, -3.7 + houseY, -250.0002 + houseZ),
                        new Point3D(-45.2 + houseX, -3.7 + houseY,-250.0002 + houseZ),
                        new Point3D(-45.2 + houseX, -6.5 + houseY, -250.0002 + houseZ),
                        new Point3D(-48.5 + houseX, -6.5 + houseY, -250.0002 + houseZ)),
                //right top window
                new Polygon(new Color(192,192,192), new Material(0, 0.8, 60,0.8,0),
                        new Point3D(-44.8 + houseX, -0.5 + houseY, -250.0002 + houseZ),
                        new Point3D(-41.5 + houseX, -0.5 + houseY,-250.0002 + houseZ),
                        new Point3D(-41.5 + houseX, -3.3 + houseY, -250.0002 + houseZ),
                        new Point3D(-44.8 + houseX, -3.3 + houseY, -250.0002 + houseZ)),
                //LEFT BOTTOM WINDOW
                new Polygon(new Color(192,192,192), new Material(0, 0.8, 60,0.8,0),
                        new Point3D(-44.8 + houseX, -3.7 + houseY, -250.0002 + houseZ),
                        new Point3D(-41.5 + houseX, -3.7 + houseY,-250.0002 + houseZ),
                        new Point3D(-41.5 + houseX, -6.5 + houseY, -250.0002 + houseZ),
                        new Point3D(-44.8 + houseX, -6.5 + houseY, -250.0002 + houseZ)),

                //MENORAT BERECHA
                new Triangle(new Color(java.awt.Color.BLACK), new Material(0, 0, 60, 0,0), //
                        new Point3D(80,10,0),
                        new Point3D(80, -10, 0),
                        new Point3D(83, -10, 0)),
                new Triangle(new Color(java.awt.Color.BLACK), new Material(0, 0, 60, 0,0), //
                        new Point3D(80, 10, 0),
                        new Point3D(83, 10, 0),
                        new Point3D(83, -10, 0)),
                new Sphere(new Color(java.awt.Color.YELLOW), new Material(0.2, 0.5, 30,0,0), // )
                        new Point3D(81.5, -10, 0),5),


                /**
                 * POOL
                 */
                new Triangle(new Color(75, 209, 246), new Material(0.4, 0.1, 60, 0,0.5), //
                        new Point3D(30, 5, -700),
                        new Point3D(0, 10, 0),
                        new Point3D(80, 10, 0)),
                new Triangle(new Color(75, 209, 246), new Material(0.4, 0.1, 60, 0,0.5), //
                        new Point3D(30, 5, -700),
                        new Point3D(0, 10, 0),
                        new Point3D(0, 5, -700)),
//
                new Triangle(new Color(160,82,45), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(0, 5, -30),
                        new Point3D(0, 10, 0),
                        new Point3D(80, 10, 0)),
                new Triangle(new Color(	160,82,45), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(78, 5, -30),
                        new Point3D(0, 5, -30),
                        new Point3D(80, 10, 0)),

                new Triangle(new Color(160,82,45), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(30.5, 4.75, -700),
                        new Point3D(30, 5, -700),
                        new Point3D(80, 10, 0)),
                new Triangle(new Color(160,82,45), new Material(0, 0.8, 60, 0,0), //
                        new Point3D(30.5, 4.75, -700),
                        new Point3D(78, 5, -30),
                        new Point3D(80, 10, 0)),

                new Triangle(new Color(160,82,45), new Material(0, 0, 60, 0,0), //
                        new Point3D(0,5,-700),
                        new Point3D(0, 4.75, -730),
                        new Point3D(30, 5, -700)),
                new Triangle(new Color(160,82,45), new Material(0, 0, 60, 0,0), //
                        new Point3D(32, 4.9, -730),
                        new Point3D(0, 4.75, -730),
                        new Point3D(30.5, 5, -700)),

/**
 * end pool
 */


//GROUND FOR FLOWERS
        new Cube(new Color(139,69,19),
                new Material(0, 0.8, 60,0,0),
                new Point3D(-40,9,-600),
                new Point3D(-2 ,10,-340))
    );


        double oldFlowerX = flowerX;

        for(int i = 0; i < 4;i++)
        {
            flowerX = oldFlowerX;

            for(int j = 0;j < 4;j++)
            {


                scene.addGeometries(
                        /**
                         * FLOWERS
                         */

                        new Triangle(new Color(34,139,34), new Material(0, 0, 60, 0,0), //
                                new Point3D(-60 + flowerX, 10 + flowerY, -250 + flowerZ),
                                new Point3D(-60 + flowerX, 5 + flowerY, -250 + flowerZ),
                                new Point3D(-61 + flowerX, 5 + flowerY, -250 + flowerZ)),
                        new Triangle(new Color(34,139,34), new Material(0, 0.8, 60, 0,0), //
                                new Point3D(-60 + flowerX, 10 + flowerY, -250 + flowerZ),
                                new Point3D(-61 + flowerX, 10 + flowerY, -250 + flowerZ),
                                new Point3D(-61 + flowerX, 5 + flowerY, -250 + flowerZ)),
                        new Sphere(new Color(java.awt.Color.BLACK), new Material(0.2, 0.5, 30), // )
                                new Point3D(-60.5 + flowerX, 5 + flowerY, -250 + flowerZ),2),
                        new Sphere(new Color(java.awt.Color.YELLOW), new Material(0.2, 0.5, 30,1,0), // )
                                new Point3D(-60.75 + flowerX, 3.5 + flowerY, -250 + flowerZ),1),
                        new Sphere(new Color(java.awt.Color.YELLOW), new Material(0.2, 0.5, 30,1,0), // )
                                new Point3D(-59.5 + flowerX, 3.75 + flowerY, -250 + flowerZ),1),
                        new Sphere(new Color(java.awt.Color.YELLOW), new Material(0.2, 0.5, 30,1,0), // )
                                new Point3D(-59 + flowerX, 5 + flowerY, -250 + flowerZ),1),
                        new Sphere(new Color(java.awt.Color.YELLOW), new Material(0.2, 0.5, 30,1,0), // )
                                new Point3D(-59.5 + flowerX, 6.25 + flowerY, -250 + flowerZ),1),
                        new Sphere(new Color(java.awt.Color.YELLOW), new Material(0.2, 0.5, 30,1,0), // )
                                new Point3D(-60.8 + flowerX, 6.7 + flowerY, -250 + flowerZ),1),
                        new Sphere(new Color(java.awt.Color.YELLOW), new Material(0.2, 0.5, 30,1,0), // )
                                new Point3D(-62 + flowerX, 5.75 + flowerY, -250 + flowerZ),1),
                        new Sphere(new Color(java.awt.Color.YELLOW), new Material(0.2, 0.5, 30,1,0), // )
                                new Point3D(-62 + flowerX, 4.25 + flowerY, -250 + flowerZ),1)

                        /**
                         * END FLOWERS
                         */

                );


                flowerX += 10;

            }
            flowerZ -= 40;
        }



        scene.addLights(

                //WINDOW LIGHT
                new PointLight(new Color(java.awt.Color.WHITE),new Point3D(-55, -75,150),1, 4E-4, 2E-5),

                //PANAS Light
        new PointLight(new Color(java.awt.Color.YELLOW),new Point3D(81.5, -10, 0.001),1, 4E-4, 2E-5)

        );

        ImageWriter imageWriter = new ImageWriter("MiniProject2Shadow0.05", 200, 200, 1800, 1800);
        Render render = new Render(imageWriter, scene);

        render.setAdaptiveSuperSamplingActive(false).setAdaptiveSuperSamplingSizeRays(80)
                .setSoftShadowActive(true).setSoftShadowRadius(0.01).setSoftShadowSizeRays(50).setMultithreading(3) //
                .setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }

}