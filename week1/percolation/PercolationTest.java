import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PercolationTest {
    @Test
    void testOpenAndCount(){
        Percolation grid = new Percolation(5);
        assertEquals(grid.numberOfOpenSites(), 0);
        assertEquals(grid.isOpen(1, 1), false);

        grid.open(1,1);
        assertEquals(grid.numberOfOpenSites(), 1);
        assertEquals(grid.isOpen(1, 1), true);
    }

    @Test
    void testSuccessPercolation(){
        Percolation grid = new Percolation(5);
        grid.open(1, 1);
        grid.open(2, 1);
        grid.open(3, 1);
        grid.open(4, 1);
        grid.open(5, 1);

        assertEquals(grid.percolates(), true, "should percolate if entire column is open");
    }

    @Test
    void testSuccessPercolation2(){
        Percolation grid = new Percolation(5);
        grid.open(1, 1);
        grid.open(2, 1);
        grid.open(3, 1);
        grid.open(3, 2);
        grid.open(3, 3);
        grid.open(3, 4);
        grid.open(3, 5);
        grid.open(4, 5);
        grid.open(5, 5);

        assertEquals(grid.percolates(), true, "should percolate if there is complex pattern");
    }

    @Test
    void testIsFullCorrectness(){
        Percolation grid = new Percolation(5);
        grid.open(1, 1);
        grid.open(2, 1);
        grid.open(3, 1);
        assertEquals(grid.isFull(3, 1), true, "should be full 1");
        assertEquals(grid.isFull(3, 2), false, "should be full 2");
        grid.open(1, 3);
        grid.open(2, 3);
        grid.open(3, 3);
        assertEquals(grid.isFull(3, 3), true, "should be full 3");
        assertEquals(grid.isFull(3, 2), false, "should be full 4");
        grid.open(3, 2);
        assertEquals(grid.isFull(3, 1), true, "should be full 5");
        assertEquals(grid.isFull(3, 2), true, "should be full 6");
        assertEquals(grid.isFull(3, 3), true, "should be full 7");

        assertEquals(grid.isFull(2, 1), true, "should be full 8");
        assertEquals(grid.isFull(4, 1), false, "should be full 9");
    }

    @Test
    void testIsFullCorrectness2(){
        Percolation grid = new Percolation(5);
        grid.open(1, 1);
        grid.open(1, 2);
        grid.open(1, 3);
        assertEquals(grid.isFull(1, 1), true, "should be full 1");
        assertEquals(grid.isFull(1, 2), true, "should be full 2");
        assertEquals(grid.isFull(1, 3), true, "should be full 3");
    }

    @Test
    void testNEqual1(){
        Percolation grid = new Percolation(1);
        grid.open(1, 1);
//        grid.open(1, 2);
//        grid.open(1, 3);
        assertEquals(grid.isFull(1, 1), true, "should be full 1");
        assertEquals(grid.percolates(), true, "shoudl percolate");
    }

        @Test
    void testNEqual2(){
        Percolation grid = new Percolation(2);
        grid.open(1, 1);
        assertEquals(grid.isFull(1, 1), true, "should be full 1");
        assertEquals(grid.isFull(1, 2), false, "should be full 2");
        assertEquals(grid.isFull(2, 1), false, "should be full 3");
        assertEquals(grid.isFull(2, 2), false, "should be full 4");
        assertEquals(grid.numberOfOpenSites(), 1, "numberOfOpenSites should be 1");
        assertEquals(grid.percolates(), false, "shoudl percolate");

        grid.open(1, 2);
        assertEquals(grid.isFull(1, 1), true, "should be full 1");
        assertEquals(grid.isFull(1, 2), true, "should be full 2");
        assertEquals(grid.isFull(2, 1), false, "should be full 3");
        assertEquals(grid.isFull(2, 2), false, "should be full 4");
        assertEquals(grid.numberOfOpenSites(), 2, "numberOfOpenSites should be 1");
        assertEquals(grid.percolates(), false, "shoudl percolate");

        grid.open(2, 2);
        assertEquals(grid.isFull(1, 1), true, "should be full 1");
        assertEquals(grid.isFull(1, 2), true, "should be full 2");
        assertEquals(grid.isFull(2, 1), false, "should be full 3");
        assertEquals(grid.isFull(2, 2), true, "should be full 4");
        assertEquals(grid.numberOfOpenSites(), 3, "numberOfOpenSites should be 1");
        assertEquals(grid.percolates(), true, "shoudl percolate");

        grid.open(2, 1);
        assertEquals(grid.isFull(1, 1), true, "should be full 1");
        assertEquals(grid.isFull(1, 2), true, "should be full 2");
        assertEquals(grid.isFull(2, 1), true, "should be full 3");
        assertEquals(grid.isFull(2, 2), true, "should be full 4");
        assertEquals(grid.numberOfOpenSites(), 4, "numberOfOpenSites should be 1");
        assertEquals(grid.percolates(), true, "shoudl percolate");
    }

        @Test
    void testNEqual5(){
        Percolation grid = new Percolation(5);
        grid.open(5, 5);
        grid.open(3, 5);
        assertEquals(grid.percolates(), false, "shoudl percolate");
        assertEquals(grid.isFull(4, 5), false, "should be full 1");
        grid.open(2, 5);
        grid.open(1, 5);
        assertEquals(grid.isFull(3, 5), true, "should be full 2");
        assertEquals(grid.isFull(4, 5), false, "should be full 3");
        assertEquals(grid.isFull(5, 5), false, "should be full 4");
        assertEquals(grid.percolates(), false, "shoudl percolate");
//        grid.open(1, 2);
//        grid.open(1, 3);
//        assertEquals(grid.isFull(1, 1), true, "should be full 1");
//        assertEquals(grid.percolates(), true, "shoudl percolate");
    }
}