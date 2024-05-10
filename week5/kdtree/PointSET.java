import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> set = new SET<Point2D>();
    
    // is the set empty?
    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return this.set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        this.set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : this.set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        // try to get floor/ceiling values in while loop
        Stack<Point2D> stack = new Stack<Point2D>();
        for (Point2D x : this.set) {
            if (rect.contains(x)) {
                stack.push(x);
            }
        }
        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Point2D goal = null;
        for (Point2D x : this.set) {
            if (goal == null) {
                goal = x;
            }
            if (goal.distanceSquaredTo(p) > x.distanceSquaredTo(p)) {
                goal = x;
            }
        }
        return goal;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        System.out.println();
    }
}