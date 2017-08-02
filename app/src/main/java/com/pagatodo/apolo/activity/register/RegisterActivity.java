package com.pagatodo.apolo.activity.register;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.CaptureActivity;
import com.pagatodo.apolo.activity.ConfirmateActivity;
import com.pagatodo.apolo.activity.PreviewImageActivity;
import com.pagatodo.apolo.activity.register._presenter.RegisterPresenterImpl;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterPresenter;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterView;
import com.pagatodo.apolo.activity.smsverification.SmsActivity;
import com.pagatodo.apolo.data.adapters.CustomAdapter;
import com.pagatodo.apolo.data.model.Cards;
import com.pagatodo.apolo.data.model.Documento;
import com.pagatodo.apolo.data.model.FormularioAfiliacion;
import com.pagatodo.apolo.data.model.Promotor;
import com.pagatodo.apolo.data.remote.RemoteConfig;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterPermissionActivity;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IValidateForms;
import com.pagatodo.apolo.utils.Constants;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.App.instance;
import static com.pagatodo.apolo.ui.UI.showSnackBar;
import static com.pagatodo.apolo.ui.UI.showToast;
import static com.pagatodo.apolo.ui.base.BaseEventContract.DOCUMENTS_RV_ITEM_SELECTED;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_REGISTERED;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_REGISTER_REINTENT;
import static com.pagatodo.apolo.ui.base.BaseEventContract.KEY_FOLIO;

public class RegisterActivity extends BasePresenterPermissionActivity<RegisterPresenter> implements RegisterView, IValidateForms{
    private static final String DIALOG_PROGRESS_REGISTER = "dialogProgressRegister";

    private final String TAG = "MainActivity";
    private CustomAdapter adapter;
    private List<Cards> cardsList = Constants.DOCUMENTS;
    @BindView(R.id.recycler_view_card) RecyclerView recyclerView;
    @BindView(R.id.btnRegister) MaterialButton btnRegister;
    @BindView(R.id.layoutRegister) CoordinatorLayout layoutRegister;
    @BindView(R.id.edtCellPhone) MaterialValidationEditText edtCellPhone;
    @BindView(R.id.edtPhone) MaterialValidationEditText edtPhone;
    @BindView(R.id.tv_name_afiliado) MaterialTextView tvAfiliado;
    @BindView(R.id.ivVerify) AppCompatImageView ivVerify;

    private Promotor mPromotor = new Promotor();
    private StatusProgresFragment statusProgresFragment = null;

    private Documento rvSelectedItem;

    private final static int CAPTURE_REQUEST_CODE = 10;
    private final static int PREVIEW_REQUEST_CODE = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new CustomAdapter(this, cardsList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        validateEditText(btnRegister, edtCellPhone);
        initData();
        mPromotor = pref.getCurrentPromotor();
        setValuesDefaultForm();
        initFragments();

    }

    private void initFragments() {
        statusProgresFragment = StatusProgresFragment.newInstance(getTasks());
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
    @OnClick(R.id.ivVerify)
    public void sms(){
        assignData();
        getDataForm();
        if(!edtCellPhone.isValidField()){
            showMessage(getString(R.string.error_cellphone_invalid));
            return;
        }
        showView(SmsActivity.class);
    }

    @Override
    public void setMessageError(String message) {
        showSnackBar(layoutRegister, message);
    }

    @Override
    public void setNavigation() {
        showView(ConfirmateActivity.class);
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
    public void errorRegister(String message) {
        if(statusProgresFragment != null){
            statusProgresFragment.setErrorRegister(message);
        }
    }

    @Override
    public void successRegister() {
        if(statusProgresFragment != null){
            statusProgresFragment.isSuccessRegister();
        }
    }

    @Override
    public void errorUploadDocument(Documento documento, String message) {
        if(statusProgresFragment != null){
            statusProgresFragment.upladDocumentFailed();
       }
    }

    @Override
    public void successUploadDocument(Documento documento) {
        statusProgresFragment.uploadDocumentSuccess();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isDeviceSupportCamera()) {
            showToast(getString((R.string.not_compatible_camera)), getApplicationContext());
            finish();
        }
        enableVerificateSMS(RemoteConfig.isEnableVerificateSMS());
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

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case DOCUMENTS_RV_ITEM_SELECTED:
                rvSelectedItem = (Documento) data;
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
                break;
            case EVENT_REGISTER_REINTENT:
                statusProgresFragment = StatusProgresFragment.newInstance(getTasks());
                if(getFormularioAfiliacion().getFolio().isEmpty()){
                    presenter.requestRegister();
                }else{
                    presenter.uploadPendingDocument();
                }
                showProgressFragment();
                break;
            case EVENT_REGISTERED:
                Intent i = new Intent(RegisterActivity.this, ConfirmateActivity.class);
                i.putExtra(KEY_FOLIO, presenter.getFolio());
                startActivity(i);
                finish();
                break;
        }
    }

