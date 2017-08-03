package com.pagatodo.apolo.activity.smsverification._presenter;

import android.os.Handler;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsInteractor;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsPresenter;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsView;
import com.pagatodo.apolo.data.model.webservice.response.GeneralServiceResponse;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;
import com.pagatodo.apolo.utils.Constants;
import com.pagatodo.networkframework.DataManager;
import static com.pagatodo.apolo.utils.Constants.SMS_ACTION_RESULT;
import static com.pagatodo.apolo.utils.Constants.SMS_FAILED_CONFIRMATE;
import static com.pagatodo.apolo.utils.Constants.SMS_FAILED_ONLINE;
import static com.pagatodo.apolo.utils.Constants.SMS_FAILED_VALIDATE;
import static com.pagatodo.networkframework.model.ResponseConstants.RESPONSE_CODE_OK;

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

    // CONFIRMAMOS NUMERO CELULAR, ENVIANDO COMO PARAMETRO EL NUMERO CELULAR PARA RECIBIR UN CODIGO GENERADO POR EL WS
    // *********************
    @Override
    public void confirmation(final String celular) {
        view.showProgress();
        if(isOnline()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SMS_ACTION_RESULT = 1;
                    smsInteractor.onConfirmation(celular, SmsPresenterImpl.this);
                }
            },TIME_TO_LOGIN);
        }else{
            view.showMessage(getString(R.string.network_error));
        }
    }

    @Override
    public void onSuccess(DataManager dataManager) {
        super.onSuccess(dataManager);
        if (view != null) {
            view.hideProgress();
            GeneralServiceResponse response = (GeneralServiceResponse) dataManager.getData();
            if(SMS_ACTION_RESULT==1) {
                switch (response.getRespuesta().getCodigo()){
                    case RESPONSE_CODE_OK:
                        view.onSuccess(response.getRespuesta().getMensaje());
                        break;
                    default:
                        view.onFailed(response.getRespuesta().getMensaje(), SMS_FAILED_CONFIRMATE);
                        break;
                }
            }
            else if(SMS_ACTION_RESULT==2) {
                switch (response.getRespuesta().getCodigo()){
                    case RESPONSE_CODE_OK:
                        pref.saveDataBool(String.valueOf(Constants.CODIGO_VERIFICADO),true);
                        view.setNavigation();
                        break;
                    default:
                        view.onFailed(response.getRespuesta().getMensaje(), SMS_FAILED_VALIDATE);
                        break;
                }
            }
        }
    }
    @Override
    public void onFailed(DataManager dataManager) {
        super.onFailed(dataManager);
        if (view != null) {
            view.hideProgress();
            if(isOnline()) {
                GeneralServiceResponse response = (GeneralServiceResponse) dataManager.getData();
                view.onFailed(response.getRespuesta().getMensaje(), SMS_FAILED_ONLINE);
            } else{
                if(SMS_ACTION_RESULT==1) {
                    view.onFailed((String) dataManager.getData(), SMS_FAILED_CONFIRMATE);
                } else {
                    view.onFailed((String) dataManager.getData(), SMS_FAILED_VALIDATE);
                }
            }

        }
    }


    // VALIDAMOS CODIGO RECIBIDO (ENVIANDO COMO PARAMETRO CELULAR Y CODIGO GENERADO)
    // *******************
    @Override
    public void validation(final String celular, final String codigo) {
        view.showProgress();
        if(isOnline()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SMS_ACTION_RESULT = 2;
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
