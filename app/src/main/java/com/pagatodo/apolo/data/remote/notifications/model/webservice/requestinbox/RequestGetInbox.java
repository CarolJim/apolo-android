package com.pagatodo.apolo.data.remote.notifications.model.webservice.requestinbox;


import com.pagatodo.apolo.data.pojo.factory.ModelPattern;

/**
 * Created by jvazquez on 14/06/2017.
 */

public class RequestGetInbox extends ModelPattern {

    private Request request = new Request();

    public RequestGetInbox(){
        super();
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
