package demos;

import biuoop.GUI;
import biuoop.DrawSurface;
import sprites.Ball;

public class BallsTest1 {
    public static void main(String[] args) {
        // Create a 400x400 window
        GUI gui = new GUI("Balls Test 1", 400, 400);
        DrawSurface d = gui.getDrawSurface();

        // Create three balls with different positions, sizes, and colors
        Ball b1 = new Ball(100, 100, 30, java.awt.Color.RED);
        Ball b2 = new Ball(100, 150, 10, java.awt.Color.BLUE);
        Ball b3 = new Ball(80,  249, 50, java.awt.Color.GREEN);

        // Draw the balls on the drawing surface
        b1.drawOn(d);
        b2.drawOn(d);
        b3.drawOn(d);

        // Show the frame (one static image)
        gui.show(d);
    }

}
