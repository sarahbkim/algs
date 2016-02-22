package com.sarahkim;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BruteCollinearPoints {
    private int MIN_COLL_LENGTH = 4;
    private int segmentCount;
    private LineSegment[] arrayOfSegments;
    private final HashMap<Double, ArrayList<Point>> slopes = new HashMap<Double, ArrayList<Point>>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        segmentCount = 0;

        validatePoints(points);
        calculateSlopes(points);
        calculateSegments();
    }
    private void validatePoints(Point[] points) {
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
            if (i < points.length-1) {
                if (points[i].compareTo(points[i + 1]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
    private void calculateSlopes(Point[] points) {
        // calculate the slope
        for (int i = 0; i < points.length; i++) {
        for (int j = i+1; j < points.length; j++) {
            double s = points[i].slopeTo(points[j]);
            ArrayList<Point> list;
            list = slopes.containsKey(s) ? slopes.get(s) : new ArrayList<Point>();
            if (!list.contains(points[i])) {
                list.add(points[i]);
            }
            if (!list.contains(points[j])) {
                list.add(points[j]);
            }
            slopes.put(s, list);
        }
    }
}
private void calculateSegments() {
    ArrayList<LineSegment> tempSegments = new ArrayList<LineSegment>();
    ArrayList<Double> keys = new ArrayList<Double>(slopes.keySet());
    if (keys.size() > 0) {
            for (int i = 0; i < keys.size(); i++) {
                ArrayList<Point> value = slopes.get(keys.get(i));
                if ((value.size() >= MIN_COLL_LENGTH)) {
                    tempSegments.add(createLineSegment(value));
                }
            }
        }
        arrayOfSegments = tempSegments.toArray((new LineSegment[tempSegments.size()]));
        segmentCount = arrayOfSegments.length;
    }

    private LineSegment createLineSegment(ArrayList<Point> value) {
        // if slope.value.length == 4,
        Point[] points = value.toArray(new Point[value.size()]);
        Arrays.sort(points);
        // create segment & save to arrayOfSegments
        return new LineSegment(points[0], points[points.length - 1]);
    }

    public int numberOfSegments() {
        return segmentCount;
    }
    // includes each line segment containing 4 points exactly once
    public LineSegment[] segments() {
        return arrayOfSegments;
    }
    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
