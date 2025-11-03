package graph.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import graph.model.Graph;
import graph.model.Edge;

public class GraphToJson {
    public static Map<String, Object> convert(Graph g, boolean directed) {
        Map<String, Object> root = new HashMap<>();
        root.put("directed", directed);
        root.put("n", g.getN());
        root.put("weight_model", "edge");
        List<Map<String, Object>> edges = new ArrayList<>();
        for (int u = 0; u < g.getN(); u++) {
            for (Edge e : g.neighbors(u)) {
                Map<String, Object> obj = new HashMap<>();
                obj.put("u", e.getU());
                obj.put("v", e.getV());
                obj.put("w", e.getW());
                edges.add(obj);
            }
        }
        root.put("edges", edges);
        root.put("source", 0);
        return root;
    }
}
