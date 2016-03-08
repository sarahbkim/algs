package com.sarahkim.kdtree;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;
import java.util.Iterator;

public class PointSET {
    TreeSet<Point2D> set;
    public PointSET() {
        set = new TreeSet();
    }
    public boolean isEmpty() {
        return set.isEmpty();
    }
    public int size() {
        return set.size();
    }
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        set.add(p);
    }
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return set.contains(p);
    }
    public void draw() {
        if (set.size() == 0) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.1);
        for(Point2D point : set) {
            StdDraw.point(point.x(), point.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        if (set.size() == 0) return null;

        Queue q = new Queue<Point2D>();

        Iterator<Point2D> it = set.iterator();
        while (it.hasNext()) {
            Point2D curr = it.next();
            if (withinRect(rect, curr)) {
                q.enqueue(curr);
            }
        }
        return q;
    }
    private boolean withinRect(RectHV rect, Point2D point) {
       return rect.contains(point);
    }
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();

        if (set.size() == 0) return null;

        double d = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = null;

        for (Point2D point : set) {
            double currDistance = point.distanceTo(p);
            if (currDistance < d) {
                nearestPoint = point;
                d = currDistance;
            }
        }
        return nearestPoint;
    }
    public static void main(String[] args) {
        PointSET set = new PointSET();
        set.insert(new Point2D(2.0, 2.0));
        set.insert(new Point2D(10.0, 2.0));
        set.insert(new Point2D(3.2, 30));
        set.draw();
    }
}