/* *****************************************************************************
 *  Name:              Ritta Gladchuk
 *  Coursera User ID:  123456
 *  Last modified:     July 21, 2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private final int n;
    private final int trials;
    private final int[] results;

    // perform PercolationStats(int n, int trials)
    public PercolationStats(int n, int trials) {
        valid(n, trials);
        this.n = n;
        this.trials = trials;
        results = new int[trials];
        for (int i = 0; i < trials; i++) {
            results[i] = runSim();
        }
    }

    private int runSim() {
        int i;
        int row;
        int col;
        int threshold = 0;
        Percolation trial = new Percolation(n);
        while (!trial.percolates()) {
            i = StdRandom.uniform(1, ((n * n) + 1));
            col = ((i - 1) % n) + 1;
            row = (i + n - 1) / n;
            if (!trial.isOpen(row, col)) {
                trial.open(row, col);
                threshold++;
            }
        }
        return threshold;
    }

    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        int ts = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(num, ts);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", "
                                   + stats.confidenceHi() + "]");
    }

    public double mean() {
        return StdStats.mean(results) / (n * n);
    }

    public double stddev() {
        return StdStats.stddev(results) / (n * n);
    }

    public double confidenceLo() {
        double s = StdStats.stddev(results);
        double mean = StdStats.mean(results);
        return (mean - (CONFIDENCE * s) / Math.sqrt(trials)) / (n * n);
    }

    public double confidenceHi() {
        double s = StdStats.stddev(results);
        double mean = StdStats.mean(results);
        return (mean + (CONFIDENCE * s) / Math.sqrt(trials)) / (n * n);
    }

    private void valid(int num, int ts) {
        if (num <= 0 || ts <= 0) {
            throw new IllegalArgumentException("recieved 0 for size or trials");
        }
    }
}
