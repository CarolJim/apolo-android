package com.pagatodo.apolo.activity.CheckIDP._presenter;

import android.os.Handler;
import android.widget.Toast;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.activity.CheckIDP._presenter._interfaces.IdpInteractor;
import com.pagatodo.apolo.activity.CheckIDP._presenter._interfaces.IdpPresenter;
import com.pagatodo.apolo.activity.CheckIDP._presenter._interfaces.IdpView;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginView;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;

/**
 * Created by FranciscoManzo on 22/12/2017.
 */

public class IdpPresenterImpl extends BasePresenter<IdpView> implements IdpPresenter,
        IdpInteractor.onIdpListener{

    private static final int TIME_TO_LOGIN = 2000;

    private IdpInteractor idpInteractor;

    public IdpPresenterImpl(IdpView view) {
        super(view);
        idpInteractor = new IdpInteractorImpl();
    }

    @Override
    public void checkIdp(final String numberIdp) {
        //view.showProgress(getString(R.string.progress_login));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                idpInteractor.onSendIdp(numberIdp, IdpPresenterImpl.this);
            }
        },TIME_TO_LOGIN);
    }

    @Override
    public void onSuccess(String idp) {
        Toast.makeText(getContext(), "Exito en nuestra peticion", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failure(String message) {
        Toast.makeText(getContext(), "Fail en nuestra peticion", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNumberError() {
        Toast.makeText(getContext(), "onUserNumberError", Toast.LENGTH_SHORT).show();
    }
}
