package Week6;

import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> set;
    public         PointSET() {
        set=new SET<Point2D>();
    }
    public           boolean isEmpty(){
        return set.isEmpty();
    }

    public  int size() {
        return set.size();
    }
    public  void insert(Point2D p) {
        if(p==null)
            throw new IllegalArgumentException();
        set.add(p);
    }
    public  boolean contains(Point2D p)  {
        if(p==null)
            throw new IllegalArgumentException();
        return set.contains(p);
    }
    public              void draw() {
        for(Point2D point:set){
            point.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        if(rect==null)
            throw new IllegalArgumentException();
        SET<Point2D> set2=new SET<>();
        for(Point2D point:set) {
            if (rect.contains(point))
                set2.add(point);
        }
        return set2;
        }

    public  Point2D nearest (Point2D p) {
        if(p==null)
            throw new IllegalArgumentException();
        Point2D b=null;
        double min=0;
        for(Point2D a:set) {
            if(min==0 && b==null){
                min=a.distanceSquaredTo(p);
                b=a;
            }
            double dist=a.distanceSquaredTo(p);
            if(dist<min){
                min=dist;
                b=a;
            }

        }
        return b;
    }

  //  public static void main(String[] args)                  // unit testing of the methods (optional)
}
