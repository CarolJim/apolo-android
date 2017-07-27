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
    public static final String URL_SERVER_MEGA 	        = "http://10.10.45.16:62703/ServiceMega.svc/http";
    public static final String URL_REMOTE_CONFIG        = "https://apolo-8e38a.firebaseapp.com/config.json";
    public static final int TIMEOUT = 40000;

    //BASE DE DATOS
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "apolo.db";

    //CAMERA
    public static final int REQUEST_CAMERA_PERMISSION = 200;
    public static final String KEY_IS_CAPTURING     = "is_capturing";
    public static final String IMAGE_DIRECTORY_NAME = "Apolo";
    public static final String TYPE_CAPTURE = "TypeCapture";

    //SOLICITUD
    public static final String SOL_CELULAR    = "Celular";
    public static final String SOL_TELEFONO   = "Telefono";
    public static final String SOL_TARJETA    = "Tarjeta";
    public static final String SOL_IFE_FRENTE = "IfeFrente";
    public static final String SOL_IFE_VUELTA = "IfeVuelta";
    public static String UrlTarjeta;
    public static String UrlIfeFrente;
    public static String UrlIfeVuelta;


}
