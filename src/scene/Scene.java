package scene;

import elements.*;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.*;

public class Scene {

    String _name;
    Color _background;
    AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    double _distance;

    public Scene(String _name) {
        this._name = _name;
        _geometries = new Geometries();
    }

    public void addGeometries(Intersectable... geometries){
        _geometries.add(geometries);
    }

    public String getName() {
        return _name;
    }

    public Color getBackground() {
        return _background;
    }

    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    public Geometries getGeometries() {
        return _geometries;
    }

    public Camera getCamera() {
        return _camera;
    }

    public double getDistance() {
        return _distance;
    }

    public Scene setBackground(Color _background) {
        this._background = _background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
        return this;
    }

    public Scene setCamera(Camera _camera) {
        this._camera = _camera;
        return this;
    }

    public Scene setDistance(double _distance) {
        this._distance = _distance;
        return this;
    }
}
