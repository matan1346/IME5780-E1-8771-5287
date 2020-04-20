package primitives;


import java.util.Objects;

import static java.lang.Math.sqrt;

/**
 * Vector class that represents a Vector in 3D dimension
 */
public class Vector {
    /**
     * Point that represents the header of the vector
     */
    private Point3D _header;

    /**
     * Constructor that gets 3 coordinates and sets the header point
     * @param c1 coordinate1 x of the header point
     * @param c2 coordinate2 y of the header point
     * @param c3 coordinate3 z of the header point
     * @throws IllegalArgumentException throws exception when vector equals ZERO vector
     */
    public Vector(Coordinate c1, Coordinate c2, Coordinate c3) throws IllegalArgumentException
    {
        Point3D p = new Point3D(c1, c2, c3);
        if(p.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Cannot Create Vector with Given Vector 0");
        _header = p;
    }

    /**
     * Constructor that gets 3 numbers and sets the header point
     * @param d1 number1 coordinate x of header point
     * @param d2 number2 coordinate y of header point
     * @param d3 number3 coordinate z of header point
     * @throws IllegalArgumentException throws exception when vector equals ZERO vector
     */
    public Vector(double d1, double d2, double d3) throws IllegalArgumentException
    {
        Point3D p = new Point3D(d1, d2, d3);
        if(p.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Cannot Create Vector with Given Vector 0");
        _header = p;
    }

    /**
     * Constructor that gets a point object and sets the header point
     * @param p1 point of header point
     * @throws IllegalArgumentException throws exception when vector equals ZERO vector
     */
    public Vector(Point3D p1) throws IllegalArgumentException
    {
        if(p1.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Cannot Create Vector with Given Vector 0");
        _header = new Point3D(p1);
    }

    /**
     * Copy Constructor
     * @param v1 vector to be copied
     */
    public Vector(Vector v1)
    {
        _header = new Point3D(v1._header);
    }

    /**
     * returns the point of the header of the vector
     * @return point of the header
     */
    public Point3D getHeader() {
        return _header;
    }

    /**
     * Adding two vectors and returns a new vector
     * @param v1 vector object
     * @return new vector in result of addition 2 vectors
     */
    public Vector add(Vector v1)
    {
        return new Vector(new Point3D(v1._header.getX().get() + this._header.getX().get(),
                                        v1._header.getY().get() + this._header.getY().get(),
                                        v1._header.getZ().get() + this._header.getZ().get())
                        );
    }

    /**
     * Subtracting two vectors and return new vector
     * @param v1 vector object
     * @return new vector in result of subtraction 2 vectors
     */
    public Vector subtract(Vector v1)
    {
        return new Vector(new Point3D(v1._header.getX().get() - this._header.getX().get(),
                v1._header.getY().get() - this._header.getY().get(),
                v1._header.getZ().get() - this._header.getZ().get())
               );
    }

    /**
     * Scale a vector with number
     * @param scalar a number to scale with
     * @return new scaled vector using given scalar
     */
    public Vector scale(double scalar)
    {
        return new Vector(
                new Point3D(this._header.getX().get() * scalar,
                            this._header.getY().get() * scalar,
                            this._header.getZ().get() * scalar));
    }


    /**
     * Scalar multiplication between 2 vectors and return a number represents the product
     * @param v1 vector object
     * @return number represents the product of multiplication
     */
    public double dotProduct(Vector v1)
    {
        double d1 = this._header.getX().get() * v1._header.getX().get() +
        this._header.getY().get() * v1._header.getY().get()+
                this._header.getZ().get() * v1._header.getZ().get();

        double divided = this._header.getX().get() / v1._header.getX().get();

        if(this._header.getY().get() / v1._header.getY().get() == divided &&
            this._header.getZ().get() / v1._header.getZ().get() == divided)
        {
            //Makbilim
        }

     return d1;
    }

    /**
     * Vector multiplication between 2 vectors and return a new vector
     * @param v1 vector object
     * @return new vector represents the product the multiplication
     */
    public Vector crossProduct(Vector v1)
    {

        return new Vector(
                (this._header.getY().get() *v1._header.getZ().get()) -
                        (this._header.getZ().get() * v1._header.getY().get()),
                (this._header.getZ().get() * v1._header.getX().get()) -
                        (this._header.getX().get() * v1._header.getZ().get()),
                (this._header.getX().get() * v1._header.getY().get()) -
                        (this._header.getY().get() * v1._header.getX().get()));
    }

    /**
     * Calculating the length squared of the vector
     * @return double vector length squared
     */
    public double lengthSquared()
    {
        return _header.distanceSquared(Point3D.ZERO);
    }

    /**
     * Calculating length of the vector
     * @return double vector length
     */
    public double length()
    {
        return sqrt(lengthSquared());
    }

    /**
     * Normalize the vector itself
     * @return self vector
     */
    public Vector normalize()
    {
        double distance = length();
        _header = new Point3D(
                _header.getX().get() / distance,
                _header.getY().get() / distance,
                _header.getZ().get() / distance);
        return this;
    }

    /**
     * return new normalized vector
     * @return new normalized vector
     */
    public Vector normalized()
    {
        return new Vector(this).normalize();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _header.equals(vector._header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_header);
    }

    @Override
    public String toString() {
        return "Vector: " + _header;
    }
}
