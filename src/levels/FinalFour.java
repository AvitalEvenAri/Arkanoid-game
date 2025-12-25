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

    @Override
    public int numberOfBalls() {
        return 3;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> v = new ArrayList<>();
        v.add(new Velocity(-4, -4));
        v.add(new Velocity(0, -5));
        v.add(new Velocity(4, -4));
        return v;
    }

    @Override
    public int paddleSpeed() {
        return 8;
    }

    @Override
    public int paddleWidth() {
        return 120;
    }

    @Override
    public String levelName() {
        return "Final Four";
    }

    @Override
    public Sprite getBackground() {
        return new Background(new Color(30, 144, 255));
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int blockW = 50;
        int blockH = 25;

        int rows = 7;
        int blocksPerRow = 15;

        int startY = 100;

        Color[] colors = {
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
            int startX = 20; // from left border

            for (int i = 0; i < blocksPerRow; i++) {
                int x = startX + i * blockW;
                Block b = new Block(new Rectangle(new Point(x, y), blockW, blockH), colors[row]);
                blocks.add(b);
            }
        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 7 * 15;
    }
}
