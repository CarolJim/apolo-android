package com.pagatodo.apolo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.register.RegisterActivity;
import com.pagatodo.apolo.activity.splash.SplashActivity;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rvargas on 22/09/2017.
 */

public class MenuActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
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
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.btnRegister)
    public void register() {
        showView(RegisterActivity.class);
        finish();
    }
    @OnClick(R.id.btnQuery)
    public void query() {
        showView(WebViewActivity.class);
        finish();
        finish();
    }


    @OnClick(R.id.logout)
    public void endLogout(final View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.option_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem items) {
                switch (items.getItemId()) {
                    case R.id.logoutPromotor:
                        //presenter.logout();
                        //onDestroy();
                        starActivity();
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void starActivity(){
        pref.destroySession();
        Intent myIntent = new Intent(this, SplashActivity.class);
        this.startActivity(myIntent);
    }
}
