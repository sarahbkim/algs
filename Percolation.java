package com.sarahkim;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// TODO: create an extra top/bottom site in siteConnections, so initializes
// with (N*N)+2, where the topSite = N*N + 1, bottom = N*N + 2
// Connect top and bottom rows with topSite and bottomSite, respectively on initialize
public class Percolation {
    private WeightedQuickUnionUF siteConnections;
    private int[] siteStates;
    private int length;

    private int topSite;
    private int bottomSite;

    private static final int BLOCKED = 0;
    private static final int OPENED = 1;

    /* create N x N grid, with all sites blocked */
    public Percolation(int N) {
        if (N <=0 ) {
            throw new IndexOutOfBoundsException();
        }
        length = N;
        siteStates = new int[length*length];
        siteConnections = new WeightedQuickUnionUF((length*length) + 2);
        topSite = length*length + 1;
        bottomSite = topSite+1;

        setUpVirtualSites();
    }

    private void setUpVirtualSites() {
        // connect top row to topSite
        for(int i=0;i<length;i++){
            siteConnections.union(i, topSite-1);
        }
        // connect bottom row to bottomSite
        for(int j=((length*length)-length-1);j<length*length;j++) {
            siteConnections.union(j, bottomSite-1);
        }
    }

    public boolean isOpen(int i, int j) {
        if(!withinRange(i, j)) {
            throw new IndexOutOfBoundsException();
        }
        int index = convertToFlatArrayIndex(i, j);
        return siteStates[index] == OPENED;
    }

    public boolean isFull(int i, int j) {
        if(!withinRange(i, j)) {
            throw new IndexOutOfBoundsException();
        }
        int index = convertToFlatArrayIndex(i, j);
        return isOpen(i, j) && (siteConnections.connected(index, topSite-1) || siteConnections.connected(index, bottomSite-1));
    }

    public void open(int i, int j) {
        if(!withinRange(i, j)) {
            throw new IndexOutOfBoundsException();
        }

        int index = convertToFlatArrayIndex(i, j);
        if(siteStates[index] == BLOCKED) { siteStates[index] = OPENED; }
        checkNeighbors(i, j);
    }

    public boolean percolates() {
        return siteConnections.connected(topSite-1, bottomSite-1);
    }

    // PRIVATE HELPER METHODS //
    private int convertToFlatArrayIndex(int i, int j) {
        if(i == 1 && j == 1) {
            return 0;
        }
        return (i*length)-(length-1)+(j-1)-1;
    }

    private int[] convertIntToArray(int k) {
        int i = (int) Math.ceil( (double)k/(double)length);
        if(i==0) { i = 1; }
        int j = k - ((length*i) - length);
        if(j==0) { j=1;}

        return new int[]{i, j};
    }

    private boolean isConnected(int i, int j) {
        return siteConnections.connected(i-1, j-1);
    }

    // if either me or my neighbor is "FULL", make each other all 'FULL' and connect them in the grid
    private void checkNeighbors(int i, int j) {
        // This will calculate to zero-based index!!!!
        int currIdx = convertToFlatArrayIndex(i, j);

        // These calculated to one-based index!!!
        int currentRowEnd = i*length;
        int currentRowStart = currentRowEnd-(length-1);

        int top = currentRowStart - length +j-1;
        int bottom = currentRowStart + length +j-1;
        int left = currentRowStart + j-2;
        int right = currentRowStart + j;

        if(top > 0 && isOpen(convertIntToArray(top)[0], convertIntToArray(top)[1])) {
            siteConnections.union(top-1, currIdx);
        }
        if(bottom <= length*length && isOpen(convertIntToArray(bottom)[0], convertIntToArray(bottom)[1])) {
            siteConnections.union(bottom-1, currIdx);
        }
        if(left >= currentRowStart && left <= currentRowEnd && isOpen(convertIntToArray(left)[0], convertIntToArray(left)[1])) {
            siteConnections.union(currIdx, left-1);
        }
        if(right >= currentRowStart && right <= currentRowEnd && isOpen(convertIntToArray(right)[0], convertIntToArray(right)[1])) {
            siteConnections.union(currIdx, right-1);
        }
    }

