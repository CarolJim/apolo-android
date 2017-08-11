package com.pagatodo.apolo.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.login.LoginActivity;
import com.pagatodo.apolo.activity.register.RegisterActivity;
import com.pagatodo.apolo.activity.splash.SplashActivity;
import com.pagatodo.apolo.data.model.Documento;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.utils.Base64Utils;
import com.pagatodo.apolo.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.data.local.PreferencesContract.SESSION_ACTIVE;
import static com.pagatodo.apolo.ui.UI.showSnackBar;

public class CaptureActivity extends BaseActivity implements PictureCallback, SurfaceHolder.Callback {
    private static final String TAG = "CaptureActivity";
    private Camera mCamera;
    private byte[] mCameraData;
    private boolean mIsCapturing;
    @BindView(R.id.layoutCapture) CoordinatorLayout layoutCapture;
    @BindView(R.id.camera_image_view) AppCompatImageView mCameraImage;
    @BindView(R.id.preview_view) SurfaceView mSurfaceView;
    @BindView(R.id.action_reintent) AppCompatImageView btnReintent;
    @BindView(R.id.action_save) AppCompatImageView btnSave;
    @BindView(R.id.action_capture) AppCompatImageView btnCapture;
    @BindView(R.id.turn_camera) AppCompatImageView turnCamera;
    @BindView(R.id.camera_frame) FrameLayout camera_frame;
    @BindView(R.id.progress_view_activity) LinearLayout progress;
    private Documento documentSaver;
    private Bitmap mBitmapTaken;
    private Handler cameraHandler = new Handler();
    private final static int HANDLER_DELAY = 4000;

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
        onAnimation();
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
    protected int setIdCoordinatorLayout() {
        return R.id.layoutCapture;//super.setIdCoordinatorLayout();
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
                //showSnackBar(layoutCapture, getString(R.string.unable_camera));
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
        btnCapture.setEnabled(true);
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
            btnCapture.setEnabled(false);
            captureImage();
        }catch (Exception e){
            showSnackBar(layoutCapture, getString(R.string.error_capture));
        }
    }

    @OnClick(R.id.action_save)
    public void saveCapture() {
        camera_frame.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.VISIBLE);

        mBitmapTaken = Bitmap.createBitmap(
                mBitmapTaken,
                (int) (mBitmapTaken.getWidth() * .15),
                (int) (mBitmapTaken.getHeight() * .14),
                (int) (mBitmapTaken.getWidth() * .69),
                (int) (mBitmapTaken.getHeight() * .68)
        );

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

    private void onAnimation(){
        Animation
        anim = AnimationUtils.loadAnimation(CaptureActivity.this, R.anim.rotate);
        anim.reset();
        turnCamera.clearAnimation();
        turnCamera.startAnimation(anim);
    }

    private void initCamera() {
        try {
            cameraHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    turnCamera.setVisibility(View.GONE);
                }
            },HANDLER_DELAY);

            mCamera = Camera.open();
            mCamera.setPreviewDisplay(mSurfaceView.getHolder());
            Camera.Parameters parameters  = mCamera.getParameters();

            int result = getProperCameraDegrees();
            mCamera.setDisplayOrientation(result);
            parameters.setRotation(result);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            mCamera.setParameters(parameters);

            if (mIsCapturing) {
                mCamera.startPreview();
            }
        } catch (Exception e) {
            //showSnackBar(layoutCapture, getString(R.string.unable_camera));
        }


        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();

                    Rect touchRect = new Rect(
                            (int)(x - 100),
                            (int)(y - 100),
                            (int)(x + 100),
                            (int)(y + 100));

                    final Rect targetFocusRect = new Rect(
                            touchRect.left * 2000/mSurfaceView.getWidth() - 1000,
                            touchRect.top * 2000/mSurfaceView.getHeight() - 1000,
                            touchRect.right * 2000/mSurfaceView.getWidth() - 1000,
                            touchRect.bottom * 2000/mSurfaceView.getHeight() - 1000);

                    doTouchFocus(targetFocusRect);
                }
                return false;
            }
        });


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



    public void doTouchFocus(final Rect tfocusRect) {
        try {
            final List<Camera.Area> focusList = new ArrayList<Camera.Area>();
            Camera.Area focusArea = new Camera.Area(tfocusRect, 1000);
            focusList.add(focusArea);

            Camera.Parameters para = mCamera.getParameters();
            para.setFocusAreas(focusList);
            para.setMeteringAreas(focusList);
            mCamera.setParameters(para);

            mCamera.autoFocus(myAutoFocusCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Camera.AutoFocusCallback myAutoFocusCallback = new Camera.AutoFocusCallback(){

        @Override
        public void onAutoFocus(boolean arg0, Camera arg1) {
            if (arg0){
                mCamera.cancelAutoFocus();
            }
        }
    };
}