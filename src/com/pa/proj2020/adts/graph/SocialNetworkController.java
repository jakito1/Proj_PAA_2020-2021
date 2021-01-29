package com.pa.proj2020.adts.graph;

public class SocialNetworkController {

    private final SocialNetwork model;

    public SocialNetworkController(SocialNetworkView view, SocialNetwork model) {
        this.model = model;
        model.addObservers(view);
        view.setTriggers(this);
    }

    public void exportSerialization() {
        getMemoryPersistence().exportSerialization();
    }

    public void importSerialization() {
        model.setGraph(getMemoryPersistence().importSerialization());
    }

    public void exportJSON() {
        getMemoryPersistence().exportJSON();
    }

    private MemoryPersistence getMemoryPersistence() {
        return model.getMemoryPersistence();
    }


}
