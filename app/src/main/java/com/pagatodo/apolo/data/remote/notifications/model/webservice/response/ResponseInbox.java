package com.pagatodo.apolo.data.remote.notifications.model.webservice.response;


import com.pagatodo.apolo.data.model.Mensaje;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jvazquez on 15/06/2017.
 */

public class ResponseInbox extends GenericResponseMega {
    private List<Mensaje> mensajes = new ArrayList<>();

    public ResponseInbox(){
        super();
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
}
