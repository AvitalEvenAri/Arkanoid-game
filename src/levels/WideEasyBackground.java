package levels;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;

public class WideEasyBackground implements Sprite {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void drawOn(DrawSurface d) {
        // 1) Bright background
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, WIDTH, HEIGHT);

        // 2) Sun (top-left)
        int sunX = 120;
        int sunY = 120;

        // Rays
        d.setColor(new Color(240, 220, 120));
        for (int i = 0; i < 120; i++) {
            // קווים “מאווררים” ימינה למטה כמו בתמונה
            int x2 = 800;
            int y2 = 200 + i * 2;
            d.drawLine(sunX, sunY, x2, y2);
        }

        // Sun circles (layered)
        d.setColor(new Color(255, 240, 150));
        d.fillCircle(sunX, sunY, 60);

        d.setColor(new Color(255, 230, 100));
        d.fillCircle(sunX, sunY, 45);

        d.setColor(new Color(255, 220, 0));
        d.fillCircle(sunX, sunY, 30);
    }

    @Override
    public void timePassed() {
        // no-op
    }
}
