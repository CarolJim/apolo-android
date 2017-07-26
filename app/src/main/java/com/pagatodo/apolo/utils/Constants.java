package com.pagatodo.apolo.utils;

import android.util.SparseIntArray;
import android.view.Surface;

import java.io.File;

/**
 * Created by rvargas on 14/07/2017.
 */

public class Constants {

    //Enable debug logs
    public static final boolean DEBUG = true;

    //SERVER
    public static final String URL_SERVER               = "";
    public static final String URL_SERVER_MEGA 	        = "";
    public static final String URL_TERMS_AND_CONDITIONS = "";
    public static final String URL_PRIVACY_POLICY       = "";
    public static final String URL_REMOTE_CONFIG        = "";

    //BASE DE DATOS
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "apolo.db";

    //CAMERA
    public static final int REQUEST_CAMERA_PERMISSION = 200;
    public static final String KEY_IS_CAPTURING     = "is_capturing";
    public static final String IMAGE_DIRECTORY_NAME = "Apolo";
    public static final String TYPE_CAPTURE = "TypeCapture";

    //SERVER
    public static final String URL_DEBUG = "https://firebasestorage.googleapis.com/v0/b/dummy-ee94d.appspot.com/o/cards.json?alt=media&token=49105f97-c7e7-4877-9497-36e507cc111b";
    public static final int TIMEOUT = 40000;

    //ID PROGRAMA
    public static final String ID_PROGRAMA = "1";

    //App Version
    public static final String APP_VERSION = "1";

    //KEY HASHMAP
    public static final String KEY_NUMERO_AFILIADO = "KEY_NUMERO_AFILIADO";
    public static final String KEY_CELULAR    = "KEY_CELULAR";
    public static final String KEY_TELEFONO   = "KEY_TELEFONO";
    public static final String KEY_IFE_FRENTE = "KEY_IFE_FRENTE";
    public static final String KEY_IFE_VUELTA = "KEY_IFE_VUELTA";
    public static final String KEY_TARJETA    = "KEY_CELULAR";


}
