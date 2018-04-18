package com.pagatodo.apolo.data.remote.notifications;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import com.google.gson.Gson;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.data.room.entities.Promotor;
import com.pagatodo.apolo.data.remote.notifications.model.TokenMega;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.requestinbox.RequestGetInbox;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.requestregistrodispositivo.RequestRegistroDispositivo;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.requestregistrousuario.RequestRegistroUsuario;
import com.pagatodo.apolo.utils.Utils;

import java.util.Calendar;
import java.util.HashMap;

import static com.pagatodo.apolo.data.local.PreferencesContract.TOKEN_MEGA;

/**
 * Created by jvazquez on 14/06/2017.
 */

public class UtilsNotifications {

    //todo validar el id de mega
    public static final String ID_PROGRAMA_MEGA = "empty";


    public static RequestRegistroDispositivo requestRegistroDispositivo(Context context, String tokenFCM){
        RequestRegistroDispositivo request = new RequestRegistroDispositivo();
        request.getRequest().setUDID(Utils.getUdid(context));
        request.getRequest().setMarca(Build.BRAND);
        request.getRequest().setModelo(Build.MODEL);
        request.getRequest().setSistemaOperativo("Android");
        request.getRequest().setVersionSO(Build.VERSION.RELEASE);
        request.getRequest().setTokenNotificacion(tokenFCM);
        try{
            request.getRequest().setVersionApp(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
        }catch (Exception e){
            request.getRequest().setVersionApp("1.0");
        }
        return request;
    }
    public static RequestRegistroUsuario requestRegistroUsuario(Promotor promotor, TokenMega tokenMega){
        RequestRegistroUsuario request = new RequestRegistroUsuario();
        request.getRequest().setEmail(promotor.getNombre());
        request.getRequest().setNombre(promotor.getNombre() + promotor.getApellidoPaterno() + promotor.getApellidoMaterno());
        request.getRequest().setIdUsuarioPrograma(ID_PROGRAMA_MEGA);
        if(tokenMega != null){
            request.getRequest().setTokenNotificacion(tokenMega.getTokenFCM());
        }else{
            request.getRequest().setTokenNotificacion("");
        }

        return request;
    }
    public static RequestGetInbox requestObtenerMensajesInbox(int idDireccion, int idMensaje){
        RequestGetInbox request = new RequestGetInbox();
        request.getRequest().setIdDireccion(idDireccion);
        request.getRequest().setIdMensaje(idMensaje);

        return request;
    }
    public static String getTokenMega(String tokenFCM){
        TokenMega tokenMega = new TokenMega();
        tokenMega.setFecha(getFechaForSendToServer());
        tokenMega.setTokenFCM(tokenFCM);
        tokenMega.setUpdated(false);

        return new Gson().toJson(tokenMega);
    }
    public static TokenMega getTokenMega(Preferences pref){
        if(pref.containsData(TOKEN_MEGA)) {
            try {
                return new Gson().fromJson(pref.loadString(TOKEN_MEGA), TokenMega.class);
            } catch (Exception e) {
                return new TokenMega();
            }
        }
        return new TokenMega();
    }
    private static String getFechaForSendToServer() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -1);
        String fechayhora;
        fechayhora = DateFormat.format("yyyyMMdd kk:mm:ss", cal.getTime()).toString();
        return fechayhora;
    }
    public  static HashMap<String, String> getMegaHeaders(@Nullable TokenMega tokenMega){
        HashMap<String, String> params = new HashMap<>();
        params.put("Content-type", "application/json");
        params.put("idPrograma", ID_PROGRAMA_MEGA);
        if(tokenMega != null){
            params.put("tokenAuth", tokenMega.getTokenAutenticacion());
            params.put("idDispositivo", tokenMega.getIdDispositivo());
        }
        return params;
    }
}