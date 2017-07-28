package com.pagatodo.apolo.activity.login._presenter;

import android.os.Handler;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginInteractor;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginPresenter;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginView;
import com.pagatodo.apolo.data.model.Promotor;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;

import static com.pagatodo.apolo.data.local.Preferences.createSession;

/**
 * Created by rvargas on 21-07-17.
 */

public class LoginPresenterImpl extends BasePresenter<LoginView> implements LoginPresenter,LoginInteractor.onLoginListener {
    private static final int TIME_TO_LOGIN = 2000;

    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        super(loginView);
        loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void login(final String numberUser) {
        view.showProgress(getString(R.string.progress_login));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginInteractor.onLogin(numberUser, LoginPresenterImpl.this);
            }
        },TIME_TO_LOGIN);
    }

    @Override
    public void onSuccess(Promotor promotor) {
        if (view!=null){
            createSession(pref, promotor);
            view.setNavigation();
//            view.hideProgress();
        }
    }

    @Override
    public void failure(String message) {
        if (view!=null){
            view.hideProgress();
            view.showMessage(message);
        }
    }

    @Override
    public void onUserNumberError() {
        if (view!=null){
            view.hideProgress();
            view.setUserNumberError();
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }
}
