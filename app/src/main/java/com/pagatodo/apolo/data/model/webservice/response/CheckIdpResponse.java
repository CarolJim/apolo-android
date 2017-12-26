package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by FranciscoManzo on 22/12/2017.
 */

public class CheckIdpResponse  extends ModelPattern {

    private int  Codigo = 99;
    private Boolean  Exito = false;
    private String  Mensaje = "Error en proceso";
    private String  CausasRechazo = "";
    private String  Estatus = "";
    private String  IDP = "";
    private String  MontoSolicitado = "";
    private String  Nombre = "";
    private String  NombreBanco = "";
    private String  NumeroBeneficiario = "";
    private String  NumeroContrato = "";
    private String  NumeroOrdenPago = "";

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public Boolean getExito() {
        return Exito;
    }

    public void setExito(Boolean exito) {
        Exito = exito;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public String getCausasRechazo() {
        return CausasRechazo;
    }

    public void setCausasRechazo(String causasRechazo) {
        CausasRechazo = causasRechazo;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String estatus) {
        Estatus = estatus;
    }

    public String getIDP() {
        return IDP;
    }

    public void setIDP(String IDP) {
        this.IDP = IDP;
    }

    public String getMontoSolicitado() {
        return MontoSolicitado;
    }

    public void setMontoSolicitado(String montoSolicitado) {
        MontoSolicitado = montoSolicitado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNombreBanco() {
        return NombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        NombreBanco = nombreBanco;
    }

    public String getNumeroBeneficiario() {
        return NumeroBeneficiario;
    }

    public void setNumeroBeneficiario(String numeroBeneficiario) {
        NumeroBeneficiario = numeroBeneficiario;
    }

    public String getNumeroContrato() {
        return NumeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        NumeroContrato = numeroContrato;
    }

    public String getNumeroOrdenPago() {
        return NumeroOrdenPago;
    }

    public void setNumeroOrdenPago(String numeroOrdenPago) {
        NumeroOrdenPago = numeroOrdenPago;
    }
}
