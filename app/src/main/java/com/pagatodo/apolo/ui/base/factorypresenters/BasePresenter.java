package com.pagatodo.apolo.ui.base.factorypresenters;

import android.content.Context;
import android.support.annotation.StringRes;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IProcessData;
import com.pagatodo.networkframework.DataManager;
import com.pagatodo.networkframework.UtilsNet;
import com.pagatodo.networkframework.interfaces.IRequestResult;

/**
 * Created by jvazquez on 19/05/2017.
 */

public abstract class BasePresenter<iEventOnView extends IEventOnView> implements IRequestResult, IProcessData {
    protected Preferences pref = App.getInstance().getPrefs();
    protected iEventOnView view;
    protected Context context = null;
    public BasePresenter(iEventOnView view){
        this.view = view;
    }

    @Override
    public void onSuccess(DataManager dataManager) {
        view.hideProgress();
    }

    @Override
    public void onFailed(DataManager dataManager) {
        view.hideProgress();
    }

    protected String getString(@StringRes int idString){
        return App.getInstance().getString(idString);
    }
    protected String getString(@StringRes int idString, Object... args){
        return App.getInstance().getString(idString, args);
    }
    @Override
    public void onCreate() {
        context = App.getInstance();
    }

    @Override
    public void onDestroy() {

    }
    protected Context getContext(){
        return App.getInstance();
    }

    protected boolean isOnline(){
        return UtilsNet.isOnline(App.getInstance());
    }
}