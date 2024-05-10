import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KdTreeTest {
    @Test
    void testSize() {
        KdTree tree = new KdTree();
        for (int i = 0; i < 5; i++) {
            tree.insert(new Point2D(i, i));
        }
        assertEquals(tree.size(), 5);
    }

    @Test
    void testSize2() {
        KdTree tree = new KdTree();
        for (int i = 0; i < 5; i++) {
            tree.insert(new Point2D(i, i));
        }
        for (int i = 0; i < 5; i++) {
            tree.insert(new Point2D(i, i));
        }
        assertEquals(tree.size(), 5);
    }

    @Test
    void testIsEmpty() {
        KdTree tree = new KdTree();
        assertEquals(tree.isEmpty(), true);
        tree.insert(new Point2D(0, 1));
        assertEquals(tree.isEmpty(), false);
    }

    @Test
    void testContain1() {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0, 1));
        boolean result = tree.contains(new Point2D(0, 1));
        assertEquals(result, true);
    }

    @Test
    void testContain2() {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0, 1));
        boolean result = tree.contains(new Point2D(0, 0));
        assertEquals(result, false);
    }

    @Test
    void testContain3() {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(1, 1));
        tree.insert(new Point2D(0, 2));
        tree.insert(new Point2D(1, 2));
        assertEquals(tree.size(), 3);
        boolean result = tree.contains(new Point2D(1, 2));
        assertEquals(result, true);
    }

    @Test
    void testRange1() {
        KdTree PS = new KdTree();
        for (int i = 1; i < 10; i++) {
            PS.insert(new Point2D(i, i));
        }

        RectHV rect = new RectHV(0, 0, 2.5, 2.5);

        Iterable<Point2D> points = PS.range(rect);
        int count = 0;
        for (Point2D x : points) {
            count++;
            StdOut.print(x.toString());
        }
        assertEquals(count, 2);
    }


}