package com.pagatodo.apolo.activity.login._presenter;

import android.text.TextUtils;
import com.pagatodo.apolo.App;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginInteractor;
import com.pagatodo.apolo.utils.Constants;

/**
 * Created by rvargas on 21-07-17.
 */

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void onLogin(String numberUser, onLoginListener listener) {
        if (TextUtils.isEmpty(numberUser)){
            listener.onUserNumberError();
        } else if(numberUser.equals("12345678")){
            App afiliado = App.getInstance();
            afiliado.put(Constants.KEY_NUMERO_AFILIADO, numberUser);
            afiliado.put(Constants.KEY_NOMBRE_AFILIADO, "Roberto Vargas");
            listener.onSuccess();
        }else {
            listener.failure("El número de usuario es invalido");
        }

    }
}
