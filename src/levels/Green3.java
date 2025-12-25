package levels;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Block;
import sprites.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Green3 implements LevelInformation {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> v = new ArrayList<>();

        // שני כדורים בזוויות נגדיות (סטנדרטי לשלב הזה)
        v.add(Velocity.fromAngleAndSpeed(-30, 6));
        v.add(Velocity.fromAngleAndSpeed(30, 6));

        return v;
    }

    @Override
    public int paddleSpeed() {
        return 7;
    }

    @Override
    public int paddleWidth() {
        return 80;
    }

    @Override
    public String levelName() {
        return "Green 3";
    }

    @Override
    public Sprite getBackground() {
        return new Green3Background();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        // פירמידה/מדרגות של 6 שורות (כמו המטלה), מתחיל מימין
        int blockW = 50;
        int blockH = 20;

        int rows = 6;
        int yStart = 100; // מעל הפס האפור, כמו אצלך
        int startXRight = WIDTH - 20; // קיר ימין

        // צבעים לשורות (אפשר לשנות כדי להתאים בדיוק)
        Color[] rowColors = {
                Color.GRAY,
                Color.RED,
                Color.YELLOW,
                Color.BLUE,
                Color.WHITE,
                Color.PINK
        };

        for (int row = 0; row < rows; row++) {
            int blocksInRow = 10 - row; // 10,9,8,7,6,5 (מדרגות)
            int y = yStart + row * blockH;

            // ממלאים מימין לשמאל
            for (int i = 0; i < blocksInRow; i++) {
                int x = startXRight - (i + 1) * blockW; // עוד בלוק אחד שמאלה כל פעם
                Block b = new Block(
                        new Rectangle(new Point(x, y), blockW, blockH),
                        rowColors[row % rowColors.length]
                );
                blocks.add(b);
            }
        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }
}
