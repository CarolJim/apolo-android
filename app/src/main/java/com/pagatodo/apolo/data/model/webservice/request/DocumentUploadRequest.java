package com.pagatodo.apolo.data.model.webservice.request;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by Omar on 26/07/2017.
 */

public class DocumentUploadRequest extends ModelPattern {

    private Request request;

    public DocumentUploadRequest(String nombre, String documentBase64, int longitud, int idTipoDocumento, String folio, String idCliente)
    {
        request = new Request(nombre, documentBase64, longitud, idTipoDocumento, folio, idCliente);
    }

    private class Request extends ModelPattern
    {
        private String Nombre;
        private String DocumentoBase64;
        private int Longitud;
        private int IdTipoDocumento;
        private String Folio;
        private String IdCliente;

        public Request(String nombre, String documentoBase64, int longitud, int idTipoDocumento, String folio, String idCliente) {
            Nombre = nombre;
            DocumentoBase64 = documentoBase64;
            Longitud = longitud;
            IdTipoDocumento = idTipoDocumento;
            Folio = folio;
            IdCliente = idCliente;
        }
    }
}
