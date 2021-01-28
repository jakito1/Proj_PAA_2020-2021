package com.pa.proj2020.adts.graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SocialNetworkLog implements Serializable {
    private static final Logging log = Logging.getInstance();

    public static Logging getLog() {
        return log;
    }

    /**
     * Metodo que permite atualizar o Logging
     */
    public static void updateLog() {
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


}