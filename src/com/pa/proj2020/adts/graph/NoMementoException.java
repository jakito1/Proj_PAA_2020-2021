package com.pa.proj2020.adts.graph;

/**
 * Classe responsável pelo lançamento de uma exceção em caso de erro no Memento
 */
public class NoMementoException extends RuntimeException {
    public NoMementoException(String s) {
        super(s);
    }
}
