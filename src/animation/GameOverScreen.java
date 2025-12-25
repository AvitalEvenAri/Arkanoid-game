package animation;

import biuoop.DrawSurface;

/**
 * Game over screen: draws message forever.
 * Stopping is handled by KeyPressStoppableAnimation.
 */
public class GameOverScreen implements Animation {

    private final int finalScore;

    public GameOverScreen(int finalScore) {
        this.finalScore = finalScore;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(100, d.getHeight() / 2, "Game Over. Your score is " + this.finalScore, 32);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
