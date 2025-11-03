package graph.io;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import graph.model.Graph;

import java.io.File;
import java.io.IOException;

public class JsonParser {
    private final ObjectMapper mapper = new ObjectMapper();

    public static class ParsedGraph {
        public final Graph graph;
        public final Integer source;
        public final String weightModel;

        public ParsedGraph(Graph graph, Integer source, String weightModel) {
            this.graph = graph;
            this.source = source;
            this.weightModel = weightModel;
        }
    }


    public ParsedGraph parseFile(File file) throws IOException {
        JsonNode root = mapper.readTree(file);

        boolean directed = root.path("directed").asBoolean(true);
        int n = root.path("n").asInt();
        String weightModel = root.path("weight_model").asText("edge"); // default edge
        Integer source = root.has("source") && root.get("source").isInt() ? root.get("source").asInt() : null;

        boolean useNodeDurations = "node".equalsIgnoreCase(weightModel);
        Graph g = new Graph(n, useNodeDurations);

        JsonNode edges = root.path("edges");
        if (edges.isArray()) {
            for (JsonNode e : edges) {
                int u = e.path("u").asInt();
                int v = e.path("v").asInt();
                long w = e.path("w").asLong(1);
                g.addEdge(u, v, w);
                if (!directed) {
                    g.addEdge(v, u, w);
                }
            }
        }

        if (useNodeDurations && root.has("nodes")) {
            JsonNode nodes = root.path("nodes");
            if (nodes.isArray()) {
                for (JsonNode nd : nodes) {
                    int id = nd.path("id").asInt();
                    long dur = nd.path("duration").asLong();
                    g.setNodeDuration(id, dur);
                }
            }
        }

        return new ParsedGraph(g, source, weightModel);
    }
}
