package com.pagatodo.apolo.data.remote.notifications.model.webservice.requestregistrodispositivo;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by jvazquez on 18/04/2017.
 */

public class RequestRegistroDispositivo extends ModelPattern{
    private Request request = new Request();

    public RequestRegistroDispositivo(){

    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
