package graph.metrics;

import java.util.HashMap;
import java.util.Map;

public class SimpleMetrics implements Metrics {
    private long startTime;
    private long endTime;
    private final Map<String, Long> counters = new HashMap<>();

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public void inc(String key) {
        counters.put(key, counters.getOrDefault(key, 0L) + 1);
    }

    public long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }

    public long getTimeNanos() {
        return endTime - startTime;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Time(ns): ").append(getTimeNanos()).append("\n");
        for (var e : counters.entrySet()) {
            sb.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }
}
