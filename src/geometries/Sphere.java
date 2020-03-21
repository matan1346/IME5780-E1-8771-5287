package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere extends RadialGeometry {

    Point3D _p;

    public Sphere(Point3D _p, double r)
    {
        super(r);
        this._p = _p;

    }

    public Vector getNormal(Point3D p1)
    {
        return null;
    }
}
