package com.pagatodo.apolo.ui.base.factoryfragments;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IProcessData;
import com.pagatodo.apolo.ui.dialogs.DialogFactory;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.apolo.ui.base.BaseEventContract.RT_OPTION_DECLINED;

/**
 * Created by Omar on 14/06/2017.
 */

public abstract class BasePermissionsFragment<iProcessData extends IProcessData> extends BaseFragment<iProcessData> {

    private final static int PERMISSIONS_ID = 22333;
    private boolean shouldCheckPermissions;
    private String[] permissions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shouldCheckPermissions = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (shouldCheckPermissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!arePermissionsGranted(permissions) && getActivity() instanceof BaseActivity)
                    ((BaseActivity) getActivity()).getIEventOnFragment().onEvent(RT_OPTION_DECLINED, null);
            }
        }
    }

    public void setShouldCheckPermissions(boolean shouldCheckPermissions) {
        this.shouldCheckPermissions = shouldCheckPermissions;
    }

    protected void requestPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!arePermissionsGranted(permissions)) {
                requestPermissions(permissions, PERMISSIONS_ID);
                this.permissions = permissions;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean arePermissionsGranted(String[] permissions) {
        boolean permissionsAreGranted = true;
        for (String permission : permissions) {
            if (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED)
                permissionsAreGranted = false;
        }
        return permissionsAreGranted;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        int index = 0;
        Log.w("Permission", String.valueOf(requestCode));
        switch (requestCode) {
            case PERMISSIONS_ID:
                if (grantResults.length > 0) {

                    for (int grantResult : grantResults) {
                        if (grantResult == PackageManager.PERMISSION_DENIED)
                            deniedPermissions.add(permissions[index]);
                        index++;
                    }

                    if (deniedPermissions.size() > 0) {
                        boolean showRationale = true;

                        for (String permission : deniedPermissions) {
                            if (!shouldShowRequestPermissionRationale(permission))
                                showRationale = false;
                        }

                        if (showRationale) {
                            //User declined permissions request, a detail may be shown to the user.
                            //A dialog may be shown to the user with all denied permissions rationale.
                            if (getActivity() instanceof BaseActivity)
                                DialogFactory.buildRationaleRunTimeDialog((BaseActivity) getActivity(), getDeniedPermissionsMessage(deniedPermissions), RT_OPTION_DECLINED);
                        } else {
                            //User checked "Never show again", User may be taken to change configuration.
                            if (getActivity() instanceof BaseActivity)
                                DialogFactory.buildAppSettingsRationaleRTDialog((BaseActivity) getActivity(), getDeniedPermissionsMessage(deniedPermissions), RT_OPTION_DECLINED, this);

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