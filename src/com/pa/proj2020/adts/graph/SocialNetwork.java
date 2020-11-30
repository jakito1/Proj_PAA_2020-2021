package com.pa.proj2020.adts.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class SocialNetwork {

    private DirectGraph<User, Relationship> graph;
    private HashMap<Integer, User> users;
    private HashMap<String, ArrayList<String>> relationships;

    /**
     * Cria um objeto SocialNetwork
     * Inicializa os atributos
     */
    public SocialNetwork(){
        graph = new DirectGraph<>();
        users = new HashMap<>();
        relationships = new HashMap<>();
    }

    /**
     * Adiciona nas coleções a informação correspondente encontrada nos ficheiros user_names.csv e relationships.csv
     */
    public void initializeData(){
        ReadData read = new ReadData();
        HashMap<String, ArrayList<String>> temp = read.readData("user_names.csv");

        for(String id : temp.keySet()){
            users.put(Integer.parseInt(id), new User(temp.get(id).get(0), Integer.parseInt(id)));
        }

        relationships = read.readData("relationships.csv");
    }

    /**
     * Insere os vertices e arestas no grafo e retorna-o
     *
     * @return o grafo
     */
    public DirectGraph<User, Relationship> constructModel(){
        for(User user : users.values()){
            graph.insertVertex(user);
        }

        for(String id : relationships.keySet()){
            for(String id2 : relationships.get(id)){
                graph.insertEdge(users.get(Integer.parseInt(id)), users.get(Integer.parseInt(id2)), new RelationshipSimple());
            }
        }

        return this.graph;
    }

    public DirectGraph<User, Relationship> getGraph(){
        return this.graph;
    }

    public HashMap<Integer, User> getUsers(){
        return this.users;
    }

    public HashMap<String, ArrayList<String>> getRelationShips(){
        return this.relationships;
    }

}
