package com.pagatodo.apolo.activity.register;

import android.util.Log;

import com.pagatodo.apolo.data.pojo.Cards;

import java.util.List;

/**
 * Created by rvargas on 21-07-17.
 */

public class RegisterPresenterImpl implements RegisterPresenter, RegisterInteractor.onRegisterListener, RegisterInteractor.onRequestListener  {

    RegisterView registerView;
    RegisterInteractor registerInteractor;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
        registerInteractor = new RegisterInteractorImpl();
    }

    @Override
    public void register(String numberCelPhone, String numberPhone, String rutaCard, String rutaINEFront, String rutaINEBack) {
        registerInteractor.onRegisterAfiliado(numberCelPhone, numberPhone, rutaCard, rutaINEFront, rutaINEBack, this);
    }

    @Override
    public void request(List<Cards> cardsList) {
        registerInteractor.onRequestData(cardsList, this);
    }

    @Override
    public void onSuccess() {
        if (registerView!=null)
            registerView.setNavigation();
    }

    @Override
    public void failure(String message) {
        if (registerView!=null)
            registerView.showMessage(message);
    }

    @Override
    public void onSuccessRequest() {
        if (registerView!=null)
            registerView.returnData();
    }

    @Override
    public void failureRequest(String message) {
        if (registerView!=null)
            registerView.showMessage(message);
    }

    @Override
    public void messagesError(String message) {
        if (registerView!=null)
            registerView.setMessageError(message);
    }

}
