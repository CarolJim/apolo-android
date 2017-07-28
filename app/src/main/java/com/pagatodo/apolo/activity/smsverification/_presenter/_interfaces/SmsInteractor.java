package com.pagatodo.apolo.activity.smsverification._presenter._interfaces;

import android.app.Activity;
/**
 * Created by rvargas on 21-07-17.
 */

public interface SmsInteractor {

    interface onValidationListener{
        void onSuccess();
        void failure(String message);
        void onCodigoError();
    }
    void onValidation(String codigo, onValidationListener listener);


    interface onConfirmationListener{
        void onSuccessConfirmate();
        void failure(String message);
        void onCodigoErrorConfirmate();
    }
    void onConfirmation(String celular, onConfirmationListener listener, Activity activity);
}
