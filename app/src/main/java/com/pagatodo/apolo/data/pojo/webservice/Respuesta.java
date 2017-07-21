package com.pagatodo.apolo.data.pojo.webservice;

import com.pagatodo.apolo.data.pojo.factory.ModelPattern;

/**
 * Created by jvazquez on 19/05/2017.
 */

public class Respuesta extends ModelPattern {
    private int codigoRespuesta     =  0;
    private String mensaje          = "";

    public Respuesta(){
        super();
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
