package com.pa.proj2020.adts.graph;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class Logging {

    private static final Logging instance = new Logging();
    private final List<String> log;

    private Logging() {
        this.log = new ArrayList<>();
    }

    public static Logging getInstance() {
        return instance;
    }


    private String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return localDateTime.format(formatter);
    }

    public void addUser(int idUserAdded, int directRelationships, int interests) {
        String logCreated = "<" + this.formatLocalDateTime(LocalDateTime.now()) + "> | <" + idUserAdded +
                "> | <" + directRelationships + "> | <" + interests + ">";
        this.log.add(logCreated);
    }

    public void addRelationshipDirect(int idUserAdded, int idUserExistent, int interests) {
        String logCreated = "<" + this.formatLocalDateTime(LocalDateTime.now()) + "> | <" + idUserAdded +
                "> | <" + idUserExistent + "> | <" + interests + ">";
        this.log.add(logCreated);
    }

    public void addInterest(int idUserAdded, int idInterest) {
        String logCreated = "<" + this.formatLocalDateTime(LocalDateTime.now()) + "> | <" + idUserAdded +
                "> | <" + idInterest + ">";
        this.log.add(logCreated);
    }

    public void addRelationshipIndirect(int idUserAdded, int idUserIncluded, int idInterest) {
        String logCreated = "<" + this.formatLocalDateTime(LocalDateTime.now()) + "> | <" + idUserAdded +
                "> | <" + idUserIncluded + "> | <" + idInterest + ">";
        this.log.add(logCreated);
    }

    public void clearLogs() {
        this.log.clear();
    }

    @Override
    public String toString() {
        String s = "******************** Logging ********************";

        for (String logLine : this.log) {
            s = s + "\n" + logLine;
        }

        s = s + "\n******************** End ********************";

        return s;
    }


}
