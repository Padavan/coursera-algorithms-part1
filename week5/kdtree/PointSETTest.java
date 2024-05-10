import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PointSETTest {
    @Test
    void testContain1() {
        PointSET PS = new PointSET();
        for (int i = 0; i < 5; i++) {
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