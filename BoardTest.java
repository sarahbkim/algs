package com.sarahkim;

import org.junit.Test;
import org.junit.Before;


import static org.junit.Assert.*;

/**
 * Created by sarahbkim on 2/27/16.
 */
public class BoardTest {
    Board b;
    int[][] threes;
    int[][] threesZeroFirst;
    int[][] scrambledThrees;

    @Before
    public void setUp() throws Exception {
        threes = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        threesZeroFirst = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        scrambledThrees = new int[][]{{5, 2, 1}, {4, 0, 6}, {3, 8, 7}};

        b = new Board(threes);
    }

    @Test
    public void testDimension() throws Exception {
        int expectedLength = threes.length;
        int boardLength = b.dimension();
        assertEquals(expectedLength, boardLength);
    }

    @Test
    public void testHamming() throws Exception {

    }

    @Test
    public void testManhattan() throws Exception {

    }

    @Test
    public void testIsGoal() throws Exception {
        Board expectedGoal = new Board(threesZeroFirst);
        Board expectedFalse = new Board(scrambledThrees);
        assertTrue(b.isGoal());
        assertTrue(expectedGoal.isGoal());
        assertFalse(expectedFalse.isGoal());
    }

    @Test
    public void testTwin() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {
        Board sameB = new Board(threes);
        Board differentB = new Board(scrambledThrees);
        assertTrue(b.equals(sameB));
        assertFalse(b.equals(differentB));
    }

    @Test
    public void testNeighbors() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        System.out.println(b.toString());
    }
}