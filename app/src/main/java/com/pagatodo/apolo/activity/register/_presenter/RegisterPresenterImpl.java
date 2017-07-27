package com.pagatodo.apolo.activity.register._presenter;

import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterInteractor;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterPresenter;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterView;
import com.pagatodo.apolo.data.model.Cards;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;

import java.util.List;

/**
 * Created by rvargas on 21-07-17.
 */

public class RegisterPresenterImpl extends BasePresenter<RegisterView> implements RegisterPresenter, RegisterInteractor.onRegisterListener, RegisterInteractor.onRequestListener  {

    private RegisterInteractor registerInteractor;

    public RegisterPresenterImpl(RegisterView registerView) {
        super(registerView);
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
        if (view!=null)
            view.setNavigation();
    }

    @Override
    public void failure(String message) {
        if (view!=null)
            view.showMessage(message);
    }

    @Override
    public void onSuccessRequest() {
        if (view!=null)
            view.returnData();
    }

    @Override
    public void failureRequest(String message) {
        if (view!=null)
            view.showMessage(message);
    }

    @Override
    public void messagesError(String message) {
        if (view!=null)
            view.setMessageError(message);
    }

}
