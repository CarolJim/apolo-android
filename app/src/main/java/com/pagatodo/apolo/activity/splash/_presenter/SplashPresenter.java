package com.pagatodo.apolo.activity.splash._presenter;

import android.util.Log;

import com.pagatodo.apolo.activity.splash._presenter._interfaces.ISplashPresenter;
import com.pagatodo.apolo.data.model.webservice.response.GetPromotersResponse;
import com.pagatodo.apolo.data.remote.BuildRequest;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;
import com.pagatodo.networkframework.DataManager;
import com.pagatodo.networkframework.interfaces.IRequestResult;

/**
 * Created by jvazquez on 26/07/2017.
 */

public class SplashPresenter extends BasePresenter<IEventOnView> implements ISplashPresenter{

    public SplashPresenter(IEventOnView view) {
        super(view);
    }

    public void getPromotersList()
    {
        BuildRequest.getPromotersRequest(this);
    }

    @Override
    public void onSuccess(DataManager dataManager) {
        super.onSuccess(dataManager);
        Log.w("EndPoint",dataManager.getMethod());
        GetPromotersResponse response = (GetPromotersResponse) dataManager.getData();
        Log.w("Successful",String.valueOf(response.getRespuesta().isExito()));
    }

    @Override
    public void onFailed(DataManager dataManager) {
        super.onFailed(dataManager);
    }
}
