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

        if (getGraph().containVertice(user)) {
            getGraph().vertices().stream().filter(userVertex -> userVertex.element()
                    .getID() == idUser && userVertex.element().getType().equals(Type.INCLUIDO))
                    .findFirst().ifPresent(userVertex -> userVertex.element().setType(Type.ADICIONADO));
        } else {
            user.addListInterest(socialNetwork.interestsOfUser(user.getID(), socialNetwork.getInterests()));
            getGraph().insertVertex(user);
        }

        for (String idRelationship : socialNetwork.getRelationships().get(user.getID())) {
            User userRelationship = socialNetwork.getUsers().get(Integer.parseInt(idRelationship));
            if (!getGraph().containVertice(userRelationship)) {
                userRelationship.setType(Type.INCLUIDO);
                getGraph().insertVertex(userRelationship);
                socialNetwork.getStatistics().addUsersIncluded(user, userRelationship);
                userRelationship.addListInterest(socialNetwork.interestsOfUser(userRelationship.getID(),
                        socialNetwork.getInterests()));
            }
        }

        getGraph().vertices().stream().filter(userVertex -> userVertex.element().getID() != idUser)
                .forEach(userVertex -> socialNetwork.insertEdge(user, userVertex.element()));

        return getGraph();
    }

    private DirectGraph<User, Relationship> getGraph() {
        return socialNetwork.getGraph();
    }

}
