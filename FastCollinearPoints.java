package com.sarahkim;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import javax.sound.sampled.Line;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private ArrayList<LineSegment> tempSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        tempSegments = new ArrayList<LineSegment>();
        if (points == null) {
            throw new NullPointerException();
        }
        Arrays.sort(points);
        for (int i=0; i<points.length;i++) {
            Point origin = points[i];
            // remove origin from array
            Point[] firstHalf = Arrays.copyOfRange(points, 0, i);
            Point[] secondHalf = i + 1 < points.length ? Arrays.copyOfRange(points, i + 1, points.length) : null;

            // join new sub arrays
            Stream<Point> sa = Arrays.stream(firstHalf);
            Stream<Point> sb = secondHalf != null ? Arrays.stream(secondHalf) : null;
            Point[] toSort = sb != null ? Stream.concat(sa, sb).toArray(size -> new Point[size]) : firstHalf;

            // sort by slope
            Point[] sorted = sortedBySlope(toSort, origin);

            // find collinear points
            getCollinearPoints(sorted, origin);
        }
        convertTempSegments();
    }
    private Point[] sortedBySlope(Point[] arr, Point origin) {
        // Sort by slope
        Arrays.sort(arr, origin.slopeOrder());
        return arr;
    }
    private void getCollinearPoints(Point[] sortedPoints, Point origin) {
        int hasSameSlope = 0;
        int start = 0;

        // Find equal slopes & create line segments
        for(int i=0;i<sortedPoints.length;i++) {
            // Validate Points
            Point next = i+1 < sortedPoints.length ? sortedPoints[i+1] : null;
            Point curr = sortedPoints[i];
            validatePoint(origin, curr, next);

            // compare slopes curr/next
            if (next != null) {
                double currSlope = origin.slopeTo(curr);
                double nextSlope = origin.slopeTo(next);
                if (currSlope == nextSlope) {
                    hasSameSlope++;
                } else {
                    if (hasSameSlope >= 2) {
                        pointsIdxToLineSegment(start, i, sortedPoints, origin);
                    }
                    hasSameSlope = 0; // reset
                    start = i + 1;
                }
            } else if (hasSameSlope >= 2){
                pointsIdxToLineSegment(start, i, sortedPoints, origin);
            }
        }
    }
    private void pointsIdxToLineSegment(int start, int end, Point[] arr, Point origin) {
        ArrayList<Point> segmentPoints = new ArrayList<Point>();
        segmentPoints.add(origin);
        while(start <= end) {
            segmentPoints.add(arr[start]);
            start++;
        }
        Point[] s = segmentPoints.toArray((new Point[segmentPoints.size()]));
        Arrays.sort(s);
        createLineSegment(s);

    }
    private void convertTempSegments() {
        segments = tempSegments.toArray((new LineSegment[tempSegments.size()]));
    }
    private String pointsArrtoString(Point[] points) {
        String s = "";
        for(int i=0;i<points.length;i++) {
            s += points[i].toString() + ", ";
        }
        return s;
    }
    private void createLineSegment(Point[] points) {
        Point min = points[0];
        Point max = points[points.length - 1];
        LineSegment newL = new LineSegment(min, max);
        if (tempSegments.size() == 0) {
            tempSegments.add(newL);
        } else {
            for (int i = 0; i < tempSegments.size(); i++) {
                LineSegment l = tempSegments.get(i);
                if (l.toString().equals(newL.toString())) {
                    return;
                }
            }
            tempSegments.add(newL);
        }
    }
    private void validatePoint(Point origin, Point p, Point next) {
        if (p == null || origin == null) {
            throw new NullPointerException();
        }
        if (origin.compareTo(p) == 0) {
            throw new IllegalArgumentException();
        }
        if (next != null && p.compareTo(next) == 0) {
            throw new IllegalArgumentException();
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
