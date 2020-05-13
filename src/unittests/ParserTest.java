package unittests;

import org.junit.Test;
import renderer.*;
import scene.*;

import java.awt.*;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void basicRendering() throws Exception {
        SceneBuilder sceneBuilder = new SceneBuilder("basicRenderTestTwoColors.xml");
        Scene scene = sceneBuilder.getScene();
        ImageWriter imageWriter = new ImageWriter("Render test", 500.0D, 500.0D, 500, 500);
        Render render = new Render(imageWriter, scene);
        render.renderImage();
        render.printGrid(50, Color.black);
        render.writeToImage();
    }

}