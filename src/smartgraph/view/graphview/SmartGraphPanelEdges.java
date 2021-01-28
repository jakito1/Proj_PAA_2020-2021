package smartgraph.view.graphview;

import com.pa.proj2020.adts.graph.Edge;

import java.util.HashMap;
import java.util.Map;

public class SmartGraphPanelEdges<V, E> {
    private final Map<Edge<E, V>, SmartGraphEdgeBase> edgeNodes;

    public SmartGraphPanelEdges() {
        edgeNodes = new HashMap<>();
    }

    public Map<Edge<E, V>, SmartGraphEdgeBase> getEdgeNodes() {
        return edgeNodes;
    }

    /**
     * Returns the associated stylable element with a graph edge.
     *
     * @param edge underlying graph edge
     * @return stylable element
     */
    public SmartStylableNode getStylableEdge(Edge<E, V> edge) {
        return edgeNodes.get(edge);
    }

    /**
     * Returns the associated stylable element with a graph edge.
     *
     * @param edgeElement underlying graph edge's element
     * @return stylable element
     */
    public SmartStylableNode getStylableEdge(E edgeElement) {
        for (Edge<E, V> e : edgeNodes.keySet()) {
            if (e.element().equals(edgeElement)) {
                return edgeNodes.get(e);
            }
        }
        return null;
    }
}