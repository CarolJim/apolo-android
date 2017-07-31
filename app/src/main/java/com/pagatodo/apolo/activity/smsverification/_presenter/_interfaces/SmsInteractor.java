package com.pagatodo.apolo.activity.smsverification._presenter._interfaces;

import android.app.Activity;
/**
 * Created by rvargas on 21-07-17.
 */

public interface SmsInteractor {

    interface onValidationListener{
        void onSuccess();
        void onFailed(String message);
    }
    void onValidation(String celular, String codigo, onValidationListener listener);


    interface onConfirmationListener{
        void onSuccess();
        void onFailed(String message);
    }
    void onConfirmation(String celular, onConfirmationListener listener);
}
