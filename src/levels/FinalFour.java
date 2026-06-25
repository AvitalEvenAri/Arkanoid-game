package levels;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Block;
import sprites.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class FinalFour implements LevelInformation {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public int numberOfBalls() {
        return 3;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> v = new ArrayList<>();

        // 3 כדורים: אחד ישר, שניים לצדדים
        v.add(Velocity.fromAngleAndSpeed(0, 6));
        v.add(Velocity.fromAngleAndSpeed(-25, 6));
        v.add(Velocity.fromAngleAndSpeed(25, 6));

        return v;
    }

    @Override
    public int paddleSpeed() {
        return 8;
    }

    @Override
    public int paddleWidth() {
        return 80;
    }

    @Override
    public String levelName() {
        return "Final Four";
    }

    @Override
    public Sprite getBackground() {
        return new FinalFourBackground();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        // רשת של בלוקים: הרבה שורות, צבע לכל שורה
        int blockW = 50;
        int blockH = 20;

        int rows = 7;
        int cols = 15; // 15*50=750 נכנס בין הקירות (20..770)

        int startX = 20;  // אחרי הקיר השמאלי
        int startY = 100; // מתחת לפס האפור

        Color[] rowColors = {
                Color.GRAY,
                Color.RED,
                Color.YELLOW,
                Color.GREEN,
                Color.WHITE,
                Color.PINK,
                Color.CYAN
        };

        for (int row = 0; row < rows; row++) {
            int y = startY + row * blockH;
            Color c = rowColors[row % rowColors.length];

            for (int col = 0; col < cols; col++) {
                int x = startX + col * blockW;

                Block b = new Block(
                        new Rectangle(new Point(x, y), blockW, blockH),
                        c
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
