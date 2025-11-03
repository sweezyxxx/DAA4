package graph.metrics;

public interface Metrics {
    void startTimer();
    void stopTimer();
    void inc(String key);
    long getCount(String key);
    long getTimeNanos();
}
