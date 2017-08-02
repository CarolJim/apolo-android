package com.pagatodo.apolo;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import java.util.HashMap;

import com.pagatodo.apolo.data.local.Preferences;

/**
 * Created by jvazquez on 19/05/2017.
 */

public class App extends Application {
    private static App m_singleton;
    public static final String TAG = App.class.getSimpleName();
    HashMap <String,String> solicitud = new HashMap<>();
    private Preferences prefs;
    public static final App instance = App.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        m_singleton = this;
        solicitud = new HashMap<>();
        prefs = new Preferences(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public static App getInstance(){
        if (m_singleton == null){
            m_singleton = new App();
        }
        return m_singleton;
    }

    public void put(String key, String value) {
        solicitud.put(key, value);
    }

    public String get(String key) {
        return solicitud.get(key);
    }

    public void clearHashMap(){ solicitud.clear(); }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public Preferences getPrefs() {
        return this.prefs;
    }

}
