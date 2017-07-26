package com.pagatodo.apolo.ui.base.factoryinterfaces;

import android.support.annotation.DrawableRes;

/**
 * Created by jvazquez on 19/05/2017.
 */

public interface IEventOnView {
    void showProgress(String message);
    void hideProgress();
    void showError(String message);
    void showDialog(String title, String message, @DrawableRes int idResource, String textBtnPrimary, String primaryEvent, String textBtnSecondary, String secondaryEvent);
    void showMessage(String message);
    void tokenExpired();
}