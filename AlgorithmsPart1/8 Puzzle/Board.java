import java.util.List;
import java.util.LinkedList;

public class Board {
    
    private final int[][] blocks;
    
    public Board(int[][] blocks) {
        int size = blocks.length;
        this.blocks = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }
    
    public int dimension() {
        return blocks.length;
    }
    
    public int hamming() {
        int hamming = 0;
        int dimension = dimension();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != dimension * i + j + 1) hamming++;
            }
        }
        return hamming;
    }
    
    public int manhattan() {
        int manhattan = 0;
        int dimension = dimension();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int number = blocks[i][j];
                if (number != 0) {
                    int a = number % dimension;
                    int row = 0;
                    int column = 0;
                    if (a == 0) {
                        column = dimension - 1;
                        row = (number / dimension) - 1;
                    } else {
                        row = (number / dimension);
                        column = a - 1;
                    }
                    manhattan += Math.abs(row - i) + Math.abs(column - j);
                }
            }
        }
        return manhattan;
    }
    
    public boolean isGoal() {
        int dimension = dimension();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != dimension * i + j + 1) return false;
            }
        }
        return true;
    }
    
    public Board twin() {
        int dimension = dimension();
        int[][] newBlocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                newBlocks[i][j] = blocks[i][j];
            }
        }
        
        int rowToSwap = 0;
        if (newBlocks[0][0] == 0 || newBlocks[0][1] == 0) {
            rowToSwap = 1;
        }
        
        int temp = newBlocks[rowToSwap][0];
        newBlocks[rowToSwap][0] = newBlocks[rowToSwap][1];
        newBlocks[rowToSwap][1] = temp;
        
        return new Board(newBlocks);
    }
    
    public boolean equals(Object y) {
        if (y == null) return false;
        
        if (this == y) return true;
        
        if (this.getClass() != y.getClass()) return false;
        
        Board b = (Board) y;
        int dimension = dimension();
        if (dimension != b.dimension()) return false;
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != b.blocks[i][j]) return false;
            }
        }
        
        return true;
    }
    
    public Iterable<Board> neighbors() {
        int emptySpaceRow = 0;
        int emptySpaceCol = 0;
        
        int dimension = dimension();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    emptySpaceRow = i;
                    emptySpaceCol = j;
                }
            }
        }
        
        List<Board> neighbours = new LinkedList<Board>();
        if (emptySpaceRow > 0) {
            int[][] newBlocks = new int[dimension][dimension];
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    newBlocks[i][j] = blocks[i][j];
                }
            }
            
            int temp = newBlocks[emptySpaceRow][emptySpaceCol];
            newBlocks[emptySpaceRow][emptySpaceCol] = newBlocks[emptySpaceRow - 1][emptySpaceCol];
            newBlocks[emptySpaceRow - 1][emptySpaceCol] = temp;
            neighbours.add(new Board(newBlocks));
        }
        
        if (emptySpaceRow < dimension - 1) {
            int[][] newBlocks = new int[dimension][dimension];
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    newBlocks[i][j] = blocks[i][j];
                }
            }
            
            int temp = newBlocks[emptySpaceRow][emptySpaceCol];
            newBlocks[emptySpaceRow][emptySpaceCol] = newBlocks[emptySpaceRow + 1][emptySpaceCol];
            newBlocks[emptySpaceRow + 1][emptySpaceCol] = temp;
            neighbours.add(new Board(newBlocks));
        }
        
        if (emptySpaceCol > 0) {
            int[][] newBlocks = new int[dimension][dimension];
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    newBlocks[i][j] = blocks[i][j];
                }
            }
            
            int temp = newBlocks[emptySpaceRow][emptySpaceCol];
            newBlocks[emptySpaceRow][emptySpaceCol] = newBlocks[emptySpaceRow][emptySpaceCol - 1];
            newBlocks[emptySpaceRow][emptySpaceCol - 1] = temp;
            neighbours.add(new Board(newBlocks));
        }
        
        if (emptySpaceCol < dimension - 1) {
            int[][] newBlocks = new int[dimension][dimension];
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    newBlocks[i][j] = blocks[i][j];
                }
            }
            
            int temp = newBlocks[emptySpaceRow][emptySpaceCol];
            newBlocks[emptySpaceRow][emptySpaceCol] = newBlocks[emptySpaceRow][emptySpaceCol + 1];
            newBlocks[emptySpaceRow][emptySpaceCol + 1] = temp;
            neighbours.add(new Board(newBlocks));
        }
        
        return neighbours;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder(dimension() + " \n ");
     
        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {
                sb.append(blocks[row][column]);
                sb.append(" ");
            }
      
            sb.append("\n ");
        }
     
        return sb.toString();
    }
    
}