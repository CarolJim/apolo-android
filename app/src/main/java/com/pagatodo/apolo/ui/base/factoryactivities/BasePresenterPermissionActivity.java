package com.pagatodo.apolo.ui.base.factoryactivities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.pagatodo.apolo.ui.base.factoryinterfaces.IProcessData;
import com.pagatodo.apolo.ui.dialogs.DialogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jose Alberto Vazquez
 * @version 1.0
 *
 * This class implements multiple methods to get permission from user.
 *
 * Created by jvazquez on 22/05/2017.
 */

public abstract class BasePresenterPermissionActivity<iProcessData extends IProcessData> extends BasePresenterActivity<iProcessData> {

    private final static int PERMISSIONS_ID = 122333;
    private final static String PACKAGE_LBL = "package";

    protected void showAppSettings()
    {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts(PACKAGE_LBL , getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    protected void requestPermissions(String [] permissions)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!arePermissionsGranted(permissions))
                requestPermissions(permissions, PERMISSIONS_ID);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean arePermissionsGranted(String [] permissions)
    {
        boolean permissionsAreGranted = true;
        for(String permission : permissions)
        {
            if(checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED)
                permissionsAreGranted = false;
        }
        return permissionsAreGranted;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        int index = 0;
        Log.w("Permission",String.valueOf(requestCode));
        switch (requestCode) {
            case PERMISSIONS_ID:
                if (grantResults.length > 0) {

                    for (int grantResult : grantResults) {
                        if (grantResult == PackageManager.PERMISSION_DENIED)
                            deniedPermissions.add(permissions[index]);
                        index++;
                    }

                    if(deniedPermissions.size() > 0)
                    {
                        boolean showRationale = true;

                        for(String permission : deniedPermissions)
                        {
                            if(!shouldShowRequestPermissionRationale(permission))
                                showRationale = false;
                        }

                        if (showRationale) {
                            //User declined permissions request, a detail may be shown to the user.
                            //A dialog may be shown to the user with all denied permissions rationale.
                            DialogFactory.buildRationaleRunTimeDialog(this, getDeniedPermissionsMessage(deniedPermissions));
                        } else {
                            //User checked "Never show again", User may be taken to change configuration.
                            DialogFactory.buildAppSettingsRationaleRTDialog(this, getDeniedPermissionsMessage(deniedPermissions));

                        }
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    protected abstract String getDeniedPermissionsMessage(List<String> permissions);
    protected abstract String getDeniedPermissionMessage(String permission);

}