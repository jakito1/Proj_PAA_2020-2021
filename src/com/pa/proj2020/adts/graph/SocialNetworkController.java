package com.pa.proj2020.adts.graph;

public class SocialNetworkController {

    private final SocialNetworkView view;
    private final SocialNetwork model;

    public SocialNetworkController(SocialNetworkView view, SocialNetwork model) {
        this.view = view;
        this.model = model;
        model.addObservers(view);
    }


}
