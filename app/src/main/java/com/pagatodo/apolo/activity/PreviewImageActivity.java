package com.pagatodo.apolo.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.register.RegisterActivity;
import com.pagatodo.apolo.activity.register.StatusProgresFragment;
import com.pagatodo.apolo.data.model.Documento;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.utils.Base64Utils;
import com.pagatodo.apolo.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.ui.base.BaseEventContract.DOCUMENTS_RV_ITEM_SELECTED;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_CANCELED;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_CONFIRMATE;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_REGISTERED;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_REGISTER_REINTENT;
import static com.pagatodo.apolo.ui.base.BaseEventContract.KEY_FOLIO;

/**
 * Created by Omar on 31/07/2017.
 */

public class PreviewImageActivity extends BaseActivity {

    @BindView(R.id.activity_image_preview_iv_picture)
    protected ImageView ivPreviewDetail;
    private Documento selectedDocument;

    @Override
    protected int setIdContainerFragments() {
        return 0;
    }

    @Override
    protected int setIdMainView() {
        return 0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        ButterKnife.bind(this);
        selectedDocument = (Documento) getIntent().getExtras().getSerializable(Constants.SELECTED_DOCUMENT_KEY);
        ivPreviewDetail.setImageBitmap(Base64Utils.getDecodedBitmap(selectedDocument.getDocumentoBase64(), this));
    }

    @OnClick(R.id.activity_image_preview_iv_delete)
    protected void deletePhoto()
    {
        showDialog("Eliminar", getString(R.string.confirmate_delete), android.R.drawable.ic_dialog_alert, getString(R.string.txt_confirmate), EVENT_CONFIRMATE, getString(R.string.txt_cancel), EVENT_CANCELED);
    }

    @OnClick(R.id.activity_image_preview_iv_close)
    protected void closePreview()
    {
        onResultCallBack(null);
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_CANCELED:
                onResultCallBack(null);
                break;
            case EVENT_CONFIRMATE:
                onResultCallBack(true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        onResultCallBack(null);
    }
}
