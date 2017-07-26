package com.pagatodo.apolo.activity.smsverification;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsPresenter;
import com.pagatodo.apolo.activity.smsverification._presenter.SmsPresenterImpl;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsView;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.pagatodo.apolo.ui.UI.showSnackBar;

/**
 * Created by rvargas on 21-07-17.
 */

public class SmsActivity extends BasePresenterActivity<SmsPresenter> implements SmsView, View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {

    @BindView(R.id.layoutSms) CoordinatorLayout layoutSms;
    @BindView(R.id.btnVerificar) Button btnVerificar;
    @BindView(R.id.pin_one) EditText mPinOne;
    @BindView(R.id.pin_two) EditText mPinTwo;
    @BindView(R.id.pin_three) EditText mPinThree;
    @BindView(R.id.pin_four) EditText mPinFour;
    @BindView(R.id.pin_five) EditText mPinFive;
    @BindView(R.id.pin_six)  EditText mPinSix;
    @BindView(R.id.pin_hidden_edittext) EditText mPinHiddenEditText;
    @BindView(R.id.progress_view_activity) LinearLayout progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        ButterKnife.bind(this);
        setPINListeners();
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

    @OnClick(R.id.btnVerificar)
    public void verificar() {
        presenter.verify(mPinHiddenEditText.getText().toString());
    }

    @Override
    public void setCodigoError() {
        showSnackBar(layoutSms,getString((R.string.ingresa_codigo)));
    }

    @Override
    public void setNavigation() {
        //startActivity(new Intent(this,RegisterActivity.class));
        onBackPressed();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
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
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pin_two:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pin_three:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pin_four:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.pin_five:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;
            case R.id.pin_six:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
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
                case R.id.pin_hidden_edittext:
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (mPinHiddenEditText.getText().length() == 6)
                            mPinSix.setText("");
                        else if (mPinHiddenEditText.getText().length() == 5)
                            mPinFive.setText("");
                        else if (mPinHiddenEditText.getText().length() == 4)
                            mPinFour.setText("");
                        else if (mPinHiddenEditText.getText().length() == 3)
                            mPinThree.setText("");
                        else if (mPinHiddenEditText.getText().length() == 2)
                            mPinTwo.setText("");
                        else if (mPinHiddenEditText.getText().length() == 1)
                            mPinOne.setText("");

                        if (mPinHiddenEditText.length() > 0)
                            mPinHiddenEditText.setText(mPinHiddenEditText.getText().subSequence(0, mPinHiddenEditText.length() - 1));

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
            btnVerificar.setText(getString(R.string.txt_button_reenviar));
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
            hideSoftKeyboard(mPinSix);
            btnVerificar.setText(getString(R.string.txt_button_continue));
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
        mPinHiddenEditText.addTextChangedListener(this);

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
        mPinHiddenEditText.setOnKeyListener(this);
    }

    /*** Shows soft keyboard.
     * @param editText EditText which has focus*/
    public void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;
        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }
    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /*** Hides soft keyboard.* @param editText EditText which has focus*/
    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;
        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
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
                final String message = intent.getStringExtra("message");
                mPinHiddenEditText.setText(message);
            }
        }
    };

}