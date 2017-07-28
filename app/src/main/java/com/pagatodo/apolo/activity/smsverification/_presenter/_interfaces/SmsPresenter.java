package com.pagatodo.apolo.activity.smsverification._presenter._interfaces;

import android.app.Activity;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IProcessData;

/**
 * Created by rvargas on 21-07-17.
 */

public interface SmsPresenter extends IProcessData {
    void validation(String codigo);
    void confirmation(String celular, Activity activity);
}
