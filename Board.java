package com.sarahkim;

/**
 * Created by sarahbkim on 2/27/16.
 */
public class Board {
    private int dimension;
    private int[][] gameBoard;
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        dimension = blocks.length;
        gameBoard = blocks;
    }

    // board dimension N
    public int dimension() {
       return dimension;
    }
    // number of blocks out of place
    public int hamming() {

    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {

    }
    // is this board the goal board?
    public boolean isGoal() {
        int startBlock = this.gameBoard[0][0];
        int endBlock = this.gameBoard[dimension()][dimension()];
        // do loop
        if (startBlock == 0 || endBlock == 0) {
            for (int i=0; i<dimension(); i++) {
                for (int j=0; j<dimension()-1; j++) {
                    int expectedVal = convertTo1DArrayIdx(i, this.dimension(), j);
                    if (endBlock == 0) expectedVal++;
                    int currBlockVal = this.gameBoard[i][j];
                    if (currBlockVal != expectedVal) return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    private int convertTo1DArrayIdx(int row, int rowLength, int col) {
        return (row * rowLength) + col;
    }
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {

    }
    // does this board equal y?
    public boolean equals(Object y) {
        if(y == null) return false;
        if(y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if(that.dimension() != this.dimension()) return false;
        // iterate through each element in boards, compare
        for(int i=0;i<dimension();i++) {
            for(int j=0;j<dimension();j++) {
                if(this.gameBoard[i][j] != that.gameBoard[i][j]) return false;
            }
        }
        return true;
    }
    // all neighboring boards
    public Iterable<Board> neighbors() {

    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        String str = "";
        str += this.dimension() + "\n";
        for (int i=0; i<dimension(); i++) {
            for (int j=0; j<dimension(); j++) {
                str += this.gameBoard[i][j] + " ";
            }
            if (i < dimension() -1) str += "\n";
        }
        return str;
    }

}
