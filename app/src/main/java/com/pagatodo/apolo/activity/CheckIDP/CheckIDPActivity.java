package com.pagatodo.apolo.activity.CheckIDP;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.CheckIDP._presenter._interfaces.CheckIDPPresenter;
import com.pagatodo.apolo.activity.MenuActivity;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterView;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterPermissionActivity;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IValidateForms;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Francisco Manzo on 21-12-17.
 */
public class CheckIDPActivity extends BasePresenterPermissionActivity<CheckIDPPresenter> {
    private final String TAG = CheckIDPActivity.class.getSimpleName();
    @BindView(R.id.edtIDPNumber)
    MaterialValidationEditText edtIDP;
    @BindView(R.id.btnCheck)
    MaterialButton btnCheck;
    @BindView(R.id.layoutLogin)
    CoordinatorLayout layoutLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateView(this, R.layout.activity_check_idp);
//        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        validateEditText(btnCheck, edtIDP);
    }

    private void validateEditText(MaterialButton btnLogin, MaterialValidationEditText edtNumber){
        if(edtNumber != null){
            ValidateForm.enableBtn(false, btnLogin);
            ValidateForm.validateEditText(btnLogin, edtNumber);
        }
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

    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        showView(MenuActivity.class);
        finish();
    }
}
