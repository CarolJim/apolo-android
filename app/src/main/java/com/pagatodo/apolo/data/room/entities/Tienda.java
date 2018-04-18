package com.pagatodo.apolo.data.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

@Entity
public class Tienda extends ModelPattern {

    @ColumnInfo(name = "activo")
    private boolean Activo;

    @ColumnInfo(name = "calle")
    private String Calle;

    @ColumnInfo(name = "codigo_postal")
    private String CodigoPostal;

    @ColumnInfo(name = "colonia")
    private String Colonia;

    @ColumnInfo(name = "delegacion")
    private String Delegacion;

    @ColumnInfo(name = "estado")
    private String Estado;

    @ColumnInfo(name = "id_iniciativa")
    private int ID_Iniciativa;

    @ColumnInfo(name = "id_tienda")
    @PrimaryKey
    private int ID_Tienda;

    @ColumnInfo(name = "latitud")
    private double Latitud;

    @ColumnInfo(name = "longitud")
    private double Longitud;

    @ColumnInfo(name = "no_exterior")
    private String NoExterior;

    @ColumnInfo(name = "no_interior")
    private String NoInterior;

    @ColumnInfo(name = "nombre")
    private String Nombre;

    @ColumnInfo(name = "usuario")
    private String Usuario;

    public Tienda() {
    }

    public Tienda(boolean activo, String calle, String codigoPostal, String colonia, String delegacion, String estado, int ID_Iniciativa, int ID_Tienda, double latitud, double longitud, String noExterior, String noInterior, String nombre, String usuario) {
        Activo = activo;
        Calle = calle;
        CodigoPostal = codigoPostal;
        Colonia = colonia;
        Delegacion = delegacion;
        Estado = estado;
        this.ID_Iniciativa = ID_Iniciativa;
        this.ID_Tienda = ID_Tienda;
        Latitud = latitud;
        Longitud = longitud;
        NoExterior = noExterior;
        NoInterior = noInterior;
        Nombre = nombre;
        Usuario = usuario;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean activo) {
        Activo = activo;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        CodigoPostal = codigoPostal;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getDelegacion() {
        return Delegacion;
    }

    public void setDelegacion(String delegacion) {
        Delegacion = delegacion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public int getID_Iniciativa() {
        return ID_Iniciativa;
    }

    public void setID_Iniciativa(int ID_Iniciativa) {
        this.ID_Iniciativa = ID_Iniciativa;
    }

    public int getID_Tienda() {
        return ID_Tienda;
    }

    public void setID_Tienda(int ID_Tienda) {
        this.ID_Tienda = ID_Tienda;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatitud(double latitud) {
        Latitud = latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
    }

    public String getNoExterior() {
        return NoExterior;
    }

    public void setNoExterior(String noExterior) {
        NoExterior = noExterior;
    }

    public String getNoInterior() {
        return NoInterior;
    }

    public void setNoInterior(String noInterior) {
        NoInterior = noInterior;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }
}
