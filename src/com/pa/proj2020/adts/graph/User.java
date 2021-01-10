package com.pa.proj2020.adts.graph;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements Serializable {

    private final String name;
    private final int id;
    private final Set<Interest> interests;
    private Type type; //adicionado ou incluido

    public User(String name, int id, Type type) {
        if (name == null || id <= 0) {
            throw new IllegalArgumentException();
        }
        this.interests = new HashSet<>();
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public User(String name, int id, Type type, Collection<Interest> interests) {
        if (name == null || id <= 0) {
            throw new IllegalArgumentException();
        }
        this.interests = new HashSet<>(interests);
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public void addInterest(Interest interest) {
        this.interests.add(interest);
    }

    public void addListInterest(List<Interest> interests) {
        this.interests.addAll(interests);
    }

    public void removeInterest(Interest interest) {
        this.interests.remove(interest);
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
