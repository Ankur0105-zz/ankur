import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class FastCollinearPoints {
    private final LineSegment[] segments;
    
    public FastCollinearPoints(Point[] ps) {
        if (ps == null) throw new java.lang.IllegalArgumentException(); 
        for (int i = 0; i < ps.length; i++) {
            if (ps[i] == null) throw new java.lang.IllegalArgumentException();
            for (int j = i+1; j < ps.length; j++) {
                if (ps[j] == null) throw new java.lang.IllegalArgumentException();
                if (ps[i].compareTo(ps[j]) == 0) throw new java.lang.IllegalArgumentException();
            }
        }
        
        List<LineSegment> tempLineSegment = new ArrayList<LineSegment>();
        
        for (int i = 0; i < ps.length - 1; i++) {
            List<Point> tempPoint = new ArrayList<Point>();
            List<Double> slopes = new ArrayList<Double>();
            Point[] points = ps.clone();
            Arrays.sort(points, i+1, points.length, points[i].slopeOrder());
            
            for (int k = 0; k < i; k++)  {
                slopes.add(points[i].slopeTo(points[k]));
            }
            
            double slope = points[i].slopeTo(points[i+1]);
            int numberOfConsecutivePoints = 2;
            tempPoint.add(points[i]);
            tempPoint.add(points[i+1]);
            
            for (int j = i+2; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == slope) {
                    numberOfConsecutivePoints++;
                    tempPoint.add(points[j]);
                } else {
                    if (numberOfConsecutivePoints >= 4) {
                        Point[] temp = tempPoint.toArray(new Point[tempPoint.size()]);
                        Arrays.sort(temp);
                        if (!slopes.contains(slope))
                            tempLineSegment.add(new LineSegment(temp[0], temp[temp.length - 1]));
                    }
                    slope = points[i].slopeTo(points[j]);
                    numberOfConsecutivePoints = 2;
                    tempPoint.clear();
                    tempPoint.add(points[i]);
                    tempPoint.add(points[j]);
                }
            }
            if (tempPoint.size() >= 4) {
                Point[] temp = tempPoint.toArray(new Point[tempPoint.size()]);
                Arrays.sort(temp);
                if (!slopes.contains(slope))
                    tempLineSegment.add(new LineSegment(temp[0], temp[temp.length - 1]));
            }
        }
        
        segments = tempLineSegment.toArray(new LineSegment[tempLineSegment.size()]);
    }
    
    public int numberOfSegments() {
        return segments.length;
    }
    
    public LineSegment[] segments() {
        return segments.clone();
    }
}