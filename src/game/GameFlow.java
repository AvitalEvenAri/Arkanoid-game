package game;
import java.io.File;

import animation.AnimationRunner;
import animation.EndScreen;
import animation.KeyPressStoppableAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import highscores.HighScoresTable;
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

        EndScreen end = new EndScreen(won, this.score);
        KeyPressStoppableAnimation endWithKey =
                new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, end);
        this.runner.run(endWithKey);




// Part 1: update highest score in highscores.txt
        HighScoresTable table = new HighScoresTable();

// נבדוק אם זו הרצה ראשונה (אין קובץ עדיין)
        File f = new File("highscores.txt");
        boolean firstRun = !f.exists();

// טוענים שיא קודם (אם אין קובץ -> table יחזיק 0)
        table.loadFromFile();

// הניקוד של המשחק הנוכחי
        int currentScore = this.score.getValue();

// אם זו הרצה ראשונה: יוצרים את הקובץ בכל מקרה (גם אם 0)
// אחרת: מעדכנים רק אם נשבר שיא
        if (firstRun) {
            table.updateIfHigher(currentScore);
            table.saveToFile();
        } else {
            boolean updated = table.updateIfHigher(currentScore);
            if (updated) {
                table.saveToFile();
            }
        }

        this.gui.close();


}
    }