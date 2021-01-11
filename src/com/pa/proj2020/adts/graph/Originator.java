package com.pa.proj2020.adts.graph;

/**
 * Interface que permite criar um Memento com a representação do seu estado atual
 * Permite também utilizar o Memento para restaurar o seu estado
 */
public interface Originator {
    Memento createMemento();

    void setMemento(Memento savedState);
}

