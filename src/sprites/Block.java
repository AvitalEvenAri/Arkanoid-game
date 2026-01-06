package sprites;

import biuoop.DrawSurface;
import collision.Collidable;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import listeners.HitListener;
import listeners.HitNotifier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle rectangle;

    // hit points
    private int hitPoints;

    // fill per hit-points (1..hitPoints)
    private final Map<Integer, BlockFill> fillsByHp;

    // optional stroke (border). null = no border
    private final Color strokeColor;

    private final List<HitListener> hitListeners = new ArrayList<>();
    private static final double EPS = 1e-7;

    /**
     * Constructor for the new IO-based blocks.
     */
    public Block(Rectangle rect, int hitPoints, Map<Integer, BlockFill> fillsByHp, Color strokeColor) {
        this.rectangle = rect;
        this.hitPoints = hitPoints;
        this.fillsByHp = new HashMap<>(fillsByHp);
        this.strokeColor = strokeColor;
    }

    /**
     * Backward-compatible constructor: one color block with 1 hit-point and black border.
     * (Keeps your old code working.)
     */
    public Block(Rectangle rect, Color color) {
        this.rectangle = rect;
        this.hitPoints = 1;
        this.fillsByHp = new HashMap<>();
        this.fillsByHp.put(1, new ColorFill(color));
        this.strokeColor = Color.BLACK; // matches your old drawRectangle black
    }

    public Block(Rectangle rect) {
        this(rect, Color.GRAY);
    }

    public int getHitPoints() {
        return this.hitPoints;
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

        // Decrease hit points (if still positive)
        if (this.hitPoints > 0) {
            this.hitPoints--;
        }

        // Notify listeners about the hit (after hp decrease)
        this.notifyHit(hitter);

        return new Velocity(dx, dy);
    }

    private void notifyHit(Ball hitter) {
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

        // Choose fill by current hit-points (if 0, use 1 as fallback)
        int key = Math.max(this.hitPoints, 1);

        BlockFill fill = fillsByHp.get(key);
        if (fill == null) {
            // fallback: try fill-1
            fill = fillsByHp.get(1);
        }
        if (fill != null) {
            fill.fill(d, x, y, w, h);
        }

        // draw stroke if exists
        if (strokeColor != null) {
            d.setColor(strokeColor);
            d.drawRectangle(x, y, w, h);
        }
    }

    @Override
    public void timePassed() {
        // no-op
    }

    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
        gameLevel.removeCollidable(this);
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
