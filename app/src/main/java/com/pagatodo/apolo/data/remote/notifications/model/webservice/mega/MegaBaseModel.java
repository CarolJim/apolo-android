package com.pagatodo.apolo.data.remote.notifications.model.webservice.mega;


import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by Omar on 05/07/2017.
 */

public class MegaBaseModel extends ModelPattern {

    private int codigoRespuesta;
    private String descripcionRespuesta;

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }
}
