package com.pagatodo.apolo.activity.smsverification._presenter._interfaces;

import com.pagatodo.apolo.ui.base.factoryinterfaces.IProcessData;

/**
 * Created by rvargas on 21-07-17.
 */

public interface SmsPresenter extends IProcessData {
    void validation(String celular, String codigo);
    void confirmation(String celular);
    void updatePin(String pin);
}
