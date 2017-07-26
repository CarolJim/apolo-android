package com.pagatodo.apolo.ui.base.factoryfragments;


import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IProcessData;
import com.pagatodo.apolo.ui.dialogs.DialogFactory;

import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_TOKEN_EXPIRED;

/**
 * Created by jvazquez on 19/05/2017.
 */

public abstract class SupportPresenterFragment<iProcessData extends IProcessData> extends SupportUXFragment {

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
}
