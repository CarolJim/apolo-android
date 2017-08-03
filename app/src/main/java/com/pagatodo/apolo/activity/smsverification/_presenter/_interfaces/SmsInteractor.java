package com.pagatodo.apolo.activity.smsverification._presenter._interfaces;

import com.pagatodo.networkframework.DataManager;

/**
 * Created by rvargas on 21-07-17.
 */

public interface SmsInteractor {

    /** Interface para confirmar numero celular y retornar codigo de verificacion */
    interface onConfirmationListener{
        void onSuccess(DataManager dataManager);
        void onFailed(DataManager dataManager);
    }
    void onConfirmation(String celular, onConfirmationListener listener);

    /** Interface para validar codigo generado */
    interface onValidationListener{
        void onSuccess(DataManager dataManager);
        void onFailed(DataManager dataManager);
    }
    void onValidation(String celular, String codigo, onValidationListener listener);

}
