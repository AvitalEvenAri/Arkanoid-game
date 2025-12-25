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

    public Counter getScore() {
        return this.score;
    }

    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation info : levels) {

            // 1) create level
            GameLevel level = new GameLevel(info, this.keyboard, this.runner, this.score);

            // 2) build objects according to level
            level.initialize();

            // 3) run the level ONCE (GameLevel.run() already loops internally until stop)
            level.run();

            // 4) if lost (no balls) -> end whole game
            if (level.getRemainingBalls() == 0) {
                // TODO later: show Game Over screen
                return;
            }

            // 5) else: cleared blocks -> for-loop continues to next level
        }

        // TODO later: show You Win screen
    }
}
