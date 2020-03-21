package geometries;

import primitives.Point3D;

public class Tube extends RadialGeomtry {
    Ray _axisRay;

    public Tube(Ray _ray, double r) {
        super(r);
        this._ray = _ray;
    }

    public Ray get_ray() {
        return _ray;
    }

    public Vector getNormal(Point3D p1)
    {
        return null;
    }
}
