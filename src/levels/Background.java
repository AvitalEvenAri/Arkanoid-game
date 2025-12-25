package levels;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;

public class Background implements Sprite {
    private final Color color;

    public Background(Color color) {
        this.color = color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle(0, 0, 800, 600);
    }

    @Override
    public void timePassed() {
        // background does not change over time
    }
}
