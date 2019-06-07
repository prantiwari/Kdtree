package Week6;

import edu.princeton.cs.algs4.StdDraw;

public class Point2D implements Comparable<Point2D> {
    private double x;
    private double y;
    public Point2D(double x, double y) {
        this.x=x;
        this.y=y;
    }
    public  double x() {
        return x;
    }
    public  double y() {
        return y;
    }
    public  double distanceTo(Point2D that) {
        return Math.sqrt(Math.pow(this.x-that.x,2)+Math.pow(this.y-this.y,2));
    }
    public  double distanceSquaredTo(Point2D that) {
        return Math.pow(this.x-that.x,2)+Math.pow(this.y-that.y,2);
    }
    public     int compareTo(Point2D that) {
        if(this.y>that.y)
            return 1;
        else if(this.y<that.y)
            return -1;
        else if(this.x>that.x)
            return 1;
        else if(this.x<that.x)
            return -1;
        else
            return 0;
        }

    public boolean equals(Object that)    {
        if(this==that)
            return true;
        else if(this.getClass()!=that.getClass())
            return false;

        else if(this.x==((Point2D)that).x &&this.y==((Point2D)that).y )
            return true;
        else
            return false;
    }
    public    void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(x,y);
        StdDraw.setPenRadius();
    }
    public  String toString() {
        return "("+x+","+y+")";
    }
}