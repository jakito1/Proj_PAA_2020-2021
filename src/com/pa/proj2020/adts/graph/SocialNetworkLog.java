package com.pa.proj2020.adts.graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SocialNetworkLog implements Serializable {
    private final Logging log = Logging.getInstance();

    public Logging getLog() {
        return log;
    }

    /**
     * Metodo que permite atualizar o Logging
     */
    public void updateLog() {
        try {
            new File("outputFiles/").mkdirs();
            FileWriter fileWriter = new FileWriter("outputFiles/LogFile " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss")) + ".log");
            fileWriter.write(log.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que retorna os interesses de um utilizador
     *
     * @param idUser representa o id do utilizador
     * @return a lista de interesses do utilizador fornecido
     */
    public List<Interest> interestsOfUser(int idUser, HashMap<Integer, Interest> thisInterests) {
        if (idUser < 0) {
            return null;
        }
        ArrayList<Interest> list = new ArrayList<>();

        for (Interest interest : thisInterests.values()) {
            if (interest.getIdsOfUsers().contains(String.valueOf(idUser))) {
                list.add(interest);
                log.addInterest(idUser, interest.getId());
            }
        }
        updateLog();

        return list;

    }
}