package com.sarahkim;
import static java.util.Arrays.sort;
import static org.junit.Assert.*;
import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sarahbkim on 2/15/16.
 */
public class PointTest {
    private static final double DELTA = 0.001;
    private Point p, right, vertical, horizontal, same;

    @Before
    public void setUp() throws Exception {
        p = new Point(0, 0);
        right = new Point(3, 5);
        vertical = new Point(0, 10);
        horizontal = new Point(-10, 0);
        same = new Point(0, 0);
    }

    @Test
    public void testSlopeTo() throws Exception {
        assertEquals(1.667, p.slopeTo(right), DELTA);
        assertEquals(p.slopeTo(vertical), Double.POSITIVE_INFINITY, DELTA);
        assertEquals(p.slopeTo(same), Double.NEGATIVE_INFINITY, DELTA);
        assertEquals(+0.0, p.slopeTo(horizontal), DELTA);
    }
    @Test
    public void testCompareTo() throws Exception {
        assertEquals(p.compareTo(right), -1);
        assertEquals(p.compareTo(horizontal), 1);
        assertEquals(p.compareTo(same), 0);
        assertEquals(p.compareTo(vertical), -1);
    }
    @Test
    public void testComparator() throws Exception {
        Comparator c = p.slopeOrder();
        Point[] array = {right, vertical, horizontal, same};

        for(int i=0;i<array.length;i++) {
            System.out.println(array[i] + ", " + p.slopeTo(array[i]));
        }
        sort(array, c);
        System.out.println("-----");
        for(int i=0;i<array.length;i++) {
            System.out.println(array[i] + ", " + p.slopeTo(array[i]));
        }
    }

}