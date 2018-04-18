package com.pagatodo.apolo.data.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

@Entity
public class Iniciativa extends ModelPattern {

    @ColumnInfo(name = "id_iniciativa")
    @PrimaryKey
    private int ID_Iniciativa;

    @ColumnInfo(name = "activo")
    private boolean Activo;

    @ColumnInfo(name = "descripcion")
    private String Descripcion;

    @ColumnInfo(name = "usuario")
    private String Usuario;

    public Iniciativa() {
    }

    public Iniciativa(int ID_Iniciativa, boolean activo, String descripcion, String usuario) {
        this.ID_Iniciativa = ID_Iniciativa;
        Activo = activo;
        Descripcion = descripcion;
        Usuario = usuario;
    }

    public int getID_Iniciativa() {
        return ID_Iniciativa;
    }

    public void setID_Iniciativa(int ID_Iniciativa) {
        this.ID_Iniciativa = ID_Iniciativa;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean activo) {
        Activo = activo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }
}
