package com.pagatodo.apolo.activity.login;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pagatodo.apolo.activity.MenuActivity;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginPresenter;
import com.pagatodo.apolo.activity.login._presenter.LoginPresenterImpl;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginView;
import com.pagatodo.apolo.activity.register.RegisterActivity;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IValidateForms;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterPermissionActivity;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.utils.Constants.MIN_SIZE_ID_AFILIADOR;

/**
 * Created by rvargas on 21-07-17.
 * Modified by cjimenez on 25/02/2019.
 */

public class LoginActivity extends BasePresenterPermissionActivity<LoginPresenter> implements LoginView, IValidateForms,
        AdapterView.OnItemSelectedListener {
    private final String TAG = "LoginActivity";
    @BindView(R.id.edtUserNumber)
    MaterialValidationEditText edtNumber;
    @BindView(R.id.edtPassUser)
    MaterialValidationEditText edtPass;
    @BindView(R.id.spn_iniciativas)
    Spinner spnIniciativas;
    @BindView(R.id.spn_tiendas)
    Spinner spnTiendas;
    @BindView(R.id.btnLogin)
    MaterialButton btnLogin;
    @BindView(R.id.layoutLogin)
    CoordinatorLayout layoutLogin;
    private String ID_Promotor = "";
    private int idIniciativa = 0;
    private int idTienda = 0;
    private MaterialTextView tvVersion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateView(this, R.layout.activity_login);
//        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        validateEditText(btnLogin, edtNumber, edtPass);
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS});
        tvVersion = (MaterialTextView) findViewById(R.id.tv_version);
        initSpinnerAdapter();
    }

    private void initSpinnerAdapter() {
        spnIniciativas.setAdapter(presenter.getAdapterIniciativas(this));
        spnIniciativas.setOnItemSelectedListener(this);
        spnTiendas.setOnItemSelectedListener(this);
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

    private void validateEditText(MaterialButton btnLogin, MaterialValidationEditText edtNumber, MaterialValidationEditText edtPass) {
        if (edtNumber != null && edtPass !=null) {
            ValidateForm.enableBtn(false, btnLogin);
            ValidateForm.validateEditText(btnLogin, edtNumber, edtPass);
        }
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        validateForm();
    }

    @Override
    protected int setIdCoordinatorLayout() {
        return R.id.layoutLogin;//super.setIdCoordinatorLayout();
    }

    @Override
    public void setUserNumberError() {
        showMessage(getString((R.string.error_empty_id_afiliador))); //showSnackBar(layoutLogin,getString((R.string.hint_numero_usuario)));
    }

    @Override
    public void setUserPassError(){
        showMessage(getString((R.string.error_empty_pass_afiliador)));
    }

    @Override
    public void setNavigation() {
        showView(MenuActivity.class);
        finish();
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
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        tvVersion.setText(version);
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
        if (ID_Promotor.isEmpty()) {
            showMessage(getString(R.string.error_empty_id_afiliador));
            return;
        }
        if (ID_Promotor.length() < MIN_SIZE_ID_AFILIADOR) {
            showMessage(getString(R.string.error_min_id_afiliador));
            return;
        }
        if (!presenter.isPromotorActive(ID_Promotor)) {
            showMessage(getString(R.string.error_inactive_promotor));
            return;
        }
        if (spnIniciativas.getChildCount() == 0) {
            showMessage(getString(R.string.error_iniciativas_empty));
            return;
        }
        if (spnTiendas.getChildCount() == 0) {
            showMessage(getString(R.string.error_tiendas_empty));
            return;
        }
        onValidationSuccess();
    }

    @Override
    public void onValidationSuccess() {
        presenter.login(ID_Promotor, idIniciativa, idTienda);
    }

    @Override
    public void getDataForm() {
        if (edtNumber != null) {
            ID_Promotor = edtNumber.getText();
            return;
        }
        showMessage(getString(R.string.error_empty_id_afiliador));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spn_iniciativas:
                if (spnIniciativas.getChildCount() != 0) {
                    idIniciativa = presenter.getIdIniciativa(position);
                    spnTiendas.setAdapter(presenter.getAdapterTiendas(this, idIniciativa));
                }
                break;
            case R.id.spn_tiendas:
                if (spnTiendas.getChildCount() != 0) {
                    idTienda = presenter.getIdTienda(position);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}