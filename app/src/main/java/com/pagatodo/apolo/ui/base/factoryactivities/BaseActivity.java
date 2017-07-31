package com.pagatodo.apolo.ui.base.factoryactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.login.LoginActivity;
import com.pagatodo.apolo.ui.dialogs.DialogFactory;
import com.pagatodo.networkframework.UtilsNet;

import java.io.Serializable;
import java.util.HashMap;

import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_LOGOUT;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_TOKEN_EXPIRED;

/**
 * Created by jvazquez on 19/05/2017.
 */

public abstract class BaseActivity extends SupportNotificationsActivity implements View.OnClickListener {

    protected final static String RESULT_KEY            = "result";
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected int setIdCoordinatorLayout() {
        return R.id.clContainer;
    }

    @Override
    protected int setIdProgress() {
        return R.id.progressViewActivity;
    }

    @Override
    protected int setIdErrorView() {
        return R.id.errorViewActivity;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event){
            case EVENT_TOKEN_EXPIRED:
                showDialog(getString(R.string.dialog_title_aviso), getString(R.string.msg_session), R.drawable.warning, getString(R.string.dialog_text_next), EVENT_LOGOUT, null, null);
                break;
            case EVENT_LOGOUT:
                logout(data != null && data instanceof Boolean && (Boolean)data);
                break;
        }
    }

    public void showDialog(String title, String message, @DrawableRes int idResource, String textBtnPrimary, String primaryEvent, String textBtnSecondary, String secondaryEvent) {
        DialogFactory.buildMessageDialog(this,
                title,
                message,
                idResource,
                textBtnPrimary,
                primaryEvent,
                textBtnSecondary,
                secondaryEvent);
    }
    protected void logout(boolean isFast){
        if(isFast){
            pref.destroySession();
            showView(LoginActivity.class);
            finish();
        }else{
            showProgressActivity(getString(R.string.progress_logout));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pref.destroySession();
                    showView(LoginActivity.class);
                    finish();
                }
            }, 3000L);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void showView(Class comingView)
    {
        showView(comingView, null);
    }

    protected void startActivityForResult(Class comingView, int requestCode, HashMap<String, Serializable> extras)
    {
        Intent intent = new Intent(this, comingView);
        if(extras != null)
        {
            for (String key : extras.keySet())
            {
                intent.putExtra(key, extras.get(key));
            }
        }
        startActivityForResult(intent, requestCode);
    }

    protected void onResultCallBack(Serializable result)
    {
        Intent callBackIntent = new Intent();
        if(result != null)
        {
            callBackIntent.putExtra(RESULT_KEY, result);
            setResult(Activity.RESULT_OK, callBackIntent);
        }
        else
            setResult(Activity.RESULT_CANCELED, callBackIntent);
        finish();
    }

    protected void showView(Class comingView, HashMap<String, Serializable> extras)
    {
        showView(comingView, extras, null);
    }

    protected  void showView(Class comingView, HashMap<String, Serializable> extras, Bundle options)
    {
        Intent intent = new Intent(this, comingView);
        //Waiting for Flags Needs
        if(extras != null)
        {
            for (String key : extras.keySet())
            {
                intent.putExtra(key, extras.get(key));
            }
        }
        startActivity(intent, options);
    }

    protected void showViewIntentBundle(Class comingView, Bundle options)
    {
        Intent intent = new Intent(this, comingView);
        for(String key : options.keySet())
        {
            intent.putExtra(key,(Serializable) options.get(key));
        }
        startActivity(intent, options);
    }
    protected boolean isOnline(){
        return UtilsNet.isOnline(this);
    }

    public void doAnalyticsTracking(String itemId)
    {
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent(itemId, bundle);
    }
}
