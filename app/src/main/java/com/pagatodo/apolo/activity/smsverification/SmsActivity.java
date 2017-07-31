package com.pagatodo.apolo.activity.smsverification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.register.RegisterActivity;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsPresenter;
import com.pagatodo.apolo.activity.smsverification._presenter.SmsPresenterImpl;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsView;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterActivity;
import com.pagatodo.apolo.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.App.instance;

/**
 * Created by rvargas on 21-07-17.
 */

public class SmsActivity extends BasePresenterActivity<SmsPresenter> implements SmsView, View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {
    private final String TAG = "SmsActivity";
    @BindView(R.id.layoutSms) CoordinatorLayout layoutSms;
    @BindView(R.id.btnValidar) Button btnValidar;
    @BindView(R.id.pin_one) EditText mPinOne;
    @BindView(R.id.pin_two) EditText mPinTwo;
    @BindView(R.id.pin_three) EditText mPinThree;
    @BindView(R.id.pin_four) EditText mPinFour;
    @BindView(R.id.pin_five) EditText mPinFive;
    @BindView(R.id.pin_six)  EditText mPinSix;
    @BindView(R.id.editText_otp) EditText editTextOtp;
    @BindView(R.id.progress_view_activity) LinearLayout progressBar;
    private String codeGenerate = "";
    private boolean isPaused = false;
    private boolean isCanceled = false;
    private long timeRemaining = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        ButterKnife.bind(this);
        setPINListeners();

