package com.sarahkim.kdtree;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root; // root of the Kd Tree
    private RectHV unitSquare = new RectHV(0, 0, 1, 1);
    private int size;
    private double nearestDistance;
    private Point2D nearestPoint;
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
        if (p == null) throw new NullPointerException();
        if (node == null) {
            size += 1;
            return new Node(p, orient, unitSquare);
        }
        int cmp = compare(orient, node.p, p);
        RectHV[] arr = splitRect(node, orient);
        if (cmp < 0) {
            node.lb = insert(node.lb, p, !node.orient, arr[0]);
        }
        else if (cmp >= 0) {
            node.rt = insert(node.rt, p, !node.orient, arr[1]);
        }
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
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (root == null) return false;
        return get(root, p) != null;
    }
    private Node get(Node n, Point2D key) {
        if (n == null) return null;
        int equalsCompare = n.p.compareTo(key);
        if (equalsCompare != 0) {
            // if not found the node, do comparisons to figure out left or right
            int cmp = compare(n.orient, n.p, key);
            if (cmp < 0) return get(n.lb, key);
            else if (cmp >= 0) return get(n.rt, key);
        }
        return n;
    }
    public void draw() {
        if (size() == 0) return;
        Node start = root;
        drawTree(start);
    }
    private void drawTree(Node n) {
        if (n != null) {
            if (n.lb != null) drawTree(n.lb);
            if (n.orient == VERTICAL) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(0.01);
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                n.p.draw();
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(0.01);
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                n.p.draw();
            }
            if (n.rt != null) drawTree(n.rt);
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
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
        nearestDistance = Double.POSITIVE_INFINITY;
        if (p == null) throw new NullPointerException();
        return checkNearest(root, p);
    }
    private Point2D checkNearest(Node n, Point2D queryPoint) {
        if (n != null) {
            updateNearest(n.p, queryPoint);
            if (n.lb != null && n.rt != null) {
                double left = n.lb.rect.distanceTo(queryPoint);
                double right = n.rt.rect.distanceTo(queryPoint);
                if (left < right && nearestDistance > left) {
                   checkNearest(n.lb, queryPoint);
                   checkNearest(n.rt, queryPoint);
                } else if (right < left && nearestDistance > right) {
                    checkNearest(n.rt, queryPoint);
                    checkNearest(n.lb, queryPoint);
                }
            } else if (n.lb != null && nearestDistance > n.lb.rect.distanceTo(queryPoint)) {
                checkNearest(n.lb, queryPoint);
            } else if (n.rt != null && nearestDistance > n.rt.rect.distanceTo(queryPoint)) {
                checkNearest(n.rt, queryPoint);
            }
        }
        return nearestPoint;
    }
    private void updateNearest(Point2D current, Point2D queryPoint) {
        double currDistance = current.distanceTo(queryPoint);
        if (currDistance < nearestDistance) {
            nearestDistance = currDistance;
            nearestPoint = current;
        }
    }
}
