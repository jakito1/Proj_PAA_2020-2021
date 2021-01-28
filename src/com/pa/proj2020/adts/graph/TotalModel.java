package com.pa.proj2020.adts.graph;

public class TotalModel implements Model{
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
        for (User user : socialNetwork.getUsers().values()) {
            socialNetwork.getGraph().insertVertex(user);
        }

        for (Integer id : socialNetwork.getRelationships().keySet()) {
            for (String id2 : socialNetwork.getRelationships().get(id)) {
                socialNetwork.insertEdge(socialNetwork.getUsers().get(id), socialNetwork.getUsers().get(Integer.parseInt(id2)));
            }
        }

        return this.socialNetwork.getGraph();
    }

}