        //Setting Activity ToolBar reference
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        //Enabling Tool Bar Back Arrow Function
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Changing Tool Bar Back Arrow Color
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.DST); // Reference -> https://developer.android.com/reference/android/graphics/PorterDuff.Mode.html
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        initPresentConfirmation();
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

    /* Presenter confirmation celular and after receive sms code
    *********************************
    */
    public void initPresentConfirmation(){
        ViewGroup group = (ViewGroup) findViewById(R.id.allClear);
        //clearEditext(group);
        startTimer();
        btnValidar.setEnabled(false);
        presenter.confirmation(instance.get(Constants.SOL_CELULAR));
    }
    @Override
    public void onSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onFailed(String message) {
        resumeTimer();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startTimer(){
        isPaused = false;
        isCanceled = false;
        long millisInFuture    = 30000; //30 seconds
        long countDownInterval = 1000; //1 second

        new CountDownTimer(millisInFuture,countDownInterval){
            public void onTick(long millisUntilFinished){
                if(isPaused || isCanceled) {
                    cancel();
                }
                else {
                    btnValidar.setText(getString(R.string.txt_button_reenviar) + " " + millisUntilFinished / 1000);
                    timeRemaining = millisUntilFinished;
                }
            }
            public void onFinish(){
                btnValidar.setText(getString(R.string.txt_button_reenviar));
            }
        }.start();
    }

    private void resumeTimer(){
        isPaused = false;
        isCanceled = false;
        long millisInFuture = timeRemaining;
        long countDownInterval = 1000;
        new CountDownTimer(millisInFuture, countDownInterval){
            public void onTick(long millisUntilFinished){
                if(isPaused || isCanceled) {
                    cancel();
                }
                else {
                    btnValidar.setText(getString(R.string.txt_button_reenviar) + " " + millisUntilFinished / 1000);
                    timeRemaining = millisUntilFinished;
                }
            }
            public void onFinish(){
                btnValidar.setText(getString(R.string.txt_button_reenviar));
            }
        }.start();
    }


    /* Presenter validation code received
    *********************************
    */
    @OnClick(R.id.btnValidar)
    public void validar() {
        if(btnValidar.getText()==getString(R.string.txt_button_reenviar))
            initPresentConfirmation();
        else
            validateForm();
    }

    public void validateForm() {
        getDataForm();

        Log.e("-------------------","------------------------->"+codeGenerate);
        if(codeGenerate.isEmpty()){
            showMessage(getString(R.string.error_codigo_empty));
            return;
        }
        onValidationSuccess();
    }

    public void onValidationSuccess() {
        codeGenerate = codeGenerate.replace(" ", "");
        presenter.validation(instance.get(Constants.SOL_CELULAR), codeGenerate);
    }

    @Override
    public void setNavigation() {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        final int id = v.getId();
        switch (id) {
            case R.id.pin_one:
                if (hasFocus) {
                    setFocus(editTextOtp);
                    showSoftKeyboard();
                }
                break;
            case R.id.pin_two:
                if (hasFocus) {
                    setFocus(editTextOtp);
                    showSoftKeyboard();
                }
                break;
            case R.id.pin_three:
                if (hasFocus) {
                    setFocus(editTextOtp);
                    showSoftKeyboard();
                }
                break;
            case R.id.pin_four:
                if (hasFocus) {
                    setFocus(editTextOtp);
                    showSoftKeyboard();
                }
                break;
            case R.id.pin_five:
                if (hasFocus) {
                    setFocus(editTextOtp);
                    showSoftKeyboard();
                }
                break;
            case R.id.pin_six:
                if (hasFocus) {
                    setFocus(editTextOtp);
                    showSoftKeyboard();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = v.getId();

            switch (id) {
                case R.id.editText_otp:
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (editTextOtp.getText().length() == 6)
                            mPinSix.setText("");
                        else if (editTextOtp.getText().length() == 5)
                            mPinFive.setText("");
                        else if (editTextOtp.getText().length() == 4)
                            mPinFour.setText("");
                        else if (editTextOtp.getText().length() == 3)
                            mPinThree.setText("");
                        else if (editTextOtp.getText().length() == 2)
                            mPinTwo.setText("");
                        else if (editTextOtp.getText().length() == 1)
                            mPinOne.setText("");
                        if (editTextOtp.length() > 0)
                            editTextOtp.setText(editTextOtp.getText().subSequence(0, editTextOtp.length() - 1));
                        return true;
                    }
                    break;

                default:
                    return false;
            }
        }
        return false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length() < 6){
            resumeTimer();
            btnValidar.setText(getString(R.string.txt_button_reenviar));
        }
        if (s.length() == 0) {
            mPinOne.setText("");
        } else if (s.length() == 1) {
            mPinOne.setText(s.charAt(0) + "");
            mPinTwo.setText("");
            mPinThree.setText("");
            mPinFour.setText("");
            mPinFive.setText("");
            mPinSix.setText("");
        } else if (s.length() == 2) {
            mPinTwo.setText(s.charAt(1) + "");
            mPinThree.setText("");
            mPinFour.setText("");
            mPinFive.setText("");
            mPinSix.setText("");
        } else if (s.length() == 3) {
            mPinThree.setText(s.charAt(2) + "");
            mPinFour.setText("");
            mPinFive.setText("");
            mPinSix.setText("");
        } else if (s.length() == 4) {
            mPinFour.setText(s.charAt(3) + "");
            mPinFive.setText("");
            mPinSix.setText("");
        } else if (s.length() == 5) {
            mPinFive.setText(s.charAt(4) + "");
            mPinSix.setText("");
        } else if (s.length() == 6) {
            mPinSix.setText(s.charAt(5) + "");
            hideSoftKeyboard();
            btnValidar.setEnabled(true);
            btnValidar.setText(getString(R.string.txt_button_continue));
            isPaused = true;
        }
    }

    /*** Sets focus on a specific EditText field.*
     * * @param editText EditText to set focus on*/
    public static void setFocus(EditText editText) {
        if (editText == null)
            return;
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    /*** Sets listeners for EditText fields.*/
    private void setPINListeners() {
        editTextOtp.addTextChangedListener(this);
        editTextOtp.setOnKeyListener(this);

        mPinOne.setOnFocusChangeListener(this);
        mPinTwo.setOnFocusChangeListener(this);
        mPinThree.setOnFocusChangeListener(this);
        mPinFour.setOnFocusChangeListener(this);
        mPinFive.setOnFocusChangeListener(this);
        mPinSix.setOnFocusChangeListener(this);

        mPinOne.setOnKeyListener(this);
        mPinTwo.setOnKeyListener(this);
        mPinThree.setOnKeyListener(this);
        mPinFour.setOnKeyListener(this);
        mPinFive.setOnKeyListener(this);
        mPinSix.setOnKeyListener(this);
    }


    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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
                String codigo =  intent.getStringExtra("message");
                editTextOtp.setText(codigo);
                mPinOne.setText(codigo.charAt(1)+"");
                mPinTwo.setText(codigo.charAt(2)+"");
                mPinThree.setText(codigo.charAt(3)+"");
                mPinFour.setText(codigo.charAt(4)+"");
                mPinFive.setText(codigo.charAt(5)+"");
                mPinSix.setText(codigo.charAt(6)+"");
                btnValidar.setEnabled(true);
                btnValidar.setText(getString(R.string.txt_button_continue));
                isPaused = true;
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
        setNavigation();
    }

    public void getDataForm() {
        Log.e("---","*************"+editTextOtp.getText());
        if(codeGenerate != null){
            codeGenerate = String.valueOf(editTextOtp.getText());
            return;
        }
    }

}