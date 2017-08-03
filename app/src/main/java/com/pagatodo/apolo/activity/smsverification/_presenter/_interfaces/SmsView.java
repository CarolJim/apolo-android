package com.pagatodo.apolo.activity.smsverification._presenter._interfaces;

import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;


/**
 * Created by rvargas on 21-07-17.
 */

public interface SmsView extends IEventOnView {
    void showProgress();
    void hideProgress();
    void setNavigation();
    void onSuccess(String message);
    void onFailed(String message,  int result);
    void showMessage(String message);

}
