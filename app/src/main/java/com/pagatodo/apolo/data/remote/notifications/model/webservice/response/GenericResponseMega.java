package com.pagatodo.apolo.data.remote.notifications.model.webservice.response;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by jvazquez on 18/04/2017.
 */

public class GenericResponseMega extends ModelPattern {
    private int codigoRespuesta          =  0;
    private String descripcionRespuesta  = "";
    private String tokenAutenticacion    = "";

    public GenericResponseMega(){
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    public String getTokenAutenticacion() {
        return tokenAutenticacion;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }

    public void setTokenAutenticacion(String tokenAutenticacion) {
        this.tokenAutenticacion = tokenAutenticacion;
    }
}
