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



    /**
     * Insere uma nova aresta entre dois vertices
     *
     * @param outbound vertice outbound
     * @param inbound vertice inbound
     * @param edgeElement o elemento da nova aresta
     * @return aresta criada entre os dois vertices
     * @throws InvalidVertexException se um ou ambos os vertices forem invalidos
     * @throws InvalidEdgeException se ja existir uma aresta igual aquela que
     * queremos inserir
     */
    @Override
    public Edge<E, V> insertEdge(Vertex<V> outbound, Vertex<V> inbound, E edgeElement) throws InvalidVertexException, InvalidEdgeException {
        MyVertex v1 = checkVertice(inbound);
        MyVertex v2 = checkVertice(outbound);

        MyEdge aresta = new MyEdge(edgeElement, inbound, outbound);

        v1.addEdge(edgeElement, aresta);
        v2.addEdge(edgeElement, aresta);

        // this.vertices.replace(v1.element(), v1);
        // this.vertices.replace(v2.element(), v2);
        return aresta;
    }

    /**
     * Insere uma nova aresta entre dois vertices
     *
     * @param outboundElement elemento guardado no vertice outbound
     * @param inboundElement elemento guardado no vertice inbound
     * @param edgeElement elemento a guardar na nova aresta
     * @return aresta criada entre os dois vertices
     * @throws InvalidVertexException se um ou ambos os vertices nao existirem
     * no grafo
     * @throws InvalidEdgeException se ja existir uma aresta igual aquele que
     * queremos inserir
     */
    @Override
    public Edge<E, V> insertEdge(V outboundElement, V inboundElement, E edgeElement) throws InvalidVertexException, InvalidEdgeException {
        Vertex<V> a = vertices.get(outboundElement);
        Vertex<V> b = vertices.get(inboundElement);

        return insertEdge(a, b, edgeElement);
    }

    /**
     * Retorna o numero de vertices existentes
     *
     * @return numero de vertices existentes
     */
    @Override
    public int numVertices() {
        return vertices.size();
    }

    /**
     * Retorna o numero de arestas existentes
     *
     * @return numero de arestas existentes
     */
    @Override
    public int numEdges() {

        return edges().size();
    }

    /**
     * Retorna uma colecao dos vertices existentes
     *
     * @return lista de vertices existentes
     */
    @Override
    public Collection<Vertex<V>> vertices() {
        return vertices.values();
    }

    /**
     * Retorna uma colecao das arestas existentes
     *
     * @return lista de arestas existentes
     */
    @Override
    public Collection<Edge<E, V>> edges() {
        ArrayList<Edge<E, V>> list = new ArrayList<>();

        for (Vertex<V> v : this.vertices.values()) {
            list.addAll(this.incidentEdges(v));

        }

        return list;
    }

    /**
     * Retorna o vertice oposto a outro vertice, sendo que estes estao ligados
     * por uma aresta
     *
     * @param v vertice que conhecemos
     * @param e aresta que liga o vertice que conhecemos a outro
     * @return o vertice oposto ao vertice que conhecemos
     * @throws InvalidVertexException se o vertice que conhecemos for invalido
     * @throws InvalidEdgeException se a aresta nao existir
     */
    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) throws InvalidVertexException, InvalidEdgeException {
        checkVertice(v);
        MyEdge edge = checkEdge(e);
        if (edge.inbound == v) {
            return edge.outbound;
        } else if (edge.outbound == v) {
            return edge.inbound;
        } else {
            throw new InvalidVertexException("INVALID VERTEX");
        }

    }

