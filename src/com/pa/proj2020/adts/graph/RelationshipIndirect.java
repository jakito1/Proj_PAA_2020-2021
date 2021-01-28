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
     * Método que permite adicinar um interesse a lista de interesses associados
     *
     * @param interest representa o interesse que pretendemos adicionar a lista
     */
    public void addInterest(Interest interest) {
        listOfInterests.add(interest);
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
        StringBuilder s = new StringBuilder("List Of Interest\n");
        for (Interest interest : listOfInterests) {
            s.append(interest.toString()).append("\n");
        }

        return s.toString();
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
