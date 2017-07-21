package com.pagatodo.apolo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.utils.checkPermission;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.edtUserNumber) MaterialValidationEditText userNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.action_next)
    public void next() {
               Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission permiso = new checkPermission(this) ;
        permiso.checarPermiso();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}

