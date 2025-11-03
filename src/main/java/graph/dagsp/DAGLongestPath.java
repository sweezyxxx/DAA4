package graph.dagsp;

import graph.model.Edge;
import graph.model.Graph;
import graph.topo.KahnTopoSorter;

import java.util.ArrayList;
import java.util.List;

public class DAGLongestPath {
    private final Graph dag;
    private final long[] dist;
    private final int[] prev;
    private final int source;
    private static final long NEG_INF = Long.MIN_VALUE / 4;

    public DAGLongestPath(Graph dag, int source) {
        this.dag = dag;
        this.source = source;
        int n = dag.getN();
        this.dist = new long[n];
        this.prev = new int[n];
        for (int i = 0; i < n; i++) {
            dist[i] = NEG_INF;
            prev[i] = -1;
        }
        dist[source] = 0;
        compute();
    }

    private void compute() {
        List<Integer> topo = KahnTopoSorter.topoSort(dag);
        for (int u : topo) {
            if (dist[u] == NEG_INF) continue;
            for (Edge e : dag.neighbors(u)) {
                int v = e.getV();
                long w = e.getW();
                if (dist[v] < dist[u] + w) {
                    dist[v] = dist[u] + w;
                    prev[v] = u;
                }
            }
        }
    }

    public long longestDistanceTo(int v) {
        long d = dist[v];
        return d <= NEG_INF ? Long.MIN_VALUE : d;
    }

    public boolean reachable(int v) {
        return dist[v] > NEG_INF;
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
