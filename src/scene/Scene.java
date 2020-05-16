package scene;

import elements.*;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class the represents the scene and its objects
 */
public class Scene {
    /**
     * The name of the scene
     */
    String _name;

    /**
     * The background color of the scene
     */
    Color _background;

    /**
     * The ambient light of the scene
     */
    AmbientLight _ambientLight;

    /**
     * List of geometries that exists in the scene
     */
    Geometries _geometries;

    /**
     * Camera object of the scene
     */
    Camera _camera;

    /**
     * The distance that can be allowed
     */
    double _distance;

    /**
     * list of light sources
     */
    List<LightSource> _lights;

    /**
     * Constructor that gets scene name and sets it
     * @param _name String scene name
     */
    public Scene(String _name) {
        this._name = _name;
        _geometries = new Geometries();
        _lights = new LinkedList<LightSource>();
    }

    /**
     * Method that gets list of geometries and add them to the list
     * @param geometries Intersectable list of geometries
     */
    public void addGeometries(Intersectable... geometries){
        _geometries.add(geometries);
    }

    /**
     * Getter that return the name of the scene
     * @return String name of the scene
     */
    public String getName() {
        return _name;
    }

    /**
     * Getter that return the background color of te scene
     * @return Color background color of the scene
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * Getter that return the Ambient light
     * @return AmbientLight ambient light of the scene
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * Getter that return the geometries in the scene
     * @return Geometries geometries of the scene
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * Getter that return the camera of the scene
     * @return Camera camera of the scene
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * Getter that return the distance of the scene
     * @return double distance of the scene
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * Getter that return list of the light sources
     * @return List<LightSource> list of light sources
     */
    public List<LightSource> getLights() {
        return _lights;
    }

    /**
     * Setter that sets the background color of the scene
     * @param _background Color background color of the scene
     * @return Scene this object to continue init
     */
    public Scene setBackground(Color _background) {
        this._background = _background;
        return this;
    }

    /**
     * Setter that sets the ambient light of the scene
     * @param _ambientLight AmbientLight ambient light of the scene
     * @return Scene this object to continue init
     */
    public Scene setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
        return this;
    }

    /**
     * Setter that sets the camera of the scene
     * @param _camera Camera camera of the scene
     * @return Scene this object to continue init
     */
    public Scene setCamera(Camera _camera) {
        this._camera = _camera;
        return this;
    }

    /**
     * Setter that sets the distance of the scene
     * @param _distance double distance of the scene
     * @return Scene this object to continue init
     */
    public Scene setDistance(double _distance) {
        this._distance = _distance;
        return this;
    }

    /**
     * Adding lights to the list of sources
     * @param lights lights sources
     */
    public void addLights(LightSource... lights){
        for(LightSource l : lights)
            _lights.add(l);
    }
}
