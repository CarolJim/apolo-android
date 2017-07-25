package com.pagatodo.apolo.data.pojo;

/**
 * Created by rvargas on 21/07/2017.
 */

public class Users {
    private String ruta_tarjeta     = "";
    private String ruta_ine_frente  = "";
    private String ruta_ine_vuelta  = "";

    public Users(){
        super();
    }

    public String getRuta_tarjeta() {
        return ruta_tarjeta;
    }

    public void setRuta_tarjeta(String ruta_tarjeta) {
        this.ruta_tarjeta = ruta_tarjeta;
    }

    public String getRuta_ine_frente() {
        return ruta_ine_frente;
    }

    public void setRuta_ine_frente(String ruta_ine_frente) {
        this.ruta_ine_frente = ruta_ine_frente;
    }

    public String getRuta_ine_vuelta() {
        return ruta_ine_vuelta;
    }

    public void setRuta_ine_vuelta(String ruta_ine_vuelta) {
        this.ruta_ine_vuelta = ruta_ine_vuelta;
    }

}
