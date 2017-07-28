package com.pagatodo.apolo.activity.register._presenter._interfaces;

import com.pagatodo.apolo.data.model.Cards;
import com.pagatodo.apolo.data.model.FormularioAfiliacion;
import com.pagatodo.apolo.data.model.webservice.request.CreditRequestRegisterRequest;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IProcessData;

import java.util.List;

/**
 * Created by rvargas on 21-07-17.
 */

public interface RegisterPresenter extends IProcessData{
    void request(List<Cards> cardsList);
    void register(String numberCelPhone, String numberPhone, String rutaCard, String rutaINEFront, String rutaINEBack);
    void requestRegister(FormularioAfiliacion request);
}
