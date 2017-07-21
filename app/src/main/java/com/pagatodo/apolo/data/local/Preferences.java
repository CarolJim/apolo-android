package com.pagatodo.apolo.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.pagatodo.apolo.data.pojo.Users;
import com.pagatodo.apolo.data.pojo.webservice.UserProperties;
import java.io.Serializable;
import java.util.HashMap;

import static com.pagatodo.apolo.data.local.PreferencesContract.SESSION_ACTIVE;
import static com.pagatodo.apolo.data.local.PreferencesContract.USER_DATA;
import static com.pagatodo.apolo.data.local.PreferencesContract.USER_PROPERTIES;
import static com.pagatodo.apolo.utils.Constants.APP_VERSION;
import static com.pagatodo.apolo.utils.Constants.ID_PROGRAMA;
import static com.pagatodo.apolo.utils.Utils.objectToString;
import static com.pagatodo.apolo.utils.Utils.stringToObject;


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


    public HashMap<String, String> getHeaders(){
        HashMap<String, String> params = new HashMap<>();
        UserProperties userProperties =  (UserProperties) loadData(USER_PROPERTIES, true);
        if(userProperties != null){
            params.put("Content-type", "application/json");
            params.put("idPrograma", ID_PROGRAMA);
            params.put("idUsuario", userProperties.getIdUsuario());
            params.put("tokenUsuario", userProperties.getTokenUsuario());
            params.put("version", APP_VERSION);
            params.put("SO", "Android");
            return params;
        }
        params.put("Content-Type", "application/json");
        params.put("idPrograma", ID_PROGRAMA);
        params.put("version",APP_VERSION);
        params.put("so", "Android");
        return params;
    }

    public Users getUserData() {
        Users userData =  (Users) loadData(USER_DATA,true);
        if(userData != null)
            return userData;
        return null;
    }

    public boolean getSessionStatus()
    {
        return loadBoolean(SESSION_ACTIVE);
    }

}