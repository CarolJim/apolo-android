package com.pagatodo.apolo.activity.smsverification._presenter;

import android.text.TextUtils;

import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsInteractor;

/**
 * Created by rvargas on 21-07-17.
 */

public class SmsInteractorImpl implements SmsInteractor {

    @Override
    public void onSMS(String codigo, onSMSListener listener) {
        if (TextUtils.isEmpty(codigo)){
            listener.onCodigoError();
        } else if(codigo.equals("123456")){
            listener.onSuccess();
        }else {
            listener.failure("El código de verificacón no es valida");
        }

    }
}
