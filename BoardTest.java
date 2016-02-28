package com.sarahkim;

import org.junit.Test;
import org.junit.Before;


import static org.junit.Assert.*;

/**
 * Created by sarahbkim on 2/27/16.
 */
public class BoardTest {
    Board b;
    int[][] twos;
    int[][] threes;
    int[][] threesZeroFirst;
    int[][] scrambledThrees;
    int[][] scrambledThreeV2;
    int[][] fours;

    @Before
    public void setUp() throws Exception {
        twos = new int[][]{{0, 1}, {3, 2}};
        threes = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        threesZeroFirst = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        scrambledThrees = new int[][]{{5, 2, 1}, {4, 0, 6}, {3, 8, 7}};
        scrambledThreeV2 = new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        fours = new int[][]{{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}};

        b = new Board(threes);
    }

    @Test
    public void testDimension() throws Exception {
        int expectedLength = threes.length;
        int boardLength = b.dimension();
        assertEquals(expectedLength, boardLength);
    }

//    8  1  3        1  2  3     1  2  3  4  5  6  7  8    1  2  3  4  5  6  7  8
//    4     2        4  5  6     ----------------------    ----------------------
//    7  6  5        7  8        1  1  0  0  1  1  0  1    1  2  0  0  2  2  0  3
    @Test
    public void testHamming() throws Exception {
        Board test = new Board(scrambledThreeV2);
        assertFalse(test.isGoal());
        int expected = 5;
        assertEquals(expected, test.hamming());
    }

    @Test
    public void testHammingArrayOfTwos() throws  Exception {
        Board test = new Board(twos);
        assertFalse(test.isGoal());
        int expected = 2;
        assertEquals(expected, test.hamming());
    }

    @Test
    public void testManhattan() throws Exception {
        Board test = new Board(scrambledThreeV2);
        assertFalse(test.isGoal());
        int expected = 10;
        assertEquals(expected, test.manhattan());
    }

    @Test
    public void testManhattanArrayOfTwos() throws Exception {
        Board test = new Board(twos);
        assertFalse(test.isGoal());
        int expected = 2;
        assertEquals(expected, test.manhattan());
    }

    @Test
    public void testIsGoal() throws Exception {
        Board expectedGoal = new Board(threesZeroFirst);
        Board expectedFalse = new Board(scrambledThrees);
        assertTrue(b.isGoal());
        assertFalse(expectedGoal.isGoal());
        assertFalse(expectedFalse.isGoal());
    }

    @Test
    public void testTwin() throws Exception {
        Board test = b.twin();
        System.out.println(b.toString());
        System.out.println(test.toString());
    }

    @Test
    public void testTwinOfScrambled() throws Exception {
        Board test = new Board(scrambledThreeV2);
        Board twin = test.twin();
        System.out.println(test.toString());
        System.out.println(twin.toString());
    }

    @Test
    public void testTwinsOfFours() throws Exception {
        Board test = new Board(fours);
        Board twin = test.twin();
        System.out.println("hi" + test.toString());
        System.out.println(twin.toString());
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
        Board fourBoard = new Board(fours);
//        System.out.println(b.toString());
//        System.out.println(fourBoard.toString());
    }
}