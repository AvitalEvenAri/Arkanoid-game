package sprites;

import biuoop.DrawSurface;
import java.awt.Image;

public class ImageFill implements BlockFill {
    private final Image image;

    public ImageFill(Image image) {
        this.image = image;
    }

    @Override
    public void fill(DrawSurface d, int x, int y, int width, int height) {
        d.drawImage(x, y, image);
    }
}
