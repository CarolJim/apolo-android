package com.pagatodo.apolo.data.remote.notifications.model.webservice.updateState;


import com.pagatodo.apolo.data.pojo.factory.ModelPattern;

/**
 * Created by jvazquez on 14/06/2017.
 */

public class Request extends ModelPattern {

    private String sistemaOperativo;
    private boolean activateNotification;

    public Request (String sistemaOperativo)
    {
        this.sistemaOperativo = sistemaOperativo;
    }
    public void setActivateNotification(boolean activateNotification) {
        this.activateNotification = activateNotification;
    }
}
