import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomTSTest {
    @Test
    void isPrefixExistTest() {
        TrieST trie = new TrieST();

        String[] words = {"TEST", "ONE", "TWO", "THREE", "FOUR"};

        for (String word : words) {
            trie.put(word, word.length());
        }

        trie.get("ONE");
        assertEquals(trie.isPrefixExist("THR"), true);
//        assertEquals(trie.get("ONE"), 3);
    }


}