package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.factory.Respuesta;
import com.pagatodo.apolo.data.room.entities.Iniciativa;
import com.pagatodo.apolo.data.room.entities.Tienda;

import java.util.List;

/**
 * Created by Omar on 26/07/2017.
 */

public class GetTiendasResponse extends ModelPattern {

    private Respuesta Respuesta;
    private List<Tienda> ListTiendas;

    public Respuesta getRespuesta() {
        return Respuesta;
    }

    public List<Tienda> getListTiendas() {
        return ListTiendas;
    }
}
