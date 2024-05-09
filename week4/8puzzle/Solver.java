import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private Node tree = null;

    private class Node {
        Board item;
        int moves;
        Node next;

        public Node(Board item, int moves) {
            this.item = item;
            this.moves = moves;
            this.next = null;
        }
    }

    //     // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        Board initialTwin = initial.twin();


        boolean solved = false;
        MinPQ<Node> pq = new MinPQ<Node>(this.comparator());
        MinPQ<Node> pqTwin = new MinPQ<Node>(this.comparator());
        pq.insert(new Node(initial, 0));
        pqTwin.insert(new Node(initialTwin, 0));

        if (initial.isGoal()) {
            this.tree = new Node(initial, 0);
            return;
        }
        if (initialTwin.isGoal()) {
            this.tree = null;
            return;
        }

        while (!solved) {

            // extract success board from pq and put it in tree
            Node searchNode = pq.delMin();
            Node searchNodeTwin = pqTwin.delMin();

            // get next state and check if twin tree contain goal
            Iterable<Board> neiboursTwin = searchNodeTwin.item.neighbors();
            for (Board twin : neiboursTwin) {
                if (twin.isGoal()) {
                    this.tree = null;
                    solved = true;
                    return;
                }
                // critical optimization
                if (searchNodeTwin.next == null || !twin.equals(searchNodeTwin.next.item)) {
                    Node nextTwinNode = new Node(twin, searchNodeTwin.moves + 1);
                    nextTwinNode.next = searchNodeTwin;
                    pqTwin.insert(nextTwinNode);
                }
            }

            // get next state
            Iterable<Board> neibours = searchNode.item.neighbors();
            for (Board b : neibours) {
                if (b.isGoal()) {
                    Node finalNode = new Node(b, searchNode.moves + 1);
                    finalNode.next = searchNode;
                    this.tree = finalNode;
                    solved = true;

                    return;
                }

                // critical optimization
                if (searchNode.next == null || !b.equals(searchNode.next.item)) {
                    Node nextNode = new Node(b, searchNode.moves + 1);
                    nextNode.next = searchNode;
                    pq.insert(nextNode);
                }
            }

            // rotation++;
        }
    }

    private Comparator<Node> comparator() {
        return new ManhattanOrder();
    }

    private class ManhattanOrder implements Comparator<Node> {
        public int compare(Node a, Node b) {
            if (a == null || b == null) {
                throw new NullPointerException();
            }

            int priorityA = a.item.manhattan() + a.moves;
            int priorityB = b.item.manhattan() + b.moves;

            if (priorityA < priorityB) {
                return -1;
            }
            else if (priorityB < priorityA) {
                return 1;
            }

            return 0;
        }
    }

    //
    //     // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.tree != null;
    }

    //
    //     // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (this.tree == null) {
            return -1;
        }

        int count = 0;
        Node current = this.tree;
        while (current.next != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    //
    //     // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> boards = new Stack<Board>();
        if (this.tree == null) {
            return null;
        }
        Node current = this.tree;
        while (current != null) {
            boards.push(current.item);
            current = current.next;
        }

        return boards;
    }

    //
    //     // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}