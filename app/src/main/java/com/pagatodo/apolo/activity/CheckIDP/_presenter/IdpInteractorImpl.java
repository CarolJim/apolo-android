package com.pagatodo.apolo.activity.CheckIDP._presenter;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.CheckIDP._presenter._interfaces.IdpInteractor;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.data.model.webservice.request.CheckIdpRequest;
import com.pagatodo.apolo.data.remote.BuildRequest;
import com.pagatodo.networkframework.DataManager;
import com.pagatodo.networkframework.interfaces.IRequestResult;

/**
 * Created by FranciscoManzo on 21/12/2017.
 */

public class IdpInteractorImpl implements IdpInteractor, IRequestResult {
    Preferences pref = App.getInstance().getPrefs();
    Context context = App.getInstance();
    onIdpListener listener;

    @Override
    public void onSendIdp(String idp, onIdpListener listener) {
        this.listener = listener;
        CheckIdpRequest checkIdpRequest = new CheckIdpRequest(idp);
        BuildRequest.sendCheckIdp(this, checkIdpRequest, pref.getHeaders());
    }

    @Override
    public void onSuccess(DataManager dataManager) {
        //Log.d("IdpInteractorImpl", "onSuccess " + dataManager);
        listener.onSuccess(dataManager);
    }

    @Override
    public void onFailed(DataManager dataManager) {
      //  Log.d("IdpInteractorImpl", "onFailed " + dataManager);
        listener.failure(dataManager);

    }
}
