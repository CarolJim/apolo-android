package com.pagatodo.apolo.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jvazquez on 28/07/2017.
 */

public class FormularioAfiliacion {
    private String telefonoMovil = "";
    private String telefonoCasa= "";
    private List<Documento> documentos = new ArrayList<>();
    private String folio = "";

    public FormularioAfiliacion(List<Documento> documentos){
        this.documentos.addAll(documentos);
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getTelefonoCasa() {
        return telefonoCasa;
    }

    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }
}
