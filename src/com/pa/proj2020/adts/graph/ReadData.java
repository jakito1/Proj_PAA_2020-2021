package com.pa.proj2020.adts.graph;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;


public class ReadData {


    /**
     * Retorna uma coleção HashMap com a Key sendo o primeiro elemento e values a lista de valores associados a esse elemento
     *
     * @return coleção HashMap
     */
    public static HashMap<String, ArrayList<String>> readData(String filename) {
        if (filename == null || filename.equals("")) return null;

        filename = "inputFiles\\" + filename;

        HashMap<String, ArrayList<String>> data = new HashMap<>();
        File file = new File(filename);

        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        for (String line : lines) {
            String[] array = line.split(";", -1);
            data.put(array[0], new ArrayList<>());
            IntStream.range(1, array.length).forEach(i -> data.get(array[0])
                    .add(array[i].replace("'", "")));
        }
        return data;
    }

}
