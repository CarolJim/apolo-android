package com.pagatodo.apolo.activity;

import android.os.Bundle;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.login.LoginActivity;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.App.instance;

/**
 * Created by rvargas on 21-07-17.
 */

public class ConfirmateActivity extends BaseActivity {
    @BindView(R.id.numeroFolio) MaterialTextView numeroFolio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmate);
        ButterKnife.bind(this);

        String generate = getString(R.string.folio_generado,  "000010000");
        numeroFolio.setText(generate);
    }

    @Override
    protected int setIdMainView() {
        return 0;
    }

    @Override
    protected int setIdContainerFragments() {
        return 0;
    }

    @OnClick(R.id.btnFinalizar)
    public void end() {
        instance.clearHashMap();
        showView(LoginActivity.class);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}