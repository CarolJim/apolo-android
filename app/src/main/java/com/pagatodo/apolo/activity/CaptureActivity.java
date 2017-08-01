package com.pagatodo.apolo.activity;

import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.data.model.Documento;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.utils.Base64Utils;
import com.pagatodo.apolo.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.ui.UI.showToast;

public class CaptureActivity extends BaseActivity implements PictureCallback, SurfaceHolder.Callback {
    private static final String TAG = "CaptureActivity";
    private Camera mCamera;
    private byte[] mCameraData;
    private boolean mIsCapturing;
    @BindView(R.id.camera_image_view) AppCompatImageView mCameraImage;
    @BindView(R.id.preview_view) SurfaceView mSurfaceView;
    @BindView(R.id.action_reintent) AppCompatImageView btnReintent;
    @BindView(R.id.action_save) AppCompatImageView btnSave;
    @BindView(R.id.action_capture) AppCompatImageView btnCapture;
    @BindView(R.id.camera_frame) RelativeLayout camera_frame;
    @BindView(R.id.progress_view_activity) LinearLayout progress;
    private Documento documentSaver;
    private Bitmap mBitmapTaken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        documentSaver = (Documento) extras.getSerializable(Constants.SELECTED_DOCUMENT_KEY);

        mCameraImage.setVisibility(View.INVISIBLE);

        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mIsCapturing = true;

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
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(Constants.KEY_IS_CAPTURING, mIsCapturing);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mIsCapturing = savedInstanceState.getBoolean(Constants.KEY_IS_CAPTURING, mCameraData == null);
        if (mCameraData != null) {
            setupImageDisplay();
        } else {
            setupImageCapture();
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        mCameraData = data;
        setupImageDisplay();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(holder);
                if (mIsCapturing) {
                    mCamera.startPreview();
                }
            } catch (IOException e) {
                showToast(getString(R.string.unable_camera), getApplicationContext());
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private void captureImage() {
        mCamera.takePicture(null, null, this);
    }

    private void setupImageCapture() {
        mCameraImage.setVisibility(View.INVISIBLE);
        mSurfaceView.setVisibility(View.VISIBLE);
        mCamera.startPreview();
    }

    private void setupImageDisplay() {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inDither = true;

        mBitmapTaken = BitmapFactory.decodeByteArray(mCameraData, 0, mCameraData.length, opts);

        if(mBitmapTaken.getWidth() > mBitmapTaken.getHeight())
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            mBitmapTaken = Bitmap.createBitmap(mBitmapTaken , 0, 0,
                    mBitmapTaken .getWidth(), mBitmapTaken .getHeight(),
                    matrix, true);
        }

        mCameraImage.setImageBitmap(mBitmapTaken);
        mCamera.stopPreview();
        mSurfaceView.setVisibility(View.GONE);
        mCameraImage.setVisibility(View.VISIBLE);
        btnReintent.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);
        btnCapture.setVisibility(View.GONE);
    }

    @OnClick(R.id.action_reintent)
    public void reintentar() {
        btnReintent.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);
        btnCapture.setVisibility(View.VISIBLE);
        setupImageCapture();
        mBitmapTaken.recycle();
    }

    @OnClick(R.id.action_close)
    public void closeCapture() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        onResultCallBack(null);
    }

    @OnClick(R.id.action_capture)
    public void captureCard() {
        try{
            captureImage();
        }catch (Exception e){
            showMessage("Ocurrió un error al tomar la foto");
        }
    }

    @OnClick(R.id.action_save)
    public void saveCapture() {
        camera_frame.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.VISIBLE);

        String encodedImage = Base64Utils.getEncodedString(mBitmapTaken);
        documentSaver.setDocumentoBase64(encodedImage);
        documentSaver.setLongitud(encodedImage.length());
        onResultCallBack(documentSaver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null)
            initCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    private void initCamera()
    {
        try {
            mCamera = Camera.open();
            mCamera.setPreviewDisplay(mSurfaceView.getHolder());
            Camera.Parameters parameters  = mCamera.getParameters();

            int result = getProperCameraDegrees();
            mCamera.setDisplayOrientation(result);
            parameters.setRotation(result);
            mCamera.setParameters(parameters);

            if (mIsCapturing) {
                mCamera.startPreview();
            }
        } catch (Exception e) {
            showToast(getString(R.string.unable_camera), getApplicationContext());
        }
    }

    private int getProperCameraDegrees()
    {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(0, info);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }

        return result;
    }
}