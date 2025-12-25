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

    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> v = new ArrayList<>();
        v.add(new Velocity(3, -4));
        v.add(new Velocity(-3, -4));
        return v;
    }

    @Override
    public int paddleSpeed() {
        return 7;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Green 3";
    }

    @Override
    public Sprite getBackground() {
        return new Background(new Color(0, 140, 0));
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int blockW = 50;
        int blockH = 25;

        int rows = 5;
        int blocksInRow = 10;

        int startY = 150;

        Color[] rowColors = {
                Color.GRAY,
                Color.RED,
                Color.YELLOW,
                Color.BLUE,
                Color.WHITE
        };

        for (int row = 0; row < rows; row++) {
            int y = startY + row * blockH;
            int startX = 800 - 20 - blocksInRow * blockW; // align to the right side (before border)

            for (int i = 0; i < blocksInRow; i++) {
                int x = startX + i * blockW;
                Block b = new Block(new Rectangle(new Point(x, y), blockW, blockH), rowColors[row]);
                blocks.add(b);
            }
        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 5 * 10;
    }
}
