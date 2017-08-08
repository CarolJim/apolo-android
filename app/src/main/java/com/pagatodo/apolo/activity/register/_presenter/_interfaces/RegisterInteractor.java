package com.pagatodo.apolo.activity.register._presenter._interfaces;

import com.pagatodo.apolo.data.model.Cards;

import java.util.List;

/**
 * Created by rvargas on 21-07-17.
 */

public interface RegisterInteractor {

    interface onRegisterListener{
        void onSuccess();
        void failure(String message);
    }
    interface onRequestListener{
        void  onSuccessRequest();
    }
    interface logoutListener{
        void  onSuccessLogout();
    }

    void onRegisterAfiliado(String numberCelPhone, String numberPhone, String rutaCard, String rutaINEFront, String rutaINEBack, onRegisterListener listener);
    void onRequestData(List<Cards> cardsList, onRequestListener listener);
    void onLogout(logoutListener listener);
}
