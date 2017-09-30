import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    
    private TreeSet<Point2D> pointSet;
    
    public PointSET() {
        pointSet = new TreeSet<Point2D>();
    }
    
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }
    
    public int size() {
        return pointSet.size();
    }
    
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        pointSet.add(p);
    }
    
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return pointSet.contains(p);
    }
    
    public void draw() {
        for (Point2D p : pointSet) {
            p.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();
        TreeSet<Point2D> temp = new TreeSet<Point2D>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                temp.add(p);
            }
        }
        return temp;
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        else if (pointSet.isEmpty()) return null;
        else {
            Point2D nearest = pointSet.first();
            double minDistance = p.distanceSquaredTo(nearest);
            for (Point2D p1 : pointSet) {
                if (p.distanceSquaredTo(p1) < minDistance) {
                    minDistance = p.distanceSquaredTo(p1);
                    nearest = p1;
                }
            }
            return nearest;
        }
    }
    
    
}