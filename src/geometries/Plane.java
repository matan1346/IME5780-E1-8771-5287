package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane {

    Point3D _p;
    Vector normal;

    public Plane(Point3D _p1,Point3D _p2,Point3D _p3) {

        this._p = _p1;
        normal = null;

    }
    public Plane(Point3D _p, Vector _normal) {
        this._p = _p;
        this.normal = _normal;
    }

    public Point3D get_p() {
        return _p;
    }

    public Vector getNormal() {
        return normal;
    }
}
