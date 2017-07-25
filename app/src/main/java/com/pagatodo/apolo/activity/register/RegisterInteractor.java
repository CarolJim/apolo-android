package com.pagatodo.apolo.activity.register;

import com.pagatodo.apolo.data.pojo.Cards;

import java.util.List;

/**
 * Created by rvargas on 21-07-17.
 */

public interface RegisterInteractor {

    interface onRegisterListener{
        void onSuccess();
        void failure(String message);
        void messagesError(String message);
    }

    interface onRequestListener{
        void onSuccessRequest();
        void failureRequest(String message);
    }
    void onRegisterAfiliado(String numberCelPhone, String numberPhone, String rutaCard, String rutaINEFront, String rutaINEBack, onRegisterListener listener);
    void onRequestData(List<Cards> cardsList, onRequestListener listener);
}
