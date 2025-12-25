package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

// from other packages
import geometry.Velocity;
import collision.Collidable;

/**
 * sprites.Paddle: the player-controlled bar at the bottom.
 * It is both a sprites.Sprite (drawn & updated each frame)
 * and a collision.Collidable (the ball can hit it).
 */
public class Paddle implements Sprite, Collidable {

    // --- constants ---
    private static final double EPS = 1e-7;

    // --- fields ---
    private Rectangle rect;            // paddle shape & position
    private Color color;               // paddle color
    private KeyboardSensor keyboard;   // for reading left/right keys
    private double speed;              // how many pixels per frame to move
    private double leftLimit;          // x of left boundary
    private double rightLimit;         // x of right boundary

    // --- constructor ---
    public Paddle(Rectangle rect,
                  Color color,
                  KeyboardSensor keyboard,
                  double speed,
                  double leftLimit,
                  double rightLimit) {
        this.rect = rect;
        this.color = color;
        this.keyboard = keyboard;
        this.speed = speed;
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }

    // --- movement helpers ---

    // move paddle left by 'speed' pixels (but not beyond leftLimit)
    public void moveLeft() {
        double x = rect.getUpperLeft().getX();
        double y = rect.getUpperLeft().getY();
        double newX = x - speed;

        if (newX < leftLimit) {
            newX = leftLimit;
        }
        this.rect = new Rectangle(new Point(newX, y),
                rect.getWidth(), rect.getHeight());
    }

    // move paddle right by 'speed' pixels (but not beyond rightLimit)
    public void moveRight() {
        double x = rect.getUpperLeft().getX();
        double y = rect.getUpperLeft().getY();
        double newX = x + speed;

        // ensure the RIGHT side of the paddle does not cross rightLimit
        double paddleRight = newX + rect.getWidth();
        if (paddleRight > rightLimit) {
            newX = rightLimit - rect.getWidth();
        }

        this.rect = new Rectangle(new Point(newX, y),
                rect.getWidth(), rect.getHeight());
    }

    // --- sprites.Sprite implementation ---

    @Override
    public void timePassed() {
        // if left arrow is pressed -> move left
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        // if right arrow is pressed -> move right
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        int x = (int) rect.getUpperLeft().getX();
        int y = (int) rect.getUpperLeft().getY();
        int w = (int) rect.getWidth();
        int h = (int) rect.getHeight();

        d.setColor(this.color);
        d.fillRectangle(x, y, w, h);

        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, w, h);
    }

    // --- collision.Collidable implementation ---

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // Paddle borders
        double leftX   = rect.getUpperLeft().getX();
        double rightX  = leftX + rect.getWidth();
        double topY    = rect.getUpperLeft().getY();
        double bottomY = topY + rect.getHeight();

        boolean hitTop    = Math.abs(collisionPoint.getY() - topY) < EPS;
        boolean hitBottom = Math.abs(collisionPoint.getY() - bottomY) < EPS;
        boolean hitLeft   = Math.abs(collisionPoint.getX() - leftX) < EPS;
        boolean hitRight  = Math.abs(collisionPoint.getX() - rightX) < EPS;

        // 1. Hit on the TOP edge → 5 regions with special angles
        if (hitTop) {
            double regionWidth = rect.getWidth() / 5.0;
            double hitX = collisionPoint.getX() - leftX;  // distance from left edge
            int region = (int) (hitX / regionWidth);      // 0..4

            if (region < 0) {
                region = 0;
            }
            if (region > 4) {
                region = 4;
            }

            double speed = currentVelocity.getSpeed();
            double angle;

            // regions: 0..4  → angles: 300,330,0,30,60
            switch (region) {
                case 0:
                    angle = 300;
                    break;
                case 1:
                    angle = 330;
                    break;
                case 2:
                    angle = 0;
                    break;
                case 3:
                    angle = 30;
                    break;
                case 4:
                default:
                    angle = 60;
                    break;
            }

            return Velocity.fromAngleAndSpeed(angle, speed);
        }

        // 2. If (for some reason) we hit sides or bottom → behave like a regular block
        if (hitLeft || hitRight) {
            dx = -dx;
        }
        if (hitBottom) {
            dy = -dy;
        }

        return new Velocity(dx, dy);
    }



    private boolean almostEquals(double a, double b) {
        return Math.abs(a - b) < EPS;
    }

    // --- helper to add paddle to game ---

    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
