package com.pagatodo.apolo.data.model.webservice.request;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by Omar on 26/07/2017.
 */

public class CreditRequestRegisterRequest extends ModelPattern {

    private Request request;

    public CreditRequestRegisterRequest(String telefonoFijo, String telefonoMovil, int idIniciativa, int idTienda, String IDP)
    {
        request = new Request(telefonoFijo, telefonoMovil, idIniciativa, idTienda, IDP);
    }

    private class Request extends ModelPattern
    {
        private String TelefonoFijo;
        private String TelefonoMovil;
        private int ID_Iniciativa;
        private int ID_Tienda;
        private String IDP;

        public Request(String telefonoFijo, String telefonoMovil, int ID_Iniciativa, int ID_Tienda, String IDP) {
            TelefonoFijo = telefonoFijo;
            TelefonoMovil = telefonoMovil;
            this.ID_Iniciativa = ID_Iniciativa;
            this.ID_Tienda = ID_Tienda;
            this.IDP = IDP;
        }
    }

}
