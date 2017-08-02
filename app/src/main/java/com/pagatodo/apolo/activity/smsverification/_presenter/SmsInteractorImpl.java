package com.pagatodo.apolo.activity.smsverification._presenter;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsInteractor;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.data.model.webservice.request.SMSCodeValidationRequest;
import com.pagatodo.apolo.data.model.webservice.request.SMSValidationRequest;
import com.pagatodo.apolo.data.remote.BuildRequest;
import com.pagatodo.apolo.utils.Constants;
import com.pagatodo.networkframework.DataManager;
import com.pagatodo.networkframework.interfaces.IRequestResult;


/**
 * Created by rvargas on 21-07-17.
 */

public class SmsInteractorImpl implements SmsInteractor, IRequestResult {
    Preferences pref = App.getInstance().getPrefs();
    private onConfirmationListener listener;
    private onValidationListener listen;
    private int action = 0;

    @Override
    public void onValidation(String celular, String codigo, onValidationListener listener) {
        this.listen = listener;
        action = 1;
        BuildRequest.sendSMSCodeValidation(this, new SMSCodeValidationRequest(celular, codigo), pref.getHeaders());
    }

    @Override
    public void onConfirmation(String celular, final onConfirmationListener listener){
        this.listener = listener;
        action = 2;
        BuildRequest.sendSMSConfirmation(this, new SMSValidationRequest(celular), pref.getHeaders());
    }

    @Override
    public void onSuccess(DataManager dataManager) {
        if(dataManager.getData() != null){
            if(action==1) {
                pref.saveDataBool(String.valueOf(Constants.CODIGO_VERIFICADO),true);
                listen.onSuccess(dataManager);
            }
            else if(action==2)
                listener.onSuccess(dataManager);
        }
    }

    @Override
    public void onFailed(DataManager dataManager) {
        if(dataManager.getData() != null){
            if(action==1) {
                pref.saveDataBool(String.valueOf(Constants.CODIGO_VERIFICADO),false);
                listen.onFailed(dataManager);
            }
            else if(action==2)
                listener.onFailed(dataManager);
        }
    }

}