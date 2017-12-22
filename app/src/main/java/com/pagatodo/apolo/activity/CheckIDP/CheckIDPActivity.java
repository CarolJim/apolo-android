package com.pagatodo.apolo.activity.CheckIDP;

import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.CheckIDP._presenter.IdpInteractorImpl;
import com.pagatodo.apolo.activity.CheckIDP._presenter.IdpPresenterImpl;
import com.pagatodo.apolo.activity.CheckIDP._presenter._interfaces.IdpPresenter;
import com.pagatodo.apolo.activity.CheckIDP._presenter._interfaces.IdpView;
import com.pagatodo.apolo.activity.MenuActivity;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterPermissionActivity;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IValidateForms;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Francisco Manzo on 21-12-17.
 */
public class CheckIDPActivity extends BasePresenterPermissionActivity<IdpPresenter> implements
        IdpView, IValidateForms {

    private final String TAG = CheckIDPActivity.class.getSimpleName();
    @BindView(R.id.edtIDPNumber)
    MaterialValidationEditText edtIDP;
    @BindView(R.id.btnCheck)
    MaterialButton btnCheck;
    @BindView(R.id.layoutLogin)
    CoordinatorLayout layoutLogin;
    private String numberIdp = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateView(this, R.layout.activity_check_idp);
//        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        validateEditText(btnCheck, edtIDP);
    }

    @Override
    protected void initializePresenter() {
        presenter = new IdpPresenterImpl(this);

    }

    private void validateEditText(MaterialButton btnCheck, MaterialValidationEditText edtNumber){
        if(edtNumber != null){
            ValidateForm.enableBtn(false, btnCheck);
            ValidateForm.validateEditText(btnCheck, edtNumber);
        }
    }

    @OnClick(R.id.btnCheck)
    public void login() {
        validateForm();
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
    public void onBackPressed() {
        //moveTaskToBack(true);
        showView(MenuActivity.class);
        finish();
    }

    @Override
    public void setValuesDefaultForm() {

    }

    @Override
    public void validateForm() {
        getDataForm();
        if(numberIdp.isEmpty()){
            showMessage(getString(R.string.error_min_idp));
            return;
        }

        onValidationSuccess();
    }

    @Override
    public void onValidationSuccess() {
        presenter.checkIdp(numberIdp);
    }

    @Override
    public void getDataForm() {
        if(edtIDP != null){
            numberIdp = edtIDP.getText();
            return;
        }
        showMessage(getString(R.string.error_min_idp));
    }

    @Override
    public void setIdpNumberError() {

    }

    @Override
    public void setNavigation() {

    }
}
