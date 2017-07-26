package com.pagatodo.apolo.activity.smsverification._presenter;


import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsInteractor;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsPresenter;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsView;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;

/**
 * Created by rvargas on 21-07-17.
 */

public class SmsPresenterImpl extends BasePresenter<SmsView> implements SmsPresenter,SmsInteractor.onSMSListener {

    SmsInteractor smsInteractor;

    public SmsPresenterImpl(SmsView smsView) {
        super(smsView);
        smsInteractor = new SmsInteractorImpl();
    }

    @Override
    public void verify(String codigo) {
        view.showProgress();
        smsInteractor.onSMS(codigo, this);
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
