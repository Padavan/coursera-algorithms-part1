import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
    private double[][] energyMatrix;
    private boolean isTransposed;
    private int[][] rgb;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = picture;
        int height = this.picture.height();
        int width = this.picture.width();

        this.rgb = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int color = this.picture.getRGB(col, row);
                this.rgb[row][col] = color;
            }
        }

        this.calculateEnergy();
    }

    private void calculateEnergy() {
        int height = this.picture.height();
        int width = this.picture.width();

        int[][] red = new int[height][width];
        int[][] green = new int[height][width];
        int[][] blue = new int[height][width];
        this.energyMatrix = new double[height][width];
        this.isTransposed = false;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int color = this.rgb[row][col];

                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = (color >> 0) & 0xFF;
                red[row][col] = r;
                green[row][col] = g;
                blue[row][col] = b;
            }
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (col == 0 || row == 0 || row == height - 1
                        || col == width - 1) {
                    this.energyMatrix[row][col] = 1000.0;
                    continue;
                }

                int redXdiff = red[row][col + 1] - red[row][col - 1];
                int greenXdiff = green[row][col + 1] - green[row][col - 1];
                int blueXdiff = blue[row][col + 1] - blue[row][col - 1];
                int xDiff = redXdiff * redXdiff + greenXdiff * greenXdiff
                        + blueXdiff * blueXdiff;

                int redYdiff = red[row + 1][col] - red[row - 1][col];
                int greenYdiff = green[row + 1][col] - green[row - 1][col];
                int blueYdiff = blue[row + 1][col] - blue[row - 1][col];
                int yDiff = redYdiff * redYdiff + greenYdiff * greenYdiff
                        + blueYdiff * blueYdiff;

                int result = xDiff + yDiff;
                // TODO more precise
                this.energyMatrix[row][col] = Math.sqrt(result);
            }
        }
    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture
    public int width() {
        return this.picture.width();
    }

    // height of current picture
    public int height() {
        return this.picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= this.width() || y < 0 || y >= this.height()) {
            throw new IllegalArgumentException();
        }
        if (this.isTransposed) {
            return this.energyMatrix[x][y];
        } else {
            return this.energyMatrix[y][x];
        }
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (!this.isTransposed) {
            this.energyMatrix = this.transposeMatrix(this.energyMatrix);
            this.isTransposed = true;
        }

        return this.findSeam();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (this.isTransposed) {
            this.energyMatrix = this.transposeMatrix(this.energyMatrix);
            this.isTransposed = false;
        }

        return this.findSeam();
    }

    private int[] findSeam() {
        double[][] evaluation = this.evaluate(this.energyMatrix);
        int[] result = new int[this.energyMatrix.length];

        int lowIndex = 0;
        int highIndex = this.energyMatrix[0].length - 1;
        int minIndex = 0;
        int row = this.energyMatrix.length - 1;

        while (row >= 0) {
            for (int i = lowIndex; i <= highIndex; i++) {
                if (evaluation[row][i] < evaluation[row][minIndex]) {
                    minIndex = i;
                }
                result[row] = lowIndex;
            }

            lowIndex = minIndex > 0 ? minIndex - 1 : minIndex;
            highIndex = minIndex < this.energyMatrix[0].length - 2 ? minIndex + 1 : minIndex;
            result[row] = minIndex;
            minIndex = lowIndex;
            row--;
        }

        return result;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != this.width()) {
            throw new IllegalArgumentException();
        }

        int[][] transposedRgb = this.transposeMatrix(this.rgb);

        int oldWidth = transposedRgb[0].length;
        int newWidth = transposedRgb[0].length - 1;
        int newHeight = transposedRgb.length;
        int[][] newRgb = new int[newHeight][newWidth];
        for (int row = 0; row < newHeight; row++) {
            if (seam[row] < 0 || seam[row] >= oldWidth || (row > 0 && Math.abs(seam[row] - seam[row - 1]) > 1)) {
                throw new IllegalArgumentException();
            }
            int[] newRow = new int[newWidth];
            newRgb[row] = newRow;
            System.arraycopy(transposedRgb[row], 0, newRow, 0, seam[row]);
            System.arraycopy(transposedRgb[row], seam[row] + 1, newRow, seam[row], newWidth - seam[row]);
        }

        this.rgb = this.transposeMatrix(newRgb);
        this.redraw();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != this.height()) {
            throw new IllegalArgumentException();
        }

        int oldWidth = this.width();
        int newWidth = this.width() - 1;
        int newHeight = this.height();
        int[][] newRgb = new int[newHeight][newWidth];
        for (int row = 0; row < newHeight; row++) {
            if (seam[row] < 0 || seam[row] >= oldWidth || (row > 0 && Math.abs(seam[row] - seam[row - 1]) > 1)) {
                throw new IllegalArgumentException();
            }
            int[] newRow = new int[newWidth];
            newRgb[row] = newRow;
            System.arraycopy(this.rgb[row], 0, newRow, 0, seam[row]);
            System.arraycopy(this.rgb[row], seam[row] + 1, newRow, seam[row], newWidth - seam[row]);
        }

        this.rgb = newRgb;
        this.redraw();
    }

    private void redraw() {
        int newHeight = this.rgb.length;
        int newWidth = this.rgb[0].length;
        this.picture = new Picture(newWidth, newHeight);

        for (int row = 0; row < newHeight; row++) {
            for (int col = 0; col < newWidth; col++) {
                this.picture.setRGB(col, row, this.rgb[row][col]);
            }
        }

        this.calculateEnergy();
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        System.out.println("Seam Carver");
    }

    private double[][] evaluate(double[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        double[][] evaluation = new double[height][width];

        for (int row = 0; row < height - 1; row++) {
            for (int col = 0; col < width; col++) {
                double currentWeight = evaluation[row][col];

                if (currentWeight + matrix[row + 1][col] < evaluation[row + 1][col]
                        || evaluation[row + 1][col] == 0) {
                    evaluation[row + 1][col] = currentWeight + matrix[row + 1][col];
                }

                if (col > 0 && col < width - 1 && (
                        currentWeight + matrix[row + 1][col - 1] < evaluation[row + 1][col
                                - 1] || evaluation[row + 1][col - 1] == 0)) {
                    evaluation[row + 1][col - 1] = currentWeight + matrix[row + 1][col
                            - 1];
                }

                if (col > 0 && col < width - 1 && (
                        currentWeight + matrix[row + 1][col + 1] < evaluation[row + 1][col
                                + 1] || evaluation[row + 1][col + 1] == 0)) {
                    evaluation[row + 1][col + 1] = currentWeight + matrix[row + 1][col
                            + 1];
                }

            }
        }

        return evaluation;
    }

    private double[][] transposeMatrix(double[][] matrix) {
        double[][] temp = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                temp[j][i] = matrix[i][j];
        return temp;
    }

    private int[][] transposeMatrix(int[][] matrix) {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                temp[j][i] = matrix[i][j];
        return temp;
    }
}