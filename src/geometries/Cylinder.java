package geometries;

import primitives.Point3D;

public class Cylinder extends RadialGeomtry {
    double height;

    public Cylinder(double height, double r) {
        super(r);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public Vector getNormal(Point3D p1)
    {
        return null;
    }
}