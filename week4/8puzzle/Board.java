import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[][] matrix;
    private int cachedManhattan = -1;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        int[][] createMatrix = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            createMatrix[i] = tiles[i].clone();
        }

        this.matrix = createMatrix;
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.matrix.length);
        str.append('\n');
        for (int[] row : this.matrix) {
            for (int item : row) {
                str.append(" ");
                str.append(item);
                str.append(" ");
            }

            str.append('\n');
        }

        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return this.matrix.length;
    }

    // number of tiles out of place
    public int hamming() {
        int evaluation = 0;
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix.length; j++) {
                int goalIndex = i * this.matrix.length + j + 1;
                if (goalIndex != this.matrix[i][j] && this.matrix[i][j] != 0) {
                    evaluation++;
                }
            }
        }
        return evaluation;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (this.cachedManhattan != -1) {
            return cachedManhattan;
        }
        int evaluation = 0;
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix.length; j++) {
                int goalIndex = i * this.matrix.length + j + 1;
                if (goalIndex != this.matrix[i][j] && this.matrix[i][j] != 0) {
                    int expectedY = (this.matrix[i][j] - 1) / this.matrix.length;
                    int expectedX = (this.matrix[i][j] - 1) % this.matrix.length;

                    int diff = Math.abs(expectedY - i) + Math.abs(expectedX - j);
                    evaluation = evaluation + diff;
                }
            }
        }
        this.cachedManhattan = evaluation;
        return evaluation;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix.length; j++) {
                int goalNumber = this.matrix.length * i + j + 1;
                if (goalNumber != this.matrix[i][j] && this.matrix[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (y == null || getClass() != y.getClass())
            return false;
        Board board = (Board) y;

        if (board.dimension() != this.dimension()) {
            return false;
        }

        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix.length; j++) {
                if (board.matrix[i][j] != this.matrix[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighborBoards = new Stack<Board>();
        int matrixSize = matrix.length;
        int zeroIndex = 0;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrix[i][j] == 0) {
                    zeroIndex = matrixSize * i + j;
                }
            }
        }

        int up = zeroIndex - matrixSize;
        if (up >= 0 && up < matrixSize * matrixSize) {
            Board newBoard = swapTilesByIndex(zeroIndex, up);
            neighborBoards.push(newBoard);
        }

        int down = zeroIndex + matrixSize;
        if (down < matrixSize * matrixSize && down >= 0) {
            Board newBoard = swapTilesByIndex(zeroIndex, down);
            neighborBoards.push(newBoard);
        }

        int left = zeroIndex - 1;
        if (left % matrixSize == (zeroIndex - 1) % matrixSize && left >= 0
                && (left / matrixSize) == (
                zeroIndex
                        / matrixSize)) {
            Board newBoard = swapTilesByIndex(zeroIndex, left);
            neighborBoards.push(newBoard);
        }

        int right = zeroIndex + 1;
        if (right % matrixSize == (zeroIndex + 1) % matrixSize && right < matrixSize * matrixSize
                && (right / matrixSize) == (zeroIndex
                / matrixSize)) {
            Board newBoard = swapTilesByIndex(zeroIndex, right);
            neighborBoards.push(newBoard);
        }

        return neighborBoards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copyMatrix = new int[this.matrix.length][this.matrix.length];
        for (int i = 0; i < this.matrix.length; i++)
            copyMatrix[i] = this.matrix[i].clone();

        int a = 0;
        int b = 1;
        int c = 2;

        int ay = 0;
        int ax = 0;
        if (copyMatrix[ay][ax] == 0) {
            ax = 1;
        }

        int by = 1;
        int bx = 0;
        if (copyMatrix[by][bx] == 0) {
            by = 0;
            bx = 1;
        }

        int[][] twinMatrix = this.swapTiles(copyMatrix, ax, ay, bx, by);
        return new Board(twinMatrix);
    }

    private int[][] swapTiles(int[][] mtrx, int x0, int y0, int x1, int y1) {
        int swapped = mtrx[y0][x0];
        mtrx[y0][x0] = mtrx[y1][x1];
        mtrx[y1][x1] = swapped;
        return mtrx;
    }

    private Board swapTilesByIndex(int idx0, int idx1) {
        int zeroX = idx0 % this.matrix.length;
        int zeroY = idx0 / this.matrix.length;
        int swapX = idx1 % this.matrix.length;
        int swapY = idx1 / this.matrix.length;
        int[][] copyMatrix = new int[this.matrix.length][this.matrix.length];
        for (int i = 0; i < this.matrix.length; i++)
            copyMatrix[i] = this.matrix[i].clone();

        int swapped = copyMatrix[zeroY][zeroX];
        copyMatrix[zeroY][zeroX] = copyMatrix[swapY][swapX];
        copyMatrix[swapY][swapX] = swapped;

        return new Board(copyMatrix);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        System.out.println("test");
    }

}