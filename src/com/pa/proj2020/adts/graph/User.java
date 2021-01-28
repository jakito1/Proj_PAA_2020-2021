package com.pa.proj2020.adts.graph;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe responsável pela criação de um Utilizador
 */
public class User implements Serializable {

    private final String name;
    private final int id;
    private final Set<Interest> interests;
    private Type type; //adicionado ou incluido

    /**
     * Cria um Utilizador através do nome, id e tipo
     *
     * @param name representa o nome do utilizador
     * @param id   representa o id do utilizador
     * @param type representa o tipo do utilizador
     */
    public User(String name, int id, Type type) {
        if (name == null || id <= 0) {
            throw new IllegalArgumentException();
        }
        this.interests = new HashSet<>();
        this.name = name;
        this.id = id;
        this.type = type;
    }

    /**
     * Cria um Utilizador através do nome, id, tipo e uma lista de interesses
     *
     * @param name      representa o nome do utilizador
     * @param id        representa o id do utilizador
     * @param type      representa o tipo do utilizador
     * @param interests representa a lista de interesses associada a esse utilizador
     */
    public User(String name, int id, Type type, Collection<Interest> interests) {
        if (name == null || id <= 0) {
            throw new IllegalArgumentException();
        }
        this.interests = new HashSet<>(interests);
        this.name = name;
        this.id = id;
        this.type = type;
    }

    /**
     * Método que devolve a lista de interesses associados a um utilizador
     *
     * @return um hashset de interesses associados a um utilizador
     */
    public Set<Interest> getInterests() {
        return interests;
    }

    /**
     * Método que permite adicionar um interesse a um Utilizador
     *
     * @param interest representa o interesse que pretendemos adicionar a um utilizador
     */
    public void addInterest(Interest interest) {
        this.interests.add(interest);
    }

    /**
     * Método que permite adicionar uma lista de interesses a um Utilizador
     *
     * @param interests representa a lista de interesses que pretendemos adicionar a um utiliza
     */
    public void addListInterest(List<Interest> interests) {
        this.interests.addAll(interests);
    }

    /**
     * Método que permite retornar o nome do utilizador
     *
     * @return nome do utilizador
     */
    public String getName() {
        return this.name;
    }

    /**
     * Método que permite retornar o id do utilizador
     *
     * @return id do utilizador
     */
    public int getID() {
        return this.id;
    }

    /**
     * Método que permite retornar um string com o id e o nome do utilizador
     *
     * @return uma string com o id e o nome do utilizador
     */
    @Override
    public String toString() {
        return this.id + " : " + this.name;
    }

    /**
     * Método que permite retornar o tipo do utilizador
     *
     * @return tipo do utilizador
     */
    public Type getType() {
        return type;
    }

    /**
     * Método que permite modificar o tipo de utilizador
     *
     * @param type representa o novo tipo que pretendemos dar ao utilizador
     */
    public void setType(Type type) {
        this.type = type;
    }
}
