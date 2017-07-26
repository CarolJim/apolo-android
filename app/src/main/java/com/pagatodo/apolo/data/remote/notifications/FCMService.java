package com.pagatodo.apolo.data.remote.notifications;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.pagatodo.apolo.App;
import com.pagatodo.apolo.data.model.Mensaje;

import java.util.List;

import static com.pagatodo.apolo.data.local.PreferencesContract.LIST_NOTIFICATIONS;

/**
 * Created by jvazquez on 14/06/2017.
 */

public class FCMService extends FirebaseMessagingService {
    public static final String KEY_MENSAJE = "mensaje";
    public static final String ACTION_HAVE_NOTIFICATION = "com.pagatodo.apolo.intent.action.NEW_NOTIFICATION";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size() > 0){
            if(remoteMessage.getData().containsKey(KEY_MENSAJE)){
                Mensaje mensaje = new Gson().fromJson(remoteMessage.getData().get(KEY_MENSAJE), Mensaje.class);
                List<Mensaje> mensajes = App.getInstance().getPrefs().obtenerColaDeMensajes();
                mensajes.add(mensaje);
                App.getInstance().getPrefs().saveData(LIST_NOTIFICATIONS, new Gson().toJson(mensajes));
                Intent bcIntent = new Intent();
                bcIntent.setAction(ACTION_HAVE_NOTIFICATION);
                sendBroadcast(bcIntent);
            }
        }
        super.onMessageReceived(remoteMessage);
    }
}
