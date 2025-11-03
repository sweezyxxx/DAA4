package graph.util;

import graph.io.JsonParser;
import graph.model.Graph;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String path = args.length > 0 ? args[0] : "data/tasks.json";
        File f = new File(path);
        JsonParser parser = new JsonParser();
        try {
            JsonParser.ParsedGraph pg = parser.parseFile(f);
            Graph g = pg.graph;
            System.out.println("Parsed graph:");
            System.out.println(g);
            System.out.println("weight_model: " + pg.weightModel);
            System.out.println("source: " + pg.source);
            graph.scc.TarjanSCC tarjan = new graph.scc.TarjanSCC(g);
            graph.scc.SCCResult scc = tarjan.run();
            System.out.println("\nSCC components:");
            System.out.println(scc);
        } catch (Exception e) {
            System.err.println("Failed to parse JSON file: " + f.getAbsolutePath());
            e.printStackTrace();
            System.exit(2);
        }
    }
}
