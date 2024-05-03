 public class LinkedStackOfStrings {
    private Node first = null;

    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(String item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    public String pop() {
        String item = first.item;
        first = first.next;
        return item;
    }
}

// memory performance
private class Node {
    String item;
    Node next;
}

/**
 * 
 * 16 bytes - object overhead
 * 8 bytes - extra overhead ( inner class extra overhead)
 * 8 bytes - reference to string 
 * 8 bytes - reference to Node 
 * ---
 * 40 bytes per stack node
 **/
