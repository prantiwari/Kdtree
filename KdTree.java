package Week6;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private double champdist;
    private Node champnode;
    private Node root;
    private Stack<Point2D> stack;

    public KdTree() {
        root = null;
        stack = new Stack<>();
        champdist = 0;
        champnode = null;
    }

    public int size() {
        int size = size(root);
        return size;


    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) return false;
        if (x.point.equals(p)) return true;
        Node champnode = p.compareTo(x.point) > 0 ? x.rt : x.lb;
        return contains(champnode, p);


    }

    private int size(Node x) {
        if (x == null) return 0;
        else
        return x.N;
    }



    public boolean isEmpty() {
        if (root == null) {
            return true;
        }

        return false;

    }

    public void insert(Point2D point) {
        if (point == null)
            throw new IllegalArgumentException();

        root = insert(root, point, false, null);


    }

    private Node insert(Node x, Point2D point, boolean vertical, Node parent) {

        if (x == null) {
            if (isEmpty())
                return new Node(point, !vertical, newNodeRec(point, x), null,1);
            else
                return new Node(point, !vertical, newNodeRec(point, parent), parent,1);
        }
        int cmp = x.compareTo(point);
        if (cmp < 0) {
            if (x.vertical) {

                x.rt = insert(x.rt, point, x.vertical, x);
            } else {
                x.rt = insert(x.rt, point, x.vertical, x);
            }
        } else if (cmp > 0) {
            if (x.vertical) {
                x.lb = insert(x.lb, point, x.vertical, x);
            }
            else {
                x.lb = insert(x.lb, point, x.vertical, x);
            }
        } else if (x.point.x() == point.x() && x.point.y() == point.y()) {
            x.point = point;
        } else if (x.point.x() == point.x() && x.vertical) {
            if (x.vertical) {
                x.rt = insert(x.rt, point, x.vertical, x);
            }
            else {
                x.rt = insert(x.rt, point, x.vertical, x);
            }
        } else if (x.point.y() == point.y() && !x.vertical) {
            if (x.vertical) {
                x.rt = insert(x.rt, point, x.vertical, x);
            } else {
                x.rt = insert(x.rt, point, x.vertical, x);
            }
        }
        x.N=size(x.lb)+size(x.rt)+1;

        return x;


    }

    private RectHV newNodeRec(Point2D point, Node root) {
        if (isEmpty())
            return new RectHV(0, 0, 1, 1);
        int cmp = root.compareTo(point);
        if (root.vertical && cmp > 0)
            return new RectHV(root.rect.xmin(), root.rect.ymin(), root.point.x(), root.rect.ymax());
        else if (root.vertical && cmp < 0)
            return new RectHV(root.point.x(), root.rect.ymin(), root.rect.xmax(), root.rect.ymax());
        else if (!root.vertical && cmp > 0)
            return new RectHV(root.rect.xmin(), root.rect.ymin(), root.rect.xmax(), root.point.y());
        else if (!root.vertical && cmp < 0)
            return new RectHV(root.rect.xmin(), root.point.y(), root.rect.xmax(), root.rect.ymax());
        else if (root.point.x() == point.x())
            return new RectHV(root.point.x(), root.rect.ymin(), root.rect.xmax(), root.rect.ymax());
        else return new RectHV(root.rect.xmin(), root.point.y(), root.rect.xmax(), root.rect.ymax());


    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (root == null) return null;
        if (this.contains(p))
            return p;
        champnode = root;
        champdist = p.distanceSquaredTo(champnode.point);
        nearest(root, p);
        return champnode.point;


    }

    private void nearest(Node x, Point2D p) {
        if (x == null) return;
        if (p.distanceSquaredTo(x.point) < champdist) {
            champdist = p.distanceSquaredTo(x.point);
            champnode = x;

        }
        Node winner = x.compareTo(p) > 0 ? x.lb : x.rt;
        Node loser = x.compareTo(p) > 0 ? x.rt : x.lb;
        if (winner != null) {
            nearest(winner, p);
        }
        if (loser != null) {
            if (loser.rect.distanceSquaredTo(p) < champdist) {
                nearest(loser, p);
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        range(root, rect);
        return stack;

    }

    private void range(Node x, RectHV rectangle) {
        if (x == null) return;
        if (rectangle.contains(x.point)) stack.push(x.point);
        if (x.lb != null) {

            if (x.lb.rect.intersects(rectangle))
                range(x.lb, rectangle);
        }
        if (x.rt != null) {
            if (x.rt.rect.intersects(rectangle))
                range(x.rt, rectangle);
        }
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null) return;
        draw(x.lb);
        drawnode(x);

        draw(x.rt);

    }

    private void drawnode(Node node) {

        node.point.draw();

        if (node.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);

            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());

        }
    }

    private class Node implements Comparable<Point2D> {
        Node parent;
        Node lb;
        Node rt;
        Point2D point;
        RectHV rect;
        boolean vertical;
        int N;

        Node(Point2D point, boolean vertical, RectHV rect, Node Parent,int N) {
            this.vertical = vertical;
            lb = null;
            rt = null;
            this.point = point;
            this.rect = rect;
            this.parent = Parent;
            this.N=N;
        }

        @Override
        public int compareTo(Point2D that) {
            if (vertical) {
                if (that.x() < this.point.x())
                    return 1;
                else if (that.x() > this.point.x())
                    return -1;
                else return 0;
            } else {
                if (that.y() < this.point.y())
                    return 1;
                else if (that.y() > this.point.y())
                    return -1;
                else return 0;
            }
        }
    }
   /* public static void main(String[] args) {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        KdTree tree = new KdTree();
        Point2D point1 = new Point2D(0.0, 1.0);
        Point2D point2 = new Point2D(0.0, 0.0);
        Point2D point3 = new Point2D(0.0, 1.0);
        tree.insert(point1);
        tree.insert(point2);
        tree.insert(point3);
        tree.draw();
        System.out.println(tree.size());
    }*/


    public static void main(String[] args) {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        KdTree tree = new KdTree();
        int k=0;
        while (k<20) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);

                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    tree.insert(p);
                    StdDraw.clear();
                    tree.draw();
                    StdDraw.show();
                    k++;

            }
            StdDraw.pause(20);
        }
        System.out.println(tree.size());
       /* Point2D point1 = new Point2D(0.125, 0.75);
        Point2D point2 = new Point2D(0.75, 0.75);
        Point2D point3 = new Point2D(0.75, 0.75);
        Point2D point4 = new Point2D(0.875, 0.375);
        Point2D point5 = new Point2D(0.125, 0.875);
        Point2D point6 = new Point2D(0.375, 0.125);
        Point2D point7 = new Point2D(0.125, 0.875);
        Point2D point8 = new Point2D(0.375, 0.125);
        Point2D point9 = new Point2D(0.5, 0.875);
        Point2D point10 = new Point2D(0.0, 0.75);
        Point2D point11 = new Point2D(0.125, 0.875);
        Point2D point12 = new Point2D(0.75, 0.0);
        Point2D point13 = new Point2D(0.875, 0.125);
        Point2D point14 = new Point2D(1.0, 0.125);
        tree.insert(point1);
        tree.insert(point2);
        tree.insert(point3);
        tree.insert(point4);
        tree.insert(point5);
        tree.insert(point6);
        tree.insert(point7);
        tree.insert(point8);
        tree.insert(point11);
        tree.insert(point9);
        tree.insert(point10);
        tree.insert(point12);
        tree.insert(point13);
        tree.insert(point14);

        tree.draw();
        //  System.out.println( tree.contains(point7));*/

        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                Point2D query = new Point2D(x, y);
                tree.draw();
                System.out.println(tree.nearest(query));
                query.draw();
                StdDraw.show();
                StdDraw.pause(20);
            }
        }

    }
}


   /*     System.out.println(tree.nearest(query));
        //  RectHV rect1=new RectHV(0.0,0.0,0.5,0.5);
        // rect1.draw();
        // for(Point2D point:tree.range(rect1))
        //   System.out.println(point);


    }
}
*/
