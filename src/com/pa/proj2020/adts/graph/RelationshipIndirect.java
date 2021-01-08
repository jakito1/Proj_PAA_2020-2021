package com.pa.proj2020.adts.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RelationshipIndirect implements Relationship {

    private final Set<Interest> listOfInterests;
    private int number;
    private final User[] users;

    public RelationshipIndirect() {
        listOfInterests = new HashSet<>();
        users = new User[2];
    }

    public RelationshipIndirect(Interest interest) {
        this();
        this.addInterest(interest);
    }

    public RelationshipIndirect(Collection<Interest> interests) {
        this();
        this.listOfInterests.addAll(interests);
    }

    public void setUsers(User inboundUser, User outboundUser) {
        if (inboundUser == null || outboundUser == null) return;
        users[0] = inboundUser;
        users[1] = outboundUser;

    }

    public void addInterest(Interest interest) {
        listOfInterests.add(interest);
    }

    public void removeInterest(Interest interest) {
        listOfInterests.remove(interest);
    }

    public Set<Interest> getListOfInterests() {
        return listOfInterests;
    }

    public int getNumber() {
        return listOfInterests.size();
    }

    public User getInboundUser() {
        return users[0];
    }

    public User getOutboundUser() {
        return users[1];
    }

    public User[] getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "RelationshipIndirect{" +
                "number=" + getNumber() +
                '}';
    }
}
