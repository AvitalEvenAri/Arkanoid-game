package game;

import animation.AnimationRunner;
import biuoop.KeyboardSensor;
import levels.LevelInformation;

import java.util.List;

public class GameFlow {
    private final AnimationRunner runner;
    private final KeyboardSensor keyboard;
    private final Counter score;

    public GameFlow(AnimationRunner runner, KeyboardSensor keyboard) {
        this.runner = runner;
        this.keyboard = keyboard;
        this.score = new Counter(0);
    }

    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation info : levels) {
            GameLevel level = new GameLevel(info, this.keyboard, this.runner, this.score);
            level.initialize();

            // run the level (one turn) until cleared or lost
            while (level.getRemainingBlocks() > 0 && level.getRemainingBalls() > 0) {
                level.run();
            }

            // if lost (no balls) -> stop whole game
            if (level.getRemainingBalls() == 0) {
                // later: show Game Over screen
                return;
            }
        }

        // later: show You Win screen
    }
}
