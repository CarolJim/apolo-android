package com.pagatodo.apolo.activity.login;

/**
 * Created by rvargas on 21-07-17.
 */

public interface LoginView {
    void showProgress();
    void hideProgress();
    void setUserNumberError();
    void setNavigation();
    void showMessage(String message);
}
