package com.pagatodo.apolo.ui.base.factoryfragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnFragment;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;
import com.pagatodo.apolo.ui.dialogs.DialogFactory;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_DISMISS_PROGRESS;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_HIDE_KEYBOARD;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_SHOW_KEYBOARD;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_SHOW_MESSAGE;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_SHOW_PROGRESS;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_TOKEN_EXPIRED;

/**
 * Created by jvazquez on 19/05/2017.
 */

public abstract class SupportUXFragment extends Fragment implements IEventOnView {
    protected IEventOnFragment eventOnFragment;

    @IdRes
    protected int idMainView        = -1;
    @IdRes
    protected int idProgressView    = -1;
    @IdRes
    protected int idErrorView       = -1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof  IEventOnFragment){
            this.eventOnFragment = (IEventOnFragment) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idMainView      = setIdMainView();
        idErrorView     = setIdErrorView();
        idProgressView  = setIdProgressView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //showMainView();
        initialShowMainView();
    }
    @IdRes
    protected abstract int setIdMainView();
    @IdRes
    protected abstract int setIdErrorView();
    @IdRes
    protected abstract int setIdProgressView();

    protected void setIdContainers(@IdRes int idMainView, @IdRes int idProgressView, @IdRes int idErrorView){
        this.idMainView = idMainView;
        this.idProgressView = idProgressView;
        this.idErrorView = idErrorView;
    }
    protected void initialShowMainView(){
        if(idMainView != -1 && getView() != null){
            View mainView = getView().findViewById(idMainView);
            if(mainView != null){
                if(getProgressViewStatusVisibility()){
                    hideProgressView();
                }
                if(getErrorViewStatusVisibility()){
                    hideErrorView();
                }
                mainView.setVisibility(VISIBLE);
            }
        }
    }
    protected void showMainView(){
        if(idMainView != -1 && getView() != null){
            View mainView = getView().findViewById(idMainView);
            if(mainView != null){
                if(getProgressViewStatusVisibility()){
                    hideProgressView();
                }
                if(getErrorViewStatusVisibility()){
                    hideErrorView();
                }
                startAnimView(mainView, VISIBLE);
            }
        }
    }
    protected void hideMainView(){
        if(idMainView != -1 && getView() != null){
            View mainView = getView().findViewById(idMainView);
            if(mainView != null){
                startAnimView(mainView, GONE);
            }
        }
    }
    protected boolean getMainViewStatusVisibility(){
        if(idMainView != -1 && getView() != null){
            View mainView = getView().findViewById(idMainView);
            if(mainView != null){
                return mainView.isShown();
            }
        }
        return false;
    }
    protected void showProgressView(String message){
        if(idProgressView != -1 && getView() != null){
            View mainView = getView().findViewById(idProgressView);
            if(mainView != null){
//                if(getMainViewStatusVisibility()){
//                    hideMainView();
//                }
                if(getErrorViewStatusVisibility()){
                    hideErrorView();
                }
                MaterialTextView tvMessage = getView().findViewById(R.id.progressTextFragment);
                if (tvMessage != null && message != null && !message.isEmpty()) {
                    tvMessage.setText(message);
                }
                startAnimView(mainView, VISIBLE);

            }
        }
    }
    protected void hideProgressView(){
        if(idProgressView != -1 && getView() != null){
            View mainView = getView().findViewById(idProgressView);
            if(mainView != null){
                startAnimView(mainView, GONE);
                showMainView();
            }
        }
    }
    public boolean getProgressViewStatusVisibility(){
        if(idProgressView != -1 && getView() != null){
            View mainView = getView().findViewById(idProgressView);
            if(mainView != null){
                return mainView.isShown();
            }
        }
        return false;
    }

    protected void showErrorView(String message){
        if(idErrorView != -1 && getView() != null){
            View mainView = getView().findViewById(idErrorView);
            if(mainView != null){
                if(getMainViewStatusVisibility()){
                    hideMainView();
                }
                if(getProgressViewStatusVisibility()){
                    hideProgressView();
                }
                MaterialTextView tvMessage =  getView().findViewById(R.id.errorTextFragment);
                if (tvMessage != null && message != null && !message.isEmpty()) {
                    tvMessage.setText(message);
                }
                if(getView() != null){
                    AppCompatImageView imageView = getView().findViewById(R.id.errorResourceFragment);
                    if(imageView != null){
                        imageView.setImageResource(R.drawable.error_state);
                    }
                }
                startAnimView(mainView, VISIBLE);
            }
        }
    }
    protected void hideErrorView(){
        if(idErrorView != -1 && getView() != null){
            View mainView = getView().findViewById(idErrorView);
            if(mainView != null){
                startAnimView(mainView, GONE);
                showMainView();
            }
        }
    }
    protected boolean getErrorViewStatusVisibility(){
        if(idErrorView != -1 && getView() != null){
            View mainView = getView().findViewById(idErrorView);
            if(mainView != null){
                return mainView.isShown();
            }
        }
        return false;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        this.eventOnFragment = null;
    }
    public void showMessage(String message){
        if(eventOnFragment != null){
            eventOnFragment.onEvent(EVENT_SHOW_MESSAGE, message);
        }
    }
    protected void showProgressActivity(String message){
        if(eventOnFragment != null){
            eventOnFragment.onEvent(EVENT_SHOW_PROGRESS, message);
        }
    }
    protected void hideProgressActivity(){
        if(eventOnFragment != null){
            eventOnFragment.onEvent(EVENT_DISMISS_PROGRESS, null);
        }
    }
    protected void hideSoftKeyboard(){
        if(eventOnFragment != null){
            eventOnFragment.onEvent(EVENT_HIDE_KEYBOARD, null);
        }
    }

    protected void showSoftKeyboard(){
        if(eventOnFragment != null){
            eventOnFragment.onEvent(EVENT_SHOW_KEYBOARD, null);
        }
    }

    protected void startAnimView(View view, int visibility){
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        in.setDuration(800);
        out.setDuration(800);
        if(view != null){
            switch (visibility){
                case GONE:
//                    view.setAlpha(1f);
                    view.startAnimation(out);
                    view.setVisibility(GONE);

                    break;
                case VISIBLE:
//                    view.setAlpha(0f);
                    view.startAnimation(in);
                    view.setVisibility(VISIBLE);
                    break;
            }

        }
    }
    protected void startAnimViewMinimize(View view, int visibility){
        final Animation in = new ScaleAnimation(0, 1f, 0, 1f);
        final Animation out = new ScaleAnimation(1.0f, 0.0f, 1f,0);
        in.setDuration(800);
        out.setDuration(800);
        if(view != null){
            switch (visibility){
                case GONE:
//                    view.setAlpha(1f);
                    view.animate().scaleX(0).scaleY(0).setDuration(800).start();
//                    view.startAnimation(out);
                    view.setVisibility(GONE);

                    break;
                case VISIBLE:
//                    view.setAlpha(0f);
//                    view.startAnimation(in);
                    view.animate().scaleX(1).scaleY(1).setDuration(800).start();
                    view.setVisibility(VISIBLE);
                    break;
            }

        }
    }
    protected void sendEvent(String event, Object data){
        if(eventOnFragment != null){
            eventOnFragment.onEvent(event, data);
        }
    }
    @Override
    public void showProgress(String message) {
        showProgressView(message);
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void showError(String message) {
        try{
            showErrorView(message);
        }catch (Exception e){

        }
    }

    @Override
    public void showDialog(String title, String message, @DrawableRes int idResource, String textBtnPrimary, String primaryEvent, String textBtnSecondary, String secondaryEvent) {
        DialogFactory.buildMessageDialog((BaseActivity) getActivity(),
                title,
                message,
                idResource,
                textBtnPrimary,
                primaryEvent,
                textBtnSecondary,
                secondaryEvent);
    }

    @Override
    public void tokenExpired() {
        sendEvent(EVENT_TOKEN_EXPIRED, null);
    }
}