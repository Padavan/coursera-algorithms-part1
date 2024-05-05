import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
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

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException();
        }

        int count = 0;
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null || points[j].equals(points[i]))
                    throw new IllegalArgumentException();
                for (int k = j + 1; k < points.length; k++) {
                    if (points[k] == null || points[k].equals(points[j]) || points[k].equals(
                            points[i])) throw new IllegalArgumentException();
                    for (int m = k + 1; m < points.length; m++) {
                        if (points[m] == null || points[m].equals(points[k]) || points[m].equals(
                                points[j]) || points[m].equals(points[i]))
                            throw new IllegalArgumentException();

                        if (isCollinear(points[i], points[j], points[k], points[m])) {
                            // System.out.println("collinear");
                            Point[] pointArray = { points[i], points[j], points[k], points[m] };
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
                        count++;
                    }
                }
            }
        }
        // System.out.println("count: " + count);
        // System.out.println("size: " + this.size);
    }

    private boolean isCollinear(Point a, Point b, Point c, Point d) {
        // .println("slopes: " + a.slopeTo(b) + " / " + b.slopeTo(c) + " / " + c.slopeTo(d));
        if (a.slopeTo(b) == c.slopeTo(d) && b.slopeTo(c) == a.slopeTo(b)) {
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}