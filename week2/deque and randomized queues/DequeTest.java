import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DequeTest {
    @Test
    void testOpenAndCount() {
        Deque<String> deck = new Deque();
        deck.addFirst("Test");
        String poppedItem = deck.removeFirst();
        assertEquals(poppedItem, "Test");
    }

    @Test
    void testOpenAndCount2() {
        Deque<String> deck = new Deque();
        assertEquals(deck.isEmpty(), true);
        deck.addFirst("1");
        deck.addFirst("2");
        deck.addFirst("3");
        assertEquals(deck.isEmpty(), false);

        assertEquals(deck.removeLast(), "1");
        assertEquals(deck.removeFirst(), "3");
        assertEquals(deck.size(), 1);
    }

    @Test
    void testIterator() {
        Deque<Integer> deck = new Deque();
        deck.addFirst(1);
        deck.addFirst(2);
        deck.addFirst(3);

        int sum = 0;
        for (Integer item: deck) {
            System.out.println(item);
            sum = sum + item;
        }

        assertEquals(sum, 6);
    }
}