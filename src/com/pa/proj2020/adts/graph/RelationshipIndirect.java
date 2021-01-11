package com.pa.proj2020.adts.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe que permite criar um Relacionamento Indireto
 */
public class RelationshipIndirect implements Relationship {

    private final Set<Interest> listOfInterests;
    private final User[] users;
    private int number;

    /**
     * Cria um relacionamento indireto
     */
    public RelationshipIndirect() {
        listOfInterests = new HashSet<>();
        users = new User[2];
    }

    /**
     * Cria um relacionamento indireto e adiciona um interesse ao mesmo
     *
     * @param interest representa o interesse que pretendemos adicionar ao relacionamento
     */
    public RelationshipIndirect(Interest interest) {
        this();
        this.addInterest(interest);
    }

    /**
     * Cria um relacionamento indireto e adiciona uma colecao ao mesmo
     *
     * @param interests representa uma colecao de interesses que pretendemos adicionar ao relacionamento
     */
    public RelationshipIndirect(Collection<Interest> interests) {
        this();
        this.listOfInterests.addAll(interests);
    }

    /**
     * Método responsável por atribuir o utilizar inbound e outbound do relacionamento
     *
     * @param inboundUser  representa o utilizador inbound
     * @param outboundUser representa o utilizador outbound
     */
    public void setUsers(User inboundUser, User outboundUser) {
        if (inboundUser == null || outboundUser == null) return;
        users[0] = inboundUser;
        users[1] = outboundUser;

    }

    /**
     * Método que permite adicinar um interesse a lista de interesses associados
     *
     * @param interest representa o interesse que pretendemos adicionar a lista
     */
    public void addInterest(Interest interest) {
        listOfInterests.add(interest);
    }

    /**
     * Método que permite remover um interesse da lista de interesses associados
     *
     * @param interest representa o interesse que pretendemos remover da lista
     */
    public void removeInterest(Interest interest) {
        listOfInterests.remove(interest);
    }

    /**
     * Método que permite obter uma lista de interesses
     *
     * @return uma lista de interesses
     */
    public Set<Interest> getListOfInterests() {
        return listOfInterests;
    }


    public String getListOfInterestsString() {
        String s = "List Of Interest\n";
        for (Interest interest : listOfInterests) {
            s = s + interest.toString() + "\n";
        }

        return s;
    }

    /**
     * Método que permite obter o numero de interesses da lista
     *
     * @return o numero de interesses da lista
     */
    public int getNumber() {
        return listOfInterests.size();
    }

    /**
     * Método que retorna o utilizador inbound
     *
     * @return o utilizador inbound
     */
    public User getInboundUser() {
        return users[0];
    }

    /**
     * Método que retorna o utilizador outbound
     *
     * @return o utilizador outbound
     */
    public User getOutboundUser() {
        return users[1];
    }

    /**
     * Método que retorna os utilizadores inbound e outbound
     *
     * @return os utilizadores inbound e outbound
     */
    public User[] getUsers() {
        return users;
    }

    /**
     * Método que retorna uma string do relacionamento indireto
     *
     * @return uma string com o relacionamento indireto
     */
    @Override
    public String toString() {
        return "[" + getNumber() +
                ']';
    }
}
