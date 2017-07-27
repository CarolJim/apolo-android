package com.pagatodo.apolo.data.remote;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.data.model.webservice.remoteconfig.ResponseRemoteConfig;
import com.pagatodo.apolo.utils.Utils;

import static com.pagatodo.apolo.data.local.PreferencesContract.SDIGESTO;
import static com.pagatodo.apolo.data.local.PreferencesContract.SFECHA_ACTUALIZACION;
import static com.pagatodo.apolo.data.local.PreferencesContract.SURL_CONFIG;
import static com.pagatodo.apolo.data.local.PreferencesContract.SURL_NOTIFICACIONES;
import static com.pagatodo.apolo.data.local.PreferencesContract.SURL_SERVIDOR;
import static com.pagatodo.apolo.utils.Constants.DEBUG;
import static com.pagatodo.apolo.utils.Constants.URL_REMOTE_CONFIG;
import static com.pagatodo.apolo.utils.Constants.URL_SERVER;
import static com.pagatodo.apolo.utils.Constants.URL_SERVER_MEGA;

/**
 * Created by jvazquez on 26/07/2017.
 */

public class RemoteConfig {

    public static String getUrlServer() {
        //TODO Remove HardCode
        /*
        Preferences pref = App.getInstance().getPrefs();
        if (pref != null) {
            if (DEBUG) {
                return URL_SERVER;
            }
            if (pref.containsData(SURL_SERVIDOR) && !pref.loadString(SURL_SERVIDOR).isEmpty()) {
                return pref.loadString(SURL_SERVIDOR);
            }
        }
        return URL_SERVER;*/
        return "http://172.16.2.117:8025/WSSolicitudCreditoAdultoMayor.svc";
    }

    static String getUrlMega() {
        Preferences pref = App.getInstance().getPrefs();
        if (pref != null) {
            if (DEBUG) {
                return URL_SERVER_MEGA;
            }
            if (pref.containsData(SURL_NOTIFICACIONES) && !pref.loadString(SURL_NOTIFICACIONES).isEmpty()) {
                return pref.loadString(SURL_NOTIFICACIONES);
            }
        }
        return URL_SERVER_MEGA;
    }
    static String getUrlRemoteConfig() {
        Preferences pref = App.getInstance().getPrefs();
        if (pref != null) {
            if (DEBUG) {
                return URL_REMOTE_CONFIG;
            }
            if (pref.containsData(SURL_CONFIG) && !pref.loadString(SURL_CONFIG).isEmpty()) {
                return pref.loadString(SURL_CONFIG);
            }
        }
        return URL_REMOTE_CONFIG;
    }

    public static boolean updateAppConfig(ResponseRemoteConfig responseRemoteConfig, Preferences pref) {
        boolean mDigestResult;
        if (!responseRemoteConfig.getData().getFechaUltimaActualizacion().equals(pref.getFechaActualizacion())) {
            mDigestResult = Utils.isValidDigest(responseRemoteConfig.getData());
        } else {
            mDigestResult = false;
        }
        if (mDigestResult) {
            pref.saveData(SFECHA_ACTUALIZACION, responseRemoteConfig.getData().getFechaUltimaActualizacion());
            pref.saveData(SURL_SERVIDOR, responseRemoteConfig.getData().getUrlServidor());
            pref.saveData(SURL_NOTIFICACIONES, responseRemoteConfig.getData().getUrlNotificaciones());
            pref.saveData(SURL_CONFIG, responseRemoteConfig.getData().getUrlConfig());
            pref.saveData(SDIGESTO, responseRemoteConfig.getData().getDigesto());
        }
        return mDigestResult;
    }
}
