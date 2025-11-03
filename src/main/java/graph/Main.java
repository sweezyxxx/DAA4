package graph.util;

import graph.dagsp.DAGLongestPath;
import graph.dagsp.DAGShortestPaths;
import graph.io.JsonParser;
import graph.metrics.SimpleMetrics;
import graph.model.Graph;
import graph.scc.SCCResult;
import graph.scc.TarjanSCC;
import graph.topo.CondensationGraph;
import graph.topo.KahnTopoSorter;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("generate")) {
            try {
                DatasetGenerator.generateAll();
                System.out.println("Datasets generated under /data");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        String path = args.length > 0 ? args[0] : "data/tasks.json";
        File f = new File(path);
        JsonParser parser = new JsonParser();
        try {
            JsonParser.ParsedGraph pg = parser.parseFile(f);
            Graph g = pg.graph;
            System.out.println("Parsed graph:");
            System.out.println(g);
            TarjanSCC tarjan = new TarjanSCC(g);
            SimpleMetrics m1 = new SimpleMetrics();
            m1.startTimer();
            SCCResult scc = tarjan.run();
            m1.stopTimer();
            System.out.println("SCC components:\n" + scc);
            System.out.println("SCC metrics:\n" + m1);
            CondensationGraph cg = new CondensationGraph(g, scc);
            SimpleMetrics m2 = new SimpleMetrics();
            m2.startTimer();
            List<Integer> compOrder = KahnTopoSorter.topoSort(cg.getDag());
            m2.stopTimer();
            System.out.println("Topological order of components:\n" + compOrder);
            System.out.println("Topo metrics:\n" + m2);
            if (pg.source != null) {
                DAGShortestPaths sp = new DAGShortestPaths(cg.getDag(), pg.source);
                DAGLongestPath lp = new DAGLongestPath(cg.getDag(), pg.source);
                System.out.println("Shortest distances from " + pg.source + ":");
                for (int i = 0; i < cg.getDag().getN(); i++) {
                    System.out.println(i + ": " + sp.distanceTo(i));
                }
                System.out.println("Critical path length: " + max(lp.getDistances()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long max(long[] arr) {
        long m = Long.MIN_VALUE;
        for (long v : arr) if (v > m) m = v;
        return m;
    }
}
