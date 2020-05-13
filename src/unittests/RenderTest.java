package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.*;
import scene.Scene;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test rendering abasic image
 *
 * @author Dan
 */
public class RenderTest {

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(100);
        scene.setBackground(new Color(75, 127, 90));
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1));

        scene.addGeometries(new Sphere(new Point3D(0, 0, 100),50));

        scene.addGeometries(
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100)));

        ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50, java.awt.Color.YELLOW);
        render.writeToImage();
    }

    @Test
    public void getClosestPointTest()
    {
        /*
        Scene scene = new Scene("Scene name");
        scene.setCamera(new Camera(Point3D.ZERO, new Vector(0,0,1), new Vector(0,-1,0)));
        Render render = new Render(new ImageWriter("Test", 1600, 100, 800, 500), scene);

        List<Point3D> points = List.of(new Point3D(1,2,3),new Point3D(6,7,8), new Point3D(0,0,2));

        Point3D closestPoint = render.getClosestPoint(points);

        assertEquals("ERROR - Closest point not good", points.get(2), closestPoint);
        */
    }
}