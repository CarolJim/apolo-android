package com.pagatodo.apolo.data.remote.notifications.model.webservice.response;

/**
 * Created by jvazquez on 18/04/2017.
 */

public class ResponseRegistroDispositivo extends GenericResponseMega {
    private String idDispositivo =  "";

    public ResponseRegistroDispositivo(){
        super();
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }
}
