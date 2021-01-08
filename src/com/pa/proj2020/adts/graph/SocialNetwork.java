package com.pa.proj2020.adts.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SocialNetwork {

    private final DirectGraph<User, Relationship> graph;
    private final HashMap<Integer, User> users;
    private final HashMap<Integer, ArrayList<String>> relationships;
    private final HashMap<Integer, Interest> interests;
    private final Logging log = Logging.getInstance();

    /**
     * Cria um objeto SocialNetwork
     * Inicializa os atributos
     */
    public SocialNetwork() {
        graph = new DirectGraph<>();
        users = new HashMap<>();
        relationships = new HashMap<>();
        interests = new HashMap<>();
    }

    /**
     * Adiciona nas coleções a informação correspondente encontrada nos ficheiros user_names.csv e relationships.csv
     */
    public void initializeData() {
        ReadData read = new ReadData();
        HashMap<String, ArrayList<String>> temp = read.readData("user_names.csv");
        HashMap<String, ArrayList<String>> tempRelationships = read.readData("relationships.csv");
        HashMap<String, ArrayList<String>> tempInterests = read.readData("interest_names.csv");
        HashMap<String, ArrayList<String>> tempIdsOfUsersInterests = read.readData("interests.csv");

        for (String id : temp.keySet()) {
            users.put(Integer.parseInt(id), new User(temp.get(id).get(0), Integer.parseInt(id), Type.ADICIONADO));
        }

        for (String id : tempRelationships.keySet()) {
            relationships.put(Integer.parseInt(id), tempRelationships.get(id));
        }

        for (String id : tempInterests.keySet()) {
            interests.put(Integer.parseInt(id), new Interest(Integer.parseInt(id), tempInterests.get(id).get(0), tempIdsOfUsersInterests.get(id)));
        }

    }

    /**
     * Insere os vertices e arestas no grafo e retorna-o
     *
     * @return o grafo
     */
    public DirectGraph<User, Relationship> constructModelTotal() {
        for (User user : users.values()) {
            graph.insertVertex(user);
        }

        for (Integer id : relationships.keySet()) {
            for (String id2 : relationships.get(id)) {
                graph.insertEdge(users.get(id), users.get(Integer.parseInt(id2)), new RelationshipSimple());
            }
        }

        return this.graph;
    }

    public DirectGraph<User, Relationship> getGraph() {
        return this.graph;
    }

    public HashMap<Integer, User> getUsers() {
        return this.users;
    }

    public HashMap<Integer, ArrayList<String>> getRelationShips() {
        return this.relationships;
    }


    public List<Interest> interestsOfUser(int idUser) {
        if (idUser < 0) {
            return null;
        }
        ArrayList<Interest> list = new ArrayList<>();

        for (Interest interest : this.interests.values()) {
            if (interest.getIdsOfUsers().contains(String.valueOf(idUser))) {
                list.add(interest);
                log.addInterest(idUser, interest.getId());
            }
        }

        return list;

    }


    public void insertEdge(User user1, User user2) {
        if (user1 == null || user2 == null) {
            return;
        }

        List<Interest> tempInterests = new ArrayList<>();
        boolean relatioshipDirect = false;
        Relationship relationship;

        for (Interest interest : this.interests.values()) {
            if (interest.getIdsOfUsers().contains(String.valueOf(user1.getID())) &&
                    interest.getIdsOfUsers().contains(String.valueOf(user2.getID()))) {
                tempInterests.add(interest);
            }
        }

        if (this.relationships.get(user1.getID()).contains(String.valueOf(user2.getID()))) {
            relatioshipDirect = true;
        }

        //HashSet<Edge<Relationship, User>> tempEdges = new HashSet<>(this.graph.edges());
        //tempEdges

        if (!tempInterests.isEmpty() && !relatioshipDirect) {
            relationship = new RelationshipIndirect(tempInterests);
            this.graph.insertEdge(user1, user2, relationship);
            log.addRelationshipIndirect(user1.getID(), user2.getID(), tempInterests.size());
        } else if (tempInterests.isEmpty() && relatioshipDirect) {
            relationship = new RelationshipSimple();
            this.graph.insertEdge(user1, user2, relationship);
            log.addRelationshipDirect(user1.getID(), user2.getID(), 0);
        } else if (!tempInterests.isEmpty() && relatioshipDirect) {
            relationship = new RelationshipShared(tempInterests);
            this.graph.insertEdge(user1, user2, relationship);
            log.addRelationshipDirect(user1.getID(), user2.getID(), tempInterests.size());
        }

    }


    public void constructModelIteractive(int idUser) {

        if (relationships.isEmpty() || users.isEmpty()) {
            this.initializeData();
        } else if (idUser < 0) {
            return;
        }
        User user = this.users.get(idUser);

        if (graph.containVertice(user)) {

            for (Vertex<User> userVertex : this.graph.vertices()) {
                if (userVertex.element().getID() == idUser && userVertex.element().getType().equals(Type.INCLUIDO)) {
                    userVertex.element().setType(Type.ADICIONADO);
                    break;
                }
            }

        } else {
            user.addListInterest(this.interestsOfUser(user.getID()));
            graph.insertVertex(user);
        }

        for (String idRelationship : this.relationships.get(user.getID())) {
            User userRelationship = this.users.get(Integer.parseInt(idRelationship));
            if (!graph.containVertice(userRelationship)) {
                userRelationship.setType(Type.INCLUIDO);
                this.graph.insertVertex(userRelationship);
                userRelationship.addListInterest(this.interestsOfUser(userRelationship.getID()));
            }
        }

        for (Vertex<User> userVertex : this.graph.vertices()) {
            if (userVertex.element().getID() != idUser) {
                this.insertEdge(user, userVertex.element());
            }
        }


//        if(graph.containVertice(this.users.get(idUser))){
//            for(User userInVertices : graph.getVertices().keySet()){
//                if(userInVertices.getID() == idUser){
//                    userInVertices.setType(Type.ADICIONADO); //user adicionado
//                }
//            }
//        } else {
//            graph.insertVertex(this.users.get(idUser));


//            HashSet<User> vertices = new HashSet<>(graph.getVertices().keySet());
//            for(User user : vertices){
//                boolean exists = false;
//                for(Interest interest : interests.values()){
//
//                    if(interest.getIdsOfUsers().contains(String.valueOf(idUser)) &&
//                            interest.getIdsOfUsers().contains(String.valueOf(user.getID())) && user.getID() != idUser &&
//                    user.getType().equals(Type.ADICIONADO)){
//
//
//                        for(Edge<Relationship, User> edge : graph.incidentEdges(graph.getVertices().get(user))){
//                            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                            System.out.println(user);
//                            System.out.println(edge.vertices()[1].element().getID());
//                            System.out.println(idUser);
//                            System.out.println(edge.element().getClass());
//                            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                            if(edge.vertices()[1].element().getID() == user.getID() && (edge.element() instanceof RelationshipIndirect)){
//                                ((RelationshipIndirect)edge.element()).addInterest(interest);
//                                exists = true;
//                            }
//                        }
//                        if(!exists){
//                            graph.insertEdge(user, this.users.get(idUser), new RelationshipIndirect(interest));
//                        }
//                    }
//                }
//
//
//            }


    }


//        for(String id : relationships.get(idUser)){
//
//            if(!graph.containVertice(this.users.get(Integer.parseInt(id)))){
//                this.users.get(Integer.parseInt(id)).setType(Type.INCLUIDO);
//                graph.insertVertex(this.users.get(Integer.parseInt(id)));
//            }
//
//            ArrayList<Interest> tempInterests = new ArrayList<>();
//
//            for(Integer idInterest : interests.keySet()){
//                for(String interest : interests.get(idInterest).getIdsOfUsers()){
//                    if(interest.equals(String.valueOf(idUser)) && interest.equals(id)){
//                        tempInterests.add(this.interests.get(interest));
//                    }
//                }
//            }
//
//            if(tempInterests.size() !=0){
//                graph.insertEdge(users.get(idUser), users.get(Integer.parseInt(id)), new RelationshipShared(tempInterests));
//            } else {
//                graph.insertEdge(users.get(idUser), users.get(Integer.parseInt(id)), new RelationshipSimple());
//            }
//        }

//    }


    public Logging getLog() {
        return log;
    }

    public HashMap<Integer, ArrayList<String>> getRelationships() {
        return relationships;
    }

    public HashMap<Integer, Interest> getInterests() {
        return interests;
    }
}
