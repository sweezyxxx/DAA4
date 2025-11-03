package graph.topo;

import graph.model.Edge;
import graph.model.Graph;
import graph.scc.SCCResult;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class KahnTopoSorter {

    public static List<Integer> topoSort(Graph dag) {
        int n = dag.getN();
        int[] indeg = new int[n];
        for (int i = 0; i < n; i++) {
            for (Edge e : dag.neighbors(i)) {
                indeg[e.getV()]++;
            }
        }
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) q.add(i);
        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int v = q.remove();
            order.add(v);
            for (Edge e : dag.neighbors(v)) {
                indeg[e.getV()]--;
                if (indeg[e.getV()] == 0) q.add(e.getV());
            }
        }
        if (order.size() != n) throw new IllegalStateException("Cycle detected in condensation DAG");
        return order;
    }

    public static List<Integer> expandOrder(SCCResult scc, List<Integer> compOrder) {
        List<Integer> out = new ArrayList<>();
        for (int cid : compOrder) {
            List<Integer> comp = scc.getComponents().get(cid);
            for (int v : comp) out.add(v);
        }
        return out;
    }
}
