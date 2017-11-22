import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    
    private static final double MAX_ENERGY = 1000.0;
    private Picture picture;
    
    public SeamCarver(Picture picture) {
        if (null == picture) throw new java.lang.IllegalArgumentException();
        this.picture = new Picture(picture);
    }
    
    public Picture picture() {
        return new Picture(picture);
    }
    public int width() {
        return picture.width();
    }
    
    public int height() {
        return picture.height();
    }
    
    public  double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) throw new java.lang.IllegalArgumentException();
        
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) return MAX_ENERGY;
        
        double xDiff = getGradient(picture.get(x - 1, y), picture.get(x + 1, y));
        double yDiff = getGradient(picture.get(x, y - 1), picture.get(x, y + 1));
        return Math.sqrt(xDiff + yDiff);
    }
    
    private double getGradient(Color a, Color b) {
        int redDiff = a.getRed() - b.getRed();
        int greenDiff = a.getGreen() - b.getGreen();
        int blueDiff = a.getBlue() - b.getBlue();
        return redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
    }
    
    public int[] findHorizontalSeam() {
        int[][] edgeTo = new int[height()][width()];
        double[][] distTo = new double[height()][width()];
        resetDist(distTo);

        for (int row = 0; row < height(); row++)
            distTo[row][0] = 1000;

        for (int col = 0; col < width() - 1; col++)
            for (int row = 0; row < height(); row++)
                relaxHorizontal(row, col, edgeTo, distTo);

        double minDist = Double.MAX_VALUE;
        int minRow = 0;
        for (int row = 0; row < height(); row++)
            if (minDist > distTo[row][width() - 1]) {
                minDist = distTo[row][width() - 1];
                minRow = row;
            }

        int[] indices = new int[width()];
        for (int col = width() - 1, row = minRow; col >= 0; col--) {
            indices[col] = row;
            row -= edgeTo[row][col];
        }

        return indices;
    }
    
    public int[] findVerticalSeam() {
        int[][] edgeTo = new int[height()][width()];
        double[][] distTo = new double[height()][width()];
        resetDist(distTo);

        for (int col = 0; col < width(); col++)
            distTo[0][col] = 1000;

        for (int row = 0; row < height() - 1; row++)
            for (int col = 0; col < width(); col++)
                relaxVertical(row, col, edgeTo, distTo);

        double minDist = Double.MAX_VALUE;
        int minCol = 0;
        for (int col = 0; col < width(); col++)
            if (minDist > distTo[height() - 1][col]) {
                minDist = distTo[height() - 1][col];
                minCol = col;
            }

        int[] indices = new int[height()];
        for (int row = height() - 1, col = minCol; row >= 0; row--) {
            indices[row] = col;
            col -= edgeTo[row][col];
        }

        return indices;
    }
    
    private void relaxVertical(int row, int col, int[][] edgeTo, double[][] distTo) {
        int nextRow = row + 1;
        for (int i = -1; i <= 1; i++) {
            int nextCol = col + i;
            if (nextCol < 0 || nextCol >= width()) continue;
            if (distTo[nextRow][nextCol] > distTo[row][col] + energy(nextCol, nextRow)) {
                distTo[nextRow][nextCol] = distTo[row][col] + energy(nextCol, nextRow);
                edgeTo[nextRow][nextCol] = i;
            }
        }
    }

    private void relaxHorizontal(int row, int col, int[][] edgeTo, double[][] distTo) {
        int nextCol = col + 1;
        for (int i = -1; i <= 1; i++) {
            int nextRow = row + i;
            if (nextRow < 0 || nextRow >= height()) continue;
            if (distTo[nextRow][nextCol] > distTo[row][col]  + energy(nextCol, nextRow)) {
                distTo[nextRow][nextCol] = distTo[row][col]  + energy(nextCol, nextRow);
                edgeTo[nextRow][nextCol] = i;
            }
        }
    }

    private void resetDist(double[][] distTo) {
        for (int i = 0; i < distTo.length; i++)
            for (int j = 0; j < distTo[i].length; j++)
                distTo[i][j] = Double.MAX_VALUE;
    }
    
    public void removeHorizontalSeam(int[] seam) {
        if (null == seam || height() < 1 || seam.length != width()) throw new java.lang.IllegalArgumentException();
        checkSeamValidity(seam);
        
        Picture p = new Picture(width(), height() - 1);
        for (int y = 0; y < width(); y++) {
            int k = 0;
            for (int x = 0; x < height(); x++) {
                if (x != seam[y]) {
                    p.set(y, k, picture.get(y, x));
                    k++;
                }
            }
        }
        this.picture = p;
    }
    
    public void removeVerticalSeam(int[] seam) {
        if (null == seam || width() < 1 || seam.length != height()) throw new java.lang.IllegalArgumentException();
        checkSeamValidity(seam);
        
        Picture p = new Picture(width() - 1, height());
        for (int y = 0; y < height(); y++) {
            int k = 0;
            for (int x = 0; x < width(); x++) {
                if (x != seam[y]) {
                    p.set(k, y, picture.get(x, y));
                    k++;
                }
            }
        }
        this.picture = p;
    }
    
    private void checkSeamValidity(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new java.lang.IllegalArgumentException();
            }
        }
    }
}