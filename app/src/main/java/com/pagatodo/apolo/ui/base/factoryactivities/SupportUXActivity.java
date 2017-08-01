package com.pagatodo.apolo.ui.base.factoryactivities;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnFragment;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.apolo.ui.UI.showSnackBar;
import static com.pagatodo.apolo.ui.UI.showToast;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_DISMISS_PROGRESS;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_HIDE_KEYBOARD;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_SHOW_ERROR;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_SHOW_KEYBOARD;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_SHOW_MESSAGE;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_SHOW_PROGRESS;

/**
 * @author Jose Alberto Vazquez
 * @version 1.0
 *
 * This class implements multiple methods to show basic User experience,
 * "ProgressView, Toast/SnackBar for Errors, custom dialogs"
 *
 * Created by jvazquez on 16/05/2017.
 */

public abstract class SupportUXActivity extends SupportFragmentActivity implements IEventOnFragment {
    @IdRes
    protected int idCoordinatorLayout = -1;
    @IdRes
    protected int idProgress = -1;
    @IdRes
    protected int idErrorView = -1;
    @IdRes
    protected int idMainView = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idCoordinatorLayout = setIdCoordinatorLayout();
        idProgress = setIdProgress();
        idErrorView = setIdErrorView();
        idMainView = setIdMainView();


    }
    public void inflateView(Activity activity, int idLayout) {
        activity.setContentView(R.layout.activity_base);
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(idLayout, (ViewGroup) activity.findViewById(R.id.activity_base_view_container), true);
    }
    @IdRes
    protected abstract int setIdCoordinatorLayout();
    @IdRes
    protected abstract int setIdProgress();
    @IdRes
    protected abstract int setIdErrorView();
    @IdRes
    protected abstract int setIdMainView();

    protected View getProgressView(){
        if(idProgress != -1){
            return findViewById(idProgress);
        }
        return null;
    }
    protected void showProgressActivity(String message) {
        //Log.e("UX_ACTIVITY", "show progress...");
        View view = findViewById(idProgress);
        if (view != null) {
//            hideMainViewActivity();
            hideErrorViewActivity();
            MaterialTextView tv_message = (MaterialTextView) findViewById(R.id.progressTextActivity);
            view.setVisibility(View.VISIBLE);
            if (tv_message != null && message != null && !message.isEmpty()) {
                tv_message.setText(message);
            }
        }
    }
    protected boolean isShownProgressActivity() {
        return findViewById(idProgress) != null && findViewById(idProgress).isShown() && findViewById(idProgress).getVisibility() == View.VISIBLE;
    }
    protected void hideProgressActivity() {
        //Log.e("UX_ACTIVITY", "hide progress...");
        View view = findViewById(idProgress);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }
    protected void hideMainViewActivity() {
        //Log.e("UX_ACTIVITY", "hide mainView...");

        View view = findViewById(idMainView);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }
    protected void showMainViewActivity() {
        //Log.e("UX_ACTIVITY", "show mainView...");

        View view = findViewById(idMainView);
        if (view != null) {
            hideProgressActivity();
            hideErrorViewActivity();
            view.setVisibility(View.VISIBLE);
        }
    }
    protected boolean isShowMainViewActivity() {
        View view = findViewById(idMainView);
        return view != null && view.isShown();
    }
    protected void hideErrorViewActivity() {
        //Log.e("UX_ACTIVITY", "hide ErrorView...");
        View view = findViewById(idErrorView);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }
    protected void showErrorViewActivity() {
        //Log.e("UX_ACTIVITY", "show errorView...");

        View view = findViewById(idErrorView);
        if (view != null) {
            hideProgressActivity();
//            hideMainViewActivity();
            view.setVisibility(View.VISIBLE);
        }
    }
    protected boolean isShowErrorViewActivity() {
        View view = findViewById(idErrorView);
        return view != null && view.isShown();
    }

    public void setIdsContainers(@IdRes int id_coordinatorLayout, @IdRes int id_containerFragments, @IdRes int id_progress, @IdRes int idErrorView, @IdRes int idMainView){
        this.idCoordinatorLayout = id_coordinatorLayout;
        this.idContainerFragments = id_containerFragments;
        this.idProgress = id_progress;
        this.idErrorView = idErrorView;
        this.idMainView = idMainView;

    }


    protected CoordinatorLayout getCoordinatorLayout(){
        if(idCoordinatorLayout != -1){
            return (CoordinatorLayout) findViewById(idCoordinatorLayout);
        }
        return null;
    }

    protected void showMessage(String message) {
        View rootCoordinator = findViewById(idCoordinatorLayout);
        if (rootCoordinator != null) {
            showSnackBar(rootCoordinator, message);
        } else {
            showToast(message, this);
        }
    }
    protected void showMessageWithAction(String message, String actionName, int duration, View.OnClickListener listener) {
        View rootCoordinator = findViewById(idCoordinatorLayout);
        if (rootCoordinator != null) {
            showSnackBar(rootCoordinator, message, actionName, duration, listener);
        } else {
            showToast(message, this);
        }
    }
    @Override
    public void onEvent(String event, Object data) {
        switch (event){
            case EVENT_SHOW_PROGRESS:
                if (data != null && data instanceof String) {
                    showProgressActivity((String) data);
                }
                break;
            case EVENT_DISMISS_PROGRESS:
                hideProgressActivity();
                showMainViewActivity();
                break;
            case EVENT_SHOW_ERROR:
                if (data != null && data instanceof String) {
                    showMessage((String) data);
                }
                break;
            case EVENT_SHOW_MESSAGE:
                if (data != null && data instanceof String) {
                    showMessage((String) data);
                }
                break;
            case EVENT_HIDE_KEYBOARD:
                hideSoftKeyboard();
                break;
            case EVENT_SHOW_KEYBOARD:
                showSoftKeyboard();
                break;
            default:
                break;
        }
    }

    public IEventOnFragment getIEventOnFragment(){
        return this;
    }


    public void setExpectedMargins(View view, int left, int top, int right, int bottom)
    {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        left = left == 0 ? left : getReSizedValue(left);
        top = top == 0 ? top : getReSizedValue(top);
        right = right == 0 ? right : getReSizedValue(right);
        bottom = bottom == 0 ? bottom : getReSizedValue(bottom);

        params.setMargins(left,top,right,bottom);
        view.requestLayout();
    }


    private int getReSizedValue(int margin)
    {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                margin,
                getResources().getDisplayMetrics()
        );
    }

    protected void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void showSoftKeyboard(){
        View view = this.getCurrentFocus();
        InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        input.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /*** Clear all editText */
    protected void clearEditext(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearEditext((ViewGroup) view);
        }
    }


    @Override
    public void onBackPressed() {
        if(isShownProgressActivity()){
            return;
        }
        super.onBackPressed();
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
    protected void startAnimView(View view, int visibility, int duration){
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        in.setDuration(duration);
        out.setDuration(duration);
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
}
