package com.pa.proj2020.adts.graph;

public class User {

    private String name;
    private int id;

    public User(String name, int id){
        if(name == null || id <=0){
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public int getID(){
        return this.id;
    }

}
