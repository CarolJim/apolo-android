package com.pagatodo.apolo.data.model;

/**
 * Created by jvazquez on 28/07/2017.
 */

public class Documento {
    private String Nombre           = "";
    private String DocumentoBase64  = "";
    private int    Longitud         = 0;
    private int    IdTipoDocumento  = 0;
    private String Folio            = "";
    private String IdCliente        = "";
    private boolean uploaded      = false;

    public Documento(){

    }

    public Documento(int idTipoDocumento, String nombre, String documentoBase64, int longitud, String folio, String idCliente) {
        Nombre = nombre;
        DocumentoBase64 = documentoBase64;
        Longitud = longitud;
        IdTipoDocumento = idTipoDocumento;
        Folio = folio;
        IdCliente = idCliente;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
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
