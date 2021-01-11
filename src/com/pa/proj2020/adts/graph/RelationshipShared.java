package com.pa.proj2020.adts.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que permite criar uma Relacionamento Direto com Partilha de Interesses
 */
public class RelationshipShared implements Relationship {
    private final List<Interest> interests;

    /**
     * Cria um Relacionamento Direto com partilha de interesses
     * @param interests representa a lista de interesses
     */
    public RelationshipShared(List<Interest> interests) {
        this.interests = new ArrayList<>(interests);
    }

    /**
     * Método que permite adicionar um interesse à lista
     * @param interest representa o interesse que pretendemos adicionar
     */
    public void addInterest(Interest interest) {
        this.interests.add(interest);
    }

    /**
     * Método que permie remover um interesse da lista
     * @param interest representa o interesse que pretendemos remover
     */
    public void removeInterest(Interest interest) {
        this.interests.remove(interest);
    }

    /**
     * Método que permite obter a lista de interesses
     * @return a lista de interesses
     */
    public List<Interest> getInterests() {
        return interests;
    }

    /**
     * Mètodo que permite obter o numero de interesses da lista
     * @return o numero de interesses na lista
     */
    public int getNumberInterests() {
        return interests.size();
    }

    /**
     * Método que retorna uma string com a informação do relacionamento Direto com partilha de interesses
     * @return uma string com a informação do relacionamento direto com partilha de interesses
     */
    @Override
    public String toString() {
        return "";
    }
}
