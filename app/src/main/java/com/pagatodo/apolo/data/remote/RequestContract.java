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

    //General Methods
    public static final String GET_PROMOTERS                = "/ObtenerListaPromotores";
    public static final String DO_CREDIT_REQUEST_REGISTER   = "/RegistrarSolicitudCredito";
    public static final String DOCUMENT_UPLOAD              = "/CargarDocumento";
    public static final String SEND_SMS_CONFIRMATION        = "/SendSMSConfirmation";
    public static final String SMS_CODE_VALIDATION          = "/SMSConfirmationCodeValidation";

    // IDS
    public static final String CHECK_IDP          = "/ValidarIDP";
}
