package com.pagatodo.apolo.activity;

import android.os.Bundle;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.register.RegisterActivity;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;

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
}
