package com.pagatodo.apolo.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.AppCompatImageView;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.App.instance;
import static com.pagatodo.apolo.ui.UI.showToast;

public class CaptureActivity extends Activity implements PictureCallback, SurfaceHolder.Callback {
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
    int TYPE_CAPTURE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        TYPE_CAPTURE = extras.getInt(Constants.TYPE_CAPTURE);

        mCameraImage.setVisibility(View.INVISIBLE);

        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mIsCapturing = true;

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

        File saveFile = openFileForImage();
        if (saveFile != null) {
            saveImageToFile(saveFile);
        } else {
            showToast(getString(R.string.unable_to_save), getApplicationContext());
        }

    }

    private void saveImageToFile(File file) {
        if (mCameraBitmap != null) {
            FileOutputStream outStream;
            try {
                outStream = new FileOutputStream(file);
                if (!mCameraBitmap.compress(Bitmap.CompressFormat.JPEG, 60, outStream)) {
                    showToast(getString(R.string.unable_to_save), getApplicationContext());
                } else {
                    switch (TYPE_CAPTURE) {
                        case 0:
                            instance.put(Constants.SOL_TARJETA, file.toString());
                            break;
                        case 1:
                            instance.put(Constants.SOL_IFE_FRENTE, file.toString());
                            break;
                        case 2:
                            instance.put(Constants.SOL_IFE_VUELTA, file.toString());
                            break;
                    }
                    closeCapture();
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

                Camera.Parameters param;
                param = mCamera.getParameters();
                Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
                if(display.getRotation() == Surface.ROTATION_0) {
                    mCamera.setDisplayOrientation(90);
                    param.setRotation(90);
                }
                if(display.getRotation() == Surface.ROTATION_270) {
                    mCamera.setDisplayOrientation(180);
                    param.setRotation(180);
                }
                mCamera.setParameters(param);

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

}