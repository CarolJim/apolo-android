package com.pagatodo.apolo.activity.smsverification._presenter;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsInteractor;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.data.model.webservice.request.SMSCodeValidationRequest;
import com.pagatodo.apolo.data.model.webservice.request.SMSValidationRequest;
import com.pagatodo.apolo.data.remote.BuildRequest;
import com.pagatodo.networkframework.DataManager;
import com.pagatodo.networkframework.interfaces.IRequestResult;
import static com.pagatodo.apolo.utils.Constants.SMS_ACTION_RESULT;

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
        SMS_ACTION_RESULT = 2;
        BuildRequest.sendSMSCodeValidation(this, new SMSCodeValidationRequest(celular, codigo), pref.getHeaders());
    }

    @Override
    public void onConfirmation(String celular, final onConfirmationListener listener){
        this.listener = listener;
        SMS_ACTION_RESULT = 1;
        BuildRequest.sendSMSConfirmation(this, new SMSValidationRequest(celular), pref.getHeaders());
    }

    @Override
    public void onSuccess(DataManager dataManager) {
        if(dataManager.getData() != null){
            if(SMS_ACTION_RESULT==2) {
                listen.onSuccess(dataManager);
            } else if(SMS_ACTION_RESULT==1) {
                listener.onSuccess(dataManager);
            }
        }
    }

    @Override
    public void onFailed(DataManager dataManager) {
        if(dataManager.getData() != null){
            if(SMS_ACTION_RESULT==2) {
                listen.onFailed(dataManager);
            } else if(SMS_ACTION_RESULT==1) {
                listener.onFailed(dataManager);
            }
        }
    }

}