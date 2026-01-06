package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class KeyPressStoppableAnimation implements Animation {
    private final KeyboardSensor keyboard;
    private final String key;
    private final Animation animation;

    private boolean stop;

    // מספר פריימים להתעלמות בתחילת המסך (כדי לא לסגור מיד)
    private int framesToIgnore;

    public KeyPressStoppableAnimation(KeyboardSensor keyboard, String key, Animation animation) {
        this.keyboard = keyboard;
        this.key = key;
        this.animation = animation;
        this.stop = false;

        // בערך 0.25 שניות ב-60 FPS
        this.framesToIgnore = 15;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        animation.doOneFrame(d);

        // מתעלמים מהפריימים הראשונים כדי "לנקות" מעבר מסך
        if (framesToIgnore > 0) {
            framesToIgnore--;
            return;
        }

        // אחרי ההמתנה הקצרה: כל לחיצה על המקש עוצרת
        if (keyboard.isPressed(key)) {
            stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }
}
