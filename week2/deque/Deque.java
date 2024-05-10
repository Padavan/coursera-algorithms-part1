import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;

    private class Node {
        Item item;
        Node next;
        Node previous;

        public Node(Item item) {
            this.item = item;
            this.previous = null;
            this.next = null;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null && last == null;
    }

    // return the number of items on the deque
    public int size() {
         int count = 0;
         Node current = first;
         while (current != null) {
             count++;
             current = current.previous;
         }
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newItem = new Node(item);
        if (first == null) {
            first = newItem;
            last = newItem;
        } else {
            newItem.previous = first;
            first.next = newItem;
            first = newItem;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newItem = new Node(item);

        if (last == null) {
            first = newItem;
            last = newItem;
        } else {
            newItem.next = last;
            last.previous = newItem;
            last = newItem;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }

         if (last == first) {
             Item itemToReturn = first.item;
             first = null;
             last = null;
             return itemToReturn;
        }

        Node temp = first;
        first = first.previous;
        first.next = null;
        temp.previous = null;
        return temp.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }

         if (last == first) {
             Item itemToReturn = first.item;
             first = null;
             last = null;
             return itemToReturn;
        }

        Node temp = last;
        last = last.next;
        last.previous = null;
        temp.next = null;
        return temp.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements  Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.previous;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("Test");
    }

}