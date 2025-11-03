package graph.scc;

import graph.model.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TarjanSCCTest {

    @Test
    void testSimpleGraphWithCycle() {
        Graph g = new Graph(5, false);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);
        g.addEdge(3, 4, 1);

        TarjanSCC tarjan = new TarjanSCC(g);
        SCCResult result = tarjan.run();

        assertEquals(3, result.getComponentCount());
        List<List<Integer>> comps = result.getComponents();

        boolean foundCycle = comps.stream().anyMatch(c -> c.size() == 3);
        assertTrue(foundCycle);
    }

    @Test
    void testSingleNode() {
        Graph g = new Graph(1, false);
        TarjanSCC t = new TarjanSCC(g);
        SCCResult res = t.run();
        assertEquals(1, res.getComponentCount());
        assertEquals(List.of(List.of(0)), res.getComponents());
    }
}
