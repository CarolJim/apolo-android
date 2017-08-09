package com.pagatodo.apolo.ui;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class UI {

    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static void showSnackBar(View coordinatorLayout, String message, String actionName, View.OnClickListener listener){
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).setAction(actionName, listener).show();
    }
    public static void showSnackBar(View coordinatorLayout, String message){
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }
    public static void showSnackBar(View coordinatorLayout, String message, String actionName, int duration, View.OnClickListener listener){
        Snackbar.make(coordinatorLayout, message, duration).setAction(actionName, listener).show();
    }
}

