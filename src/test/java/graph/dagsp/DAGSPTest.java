package graph.dagsp;

import graph.model.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DAGSPTest {

    @Test
    void testShortestPathsSimpleDAG() {
        Graph g = new Graph(6, false);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 3, 4);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 4, 2);
        g.addEdge(4, 5, 1);

        DAGShortestPaths sp = new DAGShortestPaths(g, 0);
        assertTrue(sp.reachable(5));
        assertEquals(10, sp.distanceTo(5));
        List<Integer> path = sp.reconstructPath(5);
        assertEquals(List.of(0,2,3,4,5), path);
    }

    @Test
    void testLongestPathSimpleDAG() {
        Graph g = new Graph(6, false);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 3, 4);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 4, 2);
        g.addEdge(4, 5, 1);

        DAGLongestPath lp = new DAGLongestPath(g, 0);
        assertTrue(lp.reachable(5));
        assertEquals(10, lp.longestDistanceTo(5));
        List<Integer> path = lp.reconstructPath(5);
        assertEquals(List.of(0,1,3,4,5), path);
    }
}
