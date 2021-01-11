package com.pa.proj2020.adts.graph;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MemoryPersistence {

    SocialNetwork socialNetwork;

    public MemoryPersistence(SocialNetwork socialNetwork){
        this.socialNetwork = socialNetwork;
    }

    protected void exportSerialization() {
        try {
            FileOutputStream fileOut = new FileOutputStream("outputFiles/exportSerialization");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(socialNetwork.getGraph());
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    protected DirectGraph<User, Relationship> importSerialization() {
        DirectGraph<User, Relationship> temp = null;
        try {
            FileInputStream fileIn = new FileInputStream("outputFiles/exportSerialization");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            temp = (DirectGraph<User, Relationship>) in.readObject();
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
            FileOutputStream fileOut = new FileOutputStream("outputFiles/exportSerialization");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(socialNetwork.getGraph());
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    protected DirectGraph<User, Relationship> importJSON() {
        DirectGraph<User, Relationship> temp = null;
        try {
            FileInputStream fileIn = new FileInputStream("outputFiles/exportSerialization");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            temp = (DirectGraph<User, Relationship>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return temp;
    }

}
