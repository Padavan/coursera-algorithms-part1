import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
class BoardTest {

    @Test
    void hamming() {
        int[][] matrix = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board board = new Board(matrix);
        int evaluation = board.hamming();
        assertEquals(evaluation, 5);
    }

    @Test
    void manhattan() {
        int[][] matrix = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board board = new Board(matrix);
        int evaluation = board.manhattan();
        assertEquals(evaluation, 10);
    }

    @Test
    void manhattan2() {
        int[][] matrix = { { 1, 0 }, { 2, 3 } };
        Board board = new Board(matrix);
        int evaluation = board.manhattan();
        assertEquals(evaluation, 3);
    }


    @Test
    void isGoal() {
        int[][] matrix = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board board = new Board(matrix);
        boolean isGoal = board.isGoal();
        assertEquals(isGoal, false);
    }

    @Test
    void isGoal2() {
        int[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        Board board = new Board(matrix);
        boolean isGoal = board.isGoal();
        assertEquals(isGoal, true);
    }

    @Test
    void iterator() {
        int[][] matrix = { { 1, 0, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        Board currentBoard = new Board(matrix);
        int count = 0;
        for (Board x : currentBoard.neighbors()) {
            StdOut.println(x.toString());
            count++;
        }
        assertEquals(count, 3);
    }

    @Test
    void iterator2() {
        int[][] matrix = { { 1, 0 }, { 2, 3 } };
        Board currentBoard = new Board(matrix);
        int count = 0;
        for (Board x : currentBoard.neighbors()) {
            StdOut.println(x.toString());
            count++;
        }
        assertEquals(count, 2);
    }

    @Test
    void iterator4() {
        int[][] matrix = { { 1, 2, 3 }, { 4, 0, 5 }, { 7, 8, 6 } };
        Board currentBoard = new Board(matrix);
        int count = 0;
        for (Board x : currentBoard.neighbors()) {
            StdOut.println(x.toString());
            count++;
        }
        assertEquals(count, 4);
    }

    @Test
    void iterator5() {
        int[][] matrix = { { 1, 2, 3 }, { 4, 6, 5 }, { 7, 8, 0 } };
        Board currentBoard = new Board(matrix);
        int count = 0;
        for (Board x : currentBoard.neighbors()) {
            StdOut.println(x.toString());
            count++;
        }
        assertEquals(count, 2);
    }

    @Test
    void iterator6() {
        int[][] matrix = { { 1, 2, 3 }, { 4, 6, 5 }, { 0, 8, 7 } };
        Board currentBoard = new Board(matrix);
        int count = 0;
        for (Board x : currentBoard.neighbors()) {
            StdOut.println(x.toString());
            count++;
        }
        assertEquals(count, 2);
    }

    @Test
    void iterator7() {
        int[][] matrix = { { 1, 2, 3 }, { 0, 6, 5 }, { 4, 8, 7 } };
        Board currentBoard = new Board(matrix);
        int count = 0;
        for (Board x : currentBoard.neighbors()) {
            StdOut.println(x.toString());
            count++;
        }
        assertEquals(count, 3);
    }

    @Test
    void equal() {
        int[][] matrix = { { 1, 2, 3 }, { 0, 6, 5 }, { 4, 8, 7 } };
        int[][] matrix2 = { { 1, 2, 3 }, { 0, 6, 5 }, { 4, 8, 7 } };
        Board board1 = new Board(matrix);
        Board board2 = new Board(matrix2);

        assertEquals(board1.equals(board2), true);
    }

    @Test
    void equal2() {
        int[][] matrix = { { 1, 2, 3 }, { 6, 0, 5 }, { 4, 8, 7 } };
        int[][] matrix2 = { { 1, 2, 3 }, { 0, 6, 5 }, { 4, 8, 7 } };
        Board board1 = new Board(matrix);
        Board board2 = new Board(matrix2);

        assertEquals(board1.equals(board2), false);
    }
}