package primitives;

public class Ray {
    private Point3D _p;
    private Vector _dir;

    public Ray(Point3D _p, Vector _dir) {
        this._p = new Point3D(_p);
        this._dir = new Vector(_dir);
    }

    public Ray(Ray r) {
        this(r._p, r._dir);
    }

    public Point3D get_p() {
        return _p;
    }

    public Vector get_dir() {
        return _dir;
    }


}
