package com.sarahkim;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by sarahbkim on 2/19/16.
 */

// Corner cases. Throw a java.lang.NullPointerException either the argument to the constructor is null
// or if any point in the array is null. Throw a java.lang.IllegalArgumentException if the argument to
// the constructor contains a repeated point.
public class BruteCollinearPoints {
    int segmentCount;
    LineSegment[] arrayOfSegments;
    HashMap<Double, ArrayList<Point>> slopes = new HashMap<Double, ArrayList<Point>>();

    public BruteCollinearPoints(Point[] points) throws Exception {
        if(points == null) { throw new NullPointerException(); };
        segmentCount = 0;

        validatePoints(points);
        calculateSlopes(points);
        calculateSegments();
    }
    private void validatePoints(Point[] points) throws Exception{
        Arrays.sort(points);
        for(int i=0;i<points.length;i++) {
            if(points[i] == null) { throw new NullPointerException(); };
            if(i < points.length-1) {
                if (points[i].compareTo(points[i+1]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
    private void calculateSlopes(Point[] points) {
        // calculate the slope
        for(int i=0;i<points.length;i++) {
            for(int j=i+1;j<points.length;j++) {
                double s = points[i].slopeTo(points[j]);
                ArrayList list;
                list = slopes.containsKey(s) ? slopes.get(s) : new ArrayList<Point>();
                if(!list.contains(points[i])) { list.add(points[i]); }
                if(!list.contains(points[j])) { list.add(points[j]); }
                slopes.put(s, list);
            }
        }
    }
    private void calculateSegments() {
        ArrayList<LineSegment> tempSegments = new ArrayList<LineSegment>();
        ArrayList<Double> keys = new ArrayList<Double>(slopes.keySet());
        if(keys.size() > 0) {
            for (int i = 0; i < keys.size(); i++) {
                ArrayList<Point> value = slopes.get(keys.get(i));
                if ((value.size() >= 4)) {
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
        LineSegment s = new LineSegment(points[0], points[points.length-1]);
        return s;
    }

    public int numberOfSegments() {
        return segmentCount;
    }
    // includes each line segment containing 4 points exactly once
    public LineSegment[] segments() {
        return arrayOfSegments;
    }

}
