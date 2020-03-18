package geometries;

public abstract class RadialGeometry implements Geometry {

    private double _radius;

    public RadialGeometry(double r)
    {
        _radius = r;
    }

    public RadialGeometry(RadialGeometry rg)
    {
        _radius = rg._radius;
    }

    public double getRadius()
    {
        return _radius;
    }



}
