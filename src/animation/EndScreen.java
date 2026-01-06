package animation;

import biuoop.DrawSurface;
import game.Counter;

/**
 * EndScreen shows the final message forever.
 * Stopping is handled by KeyPressStoppableAnimation (SPACE).
 */
public class EndScreen implements Animation {

    private final boolean won;
    private final Counter score;

    public EndScreen(boolean won, Counter score) {
        this.won = won;
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        String msg;
        if (won) {
            msg = "You Win! Your score is " + score.getValue();
        } else {
            msg = "Game Over. Your score is " + score.getValue();
        }

        d.drawText(100, d.getHeight() / 2, msg, 32);
        d.drawText(100, d.getHeight() / 2 + 40, "Press SPACE to exit", 20);
    }

    @Override
    public boolean shouldStop() {
        return false; // decorator stops it
    }
}
