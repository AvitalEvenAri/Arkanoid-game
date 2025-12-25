package levels;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Block;
import sprites.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class WideEasy implements LevelInformation {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public int numberOfBalls() {
        return 10;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();

        // קשת של כדורים: זוויות מ- -50 עד +50, מהירות קבועה 5
        int count = numberOfBalls();
        for (int i = 0; i < count; i++) {
            double angle = -50 + (100.0 * i) / (count - 1); // -50..+50
            velocities.add(Velocity.fromAngleAndSpeed(angle, 5));
        }

        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return 7;
    }

    @Override
    public int paddleWidth() {
        return 600; // משוט רחב מאוד כמו בתמונה
    }

    @Override
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public Sprite getBackground() {
        return new WideEasyBackground();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        // שורה אחת ארוכה של בלוקים צבעוניים
        int blockW = 50;
        int blockH = 20;

        int y = 250;          // גובה בערך כמו בתמונה (מתחת לשמש)
        int startX = 20;      // אחרי הקיר השמאלי (20px)

        // 15 בלוקים של 50px -> 750px, נכנס יפה בין 20..770
        Color[] colors = {
                Color.RED, Color.RED,
                Color.ORANGE, Color.ORANGE,
                Color.YELLOW, Color.YELLOW, Color.YELLOW,
                Color.GREEN, Color.GREEN, Color.GREEN,
                Color.BLUE, Color.BLUE,
                Color.PINK, Color.PINK,
                Color.CYAN
        };

        for (int i = 0; i < colors.length; i++) {
            int x = startX + i * blockW;
            Block b = new Block(new Rectangle(new Point(x, y), blockW, blockH), colors[i]);
            blocks.add(b);
        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }
}
