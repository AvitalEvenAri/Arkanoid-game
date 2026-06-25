package listeners;

import game.Counter;
import game.GameLevel;
import sprites.Ball;
import sprites.Block;

/**
 * A BallRemover is in charge of removing balls from the gameLevel,
 * as well as keeping count of the remaining balls.
 */
public class BallRemover implements HitListener {

    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Construct a BallRemover that works on a given gameLevel and counter.
     *
     * @param gameLevel          the gameLevel from which balls will be removed.
     * @param remainingBalls the counter that tracks how many balls still exist.
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }

    /**
     * This method is called whenever a ball hits the "death-region" block.
     * The ball should be removed from the gameLevel, and the counter decreased.
     *
     * @param beingHit the block that was hit (the death-region).
     * @param hitter   the ball that hit the block.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Remove the ball from the gameLevel
        hitter.removeFromGame(this.gameLevel);

        // Decrease the remaining balls counter
        this.remainingBalls.decrease(1);
    }
}
