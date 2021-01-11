package com.pa.proj2020.adts.graph;

import java.io.Serializable;

/**
 * Classe com o tipo enumerado que permite fazer a atribuição do tipo a um Utilizador
 * Um utilizador poderá ser do tipo Adicionado ou do tipo Incluido
 */
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
