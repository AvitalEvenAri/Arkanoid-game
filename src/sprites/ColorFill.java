package sprites;

import biuoop.DrawSurface;
import java.awt.Color;

public class ColorFill implements BlockFill {
    private final Color color;

    public ColorFill(Color color) {
        this.color = color;
    }

    @Override
    public void fill(DrawSurface d, int x, int y, int width, int height) {
        d.setColor(color);
        d.fillRectangle(x, y, width, height);
    }
}
