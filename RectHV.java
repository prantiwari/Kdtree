package Week6;

import edu.princeton.cs.algs4.StdDraw;

public class RectHV {
    private double xmin,ymin;
    private double xmax,ymax;
    private Point2D pointA,pointB, pointC, pointD ;

    public    RectHV(double xmin, double ymin,
                     double xmax, double ymax) {
        this.xmin=xmin;
        this.ymin=ymin;
        this.xmax=xmax;
        this.ymax=ymax;
        pointA=new Point2D(xmin,ymax) ;
        pointB=new Point2D(xmax,ymax) ;
        pointC=new Point2D(xmax,ymin) ;
        pointD=new Point2D(xmin,ymin) ;
    }
    public  double xmin() {
        return xmin;
    }
    public  double ymin() {
        return ymin;
    }
    public  double xmax() {
        return xmax;
    }
    public  double ymax() {
        return ymax;
    }
    public boolean contains(Point2D p)   {
        if(p.x()<=this.xmax && p.x()>=xmin && p.y()<=this.ymax && p.y()>=this.ymin)
            return true;

        else
            return false;

    }
    public boolean intersects(RectHV that) {
        if(that.xmin>this.xmax) {
            return false;}
        if(that.xmax<this.xmin) {
           return false;}
        if(that.ymin>this.ymax) {
           return false;}
        if(that.ymax<this.ymin){
             return false;}

        return true;

    }
   // public  double distanceTo(Point2D p)   {
    //}
    public  double distanceSquaredTo(Point2D p)   {
        double distance=0;
        if(this.contains(p)) return 0;
        if(p.y()<ymax&&p.y()>ymin){
            if(p.x()>xmax)
                distance= p.distanceSquaredTo(new Point2D(xmax,p.y()));
            else
                distance=p.distanceSquaredTo(new Point2D(xmin,p.y()));

        }
        else if(p.x()<xmax && p.x()>xmin){
            if(p.y()>ymax)
                distance=p.distanceSquaredTo(new Point2D(p.x(),ymax));
            else
                distance=p.distanceSquaredTo(new Point2D(p.x(),ymin));
        }
        else if(p.y()>ymax && p.x()>xmax)
            distance=p.distanceSquaredTo(pointB);
        else if(p.y()<ymin && p.x()<xmin)
            distance=p.distanceSquaredTo(pointD);
        else if(p.y()>ymax && p.x()<xmin)
            distance=p.distanceSquaredTo(pointA);
        else if(p.y()<ymin && p.x()>xmax)
            distance=p.distanceSquaredTo(pointC);

        return distance;
    }
    public boolean equals(Object that) {
        if(this==that)
            return true;
        else if(this.getClass()!=that.getClass())
            return false;

        RectHV k=(RectHV) that;

         if(this.xmin==k.xmin && this.ymin==k.ymin && this.xmax==k.xmax && this.ymax==k.xmax)
             return true;
         else return false;


    }
    public    void draw()   {
       StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.rectangle((xmin+xmax)/2,(ymin+ymax)/2,(xmax-xmin)/2,(ymax-ymin)/2);
    }
    public  String toString() {
        return"("+xmin+","+ymin+","+xmax+","+ymax+")";
    }

    public static void main(String[] args) {
        RectHV rect1=new RectHV(0.2,0.2,0.9,0.9);
        RectHV rect2=new RectHV(0.1,0.2,0.8,0.8);
        System.out.println(rect1.intersects(rect2));
    }

}
