import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int topNode;
    private final int bottomNode;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private final int size;
    private int openedCount = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Argument should be more than 0");
        }

        this.grid = new boolean[n][n];
        this.topNode = n * n;
        this.bottomNode = n * n + 1;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.size = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > this.size || col > this.size) {
            throw new IllegalArgumentException("should be less than side but got" + row + " " + col);
        }
        if (this.isOpen(row, col)) {
            return;
        }
        this.openedCount++;
        this.grid[row-1][col-1] = true;

        int currentIndex = getIndex(row, col);
        if (row == 1) {
            this.uf.union(this.topNode, currentIndex);
        }

        if (row == this.size) {
            this.uf.union(this.bottomNode, currentIndex);
        }

        if ((col - 1) >= 1 && this.isOpen(row,  col - 1)) {
            this.uf.union(currentIndex, this.getIndex(row, col - 1));
        }

        if ((col + 1) <= this.size && this.isOpen(row, col + 1)) {
            this.uf.union(currentIndex, this.getIndex(row, col + 1));
        }

        if ((row + 1) <= this.size && this.isOpen(row + 1, col)) {
            this.uf.union(currentIndex, this.getIndex(row + 1, col));
        }

        if ((row - 1) >= 1 && this.isOpen(row - 1, col)) {
            this.uf.union(currentIndex, this.getIndex(row - 1, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > this.size || col > this.size) {
            throw new IllegalArgumentException("should be less than side but got" + row + " " + col);
        }
        return this.grid[row-1][col-1];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > this.size || col > this.size) {
            throw new IllegalArgumentException("should be less than side but got" + row + " " + col);
        }
        int index = this.getIndex(row, col);
        return this.isOpen(row, col) && this.uf.find(this.topNode) == this.uf.find(index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openedCount;
    }

    // does the system percolate?
    public boolean percolates() {
        // 25 and 26 are virtual nodes;
        int groupAlpha1 = uf.find(this.topNode);
        int groupAlpha2 = uf.find(this.bottomNode);

        return groupAlpha1 == groupAlpha2;
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private int getIndex(int row, int col) {
        return this.size * (row - 1) + (col - 1);
    }
}