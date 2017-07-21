package com.pagatodo.apolo.data.pojo.webservice;

import com.pagatodo.apolo.data.pojo.factory.Response;

/**
 * Created by jvazquez on 23/05/2017.
 */

public class UserProperties  extends Response {
    private String idUsuario     = "";
    private String tokenUsuario  = "";
    private String saldo = "";
    private boolean correoValidado = false;

    public UserProperties(){

    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getSaldo() {
        return saldo;
    }

    public String getTokenUsuario() {
        return tokenUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public void setTokenUsuario(String tokenUsuario) {
        this.tokenUsuario = tokenUsuario;
    }

    public void setCorreoValidado(boolean correoValidado) {
        this.correoValidado = correoValidado;
    }
    public boolean getCorreoValidado(){
        return correoValidado;
    }
}
