package com.pagatodo.apolo.data.model.webservice.request;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by FranciscoManzo on 22/12/2017.
 */

public class CheckIdpRequest extends ModelPattern {

    private String IDP;

    public CheckIdpRequest(String idp)
    {
        IDP = idp;
    }

 /*   private Request request;
    private String IDP;

    public CheckIdpRequest(String idp)
    {
        request = new Request(idp);
    }

    private class Request extends ModelPattern
    {
        private String IDP;

        public Request(String idp) {
            IDP = idp;
        }
    }*/
}