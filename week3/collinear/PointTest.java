import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTest {
    @Test
    void testSlopeTo() {
        Point a = new Point(0, 0);
        Point b = new Point(1, 0);
        double slope = a.slopeTo(b);
        assertEquals(slope, 0.0);
    }

    @Test
    void testSlopeTo1() {
        Point a = new Point(0, 0);
        Point b = new Point(5, 5);
        double slope = a.slopeTo(b);
        assertEquals(slope, 1.0);
    }

}