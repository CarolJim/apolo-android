package com.pagatodo.apolo.activity.smsverification;


/**
 * Created by rvargas on 21-07-17.
 */

public class SmsPresenterImpl implements SmsPresenter,SmsInteractor.onSMSListener {

    SmsView smsView;
    SmsInteractor smsInteractor;

    public SmsPresenterImpl(SmsView smsView) {
        this.smsView = smsView;
        smsInteractor = new SmsInteractorImpl();
    }

    @Override
    public void verify(String codigo) {
        smsView.showProgress();
        smsInteractor.onSMS(codigo, this);
    }

    @Override
    public void onSuccess() {
        if (smsView!=null)
            smsView.hideProgress();
            smsView.setNavigation();
    }

    @Override
    public void failure(String message) {
        if (smsView!=null)
            smsView.hideProgress();
            smsView.showMessage(message);
    }

    @Override
    public void onCodigoError() {
        if (smsView!=null)
            smsView.hideProgress();
            smsView.setCodigoError();
    }
}
