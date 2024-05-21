import java.util.Arrays;

public class CircularSuffixArray {
    private String s;
    private CircularSuffix[] suffixes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }

        this.s = s;
        this.suffixes = new CircularSuffix[s.length()];

        for (int i = 0; i < s.length(); i++)
            this.suffixes[i] = new CircularSuffix(this.s, i, i);
        Arrays.sort(this.suffixes);
    }

    private class CircularSuffix implements Comparable<CircularSuffix> {
        private String refString;
        private int offset;
        private int index;

        public CircularSuffix(String s, int offset, int index) {
            this.refString = s;
            this.offset = offset;
            this.index = index;
        }

        public int compareTo(CircularSuffixArray.CircularSuffix that) {
            int thisIndex = this.offset;
            int thatIndex = that.offset;
            int diff = 0;
            for (int i = 0; i < this.refString.length(); i++) {
                diff = s.charAt(thisIndex++) - s.charAt(thatIndex++);
                if (diff != 0) return diff;
                if (thisIndex >= this.refString.length()) thisIndex = 0;
                if (thatIndex >= this.refString.length()) thatIndex = 0;
            }
            return diff;
        }
    }

    // length of s
    public int length() {
        return this.s.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= this.length()) {
            throw new IllegalArgumentException();
        }

        return this.suffixes[i].index;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}