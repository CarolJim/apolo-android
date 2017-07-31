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

    public class Request extends ModelPattern
    {
        private String Nombre;
        private String DocumentoBase64;
        private int    Longitud;
        private int    IdTipoDocumento;
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

        public String getNombre() {
            return Nombre;
        }

        public void setNombre(String nombre) {
            Nombre = nombre;
        }

        public String getDocumentoBase64() {
            return DocumentoBase64;
        }

        public void setDocumentoBase64(String documentoBase64) {
            DocumentoBase64 = documentoBase64;
        }

        public int getLongitud() {
            return Longitud;
        }

        public void setLongitud(int longitud) {
            Longitud = longitud;
        }

        public int getIdTipoDocumento() {
            return IdTipoDocumento;
        }

        public void setIdTipoDocumento(int idTipoDocumento) {
            IdTipoDocumento = idTipoDocumento;
        }

        public String getFolio() {
            return Folio;
        }

        public void setFolio(String folio) {
            Folio = folio;
        }

        public String getIdCliente() {
            return IdCliente;
        }

        public void setIdCliente(String idCliente) {
            IdCliente = idCliente;
        }
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
