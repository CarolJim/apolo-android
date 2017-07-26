package com.pagatodo.apolo.activity.splash._presenter;

import com.pagatodo.apolo.activity.splash._presenter._interfaces.ISplashPresenter;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;
import com.pagatodo.networkframework.DataManager;

/**
 * Created by jvazquez on 26/07/2017.
 */

public class SplashPresenter extends BasePresenter<IEventOnView> implements ISplashPresenter{

    public SplashPresenter(IEventOnView view) {
        super(view);
    }

    @Override
    public void onSuccess(DataManager dataManager) {
        super.onSuccess(dataManager);
    }

    @Override
    public void onFailed(DataManager dataManager) {
        super.onFailed(dataManager);
    }
}
