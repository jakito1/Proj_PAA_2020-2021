package com.pa.proj2020.adts.graph;

public class IterativeModel implements Model{
    private final SocialNetwork socialNetwork;
    private final int idUser;

    public IterativeModel(SocialNetwork socialNetwork, int idUser) {
        this.socialNetwork = socialNetwork;
        this.idUser = idUser;
    }

    /**
     * Método que constroi o algoritmo Iterativo
     *
     */
    @Override
    public DirectGraph<User, Relationship> modelConstructor() {
        if (socialNetwork.getRelationships().isEmpty() || socialNetwork.getUsers().isEmpty()) {
            socialNetwork.initializeData();
        } else if (idUser < 0) {
            return null;
        }
        User user = socialNetwork.getUsers().get(idUser);

        if (socialNetwork.getGraph().containVertice(user)) {

            for (Vertex<User> userVertex : socialNetwork.getGraph().vertices()) {
                if (userVertex.element().getID() == idUser && userVertex.element().getType().equals(Type.INCLUIDO)) {
                    userVertex.element().setType(Type.ADICIONADO);
                    break;
                }
            }

        } else {
            user.addListInterest(socialNetwork.interestsOfUser(user.getID(), socialNetwork.getInterests()));
            socialNetwork.getGraph().insertVertex(user);
        }

        for (String idRelationship : socialNetwork.getRelationships().get(user.getID())) {
            User userRelationship = socialNetwork.getUsers().get(Integer.parseInt(idRelationship));
            if (!socialNetwork.getGraph().containVertice(userRelationship)) {
                userRelationship.setType(Type.INCLUIDO);
                socialNetwork.getGraph().insertVertex(userRelationship);
                socialNetwork.getStatistics().addUsersIncluded(user, userRelationship);
                userRelationship.addListInterest(socialNetwork.interestsOfUser(userRelationship.getID(),
                        socialNetwork.getInterests()));
            }
        }

        for (Vertex<User> userVertex : socialNetwork.getGraph().vertices()) {
            if (userVertex.element().getID() != idUser) {
                socialNetwork.insertEdge(user, userVertex.element());
            }
        }

        return socialNetwork.getGraph();
    }

}
