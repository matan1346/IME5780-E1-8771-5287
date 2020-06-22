package geometries;

import primitives.*;




public class Pyramid extends Geometries {


    //public Pyramid(Point3D center, Vector direction, double widthX, double widthY, double height){
    public Pyramid(Ray axisRay, double widthX, double widthY, double height){
        this(Color.BLACK, new Material(0,0,0), axisRay, widthX, widthY, height);
    }

    public Pyramid(Color emission,Material material,Ray axisRay, double widthX, double widthY, double height){
        Point3D center = new Point3D(axisRay.get_p());
        Vector direction = new Vector(axisRay.get_dir());
        double halfWidthX = widthX/2d;
        double halfWidthY = widthY/2d;


        //Point3D pyramid - top point
        Point3D topPyramid = center.add(direction.scale(height));

        //close point to center for making vectors directions on the base polygon
        Point3D nearPCenter = center.add(new Vector(1,0,0));

        //Left and right vector
        Vector proj = direction.crossProduct(nearPCenter.subtract(center));

        //back and and forward vector
        Vector projBack = proj.crossProduct(direction);

        //base top left and to right points
        Point3D pyLeftTop = center.add(projBack.scale(halfWidthY)).add(proj.scale(-halfWidthX));
        Point3D pyRightTop = center.add(projBack.scale(halfWidthY)).add(proj.scale(halfWidthX));

        //base bottom left and bottom right points
        Point3D pyLeftBottom = center.add(projBack.scale(-halfWidthY)).add(proj.scale(-halfWidthX));
        Point3D pyRightBottom = center.add(projBack.scale(-halfWidthY)).add(proj.scale(halfWidthX));

        //adding the geometries that creates the pyramid to the list of geometries
        add(new Triangle(new Color(emission), material, pyRightTop, topPyramid, pyRightBottom),
                new Triangle(new Color(emission), material, pyRightBottom, topPyramid, pyLeftBottom),
                new Triangle(new Color(emission), material, pyLeftBottom, topPyramid, pyLeftTop),
                new Triangle(new Color(emission), material, pyLeftTop, topPyramid, pyRightTop),
                new Polygon(new Color(emission), material, pyRightTop, pyRightBottom, pyLeftBottom, pyLeftTop));
    }
}