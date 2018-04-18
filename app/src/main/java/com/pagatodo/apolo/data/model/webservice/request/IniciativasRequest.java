package com.pagatodo.apolo.data.model.webservice.request;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by Omar on 26/07/2017.
 */

public class IniciativasRequest extends ModelPattern {

    private int ID_Iniciativa = 0;
    private int Activo = 1;  // (1 = Activos, 0= Desactivados, 3= todos)

    public IniciativasRequest() {
    }
}
