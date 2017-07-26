package com.pagatodo.apolo.activity.register._presenter._interfaces;

import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;

/**
 * Created by rvargas on 21-07-17.
 */

public interface RegisterView extends IEventOnView{
    void setMessageError(String message);
    void setNavigation();
    void returnData();
    void showMessage(String message);
}