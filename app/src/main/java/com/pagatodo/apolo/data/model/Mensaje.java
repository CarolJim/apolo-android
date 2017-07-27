package com.pagatodo.apolo.data.model;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by jvazquez on 26/07/2017.
 */

public class Mensaje extends ModelPattern {

    private String bgContenido          =  "";
    private String bgTitulo             =  "";
    private int codigoRespuesta         =   0;
    private String contenido            =  "";
    private String descripcionRespuesta =  "";
    private String fechaNotificacion    =  "";
    private int idEstatus               =   0;
    private int idMensaje               =   0;
    private int idTipoMensaje           =   0;
    private String titulo               =  "";
    private String urlImagen            =  "";



    public Mensaje(){

    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public int getIdTipoMensaje() {
        return idTipoMensaje;
    }

    public String getBgContenido() {
        return bgContenido;
    }

    public String getBgTitulo() {
        return bgTitulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String getFechaNotificacion() {
        return fechaNotificacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public void setBgContenido(String bgContenido) {
        this.bgContenido = bgContenido;
    }

    public void setBgTitulo(String bgTitulo) {
        this.bgTitulo = bgTitulo;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public void setFechaNotificacion(String fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public void setIdTipoMensaje(int idTipoMensaje) {
        this.idTipoMensaje = idTipoMensaje;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
