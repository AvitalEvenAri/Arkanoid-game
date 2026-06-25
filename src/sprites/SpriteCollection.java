package sprites;

import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

public class SpriteCollection {

    private List<Sprite> sprites = new ArrayList<>();

    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    // Call timePassed() on all sprites.
    public void notifyAllTimePassed() {
        // CRITICAL FIX: Create a copy of the list before iterating.
        // This prevents the game from crashing if a sprite removes itself
        // (like a block) during this loop.
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);

        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }

    // Call drawOn(d) on all sprites.
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }
}