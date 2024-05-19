import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
    private final ST<String, Bag<Integer>> wordMap = new ST<String, Bag<Integer>>();
    private final ST<Integer, Bag<String>> idMap = new ST<Integer, Bag<String>>();
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In inSynsets = new In(synsets);

        int maxId = 0;
        while (!inSynsets.isEmpty()) {
            String x = inSynsets.readLine();
            String[] arr = x.split(",");
            int id = Integer.parseInt(arr[0]);
            String[] synsetArray = arr[1].split(" ");

            Bag<String> wordBag = new Bag<String>();
            for (String word : synsetArray) {
                wordBag.add(word);
                if (wordMap.contains(word)) {
                    Bag<Integer> currentIdsBag = wordMap.get(word);
                    currentIdsBag.add(id);
                    wordMap.put(word, currentIdsBag);
                }
                else {
                    Bag<Integer> newIdsBag = new Bag<Integer>();
                    newIdsBag.add(id);
                    wordMap.put(word, newIdsBag);
                }
            }
            idMap.put(id, wordBag);

            maxId = id;
        }

        Digraph graph = new Digraph(maxId + 1);

        In inHypernyms = new In(hypernyms);
        while (!inHypernyms.isEmpty()) {
            String x = inHypernyms.readLine();
            String[] synsetIds = x.split(",");
            int synsetId = Integer.parseInt(synsetIds[0]);
            if (synsetIds.length > 1) {
                for (int i = 1; i < synsetIds.length; i++) {
                    int hypernymId = Integer.parseInt(synsetIds[i]);
                    graph.addEdge(synsetId, hypernymId);
                }
            }
        }
        DirectedCycle cycle = new DirectedCycle(graph);
        if (cycle.hasCycle()) {
            throw new IllegalArgumentException();
        }
        this.sap = new SAP(graph);
    }

    // private class Node {
    //     int item;
    //     int depth;
    //
    //     public Node(int item, int depth) {
    //         this.item = item;
    //         this.depth = depth;
    //     }
    // }
    //
    // private void dfs(int current, int[] marked, int distance) {
    //     // System.out.println("current: " + current + " , distance: " + distance);
    //     if (distance < marked[current] || marked[current] == 0) {
    //         marked[current] = distance;
    //     }
    //     for (int w : this.graph.adj(current)) {
    //         dfs(w, marked, distance + 1);
    //     }
    // }
    //
    // private Node bfs(Queue<Node> queue, int[] marked) {
    //     int ancestor = -1;
    //     int distance = -1;
    //     while (!queue.isEmpty()) {
    //         Node current = queue.dequeue();
    //         int newDistance = marked[current.item] + current.depth;
    //         if (marked[current.item] != 0 && (newDistance < distance || distance == -1)) {
    //             distance = newDistance;
    //             ancestor = current.item;
    //         }
    //         for (int x : this.graph.adj(current.item)) {
    //             queue.enqueue(new Node(x, current.depth + 1));
    //         }
    //     }
    //
    //     return new Node(ancestor, distance);
    // }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordMap.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return wordMap.contains(word);
    }

    // distance between nounA and nounB (defined below)
    // public int distance(String nounA, String nounB) {
    //     if (nounA == null || nounB == null) {
    //         throw new IllegalArgumentException();
    //     }
    //
    //     int[] marked = new int[this.graph.V()];
    //     Bag<Integer> bagA = wordMap.get(nounA);
    //     for (int itemA : bagA) {
    //         // System.out.println("nounA ID: " + itemA);
    //         this.dfs(itemA, marked, 1);
    //     }
    //
    //     Bag<Integer> bagB = wordMap.get(nounB);
    //     Queue<Node> queue = new Queue<Node>();
    //     for (int itemB : bagB) {
    //         // System.out.println("nounB ID: " + itemB);
    //         queue.enqueue(new Node(itemB, -1));
    //     }
    //     Node node = bfs(queue, marked);
    //
    //     return node.depth;
    // }
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }

        Bag<Integer> bagA = wordMap.get(nounA);
        Bag<Integer> bagB = wordMap.get(nounB);
        return this.sap.length(bagA, bagB);
    }

    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }

        Bag<Integer> bagA = wordMap.get(nounA);
        Bag<Integer> bagB = wordMap.get(nounB);

        int ancestorId = this.sap.ancestor(bagA, bagB);

        Bag<String> wordBag = this.idMap.get(ancestorId);

        return String.join(" ", wordBag);
    }


    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    // public String sap(String nounA, String nounB) {
    //     if (nounA == null || nounB == null) {
    //         throw new IllegalArgumentException();
    //     }
    //     int[] marked = new int[this.graph.V()];
    //     Bag<Integer> bagA = wordMap.get(nounA);
    //     for (int itemA : bagA) {
    //         dfs(itemA, marked, 1);
    //     }
    //
    //     Bag<Integer> bagB = wordMap.get(nounA);
    //     Queue<Node> queue = new Queue<Node>();
    //     for (int itemB : bagB) {
    //         queue.enqueue(new Node(itemB, -1));
    //     }
    //
    //     Node node = this.bfs(queue, marked);
    //
    //     String ancestorWord = null;
    //     for (String key : this.nouns()) {
    //         for (int id : wordMap.get(key)) {
    //             if (id == node.item) {
    //                 ancestorWord = key;
    //                 break;
    //             }
    //         }
    //     }
    //
    //     return ancestorWord;
    // }

    // do unit testing of this class
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }
        WordNet wordnet = new WordNet(args[0], args[1]);

        while (!StdIn.isEmpty()) {
            String v = StdIn.readString();
            String w = StdIn.readString();
            int length = wordnet.distance(v, w);
            String ancestor = wordnet.sap(v, w);
            StdOut.printf("length = %d, ancestor = %s\n", length, ancestor);
        }
    }
}