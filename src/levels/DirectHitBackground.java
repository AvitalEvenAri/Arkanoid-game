package levels;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;

public class DirectHitBackground implements Sprite {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int INDICATOR_HEIGHT = 20;

    @Override
    public void drawOn(DrawSurface d) {
        // 1) Background black
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, WIDTH, HEIGHT);

        // 2) Target center (similar to screenshot)
        int cx = WIDTH / 2;
        int cy = 140; // מקום טוב כמו בתמונה (אפשר לכוונן)

        d.setColor(Color.BLUE);

        // circles
        d.drawCircle(cx, cy, 50);
        d.drawCircle(cx, cy, 90);
        d.drawCircle(cx, cy, 130);

        // cross lines
        d.drawLine(cx - 160, cy, cx + 160, cy);
        d.drawLine(cx, cy - 160, cx, cy + 160);
    }

    @Override
    public void timePassed() {
        // no-op
    }
}
