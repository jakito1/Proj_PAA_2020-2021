package com.pa.proj2020.adts.graph;

public class Caretaker {
    private final SocialNetwork socialNetwork;
    private Memento undo;

    public Caretaker(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public void saveState() {
        undo = socialNetwork.createMemento();
    }

    public void restoreState() throws NoMementoException {
        if (undo == null) {
            throw new NoMementoException("Nothing to undo.");
        }

        socialNetwork.setMemento(undo);
    }
}
