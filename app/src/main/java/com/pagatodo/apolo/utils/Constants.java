package com.pagatodo.apolo.utils;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.data.model.Cards;
import com.pagatodo.apolo.data.model.Documento;
import com.pagatodo.apolo.data.model.Promotor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rvargas on 14/07/2017.
 */

public final class Constants {

    //Enable debug logs
    public static final boolean DEBUG = true;

    //RemoteConfig
    public static final boolean isEnableVerificateSMS = true;

    //SERVER
    //public static final String URL_SERVER             = "http://172.16.2.117:8025/WSSolicitudCreditoAdultoMayor.svc";
    public static final String URL_SERVER               = "http://10.10.45.17:8027/WSSolicitudCreditoAdultoMayor.svc"; // QA
    public static final String URL_SERVER_MEGA 	        = "http://10.10.45.16:62703/ServiceMega.svc/http";
    public static final String URL_REMOTE_CONFIG        = "https://apolo-8e38a.firebaseapp.com/config.json";
    public static final int TIMEOUT = 40000;
    public static final int TIME_TO_RETURN= 2000;

    //BASE DE DATOS
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "apolo.db";

    //Login
    public static final int MIN_SIZE_ID_AFILIADOR = 6;

    //CAMERA
    public static final int REQUEST_CAMERA_PERMISSION = 200;
    public static final String KEY_IS_CAPTURING     = "is_capturing";
    public static final String IMAGE_DIRECTORY_NAME = "Apolo";
    public static final String SELECTED_DOCUMENT_KEY = "selectedDocument";
    public static final int CAPTURE_REQUEST_CODE = 10;
    public static final int PREVIEW_REQUEST_CODE = 20;

    //SOLICITUD
    public static final String SOL_CELULAR    = "Celular";
    public static final String SOL_TELEFONO   = "Telefono";
    public static final String SOL_TARJETA    = "Tarjeta";
    public static final String SOL_IFE_FRENTE = "IfeFrente";
    public static final String SOL_IFE_VUELTA = "IfeVuelta";
    public static final Boolean CODIGO_VERIFICADO = false;
    public static final Boolean ENABLE_VERIFY = true;

    //App Version
    public static final String APP_VERSION = "1";

    public static final List<Promotor> dummy_promotores = new ArrayList<>();
    static {
        dummy_promotores.add(new Promotor(false, "Ruiz", "Lopez",           12345671,"Saul", "PROM23"));
        dummy_promotores.add(new Promotor(true, "Hernandez", "Hernandez",   12345672,"Pablo", "PROM24"));
        dummy_promotores.add(new Promotor(false, "Santana", "Delgado",      12345673,"Winnie", "PROM25"));
        dummy_promotores.add(new Promotor(true, "Cruz", "Salgado",          12345674,"Roberto", "PROM26"));
        dummy_promotores.add(new Promotor(false, "Ruiz", "Lopez",           12345675,"Saul", "PROM27"));
        dummy_promotores.add(new Promotor(true, "Orozco", "Orozco",         12345676,"Omar", "PROM28"));
    }

    public static final int SOLICITUD_IFE_INE_FRENTE    = 1;
    public static final int SOLICITUD_IFE_INE_REVERSO   = 2;
    public static final int SOLICITUD_ADULTO_MAYOR      = 3;

    public static final List<Documento> DOCUMENTS_LIST = new ArrayList<>();
    static {
        DOCUMENTS_LIST.add(new Documento(SOLICITUD_IFE_INE_FRENTE, "IFE/INE FRENTE",         "", 0, "", ""));
        DOCUMENTS_LIST.add(new Documento(SOLICITUD_IFE_INE_REVERSO, "IFE/INE REVERSO",        "", 0, "", ""));
        DOCUMENTS_LIST.add(new Documento(SOLICITUD_ADULTO_MAYOR, "TARJETA ADULTO MAYOR",   "", 0, "", ""));
    }

    public static final List<Cards> DOCUMENTS = new ArrayList<>();
    static {
        DOCUMENTS.add(new Cards(R.drawable.btn_tarjeta_ap,  R.drawable.ic_tarjeta_ap,   R.drawable.ic_check_ap, DOCUMENTS_LIST.get(2)));
        DOCUMENTS.add(new Cards(R.drawable.btn_inefrente_ap,R.drawable.ic_inefront_ap,  R.drawable.ic_check_ap, DOCUMENTS_LIST.get(0)));
        DOCUMENTS.add(new Cards(R.drawable.btn_inevuelta_ap,R.drawable.ic_ineback_ap,   R.drawable.ic_check_ap, DOCUMENTS_LIST.get(1)));
    }


    public static final int SOLICITUD_ADULTO_MAYOR_INDEX             = 0;
    public static final int SOLICITUD_IFE_INE_FRENTE_INDEX           = 1;
    public static final int SOLICITUD_IFE_INE_REVERSO_INDEX          = 2;
}
