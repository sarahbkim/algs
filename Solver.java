package com.sarahkim;

import edu.princeton.cs.algs4.MinPQ;
import java.util.HashMap;
/**
 * Created by sarahbkim on 2/27/16.
 */
public class Solver {
    Board twin;
    Board init;
    MinPQ twinQueue;
    MinPQ initQueue;
    int initMoves;
    int twinMoves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        initMoves = 0;
        init = initial;
        initQueue = new MinPQ();
        HashMap<Integer, Board> savedBoards = new HashMap<Integer, Board>();
        addToQueue(init, savedBoards, initQueue); // initialize the queue

        while(!initQueue.isEmpty()) {
            int minKey = (int) initQueue.delMin();
            Board minBoard = savedBoards.get(minKey);

            if (minBoard.isGoal()) {
                System.out.println("Found goal board!");
                return;
            } else {
                Iterable<Board> neighbors = minBoard.neighbors();
                for(Board b : neighbors) {
                    addToQueue(b, savedBoards, initQueue);
                }
            }
        }
    }

    private void addToQueue(Board board, HashMap savedBoards, MinPQ priorityQueue) {
        int priority = initMoves + board.hamming();
        priorityQueue.insert(priority);
        savedBoards.put(priority, board);
    }

    public boolean isSolvable() {
        return true;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        // TODO; not sure about this!
        if (initMoves == -1 || twinMoves == -1) {
            return -1;
        } else {
            return initMoves;
        }
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

    }
}
