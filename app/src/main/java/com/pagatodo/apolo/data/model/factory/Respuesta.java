package com.pagatodo.apolo.data.model.factory;

/**
 * Created by Omar on 26/07/2017.
 */

public class Respuesta extends ModelPattern {
    private int Codigo;
    private boolean Exito;
    private String Mensaje;

    public int getCodigo() {
        return Codigo;
    }

    public boolean isExito() {
        return Exito;
    }

    public String getMensaje() {
        return Mensaje;
    }
}
