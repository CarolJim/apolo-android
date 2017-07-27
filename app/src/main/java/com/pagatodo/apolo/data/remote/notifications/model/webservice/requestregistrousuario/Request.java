package com.pagatodo.apolo.data.remote.notifications.model.webservice.requestregistrousuario;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by jvazquez on 18/04/2017.
 */

public class Request extends ModelPattern{
    private String email                 = "";
    private String idUsuarioPrograma     = "";
    private String nombre                = "";
    private String telefono              = "";
    private String tokenNotificacion     = "";

    public Request(){
    }

    public String getEmail() {
        return email;
    }

    public String getIdUsuarioPrograma() {
        return idUsuarioPrograma;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getTokenNotificacion() {
        return tokenNotificacion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdUsuarioPrograma(String idUsuarioPrograma) {
        this.idUsuarioPrograma = idUsuarioPrograma;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setTokenNotificacion(String tokenNotificacion) {
        this.tokenNotificacion = tokenNotificacion;
    }
}
