package com.pagatodo.apolo.activity.login._presenter._interfaces;

import com.pagatodo.apolo.ui.base.factoryinterfaces.IProcessData;

/**
 * Created by rvargas on 21-07-17.
 */

public interface LoginPresenter extends IProcessData {

    void login(String numberUser);
}
