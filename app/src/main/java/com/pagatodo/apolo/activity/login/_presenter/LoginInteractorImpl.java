package com.pagatodo.apolo.activity.login._presenter;

import android.text.TextUtils;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginInteractor;


/**
 * Created by rvargas on 21-07-17.
 */

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void onLogin(String numberUser, onLoginListener listener) {
        if (TextUtils.isEmpty(numberUser)){
            listener.onUserNumberError();
        } else if(numberUser.equals("12345678")){
            listener.onSuccess();
        }else {
            listener.failure("El número de usuario es invalido");
        }

    }
}
