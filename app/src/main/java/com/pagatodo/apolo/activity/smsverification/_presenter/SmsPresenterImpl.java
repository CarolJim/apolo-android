package com.pagatodo.apolo.activity.smsverification._presenter;


import android.app.Activity;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsInteractor;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsPresenter;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsView;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;

/**
 * Created by rvargas on 21-07-17.
 */

public class SmsPresenterImpl extends BasePresenter<SmsView> implements SmsPresenter, SmsInteractor.onValidationListener, SmsInteractor.onConfirmationListener {

    SmsInteractor smsInteractor;

    public SmsPresenterImpl(SmsView smsView) {
        super(smsView);
        smsInteractor = new SmsInteractorImpl();
    }

    //Confirmation numero
    @Override
    public void confirmation(String celular, Activity activity) {
        view.showProgress();
        smsInteractor.onConfirmation(celular, this, activity);
    }
    @Override
    public void onSuccessConfirmate() {
        if (view != null) {
            view.hideProgress();
            view.setAutoComplete();
        }
    }

    @Override
    public void onCodigoErrorConfirmate() {
        if (view!=null){
            view.hideProgress();
            view.setConfirmationError();
        }
    }

    // Validation Codigo
    @Override
    public void validation(String codigo) {
        view.showProgress();
        smsInteractor.onValidation(codigo, this);
    }

    @Override
    public void onSuccess() {
        if (view != null) {
            view.hideProgress();
            view.setNavigation();
        }
    }

    @Override
    public void failure(String message) {
        if (view!=null){
            view.hideProgress();
            view.showMessage(message);
        }
    }

    @Override
    public void onCodigoError() {
        if (view!=null){
            view.hideProgress();
            view.setCodigoError();
        }
    }
}
