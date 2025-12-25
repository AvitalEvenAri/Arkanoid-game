package levels;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;

public class Green3Background implements Sprite {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void drawOn(DrawSurface d) {
        // 1) Green background
        d.setColor(new Color(0, 120, 0));
        d.fillRectangle(0, 0, WIDTH, HEIGHT);

        // 2) Building (bottom-left)
        int bx = 60;
        int by = 420;
        int bw = 110;
        int bh = 180;

        d.setColor(new Color(25, 25, 25));
        d.fillRectangle(bx, by, bw, bh);

        // windows grid
        d.setColor(Color.WHITE);
        int rows = 6;
        int cols = 5;
        int pad = 10;
        int winW = (bw - pad * 2) / cols - 4;
        int winH = (bh - pad * 2) / rows - 4;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = bx + pad + c * (winW + 4);
                int y = by + pad + r * (winH + 4);
                d.fillRectangle(x, y, winW, winH);
            }
        }

        // 3) “Pole” left
        d.setColor(new Color(40, 40, 40));
        d.fillRectangle(110, 200, 12, 220);

        // light on top
        d.setColor(Color.ORANGE);
        d.fillCircle(116, 190, 14);
        d.setColor(Color.RED);
        d.fillCircle(116, 190, 6);
    }

    @Override
    public void timePassed() {
        // no-op
    }
}
