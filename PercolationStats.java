package com.sarahkim;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import com.sarahkim.Percolation;

/**
 * Created by sarahbkim on 2/5/16.
 */
public class PercolationStats {
    double[] thresholds;
    double criticalValue = -1.96; // z-value for 95% confidence interval

    // perform T independent experiments on an NxN grid
    public PercolationStats(int N, int T) {
        thresholds = new double[T];
        performAllExperiments(N, T);
    }
    private void performAllExperiments(int N, int reps) {
        for(int i=0; i<reps;i++) {
            performSingleExperiment(N, i);
        }
    }
    private void performSingleExperiment(int N, int round) {
        Percolation p = new Percolation(N);
        int opens = 0;

        while(!p.percolates()) {
            int randomI = StdRandom.uniform(N) + 1;
            int randomJ = StdRandom.uniform(N) + 1;
            p.open(randomI, randomJ);
            opens++;
            if(p.percolates()) {
                thresholds[round] = (double)opens/(N*N);
                break;
            }
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
        return criticalValue * stddev();
    }

    public static void main(String[] args) {
        if(args.length < 2) {
            throw new Error("Requires N (size of grid) and T(number of experiements");
        }
        PercolationStats pStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + pStats.mean());
        System.out.println("stddev = " + pStats.stddev());
        System.out.println("95% confidence interval = " + pStats.confidenceLo() + ", " + pStats.confidenceHi());
    }
}
