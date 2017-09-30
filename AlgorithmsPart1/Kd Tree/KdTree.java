import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.List;
import java.util.LinkedList;

public class KdTree {
    
    private Node root;
    private int size;
    private List<Point2D> pointList;
    private RectHV board;
    
    private class Node {
        private final Point2D point;
        private Node right, left;
        private final boolean isHorizontal;
        
        public Node(Point2D point, Node right, Node left, boolean isHorizontal) {
            this.point = point;
            this.right = right;
            this.left = left;
            this.isHorizontal = isHorizontal;
        }
    }
    
    public KdTree() {
        root = null;
        size = 0;
        board = new RectHV(0, 0, 1.0, 1.0);
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        root = put(root, p, false);
    }
    
    private Node put(Node node, Point2D p, boolean isHorizontal) {
        if (node == null) {
            size++;
            return new Node(p, null, null, isHorizontal);
        }
        
        if (node.point.equals(p)) {
        } else if ((!node.isHorizontal && (node.point.x() >= p.x())) || (node.isHorizontal && (node.point.y() >= p.y()))) {
            node.left = put(node.left, p, !node.isHorizontal);
        } else {
            node.right = put(node.right, p, !node.isHorizontal);
        }
        
        return node;
    }
    
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        Node node = root;
        while (node != null) {
            if (node.point.equals(p)) {
                return true;
            } else if ((!node.isHorizontal && (node.point.x() >= p.x())) || (node.isHorizontal && (node.point.y() >= p.y()))) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return false;
    }
    
    public void draw() {
        StdDraw.setScale(0, 1);
        draw(root, board);
    }
    
    private void draw(Node node, RectHV rect) {
        if (node != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.02);
            new Point2D(node.point.x(), node.point.y()).draw();
            StdDraw.setPenRadius();
            
            if (!node.isHorizontal) {
                StdDraw.setPenColor(StdDraw.RED);
                new Point2D(node.point.x(), rect.ymin()).drawTo(new Point2D(node.point.x(), rect.ymax()));
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                new Point2D(rect.xmin(), node.point.y()).drawTo(new Point2D(rect.xmax(), node.point.y()));
            }
            draw(node.left, leftRect(rect, node));
            draw(node.right, rightRect(rect, node));
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();
        pointList = new LinkedList<Point2D>();
        findPointsInRange(board, rect, root);
        return pointList;
    }
    
    private void findPointsInRange(RectHV nodeRect, RectHV rect, Node node) {
        if (node != null) {
            if (nodeRect.intersects(rect)) {
                if (rect.contains(node.point)) pointList.add(node.point);
                findPointsInRange(leftRect(nodeRect, node), rect, node.left);
                findPointsInRange(rightRect(nodeRect, node), rect, node.right);
            }   
        }
    }
    
    private RectHV leftRect(RectHV rect, Node p) {
        if (p.isHorizontal) {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.point.y());
        }
        return new RectHV(rect.xmin(), rect.ymin(), p.point.x(), rect.ymax());
    }
    
    private RectHV rightRect(RectHV rect, Node p) {
        if (p.isHorizontal) {
            return new RectHV(rect.xmin(), p.point.y(), rect.xmax(), rect.ymax());
        }
        return new RectHV(p.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        else if (root == null) return null;
        else {
            return nearestNeigh(root, board, p, null);
        }
    }
    
    private Point2D nearestNeigh(Node node, RectHV rect, Point2D point, Point2D nearPoint) {
        Point2D nearestPoint = nearPoint;
        if (node != null) {      
            if (nearestPoint == null || nearestPoint.distanceSquaredTo(point) > rect.distanceSquaredTo(point)) {
                if (nearestPoint == null) {
                    nearestPoint = node.point;
                } else {
                    if (node.point.distanceSquaredTo(point) < nearestPoint.distanceSquaredTo(point)) {
                        nearestPoint = node.point;
                    }
                }
                
                if (!node.isHorizontal) {
                    RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
                    RectHV rightRect = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
                    if (point.x() <= node.point.x()) {
                        nearestPoint = nearestNeigh(node.left, leftRect, point, nearestPoint);
                        nearestPoint = nearestNeigh(node.right, rightRect, point, nearestPoint);
                    } else {
                        nearestPoint = nearestNeigh(node.right, rightRect, point, nearestPoint);
                        nearestPoint = nearestNeigh(node.left, leftRect, point, nearestPoint);
                    }
                } else {
                    RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
                    RectHV rightRect = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
                    if (point.y() <= node.point.y()) {
                        nearestPoint = nearestNeigh(node.left, leftRect, point, nearestPoint);
                        nearestPoint = nearestNeigh(node.right, rightRect, point, nearestPoint);
                    } else {
                        nearestPoint = nearestNeigh(node.right, rightRect, point, nearestPoint);
                        nearestPoint = nearestNeigh(node.left, leftRect, point, nearestPoint);
                    }
                }
            }
        }
        return nearestPoint;
    }
    
}