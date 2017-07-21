package com.pagatodo.apolo;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pagatodo.apolo.data.local.Preferences;

/**
 * Created by jvazquez on 19/05/2017.
 */

public class App extends Application {
    private static App m_singleton;
    public static final String TAG = App.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private Preferences preference;

    @Override
    public void onCreate() {
        super.onCreate();
        m_singleton = this;
        preference = new Preferences(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public static App getInstance(){
        return m_singleton;
    }
    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    Preferences getPref() {
        return this.preference;
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
}
