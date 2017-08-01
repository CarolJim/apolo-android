package com.pagatodo.apolo.data.model.webservice.remoteconfig;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by jvazquez on 27/06/2017.
 */

public class Data  extends ModelPattern{
    private String  FechaUltimaActualizacion    = "";
    private String  UrlServidor                 = "";
    private String  UrlNotificaciones           = "";
    private String  UrlConfig                   = "";
    private boolean EnableVerificateSMS         = false;
    private String  Digesto                     = "";

    public Data(){

    }

    public String getFechaUltimaActualizacion() {
        return FechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(String fechaUltimaActualizacion) {
        FechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public String getUrlServidor() {
        return UrlServidor;
    }

    public void setUrlServidor(String urlServidor) {
        UrlServidor = urlServidor;
    }

    public String getUrlNotificaciones() {
        return UrlNotificaciones;
    }

    public void setUrlNotificaciones(String urlNotificaciones) {
        UrlNotificaciones = urlNotificaciones;
    }

    public boolean isEnableVerificateSMS() {
        return EnableVerificateSMS;
    }

    public void setEnableVerificateSMS(boolean enableVerificateSMS) {
        EnableVerificateSMS = enableVerificateSMS;
    }

    public String getUrlConfig() {
        return UrlConfig;
    }

    public void setUrlConfig(String urlConfig) {
        UrlConfig = urlConfig;
    }

    public String getDigesto() {
        return Digesto;
    }

    public void setDigesto(String digesto) {
        Digesto = digesto;
    }
}
