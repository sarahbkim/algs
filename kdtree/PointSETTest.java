package com.sarahkim.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PointSETTest {
    PointSET set;
    @Before
    public void setUp() throws Exception {
        set = new PointSET();
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(set.isEmpty());
        set.insert(new Point2D(1.3, 2.4));
        assertFalse(set.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(0, set.size());
        set.insert(new Point2D(1.3, 2.4));
        set.insert(new Point2D(1.3, -10));
        set.insert(new Point2D(3.2, 2.4));
        assertEquals(3, set.size());
    }

    @Test
    public void testContains() throws Exception {
        set.insert(new Point2D(1.3, 2.4));
        set.insert(new Point2D(1.3, -10));
        set.insert(new Point2D(3.2, 2.4));
        assertTrue(set.contains(new Point2D(1.3, 2.4)));
        assertTrue(set.contains(new Point2D(1.3, -10)));
        assertTrue(set.contains(new Point2D(3.2, 2.4)));
        assertFalse(set.contains(new Point2D(10, -10)));
        assertFalse(set.contains(new Point2D(3, -10)));
    }

    @Test
    public void testRange() throws Exception {
        set.insert(new Point2D(1.3, 2.4));
        set.insert(new Point2D(1.3, -10));
        set.insert(new Point2D(3.2, 2.4));
        RectHV rectEmpty = new RectHV(-2.0, -3.0, -1.0, -1.0);
        Iterable<Point2D> i = set.range(rectEmpty);
        int expectedCount = 0;
        int count = 0;
        for(Point2D p : i) {
            count++;
        }
        assertEquals(expectedCount, count);

    }
    @Test
    public void testRangeFull() throws Exception {
        set.insert(new Point2D(1.3, 2.4));
        set.insert(new Point2D(1.3, -10));
        set.insert(new Point2D(3.2, 2.4));

        RectHV rectFull = new RectHV(0, 0, 3.2, 4.0);
        Iterable<Point2D> i2 = set.range(rectFull);
        int expectedCount = 2;
        int count = 0;
        for(Point2D p : i2) {
            count++;
        }
        assertEquals(expectedCount, count);
    }

    @Test
    public void testNearest() throws Exception {
        set.insert(new Point2D(0.0, 0.0));
        set.insert(new Point2D(-2.0, 0.0));
        set.insert(new Point2D(-1.0, 3.0));
        set.insert(new Point2D(2.0, -2.0));
        set.insert(new Point2D(-1.0, 2.0));
        set.insert(new Point2D(1.0, 2.0));

        Point2D n = set.nearest(new Point2D(0, 0));
        assertEquals(new Point2D(0, 0), n);
        Point2D n2 = set.nearest(new Point2D(-1.0, -4.0));
        assertEquals(new Point2D(2, -2), n2);
        Point2D n3 = set.nearest(new Point2D(2.0, 2.0));
        assertEquals(new Point2D(1.0, 2.0), n3);
    }
    @Test
    public void testDraw() throws Exception {
        set.insert(new Point2D(1.3, 2.4));
        set.insert(new Point2D(1.3, -10));
        set.insert(new Point2D(3.2, 2.4));
        set.draw();
    }

}