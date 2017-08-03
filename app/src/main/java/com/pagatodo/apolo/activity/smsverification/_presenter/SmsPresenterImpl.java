package com.pagatodo.apolo.activity.smsverification._presenter;

import android.os.Handler;
import android.view.View;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsInteractor;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsPresenter;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsView;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;
import com.pagatodo.apolo.utils.Constants;
import com.pagatodo.networkframework.DataManager;
import static com.pagatodo.apolo.data.remote.RequestContract.SEND_SMS_CONFIRMATION;
import static com.pagatodo.apolo.data.remote.RequestContract.SMS_CODE_VALIDATION;
import static com.pagatodo.apolo.ui.UI.showSnackBar;

/**
 * Created by rvargas on 21-07-17.
 */

public class SmsPresenterImpl extends BasePresenter<SmsView> implements SmsPresenter, SmsInteractor.onConfirmationListener,  SmsInteractor.onValidationListener {
    private static final int TIME_TO_LOGIN = 2000;
    SmsInteractor smsInteractor;

    public SmsPresenterImpl(SmsView smsView) {
        super(smsView);
        smsInteractor = new SmsInteractorImpl();
    }

    /** CONFIRMAMOS NUMERO CELULAR, ENVIANDO COMO PARAMETRO EL NUMERO CELULAR
     * PARA RECIBIR UN CODIGO GENERADO POR EL WS */
    @Override
    public void confirmation(final String celular) {
        view.showProgress();
        if(isOnline()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    smsInteractor.onConfirmation(celular, SmsPresenterImpl.this);
                }
            },TIME_TO_LOGIN);
        }else{
            showSnackBar((View) view,getString(R.string.network_error));
        }
    }

    @Override
    public void onSuccess(DataManager dataManager) {
        super.onSuccess(dataManager);
        if (view != null) {
            view.hideProgress();
            switch (dataManager.getMethod()){
                case SEND_SMS_CONFIRMATION:
                    view.onSuccess(dataManager);
                    break;
                case SMS_CODE_VALIDATION:
                    view.onSuccess(dataManager);
                    break;
            }
        }
    }
    @Override
    public void onFailed(DataManager dataManager) {
        super.onFailed(dataManager);
        if (view != null) {
            view.hideProgress();
            switch (dataManager.getMethod()){
                case SEND_SMS_CONFIRMATION:
                    view.onFailed(dataManager);
                    break;
                case SMS_CODE_VALIDATION:
                    view.onFailed(dataManager);
                    break;
            }
        }
    }

    /** VALIDAMOS CODIGO RECIBIDO (ENVIANDO COMO
     * PARAMETRO CELULAR Y CODIGO GENERADO) */
    @Override
    public void validation(final String celular, final String codigo) {
        view.showProgress();
        if(isOnline()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    smsInteractor.onValidation(celular, codigo, SmsPresenterImpl.this);
                }
            },TIME_TO_LOGIN);
        }else{
            showSnackBar((View) view,getString(R.string.network_error));
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }
}
