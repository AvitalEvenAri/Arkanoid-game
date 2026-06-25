package levels;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;

public class FinalFourBackground implements Sprite {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void drawOn(DrawSurface d) {
        // 1) Sky blue
        d.setColor(new Color(20, 140, 200));
        d.fillRectangle(0, 0, WIDTH, HEIGHT);

        // 2) Clouds left
        drawCloudWithRain(d, 140, 420);

        // 3) Clouds right
        drawCloudWithRain(d, 640, 420);
    }

    private void drawCloudWithRain(DrawSurface d, int cx, int cy) {
        // rain
        d.setColor(new Color(200, 200, 255));
        for (int i = -40; i <= 40; i += 10) {
            d.drawLine(cx + i, cy + 20, cx + i - 20, cy + 120);
        }

        // cloud blobs
        d.setColor(new Color(210, 210, 210));
        d.fillCircle(cx - 30, cy, 30);
        d.fillCircle(cx, cy - 15, 35);
        d.fillCircle(cx + 35, cy, 28);

        d.setColor(new Color(180, 180, 180));
        d.fillCircle(cx + 10, cy + 10, 28);
    }

    @Override
    public void timePassed() {
        // no-op
    }
}
