package com.pagatodo.apolo.data.model;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by Omar on 27/07/2017.
 */

public class Solicitud extends ModelPattern {

    private int ID_Cliente;
    private int ID_Solicitud;
    private String Nombres;
    private String NumeroTarjeta;
    private String PrimerApellido;
    private String Promotor;
    private String SegundoApellido;
    private String Solicitud;
    private String TelefonoFijo;
    private String TelefonoMovil;
    private boolean TelefonoMovilVerificado;

    public int getID_Cliente() {
        return ID_Cliente;
    }

    public int getID_Solicitud() {
        return ID_Solicitud;
    }

    public String getNombres() {
        return Nombres;
    }

    public String getNumeroTarjeta() {
        return NumeroTarjeta;
    }

    public String getPrimerApellido() {
        return PrimerApellido;
    }

    public String getPromotor() {
        return Promotor;
    }

    public String getSegundoApellido() {
        return SegundoApellido;
    }

    public String getSolicitud() {
        return Solicitud;
    }

    public String getTelefonoFijo() {
        return TelefonoFijo;
    }

    public String getTelefonoMovil() {
        return TelefonoMovil;
    }

    public boolean isTelefonoMovilVerificado() {
        return TelefonoMovilVerificado;
    }
}
