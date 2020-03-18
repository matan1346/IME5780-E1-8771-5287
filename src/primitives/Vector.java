package primitives;


import java.awt.*;

import static java.lang.Math.sqrt;

public class Vector {
    private Point3D startPoint;
    private Point3D endPoint;

    public Vector(Coordinate c1, Coordinate c2, Coordinate c3) throws IllegalArgumentException
    {
        Point3D p = new Point3D(c1, c2, c3);
        if(p.equals(Point3D.getZERO()))
            throw new IllegalArgumentException("Cannot Create Vector with Given Vector 0");
        startPoint = new Point3D(Point3D.getZERO());
        endPoint = p;
    }

    public Vector(double d1, double d2, double d3) throws IllegalArgumentException
    {
        Point3D p = new Point3D(d1, d2, d3);
        if(p.equals(Point3D.getZERO()))
            throw new IllegalArgumentException("Cannot Create Vector with Given Vector 0");
        startPoint = new Point3D(Point3D.getZERO());
        endPoint = p;
    }

    public Vector(Point3D p1) throws IllegalArgumentException
    {
        if(p1.equals(Point3D.getZERO()))
            throw new IllegalArgumentException("Cannot Create Vector with Given Vector 0");
        startPoint = new Point3D(Point3D.getZERO());
        endPoint = new Point3D(p1);
    }

    public Vector(Vector v1)
    {
        startPoint = new Point3D(v1.startPoint);
        endPoint = new Point3D(v1.endPoint);
    }


    public Vector(Point3D startP, Point3D endP) throws IllegalArgumentException
    {
        if(startP.equals(endP))
            throw new IllegalArgumentException("Cannot Create Vector with same points");
        startPoint = new Point3D(startP);
        endPoint = new Point3D(endP);
    }



    public Point3D getStartPoint() {
        return startPoint;
    }

    public Point3D getEndPoint() {
        return endPoint;
    }

    public Vector add(Vector v1)
    {
        return new Vector(new Point3D(v1.startPoint.getCoord1().get() + this.startPoint.getCoord1().get(),
                                        v1.startPoint.getCoord2().get() + this.startPoint.getCoord2().get(),
                                        v1.startPoint.getCoord3().get() + this.startPoint.getCoord3().get()),
                        new Point3D(v1.endPoint.getCoord1().get() + this.endPoint.getCoord1().get(),
                                v1.endPoint.getCoord2().get() + this.endPoint.getCoord2().get(),
                                v1.endPoint.getCoord3().get() + this.endPoint.getCoord3().get()));
    }

    public Vector subtract(Vector v1)
    {
        return new Vector(new Point3D(v1.startPoint.getCoord1().get() - this.startPoint.getCoord1().get(),
                v1.startPoint.getCoord2().get() - this.startPoint.getCoord2().get(),
                v1.startPoint.getCoord3().get() - this.startPoint.getCoord3().get()),
                new Point3D(v1.endPoint.getCoord1().get() - this.endPoint.getCoord1().get(),
                        v1.endPoint.getCoord2().get() - this.endPoint.getCoord2().get(),
                        v1.endPoint.getCoord3().get() - this.endPoint.getCoord3().get()));
    }

    public Vector scale(double scalar)
    {
        return new Vector(
                new Point3D(this.startPoint),
                new Point3D(this.startPoint.getCoord1().get() * scalar,
                            this.startPoint.getCoord2().get() * scalar,
                            this.startPoint.getCoord3().get() * scalar));
    }


    public double dotProduct(Vector v1)
    {
        double d1 = this.startPoint.getCoord1().get() * v1.startPoint.getCoord1().get() +
        this.startPoint.getCoord2().get() * v1.startPoint.getCoord2().get()+
                this.startPoint.getCoord3().get() * v1.startPoint.getCoord3().get();

        double divided = this.startPoint.getCoord1().get() / v1.startPoint.getCoord1().get();

        if(this.startPoint.getCoord2().get() / v1.startPoint.getCoord2().get() == divided &&
            this.startPoint.getCoord3().get() / v1.startPoint.getCoord3().get() == divided)
        {
            //Makbilim
        }

     return d1;
    }


    public Vector crossProduct(Vector v1)
    {
        Vector coordV1 = new Vector(this.endPoint.getCoord1().get() - this.startPoint.getCoord1().get(),
                                    this.endPoint.getCoord2().get() - this.startPoint.getCoord2().get(),
                                    this.endPoint.getCoord3().get() - this.startPoint.getCoord3().get());

        Vector coordV2 = new Vector(v1.endPoint.getCoord1().get() - v1.startPoint.getCoord1().get(),
                v1.endPoint.getCoord2().get() - v1.startPoint.getCoord2().get(),
                v1.endPoint.getCoord3().get() - v1.startPoint.getCoord3().get());
        return new Vector(
                (coordV1.endPoint.getCoord2().get() *coordV2.endPoint.getCoord3().get()) -
                        (coordV1.endPoint.getCoord3().get() * coordV2.endPoint.getCoord2().get()),
                (coordV1.endPoint.getCoord3().get() * coordV2.endPoint.getCoord1().get()) -
                        (coordV1.endPoint.getCoord1().get() * coordV2.endPoint.getCoord3().get()),
                (coordV1.endPoint.getCoord1().get() * coordV2.endPoint.getCoord2().get()) -
                        (coordV1.endPoint.getCoord2().get() * coordV2.endPoint.getCoord1().get()));
    }

    public double lengthSquared()
    {
        return startPoint.distanceSquared(endPoint);
    }

    public double length()
    {
        return sqrt(lengthSquared());
    }

    public Vector normalize()
    {
        double distance = length();
        endPoint = new Point3D(
                endPoint.getCoord1().get() / distance,
                endPoint.getCoord2().get() / distance,
                endPoint.getCoord3().get() / distance);
        return this;
    }

    public Vector normalized()
    {
        return new Vector(normalize());
    }

}
