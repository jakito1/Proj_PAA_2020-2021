package com.pa.proj2020.adts.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Statistics {
    private final HashMap<User, ArrayList<User>> usersIncluded;


    public Statistics() {
        usersIncluded = new HashMap<>();
    }

    public HashMap<User, ArrayList<User>> getUsersIncluded() {
        return usersIncluded;
    }

    public void addUsersIncluded(User user, User userIncluded) {
        if (!this.usersIncluded.containsKey(user)) {
            this.usersIncluded.put(user, new ArrayList<>());
        }
        ArrayList<User> list = new ArrayList<>(this.usersIncluded.get(user));
        list.add(userIncluded);
        this.usersIncluded.put(user, list);

    }

    public String addedUsersStats(DirectGraph<User, Relationship> graph) {
        String s = "STATISTIC USERS ADDED\n";
        s = s + "Number of users: " + graph.getVertices().size() + "\n";
        s = s + "List of ids and names of users: " + graph.getVertices().size() + "\n";


        for (Vertex<User> userVertex : graph.vertices()) {
            s = s + "ID: " + userVertex.element().getID() + "   Name:" + userVertex.element().getName() + "\n";
        }

        return s;
    }

    public String includedUsersStats(DirectGraph<User, Relationship> graph) {
        String s = "STATISTIC USERS INCLUDED BY USER ADDED\n";

        for (User user : this.usersIncluded.keySet()) {
            s = s + "User: " + user.getName() + " has included: \n";
            for (User userIncluded : this.usersIncluded.get(user)) {
                s = s + "   " + userIncluded.getName() + "\n";
            }
            s = s + "\n";
        }

        return s;
    }


    public String userWithMoreDirectRelationshipsStats(DirectGraph<User, Relationship> graph) {
        String s = "STATISTIC USER WITH MORE DIRECT RELATIONSHIPS\n";

        User user = null;
        int edges = 0;
        int count = 0;

        for (Vertex<User> userVertex : graph.vertices()) {
            count = 0;

            for (Edge<Relationship, User> edge : graph.incidentEdges(userVertex)) {
                if (!(edge.element() instanceof RelationshipIndirect)) {
                    count++;
                }
            }

            if (count > edges) {
                edges = count;
                user = userVertex.element();
            }
        }

        if (user != null) {
            s = s + "ID: " + user.getID() + "   Name: " + user.getName() + "\n";
        } else {
            s = "Not found\n";
        }

        return s;
    }

    public String interestMostSharedStats(DirectGraph<User, Relationship> graph) {
        String s = "STATISTIC INTEREST MOST SHARED\n";

        Interest interest = null;
        ArrayList<Interest> interests = new ArrayList<>();
        int number = 0;
        int count = 0;

        for (Vertex<User> userVertex : graph.vertices()) {
            count = 0;

            for (Edge<Relationship, User> edge : graph.incidentEdges(userVertex)) {
                if ((edge.element() instanceof RelationshipShared)) {
                    interests.addAll(((RelationshipShared) edge.element()).getInterests());
                } else if ((edge.element() instanceof RelationshipIndirect)) {
                    interests.addAll(((RelationshipIndirect) edge.element()).getListOfInterests());
                }
            }

        }

        for (Interest interestOfList : interests) {
            count = 0;
            for (Interest interestOfList2 : interests) {
                if (interestOfList.equals(interestOfList2)) {
                    count++;
                }
            }
            if (count > number) {
                interest = interestOfList;
                number = count;
            }

        }

        if (interest != null) {
            s = s + "ID: " + interest.getId() + "   Name: " + interest.getName() + "\n";
        } else {
            s = "Not found\n";
        }

        return s;
    }


}
