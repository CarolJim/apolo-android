package com.pagatodo.apolo.data.model.factory;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by jvazquez on 19/05/2017.
 */

public abstract class ModelPattern implements Serializable {
    @Override
    public String toString() {
        //return super.toString();
        return new Gson().toJson(this);
    }
}
