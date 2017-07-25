package com.pagatodo.apolo.activity.login;

import android.text.TextUtils;


/**
 * Created by rvargas on 21-07-17.
 */

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void onLogin(String numberUser, onLoginListener listener) {
        if (TextUtils.isEmpty(numberUser)){
            listener.onUserNumberError();
        } else if(numberUser.equals("100")){
            listener.onSuccess();
        }else {
            listener.failure("El número de usuario es invalido");
        }

    }
}
