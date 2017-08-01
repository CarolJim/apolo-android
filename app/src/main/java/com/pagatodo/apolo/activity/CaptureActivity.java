package com.pagatodo.apolo.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
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
    private Bitmap mCameraBitmap;
    @BindView(R.id.camera_image_view) AppCompatImageView mCameraImage;
    @BindView(R.id.preview_view) SurfaceView mSurfaceView;
    @BindView(R.id.action_reintent) AppCompatImageView btnReintent;
    @BindView(R.id.action_save) AppCompatImageView btnSave;
    @BindView(R.id.action_capture) AppCompatImageView btnCapture;
    @BindView(R.id.camera_frame) RelativeLayout camera_frame;
    @BindView(R.id.progress_view_activity) LinearLayout progress;
    private Documento documentSaver;

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
        Bitmap bitmap = BitmapFactory.decodeByteArray(mCameraData, 0, mCameraData.length);
        mCameraImage.setImageBitmap(bitmap);
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
        captureImage();
    }

    @OnClick(R.id.action_save)
    public void saveCapture() {
        camera_frame.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.VISIBLE);
        mCameraBitmap = BitmapFactory.decodeByteArray(mCameraData, 0, mCameraData.length);
        mCameraImage.setImageBitmap(mCameraBitmap);
        ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
        mCameraBitmap.compress(Bitmap.CompressFormat.JPEG, 35, mOutputStream);
        byte [] mByteArray = mOutputStream.toByteArray();
        mCameraBitmap.recycle();

        String encodedImage = Base64.encodeToString(mByteArray, Base64.DEFAULT);
        documentSaver.setDocumentoBase64(encodedImage);
        documentSaver.setLongitud(encodedImage.length());
        onResultCallBack(documentSaver);
    }

    private void saveImageToFile(File file) {
        if (mCameraBitmap != null) {
            FileOutputStream outStream;
            try {
                outStream = new FileOutputStream(file);
                if (!mCameraBitmap.compress(Bitmap.CompressFormat.JPEG, 60, outStream)) {
                    showToast(getString(R.string.unable_to_save), getApplicationContext());
                } else {
                    String encodedImage = Base64Utils.getEncodedString(file);
                    documentSaver.setDocumentoBase64(encodedImage);
                    documentSaver.setLongitud(encodedImage.length());
                    onResultCallBack(documentSaver);
                }
                outStream.close();
            } catch (Exception e) {
                showToast(getString(R.string.unable_to_save), getApplicationContext());
            }
        }
    }

    private File openFileForImage() {
        File imageDirectory;
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            imageDirectory = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    Constants.IMAGE_DIRECTORY_NAME );
            if (!imageDirectory.exists() && !imageDirectory.mkdirs()) {
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss", Locale.getDefault());
                return new File(imageDirectory.getPath() +
                        File.separator + "image_" +
                        dateFormat.format(new Date()) + ".jpg");
            }
        }
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (mCamera == null) {
            try {
                mCamera = Camera.open();
                mCamera.setPreviewDisplay(mSurfaceView.getHolder());
                setDisplayOrientation(mCamera, 90);

                if (mIsCapturing) {
                    mCamera.startPreview();
                }
            } catch (Exception e) {
                showToast(getString(R.string.unable_camera), getApplicationContext());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }


    protected void setDisplayOrientation(Camera camera, int angle) {
        Method downPolymorphic;
        try {
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
            if (downPolymorphic != null)
                downPolymorphic.invoke(camera, new Object[]{angle});
        } catch (Exception e1) {
        }
    }
}