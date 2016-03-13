package com.sarahkim.kdtree;

import edu.princeton.cs.algs4.Point2D;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KdTreeTest {
    KdTree k;
    @Before
    public void setUp() throws Exception {
       k = new KdTree();
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
    public void testInsert() throws Exception {
        k.insert(new Point2D(1.0, 1.0));
        k.insert(new Point2D(0.0, 0.0));
        k.insert(new Point2D(0.3, 0.2));
        String str = k.stringVersion();
        String expectedStr = "(0.0, 0.0)(0.3, 0.2)(1.0, 1.0)";
        assertEquals(str, expectedStr);
    }
    @Test
    public void testInsert2() throws Exception {
        k.insert(new Point2D(0.9, 0.9));
        k.insert(new Point2D(0.1, 0.2));
        k.insert(new Point2D(0.3, 0.2));
        k.insert(new Point2D(0.1, 0.3));
        String str = k.stringVersion();
        String expectedStr = "(0.1, 0.2)(0.1, 0.3)(0.3, 0.2)(0.9, 0.9)";
        assertEquals(str, expectedStr);
    }

    @Test
    public void testContains() throws Exception {
        k.insert(new Point2D(0.9, 0.9));
        k.insert(new Point2D(0.1, 0.2));
        k.insert(new Point2D(0.3, 0.2));
        k.insert(new Point2D(0.1, 0.3));
        assertFalse(k.contains(new Point2D(0.0, 0.0)));
        assertTrue(k.contains(new Point2D(0.1, 0.2)));
        assertTrue(k.contains(new Point2D(0.3, 0.2)));
        assertFalse(k.contains(new Point2D(0.5, 0.2)));
    }

    @Test
    public void testStringVersion() throws Exception {

    }
}