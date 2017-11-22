import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    
    private final Digraph graph;
    
    public SAP(Digraph g) {
        if (g == null) throw new java.lang.IllegalArgumentException();
        graph = g;
    }
    
    public int length(int v, int w) {
        if (!isValidVertex(v) || !isValidVertex(w)) throw new java.lang.IllegalArgumentException();
        return new AncestarProcessor(v, w).distance;
    }
    
    public int ancestor(int v, int w) {
        if (!isValidVertex(v) || !isValidVertex(w)) throw new java.lang.IllegalArgumentException();
        return new AncestarProcessor(v, w).closestAncestor;
    }
    
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isValidVertex(v) || !isValidVertex(w)) throw new java.lang.IllegalArgumentException();
        return new AncestarProcessor(v, w).distance;
    }
    
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isValidVertex(v) || !isValidVertex(w)) throw new java.lang.IllegalArgumentException();
        return new AncestarProcessor(v, w).closestAncestor;
    }
    
    private boolean isValidVertex(int vertexId) {
        return vertexId >= 0 || vertexId < graph.V();
    }
    
    private boolean isValidVertex(Iterable<Integer> vertices) {
        if (null == vertices) return false;
        for (int vertex : vertices) {
            if (!isValidVertex(vertex)) {
                return false;
            }
        }
        return true;
    }
    
    private class AncestarProcessor {
        
        int closestAncestor;
        int distance;
        
        public AncestarProcessor(int v, int w) {
            BreadthFirstDirectedPaths first = new BreadthFirstDirectedPaths(graph, v);
            BreadthFirstDirectedPaths second = new BreadthFirstDirectedPaths(graph, w);
            
            processor(first, second);
        }
        
        public AncestarProcessor(Iterable<Integer> v, Iterable<Integer> w) {
            BreadthFirstDirectedPaths first = new BreadthFirstDirectedPaths(graph, v);
            BreadthFirstDirectedPaths second = new BreadthFirstDirectedPaths(graph, w);
            
            processor(first, second);
        }
        
        private void processor(BreadthFirstDirectedPaths first, BreadthFirstDirectedPaths second) {
            List<Integer> ancestors = new ArrayList<Integer>();
            for (int i = 0; i < graph.V(); i++) {
                if (first.hasPathTo(i) && second.hasPathTo(i)) {
                    ancestors.add(i);
                }
            }
            
            int shortestAncestor = -1;
            int minDistance = Integer.MAX_VALUE;
            for (int ancestor : ancestors) {
                int dist = first.distTo(ancestor) + second.distTo(ancestor);
                if (dist < minDistance) {
                    minDistance = dist;
                    shortestAncestor = ancestor;
                }
            }
            
            if (minDistance == Integer.MAX_VALUE) {
                distance = -1;
            } else {
                distance = minDistance;
            }
            
            closestAncestor = shortestAncestor;
        }
    }
}