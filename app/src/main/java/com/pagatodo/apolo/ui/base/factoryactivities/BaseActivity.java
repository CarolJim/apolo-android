package com.pagatodo.apolo.ui.base.factoryactivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.login.LoginActivity;
import com.pagatodo.apolo.ui.dialogs.DialogFactory;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;
import com.pagatodo.networkframework.UtilsNet;

import java.io.Serializable;
import java.util.HashMap;

import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_LOGOUT;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_TOKEN_EXPIRED;

/**
 * Created by jvazquez on 19/05/2017.
 */

public abstract class BaseActivity extends SupportNotificationsActivity implements View.OnClickListener {

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
            showView(BaseActivity.this, LoginActivity.class);
            finish();
        }else{
            showProgressActivity(getString(R.string.progress_logout));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pref.destroySession();
                    showView(BaseActivity.this, LoginActivity.class);
                    finish();
                }
            }, 3000L);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void showView(Context currentView , Class comingView)
    {
        showView(currentView, comingView, null);
    }


    protected void showView(Context currentView, Class comingView, HashMap<String, Serializable> extras)
    {
        showView(currentView, comingView, extras, null);
    }

    protected  void showView(Context currentView, Class comingView, HashMap<String, Serializable> extras, Bundle options)
    {
        Intent intent = new Intent(currentView, comingView);
        //Waiting for Flags Needs
        if(extras != null)
        {
            for (String key : extras.keySet())
            {
                intent.putExtra(key, extras.get(key));
            }
        }
        currentView.startActivity(intent, options);
    }

    protected void showViewIntentBundle(Context currentView, Class comingView, Bundle options)
    {
        Intent intent = new Intent(currentView, comingView);
        for(String key : options.keySet())
        {
            intent.putExtra(key,(Serializable) options.get(key));
        }
        currentView.startActivity(intent, options);
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
