package tasks;

import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import highscores.HighScoresTable;
import levels.LevelInformation;

import java.util.List;

public class StartGameTask implements Task<Void> {

    private final AnimationRunner runner;
    private final KeyboardSensor keyboard;
    private final GUI gui;
    private final List<LevelInformation> levels;
    private final HighScoresTable highScores;

    public StartGameTask(AnimationRunner runner,
                         KeyboardSensor keyboard,
                         GUI gui,
                         List<LevelInformation> levels,
                         HighScoresTable highScores) {
        this.runner = runner;
        this.keyboard = keyboard;
        this.gui = gui;
        this.levels = levels;
        this.highScores = highScores;
    }

    @Override
    public Void run() {
        // מריצים משחק אחד
        GameFlow flow = new GameFlow(runner, keyboard, gui);
        flow.runLevels(levels);

        // חשוב: GameFlow כבר מטפל בעדכון HighScore אצלך (Part 1)
        // ולכן פה לא צריך לגעת בזה.
        return null;
    }
}
