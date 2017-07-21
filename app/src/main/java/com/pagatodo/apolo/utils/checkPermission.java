package com.pagatodo.apolo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import com.pagatodo.apolo.R;
import static com.pagatodo.apolo.ui.UI.showToast;

/**
 * Created by rvargas on 18/07/2017.
 */

public class checkPermission extends Activity {

    Activity activity;

    public checkPermission(Activity activity) {
        this.activity = activity;
    }

    public boolean checarPermiso () {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_CAMERA_PERMISSION);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                showToast(activity.getString(R.string.not_permission), activity);
                finish();
            }
        }
    }

}



