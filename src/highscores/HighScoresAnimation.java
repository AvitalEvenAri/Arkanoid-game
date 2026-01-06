package highscores;

import animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

public class HighScoresAnimation implements Animation {

    private final HighScoresTable table;
    private final KeyboardSensor keyboard;
    private boolean stop;

    public HighScoresAnimation(HighScoresTable table, KeyboardSensor keyboard) {
        this.table = table;
        this.keyboard = keyboard;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 600);

        d.setColor(Color.BLACK);
        d.drawText(260, 120, "High Score", 40);

        d.drawText(200, 220, "The highest score so far is: " + table.getHighScore(), 26);
        d.drawText(160, 520, "Press SPACE to return to menu", 22);

        if (keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
