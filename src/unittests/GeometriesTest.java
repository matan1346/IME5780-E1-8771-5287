package unittests;

import geometries.Geometries;
import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class GeometriesTest {

    @Test
    public void findIntersections() {

        Geometries geometries = new Geometries();

        //assertEquals("ERROR - no intersections", null, geometries.findIntersections(new Ray(new Point3D())));
        geometries.add(new Plane(new Point3D(0,0,4), new Point3D(0,-2,0), new Point3D(2,0,0)),
                new Triangle(new Point3D(0,3.13,0) ,new Point3D(0,0,1), new Point3D(-3,0,0)));
        assertEquals("ERROR - אף צורה לא נחתכת " ,null , geometries.findIntersections(new Ray(new Point3D(0,6,0), new Vector(-10,-6,0).normalize())));


      assertEquals("ERROR - צורה אחת בלבד נחתכת  " ,new ArrayList<Point3D>(
              Arrays.asList(new Point3D(0.006246096189881811, -0.4896939412866957, 3.0081199250468456))) , geometries.findIntersections(new Ray(new Point3D(10,0,0), new Vector(-10,-0.49,3.01).normalize())));

        ArrayList<Point3D> arrayPoint3D =  new ArrayList<Point3D>(
             Arrays.asList(new Point3D(0.006246096189881811,-0.4896939412866957,3.0081199250468456),new Point3D(-0.4918032786885256,-0.5140983606557377,3.1580327868852462)));


        geometries.add(new Triangle(new Point3D(-6,0,0) ,new Point3D(0,-4,0), new Point3D(0,0,4)));

        assertEquals("ERROR - כמה צורות )אך לא כולן( נחתכות   " ,
                new ArrayList<Point3D>(
                Arrays.asList(new Point3D(0.006246096189881811,-0.4896939412866957,3.0081199250468456),
                        new Point3D(-0.4918032786885256,-0.5140983606557377,3.1580327868852462))),
                geometries.findIntersections(new Ray(new Point3D(10,0,0), new Vector(-10,-0.49,3.01).normalize())));


        Geometries geometries2 = new Geometries();
        geometries2.add(new Plane(new Point3D(0,1,0), new Point3D(1,0,0), new Point3D(0,0,1)),
                new Triangle(new Point3D(3,0,0) ,new Point3D(0,0,3), new Point3D(0,3,0)),
                new Triangle(new Point3D(0,-5,0) ,new Point3D(0,0,5), new Point3D(-5,0,0)));
        assertEquals("ERROR - כל הצורות נחתכות   " ,
                new ArrayList<Point3D>(
                        Arrays.asList(new Point3D(0.2892421441774484, -0.3424768946395571, 1.0532347504621073),
                                new Point3D(1.4759334565619229, 0.7554898336414051, 0.7685767097966727),
                                new Point3D(-1.509064748201438, -2.0063309352517984, 1.4846043165467624))),
                geometries2.findIntersections(new Ray(new Point3D(4.68,3.72,0), new Vector(-3.21,-2.97,0.77).normalize())));

    }
}