    // Checks if [i, j] is a valid row, col
    private boolean withinRange(int i, int j) {
        if(i < 1 || i > length) { return false; }
        return !(j < 1 || j > length);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        System.out.println("This is the new Percolation object, 'p': " + p);

        for(int i=1;i<=5;i++) {
            System.out.println("Is connected to topSite: " + i + " " + p.isConnected(i, 26));
        }
        for(int i=20;i<=25;i++) {
            System.out.println("Is connected to bottomSite: " + i + " " + p.isConnected(i, 27));
        }

        System.out.println("Checking helper function: convertToFlatArrayIndex");
        System.out.println(p.convertToFlatArrayIndex(1, 1) + " should be 0");
        System.out.println(p.convertToFlatArrayIndex(2, 1) + " should be 5");
        System.out.println(p.convertToFlatArrayIndex(1, 3) + " should be 2");
        System.out.println(p.convertToFlatArrayIndex(2, 3) + " should be 7");
        System.out.println(p.convertToFlatArrayIndex(4, 4) + " should be 18");
        System.out.println(p.convertToFlatArrayIndex(5, 5) + " should be 24");

        System.out.println("Checking helper function: convertIntToArray:");
        System.out.println(p.convertIntToArray(0)[0] + ", " + p.convertIntToArray(0)[1] + " should be [1, 1]");
        System.out.println(p.convertIntToArray(6)[0] + ", " + p.convertIntToArray(6)[1] + " should be [2, 1]");
        System.out.println(p.convertIntToArray(3)[0] + ", " + p.convertIntToArray(3)[1] + " should be [1, 3]");
        System.out.println(p.convertIntToArray(8)[0] + ", " + p.convertIntToArray(8)[1] + " should be [2, 3]");
        System.out.println(p.convertIntToArray(25)[0] + ", " + p.convertIntToArray(25)[1] + " should be [5, 5]");

//        Percolation p2 = new Percolation(2);
//        System.out.println("This is another Percolation object, 'p2");
//        System.out.println("Checking helper function: convertToFlatArrayIndex");
//        System.out.println(p2.convertToFlatArrayIndex(1, 1) + " should be 0");
//        System.out.println(p2.convertToFlatArrayIndex(2, 1) + " should be 3");
//        System.out.println(p2.convertToFlatArrayIndex(1, 2) + " should be 2");
//        System.out.println(p2.convertToFlatArrayIndex(2, 2) + " should be 4");

//
//        System.out.println("Checking helper function: convertIntToArray:");
//        System.out.println(p2.convertIntToArray(1)[0] + ", " + p.convertIntToArray(1)[1] + " should be [1, 1]");
//        System.out.println(p2.convertIntToArray(2)[0] + ", " + p.convertIntToArray(2)[1] + " should be [1, 2]");
//        System.out.println(p2.convertIntToArray(3)[0] + ", " + p.convertIntToArray(3)[1] + " should be [2, 1]");
//        System.out.println(p2.convertIntToArray(4)[0] + ", " + p.convertIntToArray(4)[1] + " should be [2, 2]");
//


        System.out.println("Checking function: open");
        p.open(1, 1);
        System.out.println("[1,1] isOpen: " + p.isOpen(1, 1));
        System.out.println("[1,1] isFull: " + p.isFull(1, 1));
        p.open(2, 1);
        System.out.println("[2,1] isOpen: " + p.isOpen(2, 1));
        System.out.println("[2,1] isFull: " + p.isFull(2, 1));
        p.open(4, 1);
        System.out.println("[4,1] isOpen: " + p.isOpen(4, 1));
        System.out.println("[4,1] isFull: " + p.isFull(4, 1));
        p.open(1, 4);
        System.out.println("[1,4] isOpen: " + p.isOpen(1, 4));
        System.out.println("[1,4] isFull: " + p.isFull(1, 4));
        p.open(5, 3);
        System.out.println("[5,3] isOpen: " + p.isOpen(5, 3));
        System.out.println("[5,3] isFull: " + p.isFull(5, 3));
        p.open(4, 4);
        System.out.println("[4,4] isOpen: " + p.isOpen(4, 4));
        System.out.println("[4,4] isFull: " + p.isFull(4, 4));

        p.open(3, 4);
        System.out.println("[3, 4] isOpen: " + p.isOpen(3, 4));
        System.out.println("[3, 4] isFull: " + p.isFull(3, 4));
        p.open(4, 3);
        System.out.println("[4,3] isOpen: " + p.isOpen(4, 3));
        System.out.println("[4,3] isFull: " + p.isFull(4, 3));
        System.out.println(p.percolates());
        p.open(2, 2);
        System.out.println(p.percolates());
        p.open(3, 2);
        System.out.println("3, 2 is Open: " + p.isOpen(3, 2));
        System.out.println("3, 2 is Full: " + p.isFull(3, 2));
        System.out.println(p.percolates());
        p.open(3,3);
        System.out.println(p.percolates());

    }

}

