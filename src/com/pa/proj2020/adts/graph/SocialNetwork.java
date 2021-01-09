package com.pa.proj2020.adts.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SocialNetwork implements Originator {

    private DirectGraph<User, Relationship> graph;
    private final Logging log = Logging.getInstance();
    private final Statistics statistics;
    private Map<Integer, User> users;
    private Map<Integer, ArrayList<String>> relationships;
    private Map<Integer, Interest> interests;

    /**
     * Cria um objeto SocialNetwork
     * Inicializa os atributos
     */
    public SocialNetwork() {
        graph = new DirectGraph<>();
        users = new HashMap<>();
        relationships = new HashMap<>();
        interests = new HashMap<>();
        statistics = new Statistics();
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
        boolean relationshipDirect = false;
        Relationship relationship;

        for (Interest interest : this.interests.values()) {
            if (interest.getIdsOfUsers().contains(String.valueOf(user1.getID())) &&
                    interest.getIdsOfUsers().contains(String.valueOf(user2.getID()))) {
                tempInterests.add(interest);
            }
        }

        if (this.relationships.get(user1.getID()).contains(String.valueOf(user2.getID()))) {
            relationshipDirect = true;
        }

        //HashSet<Edge<Relationship, User>> tempEdges = new HashSet<>(this.graph.edges());
        //tempEdges

        if (!tempInterests.isEmpty() && !relationshipDirect) {
            relationship = new RelationshipIndirect(tempInterests);
            this.graph.insertEdge(user1, user2, relationship);
            log.addRelationshipIndirect(user1.getID(), user2.getID(), tempInterests.size());
        } else if (tempInterests.isEmpty() && relationshipDirect) {
            relationship = new RelationshipSimple();
            this.graph.insertEdge(user1, user2, relationship);
            log.addRelationshipDirect(user1.getID(), user2.getID(), 0);
        } else if (!tempInterests.isEmpty()) {
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
                this.statistics.addUsersIncluded(user, userRelationship);
                userRelationship.addListInterest(this.interestsOfUser(userRelationship.getID()));
            }
        }

        for (Vertex<User> userVertex : this.graph.vertices()) {
            if (userVertex.element().getID() != idUser) {
                this.insertEdge(user, userVertex.element());
            }
        }
    }


    public int minPath(Vertex<User> origin, Vertex<User> end, ArrayList<User> path) {
        return this.graph.minCostPath(origin, end, path);
    }

    @Override
    public String toString() {
        ArrayList<User> path = new ArrayList<>();
        Vertex<User> user1 = null;
        Vertex<User> user2 = null;
        for (Vertex<User> user : this.graph.vertices()) {
            if (user.element().getID() == 1) {
                user1 = user;
            } else if (user.element().getID() == 11) {
                user2 = user;
            }
        }

        minPath(user1, user2, path);
        return "SocialNetwork{" +
                "minPath=" + path.toString() +
                '}';
    }


    public String addedUsersStats() {
        return this.statistics.addedUsersStats(this.graph);
    }

    public String includedUsersStats() {
        return this.statistics.includedUsersStats(this.graph);
    }

    public String userWithMoreDirectRelationshipsStats() {
        return this.statistics.userWithMoreDirectRelationshipsStats(this.graph);
    }

    public String interestMostSharedStats() {
        return this.statistics.interestMostSharedStats(this.graph);
    }

    public DirectGraph<User, Relationship> getGraph() {
        return this.graph;
    }

    public Map<Integer, User> getUsers() {
        return this.users;

    }

    public Logging getLog() {
        return log;
    }

    public Map<Integer, ArrayList<String>> getRelationships() {
        return relationships;
    }

    public Map<Integer, Interest> getInterests() {
        return interests;
    }

    @Override
    public Memento createMemento() {
        return new MyMemento(this);
    }

    @Override
    public void setMemento(Memento savedState) {
        List tempList = savedState.getState();
        users = (Map<Integer, User>) tempList.get(0);
        relationships = (Map<Integer, ArrayList<String>>) tempList.get(2);
        interests = (Map<Integer, Interest>) tempList.get(3);
        graph = constructModelTotal();
    }

    static class MyMemento implements Memento {
        private Map<Integer, User> users;
        private Map<Integer, ArrayList<String>> relationships;
        private Map<Integer, Interest> interests;

        public MyMemento(SocialNetwork stateToSave) {
            users = new HashMap<>();
            relationships = new HashMap<>();
            interests = new HashMap<>();
            load(stateToSave);
        }

        private void load(SocialNetwork stateToSave) {
            users = stateToSave.getUsers().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            relationships = stateToSave.getRelationships().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            interests = stateToSave.getInterests().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        @Override
        public <T> List<T> getState() {
            List<T> returnList = new ArrayList<>();
            returnList.add(0, (T) users);
            returnList.add(1, (T) relationships);
            returnList.add(2, (T) interests);
            return returnList;
        }
    }
}
