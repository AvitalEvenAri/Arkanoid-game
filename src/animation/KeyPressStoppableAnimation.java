package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class KeyPressStoppableAnimation implements Animation {
    private final KeyboardSensor keyboard;
    private final String key;
    private final Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    public KeyPressStoppableAnimation(KeyboardSensor keyboard, String key, Animation animation) {
        this.keyboard = keyboard;
        this.key = key;
        this.animation = animation;
        this.stop = false;

        // חשוב: אם המקש כבר לחוץ כשנכנסים למסך, לא נסגור מיד.
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // קודם מציירים את האנימציה המקורית (EndScreen וכו')
        this.animation.doOneFrame(d);

        // אם המקש לא לחוץ עכשיו -> אפשר "להפעיל" את האפשרות לעצור
        if (!this.keyboard.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }

        // אם המקש לחוץ, ורק אחרי שהוא שוחרר פעם אחת -> לעצור
        if (!this.isAlreadyPressed && this.keyboard.isPressed(this.key)) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
