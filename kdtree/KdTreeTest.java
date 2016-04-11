package com.sarahkim.kdtree;

import com.sarahkim.Point;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.*;

public class KdTreeTest {
    KdTree k;
    ArrayList<Point2D> hundredPoints;
    ArrayList<Point2D> thousandPoints;
    ArrayList<Point2D> tenThousPoints;
    public double getRandom() {
        Random rand = new Random();
        int max = 10;
        int min = 0;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return (double) randomNum /10;
    }
    @Before
    public void setUp() throws Exception {
        k = new KdTree();
        hundredPoints= new ArrayList<Point2D>(100);
        thousandPoints = new ArrayList<Point2D>(1000);
        tenThousPoints = new ArrayList<Point2D>(10000);
        int start = 0;
        while (start < 100) {
            Point2D randPt = new Point2D(getRandom(), getRandom());
            hundredPoints.add(randPt);
            start += 1;
        }
        start = 0;
        while (start < 1000) {
            Point2D randPt = new Point2D(getRandom(), getRandom());
            thousandPoints.add(randPt);
            start += 1;
        }
        start = 0;
        while (start < 10000) {
            Point2D randPt = new Point2D(getRandom(), getRandom());
            tenThousPoints.add(randPt);
            start += 1;
        }
    }
    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(k.isEmpty());
        k.insert(new Point2D(1.0, 0.2));
        assertFalse(k.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(k.size(), 0);
        k.insert(new Point2D(1.0, 0.2));
        assertEquals(k.size(), 1);
    }
    @Test
    public void testRandomSize() throws Exception {
        KdTree k = new KdTree();
        for(int i=0; i<hundredPoints.size(); i++) {
            k.insert(hundredPoints.get(i));
            assertEquals(k.size(), i+1);
        }
        assertEquals(k.size(), 100);
    }
    @Test
    public void testRandomSizeLarger() throws Exception {

        KdTree k = new KdTree();
        for(int i=0; i<tenThousPoints.size(); i++) {
            k.insert(tenThousPoints.get(i));
            assertEquals(k.size(), i+1);
        }
        assertEquals(k.size(), 10000);
    }
    @Test
    public void testContainsRandom() throws Exception {
        KdTree k = new KdTree();
        for(Point2D p : thousandPoints) {
            k.insert(p);
        }
        for(Point2D p : thousandPoints) {
            assertEquals(true, k.contains(p));
        }
    }
    @Test
    public void testNotContainsRandom() throws Exception {
        KdTree k = new KdTree();
        for(Point2D p: thousandPoints) {
            assertEquals(false, k.contains(p));
        }
    }
    @Test
    public void testBothContainsRandom() throws Exception {
        KdTree k = new KdTree();
        ArrayList<Point2D> inserted = new ArrayList<Point2D>();
        for (int i=0; i<hundredPoints.size(); i++) {
            if (i % 3 == 0) {
                k.insert(hundredPoints.get(i));
                inserted.add(hundredPoints.get(i));
            }
        }
        for (int i=0; i<hundredPoints.size(); i++) {
            if (inserted.indexOf(hundredPoints.get(i)) > -1) {
                assertTrue(k.contains(hundredPoints.get(i)));
            } else {
                assertFalse(k.contains(hundredPoints.get(i)));
            }
        }
    }
    @Test
    public void testRange() throws Exception {
        k.insert(new Point2D(0.1, 0.2));
        k.insert(new Point2D(0.3, 0.2));
        k.insert(new Point2D(0.1, 0.3));
        RectHV rect = new RectHV(0, 0, 0.2, 0.2);
        Iterable<Point2D> i = k.range(rect);
        for(Point2D p : i) {
            assertEquals(p, new Point2D(0.1, 0.2));
        }
    }
    @Test
    public void testNearest() throws Exception {
        k.insert(new Point2D(0.9, 0.9));
        k.insert(new Point2D(0.1, 0.2));
        k.insert(new Point2D(0.3, 0.2));
        k.insert(new Point2D(0.1, 0.3));
        Point2D nearest = k.nearest(new Point2D(0.6, 0.7));
        assertEquals(nearest, new Point2D(0.9, 0.9));
    }
    @Test
    public void testNearest2() throws Exception {
        k.insert(new Point2D(0.9, 0.9));
        k.insert(new Point2D(0.1, 0.2));
        k.insert(new Point2D(0.3, 0.2));
        k.insert(new Point2D(0.1, 0.3));
        Point2D nearest2 = k.nearest(new Point2D(0.1, 0.1));
        assertEquals(nearest2, new Point2D(0.1, 0.2));

    }
}