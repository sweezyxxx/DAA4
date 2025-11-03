package graph.dagsp;

import graph.model.Edge;
import graph.model.Graph;
import graph.topo.KahnTopoSorter;

import java.util.ArrayList;
import java.util.List;

public class DAGShortestPaths {
    private final Graph dag;
    private final long[] dist;
    private final int[] prev;
    private final int source;
    private static final long INF = Long.MAX_VALUE / 4;

    public DAGShortestPaths(Graph dag, int source) {
        this.dag = dag;
        this.source = source;
        int n = dag.getN();
        this.dist = new long[n];
        this.prev = new int[n];
        for (int i = 0; i < n; i++) {
            dist[i] = INF;
            prev[i] = -1;
        }
        dist[source] = 0;
        compute();
    }

    private void compute() {
        List<Integer> topo = KahnTopoSorter.topoSort(dag);
        for (int u : topo) {
            if (dist[u] == INF) continue;
            for (Edge e : dag.neighbors(u)) {
                int v = e.getV();
                long w = e.getW();
                if (dist[v] > dist[u] + w) {
                    dist[v] = dist[u] + w;
                    prev[v] = u;
                }
            }
        }
    }

    public long distanceTo(int v) {
        long d = dist[v];
        return d >= INF ? Long.MAX_VALUE : d;
    }

    public boolean reachable(int v) {
        return dist[v] < INF;
    }

    public List<Integer> reconstructPath(int target) {
        List<Integer> path = new ArrayList<>();
        if (!reachable(target)) return path;
        int cur = target;
        while (cur != -1) {
            path.add(0, cur);
            if (cur == source) break;
            cur = prev[cur];
        }
        return path;
    }

    public long[] getDistances() {
        return dist.clone();
    }

    public int[] getPredecessors() {
        return prev.clone();
    }
}
