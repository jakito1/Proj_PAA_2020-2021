package com.pa.proj2020.adts.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class DirectGraph<V, E> implements Digraph<V, E> {
    private final HashMap<V, Vertex<V>> vertices;

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
     *
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
     * @param inbound  vertice inbound
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
     * @param outbound    vertice outbound
     * @param inbound     vertice inbound
     * @param edgeElement o elemento da nova aresta
     * @return aresta criada entre os dois vertices
     * @throws InvalidVertexException se um ou ambos os vertices forem invalidos
     * @throws InvalidEdgeException   se ja existir uma aresta igual aquela que
     *                                queremos inserir
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
     * @param inboundElement  elemento guardado no vertice inbound
     * @param edgeElement     elemento a guardar na nova aresta
     * @return aresta criada entre os dois vertices
     * @throws InvalidVertexException se um ou ambos os vertices nao existirem
     *                                no grafo
     * @throws InvalidEdgeException   se ja existir uma aresta igual aquele que
     *                                queremos inserir
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
     * @throws InvalidEdgeException   se a aresta nao existir
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

    /**
     * Adiciona a lista de vertices um determinado vertice com um elemento
     *
     * @param vElement elemento do vertice que queremos criar
     * @return o vertice criado e adicionado a lista de vertices
     * @throws InvalidVertexException se o vertice ja existir
     */
    @Override
    public Vertex<V> insertVertex(V vElement) throws InvalidVertexException {
        if (vertices.containsKey(vElement)) {
            //return vertices.get(vElement);
            throw new InvalidVertexException("EXISTING VERTEX");
        }
        MyVertex vertice = new MyVertex(vElement);
        vertices.put(vElement, vertice);
        return vertice;
    }

    /**
     * Remove um vertice da lista de vertices existentes
     *
     * @param v vertice que queremos remover
     * @return o elemento do vertice removido
     * @throws InvalidVertexException se o vertice for invalido ou nao existir
     */
    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        MyVertex vertice = checkVertice(v);
        //HashSet<Edge<E, V>> list = new HashSet<>();
        HashSet<Vertex<V>> listVerticesOpostos = new HashSet<>();

        for (Edge<E, V> edge : vertice.getEdges().values()) {
            listVerticesOpostos.add(this.opposite(v, edge));
        }

        for (Vertex<V> vertex : listVerticesOpostos) {
            MyVertex v1 = checkVertice(vertex);

            for (Edge<E, V> e : vertice.getEdges().values()) {
                v1.removeEdge(e);

            }
        }

        vertices.remove(v.element());
        return v.element();
    }

    /**
     * Remove uma aresta da lista de arestas existentes
     *
     * @param e aresta que queremos remover
     * @return o elemento da aresta removida
     * @throws InvalidEdgeException se a aresta nao existir
     */
    @Override
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException {
        checkEdge(e);

        for (Vertex<V> vertex : vertices.values()) {
            MyVertex v = (MyVertex) vertex;
            v.removeEdge(e);
        }

        return e.element();
    }

    /**
     * Substitui o elemento associado a um determinado vertice
     *
     * @param v          vertice que queremos substituir o elemento
     * @param newElement novo elemento que queremos atribuir a um vertice
     * @return o novo elemento associado ao vertice
     * @throws InvalidVertexException se o vertice for invalido ou nao existir
     */
    @Override
    public V replace(Vertex<V> v, V newElement) throws InvalidVertexException {
        Vertex<V> vRemoved = vertices.remove(v.element());
        V elem = vRemoved.element();
        ((MyVertex) vRemoved).elemento = newElement;
        vertices.put(newElement, vRemoved);
        return elem;

    }

    /**
     * Substitui o elemento associado a uma determinada aresta
     *
     * @param e          aresta que queremos substituir o elemento
     * @param newElement novo elemento que queremos atribuir a uma aresta
     * @return o novo elemento associado a aresta
     * @throws InvalidEdgeException se a aresta nao existir ou for invalida
     */
    @Override
    public E replace(Edge<E, V> e, E newElement) throws InvalidEdgeException {
        MyVertex v1 = checkVertice(e.vertices()[0]);
        MyVertex v2 = checkVertice(e.vertices()[1]);

        E elem = v1.replaceEdge(newElement, e);
        v2.replaceEdge(newElement, e);

        replace(v1, v1.element());
        replace(v2, v2.element());

        return elem;

    }

    /**
     * Verifica se um vertice é valido
     *
     * @param vertice vertice que pretendemos validar
     * @return o vertice validado ou caso este seja null ou nao exista
     */
    private MyVertex checkVertice(Vertex<V> vertice) {
        if (vertice == null) {
            throw new InvalidVertexException("NULL VERTEX");
        }
        if (!this.vertices.containsValue(vertice)) {
            throw new InvalidVertexException("VERTEX DOESNT EXIST");
        }
        return (MyVertex) vertice;
    }

    /**
     * Verifica se uma aresta é valida
     *
     * @param edge aresta que pretendemos validar
     * @return a aresta validada ou caso seja null ou nao exista
     */
    private MyEdge checkEdge(Edge<E, V> edge) {
        if (edge == null) {
            throw new InvalidEdgeException("NULL EDGE");
        }

        MyVertex v1 = checkVertice(edge.vertices()[0]);
        MyVertex v2 = checkVertice(edge.vertices()[1]);

        if (!v1.getEdges().containsValue(edge) && !v2.getEdges().containsValue(edge)) {
            throw new InvalidEdgeException("EDGE DOESNT EXIST");
        }

        return (MyEdge) edge;
    }

    /**
     * Verifica se a lista de vertices tem um determinado vertice
     *
     * @param vElement vertice que pretendemos procurar na lista de vertices
     * @return true se o vertice estiver na lista ou false se nao estiver
     */
    public boolean containVertice(V vElement) {
        for (V user : this.vertices.keySet()) {
            if (((User) user).getID() == ((User) vElement).getID()) return true;
        }
        return false;

        //return this.vertices.containsKey(vElement);
    }


    /**
     * Classe auxiliar com as informacoes relativas a uma aresta, nomeadamente o
     * vertice inbound, o vertice outbound e o elemento contido na aresta
     */
    private class MyEdge implements Edge<E, V> {

        private final Vertex<V> inbound;
        private final Vertex<V> outbound;
        private E elemento;

        public MyEdge(E elemento, Vertex<V> vertice1, Vertex<V> vertice2) {
            this.elemento = elemento;
            this.inbound = vertice1;
            this.outbound = vertice2;
        }

        @Override
        public E element() {
            if (elemento == null) {
                throw new InvalidEdgeException("NULL EDGE");
            }
            return elemento;
        }

        @Override
        public Vertex<V>[] vertices() {
            Vertex[] vertices = new Vertex[2];
            vertices[1] = inbound;
            vertices[0] = outbound;
            return vertices;
        }

        @Override
        public String toString() {
            return elemento.toString();
        }

    }

    /**
     * Classe auxiliar com as informacoes relativas a um vertice, nomeadamente,
     * o elemento que cada vertice guarda
     */
    private class MyVertex implements Vertex<V> {

        private final HashMap<E, Edge<E, V>> edges;
        private V elemento;

        public MyVertex(V elemento) {
            this.elemento = elemento;
            this.edges = new HashMap<E, Edge<E, V>>();
        }

        public HashMap<E, Edge<E, V>> getEdges() {

            return edges;
        }

        public Edge<E, V> addEdge(E element, Edge<E, V> edge) {
            return edges.put(element, edge);
        }

        public void removeEdge(Edge<E, V> e) {

            edges.remove(e.element());

        }

        public E replaceEdge(E newElement, Edge<E, V> e) {

            Edge<E, V> eRemoved = edges.remove(e.element());
            E elem = null;
            if (eRemoved != null) {
                elem = eRemoved.element();
                ((MyEdge) eRemoved).elemento = newElement;
                edges.put(newElement, eRemoved);
            }

            return elem;

        }

        @Override
        public V element() {
            if (elemento == null) {
                throw new InvalidVertexException("NULL VERTEX");
            }
            return elemento;
        }

        @Override
        public String toString() {
            return elemento.toString();
        }

    }

}