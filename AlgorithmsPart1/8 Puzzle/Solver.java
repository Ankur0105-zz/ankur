import java.util.Comparator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
    
    private Node endNode;
    private boolean solvable;
    
    private class Node {
        private final Board board;
        private final Node parent;
        private final int moves;
        
        public Node(Board board, Node parent, int moves) {
            this.board = board;
            this.parent = parent;
            this.moves = moves;
        }
    }
    
    public Solver(Board initial) {
        
        if (initial == null) throw new java.lang.IllegalArgumentException();
        Comparator<Node> nodeComparator = new Comparator<Node>() {
            public int compare(Node a, Node b) {
                return a.board.manhattan() + a.moves - b.board.manhattan() - b.moves;
            }
        };
        MinPQ<Node> pq;
        MinPQ<Node> pqSwap;
        pq = new MinPQ<Node>(nodeComparator);
        pqSwap = new MinPQ<Node>(nodeComparator);
        
        pq.insert(new Node(initial, null, 0));
        pqSwap.insert(new Node(initial.twin(), null, 0));
        
        solvable = false;
        
        Node node = pq.delMin();
        Node nodeSwap = pqSwap.delMin();
        
        while (!node.board.isGoal() && !nodeSwap.board.isGoal()) {
            
            for (Board b: node.board.neighbors()) {
                if (node.parent == null || !b.equals(node.parent.board)) {
                    pq.insert(new Node(b, node, node.moves + 1));
                }
            }
            
            for (Board b: nodeSwap.board.neighbors()) {
                if (nodeSwap.parent == null || !b.equals(nodeSwap.parent.board)) {
                    pqSwap.insert(new Node(b, nodeSwap, nodeSwap.moves + 1));
                }
            }
            
            node = pq.delMin();
            nodeSwap = pqSwap.delMin(); 
        }
        
        if (node.board.isGoal()) {
            solvable = true;
            endNode = node;
        }
    }
    
    public boolean isSolvable() {
        return solvable;
    }
    
    public int moves() {
        if (!solvable) return -1;
        
        Node last = endNode;
        return last.moves;
    }
    
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        
        List<Board> solution = new LinkedList<Board>();
        Node last = endNode;
        solution.add(last.board);
        
        while (last.parent != null) {
            last = last.parent;
            solution.add(last.board);
        }
        Collections.reverse(solution);
        return solution;
    }
    
    public static void main(String[] args) {
        
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}