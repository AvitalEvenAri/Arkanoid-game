package animation;

import biuoop.DrawSurface;
import game.Counter;

import java.awt.Color;

public class EndScreen implements Animation {

    private final boolean isWin;
    private final Counter score;

    public EndScreen(boolean isWin, Counter score) {
        this.isWin = isWin;
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // רקע שחור
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, 800, 600);

        // טקסט מרכזי
        d.setColor(Color.WHITE);

        String msg;
        if (isWin) {
            msg = "You Win! Your score is " + score.getValue();
        } else {
            msg = "Game Over. Your score is " + score.getValue();
        }

        d.drawText(150, 300, msg, 32);
        d.drawText(180, 360, "Press SPACE to exit", 24);
    }

    @Override
    public boolean shouldStop() {
        // לא נסגר לבד. מי שעוצר זה KeyPressStoppableAnimation.
        return false;
    }
}
