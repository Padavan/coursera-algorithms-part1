import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] results;
    private final int size;
    private double confidence95 = 1.96;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.size = n;
        this.results = new double[trials];

        for (int i = 0; i < trials; i++) {
            this.run(n, i);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = this.mean();
        double stddev = this.stddev();

        return mean - ((confidence95 * stddev) / Math.sqrt(this.results.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = this.mean();
        double stddev = this.stddev();

        return mean + ((confidence95 * stddev) / Math.sqrt(this.results.length));
    }

   // test client (see below)
   public static void main(String[] args) {
       if (args.length < 2) {
           throw new IllegalArgumentException("should be two attributes");
       }
       int size = Integer.parseInt(args[0]);
       int trials = Integer.parseInt(args[1]);

       if (size <= 0 || trials <= 0) {
           throw new IllegalArgumentException("both attributes should be positive integers");
       }

       PercolationStats stats = new PercolationStats(size, trials);

       StdOut.println("mean = " + stats.mean());
       StdOut.println("stddev = " + stats.stddev());
       StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
   }

   private void run(int n, int trial) {
        boolean isPercolate = false;
        Percolation grid = new Percolation(n);

        int[] cords = new int[n*n];
        for (int i = 0; i < n*n; i++) {
            cords[i] = i;
        }
        StdRandom.shuffle(cords);

        int step = 0;
        while (!isPercolate) {
            int[] coordinates = getRowColFromIndex(cords[step]);
            grid.open(coordinates[0], coordinates[1]);
            if (grid.percolates()) {
                this.results[trial] = step / Math.pow(n, 2);
                isPercolate = true;
            }
            step++;
        }
   }

   private int[] getRowColFromIndex(int index) {
        int[] coordinates = new int[2];
        coordinates[0] = index / this.size + 1;
        coordinates[1] = index % this.size + 1;
        return coordinates;
    }

}