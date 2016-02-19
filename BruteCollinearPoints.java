package com.sarahkim;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sarahbkim on 2/19/16.
 */
// Write a program BruteCollinearPoints.java that examines 4 points at a time and checks
// whether they all lie on the same line segment, returning all such line segments.
// To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes between p and q,
// between p and r, and between p and s are all equal.

// Corner cases. Throw a java.lang.NullPointerException either the argument to the constructor is null
// or if any point in the array is null. Throw a java.lang.IllegalArgumentException if the argument to
// the constructor contains a repeated point.
public class BruteCollinearPoints {
    int segmentCount;
    LineSegment[] arrayOfSegments;
    HashMap<Double, ArrayList> slopes = new HashMap<Double, ArrayList>();

    public BruteCollinearPoints(Point[] points) {
        // initialize segmentCount
        segmentCount = 0;
        calculateSlopes(points);
        calculateSegments();
    }
    private void calculateSlopes(Point[] points) {
        // calculate the slope
        for(int i=0;i<points.length;i++) {
            for(int j=i+1;j<points.length-1;j++) {
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
        // loop through slopes
        // if slope.value.length == 4,
            // find lowest & highest point (sort by )
            // create segment & save to arrayOfSegments
            // increment segmentCount
    }
    public int numberOfSegments() {
        return segmentCount;
    }
    // includes each line segment containing 4 points exactly once
    public LineSegment[] segments() {
        return arrayOfSegments;
    }

}
