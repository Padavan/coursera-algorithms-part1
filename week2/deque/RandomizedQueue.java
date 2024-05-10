import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first;

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
    }

    private class Node {
        Item item;
        Node next;

        public Node(Item item) {
            this.item = item;
            this.next = null;
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        int count = 0;
         Node current = first;
         while (current != null) {
             count++;
             current = current.next;
         }
        return count;
    }

    // add the item
    public void enqueue(Item item) {
         if (item == null) {
            throw new IllegalArgumentException();
        }

         Node newItem = new Node(item);
         newItem.next = first;

         first = newItem;
    }

    // remove and return a random item
    public Item dequeue() {
        if (first == null) {
            throw new NoSuchElementException();
        }

        int size = this.size();

        int position = StdRandom.uniform(size);

        if (size == 1) {
             Item itemToReturn = first.item;
             first = null;
             return itemToReturn;
        }

        if (position == 0) {
            return this.removeFirst();
        }

        int count = 0;
        Node current = first;
        while (count != (position - 1)) {
            count++;
            current = current.next;
        }
        Node previous = current;
        current = current.next;
        previous.next = current.next;
        current.next = null;

        return current.item;
    }

    private Item removeFirst() {
        Node temp = first;
        first = first.next;
        temp.next = null;
        return temp.item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        int position = StdRandom.uniform(this.size());
        int count = 0;
        Node current = first;
        while (count != position) {
            count++;
            current = current.next;
        }
        return current.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements  Iterator<Item> {
        private int[] randomArray = null;

        public boolean hasNext() {

            if (randomArray == null) {
                int count = 0;
                 Node counter = first;
                 while (counter != null) {
                     count++;
                     counter = counter.next;
                 }
                 this.generateRandomPositions(count);
                 return count > 0;
            } else {
                return this.randomArray.length > 0;
            }
        }

        private void generateRandomPositions(int size) {
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = i;
            }
            StdRandom.shuffle(arr);
            this.randomArray = arr;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (this.randomArray.length == 0) {
                throw new NoSuchElementException();
            }
            int position = this.randomArray[this.randomArray.length - 1];

            int count = 0;
            Node current = first;
            while (count != position) {
                count++;
                current = current.next;
            }

            Item item = current.item;

            int randomArrSize = this.randomArray.length - 1;
            int[] newRandomArray = new int[randomArrSize];
            for (int i = 0; i < randomArrSize; i++) {
                newRandomArray[i] = this.randomArray[i];
            }
            StdRandom.shuffle(newRandomArray);
            this.randomArray = newRandomArray;

            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("Test");
    }
}
