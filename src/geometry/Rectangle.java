package geometry;

import java.util.ArrayList;
import java.util.List;

public class Rectangle {

    private Point upperLeft;      // Upper-left corner of the rectangle
    private double width;         // geometry.Rectangle width
    private double height;        // geometry.Rectangle height

    // Constructor
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    // Getters
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    /**
     * Return a list of intersection points between a given line
     * and the rectangle's 4 edges.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> points = new ArrayList<>();

        // Build the 4 edges
        Point ul = this.upperLeft;
        Point ur = new Point(ul.getX() + width, ul.getY());
        Point ll = new Point(ul.getX(), ul.getY() + height);
        Point lr = new Point(ul.getX() + width, ul.getY() + height);

        // geometry.Rectangle edges as lines
        Line top    = new Line(ul, ur);
        Line bottom = new Line(ll, lr);
        Line left   = new Line(ul, ll);
        Line right  = new Line(ur, lr);

        // Check intersection with each edge
        Point p;

        p = line.intersectionWith(top);
        if (p != null) points.add(p);

        p = line.intersectionWith(bottom);
        if (p != null) points.add(p);

        p = line.intersectionWith(left);
        if (p != null) points.add(p);

        p = line.intersectionWith(right);
        if (p != null) points.add(p);

        return points;
    }
}
