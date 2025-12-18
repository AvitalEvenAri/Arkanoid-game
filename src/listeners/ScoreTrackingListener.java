package listeners;

import game.Counter;
import sprites.Ball;
import sprites.Block;

/**
 * ScoreTrackingListener:
 * increases the score whenever a block is hit.
 */
public class ScoreTrackingListener implements HitListener {

    // reference to the score counter in the game
    private Counter currentScore;

    /**
     * Construct a score-tracking listener.
     *
     * @param scoreCounter the counter that holds the current score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Called whenever a block is hit by a ball.
     * Each hit is worth 5 points.
     *
     * @param beingHit the block that was hit.
     * @param hitter   the ball that hit the block.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // add 5 points for every hit on a block
        this.currentScore.increase(5);
    }
}
