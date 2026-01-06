package levels;

import biuoop.DrawSurface;
import sprites.Sprite;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

public class ImageBackground implements Sprite {
    private final Image image;

    public ImageBackground(String imagePath) {
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imagePath);
            if (is == null) {
                throw new RuntimeException("Background image not found in classpath: " + imagePath);
            }
            this.image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load background image: " + imagePath, e);
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.drawImage(0, 0, image);
    }

    @Override
    public void timePassed() {
        // no-op
    }
}
