import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public final class SAP {
    private final Digraph graph;
    private final int size;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.graph = new Digraph(G);
        this.size = this.graph.V();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        this.checkItem(v);
        this.checkItem(w);
        int[] marked = new int[this.size];
        dfs(v, marked, 1);

        // System.out.println("Marked: " + Arrays.toString(marked));

        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(new Node(w, -1));
        Node node = bfs(queue, marked);

        return node.depth;
    }

    private class Node {
        int item;
        int depth;

        public Node(int item, int depth) {
            this.item = item;
            this.depth = depth;
        }
    }

    private void dfs(int current, int[] marked, int distance) {
        // System.out.println("current: " + current + " , distance: " + distance);
        if (distance > this.size) {
            return;
        }
        if (distance < marked[current] || marked[current] == 0) {
            marked[current] = distance;
        }
        for (int w : this.graph.adj(current)) {
            dfs(w, marked, distance + 1);
        }
    }

    private Node bfs(Queue<Node> queue, int[] marked) {
        int ancestor = -1;
        int distance = -1;
        while (!queue.isEmpty()) {
            Node current = queue.dequeue();
            if (current.depth > this.size || (current.depth > distance && distance != -1)) {
                break;
            }
            int newDistance = marked[current.item] + current.depth;
            if (marked[current.item] != 0 && (newDistance < distance || distance == -1)) {
                distance = newDistance;
                ancestor = current.item;
            }
            for (int x : this.graph.adj(current.item)) {
                queue.enqueue(new Node(x, current.depth + 1));
            }
        }

        return new Node(ancestor, distance);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        this.checkItem(v);
        this.checkItem(w);
        int[] marked = new int[this.size];
        dfs(v, marked, 1);

        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(new Node(w, 0));

        Node node = bfs(queue, marked);

        return node.item;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        int[] marked = new int[this.size];
        for (Integer vv : v) {
            if (vv == null) throw new IllegalArgumentException();
            checkItem(vv);
            dfs(vv, marked, 1);
        }

        Queue<Node> queue = new Queue<Node>();
        for (Integer ww : w) {
            if (ww == null) throw new IllegalArgumentException();
            checkItem(ww);
            queue.enqueue(new Node(ww, -1));
        }

        Node node = bfs(queue, marked);

        return node.depth;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        int[] marked = new int[this.size];
        for (Integer vv : v) {
            if (vv == null) throw new IllegalArgumentException();
            this.checkItem(vv);
            dfs(vv, marked, 1);
        }

        Queue<Node> queue = new Queue<Node>();
        for (Integer ww : w) {
            if (ww == null) throw new IllegalArgumentException();
            this.checkItem(ww);
            queue.enqueue(new Node(ww, 0));
        }

        Node node = bfs(queue, marked);
        return node.item;
    }

    private void checkItem(Integer index) {
        if (index == null || index < 0 || index >= this.size) {
            throw new IllegalArgumentException();
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}