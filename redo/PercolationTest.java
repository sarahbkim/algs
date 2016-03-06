package com.sarahkim.redo;
import com.sarahkim.redo.Percolation;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
public class PercolationTest {
    Percolation p;
    int outOfBoundsMin;
    int outOfBoundsMax;
    int neg;
    @Before
    public void setUp() throws Exception {
        p = new Percolation(10);
        outOfBoundsMin = 0;
        outOfBoundsMax = 11;
        neg = -10;
    }
    @Test
    public void sitesInitializedProperly() throws Exception {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                assertFalse(p.isFull(i, j));
                assertFalse(p.isOpen(i, j));
                if (i == 1) {
                    p.open(i, j);
                    assertTrue(p.isFull(i, j));
                }
            }
        }
        assertFalse(p.percolates());
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testOpen() throws Exception {
        p.open(neg, 3);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testOOBOpen() throws Exception {
        p.open(9, outOfBoundsMax);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testOOBOpenMin() throws Exception {
        p.open(4, outOfBoundsMin);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() throws Exception {
        Percolation illegalP = new Percolation(neg);
    }
    @Test
    public void shouldPercolates() throws Exception {
        Percolation should = new Percolation(5);
        for(int i = 1; i<=5; i++) {
           for( int j = 1; j<=5; j++) {
               assertFalse(should.isOpen(i, j));
           }
        }
        should.open(1, 3);
        should.open(2, 3);
        should.open(3, 3);
        should.open(4, 3);
        should.open(5, 1);
        assertFalse(should.percolates());
        should.open(4, 1);
        assertFalse(should.percolates());
        should.open(4, 2);
        assertTrue(should.percolates());
    }
    @Test
    public void shouldPercolate() throws Exception {
        Percolation should = new Percolation(3);
        assertFalse(should.isOpen(3, 3));
        assertFalse(should.isFull(3, 3));
        should.open(3, 3);
        assertTrue(should.isOpen(3, 3));
        assertFalse(should.isFull(3, 3));
        assertFalse(should.percolates());

        assertFalse(should.isOpen(1, 1));
        should.open(1, 1);
        assertTrue(should.isOpen(1, 1));
        assertTrue(should.isFull(1, 1));
        assertFalse(should.percolates());

        assertFalse(should.isOpen(2, 2));
        assertFalse(should.isFull(2, 2));
        should.open(2, 2);
        assertTrue(should.isOpen(2, 2));
        assertFalse(should.isFull(2, 2));
        assertFalse(should.percolates());

        assertFalse(should.isOpen(1, 2));
        assertFalse(should.isFull(1, 2));
        should.open(1, 2);
        assertTrue(should.isOpen(1, 2));
        assertTrue(should.isFull(1, 2));
        assertTrue(should.isFull(2, 2));
        assertFalse(should.percolates());

        assertFalse(should.isOpen(2, 3));
        assertFalse(should.isFull(2, 3));
        should.open(2, 3);
        assertTrue(should.isOpen(2, 3));
        assertTrue(should.isFull(2, 3));
        assertTrue(should.percolates());
    }
    public void shouldNotPercolate() throws Exception {
        Percolation should = new Percolation(3);
        assertFalse(should.isOpen(3, 3));
        assertFalse(should.isFull(3, 3));
        should.open(3, 3);
        assertTrue(should.isOpen(3, 3));
        assertFalse(should.isFull(3, 3));
        assertFalse(should.percolates());

        assertFalse(should.isOpen(1, 1));
        should.open(1, 1);
        assertTrue(should.isOpen(1, 1));
        assertTrue(should.isFull(1, 1));
        assertFalse(should.percolates());

        assertFalse(should.isOpen(2, 2));
        assertFalse(should.isFull(2, 2));
        should.open(2, 2);
        assertTrue(should.isOpen(2, 2));
        assertFalse(should.isFull(2, 2));
        assertFalse(should.percolates());

        assertFalse(should.isOpen(1, 2));
        assertFalse(should.isFull(1, 2));
        should.open(1, 2);
        assertTrue(should.isOpen(1, 2));
        assertTrue(should.isFull(1, 2));
        assertFalse(should.percolates());
    }
//    @Test
//    public void convertIndexes() throws Exception {
//        Percolation t = new Percolation(4);
//        assertEquals(t.to1DArrayIdx(0, 0), 0);
//        assertEquals(t.to1DArrayIdx(0, 3), 3);
//        assertEquals(t.to1DArrayIdx(1, 0), 4);
//        assertEquals(t.to1DArrayIdx(1, 3), 7);
//        assertEquals(t.to1DArrayIdx(2, 1), 9);
//        assertEquals(t.to1DArrayIdx(2, 2), 10);
//        assertEquals(t.to1DArrayIdx(3, 2), 14);
//        assertEquals(t.to1DArrayIdx(3, 3), 15);
//
//        assertArrayEquals(t.to2DArrayIdx(0), new int[]{0, 0});
//        assertArrayEquals(t.to2DArrayIdx(3), new int[]{0, 3});
//        assertArrayEquals(t.to2DArrayIdx(4), new int[]{1, 0});
//        assertArrayEquals(t.to2DArrayIdx(7), new int[]{1, 3});
//        assertArrayEquals(t.to2DArrayIdx(9), new int[]{2, 1});
//        assertArrayEquals(t.to2DArrayIdx(10), new int[]{2, 2});
//        assertArrayEquals(t.to2DArrayIdx(14), new int[]{3, 2});
//        assertArrayEquals(t.to2DArrayIdx(15), new int[]{3, 3});
//    }
//    @Test
//    public void convertIndexesSmaller() throws Exception {
//        Percolation s = new Percolation(2);
//        assertEquals(s.to1DArrayIdx(0, 0), 0);
//        assertEquals(s.to1DArrayIdx(0, 1), 1);
//        assertEquals(s.to1DArrayIdx(1, 0), 2);
//        assertEquals(s.to1DArrayIdx(1, 1), 3);
//
//        assertArrayEquals(s.to2DArrayIdx(0), new int[]{0, 0});
//        assertArrayEquals(s.to2DArrayIdx(1), new int[]{0, 1});
//        assertArrayEquals(s.to2DArrayIdx(2), new int[]{1, 0});
//        assertArrayEquals(s.to2DArrayIdx(3), new int[]{1, 1});
//    }
}