package game;

import animation.AnimationRunner;
import animation.EndScreen;
import animation.KeyPressStoppableAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.LevelInformation;

import java.util.List;

public class GameFlow {
    private final AnimationRunner runner;
    private final KeyboardSensor keyboard;
    private final Counter score;
    private final GUI gui;

    public GameFlow(AnimationRunner runner, KeyboardSensor keyboard, GUI gui) {
        this.runner = runner;
        this.keyboard = keyboard;
        this.gui = gui;
        this.score = new Counter(0);
    }

    public void runLevels(List<LevelInformation> levels) {
        boolean won = true; // נניח שננצח, ואם נפסיד נשנה ל-false

        for (LevelInformation info : levels) {
            GameLevel level = new GameLevel(info, this.keyboard, this.runner, this.score);
            level.initialize();

            // מריצים את השלב עד שייגמר (בלוקים או כדורים)
            while (level.getRemainingBlocks() > 0 && level.getRemainingBalls() > 0) {
                level.run();
            }

            // אם אין כדורים - הפסד, נגמר המשחק
            if (level.getRemainingBalls() == 0) {
                won = false;
                break;
            }
        }

        // בסוף: להציג EndScreen בהתאם ל-won
        EndScreen end = new EndScreen(won, this.score);

        // לעטוף כדי שייסגר רק אחרי SPACE
        KeyPressStoppableAnimation endWithKey =
                new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, end);

        this.runner.run(endWithKey);

        // אחרי שלחצו SPACE -> לסיים תוכנה
        this.gui.close();
    }
}
