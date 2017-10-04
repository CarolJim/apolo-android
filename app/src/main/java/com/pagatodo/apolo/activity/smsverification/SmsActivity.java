package com.pagatodo.apolo.activity.smsverification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsPresenter;
import com.pagatodo.apolo.activity.smsverification._presenter.SmsPresenterImpl;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsView;
import com.pagatodo.apolo.data.model.webservice.response.GeneralServiceResponse;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterActivity;
import com.pagatodo.apolo.utils.Constants;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;
import com.pagatodo.apolo.utils.customviews.pin_keyboard.PinKeyboardView;
import com.pagatodo.networkframework.DataManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.App.instance;
import static com.pagatodo.apolo.data.remote.RequestContract.SEND_SMS_CONFIRMATION;
import static com.pagatodo.apolo.data.remote.RequestContract.SMS_CODE_VALIDATION;
import static com.pagatodo.apolo.ui.UI.showSnackBar;
import static com.pagatodo.apolo.utils.Constants.TIME_TO_RETURN;
import static com.pagatodo.networkframework.model.ResponseConstants.RESPONSE_CODE_OK;

/**
 * Created by rvargas on 21-07-17.
 */

public class SmsActivity extends BasePresenterActivity<SmsPresenter> implements SmsView {

    private final String TAG = "SmsActivity";
    @BindView(R.id.layoutSms) CoordinatorLayout layoutSms;
    @BindView(R.id.btnValidar) Button btnValidar;
    @BindView(R.id.tvTitle) MaterialTextView tvTitle;
    private int seconds = 30;
    private boolean stopTimer = false;
    private boolean isFinish = false;
    private int timeRemaining = 0;
    private String codigoSMS = null;
    private PinKeyboardView pinView;
    private int pinLength = 6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_sms);
        inflateView(this, R.layout.activity_sms);
        ButterKnife.bind(this);

        //Setting Activity ToolBar reference
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        //Enabling Tool Bar Back Arrow Function
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Changing Tool Bar Back Arrow Color
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.colorText), PorterDuff.Mode.DST); // Reference -> https://developer.android.com/reference/android/graphics/PorterDuff.Mode.html
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        initPresentConfirmation();

        pinView = (PinKeyboardView) findViewById(R.id.pin_entry);
        pinView.setOnPinEnteredListener(new PinKeyboardView.OnPinEnteredListener() {
            @Override
            public void onPinEntered(String pin) {
                ValidateForm.enableBtn(true, (MaterialButton) btnValidar);
                btnValidar.setText(getString(R.string.txt_button_continue));
                stopTimer = true;
            }
        });
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
        presenter = new SmsPresenterImpl(this);
    }

    @Override
    protected int setIdCoordinatorLayout() {
        return R.id.layoutSms;//super.setIdCoordinatorLayout();
    }

    /* Presenter confirmation celular and after receive sms code
    *********************************
    */
    public void initPresentConfirmation(){
        presenter.confirmation(instance.get(Constants.SOL_CELULAR));
    }
    @Override
    public void onSuccess(DataManager dataManager) {
        GeneralServiceResponse response = (GeneralServiceResponse) dataManager.getData();
        switch (response.getRespuesta().getCodigo()) {
            case RESPONSE_CODE_OK:
                switch (dataManager.getMethod()){
                    case SEND_SMS_CONFIRMATION:
                        hideProgress();
                        timer();
                        ValidateForm.enableBtn(false, (MaterialButton) btnValidar);
                        tvTitle.setText(getString(R.string.codigo_sms));
                        pref.saveDataBool(String.valueOf(Constants.ENABLE_VERIFY), false);
                        showSnackBar(layoutSms, response.getRespuesta().getMensaje());
                        break;
                    case SMS_CODE_VALIDATION:
                        pref.saveDataBool(String.valueOf(Constants.ENABLE_VERIFY), false);
                        pref.saveDataBool(String.valueOf(Constants.CODIGO_VERIFICADO),true);
                        setNavigation();
                        break;
                }
                break;
            default:
                hideSoftKeyboard();
                showSnackBar(layoutSms, response.getRespuesta().getMensaje());
                if(dataManager.getMethod()== SMS_CODE_VALIDATION) {
                    ValidateForm.enableBtn(false, (MaterialButton) btnValidar);
                    stopTimer = false;
                    timer();
                }
                break;
        }
    }
    @Override
    public void onFailed(DataManager dataManager) {
        if(dataManager.getData() instanceof String){
            hideProgress();
            showSnackBar(layoutSms, (String)dataManager.getData());
        }
        switch (dataManager.getMethod()){
            case SEND_SMS_CONFIRMATION:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                },TIME_TO_RETURN);
                break;
        }
    }

    private void timer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                timeRemaining = seconds;
                if (timeRemaining < 0) {
                    isFinish = true;
                    hideSoftKeyboard();
                    ValidateForm.enableBtn(true, (MaterialButton) btnValidar);
                    btnValidar.setText(getString(R.string.txt_button_reenviar));
                } else if (stopTimer) {
                    seconds = timeRemaining;
                    btnValidar.setText(getString(R.string.txt_button_continue));
                } else if(!stopTimer) {
                    seconds--;
                    btnValidar.setText(getString(R.string.txt_button_reenviar) + " " + timeRemaining);
                    handler.postDelayed(this, 1000);
                }
            }

        });
    }


    /* Presenter validation code received
    *********************************
    */
    @OnClick(R.id.btnValidar)
    public void validar() {
        if(btnValidar.getText()==getString(R.string.txt_button_reenviar)) {
            if(isOnline()) {
                ValidateForm.enableBtn(false, (MaterialButton) btnValidar);
                initPresentConfirmation();
                seconds = 30;
                stopTimer = false;
                isFinish  = false;
            } else {
                showSnackBar(layoutSms,getString(R.string.network_error));
            }
        } else {
            validateForm();
        }
    }

    public void validateForm() {
        getDataForm();
        if(codigoSMS.isEmpty() || codigoSMS.length() < pinLength){
            showSnackBar(layoutSms,getString(R.string.error_codigo_empty));
            return;
        }
        onValidationSuccess();
    }

    public void onValidationSuccess() {
        if(isOnline())
            presenter.validation(instance.get(Constants.SOL_CELULAR), codigoSMS);
        else
            showSnackBar(layoutSms,getString(R.string.network_error));
    }

    @Override public void setNavigation() {
        //startActivity(new Intent(this,RegisterActivity.class));
        onBackPressed();
    }

    @Override
    public void showProgress(String message) {
        super.showProgress(message);
        hideSoftKeyboard();
    }


    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                presenter.updatePin(intent.getStringExtra("message"));
                ValidateForm.enableBtn(true, (MaterialButton) btnValidar);
                btnValidar.setText(getString(R.string.txt_button_continue));
                stopTimer = true;
            }
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //setNavigation();
        finish();
    }

    public boolean  getDataForm() {
        if(pinView.getText().toString() != null){
            codigoSMS = pinView.getText().toString();
            return true;
        }
        return false;
    }

    @Override
    public void updateSMS(String codigo) {
        pinView.setText(codigo);
    }

}