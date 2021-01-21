package com.pa.proj2020.adts.graph;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pela criação do Logging. Faz uso do padrão Singleton
 */
public final class Logging {

    private static final Logging instance = new Logging();
    private final List<String> log;

    /**
     * Cria um novo Logging
     */
    private Logging() {
        this.log = new ArrayList<>();
    }

    /**
     * Método que retorna a instancia de um Logging
     *
     * @return a instancia de um Logging
     */
    public static Logging getInstance() {
        return instance;
    }


    /**
     * Método auxiliar responsável por formatar a data
     *
     * @param localDateTime representa a data que pretendemos formatar
     * @return uma string com a data formatada
     */
    private String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return localDateTime.format(formatter);
    }

    /**
     * Método que permite gerar o Logging para a adição de um utilizador
     *
     * @param idUserAdded         representa o id de um utilizador adicionado
     * @param directRelationships representa o numero de relacionamentos diretos
     * @param interests           representa o numero de interesses
     */
    public void addUser(int idUserAdded, int directRelationships, int interests) {
        String logCreated = "<" + this.formatLocalDateTime(LocalDateTime.now()) + "> | <" + idUserAdded +
                "> | <" + directRelationships + "> | <" + interests + ">";
        this.log.add(logCreated);
    }

    /**
     * Método que permite gerar o Logging para a adição de um relacionamento direto
     *
     * @param idUserAdded    representa o id do utilizador adicionado
     * @param idUserExistent representa o id do utilizador existente
     * @param interests      representa o numero de interesses partilhados
     */
    public void addRelationshipDirect(int idUserAdded, int idUserExistent, int interests) {
        String logCreated = "<" + this.formatLocalDateTime(LocalDateTime.now()) + "> | <" + idUserAdded +
                "> | <" + idUserExistent + "> | <" + interests + ">";
        this.log.add(logCreated);
    }

    /**
     * Método que permite gerar o Logging para a adição de um interesse
     *
     * @param idUserAdded representa o id do utilizador adicionado
     * @param idInterest  representa o id do interesse
     */
    public void addInterest(int idUserAdded, int idInterest) {
        String logCreated = "<" + this.formatLocalDateTime(LocalDateTime.now()) + "> | <" + idUserAdded +
                "> | <" + idInterest + ">";
        this.log.add(logCreated);
    }

    /**
     * Método que permite gerar o Logging para a adição de um relacionamento indireto
     *
     * @param idUserAdded    representa o id do utilizador adicionado
     * @param idUserIncluded representa o id do utilizador incluido
     * @param idInterest     representa o id do interesse
     */
    public void addRelationshipIndirect(int idUserAdded, int idUserIncluded, int idInterest) {
        String logCreated = "<" + this.formatLocalDateTime(LocalDateTime.now()) + "> | <" + idUserAdded +
                "> | <" + idUserIncluded + "> | <" + idInterest + ">";
        this.log.add(logCreated);
    }

    public void clearLogs() {
        this.log.clear();
    }

    /**
     * Método que permite criar uma string para cada Logging
     *
     * @return um string para o Logging
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("******************** Logging ********************");

        for (String logLine : this.log) {
            s.append("\n").append(logLine);
        }

        s.append("\n******************** End ********************");

        return s.toString();
    }


}
