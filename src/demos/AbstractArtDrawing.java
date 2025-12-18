package demos;

import biuoop.GUI;
import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;

import java.awt.Color;
import java.util.Random;

public class AbstractArtDrawing {
    // Constants: window dimensions, number of lines, and circle radius
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int N = 10;   // how many random line segments to draw
    private static final int R = 3;    // radius for middle/intersection dots

    // One Random generator for the whole class
    private static final Random RAND = new Random();

    // Return a random X inside the window (avoid the exact edges for aesthetics)
    private static int rndX() {
        return RAND.nextInt(WIDTH - 2) + 1;  // range: 1..WIDTH-1
    }

    // Return a random Y inside the window
    private static int rndY() {
        return RAND.nextInt(HEIGHT - 2) + 1; // range: 1..HEIGHT-1
    }

    // Create a random line segment with two DISTINCT points.
    // If both points are equal (zero length), retry.
    private static Line generateRandomLine() {
        while (true) {
            Point a = new Point(rndX(), rndY());
            Point b = new Point(rndX(), rndY());
            if (!a.equals(b)) {                 // ensure non-degenerate segment
                return new Line(a, b);
            }
        }
    }

    // Draw one black line and mark its middle (blue filled circle)
    private static void drawLineWithMiddle(Line l, DrawSurface d) {
        int x1 = (int) l.start().getX();
        int y1 = (int) l.start().getY();
        int x2 = (int) l.end().getX();
        int y2 = (int) l.end().getY();

        // Draw the segment
        d.setColor(Color.BLACK);
        d.drawLine(x1, y1, x2, y2);

        // Draw its middle point
        Point mid = l.middle();
        d.setColor(Color.BLUE);
        d.fillCircle((int) mid.getX(), (int) mid.getY(), R);
    }

    // Main: build the frame, draw lines + middles, then intersections
    public static void main(String[] args) {
        // 1) Create a window and get a fresh drawing surface
        GUI gui = new GUI("Abstract Art - Lines & Intersections", WIDTH, HEIGHT);
        DrawSurface d = gui.getDrawSurface();

        // 2) Generate N random line segments and keep them (we need them for intersections)
        Line[] lines = new Line[N];
        for (int i = 0; i < N; i++) {
            lines[i] = generateRandomLine();
        }

        // 3) Draw each line (black) and its middle point (blue)
        for (Line l : lines) {
            drawLineWithMiddle(l, d);
        }

        // 4) For every pair of lines, compute intersection and mark it (red) if it exists
        d.setColor(Color.RED);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                Point p = lines[i].intersectionWith(lines[j]);
                if (p != null) {
                    d.fillCircle((int) p.getX(), (int) p.getY(), R);
                }
            }
        }

        // 5) Show everything on screen in a single frame
        gui.show(d);
    }
}
