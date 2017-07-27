package com.pagatodo.apolo.activity.login._presenter._interfaces;


/**
 * Created by rvargas on 21-07-17.
 */

public interface LoginInteractor {

    interface onLoginListener{
        void onSuccess();
        void failure(String message);
        void onUserNumberError();
    }
    void onLogin(String username, onLoginListener listener);
}
