package com.pagatodo.apolo.data.model.webservice.remoteconfig;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by jvazquez on 27/06/2017.
 */

public class ResponseRemoteConfig extends ModelPattern {
    private Data data = new Data();

    public ResponseRemoteConfig(){

    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
