import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RandomizedQueueTest {

    @Test
    void enqueue() {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("0");
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");
        queue.enqueue("4");
        queue.enqueue("5");
        queue.enqueue("6");
        queue.enqueue("7");
        queue.enqueue("8");
        queue.enqueue("9");

        int size = queue.size();
        assertEquals(size, 10);
    }

    @Test
    void dequeue() {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("0");
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");
        queue.enqueue("4");

        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();

        int size = queue.size();
        assertEquals(size, 0);
    }

    @Test
    void sample() {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("0");

        String item = queue.sample();
        assertEquals(item, "0");
    }

    @Test
    void sample2() {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("0");
        queue.enqueue("1");
        queue.enqueue("2");

        String expectedItems[] = {"0","1", "2"};

        String item = queue.sample();
        System.out.println(item);
        assertTrue(Arrays.asList(expectedItems).contains(item));
    }

    @Test
    void iterator() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        int sum = 0;
        for (Integer item: queue) {
            System.out.println(item);
            sum = sum + item;
        }

        assertEquals(sum, 6);
    }
}