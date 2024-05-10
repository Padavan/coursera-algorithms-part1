import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
   public static void main(String[] args) {
       int numberOfWords = Integer.parseInt(args[0]);

       double index = 1;
       Deque<String> deck = new Deque<String>();

       while (!StdIn.isEmpty()) {
           String word = StdIn.readString();
            double coefficient = numberOfWords > index ? 1.0 : numberOfWords / index;
            boolean isChosen = StdRandom.bernoulli(coefficient);
            if (isChosen) {
                int currentSize = deck.size();
                if (currentSize >= numberOfWords) {
                    deck.removeFirst();
                }
                deck.addLast(word);
            }
            index = index + 1;
        }

        for (String w: deck) {
            StdOut.println(w);
        }
   }
}