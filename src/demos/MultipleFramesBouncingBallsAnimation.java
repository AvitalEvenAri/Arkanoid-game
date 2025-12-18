package demos;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import geometry.Velocity;
import sprites.Ball;

import java.awt.Color;
import java.util.Random;

public class MultipleFramesBouncingBallsAnimation {

    // Helper: choose speed based on radius (bigger ball -> slower)
    private static double speedForRadius(int r) {
        if (r >= 50) {
            return 1.0;          // big balls: slow
        }
        return 50.0 / r;         // small balls: faster
    }

    public static void main(String[] args) {
        // We assume an even number of arguments (as allowed by the assignment).
        if (args.length == 0 || args.length % 2 != 0) {
            System.out.println("Please provide an even number of radii.");
            return;
        }

        // Create GUI and helper objects
        GUI gui = new GUI("Multiple Frames", 800, 600);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();

        // Define frames:
        // Gray frame: rectangle from (50,50) to (500,500)
        int grayLeft = 50, grayTop = 50, grayRight = 500, grayBottom = 500;

        // Yellow frame: rectangle from (450,450) to (600,600)
        int yellowLeft = 450, yellowTop = 450, yellowRight = 600, yellowBottom = 600;

        int n = args.length;
        int half = n / 2;
        Ball[] balls = new Ball[n];

        // ---- First half of balls: inside gray frame ----
        for (int i = 0; i < half; i++) {
            int r = Integer.parseInt(args[i]);
            if (r <= 0) {
                continue; // skip invalid radius
            }

            // Random position inside gray frame, keeping the whole ball inside
            int xRange = (grayRight - grayLeft) - 2 * r;
            int yRange = (grayBottom - grayTop) - 2 * r;
            if (xRange <= 0 || yRange <= 0) {
                continue; // ball too big for this frame
            }

            int x = grayLeft + r + rand.nextInt(xRange);
            int y = grayTop + r + rand.nextInt(yRange);

            // random color for each ball
            Color color = new Color(rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256));

            Ball b = new Ball(x, y, r, color);

            double speed = speedForRadius(r);
            double angle = rand.nextInt(360); // random direction
            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
            b.setVelocity(v);

            balls[i] = b;
        }

        // ---- Second half of balls: inside yellow frame ----
        for (int i = half; i < n; i++) {
            int r = Integer.parseInt(args[i]);
            if (r <= 0) {
                continue;
            }

            int xRange = (yellowRight - yellowLeft) - 2 * r;
            int yRange = (yellowBottom - yellowTop) - 2 * r;
            if (xRange <= 0 || yRange <= 0) {
                continue;
            }

            int x = yellowLeft + r + rand.nextInt(xRange);
            int y = yellowTop + r + rand.nextInt(yRange);

            // random color for each ball
            Color color = new Color(rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256));

            Ball b = new Ball(x, y, r, color);

            double speed = speedForRadius(r);
            double angle = rand.nextInt(360); // random direction
            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
            b.setVelocity(v);

            balls[i] = b;
        }

        // ---- Animation loop ----
        while (true) {
            DrawSurface d = gui.getDrawSurface();

            // background
            d.setColor(Color.WHITE);
            d.fillRectangle(0, 0, 800, 600);

            // draw gray frame
            d.setColor(Color.GRAY);
            d.fillRectangle(grayLeft, grayTop,
                    grayRight - grayLeft, grayBottom - grayTop);

            // draw yellow frame
            d.setColor(Color.YELLOW);
            d.fillRectangle(yellowLeft, yellowTop,
                    yellowRight - yellowLeft, yellowBottom - yellowTop);

            // move and draw all balls
            for (int i = 0; i < n; i++) {
                Ball b = balls[i];
                if (b == null) {
                    continue;
                }

                if (i < half) {
                    // first half -> bounce inside gray frame
                    b.moveOneStepInBounds(grayLeft, grayTop, grayRight, grayBottom);
                } else {
                    // second half -> bounce inside yellow frame
                    b.moveOneStepInBounds(yellowLeft, yellowTop, yellowRight, yellowBottom);
                }

                b.drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}
