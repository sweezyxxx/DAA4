package graph.topo;

import graph.model.Graph;
import graph.scc.SCCResult;
import graph.scc.TarjanSCC;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class KahnTopoSorterTest {

    @Test
    void testTopoOnCondensation() {
        Graph g = new Graph(6, false);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);
        g.addEdge(3, 4, 1);
        g.addEdge(4, 5, 1);
        g.addEdge(1, 3, 1);
        TarjanSCC tarjan = new TarjanSCC(g);
        SCCResult scc = tarjan.run();
        CondensationGraph cg = new CondensationGraph(g, scc);
        List<Integer> compOrder = KahnTopoSorter.topoSort(cg.getDag());
        assertEquals(scc.getComponentCount(), compOrder.size());
        List<Integer> expanded = KahnTopoSorter.expandOrder(scc, compOrder);
        Set<Integer> seen = new HashSet<>(expanded);
        for (int i = 0; i < g.getN(); i++) assertTrue(seen.contains(i));
    }
}
