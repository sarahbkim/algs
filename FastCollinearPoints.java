package com.sarahkim;
import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by sarahbkim on 2/20/16.
 */
public class FastCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        Arrays.sort(points);
        Point origin = points[0];
        calculateSlope(origin, points);
    }
    private void calculateSlope(Point origin, Point[] points) {
        Comparator c = origin.slopeOrder();
        Arrays.sort(points, c);
        double currSlope = origin.slopeTo(points[0]);
        int sameCount = 0;
        int start = 0;
        ArrayList<LineSegment> tempSegments = new ArrayList<LineSegment>();
        for(int i=1;i<points.length;i++) {
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
    public int numberOfSegments() {
        return segments.length;
    }
    public LineSegment[] segments() {
        return segments;
    }
}
