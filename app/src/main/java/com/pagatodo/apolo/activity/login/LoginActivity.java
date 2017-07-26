package com.pagatodo.apolo.activity.login;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.pagatodo.apolo.activity.register.RegisterActivity;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.ValidatePermission;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.pagatodo.apolo.ui.UI.showSnackBar;

/**
 * Created by rvargas on 21-07-17.
 */

public class LoginActivity extends Activity implements LoginView{

    LoginPresenter loginpresenter;
    @BindView(R.id.edtUserNumber) MaterialValidationEditText edtNumber;
    @BindView(R.id.btnLogin) MaterialButton btnLogin;
    @BindView(R.id.layoutLogin) CoordinatorLayout layoutLogin;
    @BindView(R.id.progress_view_activity) LinearLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginpresenter = new LoginPresenterImpl(this);
        ButterKnife.bind(this);
        validateEditText(btnLogin, edtNumber);
        edtNumber.setGravity(Gravity.CENTER | Gravity.LEFT);
        edtNumber.setMaxLength(8);
    }

    private void validateEditText(MaterialButton btnLogin, MaterialValidationEditText edtNumber){
        if(edtNumber != null){
            ValidateForm.enableBtn(false, btnLogin);
            ValidateForm.validateEditText(btnLogin, edtNumber);
        }
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        loginpresenter.login(edtNumber.getText().toString());
    }

    @Override
    public void setUserNumberError() {
        showSnackBar(layoutLogin,getString((R.string.hint_numero_usuario)));
    }

    @Override public void setNavigation() {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }
    @Override public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        hideSoftKeyboard(edtNumber);
    }
    @Override public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ValidatePermission verify = new ValidatePermission(this);
        verify.verifyPermiso();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /*** Hides soft keyboard.* @param editText EditText which has focus*/
    public void hideSoftKeyboard(MaterialValidationEditText editText) {
        if (editText == null)
            return;
        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}