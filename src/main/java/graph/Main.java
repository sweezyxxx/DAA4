package graph.util;

import graph.io.JsonParser;
import graph.model.Graph;
import graph.scc.SCCResult;
import graph.scc.TarjanSCC;
import graph.topo.CondensationGraph;
import graph.topo.KahnTopoSorter;

import java.io.File;
import java.util.List;

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
            TarjanSCC tarjan = new TarjanSCC(g);
            SCCResult scc = tarjan.run();
            System.out.println("\nSCC components:");
            System.out.println(scc);
            CondensationGraph cg = new CondensationGraph(g, scc);
            System.out.println("\nCondensation DAG:");
            System.out.println(cg.getDag());
            List<Integer> compOrder = KahnTopoSorter.topoSort(cg.getDag());
            System.out.println("\nTopological order of components:");
            System.out.println(compOrder);
            List<Integer> taskOrder = KahnTopoSorter.expandOrder(scc, compOrder);
            System.out.println("\nDerived order of original tasks after SCC compression:");
            System.out.println(taskOrder);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
    }
}
