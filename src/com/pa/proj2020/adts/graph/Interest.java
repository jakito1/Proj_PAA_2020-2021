package com.pa.proj2020.adts.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Interest implements Serializable {
    private final int id;
    private final String name;
    private final ArrayList<String> idsOfUsers;

    public Interest(int id, String name) {
        this.id = id;
        this.name = name;
        this.idsOfUsers = new ArrayList<>();
    }

    public Interest(int id, String name, List<String> ids) {
        this(id, name);
        idsOfUsers.addAll(ids);
    }

    public void addIdUser(String id) {
        this.idsOfUsers.add(id);
    }

    public void removeIdUser(String id) {
        this.idsOfUsers.remove(id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIdsOfUsers() {
        return idsOfUsers;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
