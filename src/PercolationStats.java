/* *****************************************************************************
 *  Name:              Ritta Gladchuk
 *  Coursera User ID:  123456
 *  Last modified:     July 21, 2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import static java.lang.Integer.parseInt;
import static java.lang.Math.sqrt;

public class PercolationStats {
    private int n;
    private int trials;
    private static final double CONFIDENCE = 1.96;
    private int[] results;

    // perform PercolationStats(int n, int trials)
    public PercolationStats(int n, int trials) {
        valid(n, trials);
        this.n = n;
        this.trials = trials;
        Percolation trial;
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
            i = StdRandom.uniform(1, n * n);
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
        int n = parseInt(args[0]);
        int trials = parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
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
        return (mean - (CONFIDENCE * s) / sqrt(trials)) / (n * n);
    }

    public double confidenceHi() {
        double s = StdStats.stddev(results);
        double mean = StdStats.mean(results);
        return (mean + (CONFIDENCE * s) / sqrt(trials)) / (n * n);
    }

    private void valid(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IndexOutOfBoundsException("recieved 0 for size or trials");
        }
    }
}
