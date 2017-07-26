package com.pagatodo.apolo.ui.dialogs;

import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.ui.base.factoryfragments.BasePermissionsFragment;
import com.pagatodo.apolo.ui.dialogs.model.DialogContent;

import java.util.List;

import static com.pagatodo.apolo.ui.base.BaseEventContract.SHOW_APP_SETTINGS;
import static com.pagatodo.apolo.ui.dialogs.DialogContract.DIALOG_MESSAGE;
import static com.pagatodo.apolo.ui.dialogs.DialogContract.DIALOG_RUN_TIME_RATIONALE;
import static com.pagatodo.apolo.ui.dialogs.DialogContract.DIALOG_WA_NOT_FOUND;

/**
 * Class that implements a CustomDialogs
 * Created by jvazquez on 22/05/2017.
 */

public class DialogFactory {

    public static void buildMessageDialog(BaseActivity activity, String title, String message, @DrawableRes int idImageResource, String btnPrimaryText, String eventPrimary, String btnSecondaryText, String eventSecondary){
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment prev = activity.getSupportFragmentManager().findFragmentByTag(DIALOG_MESSAGE);
        if (prev != null) {
            ft.remove(prev);
        }
        GenericDialog dialog = GenericDialog.newInstance(
                buildDialogContent(DIALOG_MESSAGE,
                        R.layout.dialog_base_layout,
                        idImageResource,
                        title,
                        message,
                        btnPrimaryText,
                        btnSecondaryText,
                        eventPrimary,
                        eventSecondary));
        dialog.setCancelable(false);
        dialog.setEventOnFragment(activity.getIEventOnFragment());
        dialog.show(ft, DIALOG_MESSAGE);
    }

    public static void buildWhatsAppNotFoundDialog(BaseActivity activity){
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment prev = activity.getSupportFragmentManager().findFragmentByTag(DIALOG_WA_NOT_FOUND);
        if (prev != null) {
            ft.remove(prev);
        }
        GenericDialog dialog = GenericDialog.newInstance(
                buildDialogContent(DIALOG_WA_NOT_FOUND,
                        R.layout.dialog_base_layout,
                        android.R.drawable.ic_dialog_alert,
                        activity.getString(R.string.dialog_title_aviso),
                        activity.getString(R.string.whatsapp_sharing_not_found_msg),
                        "",
                        activity.getString(R.string.dialog_text_accept),
                        null,
                        null));
        dialog.setCancelable(false);
        dialog.setEventOnFragment(activity.getIEventOnFragment());
        dialog.show(ft, DIALOG_WA_NOT_FOUND);
    }


    public static void buildAppSettingsRationaleRTDialog(BaseActivity activity, String dialogMsg){
        buildAppSettingsRationaleRTDialog(activity,dialogMsg,null,null);
    }

    public static void buildAppSettingsRationaleRTDialog(BaseActivity activity, String dialogMsg, String secondaryEvent, BasePermissionsFragment permissionFragment)
    {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment prev = activity.getSupportFragmentManager().findFragmentByTag(DIALOG_RUN_TIME_RATIONALE);
        if (prev != null) {
            ft.remove(prev);
        }
        GenericDialog dialog = GenericDialog.newInstance(
                buildDialogContent(DIALOG_RUN_TIME_RATIONALE,
                        R.layout.dialog_base_layout,
                        android.R.drawable.ic_dialog_alert,
                        activity.getString(R.string.dialog_title_aviso),
                        dialogMsg,
                        activity.getString(R.string.run_time_enable_permissions_btn_lbl),
                        activity.getString(R.string.run_time_continue_anyway_btn_lbl),
                        SHOW_APP_SETTINGS,
                        secondaryEvent));
        dialog.setCancelable(false);
        dialog.setEventOnFragment(activity.getIEventOnFragment());
        dialog.setPermissionsFragment(permissionFragment);
        ft.add(dialog,DIALOG_RUN_TIME_RATIONALE);
        ft.commitAllowingStateLoss();
    }


    public static void buildRationaleRunTimeDialog (BaseActivity activity, String dialogMsg){
        buildRationaleRunTimeDialog(activity,dialogMsg,null);
    }

    public static void buildRationaleRunTimeDialog (BaseActivity activity, String dialogMsg, String secondaryEvent){
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment prev = activity.getSupportFragmentManager().findFragmentByTag(DIALOG_RUN_TIME_RATIONALE);
        if (prev != null) {
            ft.remove(prev);
        }
        GenericDialog dialog = GenericDialog.newInstance(
                buildDialogContent(DIALOG_RUN_TIME_RATIONALE,
                        R.layout.dialog_base_layout,
                        android.R.drawable.ic_dialog_alert,
                        activity.getString(R.string.dialog_title_aviso),
                        dialogMsg,
                        "",
                        activity.getString(R.string.dialog_text_accept),
                        null,
                        secondaryEvent));
        dialog.setCancelable(false);
        dialog.setEventOnFragment(activity.getIEventOnFragment());
        ft.add(dialog,DIALOG_RUN_TIME_RATIONALE);
        ft.commitAllowingStateLoss();
    }

    private static String buildDialogContent(String type, @LayoutRes int idLayout, @DrawableRes int idDrawable, String title, String message, String btnPrimaryText, String btnSecondaryText, String eventPrimary, String eventSecondary){
        DialogContent content = new DialogContent();
        content.setType(type);
        content.setIdLayout(idLayout);
        content.setIdImageResource(idDrawable);
        content.setTitle(title);
        content.setContent(message);
        content.setBtnPrimaryText(btnPrimaryText);
        content.setBtnSecondaryText(btnSecondaryText);
        content.setActionPrimary(eventPrimary);
        content.setActionSecondary(eventSecondary);
        return content.toString();
    }
}