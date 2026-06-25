package animation;

import biuoop.DrawSurface;

/**
 * Win screen: draws message forever.
 * Stopping is handled by KeyPressStoppableAnimation.
 */
public class YouWinScreen implements Animation {

    private final int finalScore;

    public YouWinScreen(int finalScore) {
        this.finalScore = finalScore;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(100, d.getHeight() / 2, "You Win! Your score is " + this.finalScore, 32);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
