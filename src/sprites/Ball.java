package sprites;

import biuoop.DrawSurface;
import collision.Collidable;
import collision.CollisionInfo;
import collision.GameEnvironment;
import game.GameLevel;
import geometry.Line;
import geometry.Velocity;

import java.awt.Color;

// from other packages
import geometry.Point;

public class Ball implements Sprite {
    // === Fields ===
    private Point center;   // the location of the ball
    private int radius;     // the radius (size) of the ball
    private Color color;    // the color of the ball
    // ---- NEW: velocity is stored here; default (0,0) so program won't crash ----
    private Velocity velocity = new Velocity(0, 0);
    private GameEnvironment environment;


    // === Constructors ===

    // Constructor that accepts a geometry.Point as the center
    public Ball(Point center, int r, Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }

    // Convenience constructor that accepts (x, y) instead of a geometry.Point
    public Ball(int x, int y, int r, Color color) {
        this(new Point(x, y), r, color);
    }

    // === Accessors (getters) ===

    // Return the x-coordinate as int (DrawSurface expects ints)
    public int getX() {
        return (int) this.center.getX();
    }

    // Return the y-coordinate as int
    public int getY() {
        return (int) this.center.getY();
    }

    // Return the size (radius) of the ball
    public int getSize() {
        return this.radius;
    }

    // Return the color of the ball
    public Color getColor() {
        return this.color;
    }

    /**
     * Set the game environment this ball moves in.
     *
     * @param environment the collision.GameEnvironment to use for collision checks.
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }


    // === Drawing ===

    // Draw the ball on the given DrawSurface
    // === sprites.Sprite: drawOn ===
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);


        surface.setColor(Color.BLACK);
        surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);

    }

    // === sprites.Sprite: timePassed ===
    @Override
    public void timePassed() {
        this.moveOneStep();
    }
    // === NEW: velocity API ===
    public void setVelocity(Velocity v) {
        if (v == null) return;
        this.velocity = v;
    }
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Move the ball one step, taking into account possible collisions
     * with objects in the collision.GameEnvironment.
     *
     * The movement algorithm:
     * 1) Compute trajectory from current center to where velocity would take us.
     * 2) Ask the game environment for the closest collision on this trajectory.
     * 3) If no collision: move to the end of the trajectory.
     * 4) Else:
     *    - move the ball to a point just before the collision point.
     *    - update velocity according to the hit object.
     */
    public void moveOneStep() {
        // If we have no velocity, nothing to do.
        if (this.velocity == null) {
            return;
        }

        // If we don't have an environment yet, just move straight.
        if (this.environment == null) {
            this.center = this.velocity.applyToPoint(this.center);
            return;
        }

        // 1) Compute the "intended" next position (if no collisions)
        Point currentCenter = this.center;
        Point nextCenter = this.velocity.applyToPoint(this.center);
        Line trajectory = new Line(currentCenter, nextCenter);

        // 2) Check for the closest collision along this trajectory
        CollisionInfo info = this.environment.getClosestCollision(trajectory);

        // 2.1) If there is no collision, simply move to nextCenter
        if (info == null) {
            this.center = nextCenter;
            return;
        }

        // 2.2) There is a hit:
        Point collisionPoint = info.collisionPoint();
        Collidable collisionObject = info.collisionObject();

        // Move to "almost" the hit point: a tiny step before the collision point
        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();

        // Small epsilon to step back from the collision point
        double epsilon = 0.1;
        double moveBackX = 0;
        double moveBackY = 0;

        if (dx > 0) {
            moveBackX = -epsilon;
        } else if (dx < 0) {
            moveBackX = epsilon;
        }

        if (dy > 0) {
            moveBackY = -epsilon;
        } else if (dy < 0) {
            moveBackY = epsilon;
        }

        this.center = new Point(collisionPoint.getX() + moveBackX,
                collisionPoint.getY() + moveBackY);

        // 2.4) Let the object decide the new velocity
        this.velocity = collisionObject.hit(this, collisionPoint, this.velocity);
    }


    // Move one step while bouncing inside given rectangle bounds:
    // left, top, right, bottom are the walls.
    public void moveOneStepInBounds(int left, int top, int right, int bottom) {
        double nextX = this.center.getX() + this.velocity.getDx();
        double nextY = this.center.getY() + this.velocity.getDy();

        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();

        // Check left / right walls (respect radius)
        if (nextX - this.radius < left) {
            nextX = left + this.radius;
            dx = -dx;
        } else if (nextX + this.radius > right) {
            nextX = right - this.radius;
            dx = -dx;
        }

        // Check top / bottom walls
        if (nextY - this.radius < top) {
            nextY = top + this.radius;
            dy = -dy;
        } else if (nextY + this.radius > bottom) {
            nextY = bottom - this.radius;
            dy = -dy;
        }

        // Apply the updated position and velocity
        this.center = new Point(nextX, nextY);
        this.velocity = new Velocity(dx, dy);
    }

    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
    /**
     * Remove this ball from the given gameLevel.
     *
     * @param gameLevel the gameLevel from which the ball should be removed.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }




}

