package com.pa.proj2020.adts.graph;

public interface Originator {
    public Memento createMemento();

    public void setMemento(Memento savedState);
}

