package unittests;

import elements.AmbientLight;
import elements.Camera;
import geometries.Cube;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import static org.junit.Assert.*;

public class CubeTest {

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a
     * grid
     */
    @Test
    public void CubeTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        //scene.addGeometries(new Sphere(new Point3D(0, 0, 100),50));

        scene.addGeometries(
                new Cube(new Color(java.awt.Color.RED),
                        new Material(0, 0.8, 60,0,1),
                        new Point3D(-20,8,100), new Point3D(2,15,300))
                );

        ImageWriter imageWriter = new ImageWriter("cubeTest", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        //render.printGrid(50, java.awt.Color.YELLOW);
        render.writeToImage();
    }

}