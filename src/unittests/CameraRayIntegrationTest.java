package unittests;

import elements.Camera;
import org.junit.Test;
import primitives.*;
import geometries.*;

import java.util.List;

import static org.junit.Assert.*;

public class CameraRayIntegrationTest {

    Camera cam1 = new Camera(Point3D.ZERO, new Vector(new Point3D(0,0,1)), new Vector(0,-1,0));
    Camera cam2 = new Camera(new Point3D(0,0,-0.5), new Vector(new Point3D(0,0,1)), new Vector(0,-1,0));

    double screenDistance = 1;
    double screenWidth = 3;
    double screenHeight = 3;
    int Nx = 3;
    int Ny = 3;

    @Test
    public void IntegrationSphereTest()
    {
        Sphere sp = new Sphere(new Point3D(0,0,3), 1);
        int count = 0;

        for(int i = 0;i < Nx;i++)
        {
            for(int j = 0;j < Ny;j++)
            {
                Ray r = cam1.constructRayThroughPixel(Nx,Ny,j,i,screenDistance,screenWidth,screenHeight);
                List<Point3D> listPoints = sp.findIntersections(r);
                count += (listPoints != null ? listPoints.size() : 0);
            }
        }

        assertEquals("ERROR - INTEGRATION SPHERE FAILED ON 2 INTERSECTIONS", 2, count);

    }

    @Test
    public void IntegrationSphere2Test()
    {
        Sphere sp = new Sphere(new Point3D(0,0,2.5), 2.5);
        int count = 0;

        for(int i = 0;i < Nx;i++)
        {
            for(int j = 0;j < Ny;j++)
            {
                Ray r = cam2.constructRayThroughPixel(Nx,Ny,j,i,screenDistance,screenWidth,screenHeight);
                List<Point3D> listPoints = sp.findIntersections(r);
                count += (listPoints != null ? listPoints.size() : 0);
            }
        }

        assertEquals("ERROR - INTEGRATION SPHERE FAILED ON 18 INTERSECTIONS", 18, count);

    }

    @Test
    public void IntegrationSphere3Test()
    {
        Sphere sp = new Sphere(new Point3D(0,0,2), 2);
        int count = 0;

        for(int i = 0;i < Nx;i++)
        {
            for(int j = 0;j < Ny;j++)
            {
                Ray r = cam2.constructRayThroughPixel(Nx,Ny,j,i,screenDistance,screenWidth,screenHeight);
                List<Point3D> listPoints = sp.findIntersections(r);
                count += (listPoints != null ? listPoints.size() : 0);
            }
        }

        assertEquals("ERROR - INTEGRATION SPHERE FAILED ON 10 INTERSECTIONS", 10, count);

    }

    @Test
    public void IntegrationSphere4Test()
    {
        Sphere sp = new Sphere(new Point3D(0,0,2), 4);
        int count = 0;

        for(int i = 0;i < Nx;i++)
        {
            for(int j = 0;j < Ny;j++)
            {
                Ray r = cam2.constructRayThroughPixel(Nx,Ny,j,i,screenDistance,screenWidth,screenHeight);
                List<Point3D> listPoints = sp.findIntersections(r);
                count += (listPoints != null ? listPoints.size() : 0);
            }
        }

        assertEquals("ERROR - INTEGRATION SPHERE FAILED ON 9 INTERSECTIONS", 9, count);

    }

    @Test
    public void IntegrationSphere5Test()
    {
        Sphere sp = new Sphere(new Point3D(0,0,-1), 0.5d);
        int count = 0;

        for(int i = 0;i < Nx;i++)
        {
            for(int j = 0;j < Ny;j++)
            {
                Ray r = cam2.constructRayThroughPixel(Nx,Ny,j,i,screenDistance,screenWidth,screenHeight);
                List<Point3D> listPoints = sp.findIntersections(r);
                count += (listPoints != null ? listPoints.size() : 0);
            }
        }

        assertEquals("ERROR - INTEGRATION SPHERE FAILED ON 0 INTERSECTIONS", 0, count);

    }

    @Test
    public void IntegrationTriangleTest()
    {
        Triangle tr = new Triangle(new Point3D(1,1,2), new Point3D(0,-1,2), new Point3D(-1,1,2));
        int count = 0;

        for(int i = 0;i < Nx;i++)
        {
            for(int j = 0;j < Ny;j++)
            {
                Ray r = cam1.constructRayThroughPixel(Nx,Ny,j,i,screenDistance,screenWidth,screenHeight);
                List<Point3D> listPoints = tr.findIntersections(r);
                count += (listPoints != null ? listPoints.size() : 0);
            }
        }

        assertEquals("ERROR - INTEGRATION TRIANGLE FAILED ON 1 INTERSECTIONS", 1, count);

    }

    @Test
    public void IntegrationTriangle2Test()
    {
        Triangle tr = new Triangle(new Point3D(1,1,2), new Point3D(0,-20,2), new Point3D(-1,1,2));
        int count = 0;

        for(int i = 0;i < Nx;i++)
        {
            for(int j = 0;j < Ny;j++)
            {
                Ray r = cam1.constructRayThroughPixel(Nx,Ny,j,i,screenDistance,screenWidth,screenHeight);
                List<Point3D> listPoints = tr.findIntersections(r);
                count += (listPoints != null ? listPoints.size() : 0);
            }
        }

        assertEquals("ERROR - INTEGRATION TRIANGLE FAILED ON 2 INTERSECTIONS", 2, count);

    }

    @Test
    public void IntegrationPlaneTest()
    {
        Plane pl = new Plane(new Point3D(0,3,3), new Point3D(0,-3,3), new Point3D(1,5,3));
        int count = 0;

        for(int i = 0;i < Nx;i++)
        {
            for(int j = 0;j < Ny;j++)
            {
                Ray r = cam1.constructRayThroughPixel(Nx,Ny,j,i,screenDistance,screenWidth,screenHeight);
                List<Point3D> listPoints = pl.findIntersections(r);
                count += (listPoints != null ? listPoints.size() : 0);
            }
        }

        assertEquals("ERROR - INTEGRATION PLANE FAILED ON 9 INTERSECTIONS", 9, count);

    }

    @Test
    public void IntegrationPlane2Test()
    {
        Plane pl = new Plane(new Point3D(1,8,0), new Point3D(0,0,3), new Point3D(5,-5,5));
        int count = 0;

        for(int i = 0;i < Nx;i++)
        {
            for(int j = 0;j < Ny;j++)
            {
                Ray r = cam1.constructRayThroughPixel(Nx,Ny,j,i,screenDistance,screenWidth,screenHeight);
                List<Point3D> listPoints = pl.findIntersections(r);
                count += (listPoints != null ? listPoints.size() : 0);
            }
        }

        assertEquals("ERROR - INTEGRATION PLANE FAILED ON 9 INTERSECTIONS", 9, count);

    }

    //TODO Make this work
    @Test
    public void IntegrationPlane3Test()
    {
        Plane pl = new Plane(new Point3D(-1,-20,-10), new Point3D(0,0,10), new Point3D(1,-20,-10));
        int count = 0;

        for(int i = 0;i < Nx;i++)
        {
            for(int j = 0;j < Ny;j++)
            {
                Ray r = cam1.constructRayThroughPixel(Nx,Ny,j,i,screenDistance,screenWidth,screenHeight);
                List<Point3D> listPoints = pl.findIntersections(r);
                count += (listPoints != null ? listPoints.size() : 0);
            }
        }

        assertEquals("ERROR - INTEGRATION PLANE FAILED ON 6 INTERSECTIONS", 6, count);

    }

}