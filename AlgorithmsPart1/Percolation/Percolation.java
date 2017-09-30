import edu.princeton.cs.algs4.WeightedQuickUnionUF;
    
public class Percolation {

    private final int gridSize;
    private final int top;
    private final int bottom;
    private final WeightedQuickUnionUF weightedQuickUnion;
    private final WeightedQuickUnionUF weightedQuickUnion2;
    private boolean[][] site;
    private int openSites;
    
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        
        openSites = 0;
        gridSize = N;
        weightedQuickUnion = new WeightedQuickUnionUF(N*N + 2);
        weightedQuickUnion2 = new WeightedQuickUnionUF(N*N + 1);
        site = new boolean[N][N];
        top = N*N;
        bottom = N*N + 1;
    }
    
    public int numberOfOpenSites() {
        return openSites;
    }
    
    public void open(int i, int j) {
        if (isIndexInRange(i, j)) {
            site[i-1][j-1] = true;
            openSites++;
            
            int currentSite = convert2Dto1DCoordinates(i, j);
            if (i == 1 && !weightedQuickUnion.connected(currentSite, top)) {
                weightedQuickUnion.union(currentSite, top);
                weightedQuickUnion2.union(currentSite, top);
            }
            
            if (i == gridSize) {
                weightedQuickUnion.union(currentSite, bottom);
            }
            
            if (i > 1 && isOpen(i-1, j)) {
                weightedQuickUnion.union(currentSite, convert2Dto1DCoordinates(i-1, j));
                weightedQuickUnion2.union(currentSite, convert2Dto1DCoordinates(i-1, j));
            }
            
            if (i < gridSize && isOpen(i+1, j)) {
                weightedQuickUnion.union(currentSite, convert2Dto1DCoordinates(i+1, j));
                weightedQuickUnion2.union(currentSite, convert2Dto1DCoordinates(i+1, j));
            }
            
            if (j > 1 && isOpen(i, j-1)) {
                weightedQuickUnion.union(currentSite, convert2Dto1DCoordinates(i, j-1));
                weightedQuickUnion2.union(currentSite, convert2Dto1DCoordinates(i, j-1));
            }
            
            if (j < gridSize && isOpen(i, j+1)) {
                weightedQuickUnion.union(currentSite, convert2Dto1DCoordinates(i, j+1));
                weightedQuickUnion2.union(currentSite, convert2Dto1DCoordinates(i, j+1));
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    private boolean isIndexInRange(int i, int j) {
        if (i < 1 || i > gridSize || j < 1 || j > gridSize) {
            throw new IllegalArgumentException();
        }
        return true;
    }
    
    public boolean isOpen(int i, int j) {
        if (isIndexInRange(i, j)) {
            return site[i-1][j-1];
        }
        throw new IllegalArgumentException();
    }
    
    public boolean isFull(int i, int j) {
        if (isIndexInRange(i, j)) {
            return weightedQuickUnion2.connected(top, convert2Dto1DCoordinates(i, j));
        }
        throw new IllegalArgumentException();
    }
    
    private int convert2Dto1DCoordinates(int i, int j) {
        return  ((i - 1) * gridSize) + (j - 1);
    }
    
    public boolean percolates() {
        return weightedQuickUnion.connected(top, bottom);
    }
}