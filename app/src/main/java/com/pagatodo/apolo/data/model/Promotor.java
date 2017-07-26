package com.pagatodo.apolo.data.model;

import com.pagatodo.apolo.data.pojo.factory.ModelPattern;

/**
 * Created by jvazquez on 26/07/2017.
 */

public class Promotor extends ModelPattern {
    private boolean Activo           = false;
    private String  ApellidoMaterno  = "";
    private String  ApellidoPaterno  = "";
    private int     ID_Promotor      =  0;
    private String  Nombre           = "";
    private String  Promotor         = "";

    public Promotor(){

    }
    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        ApellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        ApellidoPaterno = apellidoPaterno;
    }

    public int getID_Promotor() {
        return ID_Promotor;
    }

    public void setID_Promotor(int ID_Promotor) {
        this.ID_Promotor = ID_Promotor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPromotor() {
        return Promotor;
    }

    public void setPromotor(String promotor) {
        Promotor = promotor;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean activo) {
        Activo = activo;
    }
}
