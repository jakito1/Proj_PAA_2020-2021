package com.pa.proj2020.adts.graph;

public class TotalModel implements Model {
    private final SocialNetwork socialNetwork;

    public TotalModel(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    /**
     * Método que constroi o algoritmo Total
     *
     * @return grafo com algoritmo total
     */
    @Override
    public DirectGraph<User, Relationship> modelConstructor() {
        socialNetwork.getUsers().values().forEach(user -> socialNetwork.getGraph().insertVertex(user));

        socialNetwork.getRelationships().keySet().forEach(id -> {
            socialNetwork.getRelationships().get(id)
                    .forEach(id2 -> socialNetwork.insertEdge(socialNetwork.getUsers()
                            .get(id), socialNetwork.getUsers().get(Integer.parseInt(id2))));
        });

        return this.socialNetwork.getGraph();
    }

}
