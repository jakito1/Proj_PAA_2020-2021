package com.pa.proj2020.adts.graph;

import java.io.Serializable;

public enum Type implements Serializable {

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
