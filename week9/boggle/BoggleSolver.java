import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    private final CustomTST dict;

    public BoggleSolver(String[] dictionary) {
        this.dict = new CustomTST();
        for (String word : dictionary) {
            this.dict.put(word, word.length());
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        SET<String> findedWords = new SET<String>();

        ST<Integer, Bag<Integer>> precomputedMap = precomputeBoard(board);
        ST<Integer, String> charMap = precomputeBoardCharacters(board);
//        for (int x : charMap.keys()) {
//            System.out.println("key: " + x);
//        }

        for (int point : precomputedMap.keys()) {
            boolean[] marked = new boolean[board.rows() * board.cols()];
            traverse(findedWords, precomputedMap, charMap, point, marked, "");
        }

        return findedWords;
    }

    private ST<Integer, Bag<Integer>> precomputeBoard(BoggleBoard board) {
        ST<Integer, Bag<Integer>> map = new ST<Integer, Bag<Integer>>();

        for (int y = 0; y < board.rows(); y++) {
            for (int x = 0; x < board.cols(); x++) {
                int index = y * board.cols() + x;
                Bag<Integer> bag = new Bag<Integer>();

                if (this.checkCoordExist(x - 1, y, board.rows(), board.cols())) {
                    bag.add(y * board.cols() + x - 1);
                }

                if (this.checkCoordExist(x + 1, y, board.rows(), board.cols())) {
                    bag.add(y * board.cols() + x + 1);
                }

                if (this.checkCoordExist(x - 1, y + 1, board.rows(), board.cols())) {
                    bag.add((y + 1) * board.cols() + x - 1);
                }

                if (this.checkCoordExist(x, y + 1, board.rows(), board.cols())) {
                    bag.add((y + 1) * board.cols() + x);
                }

                if (this.checkCoordExist(x + 1, y + 1, board.rows(), board.cols())) {
                    bag.add((y + 1) * board.cols() + x + 1);
                }

                if (this.checkCoordExist(x - 1, y - 1, board.rows(), board.cols())) {
                    bag.add((y - 1) * board.cols() + x - 1);
                }

                if (this.checkCoordExist(x, y - 1, board.rows(), board.cols())) {
                    bag.add((y - 1) * board.cols() + x);
                }

                if (this.checkCoordExist(x + 1, y - 1, board.rows(), board.cols())) {
                    bag.add((y - 1) * board.cols() + x + 1);
                }

                map.put(index, bag);
            }
        }

        return map;
    }

    private ST<Integer, String> precomputeBoardCharacters(BoggleBoard board) {
        ST<Integer, String> map = new ST<Integer, String>();

        for (int y = 0; y < board.rows(); y++) {
            for (int x = 0; x < board.cols(); x++) {
                int index = y * board.cols() + x;
                char letter = board.getLetter(y, x);
                if (letter == 'Q') {
                    map.put(index, "QU");
                } else {
                    map.put(index, Character.toString(letter));
                }
            }
        }

        return map;
    }

    private void traverse(
            SET<String> findedWords,
            ST<Integer, Bag<Integer>> precomputedMap,
            ST<Integer, String> charMap,
            int point,
            boolean[] visited,
            String str
    ) {
        if (visited[point]) {
            return;
        }

        String newChar = charMap.get(point);
//        System.out.println("NewChar: " + newChar);

        String checkedStr = str.concat(newChar);

//        System.out.println("checked: " + checkedStr);

        if (checkedStr.length() >= 3 && this.dict.contains(checkedStr)) {
            findedWords.add(checkedStr);
        }

        if (checkedStr.length() >= 3 && !this.dict.isPrefixExist(checkedStr)) {
            return;
        }

        boolean[] visitedClone = visited.clone();
        visitedClone[point] = true;

        for (int newPoint : precomputedMap.get(point)) {
            traverse(findedWords, precomputedMap, charMap, newPoint, visitedClone, checkedStr);
        }

    }

    private boolean checkCoordExist(int x, int y, int rows, int columns) {
        return x >= 0 && x < columns && y >= 0 && y < rows;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!this.dict.contains(word)) {
            return 0;
        } else if (word.length() == 3 || word.length() == 4) {
            return 1;
        } else if (word.length() == 5) {
            return 2;
        } else if (word.length() == 6) {
            return 3;
        } else if (word.length() == 7) {
            return 5;
        } else {
            return 11;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }


}