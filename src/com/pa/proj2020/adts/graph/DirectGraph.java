package com.pa.proj2020.adts.graph;

import com.pa.proj2020.adts.graph.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class DirectGraph<V, E> implements Digraph<V, E> {
    private HashMap<V, Vertex<V>> vertices;

    /**
     * Cria um grafo
     */
    public DirectGraph() {
        vertices = new HashMap();
    }


    /**
     * Cria um grafo com uma lista de vertices fornecida
     *
     * @param list
     */
    public DirectGraph(HashMap<V, Vertex<V>> list) {
        vertices = new HashMap<>();
        vertices.putAll(list);
    }

    /**
     * Retorna os vertices existentes no map
     *
     * @return vertices existentes no map
     */
    public HashMap<V, Vertex<V>> getVertices() {
        return vertices;
    }

    /**
     * Insere vertices no map
     * @param vertices
     */
    public void setVertices(HashMap<V, Vertex<V>> vertices) {
        this.vertices.putAll(vertices);
    }

    /**
     * Esvazia o map
     */
    public void clear() {
        vertices.clear();

    }

    /**
     * Retorna a colecao de arestas incidentes a um determinado vertice
     *
     * @param inbound representa o vertice que contem as arestas incidentes
     * @return colecao de arestas incidentes
     * @throws InvalidVertexException
     */
    @Override
    public Collection<Edge<E, V>> incidentEdges(Vertex<V> inbound) throws InvalidVertexException {
        MyVertex v = checkVertice(inbound);
        HashSet<Edge<E, V>> list = new HashSet<>();

        for (Edge<E, V> edge : v.getEdges().values()) {
            if (edge.vertices()[1] == inbound) {
                list.add(edge);
            }
        }
        return list;
    }

    /**
     * Retorna a colecao de arestas que terminam num determinado vertice
     *
     * @param outbound representa o vertice em que terminam as arestas
     * @return colecao de arestas com o vertice outbound
     * @throws InvalidVertexException
     */
    @Override
    public Collection<Edge<E, V>> outboundEdges(Vertex<V> outbound) throws InvalidVertexException {
        MyVertex v = checkVertice(outbound);
        HashSet<Edge<E, V>> list = new HashSet<>();
        for (Edge<E, V> edge : v.getEdges().values()) {
            if (edge.vertices()[0] == outbound) {
                list.add(edge);
            }
        }
        return list;

    }

    /**
     * Verifica se dois vertices sao adjacentes (se estao ligados por uma
     * aresta)
     *
     * @param outbound vertice outbound
     * @param inbound vertice inbound
     * @return true se forem adjacentes ou false se nao forem
     * @throws InvalidVertexException se um ou ambos os vertices forem invalidos
     */
    @Override
    public boolean areAdjacent(Vertex<V> outbound, Vertex<V> inbound) throws InvalidVertexException {
        MyVertex v1 = checkVertice(outbound);
        MyVertex v2 = checkVertice(inbound);

        for (Edge<E, V> aresta : v1.getEdges().values()) {
            if (aresta.vertices()[0] == outbound && aresta.vertices()[1] == inbound
                    || aresta.vertices()[0] == inbound && aresta.vertices()[1] == outbound) {
                return true;
            }
        }

        for (Edge<E, V> aresta : v2.getEdges().values()) {
            if (aresta.vertices()[0] == outbound && aresta.vertices()[1] == inbound
                    || aresta.vertices()[0] == inbound && aresta.vertices()[1] == outbound) {
                return true;
            }
        }

        return false;

    }