    private int getTasks() {
        if(!getFormularioAfiliacion().getFolio().isEmpty()){
            int pendingTasks = 0;
            for(Documento documento: getFormularioAfiliacion().getDocumentos()){
                if(!documento.isUploaded()){
                    pendingTasks++;
                }
            }
            return pendingTasks;
        }
        return getFormularioAfiliacion().getDocumentos().size() + 1;
    }

    protected void doPermissionsGrantedAction() {
        assignData();
        HashMap<String, Serializable> extras = new HashMap<>();

        if(presenter.doesDocumentExist(rvSelectedItem))
        {
            extras.put(Constants.SELECTED_DOCUMENT_KEY, getFormularioAfiliacion().getDocumentos().get(presenter.getDocumentPosition(rvSelectedItem)));
            startActivityForResult(PreviewImageActivity.class, PREVIEW_REQUEST_CODE, extras);
        }
        else
        {
            extras.put(Constants.SELECTED_DOCUMENT_KEY, rvSelectedItem);
            startActivityForResult(CaptureActivity.class, CAPTURE_REQUEST_CODE, extras);
        }
    }

    @Override
    public void setValuesDefaultForm() {
        tvAfiliado.setText(getNamePromotor());
    }

    private String getNamePromotor(){
        String nombre           = mPromotor.getNombre() != null ? mPromotor.getNombre():"";
        String apellido         = mPromotor.getApellidoPaterno() != null ? mPromotor.getApellidoPaterno(): "";
        String apellidoMaterno  = mPromotor.getApellidoMaterno() != null ? mPromotor.getApellidoMaterno(): "";
        return  getString(R.string.name_agente_format, nombre, apellido, apellidoMaterno);
    }
    @OnClick(R.id.btnRegister)
    public void goRegister(){
        validateForm();
    }
    @Override
    public void validateForm() {
        getDataForm();
        if(getFormularioAfiliacion().getTelefonoMovil().isEmpty()){
            showMessage(getString(R.string.error_cellphone_empty));
            return;
        }
        if(!edtCellPhone.isValidField()){
            showMessage(getString(R.string.error_phone_empty));
            return;
        }
        String errorDocument = "";
        for(Documento documento: getFormularioAfiliacion().getDocumentos()){
            if(documento.getDocumentoBase64().isEmpty() || documento.getLongitud() == 0){
                errorDocument = documento.getNombre();
                break;
            }
        }
        if(!errorDocument.isEmpty()){
            showMessage(getString(R.string.error_listaDocumentoVacio, errorDocument));
            return;
        }
        onValidationSuccess();
    }

    @Override
    public void onValidationSuccess() {
        showProgressFragment();
        presenter.requestRegister();
    }

    @Override
    public void getDataForm() {
        getFormularioAfiliacion().setTelefonoCasa(edtPhone.getText());
        getFormularioAfiliacion().setTelefonoMovil(edtPhone.getText());
    }
    private void enableVerificateSMS(boolean enable){
        if(ivVerify != null && !edtCellPhone.isValidField()){
            pref.saveDataBool(String.valueOf(Constants.CODIGO_VERIFICADO),false);
            ivVerify.setVisibility(enable ? View.VISIBLE : View.GONE);
            ivVerify.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_verificar_ap));
        } else if(pref.loadBoolean(String.valueOf(Constants.CODIGO_VERIFICADO))){
            ivVerify.setEnabled(false);
            ivVerify.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_verificado_ap));
        } else {
            ivVerify.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_noverificado_ap));
        }
    }
    public  void showProgressFragment() {
        if (statusProgresFragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag(DIALOG_PROGRESS_REGISTER);
            if (prev != null) {
                ft.remove(prev);
            }
            statusProgresFragment.setCancelable(false);
            statusProgresFragment.show(ft, DIALOG_PROGRESS_REGISTER);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAPTURE_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK)
                    if (data.getExtras().containsKey(RESULT_KEY))
                        updateList((Documento) data.getSerializableExtra(RESULT_KEY), true);
                break;
            case PREVIEW_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK)
                    updateList(rvSelectedItem, false);
                break;
        }
    }


    private void updateList(Documento currentDocument, boolean shouldAddDocument)
    {
        int currentIndex = presenter.getDocumentPosition(currentDocument);
        View currentView = recyclerView.getLayoutManager().findViewByPosition(presenter.getListPosition(currentDocument));
        AppCompatImageView ivCheck = currentView.findViewById(R.id.ivCheck);
        ArrayList<Documento> documents = (ArrayList<Documento>) getFormularioAfiliacion().getDocumentos();

        if(shouldAddDocument) {
            ivCheck.setImageResource(R.drawable.ic_check_ap);
            documents.remove(currentIndex);
            documents.add(currentIndex, currentDocument);
        }else {
            ivCheck.setImageResource(R.drawable.ic_nocheck_ap);
            documents.get(currentIndex).setLongitud(0);
        }
    }
    private FormularioAfiliacion getFormularioAfiliacion(){
        return presenter.getFormularioAfiliacion();
    }
}
