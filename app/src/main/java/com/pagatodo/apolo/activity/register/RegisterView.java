package com.pagatodo.apolo.activity.register;

/**
 * Created by rvargas on 21-07-17.
 */

public interface RegisterView {
    void setMessageError(String message);
    void setNavigation();
    void returnData();
    void showMessage(String message);
}