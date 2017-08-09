package com.pagatodo.apolo.activity.register;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnFragment;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;
import com.pagatodo.apolo.utils.customviews.StatusViewCupo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_REGISTERED;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_REGISTER_REINTENT;

/**
 * Created by jvazquez on 28/07/2017.
 */

public class StatusProgresFragment extends DialogFragment  {
    private static final String KEY_SIZE_TASKS  = "StatusProgresFragment#sizeTasks";

    @BindView(R.id.status_view) StatusViewCupo statusViewCupo;
    @BindView(R.id.progressDocuments) MaterialTextView mProgressDocuments;
    @BindView(R.id.ivStatus) AppCompatImageView mIvStatus;
    @BindView(R.id.fl_Progress) FrameLayout mFlProgress;
    @BindView(R.id.ll_Status) LinearLayout mLlStatus;
    @BindView(R.id.tv_Status) MaterialTextView mTvStatus;
    @BindView(R.id.btn_action) MaterialButton mBtnAction;
    private String mCurrentAction = "";
    private IEventOnFragment eventOnFragment = null;
    private int taskFinishedsSuccess = 0;
    private int taskFinishedsFailure = 0;
    private int sizeofTasks = 0;

    public static StatusProgresFragment newInstance(int sizeOfTaks) {

        Bundle args = new Bundle();
        args.putInt(KEY_SIZE_TASKS, sizeOfTaks);
        StatusProgresFragment fragment = new StatusProgresFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IEventOnFragment){
            eventOnFragment = ((IEventOnFragment) context);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() != null && getArguments().containsKey(KEY_SIZE_TASKS)){
           sizeofTasks = getArguments().getInt(KEY_SIZE_TASKS);
        }
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_NoActionBar);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_register, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mProgressDocuments.setText(getString(R.string.dialog_status_upload, 0, sizeofTasks));
        mFlProgress.setVisibility(View.VISIBLE);
        mLlStatus.setVisibility(View.GONE);
        enableBtn(false);
        mBtnAction.setVisibility(view.GONE);
    }

    private void enableBtn(boolean enable) {
        mBtnAction.setEnabled(enable);
        mBtnAction.setAlpha(enable ? 1.0f: 0.7f);
        mBtnAction.setVisibility(View.VISIBLE);
        mBtnAction.setTextColor(ContextCompat.getColor(getActivity(), enable ? R.color.white : R.color.white));

    }

    @OnClick(R.id.btn_action)
    public void nextStatusView(){
        sendEvent(mCurrentAction, null);
        //dismiss();
    }
    public void setErrorRegister(String message){
        if(mLlStatus != null){
            mFlProgress.setVisibility(View.GONE);
            mLlStatus.setVisibility(View.VISIBLE);
            mIvStatus.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_upload_error_ap));
            mTvStatus.setText(message);
            mCurrentAction = EVENT_REGISTER_REINTENT;
            mBtnAction.setText(getString(R.string.txt_btn_reintent));
            enableBtn(true);
        }
    }
    public void isSuccessRegister(){
        taskFinishedsSuccess = taskFinishedsSuccess + 1;
        mProgressDocuments.setText(getString(R.string.dialog_status_upload, getFullTasks(), sizeofTasks));
        updatestatus();
    }
    private void sendEvent(String event, Object object){
        if(eventOnFragment != null){
            eventOnFragment.onEvent(event, object);
        }
    }
    private void updatestatus(){
        statusViewCupo.updateStatus((getFullTasks())*getPercent(), ((getFullTasks())-1)*getPercent());
        validateSuccessTasks();
    }
    private int getPercent(){
        return 100/sizeofTasks;
    }

    public void uploadDocumentSuccess(){
        taskFinishedsSuccess = taskFinishedsSuccess + 1;
        mProgressDocuments.setText(getString(R.string.dialog_status_upload, getFullTasks(), sizeofTasks));
        updatestatus();
    }
    public void upladDocumentFailed(){
        taskFinishedsFailure = taskFinishedsFailure + 1;
        mProgressDocuments.setText(getString(R.string.dialog_status_upload, getFullTasks(), sizeofTasks));
        updatestatus();
    }
    private void validateSuccessTasks(){
        if(taskFinishedsFailure > 0 && getFullTasks() == sizeofTasks){
            if(mLlStatus != null){
                mFlProgress.setVisibility(View.GONE);
                mLlStatus.setVisibility(View.VISIBLE);
                mIvStatus.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_upload_error_ap));
                mTvStatus.setText(getString(R.string.dialog_status_failure));
                mCurrentAction = EVENT_REGISTER_REINTENT;
                mBtnAction.setText(getString(R.string.txt_btn_reintent));
                enableBtn(true);
                return;
            }
        }
        if((taskFinishedsSuccess) == sizeofTasks){
            if(mLlStatus != null){
                mFlProgress.setVisibility(View.GONE);
                //mLlStatus.setVisibility(View.VISIBLE);
                //mIvStatus.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_success));
                //mTvStatus.setText(getString(R.string.dialog_status_success));
                mCurrentAction = EVENT_REGISTERED;
                mBtnAction.setVisibility(View.GONE);
                //mBtnAction.setText(getString(R.string.txt_button_continue));
                //enableBtn(true);
                nextStatusView();
            }
        }
    }
    private int getFullTasks(){
        return taskFinishedsSuccess + taskFinishedsFailure;
    }
}
