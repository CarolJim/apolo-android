package com.pagatodo.apolo.ui.base.factoryfragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IProcessData;
import com.pagatodo.networkframework.UtilsNet;

import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_ENABLE_BACK_BUTTON;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_SET_TOOLBAR;

/**
 * Created by jvazquez on 19/05/2017.
 */

public abstract class BaseFragment<iProcessData extends IProcessData> extends SupportPresenterFragment<iProcessData> implements View.OnClickListener{

    protected String FRAGMENT_TAG = "";

    protected Gson gson = new Gson();
    protected Preferences pref = null;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = App.getInstance().getPrefs();
        FRAGMENT_TAG = setFragmentTag() != null ? setFragmentTag() : getClass().getSimpleName();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    protected abstract String setFragmentTag();

    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    protected int setIdErrorView() {
        return R.id.errorViewFragment;
    }

    @Override
    protected int setIdProgressView() {
        return R.id.progressViewFragment;
    }

    @ColorInt
    protected int getColor(@ColorRes int color){
        return ContextCompat.getColor(getActivity(), color);
    }
    protected Drawable getDrawable(@DrawableRes int drawable){
        return ContextCompat.getDrawable(getActivity(), drawable);
    }

    protected boolean isOnline(){
        return UtilsNet.isOnline(App.getInstance());
    }

    @Override
    public void onClick(View v) {

    }

    protected void setToolbarToActivity(Toolbar toolbar){
        sendEvent(EVENT_SET_TOOLBAR, toolbar);
    }
    protected void enableToolbarBackButton(boolean enable){
        sendEvent(EVENT_ENABLE_BACK_BUTTON, enable);
    }

    public void doAnalyticsTracking(String itemId)
    {
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent(itemId, bundle);
    }
}