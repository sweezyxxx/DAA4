package graph.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {
    private final int n;
    private final List<Edge>[] adj;
    private final Long[] nodeDurations;

    @SuppressWarnings("unchecked")
    public Graph(int n, boolean useNodeDurations) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        this.n = n;
        this.adj = (List<Edge>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        this.nodeDurations = useNodeDurations ? new Long[n] : null;
    }

    public int getN() { return n; }

    public void addEdge(int u, int v, long w) {
        checkVertex(u); checkVertex(v);
        adj[u].add(new Edge(u, v, w));
    }

    public List<Edge> neighbors(int u) {
        checkVertex(u);
        return Collections.unmodifiableList(adj[u]);
    }

    public void setNodeDuration(int v, long dur) {
        if (nodeDurations == null) {
            throw new IllegalStateException("Graph not configured for node durations.");
        }
        checkVertex(v);
        nodeDurations[v] = dur;
    }

    public Long getNodeDuration(int v) {
        if (nodeDurations == null) return null;
        checkVertex(v);
        return nodeDurations[v];
    }

    private void checkVertex(int v) {
        if (v < 0 || v >= n) throw new IndexOutOfBoundsException("Vertex out of range: " + v);
    }

    public List<Edge>[] getAdjacency() {
        return adj;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph(n=").append(n).append(", edges=\n");
        for (int i = 0; i < n; i++) {
            for (Edge e : adj[i]) sb.append("  ").append(e).append("\n");
        }
        sb.append(")");
        return sb.toString();
    }
}
