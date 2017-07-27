package com.pagatodo.apolo.ui.base.factoryactivities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.pagatodo.apolo.R;
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
        }else
            doPermissionsGrantedAction();
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
                            DialogFactory.buildRationaleRunTimeDialog(this, getDeniedPermissionsMessage(getDeniedPermissionsGroups(deniedPermissions)));
                        } else {
                            //User checked "Never show again", User may be taken to change configuration.
                            DialogFactory.buildAppSettingsRationaleRTDialog(this, getDeniedPermissionsMessage(getDeniedPermissionsGroups(deniedPermissions)));

                        }
                    }else
                    {
                        doPermissionsGrantedAction();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    protected void doPermissionsGrantedAction(){}

    private List<String> getDeniedPermissionsGroups(List<String> permissions)
    {
        List<String> deniedGroups = new ArrayList<>();

        for(String permission : permissions)
        {
            switch (permission)
            {
                case Manifest.permission.CAMERA:
                    deniedGroups.add(Manifest.permission_group.CAMERA);
                    break;
                case Manifest.permission.READ_SMS:
                case Manifest.permission.RECEIVE_SMS:
                    if(!deniedGroups.contains(Manifest.permission_group.SMS))
                        deniedGroups.add(Manifest.permission_group.SMS);
                    break;
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    if(!deniedGroups.contains(Manifest.permission_group.STORAGE))
                        deniedGroups.add(Manifest.permission_group.STORAGE);
                    break;
            }
        }

        return deniedGroups;
    }

    protected String getDeniedPermissionsMessage(List<String> permissionsGroups) {

        StringBuffer buffer = new StringBuffer();

        buffer.append(getString(R.string.general_run_time_message) + "\n\n\n");

        for(String permission : permissionsGroups)
        {
            if(getDeniedPermissionMessage(permission).trim().length() <= 0)
                continue;
            else
            {
                buffer.append(getDeniedPermissionMessage(permission));
                if(permission.indexOf(permission) != permissionsGroups.size()-1)
                    buffer.append("\n\n");
            }

        }

        return buffer.toString();
    }
    protected String getDeniedPermissionMessage(String permissionGroup)
    {
        switch (permissionGroup)
        {
            case Manifest.permission_group.CAMERA:
                return getString(R.string.runtime_camera);
            case Manifest.permission_group.SMS:
                return getString(R.string.runtime_sms);
            case Manifest.permission_group.STORAGE:
                return getString(R.string.runtime_storage);
        }
        return "";
    }

}