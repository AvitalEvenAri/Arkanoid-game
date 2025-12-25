package animation;

import biuoop.DrawSurface;
import sprites.SpriteCollection;

import java.awt.Color;

/**
 * CountdownAnimation displays the given game screen and draws a countdown
 * number on top of it. During the countdown, the game objects do NOT move.
 */
public class CountdownAnimation implements Animation {

    private final double numOfSeconds;
    private final int countFrom;
    private final SpriteCollection gameScreen;

    private final long millisecondsPerCount;
    private final long startTime;

    private boolean stop;

    /**
     * @param numOfSeconds how long the entire countdown lasts (e.g., 2.0 seconds)
     * @param countFrom    start counting from this number down to 1 (e.g., 3)
     * @param gameScreen   sprites to draw in the background (the game screen)
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;

        this.millisecondsPerCount = (long) ((numOfSeconds * 1000.0) / countFrom);
        this.startTime = System.currentTimeMillis();
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // 1) Draw background (use the same background as your game)
        d.setColor(new Color(0, 0, 150)); // dark blue
        d.fillRectangle(0, 0, 800, 600);

        // 2) Draw the game screen (sprites) WITHOUT updating timePassed()
        this.gameScreen.drawAllOn(d);

        // 3) Compute which number to show based on elapsed time
        long elapsed = System.currentTimeMillis() - this.startTime;
        int stepsPassed = (int) (elapsed / this.millisecondsPerCount);
        int currentNumber = this.countFrom - stepsPassed;

        // 4) If countdown finished, stop the animation
        if (currentNumber <= 0) {
            this.stop = true;
            return;
        }

        // 5) Draw the countdown number on top
        d.setColor(Color.WHITE);
        d.drawText(390, 320, Integer.toString(currentNumber), 80);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
