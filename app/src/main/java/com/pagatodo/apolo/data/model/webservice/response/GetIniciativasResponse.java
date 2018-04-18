package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.factory.Respuesta;
import com.pagatodo.apolo.data.room.entities.Iniciativa;

import java.util.List;

/**
 * Created by Omar on 26/07/2017.
 */

public class GetIniciativasResponse extends ModelPattern {

    private Respuesta Respuesta;
    private List<Iniciativa> ListIniciativas;

    public Respuesta getRespuesta() {
        return Respuesta;
    }

    public List<Iniciativa> getListIniciativas() {
        return ListIniciativas;
    }
}
