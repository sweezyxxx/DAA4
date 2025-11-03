package graph.model;

public class Node {
    private final int id;
    private final Long duration; // nullable, used if weight_model == "node"

    public Node(int id, Long duration) {
        this.id = id;
        this.duration = duration;
    }

    public Node(int id) {
        this(id, null);
    }

    public int getId() { return id; }
    public Long getDuration() { return duration; }

    @Override
    public String toString() {
        return duration == null ? String.format("Node(%d)", id)
                : String.format("Node(%d, dur=%d)", id, duration);
    }
}
