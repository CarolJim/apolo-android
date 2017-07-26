package com.pagatodo.apolo.data.remote.notifications.model.webservice.response;

/**
 * Created by jvazquez on 18/04/2017.
 */

public class ResponseRegistroUsuario extends GenericResponseMega {
    public String idUsuarioMega = "";

    public ResponseRegistroUsuario(){
        super();
    }

    public String getIdUsuarioMega() {
        return idUsuarioMega;
    }

    public void setIdUsuarioMega(String idUsuarioMega) {
        this.idUsuarioMega = idUsuarioMega;
    }
}
