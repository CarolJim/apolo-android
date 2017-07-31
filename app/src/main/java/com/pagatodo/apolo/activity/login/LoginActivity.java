package com.pagatodo.apolo.activity.login;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginPresenter;
import com.pagatodo.apolo.activity.login._presenter.LoginPresenterImpl;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginView;
import com.pagatodo.apolo.activity.register.RegisterActivity;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IValidateForms;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterPermissionActivity;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.ui.UI.showSnackBar;
import static com.pagatodo.apolo.utils.Constants.MIN_SIZE_ID_AFILIADOR;

/**
 * Created by rvargas on 21-07-17.
 */

public class LoginActivity extends BasePresenterPermissionActivity<LoginPresenter> implements LoginView, IValidateForms{
    private final String TAG = "LoginActivity";
    @BindView(R.id.edtUserNumber) MaterialValidationEditText edtNumber;
    @BindView(R.id.btnLogin) MaterialButton btnLogin;
    @BindView(R.id.layoutLogin) CoordinatorLayout layoutLogin;

    private String ID_Promotor = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateView(this, R.layout.activity_login);
//        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        validateEditText(btnLogin, edtNumber);
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS});
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
        presenter = new LoginPresenterImpl(this);
    }

    private void validateEditText(MaterialButton btnLogin, MaterialValidationEditText edtNumber){
        if(edtNumber != null){
            ValidateForm.enableBtn(false, btnLogin);
            ValidateForm.validateEditText(btnLogin, edtNumber);
        }
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        validateForm();
    }

    @Override
    public void setUserNumberError() {
        showSnackBar(layoutLogin,getString((R.string.hint_numero_usuario)));
    }

    @Override public void setNavigation() {
        showView(RegisterActivity.class);
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

    @Override
    public void showProgress(String message) {
        super.showProgress(message);
        hideSoftKeyboard();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void setValuesDefaultForm() {

    }

    @Override
    public void validateForm() {
        getDataForm();
        if(ID_Promotor.isEmpty()){
            showMessage(getString(R.string.error_empty_id_afiliador));
            return;
        }
        if(ID_Promotor.length() < MIN_SIZE_ID_AFILIADOR){
            showMessage(getString(R.string.error_min_id_afiliador));
            return;
        }
        onValidationSuccess();
    }

    @Override
    public void onValidationSuccess() {
        presenter.login(ID_Promotor);
    }

    @Override
    public void getDataForm() {
        if(edtNumber != null){
            ID_Promotor = edtNumber.getText();
            return;
        }
        showMessage(getString(R.string.error_empty_id_afiliador));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }
}