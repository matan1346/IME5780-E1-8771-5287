package primitives;


import java.awt.*;

import static java.lang.Math.sqrt;

public class Vector {
    private Point3D _header;

    public Vector(Coordinate c1, Coordinate c2, Coordinate c3) throws IllegalArgumentException
    {
        Point3D p = new Point3D(c1, c2, c3);
        if(p.equals(Point3D.getZERO()))
            throw new IllegalArgumentException("Cannot Create Vector with Given Vector 0");
        _header = p;
    }

    public Vector(double d1, double d2, double d3) throws IllegalArgumentException
    {
        Point3D p = new Point3D(d1, d2, d3);
        if(p.equals(Point3D.getZERO()))
            throw new IllegalArgumentException("Cannot Create Vector with Given Vector 0");
        _header = p;
    }

    public Vector(Point3D p1) throws IllegalArgumentException
    {
        if(p1.equals(Point3D.getZERO()))
            throw new IllegalArgumentException("Cannot Create Vector with Given Vector 0");
        _header = new Point3D(p1);
    }

    public Vector(Vector v1)
    {
        _header = new Point3D(v1._header);
    }


    public Point3D getHeader() {
        return _header;
    }

    public Vector add(Vector v1)
    {
        return new Vector(new Point3D(v1._header.getCoord1().get() + this._header.getCoord1().get(),
                                        v1._header.getCoord2().get() + this._header.getCoord2().get(),
                                        v1._header.getCoord3().get() + this._header.getCoord3().get())
                        );
    }

    public Vector subtract(Vector v1)
    {
        return new Vector(new Point3D(v1._header.getCoord1().get() - this._header.getCoord1().get(),
                v1._header.getCoord2().get() - this._header.getCoord2().get(),
                v1._header.getCoord3().get() - this._header.getCoord3().get())
               );
    }

    public Vector scale(double scalar)
    {
        return new Vector(
                new Point3D(this._header.getCoord1().get() * scalar,
                            this._header.getCoord2().get() * scalar,
                            this._header.getCoord3().get() * scalar));
    }


    public double dotProduct(Vector v1)
    {
        double d1 = this._header.getCoord1().get() * v1._header.getCoord1().get() +
        this._header.getCoord2().get() * v1._header.getCoord2().get()+
                this._header.getCoord3().get() * v1._header.getCoord3().get();

        double divided = this._header.getCoord1().get() / v1._header.getCoord1().get();

        if(this._header.getCoord2().get() / v1._header.getCoord2().get() == divided &&
            this._header.getCoord3().get() / v1._header.getCoord3().get() == divided)
        {
            //Makbilim
        }

     return d1;
    }


    public Vector crossProduct(Vector v1)
    {

        return new Vector(
                (this._header.getCoord2().get() *v1._header.getCoord3().get()) -
                        (this._header.getCoord3().get() * v1._header.getCoord2().get()),
                (this._header.getCoord3().get() * v1._header.getCoord1().get()) -
                        (this._header.getCoord1().get() * v1._header.getCoord3().get()),
                (this._header.getCoord1().get() * v1._header.getCoord2().get()) -
                        (this._header.getCoord2().get() * v1._header.getCoord1().get()));
    }

    public double lengthSquared()
    {
        return _header.distanceSquared(Point3D.getZERO());
    }

    public double length()
    {
        return sqrt(lengthSquared());
    }

    public Vector normalize()
    {
        double distance = length();
        _header = new Point3D(
                _header.getCoord1().get() / distance,
                _header.getCoord2().get() / distance,
                _header.getCoord3().get() / distance);
        return this;
    }

    public Vector normalized()
    {
        return new Vector(normalize());
    }

}
