package tasks;

import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;
import highscores.HighScoresAnimation;
import highscores.HighScoresTable;

public class ShowHighScoresTask implements Task<Void> {

    private final AnimationRunner runner;
    private final KeyboardSensor keyboard;
    private final HighScoresTable table;

    public ShowHighScoresTask(AnimationRunner runner, KeyboardSensor keyboard, HighScoresTable table) {
        this.runner = runner;
        this.keyboard = keyboard;
        this.table = table;
    }

    @Override
    public Void run() {
        // טוענים תמיד כדי להציג את הערך העדכני מהקובץ
        table.loadFromFile();

        HighScoresAnimation anim = new HighScoresAnimation(table, keyboard);

        // עוטפים כדי שהמסך ייסגר רק בלחיצה על SPACE
        KeyPressStoppableAnimation stoppable =
                new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY, anim);

        runner.run(stoppable);
        return null;
    }
}
