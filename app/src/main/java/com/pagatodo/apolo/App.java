package com.pagatodo.apolo;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.pagatodo.apolo.data.local.Preferences;

/**
 * Created by jvazquez on 19/05/2017.
 */

public class App extends Application {
    private static App m_singleton;
    public static final String TAG = App.class.getSimpleName();
    private RequestQueue mRequestQueue;
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

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public Preferences getPrefs() {
        return this.prefs;
    }

}
