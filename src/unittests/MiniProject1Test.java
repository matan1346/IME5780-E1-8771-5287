package unittests;

import elements.AmbientLight;
import elements.Camera;
import elements.SpotLight;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Tube;
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

        ImageWriter imageWriter = new ImageWriter("MiniProject2", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }


}