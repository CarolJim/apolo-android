package com.pagatodo.apolo.data.remote;

/**
 * Created by jvazquez on 26/07/2017.
 */

public class RequestContract {

    //RemoteConfig
    public static final String GET_REMOTE_CONFIG       = "remoteConfig";

    //MEGA
    public static final String MEGA_REGISTER_DEVICE    = "/RegistroDispositivo";
    public static final String MEGA_REGISTER_USER      = "/RegistroUsuario";
    public static final String MEGA_OBTENER_MSJ_INBOX  = "/ObtenerMensajesInbox";
    public static final String MEGA_ACTIVATE_PUSH      = "/ActivateNotification";
    public static final String MEGA_GET_PUSH_STATUS    = "/GetStatusNotification";
}
