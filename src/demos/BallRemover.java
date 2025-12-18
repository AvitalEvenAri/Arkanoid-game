package demos;

import game.Counter;
import game.Game;
import listeners.HitListener;
import sprites.Ball;
import sprites.Block;

/**
 * A BallRemover is in charge of removing balls from the game,
 * as well as keeping count of the remaining balls.
 */
public class BallRemover implements HitListener {

    private Game game;
    private Counter remainingBalls;

    /**
     * Construct a BallRemover that works on a given game and counter.
     *
     * @param game          the game from which balls will be removed.
     * @param remainingBalls the counter that tracks how many balls still exist.
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * This method is called whenever a ball hits the "death-region" block.
     * The ball should be removed from the game, and the counter decreased.
     *
     * @param beingHit the block that was hit (the death-region).
     * @param hitter   the ball that hit the block.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Remove the ball from the game
        hitter.removeFromGame(this.game);

        // Decrease the remaining balls counter
        this.remainingBalls.decrease(1);
    }
}
