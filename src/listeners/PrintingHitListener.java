package listeners;

import sprites.Block;
import sprites.Ball;

/**
 * Simple listener that prints a message every time a block is hit.
 */
public class PrintingHitListener implements HitListener {

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block was hit.");
    }
}
