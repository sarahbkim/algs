package com.sarahkim;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

import java.util.Comparator;
import java.util.Iterator;
import com.sarahkim.Board;

public class Solver {
    private int moves;
    private Queue<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        Board currBoard = initial;
        Board currTwinBoard = initial.twin();
        moves = 1;

        Stepper realStepper = new Stepper(currBoard);
        Stepper twinStepper = new Stepper(currTwinBoard);

        while (!realStepper.isGoal() && !twinStepper.isGoal()) {
            moves++;
            realStepper.nextStep();
            twinStepper.nextStep();
        }
        if ( realStepper.currBoardIsGoal() ) {
            solution = realStepper.getSolution();
        } else {
            moves = -1;
        }

    }

    private class Stepper {
        MinPQ<Board> priorityQ;
        Queue<Board> solution;
        Board currBoard;

        public Stepper(Board b) {
            Comparator<Board> c = new priorityComparator();
            priorityQ = new MinPQ<Board>(10, c);
            solution = new Queue<Board>();
            priorityQ.insert(b);
            solution.enqueue(b);
        }

        private boolean currBoardIsGoal() {
            return currBoard.isGoal();
        }

        private boolean isGoal() {
            if (priorityQ.size() > 0) {
                currBoard = priorityQ.delMin();
                solution.enqueue(currBoard);
                return currBoard.isGoal();
            }
            return false;
        }

        private void nextStep() {
            Iterable<Board> neighbors = currBoard.neighbors();
            for(Board b : neighbors) {
                priorityQ.insert(b);
            }
        }

        private Queue<Board> getSolution() {
            return solution;
        }

    }

    class priorityComparator implements Comparator<Board> {
        @Override
        public int compare(Board b1, Board b2) {
            int b1Priority = b1.manhattan();
            int b2Priority = b2.manhattan();
            if (b1Priority == b2Priority)
                return 0;
            else if (b1Priority > b2Priority)
                return 1;
            else
                return -1;
        }
    }

    public boolean isSolvable() {
        return moves >= 0;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            return solution;
        }
        return null;
    }
}
