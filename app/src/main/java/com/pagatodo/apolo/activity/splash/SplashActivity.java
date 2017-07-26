package com.pagatodo.apolo.activity.splash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class SplashActivity extends BasePresenterActivity<ISplashPresenter> implements ISplashView{

    @BindView(R.id.layout_splash) LinearLayout layout;
    @BindView(R.id.ic_launcher) ImageView image_icon;
    Boolean session = false;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        StartAnimations();
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
        Thread timer = new Thread() {
            public void run() {
                try {
                    Animation anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.alpha);
                    anim.reset();
                    layout.clearAnimation();
                    layout.startAnimation(anim);
                    anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.translate);
                    anim.reset();
                    image_icon.clearAnimation();
                    image_icon.startAnimation(anim);
                    sleep(4000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(SplashActivity.this, session ? RegisterActivity.class : LoginActivity.class);
                    startActivity(i);
                }
            }
        };
        timer.start();
    }
}

