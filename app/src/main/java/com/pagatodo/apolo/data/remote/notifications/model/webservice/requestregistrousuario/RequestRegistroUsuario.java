package com.pagatodo.apolo.data.remote.notifications.model.webservice.requestregistrousuario;

import com.pagatodo.apolo.data.pojo.factory.ModelPattern;

/**
 * Created by jvazquez on 18/04/2017.
 */

public class RequestRegistroUsuario extends ModelPattern {
    private Request request = new Request();

    public RequestRegistroUsuario(){
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
