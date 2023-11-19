import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class RandomWord {
    public static void main(String[] args) {
        String winner = StdIn.readString();
        double index = 2;

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (index == 1) {
                winner = word;
            }

            Boolean isChosen = StdRandom.bernoulli(1.0 / index);
            if (isChosen && index != 1) {
                winner = word;
            }
            index = index + 1;
        }
        StdOut.println(winner);

    }
}