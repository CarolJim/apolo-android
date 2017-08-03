package com.pagatodo.apolo.activity.smsverification._presenter;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsInteractor;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.data.model.webservice.request.SMSCodeValidationRequest;
import com.pagatodo.apolo.data.model.webservice.request.SMSValidationRequest;
import com.pagatodo.apolo.data.remote.BuildRequest;
import com.pagatodo.networkframework.DataManager;
import com.pagatodo.networkframework.interfaces.IRequestResult;

import static com.pagatodo.apolo.data.remote.RequestContract.SEND_SMS_CONFIRMATION;
import static com.pagatodo.apolo.data.remote.RequestContract.SMS_CODE_VALIDATION;

/**
 * Created by rvargas on 21-07-17.
 */

public class SmsInteractorImpl implements SmsInteractor, IRequestResult {
    Preferences pref = App.getInstance().getPrefs();
    private onConfirmationListener listener;
    private onValidationListener listen;

    @Override
    public void onValidation(String celular, String codigo, onValidationListener listener) {
        this.listen = listener;
        BuildRequest.sendSMSCodeValidation(this, new SMSCodeValidationRequest(celular, codigo), pref.getHeaders());
    }

    @Override
    public void onConfirmation(String celular, final onConfirmationListener listener){
        this.listener = listener;
        BuildRequest.sendSMSConfirmation(this, new SMSValidationRequest(celular), pref.getHeaders());
    }

    @Override
    public void onSuccess(DataManager dataManager) {
        switch (dataManager.getMethod()){
            case SEND_SMS_CONFIRMATION:
                listener.onSuccess(dataManager);
                break;
            case SMS_CODE_VALIDATION:
                listen.onSuccess(dataManager);
                break;
        }
    }

    @Override
    public void onFailed(DataManager dataManager) {
        switch (dataManager.getMethod()){
            case SEND_SMS_CONFIRMATION:
                listener.onFailed(dataManager);
                break;
            case SMS_CODE_VALIDATION:
                listen.onFailed(dataManager);
                break;
        }
    }

}