package com.pagatodo.apolo.data.remote.notifications.model.webservice.requestinbox;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by jvazquez on 14/06/2017.
 */

public class Request extends ModelPattern {
    private int idDireccion = 0;
    private int idMensaje   = 0;

    public Request(){
        super();
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }
}
