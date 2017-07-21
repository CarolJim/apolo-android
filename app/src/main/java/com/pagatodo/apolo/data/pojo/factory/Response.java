package com.pagatodo.apolo.data.pojo.factory;

import com.google.gson.Gson;
import com.pagatodo.apolo.data.pojo.webservice.Respuesta;

import java.io.Serializable;

/**
 * Factory that return object in jsonFormat
 * Created by jvazquez on 19/05/2017.
 */

public class Response extends ModelPattern {
    protected Respuesta respuesta = new Respuesta();

    public Response(){
        super();
    }

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }
}
