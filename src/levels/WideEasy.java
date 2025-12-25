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

    @Override
    public int numberOfBalls() {
        return 10;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> v = new ArrayList<>();
        // 10 balls, different angles
        v.add(new Velocity(-5, -3));
        v.add(new Velocity(-4, -4));
        v.add(new Velocity(-3, -5));
        v.add(new Velocity(-2, -5));
        v.add(new Velocity(-1, -5));
        v.add(new Velocity(1, -5));
        v.add(new Velocity(2, -5));
        v.add(new Velocity(3, -5));
        v.add(new Velocity(4, -4));
        v.add(new Velocity(5, -3));
        return v;
    }

    @Override
    public int paddleSpeed() {
        return 6;
    }

    @Override
    public int paddleWidth() {
        return 600;
    }

    @Override
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public Sprite getBackground() {
        return new Background(Color.WHITE);
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int y = 250;
        int blockW = 50;
        int blockH = 25;

        // 15 blocks across the screen (between borders 20..780)
        int startX = 20;
        Color[] colors = {
                Color.RED, Color.RED,
                Color.ORANGE, Color.ORANGE,
                Color.YELLOW, Color.YELLOW,
                Color.GREEN, Color.GREEN, Color.GREEN,
                Color.BLUE, Color.BLUE,
                Color.PINK, Color.PINK,
                Color.CYAN, Color.CYAN
        };

        for (int i = 0; i < 15; i++) {
            int x = startX + i * blockW;
            Block b = new Block(new Rectangle(new Point(x, y), blockW, blockH), colors[i]);
            blocks.add(b);
        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 15;
    }
}
