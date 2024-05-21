public class CustomTST {
    private Node root = new Node();

    private class Node {
        private int val;
        private char c;
        private Node left, mid, right;
    }

    public void put(String key, int val) {
        root = this.allocate(root, key, val, 0);
    }

    private Node allocate(Node x, String key, int val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }

        if (c < x.c) {
            x.left = this.allocate(x.left, key, val, d);
        } else if (c > x.c) {
            x.right = this.allocate(x.right, key, val, d);
        } else if (d < key.length() - 1) {
            x.mid = this.allocate(x.mid, key, val, d + 1);
        } else {
            x.val = val;
        }
        return x;
    }

    public boolean contains(String key) {
        return get(key) != 0;
    }

    public int get(String key) {
        Node x = this.getter(root, key, 0);
        if (x == null) return 0;
        return x.val;
    }

    private Node getter(Node x, String key, int d) {
        if (x == null) {
            return null;
        }

        char c = key.charAt(d);

        if (c < x.c) return this.getter(x.left, key, d);
        else if (c > x.c) return this.getter(x.right, key, d);
        else if (d < key.length() - 1) return this.getter(x.mid, key, d + 1);
        else return x;
    }

    public boolean isPrefixExist(String key) {
        return this.checkPrefix(root, key, 0);
    }

    private boolean checkPrefix(Node x, String key, int d) {
        if (x == null) {
            return false;
        }
//        System.out.println("checkPrefix: " + x.toString() + " - " + key + " - " + d);

        char c = key.charAt(d);
        if (c < x.c) return this.checkPrefix(x.left, key, d);
        else if (c > x.c) return this.checkPrefix(x.right, key, d);
        else if (d < key.length() - 1) return this.checkPrefix(x.mid, key, d + 1);
        else return true;
    }
}