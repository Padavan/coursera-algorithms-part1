import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int[] weight = new int[nouns.length];

        for (int i = 0; i < nouns.length; i++) {
            String target = nouns[i];
            int distanceSum = 0;
            for (String noun : nouns) {
                distanceSum = distanceSum + this.wordnet.distance(noun, target);
            }
            weight[i] = distanceSum;
        }

        int minIndex = 0;
        for (int i = 0; i < weight.length; i++) {
            if (weight[i] < weight[minIndex]) {
                minIndex = i;
            }
        }

        return nouns[minIndex];
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}