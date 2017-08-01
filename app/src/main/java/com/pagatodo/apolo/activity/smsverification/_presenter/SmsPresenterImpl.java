package com.pagatodo.apolo.activity.smsverification._presenter;

import android.os.Handler;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsInteractor;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsPresenter;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsView;
import com.pagatodo.apolo.data.model.webservice.response.GeneralServiceResponse;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;
import com.pagatodo.networkframework.DataManager;

/**
 * Created by rvargas on 21-07-17.
 */

public class SmsPresenterImpl extends BasePresenter<SmsView> implements SmsPresenter, SmsInteractor.onConfirmationListener,  SmsInteractor.onValidationListener {
    private static final int TIME_TO_LOGIN = 2000;
    SmsInteractor smsInteractor;
    private int action = 0;

    public SmsPresenterImpl(SmsView smsView) {
        super(smsView);
        smsInteractor = new SmsInteractorImpl();
    }

    // SEND_SMS_CONFIRMATION
    // *********************
    @Override
    public void confirmation(final String celular) {
        view.showProgress();
        if(isOnline()){
            view.showProgress("Solicitando codigo de verificacion.");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    action = 1;
                    smsInteractor.onConfirmation(celular, SmsPresenterImpl.this);
                }
            },TIME_TO_LOGIN);
        }else{
            view.showMessage(getString(R.string.network_error));
        }
    }

    @Override
    public void onSuccess(Object... params) {
        if (view != null) {
            view.hideProgress();
            DataManager manager = (DataManager) params[0];
            GeneralServiceResponse response = (GeneralServiceResponse) manager.getData();
            if(action==1)
                view.onSuccess(response.getRespuesta().getMensaje());
            else if(action==2)
                if(response.getRespuesta().getCodigo() != 0) {
                    view.onFailed(response.getRespuesta().getMensaje());
                }  else {
                    view.setNavigation();
                }

        }

    }
    @Override
    public void onFailed(Object... params) {
        if (view != null) {
            view.hideProgress();
            DataManager manager = (DataManager) params[0];
            GeneralServiceResponse response = (GeneralServiceResponse) manager.getData();
            view.onFailed(response.getRespuesta().getMensaje());
        }
    }


    // SMS_CODE_VALIDATION
    // *******************
    @Override
    public void validation(final String celular, final String codigo) {
        view.showProgress();
        if(isOnline()){
            view.showProgress("Validando codigo de verificacion.");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    action = 2;
                    smsInteractor.onValidation(celular, codigo, SmsPresenterImpl.this);
                }
            },TIME_TO_LOGIN);
        }else{
            view.showMessage(getString(R.string.network_error));
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }
}
