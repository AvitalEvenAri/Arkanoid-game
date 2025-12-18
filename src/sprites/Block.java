package sprites;

import biuoop.DrawSurface;
import collision.Collidable;
import game.Game;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import listeners.HitListener;
import listeners.HitNotifier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle rectangle;
    private Color color;
    private List<HitListener> hitListeners = new ArrayList<>();
    private static final double EPS = 1e-7;

    public Block(Rectangle rect, Color color) {
        this.rectangle = rect;
        this.color = color;
    }

    public Block(Rectangle rect) {
        this(rect, Color.GRAY);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        double leftX = rectangle.getUpperLeft().getX();
        double rightX = leftX + rectangle.getWidth();
        double topY = rectangle.getUpperLeft().getY();
        double bottomY = topY + rectangle.getHeight();

        // Check which side was hit
        boolean hitLeft = Math.abs(collisionPoint.getX() - leftX) < EPS;
        boolean hitRight = Math.abs(collisionPoint.getX() - rightX) < EPS;
        boolean hitTop = Math.abs(collisionPoint.getY() - topY) < EPS;
        boolean hitBottom = Math.abs(collisionPoint.getY() - bottomY) < EPS;

        if (hitLeft || hitRight) {
            dx = -dx;
        }
        if (hitTop || hitBottom) {
            dy = -dy;
        }

        // Notify listeners about the hit
        this.notifyHit(hitter);

        return new Velocity(dx, dy);
    }

    private void notifyHit(Ball hitter) {
        // CRITICAL FIX: Make a copy of the hitListeners before iterating.
        // This is needed because a listener (like BlockRemover) might remove
        // itself from the list inside the loop.
        List<HitListener> listenersCopy = new ArrayList<>(this.hitListeners);

        for (HitListener hl : listenersCopy) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        int x = (int) rectangle.getUpperLeft().getX();
        int y = (int) rectangle.getUpperLeft().getY();
        int w = (int) rectangle.getWidth();
        int h = (int) rectangle.getHeight();

        d.setColor(this.color);
        d.fillRectangle(x, y, w, h);
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, w, h);
    }

    @Override
    public void timePassed() {
        // Currently does nothing
    }

    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    public void removeFromGame(Game game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}