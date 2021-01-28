package com.pa.proj2020.adts.graph;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;

public class MemoryPersistence {

    SocialNetwork socialNetwork;

    public MemoryPersistence(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    protected void exportSerialization() {
        try {
            FileOutputStream fileOut = new FileOutputStream("outputFiles/exportSerialization");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(socialNetwork.getVertices());
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    protected DirectGraph<User, Relationship> importSerialization() {
        DirectGraph<User, Relationship> temp = new DirectGraph<>();
        HashMap<User, Vertex<User>> hashMap;
        try {
            FileInputStream fileIn = new FileInputStream("outputFiles/exportSerialization");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            hashMap = (HashMap<User, Vertex<User>>) in.readObject();
            temp = new DirectGraph<>(hashMap);
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return temp;
    }

    protected void exportJSON() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileOutputStream fileOut = new FileOutputStream("outputFiles/exportJSON.json");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gson.toJson(socialNetwork.getVertices()));
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
