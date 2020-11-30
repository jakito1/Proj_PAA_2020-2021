package com.pa.proj2020.adts.graph;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

class DirectGraphTest {

    private DirectGraph<String, Integer> graph;
    private ArrayList<String> vertices;
    private Vertex<String> v1, v2, v3, v4, v5;
    private Edge<Integer, String> e1, e2, e3, e4, e5, e6, e7;

    public DirectGraphTest() {
        graph = new DirectGraph<>();
        vertices = new ArrayList<String>();

        v1 = graph.insertVertex("Vertice1");
        v2 = graph.insertVertex("Vertice2");
        v3 = graph.insertVertex("Vertice3");
        v4 = graph.insertVertex("Vertice4");
        v5 = graph.insertVertex("Vertice5");

        vertices.add(v1.element());
        vertices.add(v4.element());
        vertices.add(v5.element());
        vertices.add(v2.element());
        vertices.add(v3.element());

        e1 = graph.insertEdge("Vertice1", "Vertice2", 1);
        e2 = graph.insertEdge("Vertice2", "Vertice1", 2);
        e3 = graph.insertEdge("Vertice1", "Vertice5", 3);
        e4 = graph.insertEdge("Vertice4", "Vertice3", 4);
        e5 = graph.insertEdge("Vertice2", "Vertice5", 5);
        e6 = graph.insertEdge("Vertice3", "Vertice4", 6);
        e7 = graph.insertEdge("Vertice5", "Vertice4", 7);

    }


    /**
     * Test of incidentEdges method, of class DirectGraph.
     */
    @Test
    public void testIncidentEdges() {
        Vertex<String> v1 = null;
        for (Vertex<String> v2 : graph.vertices()) {
            if (v2.element().equalsIgnoreCase("Vertice1")) {
                v1 = v2;
            }
        }

        Edge<Integer, String> edge1 = null;
        for (Edge<Integer, String> edge : graph.edges()) {
            if (edge.vertices()[1].element().equalsIgnoreCase("Vertice5")) {
                edge1 = edge;
            }
        }
        ;
        Collection<Edge<Integer, String>> result = graph.incidentEdges(v1);
        Collection<Edge<Integer, String>> expResult = result;
        expResult.clear();
        expResult.add(edge1);
        assertEquals(expResult, result);

    }

    /**
     * Test of outboundEdges method, of class DirectGraph.
     */
    @Test
    public void testOutboundEdges() {
        Vertex<String> v1 = null;
        for (Vertex<String> v2 : graph.vertices()) {
            if (v2.element().equalsIgnoreCase("Vertice1")) {
                v1 = v2;
            }
        }

        Edge<Integer, String> edge1 = null;
        Edge<Integer, String> edge2 = null;
        for (Edge<Integer, String> edge : graph.edges()) {
            if (edge.vertices()[1].element().equalsIgnoreCase("Vertice5")) {
                edge1 = edge;
            }
            if (edge.vertices()[1].element().equalsIgnoreCase("Vertice2")) {
                edge2 = edge;
            }
        }

        Collection<Edge<Integer, String>> result = graph.incidentEdges(v1);
        Collection<Edge<Integer, String>> expResult = result;
        expResult.clear();
        expResult.add(edge2);
        expResult.add(edge2);

        assertEquals(expResult, result);

    }

    /**
     * Test of areAdjacent method, of class DirectGraph.
     */
    @Test
    public void testAreAdjacent() {
        Vertex<String> inbound = null;
        Vertex<String> outbound = null;

        for (Vertex<String> v : graph.vertices()) {
            if (v.element().equalsIgnoreCase("Vertice1")) {
                outbound = v;
            }
            if (v.element().equalsIgnoreCase("Vertice2")) {
                inbound = v;
            }
        }

        boolean expResult = true;
        boolean result = graph.areAdjacent(outbound, inbound);
        assertEquals(expResult, result);

    }

}