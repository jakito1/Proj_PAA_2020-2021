package com.pa.proj2020.adts.graph;

import java.util.ArrayList;
import java.util.List;

public class RelationshipShared implements Relationship {
    private final List<Interest> interests;

    public RelationshipShared(List<Interest> interests) {
        this.interests = new ArrayList<>(interests);
    }


    public void addInterest(Interest interest) {
        this.interests.add(interest);
    }

    public void removeInterest(Interest interest) {
        this.interests.remove(interest);
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public int getNumberInterests() {
        return interests.size();
    }

    @Override
    public String toString() {
        return "";
    }
}
