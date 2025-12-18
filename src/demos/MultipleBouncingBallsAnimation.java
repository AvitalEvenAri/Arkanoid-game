package demos;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import geometry.Velocity;
import sprites.Ball;

import java.awt.Color;
import java.util.Random;

public class MultipleBouncingBallsAnimation {

    // Creates a single ball with:
    // - given radius
    // - random position inside the screen (respecting radius)
    // - random color
    // - velocity depending on radius (larger -> slower)
    private static Ball createBall(int radius, int screenWidth, int screenHeight, Random rand) {
        // Ensure the ball is fully inside the screen:
        int x = radius + rand.nextInt(screenWidth - 2 * radius);
        int y = radius + rand.nextInt(screenHeight - 2 * radius);

        // Random color for fun
        Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        Ball ball = new Ball(x, y, radius, color);

        // Decide speed based on radius:
        // Larger balls move slower. Balls of radius >= 50 move at speed 1.
        double speed;
        if (radius >= 50) {
            speed = 1.0;
        } else {
            speed = 50.0 / radius;  // example: r=10 -> speed=5, r=25 -> speed=2, etc.
        }

        // Random direction: angle between 0 and 360 degrees
        double angle = rand.nextInt(360);

        // Use the helper to convert (angle, speed) into (dx, dy)
        Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
        ball.setVelocity(v);

        return ball;
    }

    public static void main(String[] args) {
        // Screen size (must match the logic inside sprites.Ball.moveOneStep if it's hard-coded there)
        int width = 200;
        int height = 200;

        // If no sizes were given - nothing to animate
        if (args.length == 0) {
            System.out.println("Please provide ball sizes (radius) as command line arguments.");
            return;
        }

        // Initialize GUI and helper objects
        GUI gui = new GUI("Multiple Bouncing Balls", width, height);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();

        // Create an array to hold all balls
        Ball[] balls = new Ball[args.length];

        // For each argument: parse radius, create ball
        for (int i = 0; i < args.length; i++) {
            int radius = Integer.parseInt(args[i]);

            // Simple validation: ignore non-positive radii
            if (radius <= 0) {
                radius = 5; // default small radius instead of crashing
            }

            balls[i] = createBall(radius, width, height, rand);
        }

        // === Animation loop ===
        while (true) {
            DrawSurface d = gui.getDrawSurface();

            // Move and draw each ball
            for (Ball ball : balls) {
                ball.moveOneStep();   // uses its own velocity and bounces on borders
                ball.drawOn(d);       // draw current position
            }

            gui.show(d);
            sleeper.sleepFor(50); // ~20 frames per second
        }
    }
}
