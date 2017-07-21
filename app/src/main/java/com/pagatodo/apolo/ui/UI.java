package com.pagatodo.apolo.ui;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class UI {

    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void Logs(String message_a, String message_b){
        Log.e(message_a, message_b);
    }

}

