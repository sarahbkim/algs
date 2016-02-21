package com.sarahkim;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;
import java.lang.reflect.Method;

/**
 * Created by sarahbkim on 2/19/16.
 */
public class BruteCollinearPointsTest {
    private BruteCollinearPoints b;
    private BruteCollinearPoints b2;
    private Point[] points = new Point[4];
    private Point[] points2 = new Point[4];
    @Before
    public void setUp() throws Exception {
        // fill in arr
        points[0] = (new Point(0, 0));
        points[1] = (new Point(-1, 3));
        points[2] = (new Point(5, 5));
        points[3] = (new Point(-10, 3));

        // fill in arr2
        points2[0] = (new Point(0, 0));
        points2[1] = (new Point(1, 1));
        points2[2] = (new Point(2, 2));
        points2[3] = (new Point(3, 3));

        b = new BruteCollinearPoints(points);
        b2 = new BruteCollinearPoints(points2);
    }
    @Test
    public void testInitB1() throws Exception {
        assertEquals(0, b.numberOfSegments());
        assertEquals(0, b.segments().length);
    }
    @Test
    public void testInitB2() throws Exception {
        assertEquals(1, b2.numberOfSegments());
        assertEquals(1, b2.segments().length);
        assertEquals("(0, 0) -> (3, 3)", b2.segments()[0].toString());
    }
    @Test(expected = NullPointerException.class)
    public void testNullPoints() throws Exception {
        BruteCollinearPoints t = new BruteCollinearPoints(null);
    }
    @Test(expected = NullPointerException.class)
    public void testnullPoint() throws Exception {
        Point[] pointsWithNull = new Point[]{new Point(0, 0), null, new Point(3, 3)};
        BruteCollinearPoints t = new BruteCollinearPoints(pointsWithNull);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRepeatedPoint() throws Exception {
        Point[] pointsWithRep = new Point[]{new Point(0, 0), new Point(-30, 3), new Point(3, 3), new Point(0, 0), new Point(3, 3)};
        BruteCollinearPoints t = new BruteCollinearPoints(pointsWithRep);
    }
}