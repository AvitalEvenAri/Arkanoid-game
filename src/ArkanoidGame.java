import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import levels.LevelInformation;
import levels.DirectHit;
import levels.WideEasy;
import levels.Green3;
import levels.FinalFour;

import java.util.ArrayList;
import java.util.List;

public class ArkanoidGame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {

        // 1) Create ONE GUI for the entire game
        GUI gui = new GUI("Arkanoid", WIDTH, HEIGHT);

        // 2) Get ONE keyboard sensor from that GUI
        KeyboardSensor keyboard = gui.getKeyboardSensor();

        // 3) Create ONE animation runner for the entire game
        AnimationRunner runner = new AnimationRunner(gui, 60);

        // 4) Build levels list based on args
        List<LevelInformation> levelsToRun = buildLevelsFromArgs(args);

        // 5) Run the game flow (keeps score across levels + shows end screen)
        GameFlow flow = new GameFlow(runner, keyboard, gui);
        flow.runLevels(levelsToRun);

        // 6) Safety: if flow returns for any reason, close the GUI
        gui.close();
    }

    /**
     * If args are empty -> run all 4 levels (1..4).
     * If args exist -> treat them as level numbers, keep order, ignore invalid ones.
     */
    private static List<LevelInformation> buildLevelsFromArgs(String[] args) {
        List<LevelInformation> levels = new ArrayList<>();

        // No args => default: all levels in order
        if (args == null || args.length == 0) {
            levels.add(new DirectHit());  // level 1
            levels.add(new WideEasy());   // level 2
            levels.add(new Green3());     // level 3
            levels.add(new FinalFour());  // level 4
            return levels;
        }

        // With args => parse each, ignore invalid
        for (String s : args) {
            Integer levelNum = tryParseInt(s);
            if (levelNum == null) {
                continue; // not a number => ignore
            }

            LevelInformation info = levelFromNumber(levelNum);
            if (info != null) {
                levels.add(info); // valid 1..4
            }
        }

        // If everything was invalid => fallback to all levels
        if (levels.isEmpty()) {
            levels.add(new DirectHit());
            levels.add(new WideEasy());
            levels.add(new Green3());
            levels.add(new FinalFour());
        }

        return levels;
    }

    /**
     * Returns LevelInformation by number:
     * 1 -> DirectHit, 2 -> WideEasy, 3 -> Green3, 4 -> FinalFour.
     * If out of range -> null.
     */
    private static LevelInformation levelFromNumber(int n) {
        switch (n) {
            case 1:
                return new DirectHit();
            case 2:
                return new WideEasy();
            case 3:
                return new Green3();
            case 4:
                return new FinalFour();
            default:
                return null;
        }
    }

    /**
     * Parse int safely. If not parsable -> return null.
     */
    private static Integer tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return null;
        }
    }
}
