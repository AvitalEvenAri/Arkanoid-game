package levels;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Block;
import sprites.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class DirectHit implements LevelInformation {

    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> v = new ArrayList<>();
        // straight up (towards the single block)
        v.add(new Velocity(0, -5));
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
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        return new Background(Color.BLACK);
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int blockW = 40;
        int blockH = 40;

        // Center block near top
        int x = (800 - blockW) / 2;
        int y = 120;

        Block b = new Block(new Rectangle(new Point(x, y), blockW, blockH), Color.RED);
        blocks.add(b);

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }
}
