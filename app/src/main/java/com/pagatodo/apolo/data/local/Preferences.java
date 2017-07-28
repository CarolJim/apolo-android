package com.pagatodo.apolo.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pagatodo.apolo.data.model.Mensaje;
import com.pagatodo.apolo.data.model.Promotor;
import com.pagatodo.apolo.utils.Constants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.pagatodo.apolo.data.local.PreferencesContract.LIST_NOTIFICATIONS;
import static com.pagatodo.apolo.data.local.PreferencesContract.LIST_PROMOTORS;
import static com.pagatodo.apolo.data.local.PreferencesContract.SESSION_ACTIVE;
import static com.pagatodo.apolo.data.local.PreferencesContract.CURRENT_PROMOTOR;
import static com.pagatodo.apolo.data.local.PreferencesContract.SFECHA_ACTUALIZACION;

/**
 * Created by jvazquez on 19/05/2017.
 */

public class Preferences {

    private SharedPreferences preferences;


    public Preferences(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveData(String key, String data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString(key, data);
        editor.commit();
    }

    public void saveData(String key, Serializable data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString(key, objectToString(data));
        editor.commit();
    }

    public void saveDataBool(String key, boolean data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putBoolean(key, data);
        editor.commit();
    }

    public boolean containsData(String key) {
        return this.preferences.contains(key);
    }

    public Serializable loadData(String key, Boolean isObject) {
        return stringToObject(this.preferences.getString(key, null));
    }
    public boolean loadBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public <T extends Serializable> T loadData(String key, Class<T> type) {
        return type.cast(stringToObject(this.preferences.getString(key, null)));
    }

    public String loadString(String key) {
        return preferences.getString(key, "");
    }


    public void clearPreferences() {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.clear();
        editor.commit();
        return;
    }

    public void clearPreference(String key) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.remove(key);
        editor.commit();
        return;
    }

    public void saveDataInt(String key, int data) {
        SharedPreferences.Editor editor = this.preferences.edit();
        String stringData = Integer.toString(data);
        editor.putString(key, stringData);
        editor.commit();
    }

    public static void createSession(Preferences pref, Promotor promotor) {
        /*Implementamos el manejo de Sesión*/
        pref.saveDataBool(SESSION_ACTIVE,true);
        pref.saveData(CURRENT_PROMOTOR, promotor);
    }
    public void destroySession(){
        List<String> preferenciasList = new ArrayList<>();
        preferenciasList.add(SESSION_ACTIVE);
        preferenciasList.add(CURRENT_PROMOTOR);
        for(String preferencia: preferenciasList){
            clearPreference(preferencia);
        }

    }
    public HashMap<String, String> getHeaders(){
        HashMap<String, String> params = new HashMap<>();
        Promotor promotor =  (Promotor) loadData(CURRENT_PROMOTOR, true);
        if(promotor != null){
            params.put("Content-type", "application/json");
            params.put("Promotor", String.valueOf(promotor.getID_Promotor()));
            return params;
        }
        return params;
    }

    public Promotor getCurrentPromotor()
    {
        Promotor userData =  (Promotor) loadData(CURRENT_PROMOTOR,true);
        if(userData != null)
            return userData;
        return null;
    }

    public boolean getSessionStatus()
    {
        return loadBoolean(SESSION_ACTIVE);
    }

    public String getFechaActualizacion() {
        return this.preferences.getString(SFECHA_ACTUALIZACION, "");
    }
    public List<Mensaje> obtenerColaDeMensajes(){
        Type listType = new TypeToken<ArrayList<Mensaje>>(){}.getType();
        List<Mensaje> mensajes = new Gson().fromJson(loadString(LIST_NOTIFICATIONS), listType);
        return mensajes != null ? mensajes : new ArrayList<Mensaje>();
    }
    private static String objectToString(Serializable obj) {
        if (obj == null)
            return "";
        try {
            ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
            ObjectOutputStream objStream;
            objStream = new ObjectOutputStream(serialObj);
            objStream.writeObject(obj);
            objStream.close();
            return asHexStr(serialObj.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }
    private static String asHexStr (byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (( buf[i] & 0xff) < 0x10)
                strbuf.append("0");
            strbuf.append(Long.toString( buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }
    private static byte[] asBytes (String s) {
        String s2;
        byte[] b = new byte[s.length() / 2];
        int i;
        for (i = 0; i < s.length() / 2; i++) {
            s2 = s.substring(i * 2, i * 2 + 2);
            b[i] = (byte)(Integer.parseInt(s2, 16) & 0xff);
        }
        return b;
    }
    private static Serializable stringToObject(String str) {
        if (str == null || str.length() == 0)
            return null;
        try {
            ByteArrayInputStream serialObj = new ByteArrayInputStream(asBytes(str));
            ObjectInputStream objStream;
            objStream = new ObjectInputStream(serialObj);
            return (Serializable) objStream.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Promotor> getListOfPromotors() {
        Type listType = new TypeToken<ArrayList<Promotor>>(){}.getType();
        List<Promotor> promotors = new Gson().fromJson(loadString(LIST_PROMOTORS), listType);
        return promotors != null ? promotors : new ArrayList<Promotor>();
//        return Constants.dummy_promotores;
    }
}
