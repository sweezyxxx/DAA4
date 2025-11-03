package graph.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import graph.model.Graph;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class DatasetGenerator {

    public static void generateAll() throws IOException {
        generateCategory("small", 3, 6, 10);
        generateCategory("medium", 3, 10, 20);
        generateCategory("large", 3, 20, 50);
    }

    private static void generateCategory(String name, int count, int minN, int maxN) throws IOException {
        File dir = new File("data/" + name);
        if (!dir.exists()) dir.mkdirs();
        for (int i = 1; i <= count; i++) {
            int n = minN + new Random().nextInt(maxN - minN + 1);
            double density = 0.2 + new Random().nextDouble() * 0.5;
            Graph g = randomGraph(n, density);
            ObjectMapper mapper = new ObjectMapper();
            File out = new File(dir, "graph_" + i + ".json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(out, GraphToJson.convert(g, true));
        }
    }

    private static Graph randomGraph(int n, double density) {
        Graph g = new Graph(n, false);
        Random rand = new Random();
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (u != v && rand.nextDouble() < density) {
                    int w = 1 + rand.nextInt(9);
                    g.addEdge(u, v, w);
                }
            }
        }
        return g;
    }
}
