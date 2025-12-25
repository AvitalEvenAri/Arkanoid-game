package levels;

import geometry.Velocity;
import sprites.Block;
import sprites.Sprite;

import java.util.List;

public interface LevelInformation {
    int numberOfBalls();
    List<Velocity> initialBallVelocities();

    int paddleSpeed();
    int paddleWidth();

    String levelName();
    Sprite getBackground();

    List<Block> blocks();
    int numberOfBlocksToRemove();
}
