package com.pagatodo.apolo.data.model.webservice.request;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by Omar on 26/07/2017.
 */

public class CreditRequestRegisterRequest extends ModelPattern {

    private Request request;

    public CreditRequestRegisterRequest(String telefonoFijo, String telefonoMovil)
    {
        request = new Request(telefonoFijo, telefonoMovil);
    }

    private class Request extends ModelPattern
    {
        private String TelefonoFijo;
        private String TelefonoMovil;

        public Request(String telefonoFijo, String telefonoMovil) {
            TelefonoFijo = telefonoFijo;
            TelefonoMovil = telefonoMovil;
        }
    }

}
