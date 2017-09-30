import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final double[] percolationData;
    private final int test;
    private double mean = 0;
    private double stdDev = 0;
    
    public PercolationStats(int N, int T) {
        test = T;
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        
        percolationData = new double[test];
        for (int k = 0; k < test; k++) {
            int sitesOpenCount = 0;
            Percolation pc = new Percolation(N);
            while (!pc.percolates()) {
                int i = StdRandom.uniform(1, N+1);
                int j = StdRandom.uniform(1, N+1);
                if (!pc.isOpen(i, j)) {
                    pc.open(i, j);
                    sitesOpenCount++;
                }
            }
            percolationData[k] = (double) sitesOpenCount/(N * N);
        }
    }
    
    public double mean() {
        if (mean > 0) {
            return mean;
        }
        mean = StdStats.mean(percolationData);
        return mean;
    }
    
    public double stddev() {
        if (stdDev > 0) {
            return stdDev;
        }
        stdDev = StdStats.stddev(percolationData);
        return stdDev;
    }
    
    public double confidenceLo() {
        if (mean != 0 && stdDev != 0) {
            return mean - ((1.96 * stdDev) / Math.sqrt(test));
        } else if (mean == 0 && stdDev != 0) {
            return mean() - ((1.96 * stdDev) / Math.sqrt(test));
        } else if (mean != 0 && stdDev == 0) {
            return mean - ((1.96 * stddev()) / Math.sqrt(test));
        } else {
            return mean() - ((1.96 * stddev()) / Math.sqrt(test));
        }
    }
    
    public double confidenceHi() {
        if (mean != 0 && stdDev != 0) {
            return mean + ((1.96 * stdDev) / Math.sqrt(test));
        } else if (mean == 0 && stdDev != 0) {
            return mean() + ((1.96 * stdDev) / Math.sqrt(test));
        } else if (mean != 0 && stdDev == 0) {
            return mean + ((1.96 * stddev()) / Math.sqrt(test));
        } else {
            return mean() + ((1.96 * stddev()) / Math.sqrt(test));
        }
    }
    
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("95% confidence interval = " + confidence);       
    }
}