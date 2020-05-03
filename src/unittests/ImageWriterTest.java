package unittests;

import org.junit.Test;
import renderer.ImageWriter;

import java.awt.*;

import static org.junit.Assert.*;

public class ImageWriterTest {

    @Test
    public void writeToImage() {
        int width = 1600;
        int height = 1000;
        int nx = 800;
        int ny = 500;
        ImageWriter Iw = new ImageWriter("Test", width, height, nx, ny);

        for (int col = 0; col < ny; col++) {
            for (int row = 0; row < nx; row++) {
                if (col % 10 == 0 || row % 10 == 0) {
                    Iw.writePixel(row, col, Color.WHITE);
                }
            }
        }
        Iw.writeToImage();

    }
}