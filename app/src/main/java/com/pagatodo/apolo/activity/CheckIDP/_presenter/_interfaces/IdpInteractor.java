package com.pagatodo.apolo.activity.CheckIDP._presenter._interfaces;

import com.pagatodo.networkframework.DataManager;

/**
 * Created by FranciscoManzo on 22/12/2017.
 */

public interface IdpInteractor {
    interface onIdpListener{
       // void onSuccess(Promotor promotor);
        //void onSuccess(String idp);
       //void failure(String message);
        void onUserNumberError();

        void onSuccess(DataManager dataManager);
        void failure(DataManager dataManager);
    }
   // void onLogin(String username, onLoginListener listener);
    void onSendIdp(String idp, onIdpListener listener);
}
