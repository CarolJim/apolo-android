package com.pagatodo.apolo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.EditText;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.login.LoginActivity;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rvargas on 21-07-17.
 */

public class ConfirmateActivity extends Activity {
    @BindView(R.id.numeroFolio) MaterialTextView numeroFolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmate);
        ButterKnife.bind(this);

        int randomNum = 5 + (int)(Math.random() * 10);
        String generate = getString(R.string.folio_generado, randomNum);
        numeroFolio.setText(generate);
    }

    @OnClick(R.id.btnFinalizar)
    public void end() {
        App afiliado = App.getInstance();
        afiliado.clearHashMap();
        startActivity(new Intent(this,LoginActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}