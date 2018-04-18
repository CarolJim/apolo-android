package com.pagatodo.apolo.activity.login._presenter;

import android.content.Context;
import android.text.TextUtils;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginInteractor;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.data.room.DatabaseManager;
import com.pagatodo.apolo.data.room.entities.Promotor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by rvargas on 21-07-17.
 */

public class LoginInteractorImpl implements LoginInteractor {
    Preferences prefs = App.getInstance().getPrefs();
    Context context = App.getInstance();

    @Override
    public void onLogin(String numberUser,onLoginListener listener) {
        Promotor promotor = validateUser(numberUser);
        if (TextUtils.isEmpty(numberUser)) {
            listener.onUserNumberError();
        } else if (promotor != null) {
            listener.onSuccess(promotor);
        } else {
            listener.failure(context.getString(R.string.error_invalid_id));
        }
    }

    private Promotor validateUser(String passPromotor) {
        List<Promotor> promotores = new ArrayList<>();
        try {
            promotores = new DatabaseManager().getPromotorList();
        } catch (ExecutionException|InterruptedException e) {
            e.printStackTrace();
        }
        for (Promotor promotor : promotores) {
            if (promotor.getPromotor().equalsIgnoreCase(passPromotor)) {
                return promotor;
            }
        }
        return null;
    }
}
