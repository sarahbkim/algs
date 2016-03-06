package com.sarahkim.redo;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] sites;
    private WeightedQuickUnionUF connections;
    private int dim;
    private int virtualTopSiteIndex;
    private int virtualBottomSiteIndex;

    private static final int BLOCKED = 0;
    private static final int OPENED = 1;

    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException();
        sites = new int[N*N];
        connections = new WeightedQuickUnionUF((N*N) + 2);
        dim = N;
        virtualTopSiteIndex = N*N;
        virtualBottomSiteIndex = virtualTopSiteIndex + 1;

        // initializes all sites to be blocked
        for (int i = 0; i < N*N; i++) {
            // connect top row to topSite
            if (i >= 0 && i < dim) {
                connections.union(i, virtualTopSiteIndex);
            }
            // connect bottom row to bottomSite
            if (i >= (dim*dim) - dim && i < dim*dim) {
                connections.union(i, virtualBottomSiteIndex);
            }
            sites[i] = BLOCKED;
        }
    }
    public void open(int i, int j) {
        checkInputs(i, j);
        int sI = sanitizeIdxInput(i);
        int sJ = sanitizeIdxInput(j);
        int convertedIdx = to1DArrayIdx(sI, sJ);

        sites[convertedIdx] = OPENED;
        connectNeighbors(sI, sJ, convertedIdx);
    }
    // takes in sanitized inputs
    private void connectNeighbors(int sI, int sJ, int convertedIdx) {
        int top = sI - 1;
        int bottom = sI + 1;
        int left = sJ - 1;
        int right = sJ + 1;

        openNeighbor(top, sJ, convertedIdx);
        openNeighbor(bottom, sJ, convertedIdx);
        openNeighbor(sI, left, convertedIdx);
        openNeighbor(sI, right, convertedIdx);
    }
    // takes in sanitized inputs
    private void openNeighbor(int i, int j, int originIdx) {
        if (i >= 0 && i < dim && j >= 0 && j < dim) {
            int idx = to1DArrayIdx(i, j);
            if (sites[idx] == OPENED) connections.union(idx, originIdx);
        }
    }
    public boolean isOpen(int i, int j) {
        checkInputs(i, j);
        int sI = sanitizeIdxInput(i);
        int sJ = sanitizeIdxInput(j);
        int convertedIdx = to1DArrayIdx(sI, sJ);

        return sites[convertedIdx] == OPENED;
    }
    public boolean isFull(int i, int j) {
        checkInputs(i, j);
        int sI = sanitizeIdxInput(i);
        int sJ = sanitizeIdxInput(j);
        int convertedIdx = to1DArrayIdx(sI, sJ);

        return isOpen(i, j) && connections.connected(convertedIdx, virtualTopSiteIndex);
    }
    public boolean percolates() {
        return connections.connected(virtualTopSiteIndex, virtualBottomSiteIndex);
    }
    private void checkInputs(int i, int j) {
       if(i < 1 || j < 1 || i > dim || j > dim) {
           throw new IndexOutOfBoundsException();
       }
    }
    private int sanitizeIdxInput(int i) {
        return i-1;
    }
    private int[] to2DArrayIdx(int i) {
        // returns (row, column)
        return new int[]{Math.floorDiv(i, dim), (i % dim)};
    }
    private int to1DArrayIdx(int i, int j) {
        return i * dim + j;
    }
}
