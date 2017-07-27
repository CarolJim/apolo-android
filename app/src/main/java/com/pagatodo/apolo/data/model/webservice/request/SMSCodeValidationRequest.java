package com.pagatodo.apolo.data.model.webservice.request;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by Omar on 26/07/2017.
 */

public class SMSCodeValidationRequest extends ModelPattern {

    private Request request;

    public SMSCodeValidationRequest(String telefonoCelular, String codigo)
    {
        request = new Request(telefonoCelular,codigo);
    }


    private class Request extends ModelPattern
    {
        private String TelefonoCelular;
        private String Codigo;

        public Request(String telefonoCelular, String codigo) {
            TelefonoCelular = telefonoCelular;
            Codigo = codigo;
        }
    }

}
