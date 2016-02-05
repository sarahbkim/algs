package com.sarahkim;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import com.sarahkim.Percolation;

/**
 * Created by sarahbkim on 2/5/16.
 */
public class PercolationStats {
    int gridSize;
    int repeat;
    double[] thresholds;
    double criticalValue = -1.96;

    // perform T independent experiments on an NxN grid
    public PercolationStats(int N, int T) {
        gridSize = N;
        repeat = T;
        thresholds = new double[T];
    }

    private void performAllExperiments(int N, int reps) {
        for(int i=1; i<reps;i++) {
            performSingleExperiment(N);
        }
    }

    private void performSingleExperiment(int N) {
        Percolation p = new Percolation(N);
        int opens = 0;
        int rounds = 0;

        while(!p.percolates()) {
            int randomI = StdRandom.uniform(N*N+1);
            int randomJ = StdRandom.uniform(N*N+1);
            System.out.println("Choosing random number " + randomI + ", " + randomJ);
            p.open(randomI, randomJ);
            opens++;
            if(p.percolates()) {
                thresholds[rounds] = (double)opens*1.0/N*N;
                break;
            }
            rounds++;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample std deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - marginOfError();
    }

    public double confidenceHi() {
        return mean() + marginOfError();
    }

    private double marginOfError() {
        return criticalValue * stddev()/Math.sqrt(gridSize*gridSize);
    }

    public static void main(String[] args) {

    }

}
