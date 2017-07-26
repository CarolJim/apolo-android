package com.pagatodo.apolo.activity.smsverification;

/**
 * Created by rvargas on 21-07-17.
 */

public interface SmsView {
    void showProgress();
    void hideProgress();
    void setCodigoError();
    void setNavigation();
    void showMessage(String message);
}
