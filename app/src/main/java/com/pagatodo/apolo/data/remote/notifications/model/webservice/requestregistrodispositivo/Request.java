package com.pagatodo.apolo.data.remote.notifications.model.webservice.requestregistrodispositivo;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by jvazquez on 18/04/2017.
 */

public class Request extends ModelPattern {
    private String UDID                = "";
    private String marca               = "";
    private String modelo              = "";
    private String sistemaOperativo    = "";
    private String tokenNotificacion   = "";
    private String versionApp          = "";
    private String versionSO           = "";

    public Request(){
    }

    public String getTokenNotificacion() {
        return tokenNotificacion;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public String getUDID() {
        return UDID;
    }

    public String getVersionApp() {
        return versionApp;
    }

    public String getVersionSO() {
        return versionSO;
    }

    public void setTokenNotificacion(String tokenNotificacion) {
        this.tokenNotificacion = tokenNotificacion;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public void setUDID(String UDID) {
        this.UDID = UDID;
    }

    public void setVersionApp(String versionApp) {
        this.versionApp = versionApp;
    }

    public void setVersionSO(String versionSO) {
        this.versionSO = versionSO;
    }
}
