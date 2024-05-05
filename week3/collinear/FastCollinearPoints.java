import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private Node first = null;
    private int size = 0;

    private class Node {
        LineSegment item;
        Node next;

        public Node(LineSegment item) {
            this.item = item;
            this.next = null;
        }
    }

    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < (points.length - 2); i++) {
            Point[] carr = Arrays.copyOfRange(points.clone(), i + 1, points.length);
            Point origin = points[i];
            Arrays.sort(carr, origin.slopeOrder());

            for (int j = 0; j <= (carr.length - 3); j++) {
                Point[] pointArray = { origin, carr[j], carr[j + 1], carr[j + 2] };
                if (pointArray[0] == null || pointArray[1] == null || pointArray[2] == null
                        || pointArray[3] == null) {
                    throw new IllegalArgumentException();
                }

                if (isCollinear(pointArray[0], pointArray[1], pointArray[2], pointArray[3])) {
                    Arrays.sort(pointArray);
                    // System.out.println("collinear: " +
                    //                            pointArray[0] + " / " + pointArray[1] + " / "
                    //                            + pointArray[2]
                    //                            + " / " + pointArray[3]);
                    LineSegment item = new LineSegment(pointArray[0], pointArray[3]);
                    if (first == null) {
                        first = new Node(item);
                    }
                    else {
                        Node newNode = new Node(item);
                        newNode.next = first;
                        first = newNode;
                    }
                    this.size = this.size + 1;
                }
            }
        }
    }

    private boolean isCollinear(Point a, Point b, Point c, Point d) {

        if (a.slopeTo(b) == c.slopeTo(d) && b.slopeTo(c) == a.slopeTo(d)
                && a.slopeTo(b) == b.slopeTo(c)) {
            // System.out.println(
            //         "slope: " + a.slopeTo(b) + " and " + b.slopeTo(c) + " and " + c.slopeTo(d));
            return true;
        }
        return false;
    }

    public int numberOfSegments() {
        // the number of line segments
        return this.size;
    }

    public LineSegment[] segments() {
        LineSegment[] arr = new LineSegment[this.size];
        Node current = first;
        int count = 0;
        while (current != null) {
            arr[count] = current.item;
            current = current.next;
            count++;
        }
        return arr;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}