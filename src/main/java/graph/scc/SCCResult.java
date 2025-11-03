package graph.scc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SCCResult {
    private final List<List<Integer>> components;
    private final int[] componentId;

    public SCCResult(List<List<Integer>> components, int[] componentId) {
        this.components = components;
        this.componentId = componentId;
    }

    public List<List<Integer>> getComponents() {
        return Collections.unmodifiableList(components);
    }

    public int getComponentCount() {
        return components.size();
    }

    public int getComponentId(int vertex) {
        return componentId[vertex];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SCCResult{\n");
        for (int i = 0; i < components.size(); i++) {
            sb.append("  C").append(i).append(": ").append(components.get(i)).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    public List<Integer> getComponentSizes() {
        List<Integer> sizes = new ArrayList<>();
        for (List<Integer> comp : components) {
            sizes.add(comp.size());
        }
        return sizes;
    }
}
