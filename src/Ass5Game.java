import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import io.LevelSpecificationReader;
import levels.LevelInformation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Ass5Game {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final String DEFAULT_LEVELS_FILE = "levels/campaign.txt";

    public static void main(String[] args) {
        String levelsPath = DEFAULT_LEVELS_FILE;

        if (args != null && args.length > 0 && args[0] != null && !args[0].isBlank()) {
            levelsPath = args[0];
        }

        List<LevelInformation> levels = loadLevelsFromResources(levelsPath);

        GUI gui = new GUI("Arkanoid", WIDTH, HEIGHT);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui, 60);

        GameFlow flow = new GameFlow(runner, keyboard, gui);
        flow.runLevels(levels);
    }

    private static List<LevelInformation> loadLevelsFromResources(String path) {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("Levels file not found in classpath: " + path);
        }

        LevelSpecificationReader reader = new LevelSpecificationReader();
        return reader.fromReader(new InputStreamReader(is));
    }
}
