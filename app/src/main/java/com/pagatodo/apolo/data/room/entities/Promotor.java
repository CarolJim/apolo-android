package com.pagatodo.apolo.data.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by jvazquez on 26/07/2017.
 * Modified by cjimenez on 25/02/2019.
 */
@Entity
public class Promotor extends ModelPattern {

    @ColumnInfo(name = "activo")
    private boolean Activo = false;

    @ColumnInfo(name = "apellido_materno")
    private String ApellidoMaterno = "";

    @ColumnInfo(name = "apellido_paterno")
    private String ApellidoPaterno = "";

    @ColumnInfo(name = "id_promotor")
    @PrimaryKey
    private int ID_Promotor = 0;

    @ColumnInfo(name = "nombre")
    private String Nombre = "";

    @ColumnInfo(name = "promotor")
    private String Promotor = "";

    @ColumnInfo(name = "reset_contraseña")
    private boolean ResetContraseña = true;

    @ColumnInfo(name = "usuario_valido")
    private boolean UsuarioValido = true;

    public Promotor() {

    }

    public Promotor(boolean activo, String apellidoMaterno, String apellidoPaterno, int ID_Promotor, String nombre, String promotor, boolean resetContraseña, boolean usuarioValido) {
        Activo = activo;
        ApellidoMaterno = apellidoMaterno;
        ApellidoPaterno = apellidoPaterno;
        this.ID_Promotor = ID_Promotor;
        Nombre = nombre;
        Promotor = promotor;
        ResetContraseña = resetContraseña;
        UsuarioValido = usuarioValido;
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

    public boolean isResetContraseña(){
        return ResetContraseña;
    }

    public void setResetContraseña(boolean resetContraseña){
        ResetContraseña = resetContraseña;
    }

    public boolean isUsuarioValido(){
        return UsuarioValido;
    }

    public void setUsuarioValido(boolean usuarioValido){
        UsuarioValido = usuarioValido;
    }
}
