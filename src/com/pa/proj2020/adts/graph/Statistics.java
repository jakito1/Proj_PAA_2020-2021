package com.pa.proj2020.adts.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe responsável pela criação de estatisticas
 */
public class Statistics {
    private final Map<User, ArrayList<User>> usersIncluded;


    /**
     * Permite criar estatisticas com uma listagem de utilizadores incluidos
     */
    public Statistics() {
        usersIncluded = new HashMap<>();
    }


    /**
     * Método que permite adicionar um utilizador como key e a sua lista de utilizadores que
     * incluiu como value
     *
     * @param user         representa a key que pretendemos adicionar
     * @param userIncluded representa um value que pretendemos adicionar à lista associada
     */
    public void addUsersIncluded(User user, User userIncluded) {
        if (!this.usersIncluded.containsKey(user)) {
            this.usersIncluded.put(user, new ArrayList<>());
        }

        ArrayList<User> list = new ArrayList<>(this.usersIncluded.get(user));
        list.add(userIncluded);
        this.usersIncluded.put(user, list);
    }


    /**
     * Método que representa a estatistica relativa ao numero e listagem de utilizadores adicionados
     *
     * @param graph representa o grafo utilizado
     * @return uma string com o numero e a listagem de utilizadores adicionados ao grafo
     */
    public String addedUsersStats(DirectGraph<User, Relationship> graph) {
        StringBuilder s = new StringBuilder("STATISTIC USERS ADDED\n");
        s.append("Number of users: ").append(graph.getVertices().size()).append("\n");
        s.append("List of ids and names of users: ").append(graph.getVertices().size()).append("\n");

        graph.vertices().forEach(userVertex -> s.append("ID: ")
                .append(userVertex.element().getID()).append("   Name:")
                .append(userVertex.element().getName()).append("\n"));

        return s.toString();
    }


    /**
     * Método que representa a estatistica relativa ao numero e listagem de utilizadores incluidos
     * por um utilizador adicionado
     *
     * @return uma string com o numero e a listagem de utilizadores incluidos atraves dos adicionados
     */
    public String includedUsersStats() {
        StringBuilder s = new StringBuilder("STATISTIC USERS INCLUDED BY USER ADDED\n");

        this.usersIncluded.keySet().forEach(user -> {
            s.append("User: ").append(user.getName()).append(" has included: \n");
            this.usersIncluded.get(user).forEach(userIncluded -> s.append("   ")
                    .append(userIncluded.getName()).append("\n\n"));
        });

        return s.toString();
    }


    /**
     * Método que representa a estatistica relativa ao utilizador com mais relacionamentos diretos
     *
     * @param graph representa o grafo utilizado
     * @return uma string com o id e o nome do utilizador com mais relacionamentos diretos
     */
    public String userWithMoreDirectRelationshipsStats(DirectGraph<User, Relationship> graph) {
        String s = "STATISTIC USER WITH MORE DIRECT RELATIONSHIPS\n";
        User user = getUser(graph);

        return user != null ? s + "ID: " + user.getID() + "   Name: " + user.getName() + "\n" : "Not found\n";
    }

    private User getUser(DirectGraph<User, Relationship> graph) {
        User user = null;
        int edges = 0;

        for (Vertex<User> userVertex : graph.vertices()) {
            int count = 0;

            count += graph.incidentEdges(userVertex).stream()
                    .filter(edge -> !(edge.element() instanceof RelationshipIndirect)).count();

            if (count > edges) {
                edges = count;
                user = userVertex.element();
            }
        }
        return user;
    }


    /**
     * Método que representa a estatistica relativa ao interesse mais partilhado
     *
     * @param graph representa o grafo utilizado
     * @return uma string com o id e o nome do interesse mais partilhado
     */
    public String interestMostSharedStats(DirectGraph<User, Relationship> graph) {
        String s = "STATISTIC INTEREST MOST SHARED\n";
        Interest interest = getInterest(graph);

        if (interest != null) {
            s = s + "ID: " + interest.getId() + "   Name: " + interest.getName() + "\n";
        } else {
            s = "Not found\n";
        }
        return s;
    }

    private Interest getInterest(DirectGraph<User, Relationship> graph) {
        Interest interest = null;
        ArrayList<Interest> interests = new ArrayList<>();
        int number = 0;

        graph.vertices().stream().flatMap(userVertex -> graph.incidentEdges(userVertex).stream()).forEach(edge -> {
            if ((edge.element() instanceof RelationshipShared)) {
                interests.addAll(((RelationshipShared) edge.element()).getInterests());
            } else if ((edge.element() instanceof RelationshipIndirect)) {
                interests.addAll(((RelationshipIndirect) edge.element()).getListOfInterests());
            }
        });

        for (Interest interestOfList : interests) {
            int count = 0;
            count += interests.stream().filter(interestOfList::equals).count();
            if (count > number) {
                interest = interestOfList;
                number = count;
            }
        }
        return interest;
    }


    /**
     * Método que representa a estatistica relativa ao número de relacionamentos dos
     * 5 utilizadores com mais relacionamentos
     *
     * @param graph representa o grafo utilizador
     * @return um hashmap com os 5 utilizadores com mais relationamentos
     */
    public Map<User, Integer> topFiveUsersWithMostRelationshipsStats(DirectGraph<User, Relationship> graph) {
        Map<User, Integer> map = new HashMap<>();
        List<Vertex<User>> usersList = new ArrayList<>(graph.vertices());
        Vertex<User>[] user = new Vertex[5];
        int edges = 0;

        for (int i = 0; i < 5; i++) {
            for (Vertex<User> userVertex : usersList) {
                int count = 0;
                count += graph.incidentEdges(userVertex).stream()
                        .filter(edge -> !(edge.element() instanceof RelationshipIndirect)).count();
                if (count > edges) {
                    edges = count;
                    user[i] = userVertex;
                }
            }
            map.put(user[i].element(), edges);
            usersList.remove(user[i]);
            edges = 0;
        }
        return map;
    }


    /**
     * Método que representa a estatistica relativa aos 5 interesses mais partilhados
     *
     * @param graph representa ao grafo utilizado
     * @return um hashmap com os 5 intesses mais partilhados
     */
    public Map<Interest, Integer> topFiveInterestsStats(DirectGraph<User, Relationship> graph) {
        Map<Interest, Integer> map = new HashMap<>();
        List<Interest> interests = new ArrayList<>();
        Interest[] interest = new Interest[5];
        int number = 0;

        graph.vertices().stream().flatMap(userVertex -> graph.incidentEdges(userVertex).stream()).forEach(edge -> {
            if ((edge.element() instanceof RelationshipShared)) {
                interests.addAll(((RelationshipShared) edge.element()).getInterests());
            } else if ((edge.element() instanceof RelationshipIndirect)) {
                interests.addAll(((RelationshipIndirect) edge.element()).getListOfInterests());
            }
        });

        for (int i = 0; i < 5; i++) {
            for (Interest interestOfList : interests) {
                int count = 0;
                count += interests.stream().filter(interestOfList::equals).count();
                if (count > number) {
                    interest[i] = interestOfList;
                    number = count;
                }
            }

            map.put(interest[i], number);
            List<Interest> tempInterest = new ArrayList<>();
            for (Interest interest1 : interests) {
                if (interest1.getId() == interest[i].getId()) {
                    tempInterest.add(interest1);
                }
            }
            interests.removeAll(tempInterest);
            number = 0;
        }

        return map;
    }
}
