package com.sarahkim;

import java.util.Random;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Queue;


public class Board {
    private int dimension;
    private int[] gameBoard;
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        dimension = blocks.length;
        gameBoard = new int[dimension*dimension];
        for (int i=0;i<blocks.length;i++){
            for (int j=0;j<blocks.length;j++) {
                int idx = convertTo1DArrayIdx(i, dimension, j);
                gameBoard[idx] = blocks[i][j];
            }
        }
    }

    // board dimension N
    public int dimension() {
        return dimension;
    }
    // number of blocks out of place
    public int hamming() {
        int distance = 0;
        for (int i=0; i<this.gameBoard.length; i++) {
            int val = this.gameBoard[i];
            if (val != 0 && val != i+1) { distance++; }
        }
        return distance;
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int distance = 0;
        for (int i=0; i<this.gameBoard.length; i++) {
            int blockVal = this.gameBoard[i];
            if (blockVal != 0) {
                int currRow = i / dimension;
                int currCol = i % dimension;

                int expectedRow = (blockVal - 1) / dimension;
                int expectedCol = (blockVal - 1) % dimension;

                distance += Math.abs(expectedCol - currCol);
                distance += Math.abs(expectedRow - currRow);
            }
        }
        return distance;
    }
    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }
    private int convertTo1DArrayIdx(int row, int rowLength, int col) {
        return (row * rowLength) + col;
    }
    // does this board equal y?
    public boolean equals(Object y) {
        if(y == this) return true;
        if(y == null) return false;
        if(y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if(that.dimension() != dimension) return false;
        // iterate through each element in boards, compare
        for(int i=0;i<dimension*dimension;i++) {
            if(this.gameBoard[i] != that.gameBoard[i]) return false;
        }
        return true;
    }
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random randomGenerator = new Random();
        while (numbers.size() < 3) {
            int random = randomGenerator.nextInt((gameBoard.length-1)) + 1;
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }

        int swap = numbers.get(0);
        int swap2 = numbers.get(1);
        int[][] copy = new int[dimension][dimension];
        int swapCurrRow = 0;
        int swapCurrCol = 0;
        int swap2CurrRow = 0;
        int swap2CurrCol =0;

        for(int i=0; i<gameBoard.length;i++) {
            int currRow = i / dimension;
            int currCol = i % dimension;
            if (swap == gameBoard[i]) {
                swapCurrRow = currRow;
                swapCurrCol = currCol;
            }
            if (swap2 == gameBoard[i]) {
                swap2CurrRow = currRow;
                swap2CurrCol = currCol;
            }
            copy[currRow][currCol] = gameBoard[i];
        }
        copy[swapCurrRow][swapCurrCol] = swap2;
        copy[swap2CurrRow][swap2CurrCol] = swap;
        return new Board(copy);
    }
    // string representation of this board (in the output format specified below)
    public String toString() {
        int round = 1;
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i=0; i<gameBoard.length; i++) {
            s.append(String.format("%2d ", this.gameBoard[i]));
            // add new line
            if ((i < gameBoard.length-1) && ((i + 1) / round == dimension)) {
                s.append("\n");
                round++;
            }
        }
        return s.toString();
    }
    public Iterable<Board> neighbors() {
        for (int i=0; i<gameBoard.length; i++) {
            if (gameBoard[i] == 0) return calculateNeighbors(i);
        }
        return null;
    }

    private Iterable<Board> calculateNeighbors(int idx) {
        int currRow = idx / dimension;
        int currCol = idx % dimension;
        Queue<Board> q = new Queue<Board>();
        if (currRow > 0) q.enqueue(new Board(swap(currRow, currCol, currRow-1, currCol)));
        if (currRow < dimension-1) q.enqueue(new Board(swap(currRow, currCol, currRow+1, currCol)));
        if (currCol > 0) q.enqueue(new Board(swap(currRow, currCol, currRow, currCol-1)));
        if (currCol < dimension-1) q.enqueue(new Board(swap(currRow, currCol, currRow, currCol+1)));
        return q;
    }

    private int[][] swap(int currRow, int currCol, int newRow, int newCol) {
        int[][] copy = copyGameBoard();
        int tmp = copy[newRow][newCol];
        copy[newRow][newCol] = copy[currRow][currCol];
        copy[currRow][currCol] = tmp;
        return copy;
    }

    private int[][] copyGameBoard() {
        int[][] copy = new int[dimension][dimension];
        // create a copy
        for(int i=0; i<gameBoard.length;i++) {
            int row = i / dimension;
            int col = i % dimension;
            copy[row][col]  = gameBoard[i];
        }
        return copy;
    }
}
