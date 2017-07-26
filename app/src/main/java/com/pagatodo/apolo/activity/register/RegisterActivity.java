package com.pagatodo.apolo.activity.register;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.CaptureActivity;
import com.pagatodo.apolo.activity.ConfirmateActivity;
import com.pagatodo.apolo.activity.smsverification.SmsActivity;
import com.pagatodo.apolo.data.adapters.CustomAdapter;
import com.pagatodo.apolo.data.pojo.Cards;
import com.pagatodo.apolo.utils.Constants;
import com.pagatodo.apolo.utils.RecyclerItemClickListener;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.ui.UI.showSnackBar;
import static com.pagatodo.apolo.ui.UI.showToast;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    RegisterPresenter registerpresenter;
    private final String TAG = "MainActivity";
    private CustomAdapter adapter;
    private List<Cards> cardsList;
    @BindView(R.id.recycler_view_card) RecyclerView recyclerView;
    @BindView(R.id.btnRegister) MaterialButton btnRegister;
    @BindView(R.id.layoutRegister) CoordinatorLayout layoutRegister;
    @BindView(R.id.edtCellPhone) MaterialValidationEditText edtCellPhone;
    @BindView(R.id.edtPhone) MaterialValidationEditText edtPhone;
    @BindView(R.id.tv_name_afiliado) MaterialTextView tvAfiliado;
    App afiliado = App.getInstance();
    private static int positionItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerpresenter = new RegisterPresenterImpl(this);
        ButterKnife.bind(this);

        cardsList = new ArrayList<>();
        adapter = new CustomAdapter(this, cardsList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new  RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        positionItem = position;
                        Intent i = new Intent(RegisterActivity.this, CaptureActivity.class);
                        i.putExtra(Constants.TYPE_CAPTURE,position);
                        startActivity(i);
                    }
                })
        );
        registerpresenter.request(cardsList);
        validateEditText(btnRegister, edtCellPhone);
        tvAfiliado.setText(afiliado.get(Constants.KEY_NOMBRE_AFILIADO) );
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
        Log.e("---->"," route "+ afiliado.get(Constants.KEY_TARJETA) +  " -- " +   afiliado.get(Constants.KEY_IFE_FRENTE)  + " -- " + afiliado.get(Constants.KEY_IFE_VUELTA));
        registerpresenter.register(edtCellPhone.getText().toString(), edtPhone.getText().toString(), afiliado.get(Constants.KEY_TARJETA), afiliado.get(Constants.KEY_IFE_FRENTE), afiliado.get(Constants.KEY_IFE_VUELTA));
    }

    @OnClick(R.id.ivVerify)
    public void sms(){
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
        } if(afiliado.get(Constants.KEY_TARJETA) != null  || afiliado.get(Constants.KEY_IFE_FRENTE) != null || afiliado.get(Constants.KEY_IFE_VUELTA) != null  ){
            Log.e("--->"," size ---->"+ cardsList.size() + "/" +positionItem);
            for (int i = 0; i < cardsList.size(); i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.layout_cardview, null);
                AppCompatImageView checkImg = view.findViewById(R.id.ivCheck);
                if (i == positionItem)
                    checkImg.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
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


}
