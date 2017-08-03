package com.pagatodo.apolo.activity.smsverification._presenter._interfaces;

import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;
import com.pagatodo.networkframework.DataManager;


/**
 * Created by rvargas on 21-07-17.
 */

public interface SmsView extends IEventOnView {
    void showProgress();
    void hideProgress();
    void setNavigation();
    void onSuccess(DataManager dataManager);
    void onFailed(DataManager dataManager);
    void showMessage(String message);

}
