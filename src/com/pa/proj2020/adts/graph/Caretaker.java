package com.pa.proj2020.adts.graph;

import java.util.Stack;

public class Caretaker {
    private final SocialNetwork socialNetwork;
    private final Stack<Memento> undo;

    public Caretaker(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
        undo = new Stack<>();
    }

    public void saveState() {
        undo.push(socialNetwork.createMemento());
    }

    public void restoreState() throws NoMementoException {
        if (undo.empty()) {
            throw new NoMementoException("Nothing to undo.");
        }

        socialNetwork.setMemento(undo.pop());
    }
}
