package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.Solicitud;
import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.factory.Respuesta;

/**
 * Created by Omar on 27/07/2017.
 */

public class CreditRequestRegisterResponse extends ModelPattern {

    private Respuesta Respuesta;
    private Solicitud Solicitud;

    public Respuesta getRespuesta() {
        return Respuesta;
    }

    public Solicitud getSolicitud() {
        return Solicitud;
    }
}
