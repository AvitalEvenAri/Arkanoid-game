package demos;

import geometry.Line;
import geometry.Point;

/**
 * This class does some simple tessting of the geometry.Point and geometry.Line classes.
 */
public class GeometryTester {

    final static double Comparison_threshold = 0.00001;

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean doubleEquals(double a, double b) {
        return  Math.abs(a - b) < GeometryTester.Comparison_threshold;
    }

    /**
     * The method is in charge of testing the geometry.Point class.
     *
     * @return true if not mistakes were found, false otherwise.
     */
    public boolean testPoint() {
        boolean mistake = false;
        Point p1 = new Point(12, 2);
        Point p2 = new Point(9, -2);

        if (!doubleEquals(p1.getX(), 12)) {
            System.out.println("Test p1.getX() failed.");
            mistake = true;
        }
        if (!doubleEquals(p1.getY(),2)) {
            System.out.println("Test p1.getY() failed.");
            mistake = true;
        }
        if (!doubleEquals(p1.distance(p1), 0)) {
            System.out.println("Test distance to self failed.");
            mistake = true;
        }
        if (!doubleEquals(p1.distance(p2), p2.distance(p1))) {
            System.out.println("Test distance symmetry failed.");
            mistake = true;
        }
        if (!doubleEquals(p1.distance(p2),5)) {
            System.out.println("Test distance failed.");
            mistake = true;
        }
        if (!p1.equals(p1)) {
            System.out.println("Equality to self failed.");
            mistake = true;
        }
        if (!p1.equals(new Point(12, 2))) {
            System.out.println("Equality failed.");
            mistake = true;
        }
        if (p1.equals(p2)) {
            System.out.println("Equality failed -- should not be equal.");
            mistake = true;
        }

        return !mistake;
    }

    /**
     * The method is in charge of testing the geometry.Line class.
     *
     * @return true if not mistakes were found, false otherwise.
     */
    public boolean testLine() {
        boolean mistakes = false;
        Line l1 = new Line(12, 2, 9, -2);
        Line l2 = new Line(0, 0, 20, 0);
        Line l3 = new Line(9, 2, 12, -2);

        if (!l1.isIntersecting(l2)) {
            System.out.println("Test isIntersecting failed (1).");
            mistakes = true;
        }
        if (l1.isIntersecting(new Line(0, 0, 1, 1))) {
            System.out.println("Test isIntersecting failed (2).");
            mistakes = true;
        }
        Point intersectL1L2 = l1.intersectionWith(l2);
        if (!l1.middle().equals(intersectL1L2)) {
            System.out.println("Test intersectionWith middle failed.");
            mistakes = true;
        }

        return !mistakes;
    }

    // ===== Added: Simple, basic tests for the new geometry.Line methods =====

    /** length(), middle(), start(), end() */
    public boolean testLineBasics() {
        boolean mistakes = false;
        Line l = new Line(0, 0, 6, 8); // classic 3-4-5 triangle scaled => length 10

        if (!doubleEquals(l.length(), 10.0)) {
            System.out.println("geometry.Line length() basic failed.");
            mistakes = true;
        }
        Point mid = l.middle();
        if (!doubleEquals(mid.getX(), 3.0) || !doubleEquals(mid.getY(), 4.0)) {
            System.out.println("geometry.Line middle() basic failed.");
            mistakes = true;
        }
        if (!l.start().equals(new Point(0, 0))) {
            System.out.println("geometry.Line start() basic failed.");
            mistakes = true;
        }
        if (!l.end().equals(new Point(6, 8))) {
            System.out.println("geometry.Line end() basic failed.");
            mistakes = true;
        }
        return !mistakes;
    }

    /** Simple vertical ⟂ horizontal intersection at a single point */
    public boolean testSimpleVerticalHorizontal() {
        boolean mistakes = false;
        Line vertical = new Line(2, 0, 2, 4);     // x = 2
        Line horizontal = new Line(0, 2, 4, 2);   // y = 2

        if (!vertical.isIntersecting(horizontal)) {
            System.out.println("Vertical/Horizontal isIntersecting failed.");
            mistakes = true;
        }
        Point p = vertical.intersectionWith(horizontal);
        if (p == null || !doubleEquals(p.getX(), 2.0) || !doubleEquals(p.getY(), 2.0)) {
            System.out.println("Vertical/Horizontal intersectionWith failed.");
            mistakes = true;
        }
        return !mistakes;
    }

    /** Two simple parallel (non-intersecting) lines */
    public boolean testSimpleParallelNoIntersection() {
        boolean mistakes = false;
        Line a = new Line(0, 1, 5, 1); // y=1
        Line b = new Line(0, 3, 5, 3); // y=3

        if (a.isIntersecting(b)) {
            System.out.println("Parallel lines should not intersect (isIntersecting failed).");
            mistakes = true;
        }
        if (a.intersectionWith(b) != null) {
            System.out.println("Parallel lines should return null (intersectionWith failed).");
            mistakes = true;
        }
        return !mistakes;
    }

    /** Touching at a single endpoint */
    public boolean testEndpointTouchingSimple() {
        boolean mistakes = false;
        Line s1 = new Line(0, 0, 2, 2);
        Line s2 = new Line(2, 2, 4, 0);

        if (!s1.isIntersecting(s2)) {
            System.out.println("Endpoint touching isIntersecting failed.");
            mistakes = true;
        }
        Point p = s1.intersectionWith(s2);
        if (p == null || !doubleEquals(p.getX(), 2.0) || !doubleEquals(p.getY(), 2.0)) {
            System.out.println("Endpoint touching intersectionWith failed.");
            mistakes = true;
        }
        return !mistakes;
    }

    /** equals(geometry.Line other): same line and reversed endpoints */
    public boolean testLineEqualsBasic() {
        boolean mistakes = false;
        Line l = new Line(1, 2, 3, 4);
        Line same = new Line(1, 2, 3, 4);
        Line rev  = new Line(3, 4, 1, 2);
        Line diff = new Line(1, 2, 3, 5);

        if (!l.equals(same)) {
            System.out.println("equals() failed for identical lines.");
            mistakes = true;
        }
        if (!l.equals(rev)) {
            System.out.println("equals() failed for reversed endpoints.");
            mistakes = true;
        }
        if (l.equals(diff)) {
            System.out.println("equals() should be false for different line.");
            mistakes = true;
        }
        return !mistakes;
    }

    /**
     * Main method, running tests on both the point and the line classes.
     * @param args ignored.
     */
    public static void main(String[] args) {
        GeometryTester tester = new GeometryTester();
        boolean ok = true;

        ok &= tester.testPoint();
        ok &= tester.testLine();

        // Added basic tests (no exotic edge-cases)
        ok &= tester.testLineBasics();
        ok &= tester.testSimpleVerticalHorizontal();
        ok &= tester.testSimpleParallelNoIntersection();
        ok &= tester.testEndpointTouchingSimple();
        ok &= tester.testLineEqualsBasic();

        if (ok)
            System.out.println("Test Completed Successfully!");
        else
            System.out.println("Found failing tests.");
    }
}
