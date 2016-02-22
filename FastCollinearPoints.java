package com.sarahkim;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by sarahbkim on 2/20/16.
 */
public class FastCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        Arrays.sort(points);
        Point origin = points[0];
        calculateSlope(origin, points);
    }
    private void calculateSlope(Point origin, Point[] points) {
        double currSlope = origin.slopeTo(points[0]);
        int sameCount = 0;
        int start = 0;
        ArrayList<LineSegment> tempSegments = new ArrayList<LineSegment>();

        // Sort by slope
        Arrays.sort(points, origin.slopeOrder());

        // Find equal slopes & create line segments
        for(int i=1;i<points.length-1;i++) {
            // Validate Points
            if ( i < points.length-2 ) {
                validatePoint(origin, points[i], points[i + 1]);
            }
            double nextSlope = origin.slopeTo(points[i]);
            if (currSlope == nextSlope) {
                sameCount++;
            }
            if (currSlope != nextSlope) {
                currSlope = nextSlope;
                if (sameCount >= 3)  {
                    // create line segment from start to end
                    LineSegment l = new LineSegment(points[start], points[i-1]);
                    tempSegments.add(l);
                }
                sameCount = 0;
                start = i;
            }
        }
        segments = tempSegments.toArray((new LineSegment[tempSegments.size()]));
    }
    private void validatePoint(Point origin, Point p, Point next) {
        if (p == null || origin == null) {
            throw new NullPointerException();
        }
        if (origin.compareTo(p) == 0) {
            throw new IllegalArgumentException();
        }
        if (next != null) {
            if (p.compareTo(next) == 0) {
                throw new IllegalArgumentException();
            }
        }

    }
    public int numberOfSegments() {
        return segments.length;
    }
    public LineSegment[] segments() {
        return segments;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
