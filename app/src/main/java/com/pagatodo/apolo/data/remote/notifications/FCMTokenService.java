package com.pagatodo.apolo.data.remote.notifications;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;
import com.pagatodo.apolo.App;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.data.remote.BuildRequest;
import com.pagatodo.apolo.data.remote.notifications.model.TokenMega;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.response.ResponseRegistroDispositivo;
import com.pagatodo.networkframework.DataManager;
import com.pagatodo.networkframework.interfaces.IRequestResult;

import static com.pagatodo.apolo.data.local.PreferencesContract.TOKEN_MEGA;
import static com.pagatodo.apolo.data.remote.RequestContract.MEGA_REGISTER_DEVICE;
import static com.pagatodo.apolo.data.remote.notifications.UtilsNotifications.requestRegistroDispositivo;
import static com.pagatodo.networkframework.model.ResponseConstants.RESPONSE_CODE_OK;

/**
 * Created by jvazquez on 18/04/2017.
 *
 * Servicio que obtiene el token de FCM que sincroniza con mega, además lo guarda en preferencias y en caso de que suceda un error
 * almacena el token con una bandera para que en cuanto haya conexión a internet actualize el token
 */

public class FCMTokenService extends FirebaseInstanceIdService implements IRequestResult {
    Preferences pref = App.getInstance().getPrefs();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        pref.saveData(TOKEN_MEGA, UtilsNotifications.getTokenMega(refreshedToken));
        BuildRequest.registerDeviceMega(UtilsNotifications.getMegaHeaders(null), requestRegistroDispositivo(App.getInstance().getApplicationContext(), refreshedToken), this);
    }


    @Override
    public void onSuccess(DataManager data) {
        switch (data.getMethod()){
            case MEGA_REGISTER_DEVICE:
                ResponseRegistroDispositivo responseRegistroDispositivo = (ResponseRegistroDispositivo) data.getData();
                switch (responseRegistroDispositivo.getCodigoRespuesta()){
                    case RESPONSE_CODE_OK:
                        if(pref.containsData(TOKEN_MEGA)){
                            try{
                                TokenMega tokenMega = new Gson().fromJson(pref.loadString(TOKEN_MEGA), TokenMega.class);
                                if(tokenMega != null && !tokenMega.getTokenFCM().isEmpty()){
                                    if(!tokenMega.getIsUpdated())
                                        tokenMega.setUpdated(true);
                                    tokenMega.setIdDispositivo(responseRegistroDispositivo.getIdDispositivo());
                                    tokenMega.setTokenAutenticacion(responseRegistroDispositivo.getTokenAutenticacion());
                                    pref.saveData(TOKEN_MEGA, new Gson().toJson(tokenMega));
                                }else{
                                    pref.clearPreference(TOKEN_MEGA);
                                }
                            }catch (Exception e){
                                pref.clearPreference(TOKEN_MEGA);
                            }
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public void onFailed(DataManager error) {

    }
}