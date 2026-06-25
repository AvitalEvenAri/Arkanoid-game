package geometry;

public class Velocity {
    private final double dx;
    private final double dy;

    // Constructor: store the per-step change in x (dx) and y (dy)
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // Apply this velocity to a point: (x, y) -> (x + dx, y + dy)
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    public double getDx() { return dx; }
    public double getDy() { return dy; }

    /**
     * Build a velocity from an angle (in degrees) and a speed (length).
     *
     * Angle convention (as required by the assignment):
     *   - 0°  = up
     *   - 90° = right
     *   - 180° = down
     *   - 270° = left
     *
     * Note: screen Y axis grows downward, so moving "up" means negative dy.
     */
    public static Velocity fromAngleAndSpeed(double angleDegrees, double speed) {
        double a = Math.toRadians(angleDegrees);

        // With the above convention, the x-component comes from sin,
        // and the y-component from -cos (minus because screen y goes down).
        double dx = speed * Math.sin(a);
        double dy = -speed * Math.cos(a);

        // Tiny cleanup to avoid -0.0 and floating noise near zero.
        final double EPS = 1e-10;
        if (Math.abs(dx) < EPS) dx = 0.0;
        if (Math.abs(dy) < EPS) dy = 0.0;

        return new Velocity(dx, dy);
    }

    // >>> NEW: return the speed (vector length) <<<
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }
}
