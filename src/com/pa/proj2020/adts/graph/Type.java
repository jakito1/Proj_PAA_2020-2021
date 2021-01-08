package com.pa.proj2020.adts.graph;

public enum Type {

    ADICIONADO, INCLUIDO;


    @Override
    public String toString() {
        switch (this) {
            case ADICIONADO:
                return "Adicionado";
            case INCLUIDO:
                return "Incluido";
            default:
                return "";
        }

    }

}
