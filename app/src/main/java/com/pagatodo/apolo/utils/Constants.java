package com.pagatodo.apolo.utils;

import android.util.SparseIntArray;
import android.view.Surface;

import com.pagatodo.apolo.data.model.Promotor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    //Login
    public static final int MIN_SIZE_ID_AFILIADOR = 8;

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

    //App Version
    public static final String APP_VERSION = "1";

    //KEY HASHMAP
    public static final String KEY_NUMERO_AFILIADO = "KEY_NUMERO_AFILIADO";
    public static final String KEY_NOMBRE_AFILIADO = "KEY_NOMBRE_AFILIADO";
    public static final String KEY_CELULAR    = "KEY_CELULAR";
    public static final String KEY_TELEFONO   = "KEY_TELEFONO";
    public static final String KEY_IFE_FRENTE = "KEY_IFE_FRENTE";
    public static final String KEY_IFE_VUELTA = "KEY_IFE_VUELTA";
    public static final String KEY_TARJETA    = "KEY_TARJETA";

    public static final List<Promotor> dummy_promotores = new ArrayList<>();
    static {
        dummy_promotores.add(new Promotor(false, "Ruiz", "Lopez",           12345671,"Saul", "PROM23"));
        dummy_promotores.add(new Promotor(true, "Hernandez", "Hernandez",   12345672,"Pablo", "PROM24"));
        dummy_promotores.add(new Promotor(false, "Santana", "Delgado",      12345673,"Winnie", "PROM25"));
        dummy_promotores.add(new Promotor(true, "Cruz", "Salgado",          12345674,"Roberto", "PROM26"));
        dummy_promotores.add(new Promotor(false, "Ruiz", "Lopez",           12345675,"Saul", "PROM27"));
        dummy_promotores.add(new Promotor(true, "Orozco", "Orozco",         12345676,"Omar", "PROM28"));
    }
}
