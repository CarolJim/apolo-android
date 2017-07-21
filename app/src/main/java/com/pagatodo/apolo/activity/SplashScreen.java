package com.pagatodo.apolo.activity;

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
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreen extends Activity {

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
    private void StartAnimations() {
        Thread timer = new Thread() {
            public void run() {
                try {
                    Animation anim = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.alpha);
                    anim.reset();

                    layout.clearAnimation();
                    layout.startAnimation(anim);

                    anim = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.translate);
                    anim.reset();

                    image_icon.clearAnimation();
                    image_icon.startAnimation(anim);
                    sleep(4000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(SplashScreen.this, session ? MainActivity.class : LoginActivity.class);
                    startActivity(i);
                }
            }
        };
        timer.start();
    }
}

