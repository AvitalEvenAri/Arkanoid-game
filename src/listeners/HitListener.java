package listeners;

import sprites.Ball;
import sprites.Block;

public interface HitListener {
    // called whenever a block is hit
    void hitEvent(Block beingHit, Ball hitter);
}
