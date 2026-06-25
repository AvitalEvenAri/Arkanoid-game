package animation;

import biuoop.DrawSurface;

/**
 * Pause screen: draws "paused" forever.
 * Stopping is handled by KeyPressStoppableAnimation.
 */
public class PauseScreen implements Animation {

    public PauseScreen() {
        // no fields needed
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    @Override
    public boolean shouldStop() {
        return false; // runs forever, decorator stops it
    }
}
