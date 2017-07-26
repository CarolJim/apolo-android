package com.pagatodo.apolo.data.remote.notifications.model.webservice.updateState;


import com.pagatodo.apolo.data.pojo.factory.ModelPattern;

/**
 * Created by Omar on 05/07/2017.
 */

public class RequestUpdateStatus extends ModelPattern {

    private Request request = new Request("Android");

    public Request getRequest() {
        return request;
    }
}
