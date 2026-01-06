package menu;

import animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class MenuAnimation<T> implements Menu<T> {

    // מחזיק אפשרות אחת בתפריט
    private static class Selection<T> {
        private final String key;
        private final String message;
        private final T returnVal;

        Selection(String key, String message, T returnVal) {
            this.key = key;
            this.message = message;
            this.returnVal = returnVal;
        }
    }

    private final String title;
    private final KeyboardSensor keyboard;
    private final List<Selection<T>> selections;

    private boolean stop;
    private T status;

    public MenuAnimation(String title, KeyboardSensor keyboard) {
        this.title = title;
        this.keyboard = keyboard;
        this.selections = new ArrayList<>();
        this.stop = false;
        this.status = null;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new Selection<>(key, message, returnVal));
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // רקע
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 600);

        // כותרת
        d.setColor(Color.BLACK);
        d.drawText(250, 120, this.title, 46);

        // רשימת אפשרויות
        int y = 230;
        for (Selection<T> s : selections) {
            d.drawText(220, y, "(" + s.key + ") " + s.message, 28);
            y += 55;
        }

        // בדיקת מקשים — אם לחצו על אחד המקשים, נשמור סטטוס ונעצור
        for (Selection<T> s : selections) {
            if (keyboard.isPressed(s.key)) {
                this.status = s.returnVal;
                this.stop = true;
                return;
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    @Override
    public T getStatus() {
        return this.status;
    }

    @Override
    public void reset() {
        this.stop = false;
        this.status = null;
    }
}
