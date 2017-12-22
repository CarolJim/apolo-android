package com.pagatodo.apolo.activity.CheckIDP._presenter;


import android.content.Context;
import android.text.TextUtils;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.CheckIDP._presenter._interfaces.IdpInteractor;

/**
 * Created by FranciscoManzo on 21/12/2017.
 */

public class IdpInteractorImpl implements IdpInteractor {
    Context context = App.getInstance();

    @Override
    public void onSendIdp(String idp, onIdpListener listener) {
       // CheckIDPRequest
       /* idp = "2";
        if (TextUtils.isEmpty(idp)){
            listener.onUserNumberError();
        } else if(idp.equals("0")){
            listener.onSuccess(idp);
        }else {
            listener.failure(context.getString(R.string.error_invalid_id));
        }*/
    }
}
