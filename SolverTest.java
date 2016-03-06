package com.sarahkim;

import org.junit.Before;
import org.junit.Test;
import com.sarahkim.Board;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by sarahbkim on 2/29/16.
 */
public class SolverTest {

    int[][] goal;
    int[][] threesZeroFirst;
    int[][] threesUnsolvable;
    int[][] foursUnsolvable;

    Board threesBoardGoal;
    Board threesZeroFirstBoard;
    Board unsolvable;
    Board unsolvableFours;
    @Before
    public void setUp() throws Exception {
        goal = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        threesBoardGoal = new Board(goal);
        threesZeroFirst = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        threesZeroFirstBoard = new Board(threesZeroFirst);

        threesUnsolvable = new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
        unsolvable = new Board(threesUnsolvable);

        foursUnsolvable = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 15, 14, 0}};
        unsolvableFours = new Board(foursUnsolvable);
    }

    @Test
    public void testIsSolvable() throws Exception {
        Solver s = new Solver(threesBoardGoal);
        int expectedMoves = 0;
        assertTrue(s.isSolvable());
        assertEquals(expectedMoves, s.moves());
        for (Board b : s.solution()) {
            System.out.println(b.toString());
        }
    }
    @Test
    public void testIsSolvableSwapped() throws Exception {
        Solver s = new Solver(threesZeroFirstBoard);
        assertTrue(s.isSolvable());
        for (Board b : s.solution()) {
            System.out.println(b.toString());
        }
    }
//    @Test
//    public void testUnsolvable() throws Exception {
//        Solver s = new Solver(unsolvable);
//        assertFalse(s.isSolvable());
//    }

}