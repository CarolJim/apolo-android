package com.pagatodo.apolo.activity.splash._presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.splash._presenter._interfaces.ISplashPresenter;
import com.pagatodo.apolo.activity.splash._presenter._interfaces.ISplashView;
import com.pagatodo.apolo.data.model.Promotor;
import com.pagatodo.apolo.data.model.webservice.response.GetPromotersResponse;
import com.pagatodo.apolo.data.remote.BuildRequest;
import com.pagatodo.apolo.data.remote.RequestContract;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;
import com.pagatodo.networkframework.DataManager;
import com.pagatodo.networkframework.interfaces.IRequestResult;

import java.util.List;

import static com.pagatodo.apolo.data.local.PreferencesContract.LIST_PROMOTORS;
import static com.pagatodo.apolo.data.remote.RequestContract.GET_PROMOTERS;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_RE_GET_PROMOTORS;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_SALIR;
import static com.pagatodo.networkframework.model.ResponseConstants.RESPONSE_CODE_OK;

/**
 * Created by jvazquez on 26/07/2017.
 */

public class SplashPresenter extends BasePresenter<ISplashView> implements ISplashPresenter{

    public SplashPresenter(ISplashView view) {
        super(view);
    }

    public void getPromotersList()
    {
        if(isOnline()){
            view.showProgress("Actualizando promotores.");
            BuildRequest.getPromotersRequest(this);
        }else{
            if(pref.getListOfPromotors().isEmpty()){
                view.updatePromotorsFailed("Sin Internet", getString(R.string.network_error));
                return;
            }
            view.updatePromotorsSuccess();
        }

    }

    @Override
    public void onSuccess(DataManager dataManager) {
        super.onSuccess(dataManager);
        if(dataManager.getData() != null){
            switch (dataManager.getMethod()){
                case GET_PROMOTERS:
                    processPromotersResponse((GetPromotersResponse) dataManager.getData());
                    break;
            }
        }
    }

    @Override
    public void onFailed(DataManager dataManager) {
        super.onFailed(dataManager);
        if(dataManager.getData() != null){
            switch (dataManager.getMethod()){
                case GET_PROMOTERS:
                    if(pref.getListOfPromotors().isEmpty()){
                        view.updatePromotorsFailed("Error", (String) dataManager.getData());
                        return;
                    }
                    view.updatePromotorsSuccess();
                    break;
                default:
                    view.showMessage((String) dataManager.getData());
                    break;
            }
        }
    }

    private void processPromotersResponse(GetPromotersResponse getPromotersResponse) {
        switch (getPromotersResponse.getRespuesta().getCodigo()){
            case RESPONSE_CODE_OK:
                updatePromotores(getPromotersResponse.getPromotorList());
                break;
            default:
                view.showMessage(getPromotersResponse.getRespuesta().getMensaje());
                break;
        }
    }

    private void updatePromotores(List<Promotor> promotorList) {
        if(promotorList.isEmpty()){
            view.updatePromotorsFailed("No hay promotores", "no se han encontrado promotores en la base de datos");
            return;
        }
        pref.saveData(LIST_PROMOTORS, new Gson().toJson(promotorList));
        view.updatePromotorsSuccess();
    }
}
