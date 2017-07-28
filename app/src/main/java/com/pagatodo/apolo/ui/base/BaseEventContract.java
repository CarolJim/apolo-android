package com.pagatodo.apolo.ui.base;

/**
 * Created by jvazquez on 19/05/2017.
 *
 * Eventos que desencadenan acciones dentro de la aplicación en la capa activity.
 *
 * Ademas están los TAGS de los fragments para ser manipulados en la capa activity.
 */


public class BaseEventContract {
    //EVENTOS de mensajes
    public static final String EVENT_SHOW_PROGRESS          = "showdialog";
    public static final String EVENT_DISMISS_PROGRESS       = "dismissdialog";
    public static final String EVENT_SHOW_ERROR             = "showError";
    public static final String EVENT_SHOW_MESSAGE           = "showMessage";
    public static final String EVENT_HIDE_KEYBOARD          = "hideKeyboard";
    public static final String EVENT_DISSMISS_DIALOG        = "dimissDialog";
    public static final String EVENT_OPEN_TERMS             = "openTerms";
    public static final String EVENT_ENABLE_BACK_BUTTON     = "enableBackButton";
    public static final String EVENT_SET_TOOLBAR            = "setToolbar";

    //EVENTOS de session
    public static final String EVENT_TOKEN_EXPIRED          = "tokenExpired";
    public static final String EVENT_LOGOUT                 = "logout";

    //Login
    public static final String EVENT_RE_GET_PROMOTORS       = "reGetPromotors";
    public static final String EVENT_SALIR                  = "goExit";


    //Run Time Permissions
    public static final String SHOW_APP_SETTINGS            = "appSettings";
    public static final String RT_OPTION_DECLINED           = "rtWereDeclined";
    public static final String HOME_RT_SHOW_DIALOG          = "HomeViewActivityShowRTFlag";
}
