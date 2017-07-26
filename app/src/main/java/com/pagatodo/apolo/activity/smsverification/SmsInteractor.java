package com.pagatodo.apolo.activity.smsverification;
/**
 * Created by rvargas on 21-07-17.
 */

public interface SmsInteractor {

    interface onSMSListener{
        void onSuccess();
        void failure(String message);
        void onCodigoError();
    }
    void onSMS(String codigo, onSMSListener listener);
}
