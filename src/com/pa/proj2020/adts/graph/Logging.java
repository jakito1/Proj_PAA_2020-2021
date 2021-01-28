package com.pa.proj2020.adts.graph;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * Método que retorna a instância de um Logging
     *
     * @return a instância de um Logging
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
        return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }


    /**
     * Método que permite gerar o Logging para a adição de um relacionamento direto
     *
     * @param idUserAdded    representa o id do utilizador adicionado
     * @param idUserExistent representa o id do utilizador existente
     * @param interests      representa o numero de interesses partilhados
     */
    public void addRelationshipDirect(int idUserAdded, int idUserExistent, int interests) {
        addToLog("<" + this.formatLocalDateTime(LocalDateTime.now()) + "> | <" + idUserAdded +
                "> | <" + idUserExistent + "> | <" + interests + ">");
    }

    /**
     * Método que permite gerar o Logging para a adição de um interesse
     *
     * @param idUserAdded representa o id do utilizador adicionado
     * @param idInterest  representa o id do interesse
     */
    public void addInterest(int idUserAdded, int idInterest) {
        addToLog("<" + this.formatLocalDateTime(LocalDateTime.now()) + "> | <" + idUserAdded +
                "> | <" + idInterest + ">");
    }

    private boolean addToLog(String logCreated) {
        return log.add(logCreated);
    }

    /**
     * Método que permite criar uma string para cada Logging
     *
     * @return um string para o Logging
     */
    @Override
    public String toString() {
        return log.stream().map(logLine -> "\n" + logLine).collect(Collectors
                .joining("", "******************** Logging ********************",
                        "\n******************** End ********************"));
    }


}
