package graph.topo;

import graph.model.Edge;
import graph.model.Graph;
import graph.scc.SCCResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CondensationGraph {
    private final Graph dag;
    private final List<List<Integer>> components;

    public CondensationGraph(Graph original, SCCResult scc) {
        int m = scc.getComponentCount();
        this.dag = new Graph(m, false);
        this.components = scc.getComponents();
        Set<Long> added = new HashSet<>();
        int n = original.getN();
        for (int u = 0; u < n; u++) {
            int cu = scc.getComponentId(u);
            for (Edge e : original.neighbors(u)) {
                int v = e.getV();
                int cv = scc.getComponentId(v);
                if (cu != cv) {
                    long key = (((long) cu) << 32) | (cv & 0xffffffffL);
                    if (!added.contains(key)) {
                        added.add(key);
                        dag.addEdge(cu, cv, 1);
                    }
                }
            }
        }
    }

    public Graph getDag() {
        return dag;
    }

    public List<List<Integer>> getComponents() {
        return components;
    }
}
