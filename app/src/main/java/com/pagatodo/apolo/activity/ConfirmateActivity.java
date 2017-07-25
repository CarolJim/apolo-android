package com.pagatodo.apolo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.login.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rvargas on 21-07-17.
 */

public class ConfirmateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmate);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnFinalizar)
    public void end() {
        startActivity(new Intent(this,LoginActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}