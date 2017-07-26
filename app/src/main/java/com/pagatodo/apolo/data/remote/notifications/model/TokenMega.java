package com.pagatodo.apolo.data.remote.notifications.model;

import com.pagatodo.apolo.data.pojo.factory.ModelPattern;

/**
 * Created by jvazquez on 14/06/2017.
 */

public class TokenMega extends ModelPattern {
    private String idUsuarioMega = "";
    private String idDispositivo = "";
    private String tokenAutenticacion = "";
    private String tokenFCM = "";
    private String fecha = "";
    private boolean isUpdated = false;

    public TokenMega() {
    }

    public String getFecha() {
        return fecha;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public String getIdUsuarioMega() {
        return idUsuarioMega;
    }

    public String getTokenAutenticacion() {
        return tokenAutenticacion;
    }

    public String getTokenFCM() {
        return tokenFCM;
    }

    public boolean getIsUpdated() {
        return isUpdated;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public void setIdUsuarioMega(String idUsuarioMega) {
        this.idUsuarioMega = idUsuarioMega;
    }

    public void setTokenAutenticacion(String tokenAutenticacion) {
        this.tokenAutenticacion = tokenAutenticacion;
    }

    public void setTokenFCM(String tokenFCM) {
        this.tokenFCM = tokenFCM;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }
}
