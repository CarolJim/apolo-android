package com.pagatodo.apolo.activity.CheckIDP._presenter._interfaces;

import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;
import com.pagatodo.networkframework.DataManager;

/**
 * Created by FranciscoManzo on 22/12/2017.
 */

public interface IdpView extends IEventOnView {
    void setIdpNumberError();
    void setNavigation();
    void showMessage(String message);

    void onSuccess(DataManager dataManager);

    void onFailed(DataManager dataManager);
}
