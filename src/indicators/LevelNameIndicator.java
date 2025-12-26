package indicators;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;

/**
 * LevelNameIndicator:
 * Sprite that displays the current level name at the top bar.
 */
public class LevelNameIndicator implements Sprite {

    private final String levelName;

    public LevelNameIndicator(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // We assume the top bar is height 20, same as ScoreIndicator
        // We do NOT draw the bar itself here to avoid double painting.
        d.setColor(Color.BLACK);
        d.drawText(520, 15, "Level Name: " + this.levelName, 15);
    }

    @Override
    public void timePassed() {
        // no-op
    }
}
