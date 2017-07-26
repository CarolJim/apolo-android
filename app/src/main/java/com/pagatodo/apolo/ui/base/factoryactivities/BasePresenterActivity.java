package com.pagatodo.apolo.ui.base.factoryactivities;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IProcessData;

/**
 * Created by jvazquez on 29/05/2017.
 */

public abstract class BasePresenterActivity<iProcessData extends IProcessData> extends BaseActivity implements IEventOnView {

    protected iProcessData presenter = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePresenter();
        if(presenter != null){
            presenter.onCreate();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.onDestroy();
        }
    }
    protected abstract void initializePresenter();

    @Override
    public void showProgress(String message) {
        super.showProgressActivity(message);
    }

    @Override
    public void hideProgress() {
        super.hideProgressActivity();
    }

    @Override
    public void showError(String message) {
        super.showErrorViewActivity();
    }

    @Override
    public void showDialog(String title, String message, @DrawableRes int idResource, String textBtnPrimary, String primaryEvent, String textBtnSecondary, String secondaryEvent) {
        super.showDialog(title, message, idResource, textBtnPrimary, primaryEvent, textBtnSecondary, secondaryEvent);
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

    @Override
    public void tokenExpired() {

    }
}