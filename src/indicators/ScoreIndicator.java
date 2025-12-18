package indicators;

import biuoop.DrawSurface;
import game.Counter;
import sprites.Sprite;

import java.awt.Color;

/**
 * ScoreIndicator:
 * a sprite that displays the current score at the top of the screen.
 */
public class ScoreIndicator implements Sprite {

    // reference to the score counter
    private Counter score;

    /**
     * Construct a new ScoreIndicator.
     *
     * @param score the counter that holds the current score value.
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // background bar at the top
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 20);

        // frame
        d.setColor(Color.BLACK);
        d.drawRectangle(0, 0, 800, 20);

        // draw the text "Score: X"
        d.drawText(350, 15, "Score: " + this.score.getValue(), 15);
    }

    @Override
    public void timePassed() {
        // no animation logic: the value changes via Counter,
        // we just draw it every frame.
    }
}
