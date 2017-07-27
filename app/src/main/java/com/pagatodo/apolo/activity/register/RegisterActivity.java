package com.pagatodo.apolo.activity.register;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.ConfirmateActivity;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterPresenter;
import com.pagatodo.apolo.activity.register._presenter.RegisterPresenterImpl;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterView;
import com.pagatodo.apolo.activity.smsverification.SmsActivity;
import com.pagatodo.apolo.data.adapters.CustomAdapter;
import com.pagatodo.apolo.data.pojo.Cards;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterActivity;
import com.pagatodo.apolo.utils.Constants;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.App.instance;
import static com.pagatodo.apolo.ui.UI.showSnackBar;
import static com.pagatodo.apolo.ui.UI.showToast;

public class RegisterActivity extends BasePresenterActivity<RegisterPresenter> implements RegisterView {
    private final String TAG = "MainActivity";
    private CustomAdapter adapter;
    private List<Cards> cardsList;
    @BindView(R.id.recycler_view_card) RecyclerView recyclerView;
    @BindView(R.id.btnRegister) MaterialButton btnRegister;
    @BindView(R.id.layoutRegister) CoordinatorLayout layoutRegister;
    @BindView(R.id.edtCellPhone) MaterialValidationEditText edtCellPhone;
    @BindView(R.id.edtPhone) MaterialValidationEditText edtPhone;
    @BindView(R.id.tv_name_afiliado) MaterialTextView tvAfiliado;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cardsList = new ArrayList<>();
        adapter = new CustomAdapter(this, cardsList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        presenter.request(cardsList);
        validateEditText(btnRegister, edtCellPhone);
        initData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(Constants.SOL_CELULAR, instance.get(Constants.SOL_CELULAR));
        outState.putString(Constants.SOL_TELEFONO, instance.get(Constants.SOL_TELEFONO));
        outState.putString(Constants.SOL_TARJETA, instance.get(Constants.SOL_TARJETA));
        outState.putString(Constants.SOL_IFE_FRENTE, instance.get(Constants.SOL_IFE_FRENTE));
        outState.putString(Constants.SOL_IFE_VUELTA, instance.get(Constants.SOL_IFE_VUELTA));
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        initData();
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
        presenter = new RegisterPresenterImpl(this);
    }

    private void validateEditText(MaterialButton btnRegister, MaterialValidationEditText edtCellPhone){
        if(edtCellPhone != null && btnRegister != null){
            ValidateForm.enableBtn(false, btnRegister);
            ValidateForm.validateEditText(btnRegister, edtCellPhone);
        }
        edtCellPhone.setMaxLength(10);
        edtPhone.setMaxLength(8);
    }

    @OnClick(R.id.btnRegister)
    public void registrar() {
        presenter.register(edtCellPhone.getText(), edtPhone.getText(), instance.get(Constants.SOL_TARJETA), instance.get(Constants.SOL_IFE_FRENTE),instance.get(Constants.SOL_IFE_VUELTA));
    }

    @OnClick(R.id.ivVerify)
    public void sms(){
        assignData();
        startActivity(new Intent(this,SmsActivity.class));
    }

    @Override
    public void setMessageError(String message) {
        showSnackBar(layoutRegister, message);
    }

    @Override
    public void setNavigation() {
        startActivity(new Intent(this,ConfirmateActivity.class));
    }

    @Override
    public void returnData(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isDeviceSupportCamera()) {
            showToast(getString((R.string.not_compatible_camera)), getApplicationContext());
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /*** Checking device has camera hardware or not* */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    public void assignData(){
        instance.put(Constants.SOL_CELULAR, edtCellPhone.getText());
        instance.put(Constants.SOL_TELEFONO, edtPhone.getText());
    }
    public void initData(){
        edtCellPhone.setText(instance.get(Constants.SOL_CELULAR));
        edtPhone.setText(instance.get(Constants.SOL_TELEFONO));
    }
}
