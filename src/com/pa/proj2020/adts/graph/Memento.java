package com.pa.proj2020.adts.graph;

import java.util.List;

public interface Memento {
    List<DirectGraph<User, Relationship>> getState();
}