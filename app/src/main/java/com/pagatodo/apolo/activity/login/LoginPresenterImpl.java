package com.pagatodo.apolo.activity.login;

import android.util.Log;

/**
 * Created by rvargas on 21-07-17.
 */

public class LoginPresenterImpl implements LoginPresenter,LoginInteractor.onLoginListener {

    LoginView loginView;
    LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void login(String numberUser) {
        loginInteractor.onLogin(numberUser, this);
    }

    @Override
    public void onSuccess() {
        loginView.showProgress();
        if (loginView!=null)
        loginView.setNavigation();
    }

    @Override
    public void failure(String message) {
        if (loginView!=null)
        loginView.hideProgress();
        loginView.showMessage(message);

    }

    @Override
    public void onUserNumberError() {
        if (loginView!=null)
        loginView.hideProgress();
        loginView.setUserNumberError();

    }
}
