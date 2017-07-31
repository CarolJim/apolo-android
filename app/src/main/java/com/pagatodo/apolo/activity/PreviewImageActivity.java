package com.pagatodo.apolo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.data.model.Documento;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.utils.Base64Utils;
import com.pagatodo.apolo.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        onResultCallBack(true);
    }


    @OnClick(R.id.activity_image_preview_iv_close)
    protected void closePreview()
    {
        onResultCallBack(null);
    }

    @Override
    public void onBackPressed() {
        onResultCallBack(null);
    }
}
