package listeners;

import game.Counter;
import game.GameLevel;
import sprites.Ball;
import sprites.Block;

public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // 1. Remove this listener from the block
        beingHit.removeHitListener(this);

        // 2. Remove the block from the gameLevel
        beingHit.removeFromGame(this.gameLevel);

        // 3. Update the counter
        this.remainingBlocks.decrease(1);
    }
}