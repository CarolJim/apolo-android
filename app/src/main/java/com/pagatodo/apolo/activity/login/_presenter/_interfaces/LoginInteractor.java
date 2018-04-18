package com.pagatodo.apolo.activity.login._presenter._interfaces;


import com.pagatodo.apolo.data.room.entities.Promotor;

/**
 * Created by rvargas on 21-07-17.
 */

public interface LoginInteractor {

    interface onLoginListener{
        void onSuccess(Promotor promotor);
        void failure(String message);
        void onUserNumberError();
    }
    void onLogin(String username, onLoginListener listener);
}
