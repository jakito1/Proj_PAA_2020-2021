package com.pa.proj2020.adts.graph;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe responsável pela gestão da Social Network
 */
public class SocialNetwork implements Originator, Serializable {

    private final HashMap<Integer, User> users;
    private final HashMap<Integer, ArrayList<String>> relationships;
    private final HashMap<Integer, Interest> interests;
    private final Logging log = Logging.getInstance();
    private final Statistics statistics;
    private DirectGraph<User, Relationship> graph;
    private String userNamesFile, relationshipsFile, interestNamesFile, interestsFile;
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
        this.checkFilenames();

    }

    /**
     * Cria um objeto SocialNetwork atraves do usernamefiles, relationshipfiles, interestnamefile e interestfiles
     * @param userNamesFile representa o ficheiro com os nomes dos utilizadores
     * @param relationshipsFile representa o ficheiro com relationships
     * @param interestNamesFile representa o ficheiro com o nome dos interesses
     * @param interestsFile representa o ficheiro com os interesses
     */
    public SocialNetwork(String userNamesFile, String relationshipsFile, String interestNamesFile, String interestsFile){
        this();
        this.userNamesFile = userNamesFile;
        this.relationshipsFile = relationshipsFile;
        this.interestNamesFile = interestNamesFile;
        this.interestsFile = interestsFile;
    }

    /**
     * Método que verifica se os nomes dos ficheiros foram inicializados
     */
    public void checkFilenames(){
        if(this.userNamesFile == null || this.relationshipsFile == null || this.interestNamesFile == null || this.interestsFile == null){
            this.userNamesFile = "user_names.csv";
            this.relationshipsFile = "relationships.csv";
            this.interestNamesFile = "interest_names.csv";
            this.interestsFile = "interests.csv";
        }

    }

    /**
     * Método que inicializa os nomes dos ficheiros
     * @param userNamesFile representa o ficheiro com os nomes dos utilizadores
     * @param relationshipsFile representa o ficheiro com relationships
     * @param interestNamesFile representa o ficheiro com o nome dos interesses
     * @param interestsFile representa o ficheiro com os interesses
     */
    public void setFileNames(String userNamesFile, String relationshipsFile, String interestNamesFile, String interestsFile){
        this.userNamesFile = userNamesFile;
        this.relationshipsFile = relationshipsFile;
        this.interestNamesFile = interestNamesFile;
        this.interestsFile = interestsFile;
        this.checkFilenames();
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
     * Método que constroi o algoritmo Total
     * @return grafo com algoritmo total
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

    /**
     * Método que retorna o grafo
     * @return o grafo construido
     */
    public DirectGraph<User, Relationship> getGraph() {
        return this.graph;
    }

    /**
     * Metodo que retorna uma colecao com users
     * @return um hashmap com users
     */
    public HashMap<Integer, User> getUsers() {
        return this.users;
    }

    /**
     * Metodo que retorna uma colecao com relacoes
     * @return um hashmap com relacoes
     */
    public HashMap<Integer, ArrayList<String>> getRelationShips() {
        return this.relationships;
    }

    /**
     * Metodo que retorna os interesses de um utilizador
     * @param idUser representa o id do utilizador
     * @return a lista de interesses do utilizador fornecido
     */
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

    /**
     * Metodo que insere uma aresta entre dois users
     * @param user1 representa um utilizador
     * @param user2 representa outro utilizador
     */
    public void insertEdge(User user1, User user2) {
        this.insertEdge(user1, user2, false);
    }

    /**
     * Metodo que insere uma aresta entre dois utilizadores
     * @param user1 representa um utilizador
     * @param user2 representa outro utilizador
     * @param addIndirect true se quiser adicionar relacoes indiretas, false caso contrario
     */
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

    /**
     * Metodo que adiciona relacoes indiretas
     * @param idUser representa o user
     */
    public void addIndirectRelationships(int idUser) {
        User user = this.users.get(idUser);

        for (Vertex<User> userVertex : this.graph.vertices()) {
            if (userVertex.element().getID() != idUser) {
                this.insertEdge(user, userVertex.element(), true);
            }
        }
    }

    /**
     * Método que constroi o algoritmo Iterativo
     * @param idUser representa o user
     */
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


    /**
     * Metodo que retorna o caminho de menor custo entre dois vertices
     * @param origin representa um vertice
     * @param end representa outro vertice
     * @param path representa o caminho de menor custo
     * @return o custo do caminho
     */
    public int minPath(Vertex<User> origin, Vertex<User> end, ArrayList<User> path) {
        return this.graph.minCostPath(origin, end, path);
    }

    /**
     * Metodo que retorna uma string com a social network
     * @return uma string com a social network
     */
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

    /**
     * Metodo que permite atualizar o Logging
     */
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


    /**
     * Metodo que permite obter a lista de utilizadores nao inseridos
     * @return a lista de utilizadores nao inseridos
     */
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


    /**
     * Metodo que retorna a estatistica de utilizadores adicionados
     * @return estatistica de utilizadores adicionados
     */
    public String addedUsersStats() {
        return this.statistics.addedUsersStats(this.graph);
    }

    /**
     * Metodo que retorna a estatistica de utilizadores incluidos
     * @return estatistica de utilizadores incluidos
     */
    public String includedUsersStats() {
        return this.statistics.includedUsersStats(this.graph);
    }

    /**
     * Metodo que retorna a estatistica de utilizadores com mais relacionamentos
     * @return estatistica de utilizadores com mais relacionamentos
     */
    public String userWithMoreDirectRelationshipsStats() {
        return this.statistics.userWithMoreDirectRelationshipsStats(this.graph);
    }

    /**
     * Metodo que retorna a estatistica de interesse mais partilhado
     * @return estatistica de interesse mais partilhado
     */
    public String interestMostSharedStats() {
        return this.statistics.interestMostSharedStats(this.graph);
    }

    /**
     * Metodo que retorna a estatistica de utilizadores com mais relacionamentos
     * @return estatistica de utilizadores com mais relacionamentos
     */
    public Map<User, Integer> topFiveUsersWithMostRelationshipsStats() {
        return this.statistics.topFiveUsersWithMostRelationshipsStats(this.graph);
    }

    /**
     * Metodo que permite obter um Logging
     * @return um Logging
     */
    public Logging getLog() {
        return log;
    }

    /**
     * Metodo que permite obter uma colecao de relacoes
     * @return um hashmap de relacoes
     */
    public HashMap<Integer, ArrayList<String>> getRelationships() {
        return relationships;
    }

    /**
     * Metodo que permite obter uma colecao de interesses
     * @return um hashmap de interesses
     */
    public HashMap<Integer, Interest> getInterests() {
        return interests;
    }

    /**
     * Metodo que permite obter a estatistica do top 5 de interesses
     * @return a estatistica do top 5 de interesses
     */
    public Map<Interest, Integer> topFiveInterestsStats(){
        return this.statistics.topFiveInterestsStats(this.graph);
    }

    /**
     * Metodo que permite criar o memento
     * @return o novo memento
     */
    @Override
    public Memento createMemento() {
        return new MyMemento(this.graph);
    }

    /**
     * Metodo que permite usar o memento
     * @param savedState representa o novo memento
     */
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
