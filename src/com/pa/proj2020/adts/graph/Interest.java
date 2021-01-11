package com.pa.proj2020.adts.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pela gestão dos interesses associados a um utilizador
 */
public class Interest implements Serializable {
    private final int id;
    private final String name;
    private final ArrayList<String> idsOfUsers;

    /**
     * Cria um Interest com o id e o nome
     * @param id representa o id de um interest
     * @param name representa o nome de um interest
     */
    public Interest(int id, String name) {
        this.id = id;
        this.name = name;
        this.idsOfUsers = new ArrayList<>();
    }

    /**
     * Cria um Interest com o id, nome e lista de ids dos utilizadores com esse interest
     * @param id representa o id de um interest
     * @param name representa o nome de um interest
     * @param ids representa a lista dos ids dos utilizadores com esse interest
     */
    public Interest(int id, String name, List<String> ids) {
        this(id, name);
        idsOfUsers.addAll(ids);
    }

    public void addIdUser(String id) {
        this.idsOfUsers.add(id);
    }

    public void removeIdUser(String id) {
        this.idsOfUsers.remove(id);
    }

    /**
     * Método que retorna o id de um interest
     * @return o id do interest
     */
    public int getId() {
        return id;
    }

    /**
     * Método que retorna o nome do interest
     * @return o nome do interest
     */
    public String getName() {
        return name;
    }

    /**
     * Método que retorna uma lista com os ids dos utilizadores
     * @return uma lista com os ids dos utilizadores
     */
    public ArrayList<String> getIdsOfUsers() {
        return idsOfUsers;
    }

    /**
     * Método que retorna uma string com o nome de um interest
     * @return uma string com o nome de um interest
     */
    @Override
    public String toString() {
        return this.name;
    }
}
