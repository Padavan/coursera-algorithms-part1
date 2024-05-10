import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

    private Node tree = null;
    private int size = 0;

    // construct an empty set of points
    public KdTree() {
        tree = null;
    }

    private class Node {
        Point2D item;
        Node parent;
        Node left;
        Node right;
        boolean orientation;

        public Node(Point2D item, boolean orientation) {
            this.item = item;
            this.orientation = orientation;
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // number of points in the set
    public int size() {
        return this.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        this.tree = put(this.tree, p, HORIZONTAL);

    }

    private Node put(Node h, Point2D point, boolean orientation) {
        if (h == null) {
            this.size++;
            return new Node(point, orientation);
        }
        if (h.item.equals(point)) {
            return h;
        }
        if (orientation == HORIZONTAL) {
            if (point.y() > h.item.y()) {
                h.right = put(h.right, point, VERTICAL);
            }
            else {
                h.left = put(h.left, point, VERTICAL);
            }
        }

        if (orientation == VERTICAL) {
            if (point.x() > h.item.x()) {
                h.right = put(h.right, point, HORIZONTAL);
            }
            else {
                h.left = put(h.left, point, VERTICAL);
            }
        }

        return h;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return searchNode(this.tree, p);
    }

    private boolean searchNode(Node node, Point2D p) {
        if (node == null) {
            return false;
        }

        if (node.item.equals(p)) {
            return true;
        }
        if (node.orientation == VERTICAL && node.item.x() < p.x()
                || node.orientation == HORIZONTAL && node.item.y() < p.y()) {
            return searchNode(node.right, p);
        }
        else {
            return searchNode(node.left, p);
        }
    }

    // draw all points to standard draw
    public void draw() {
        traverseDraw(this.tree);
    }

    private void traverseDraw(Node node) {
        if (node == null) {
            return;
        }
        if (node.item != null) {
            StdDraw.point(node.item.x(), node.item.y());
        }
        traverseDraw(node.left);
        traverseDraw(node.right);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> stack = new Stack<Point2D>();
        get(this.tree, rect, stack);
        return stack;
    }

    private void get(Node node, RectHV rect, Stack<Point2D> stack) {
        if (node == null) return;
        if (rect.contains(node.item)) {
            stack.push(node.item);
        }

        if (node.orientation == VERTICAL && rect.xmin() > node.item.x()) {
            get(node.right, rect, stack);
        }
        else if (node.orientation == VERTICAL && rect.xmax() <= node.item.x()) {
            get(node.left, rect, stack);
        }
        else if (node.orientation == VERTICAL) {
            get(node.left, rect, stack);
            get(node.right, rect, stack);
        }

        if (node.orientation == HORIZONTAL && rect.ymin() > node.item.y()) {
            get(node.right, rect, stack);
        }
        else if (node.orientation == HORIZONTAL && rect.ymax() <= node.item.y()) {
            get(node.left, rect, stack);
        }
        else if (node.orientation == HORIZONTAL) {
            get(node.left, rect, stack);
            get(node.right, rect, stack);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (this.tree == null) {
            return null;
        }

        return getNearest(this.tree, this.tree.item, p);
    }

    private Point2D getNearest(Node node, Point2D oldChampion, Point2D target) {
        if (node == null) return oldChampion;


        Point2D newChampion = oldChampion;
        if (node.item.distanceSquaredTo(target) < oldChampion.distanceSquaredTo(target)) {
            newChampion = node.item;
        }

        if (node.orientation == VERTICAL && target.x() <= node.item.x()) {
            newChampion = getNearest(node.left, newChampion, target);
            if (oldChampion.distanceSquaredTo(target) > new Point2D(node.item.x(),
                                                                    target.y()).distanceSquaredTo(
                    target)) {
                newChampion = getNearest(node.right, newChampion, target);
            }
        }
        else if (node.orientation == VERTICAL && target.x() > node.item.x()) {
            newChampion = getNearest(node.right, newChampion, target);
            if (oldChampion.distanceSquaredTo(target) > new Point2D(node.item.x(),
                                                                    target.y()).distanceSquaredTo(
                    target)) {
                newChampion = getNearest(node.left, newChampion, target);
            }
        }

        if (node.orientation == HORIZONTAL && target.y() <= node.item.y()) {
            newChampion = getNearest(node.left, newChampion, target);
            if (oldChampion.distanceSquaredTo(target) > new Point2D(target.x(),
                                                                    node.item.y()).distanceSquaredTo(
                    target)) {
                newChampion = getNearest(node.right, newChampion, target);
            }
        }
        else if (node.orientation == HORIZONTAL && target.y() > node.item.y()) {
            newChampion = getNearest(node.right, newChampion, target);
            if (oldChampion.distanceSquaredTo(target) > new Point2D(target.x(),
                                                                    node.item.y()).distanceSquaredTo(
                    target)) {
                newChampion = getNearest(node.left, newChampion, target);
            }
        }


        // newChampion = getNearest(node.left, newChampion, target);
        // newChampion = getNearest(node.right, newChampion, target);

        return newChampion;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}