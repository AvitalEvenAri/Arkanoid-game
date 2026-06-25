package sprites;

import biuoop.DrawSurface;

/**
 * A sprites.Sprite is a game object that can be drawn on the screen,
 * and can be notified that time has passed.
 */
public interface Sprite {

    /**
     * Draw the sprite on the given surface.
     *
     * @param d the surface to draw on
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that a unit of time has passed.
     * (For example: move, change color, etc.)
     */
    void timePassed();
}
