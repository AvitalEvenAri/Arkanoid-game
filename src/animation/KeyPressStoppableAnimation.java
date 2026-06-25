package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * A decorator Animation that stops when a specific key is pressed.
 * Fixes the "same press closes next animation" bug using isAlreadyPressed.
 */
public class KeyPressStoppableAnimation implements Animation {

    private final KeyboardSensor sensor;
    private final String key;
    private final Animation animation;

    private boolean stop;
    private boolean isAlreadyPressed;

    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;

        this.stop = false;
        this.isAlreadyPressed = true; // critical bug-fix: ignore key if it was already down
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // 1) draw the wrapped animation
        this.animation.doOneFrame(d);

        // 2) bug fix logic:
        // if key is currently NOT pressed, we can allow stopping on next press
        if (!this.sensor.isPressed(this.key)) {
            this.isAlreadyPressed = false;
            return;
        }

        // 3) if key is pressed but it was already pressed when animation started -> ignore
        if (this.isAlreadyPressed) {
            return;
        }

        // 4) key is pressed AND it was not pressed earlier during this animation -> stop
        this.stop = true;
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
