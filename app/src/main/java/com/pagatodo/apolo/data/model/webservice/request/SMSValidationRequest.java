package com.pagatodo.apolo.data.model.webservice.request;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by Omar on 26/07/2017.
 */

public class SMSValidationRequest extends ModelPattern {

    private Request request;

    public SMSValidationRequest(String telefonoCelular)
    {
        request = new Request(telefonoCelular);
    }

    private class Request extends ModelPattern
    {
        private String TelefonoCelular;

        public Request(String telefonoCelular) {
            TelefonoCelular = telefonoCelular;
        }
    }
}
