import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] trialRuns;
    private int trials;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n and trials must be positive integers");

        this.trials = trials;

        trialRuns = new double[trials];
        for (int i = 0; i < trials; i++)
            trialRuns[i] = runPercolation(n);
    }

    private double runPercolation(int n) {
        Percolation perc = new Percolation(n);
        int opened = 0;
        boolean[][] openCells = new boolean[n+1][n+1];
        while (!perc.percolates()) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            if (!openCells[row][col]) {
                perc.open(row, col);
                openCells[row][col] = true;
                opened++;
            }
        }
        return ((double) opened) / (n * n);
    }

    public double mean() {
        return StdStats.mean(trialRuns);
    }

    public double stddev() {
        return StdStats.stddev(trialRuns);
    }

    public double confidenceLo() {
        double std = stddev();
        double mean = mean();
        return mean - (1.96 * std / Math.sqrt(trials));
    }

    public double confidenceHi() {
        double std = stddev();
        double mean = mean();
        return mean + (1.96 * std / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean: " + stats.mean());
        StdOut.println("stddev: " + stats.stddev());
        StdOut.println("95% confidence interval: [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
