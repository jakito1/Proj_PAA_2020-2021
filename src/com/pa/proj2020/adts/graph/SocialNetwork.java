package com.pa.proj2020.adts.graph;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialNetwork implements Originator, Serializable {

    private final HashMap<Integer, User> users;
    private final HashMap<Integer, ArrayList<String>> relationships;
    private final HashMap<Integer, Interest> interests;
    private final Logging log = Logging.getInstance();
    private final Statistics statistics;
    private DirectGraph<User, Relationship> graph;
    MemoryPersistence memoryPersistence;

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
        memoryPersistence = new MemoryPersistence(this);
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
                this.insertEdge(users.get(id), users.get(Integer.parseInt(id2)));
//                graph.insertEdge(users.get(id), users.get(Integer.parseInt(id2)), new RelationshipSimple());
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
        updateLog();

        return list;

    }

    public void insertEdge(User user1, User user2) {
        this.insertEdge(user1, user2, false);
    }

    public void insertEdge(User user1, User user2, boolean addIndirect) {
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

        if (!tempInterests.isEmpty() && !relationshipDirect && addIndirect) {
            relationship = new RelationshipIndirect(tempInterests);
            this.graph.insertEdge(user1, user2, relationship);
            log.addRelationshipIndirect(user1.getID(), user2.getID(), tempInterests.size());
        } else if (tempInterests.isEmpty() && relationshipDirect && !addIndirect) {
            relationship = new RelationshipSimple();
            this.graph.insertEdge(user1, user2, relationship);
            log.addRelationshipDirect(user1.getID(), user2.getID(), 0);
        } else if (!tempInterests.isEmpty() && relationshipDirect && !addIndirect) {
            relationship = new RelationshipShared(tempInterests);
            this.graph.insertEdge(user1, user2, relationship);
            log.addRelationshipDirect(user1.getID(), user2.getID(), tempInterests.size());
        }
        updateLog();

    }


    public void addIndirectRelationships(int idUser) {
        User user = this.users.get(idUser);

        for (Vertex<User> userVertex : this.graph.vertices()) {
            if (userVertex.element().getID() != idUser) {
                this.insertEdge(user, userVertex.element(), true);
            }
        }
    }

    public void constructModelIterative(int idUser) {

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

    private void updateLog() {
        try {
            new File("outputFiles/").mkdirs();
            FileWriter fileWriter = new FileWriter("outputFiles/LogFile " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss")) + ".log");
            fileWriter.write(log.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> getUsersNotInserted() {
        ArrayList<String> list = new ArrayList<>();

        ArrayList<Integer> tempList = new ArrayList<>();

        for (Vertex<User> user : this.graph.vertices()) {
            tempList.add(user.element().getID());
        }

        for (Integer id : this.users.keySet()) {
            if (!tempList.contains(id)) {
                list.add(this.users.get(id).toString());
            }
        }
        return list;
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

    public Map<User, Integer> topFiveUsersWithMostRelationshipsStats() {
        return this.statistics.topFiveUsersWithMostRelationshipsStats(this.graph);
    }

    public Logging getLog() {
        return log;
    }

    public HashMap<Integer, ArrayList<String>> getRelationships() {
        return relationships;
    }

    public HashMap<Integer, Interest> getInterests() {
        return interests;
    }
    public Map<Interest, Integer> topFiveInterestsStats(){
        return this.statistics.topFiveInterestsStats(this.graph);
    }

    @Override
    public Memento createMemento() {
        return new MyMemento(this.graph);
    }

    @Override
    public void setMemento(Memento savedState) {
        ByteArrayInputStream temp = new ByteArrayInputStream(savedState.getState());
        try {
            graph = (DirectGraph<User, Relationship>) new ObjectInputStream(temp).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void exportSerialization(){
        memoryPersistence.exportSerialization();
    }

    public void importSerialization(){
        graph = memoryPersistence.importSerialization();
    }

    static class MyMemento implements Memento {
        private byte[] state;

        public MyMemento(DirectGraph<User, Relationship> stateToSave) {
            load(stateToSave);
        }

        private void load(DirectGraph<User, Relationship> stateToSave) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(bos);
                oos.writeObject(stateToSave);
                oos.flush();
                oos.close();
                bos.close();
                state = bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public byte[] getState() {
            return state;
        }
    }


}
