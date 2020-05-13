package parser;

import geometries.Geometries;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import scene.Scene;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * An handler of the XML SAX Retrieving data
 */
public class MyXMLHandler extends DefaultHandler {

    /**
     * SceneDescriptor that hold the description of the scene from xml
     */
    SceneDescriptor _sceneDescriptor = new SceneDescriptor();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        Map<String, String> myAttributes = new HashMap();

        for(int i = 0; i < attributes.getLength(); ++i) {
            String k = attributes.getQName(i);
            String v = attributes.getValue(i).trim();
            myAttributes.put(k, v);
        }

        if (qName == "scene") {
            _sceneDescriptor._sceneAttributes = myAttributes;
        }/* else if (qName.equalsIgnoreCase("image")) {

            _sceneDescriptor._ambientImage = myAttributes;
        }*/ else if (qName == "camera") {
            _sceneDescriptor._cameraAttributes = myAttributes;
        } else if (qName == "ambient-light") {
            _sceneDescriptor._ambientLightAttributes = myAttributes;
        } else if (qName == "sphere") {
            _sceneDescriptor._spheres.add(myAttributes);
        }else if (qName == "triangle") {
            _sceneDescriptor._triangles.add(myAttributes);
        } /*else if (qName == "tube") {
        _sceneDescriptor._tubes.add(myAttributes);
        }else if (qName == "plane") {
            _sceneDescriptor._planes.add(myAttributes);
        } else if (qName == "polygon") {
        _sceneDescriptor._polygons.add(myAttributes);
        }else if (qName == "cylinder") {
        _sceneDescriptor._cylinders.add(myAttributes);
        }*/
        super.startElement(uri, localName, qName, attributes);
    }


    /**
     * Parser function that parse the xml using the handler
     * @param text
     * @param sceneDesc
     */
    public void parse(String text, SceneDescriptor sceneDesc) {
        this._sceneDescriptor = sceneDesc;

        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            XMLReader xr = parser.getXMLReader();
            xr.setContentHandler(this);
            xr.setErrorHandler(this);
            xr.parse(new InputSource(new StringReader(text)));
        } catch (SAXException var6) {
            var6.printStackTrace();
        } catch (IOException var7) {
            var7.printStackTrace();
        } catch (ParserConfigurationException var8) {
            var8.printStackTrace();
        }

    }
}