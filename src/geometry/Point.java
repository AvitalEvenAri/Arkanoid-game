package geometry;

public class Point {
    private double X;
    private double Y;

    // constructor
    public Point(double x, double y) {
        this.X=x;
        this.Y=y;
    }

    // distance -- return the distance of this point to the other point
    public double distance(Point other) {
        double x2=other.X;
        double y2=other.Y;
        return  Math.sqrt(((this.X-x2)*(this.X-x2))+((this.Y-y2)*(this.Y-y2)));
    }

    // equals -- return true is the points are equal, false otherwise
    public boolean equals(Point other) {
        if ((this.X==other.X)&&(this.Y== other.Y)){
            return true;
        }
        return false;
    }

    // Return the x and y values of this point
    public double getX() {
        return this.X;
    }

    public double getY() {
        return this.Y;
    }


}