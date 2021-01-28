package com.pa.proj2020.adts.graph;

import java.util.Stack;

/**
 * Classe responsável por armazenar todos os Mementos gerados pelo Originator.
 * Contêm as referências de todos os objetos Memento associados ao Originator.
 */
public class Caretaker {
    private final SocialNetwork socialNetwork;
    private final Stack<Memento> undo;

    /**
     * Inicializa um objeto Caretaker para uma socialNetwork (objeto Memento)
     *
     * @param socialNetwork representa o objeto Memento associado ao Originator
     */
    public Caretaker(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
        undo = new Stack<>();
    }

    /**
     * Método que permite guardar o estado da SocialNetwork
     */
    public void saveState() {
        undo.push(socialNetwork.createMemento());
    }

    /**
     * Método que permite fazer restore ao estado a SocialNetwork
     *
     * @throws NoMementoException quando não é possível fazer mais undo
     */
    public void restoreState() throws NoMementoException {
        if (undo.empty()) {
            throw new NoMementoException("Nothing to undo.");
        }

        socialNetwork.setMemento(undo.pop());
    }
}
