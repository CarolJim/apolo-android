package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.Promotor;
import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.factory.Respuesta;


import java.util.List;

/**
 * Created by Omar on 26/07/2017.
 */

public class GetPromotersResponse extends ModelPattern {

    private Respuesta Respuesta;
    private List<Promotor> PromotorList;

    public Respuesta getRespuesta() {
        return Respuesta;
    }

    public List<Promotor> getPromotorList() {
        return PromotorList;
    }
}
