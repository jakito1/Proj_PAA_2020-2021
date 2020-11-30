package com.pa.proj2020.adts.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DirectGraphTest {

    private final DirectGraph<String, Integer> graph;
    private final ArrayList<String> vertices;
    private final Vertex<String> v1;
    private final Vertex<String> v2;
    private final Vertex<String> v3;
    private final Vertex<String> v4;
    private final Vertex<String> v5;
    private final Edge<Integer, String> e1;
    private final Edge<Integer, String> e2;
    private final Edge<Integer, String> e3;
    private final Edge<Integer, String> e4;
    private final Edge<Integer, String> e5;
    private final Edge<Integer, String> e6;
    private final Edge<Integer, String> e7;

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

    /**
     * Test of insertEdge method, of class DirectGraph.
     */
    @Test
    public void testInsertEdge_3args_1() {
        Vertex<String> inbound = null;
        Vertex<String> outbound = null;

        for (Edge<Integer, String> edge : graph.edges()) {
            if (edge.element() == 1 && edge.vertices()[0].element().equalsIgnoreCase("Vertice1")
                    && edge.vertices()[1].element().equalsIgnoreCase("Vertice2")) {
                outbound = edge.vertices()[0];
                inbound = edge.vertices()[1];
            }

        }

        graph.insertEdge(outbound, inbound, 10);
    }

    /**
     * Test of insertEdge method, of class DirectGraph.
     */
    @Test
    public void testInsertEdge_3args_2() {

        DirectGraph<String, Integer> digraph = graph;
        Vertex<String> inbound = null;
        Vertex<String> outbound = null;

        for (Vertex<String> v : digraph.vertices()) {
            if (v.element().equalsIgnoreCase("Vertice1")) {
                inbound = v;
            }
            if (v.element().equalsIgnoreCase("Vertice2")) {
                outbound = v;
            }
        }

        Edge<Integer, String> expResult = null;
        Edge<Integer, String> result = digraph.insertEdge(outbound, inbound, 25);
        assertNotEquals(expResult, result);

    }

    /**
     * Test of numVertices method, of class DirectGraph.
     */
    @Test
    public void testNumVertices() {
        int expResult = 5;
        int result = graph.numVertices();
        assertEquals(expResult, result);

    }

    /**
     * Test of numEdges method, of class DirectGraph.
     */
    @Test
    public void testNumEdges() {
        int expResult = 7;
        int result = graph.numEdges();
        assertEquals(expResult, result);
    }

    /**
     * Test of opposite method, of class DirectGraph.
     */
    @Test
    public void testOpposite() {
        Vertex expResult = v1;
        Vertex result = graph.opposite(v2, e1);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeVertex method, of class DirectGraph.
     */
    @Test
    public void testRemoveVertex() {
        Object expResult = "Vertice1";
        Object result = graph.removeVertex(v1);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeEdge method, of class DirectGraph.
     */
    @Test
    public void testRemoveEdge() {
        Object expResult = e1.element();
        Object result = graph.removeEdge(e1);
        assertEquals(expResult, result);
    }

    /**
     * Test of replace method, of class DirectGraph.
     */
    @Test
    public void testReplace_Vertex_GenericType() {
        Object expResult = v1.element();
        Object result = graph.replace(v1, "Vertice6");
        assertEquals(expResult, result);
    }

    /**
     * Test of replace method, of class DirectGraph.
     */
    @Test
    public void testReplace_Edge_GenericType() {
        Object expResult = e1.element();
        Object result = graph.replace(e1, 8);
        assertEquals(expResult, result);
    }

    /**
     * Test of containVertice method, of class DirectGraph.
     */
    @Test
    public void testContainVertice() {
        Object vElement = null;
        boolean expResult = true;
        boolean result = graph.containVertice(v1.element());
        assertEquals(expResult, result);
    }


}