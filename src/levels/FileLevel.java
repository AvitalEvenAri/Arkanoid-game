package levels;

import geometry.Velocity;
import sprites.Block;
import sprites.Sprite;

import java.util.List;

public class FileLevel implements LevelInformation {

    private final int numberOfBalls;
    private final List<Velocity> ballVelocities;
    private final int paddleSpeed;
    private final int paddleWidth;
    private final String levelName;
    private final Sprite background;
    private final List<Block> blocks;
    private final int numBlocksToRemove;

    public FileLevel(int numberOfBalls,
                     List<Velocity> ballVelocities,
                     int paddleSpeed,
                     int paddleWidth,
                     String levelName,
                     Sprite background,
                     List<Block> blocks,
                     int numBlocksToRemove) {

        this.numberOfBalls = numberOfBalls;
        this.ballVelocities = ballVelocities;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.levelName = levelName;
        this.background = background;
        this.blocks = blocks;
        this.numBlocksToRemove = numBlocksToRemove;
    }

    @Override
    public int numberOfBalls() {
        return numberOfBalls;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return ballVelocities;
    }

    @Override
    public int paddleSpeed() {
        return paddleSpeed;
    }

    @Override
    public int paddleWidth() {
        return paddleWidth;
    }

    @Override
    public String levelName() {
        return levelName;
    }

    @Override
    public Sprite getBackground() {
        return background;
    }

    @Override
    public List<Block> blocks() {
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return numBlocksToRemove;
    }
}
