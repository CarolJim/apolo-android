package com.pagatodo.apolo.activity.smsverification._presenter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pagatodo.apolo.activity.smsverification._presenter._interfaces.SmsInteractor;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.ui.base.factoryactivities.SupportFragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rvargas on 21-07-17.
 */

public class SmsInteractorImpl implements SmsInteractor {

    @Override
    public void onValidation(String codigo, onValidationListener listener) {
        if (TextUtils.isEmpty(codigo)){
            listener.onCodigoError();
        } else if(codigo.equals("123456")){
            listener.onSuccess();
        }else {
            listener.failure("El código de verificacón no es valida");
        }

    }

    @Override
    public void onConfirmation(String celular, final onConfirmationListener listener, Activity activity){

        RequestQueue queue = Volley.newRequestQueue(activity);
        final JSONObject jsonObj = new JSONObject();
        JSONObject jsonAdd = new JSONObject();
        try {
            jsonAdd.put("TelefonoCelular", celular);
            jsonObj.put("request", jsonAdd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, "http://172.16.2.117:8025/WSSolicitudCreditoAdultoMayor.svc/SendSMSConfirmation",jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onFailed(String.valueOf(error));
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("Promotor","PROM23");
                return params;
            }

        };
        queue.add(stringRequest);

    }
}
