package com.pagatodo.apolo.data.model.webservice.request;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by Omar on 26/07/2017.
 */

public class TiendasRequest extends ModelPattern {

    private int ID_Iniciativa = 0; // (0 = todos)
    private int Activo = 1;  // (1 = Activos, 0= Desactivados, 3= todos)
    private int ID_Tienda = 0; // (0 = todos)

    public TiendasRequest() {
    }
}
