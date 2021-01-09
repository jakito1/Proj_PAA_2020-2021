package com.pa.proj2020.adts.graph;

public interface Originator {
    Memento createMemento();

    void setMemento(Memento savedState);
}

