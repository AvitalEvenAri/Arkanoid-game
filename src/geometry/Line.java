package geometry;

import java.util.List;

public class Line {
    final Point start;
    final Point end;
    private static final double EPS = 1e-7;


    private static boolean eq(double a, double b) {
        return Math.abs(a - b) < EPS;
    }

    private static boolean inRange(double v, double a, double b) {
        double min = Math.min(a, b) - EPS, max = Math.max(a, b) + EPS;
        return v >= min && v <= max;
    }

    private static boolean pointOnSegment(Point p, Point a, Point b) {
        return inRange(p.getX(), a.getX(), b.getX()) &&
                inRange(p.getY(), a.getY(), b.getY());
    }

    // constructors
    public Line(Point start, Point end) {
        this.start=start;
        this.end=end;
    }
    public Line(double x1, double y1, double x2, double y2) {
        this.start=new Point(x1, y1);
        this.end=new Point(x2, y2);
    }


    // Return the length of the line
    public double length() {
       return start.distance(end);
    }

    // Returns the middle point of the line
    public Point middle() {
        double x1=(start.getX()+ end.getX())/2.0;
        double y1=(start.getY()+ end.getY())/2.0;
        Point middle=new Point(x1,y1);
        return middle;
    }

    // Returns the start point of the line
    public Point start() {
        return start;
    }

    // Returns the end point of the line
    public Point end() {
        return end;
    }

    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }


    private double[] interFormula(Point start, Point end) {
        double[] arr = new double[3];
        double x1 = start.getX();
        double x2 = end.getX();
        double y1 = start.getY();
        double y2 = end.getY();


        if (eq(x2, x1)) {
            arr[0] = Double.POSITIVE_INFINITY; // m
            arr[1] = x1;                       // xConst
            arr[2] = 1;                        // vertical flag
            return arr;
        }

        double m = (y2 - y1) / (x2 - x1);
        double b = y1 - m * x1;
        arr[0] = m;
        arr[1] = b;
        arr[2] = 0; // not vertical
        return arr;
    }


    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {
        if (this.equals(other)) {
            return null;
        }

        double[] a1 = interFormula(this.start, this.end);
        double[] a2 = interFormula(other.start, other.end);

        if (a1[2] == 1 && a2[2] == 1) {
            if (!eq(a1[1], a2[1])) return null;

            if (pointOnSegment(this.start, other.start, other.end)) return this.start;
            if (pointOnSegment(this.end,   other.start, other.end)) return this.end;
            if (pointOnSegment(other.start, this.start, this.end))  return other.start;
            if (pointOnSegment(other.end,   this.start, this.end))  return other.end;
            return null;
        }

        double x, y;

        if (a1[2] == 1) {
            x = a1[1];                // xConst של הקו הראשון
            y = a2[0] * x + a2[1];    // y מהמדים של הקו השני
        } else if (a2[2] == 1) {
            x = a2[1];                // xConst של הקו השני
            y = a1[0] * x + a1[1];    // y מהמדים של הקו הראשון
        } else {
            double m1 = a1[0], b1 = a1[1];
            double m2 = a2[0], b2 = a2[1];

            if (eq(m1, m2)) {
                if (!eq(b1, b2)) return null;

                if (pointOnSegment(this.start, other.start, other.end)) return this.start;
                if (pointOnSegment(this.end,   other.start, other.end)) return this.end;
                if (pointOnSegment(other.start, this.start, this.end))  return other.start;
                if (pointOnSegment(other.end,   this.start, this.end))  return other.end;
                return null;
            }

            x = (b2 - b1) / (m1 - m2);
            y = m1 * x + b1;
        }

        Point inter = new Point(x, y);
        if (!pointOnSegment(inter, this.start, this.end))  return null;
        if (!pointOnSegment(inter, other.start, other.end)) return null;

        return inter;
    }


    // equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        if (other == null) return false;
        if ((start().equals(other.start)&&end.equals(other.end))||
                (start().equals(other.end)&&end.equals(other.start)))
        {
            return true;
        }
        return false;

    }

    /**
     * Returns the closest intersection point between this line segment
     * and the given rectangle, measured from this line's start point.
     * If there are no intersection points, returns null.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // 1. Ask the rectangle for all intersection points with this line.
        List<Point> points = rect.intersectionPoints(this);

        // 2. If there are no intersection points, return null.
        if (points.isEmpty()) {
            return null;
        }

        // 3. Assume the first point is the closest, for now.
        Point closest = points.get(0);
        double minDistance = this.start.distance(closest);

        // 4. Go over all points and look for a closer one.
        for (Point p : points) {
            double d = this.start.distance(p);
            if (d < minDistance) {
                minDistance = d;
                closest = p;
            }
        }

        // 5. Return the closest intersection point we found.
        return closest;
    }


}