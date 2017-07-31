package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.factory.Respuesta;

/**
 * Created by Omar on 28/07/2017.
 */

public class GeneralServiceResponse extends ModelPattern {

    private Respuesta Respuesta;

    public Respuesta getRespuesta() {
        return Respuesta;
    }
}
