import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BruteCollinearPoints {
    
    private final LineSegment[] segments;
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException(); 
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException();
            for (int j = i+1; j < points.length; j++) {
                if (points[j] == null) throw new java.lang.IllegalArgumentException();
                if (points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException();
            }
        }
        
        List<LineSegment> tempLineSegment = new ArrayList<LineSegment>();
        for (int p1 = 0; p1 < points.length - 3; p1++) {
            for (int p2 = p1+1; p2 < points.length - 2; p2++) {
                for (int p3 = p2+1; p3 < points.length -1; p3++) {
                    for (int p4 = p3+1; p4 < points.length; p4++) {
                        double slopeP1P2 = points[p1].slopeTo(points[p2]);
                        double slopeP2P3 = points[p2].slopeTo(points[p3]);
                        double slopeP3P4 = points[p3].slopeTo(points[p4]);
                        
                        if (slopeP1P2 == slopeP2P3 && slopeP1P2 == slopeP3P4) {
                            Point[] temp = new Point[4];
                            temp[0] = points[p1];
                            temp[1] = points[p2];
                            temp[2] = points[p3];
                            temp[3] = points[p4];
                            
                            Arrays.sort(temp);
                            tempLineSegment.add(new LineSegment(temp[0], temp[3]));
                        }
                    }
                }
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