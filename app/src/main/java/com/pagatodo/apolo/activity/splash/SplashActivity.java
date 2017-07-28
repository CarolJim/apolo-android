package com.pagatodo.apolo.activity.splash;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.login.LoginActivity;
import com.pagatodo.apolo.activity.register.RegisterActivity;
import com.pagatodo.apolo.activity.splash._presenter.SplashPresenter;
import com.pagatodo.apolo.activity.splash._presenter._interfaces.ISplashPresenter;
import com.pagatodo.apolo.activity.splash._presenter._interfaces.ISplashView;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.apolo.data.local.PreferencesContract.SESSION_ACTIVE;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_RE_GET_PROMOTORS;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_SALIR;

public class SplashActivity extends BasePresenterActivity<ISplashPresenter> implements ISplashView{

    @BindView(R.id.layout_splash)
    FrameLayout layout;
    @BindView(R.id.ic_launcher) ImageView image_icon;
    Boolean session = false;
    private Handler splashHandler = new Handler();
    private final static int HANDLER_DELAY = 3000;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        inflateView(this, R.layout.activity_splash);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        StartAnimations();
        presenter.getPromotersList();
    }

    @Override
    protected int setIdMainView() {
        return 0;
    }

    @Override
    protected int setIdContainerFragments() {
        return 0;
    }

    @Override
    protected void initializePresenter() {
        presenter = new SplashPresenter(this);
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.alpha);
        anim.reset();
        layout.clearAnimation();
        layout.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.translate);
        anim.reset();
        image_icon.clearAnimation();
        image_icon.startAnimation(anim);
    }

    @Override
    protected int setIdProgress() {
        return R.id.progressSplash;
        //return super.setIdProgress();
    }

    @Override
    public void updatePromotorsSuccess() {
        splashHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, pref.containsData(SESSION_ACTIVE) ? RegisterActivity.class : LoginActivity.class);
                startActivity(i);
            }
        },HANDLER_DELAY);
    }

    @Override
    public void updatePromotorsFailed(String title, String message) {
        showDialog(title, message, android.R.drawable.ic_dialog_alert, getString(R.string.txt_reintent), EVENT_RE_GET_PROMOTORS, getString(R.string.txt_exit), EVENT_SALIR);
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

    @Override
    public void showProgress(String message) {
        super.showProgress(message);
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event){
            case EVENT_RE_GET_PROMOTORS:
                presenter.getPromotersList();
                break;
            case EVENT_SALIR:
                pref.destroySession();
                finish();
                break;
        }
    }
}

