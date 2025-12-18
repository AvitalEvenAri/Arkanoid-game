package demos;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import collision.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;
import sprites.Block;

import java.awt.Color;

public class CollisionDemo {

    public static void main(String[] args) {

        // 1) Create GUI
        GUI gui = new GUI("Collision Demo", 800, 600);
        Sleeper sleeper = new Sleeper();

        // 2) Create collision.GameEnvironment
        GameEnvironment env = new GameEnvironment();

        // 3) Create some blocks in the middle
        Block b1 = new Block(new Rectangle(new Point(200, 150), 100, 30), Color.RED);
        Block b2 = new Block(new Rectangle(new Point(400, 250), 150, 30), Color.BLUE);
        Block b3 = new Block(new Rectangle(new Point(300, 400), 80, 30), Color.GREEN);

        env.addCollidable(b1);
        env.addCollidable(b2);
        env.addCollidable(b3);

        // 4) Create border blocks (VERY important!)
        // top border
        Block top = new Block(new Rectangle(new Point(0, 0), 800, 20), Color.GRAY);
        // bottom border
        Block bottom = new Block(new Rectangle(new Point(0, 580), 800, 20), Color.GRAY);
        // left border
        Block left = new Block(new Rectangle(new Point(0, 0), 20, 600), Color.GRAY);
        // right border
        Block right = new Block(new Rectangle(new Point(780, 0), 20, 600), Color.GRAY);

        env.addCollidable(top);
        env.addCollidable(bottom);
        env.addCollidable(left);
        env.addCollidable(right);

        // 5) Create the ball
        Ball ball = new Ball(new Point(100, 100), 10, Color.BLACK);
        ball.setVelocity(4, 3);
        ball.setGameEnvironment(env);

        // 6) Animation loop
        while (true) {
            DrawSurface d = gui.getDrawSurface();

            // Draw blocks
            b1.drawOn(d);
            b2.drawOn(d);
            b3.drawOn(d);

            top.drawOn(d);
            bottom.drawOn(d);
            left.drawOn(d);
            right.drawOn(d);

            // Move & draw ball
            ball.moveOneStep();
            ball.drawOn(d);

            gui.show(d);
            sleeper.sleepFor(16); // ~60 FPS
        }
    }
}
