//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.SAXException;
import parser.SceneDescriptor;
import primitives.Color;
import renderer.ImageWriter;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Scene builder class used to set up the scene
 */
public class SceneBuilder {
    /**
     * SceneDescriptor is a description of the scene
     */
    private SceneDescriptor _sceneDescriptor;

    /**
     * Scene object
     */
    private Scene _scene;

    /**
     * ImageWrite object that sets the image
     */
    private ImageWriter _imageWriter;

    /**
     * The scene file path
     */
    private final String SCENE_FILE_PATH;
    String sceneXMLDesc;

    /**
     * Constructor that gets a scene file name and destination directory
     * @param sceneFileName String the scene file name
     * @param destinationDirectory String the destination directory name
     */
    public SceneBuilder(String sceneFileName, String destinationDirectory) {
        this.SCENE_FILE_PATH = System.getProperty("user.dir") + "/src/";
        File sceneFile = new File(this.SCENE_FILE_PATH + sceneFileName);
        this._scene = new Scene("Scene");
        this.loadSceneFromFile(sceneFile);
        this._sceneDescriptor = new SceneDescriptor();

        try {
            this._sceneDescriptor.InitializeFromXMLstring(this.sceneXMLDesc);
        } catch (ParseException var16) {
            System.out.println("Syntactical error in scene description:");
            var16.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        String[] aLColors = ((String)this._sceneDescriptor.getAmbientLightAttributes().get("color")).split("\\s+");
        Color color = new Color(Double.valueOf(aLColors[0]), Double.valueOf(aLColors[1]), Double.valueOf(aLColors[2]));
        this._scene.setAmbientLight(new AmbientLight(color, 0.7D));
        Camera camera = new Camera(this._sceneDescriptor.getCameraAttributes());
        String[] backgroundColor = ((String)this._sceneDescriptor.getSceneAttributes().get("background-color")).split("\\s+");
        Color background = new Color(Double.valueOf(backgroundColor[0]), Double.valueOf(backgroundColor[1]), Double.valueOf(backgroundColor[2]));
        double screenDist = Double.valueOf((String)this._sceneDescriptor.getSceneAttributes().get("screen-distance"));
        this._scene.setBackground(background);
        this._scene.setCamera(camera);
        this._scene.setDistance(screenDist);
        int screenWidth = Integer.valueOf((String)this._sceneDescriptor.getSceneAttributes().get("screen-width"));
        int screenHeight = Integer.valueOf((String)this._sceneDescriptor.getSceneAttributes().get("screen-width"));
        this._imageWriter = new ImageWriter(destinationDirectory + "scene", (double)screenWidth, (double)screenHeight, screenWidth, screenHeight);
        Iterator var14 = this._sceneDescriptor.getSpheres().iterator();

        Map triangleAttributes;
        while(var14.hasNext()) {
            triangleAttributes = (Map)var14.next();
            Sphere sphere = new Sphere(triangleAttributes);
            this._scene.addGeometries(new Geometry[]{sphere});
        }

        var14 = this._sceneDescriptor.getTriangles().iterator();

        while(var14.hasNext()) {
            triangleAttributes = (Map)var14.next();
            Triangle triangle = new Triangle(triangleAttributes);
            this._scene.addGeometries(new Geometry[]{triangle});
        }

    }

    /**
     * Constructor that gets the scene file name
     * @param sceneFileName String the scene file name
     */
    public SceneBuilder(String sceneFileName) {
        this(sceneFileName, "");
    }

    /**
     * Getter that return the scene object
     * @return Scene scene object
     */
    public Scene getScene() {
        return this._scene;
    }

    /**
     * Getter that return the ImageWriter object
     * @return ImageWriter the image writer object
     */
    public ImageWriter getImageWriter() {
        return this._imageWriter;
    }

    /**
     * Method that gets a file and loads the scene from it
     * @param file File this file need to be loaded
     * @return boolean returns true when load succeed, else false
     */
    private boolean loadSceneFromFile(File file) {
        if (file == null) {
            return false;
        } else {
            try {
                byte[] buffer = new byte[(int)file.length()];
                FileInputStream fin = new FileInputStream(file);
                fin.read(buffer);
                this.sceneXMLDesc = new String(buffer);
                fin.close();
                return true;
            } catch (IOException var4) {
                var4.printStackTrace();
                return false;
            }
        }
    }
}