package com.sarahkim.kdtree;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root; // root of the Kd Tree
    private RectHV unitSquare = new RectHV(0, 0, 1, 1);
    private int size;
    private boolean VERTICAL = true; // HORIZONTAL is FALSE
    public KdTree() {
        size = 0;
    }
    private static class Node {

        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean orient;
        public Node(Point2D p, boolean orient, RectHV rect) {
            this.p = p;
            this.orient = orient;
            this.rect = rect;
        }
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public int size() {
        return size;
    }
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        root = insert(root, p, VERTICAL, unitSquare);
    }
    private Node insert(Node node, Point2D p, boolean orient, RectHV unitSquare) {
        if (node == null) {
            size++;
            return new Node(p, orient, unitSquare);
        }
        int cmp = compare(orient, node.p, p);
        RectHV[] arr = splitRect(node, orient);
        if (cmp < 0) node.lb = insert(node.lb, p, !node.orient, arr[0]);
        else if (cmp >= 0) node.rt = insert(node.rt, p, !node.orient, arr[1]);
        return node;
    }
    private RectHV[] splitRect(Node startNode, boolean orient) {
        RectHV origin = startNode.rect;
        double xmin = origin.xmin();
        double ymin = origin.ymin();
        double xmax = origin.xmax();
        double ymax = origin.ymax();
        RectHV[] arr = new RectHV[2];

        if(orient == VERTICAL) {
           // split by x-axis
            RectHV left = new RectHV(xmin, ymin, startNode.p.x(), ymax);
            RectHV right = new RectHV(startNode.p.x(), ymin, xmax, ymax);
            arr[0] = left;
            arr[1] = right;
        } else {
           // split by y-axis
            RectHV bottom = new RectHV(xmin, ymin, xmax, startNode.p.y());
            RectHV top = new RectHV(xmin, startNode.p.y(), xmax, ymax);
            arr[0] = bottom;
            arr[1] = top;
        }
        return arr;
    }
    private int compare(boolean orientation, Point2D nodePoint, Point2D p) {
       if (orientation == VERTICAL)  {
           return comparePoints(nodePoint.x(), p.x());
       } else {
           return comparePoints(nodePoint.y(), p.y());
       }
    }
    private int comparePoints(double x, double y) {
        if (x < y) return 1;
        else if (x > y) return -1;
        else return 0;
    }
    // TODO: replace as private --- temp check
    public String stringVersion() {
        String str = "";
        str = toString(root);
        return str;
    }
    private String toString(Node root) {
        StringBuilder builder = new StringBuilder();
        if (root == null)
            return "";
        builder.append(toString(root.lb));
        builder.append(root.p.toString());
        builder.append(toString(root.rt));
        return builder.toString();
    }
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (root == null) return false;
        return get(root, p) != null;
    }
    private Node get(Node n, Point2D key) {
        if (n == null) return null;
        int cmp = key.compareTo(n.p);
        if (cmp < 0) return get(n.lb, key);
        else if (cmp > 0) return get(n.rt, key);
        else return n;
    }
    public void draw() {
        if (size() == 0) return;
        Node start = root;
        drawTree(start);
    }
    private void drawTree(Node n) {
        if (n != null) {
            drawTree(n.lb);
            // draw the splitting lines
            if (n.orient == VERTICAL) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(0.001);
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                n.p.draw();
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(0.001);
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                n.p.draw();
            }
            drawTree(n.rt);
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        Node start = root;
        return rectIntersect(q, start, rect);
    }
    private Queue<Point2D> rectIntersect(Queue<Point2D> q, Node node, RectHV rect) {
        if (node != null) {
            // if query rect doesn't intersect the rectangle, don't explore
            if (node.rect.intersects(rect)) {
                if (rect.contains(node.p)) {
                    q.enqueue(node.p);
                }
                rectIntersect(q, node.lb, rect);
                rectIntersect(q, node.rt, rect);
            }
        }
        return q;
    }
    public Point2D nearest(Point2D p) {
        double d = p.distanceTo(root.p);
        return checkNearest(root, d, root.p, p);
    }
    private Point2D checkNearest(Node n, double distance, Point2D nearest, Point2D queryPoint) {
        if (n != null) {
            // set new nearest
            double currDistance = n.p.distanceTo(queryPoint);
            if (currDistance < distance) {
                distance = currDistance;
                nearest = n.p;
            }
            // prune trees
            if (n.lb != null) {
                double leftD = n.lb.rect.distanceTo(queryPoint);
                if (leftD == 0 || leftD < distance) {
                    return checkNearest(n.lb, distance, nearest, queryPoint);
                }
            }
            if (n.rt != null) {
                double rightD = n.rt.rect.distanceTo(queryPoint);
                if (rightD == 0 || rightD < distance) {
                    return checkNearest(n.rt, distance, nearest, queryPoint);
                }
            }
        }
        return nearest;
    }

}
