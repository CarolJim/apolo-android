package com.pagatodo.apolo.data.remote.notifications.model.webservice.mega;

/**
 * Created by Omar on 05/07/2017.
 */

public class GetPushStatus extends MegaBaseModel {

    private boolean isActiveNotification;

    public boolean isActiveNotification() {
        return isActiveNotification;
    }
}
