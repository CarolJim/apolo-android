package com.pagatodo.apolo.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.ui.UI;
import com.pagatodo.apolo.ui.base.factoryfragments.BasePermissionsFragment;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnFragment;
import com.pagatodo.apolo.ui.dialogs.model.DialogContent;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.apolo.ui.dialogs.DialogContract.DIALOG_RUN_TIME_RATIONALE;
import static com.pagatodo.apolo.ui.dialogs.DialogContract.KEY_DIALOG_CONTENT;


/**
 * Created by jvazquez on 22/05/2017.
 */

public class GenericDialog extends DialogFragment implements View.OnClickListener{
    private final static String MAIL_COLOR_KEY = "mailColor";
    private DialogContent dialogContent;
    private IEventOnFragment eventOnFragment;
    private boolean secondaryActionTransition = false;
    private BasePermissionsFragment permissionsFragment = null;

    public static GenericDialog newInstance(String dialogContent) {

        Bundle args = new Bundle();
        args.putString(KEY_DIALOG_CONTENT, dialogContent);
        GenericDialog fragment = new GenericDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static GenericDialog newInstance(String dialogContent, int colorId) {

        Bundle args = new Bundle();
        args.putString(KEY_DIALOG_CONTENT, dialogContent);
        args.putInt(MAIL_COLOR_KEY,colorId);
        GenericDialog fragment = new GenericDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(KEY_DIALOG_CONTENT)) {
            dialogContent = new Gson().fromJson(getArguments().getString(KEY_DIALOG_CONTENT), DialogContent.class);
        }else{
            throw new RuntimeException("Cant create dialog!!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(dialogContent.getIdLayout() != -1){
            return inflater.inflate(dialogContent.getIdLayout(), container, false);
        }else{
            throw new RuntimeException("first you need set a idLayout resource!!");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setButtonPrimaryProperties(view);
        setButtonSecondaryProperties(view);
        setTitleProperties(view);
        setContentProperties(view);
        setImageResource(view);
    }

    public void setPermissionsFragment(BasePermissionsFragment permissionsFragment) {
        this.permissionsFragment = permissionsFragment;
    }

    private void setImageResource(View root) {
        AppCompatImageView imageResource = (AppCompatImageView) root.findViewById(R.id.idImageResource);
        if(imageResource != null){
            if(dialogContent.getIdImageResource() != -1){
                imageResource.setVisibility(VISIBLE);
                imageResource.setImageDrawable(ContextCompat.getDrawable(getActivity(), dialogContent.getIdImageResource()));
            }else{
                imageResource.setVisibility(GONE);
            }
        }
    }

    private void setContentProperties(View root) {
        MaterialTextView tvContent = (MaterialTextView) root.findViewById(R.id.tvContent);
        if(tvContent != null){
            tvContent.setText(dialogContent.getContent());
        }
    }

    private void setTitleProperties(View root) {
        MaterialTextView tvTitle = (MaterialTextView) root.findViewById(R.id.tvTitle);
        if(tvTitle != null){
            tvTitle.setText(dialogContent.getTitle());
        }
    }

    private void setButtonPrimaryProperties(View root) {
        MaterialButton button = (MaterialButton) root.findViewById(R.id.btnPrimary);
        if(button != null){
            if(!dialogContent.getBtnPrimaryText().isEmpty()){
                button.setVisibility(VISIBLE);
                button.setText(dialogContent.getBtnPrimaryText());
                button.setOnClickListener(this);
            }else{
                button.setVisibility(GONE);
            }
        }
    }
    private void setButtonSecondaryProperties(View root) {
        MaterialButton button = (MaterialButton) root.findViewById(R.id.btnSecondary);
        if(button != null){
            if(!dialogContent.getBtnSecondaryText().isEmpty()){
                button.setVisibility(VISIBLE);
                button.setText(dialogContent.getBtnSecondaryText());
                button.setOnClickListener(this);
            }else{
                button.setVisibility(GONE);
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPrimary:
                switch (dialogContent.getType()){
                    case DIALOG_RUN_TIME_RATIONALE:
                        if(permissionsFragment != null)
                            permissionsFragment.setShouldCheckPermissions(true);
                    default:
                        sendEvent(dialogContent.getActionPrimary(), null);
                        dismiss();
                        break;
                }
                break;
            case R.id.btnSecondary:
                switch (dialogContent.getType()){
                    default:
                        sendEvent(dialogContent.getActionSecondary(), null);
                        secondaryActionTransition = true;
                        dismiss();
                        break;
                }
                break;
            default:
                dismiss();
                break;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        //dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_inset);

        return dialog;
    }

    public void setEventOnFragment(IEventOnFragment eventOnFragment) {
        this.eventOnFragment = eventOnFragment;
    }

    public IEventOnFragment getEventOnFragment() {
        return eventOnFragment;
    }

    private void sendEvent(String event, Object data){
        if(eventOnFragment != null){
            eventOnFragment.onEvent(event, data);
        }
    }
}
