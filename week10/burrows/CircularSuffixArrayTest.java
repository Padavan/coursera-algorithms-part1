import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomTSTest {
    @Test
    void index1() {
        CircularSuffixArray array = new CircularSuffixArray("ABRACADABRA!");

        assertEquals(array.index(0), 11);
        assertEquals(array.index(3), 0);
        assertEquals(array.index(6), 8);
        assertEquals(array.index(7), 1);
        assertEquals(array.index(10), 9);
    }


}