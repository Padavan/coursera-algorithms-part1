import edu.princeton.cs.algs4.Digraph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SAPTest {
    private Digraph graph;

    private void initGraph() {
        this.graph = new Digraph(7);
        this.graph.addEdge(1, 0);
        this.graph.addEdge(2, 1);
        this.graph.addEdge(3, 1);
        this.graph.addEdge(4, 0);
        this.graph.addEdge(5, 4);
        this.graph.addEdge(6, 4);
    }

    private void initGraph2() {
        this.graph = new Digraph(25);
        this.graph.addEdge(1, 0);
        this.graph.addEdge(2, 0);
        this.graph.addEdge(3, 1);
        this.graph.addEdge(4, 1);
        this.graph.addEdge(5, 2);
        this.graph.addEdge(6, 2);
        this.graph.addEdge(7, 3);
        this.graph.addEdge(8, 3);
        this.graph.addEdge(9, 3);
        this.graph.addEdge(10, 5);
        this.graph.addEdge(11, 5);
        this.graph.addEdge(12, 5);
        this.graph.addEdge(13, 7);
        this.graph.addEdge(14, 7);
        this.graph.addEdge(15, 9);
        this.graph.addEdge(16, 9);
        this.graph.addEdge(17, 10);
        this.graph.addEdge(18, 10);
        this.graph.addEdge(19, 12);
        this.graph.addEdge(20, 12);
        this.graph.addEdge(21, 16);
        this.graph.addEdge(22, 16);
        this.graph.addEdge(23, 20);
        this.graph.addEdge(24, 20);
    }

    @Test
    void length1() {
        this.initGraph();

        SAP sap = new SAP(this.graph);
        int length = sap.length(2, 3);

        assertEquals(length, 2);
    }

    @Test
    void length2() {
        this.initGraph();

        SAP sap = new SAP(this.graph);
        int length = sap.length(2, 5);

        assertEquals(length, 4);
    }

    @Test
    void lengthOfSame() {
        this.initGraph();

        SAP sap = new SAP(this.graph);
        int length = sap.length(2, 2);

        assertEquals(length, 0);
    }

    @Test
    void lengthOfCycled() {
        Digraph graph = new Digraph(6);

        graph.addEdge(1, 0);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 0);

        SAP sap = new SAP(graph);
        int length = sap.length(1, 3);
        int ancestor = sap.ancestor(1, 3);

        assertEquals(length, 2);
        assertEquals(ancestor, 3);

        int length2 = sap.length(3, 1);
        int ancestor2 = sap.ancestor(3, 1);

        assertEquals(length2, 2);
        assertEquals(ancestor2, 3);
    }

    @Test
    void ancestor1() {
        this.initGraph();

        SAP sap = new SAP(this.graph);
        int length = sap.ancestor(2, 3);

        assertEquals(length, 1);
    }

    @Test
    void ancestor2() {
        this.initGraph();

        SAP sap = new SAP(this.graph);
        int length = sap.ancestor(2, 5);

        assertEquals(length, 0);
    }

    @Test
    void ancestorSame() {
        this.initGraph();

        SAP sap = new SAP(this.graph);
        int length = sap.ancestor(2, 2);

        assertEquals(length, 2);
    }

    @Test
    void arrayLength1() {
        this.initGraph2();

        SAP sap = new SAP(this.graph);
        List<Integer> a = new ArrayList<>(Arrays.asList(13, 23, 24));
        List<Integer> b = new ArrayList<>(Arrays.asList(16, 17, 6));
        int length = sap.length(a, b);

        assertEquals(length, 4);
    }

    @Test
    void arrayAncestor1() {
        this.initGraph2();

        SAP sap = new SAP(this.graph);
        List<Integer> a = new ArrayList<>(Arrays.asList(13, 23, 24));
        List<Integer> b = new ArrayList<>(Arrays.asList(16, 17, 6));
        int ancestor = sap.ancestor(a, b);

        assertEquals(ancestor, 3);
    }

    @Test
    void arrayAncestorSame() {
        this.initGraph2();

        SAP sap = new SAP(this.graph);
        List<Integer> a = new ArrayList<>(Arrays.asList(13, 23, 24, 2));
        List<Integer> b = new ArrayList<>(Arrays.asList(16, 17, 6, 2));
        int ancestor = sap.ancestor(a, b);

        assertEquals(ancestor, 2);
    }

    @Test
    void arrayLengthSame() {
        this.initGraph2();

        SAP sap = new SAP(this.graph);
        List<Integer> a = new ArrayList<>(Arrays.asList(13, 23, 24, 2));
        List<Integer> b = new ArrayList<>(Arrays.asList(16, 17, 6, 2));
        int ancestor = sap.length(a, b);

        assertEquals(ancestor, 0);
    }
}