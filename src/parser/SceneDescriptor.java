package parser;

import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A descriptor of the scene
 */
public class SceneDescriptor {
    /**
     * Mapping values of attributes of scene
     */
    Map<String, String> _sceneAttributes;

    /**
     * Mapping values of attributes of camera
     */
    Map<String, String> _cameraAttributes;

    /**
     * Mapping values of attributes of ambient light
     */
    Map<String, String> _ambientLightAttributes;

    /**
     * List of object with map of attributes of each sphere
     */
    List<Map<String, String>> _spheres = new ArrayList();

    /**
     * List of object with map of attributes of each triangle
     */
    List<Map<String, String>> _triangles = new ArrayList();
    /*List<Map<String, String>> _tubes;
    List<Map<String, String>> _planes;
    List<Map<String, String>> _polygons;
    List<Map<String, String>> _cylinders;
    */

    /**
     * A function that parse them xml using the handler with xmlTest name
     * @param xmlTest
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws ParseException
     */
    public void InitializeFromXMLstring(String xmlTest) throws ParserConfigurationException, SAXException, ParseException {

        MyXMLHandler parser = new MyXMLHandler();
        parser.parse(xmlTest, this);
        if (this._sceneAttributes == null) {
            throw new ParseException("No scene element found!", 0);
        } else if (this._cameraAttributes == null) {
            throw new ParseException("No camera element found!", 0);
        } else if (this._ambientLightAttributes == null) {
            throw new ParseException("No camera element found!", 0);
        }

    }

    /**
     * Getter that return the scene attributes
     * @return Map<String, String> scene attributes
     */
    public Map<String, String> getSceneAttributes() {
        return this._sceneAttributes;
    }

    /**
     * Getter that return the camera attributes
     * @return Map<String, String> camera attributes
     */
    public Map<String, String> getCameraAttributes() {
        return this._cameraAttributes;
    }

    /**
     * Getter that return the ambient light attributes
     * @return Map<String, String> ambient light attributes
     */
    public Map<String, String> getAmbientLightAttributes() {
        return this._ambientLightAttributes;
    }

    /**
     * Getter that return the list of spheres attributes
     * @return Map<String, String> list of spheres attributes
     */
    public List<Map<String, String>> getSpheres() {
        return this._spheres;
    }

    /**
     * Getter that return the list of triangles attributes
     * @return Map<String, String> list of triangles attributes
     */
    public List<Map<String, String>> getTriangles() {
        return this._triangles;
    }
}
