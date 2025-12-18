package listeners;

import game.Counter;
import game.Game;
import sprites.Ball;
import sprites.Block;

public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    public BlockRemover(Game game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // 1. Remove this listener from the block
        beingHit.removeHitListener(this);

        // 2. Remove the block from the game
        beingHit.removeFromGame(this.game);

        // 3. Update the counter
        this.remainingBlocks.decrease(1);
    }
}