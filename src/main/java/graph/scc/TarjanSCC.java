package graph.scc;

import graph.model.Graph;
import graph.model.Edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TarjanSCC {

    private final Graph graph;
    private int time;
    private int[] ids;
    private int[] low;
    private boolean[] onStack;
    private Stack<Integer> stack;
    private List<List<Integer>> components;

    public TarjanSCC(Graph graph) {
        this.graph = graph;
    }

    public SCCResult run() {
        int n = graph.getN();
        time = 0;
        ids = new int[n];
        low = new int[n];
        onStack = new boolean[n];
        stack = new Stack<>();
        components = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            ids[i] = -1;
            low[i] = 0;
        }

        for (int i = 0; i < n; i++) {
            if (ids[i] == -1) dfs(i);
        }

        int[] compId = new int[n];
        for (int i = 0; i < components.size(); i++) {
            for (int v : components.get(i)) {
                compId[v] = i;
            }
        }

        return new SCCResult(components, compId);
    }

    private void dfs(int at) {
        ids[at] = low[at] = time++;
        stack.push(at);
        onStack[at] = true;

        for (Edge e : graph.neighbors(at)) {
            int to = e.getV();
            if (ids[to] == -1) {
                dfs(to);
                low[at] = Math.min(low[at], low[to]);
            } else if (onStack[to]) {
                low[at] = Math.min(low[at], ids[to]);
            }
        }

        if (ids[at] == low[at]) {
            List<Integer> comp = new ArrayList<>();
            while (true) {
                int node = stack.pop();
                onStack[node] = false;
                comp.add(node);
                if (node == at) break;
            }
            components.add(comp);
        }
    }
}
