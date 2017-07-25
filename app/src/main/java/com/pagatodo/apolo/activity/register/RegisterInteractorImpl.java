package com.pagatodo.apolo.activity.register;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.pagatodo.apolo.App;
import com.pagatodo.apolo.data.pojo.Cards;
import com.pagatodo.apolo.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.text.TextUtils.isEmpty;

/**
 * Created by rvargas on 21-07-17.
 */

public class RegisterInteractorImpl implements RegisterInteractor {

    @Override
    public void onRegisterAfiliado(String numberCelPhone, String numberPhone, String rutaCard, String rutaINEFront, String rutaINEBack, onRegisterListener listener) {
        if (isEmpty(numberCelPhone)){
            listener.messagesError("Número de celular obligatorio");
        } else if (rutaCard == null){
            listener.messagesError("La fotografia de la tarjeta es obligatoria");
        } else if (rutaINEFront == null){
            listener.messagesError("La fotografia del INE Enfrente es obligatoria");
        } else if (rutaINEBack == null){
            listener.messagesError("La fotografia del INE Vuelta es obligatoria");
        }
        else if(!isEmpty(numberCelPhone) && !isEmpty(numberPhone) && rutaCard != null && rutaINEFront != null && rutaINEBack != null){
            listener.onSuccess();
        }else {
            listener.failure("Favor de revisar todos los campos del formulario");
        }
    }

    @Override
    public void onRequestData(final List<Cards> cardsList, final onRequestListener listener) {

        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, Constants.URL_DEBUG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray objects = jsonObj.getJSONArray("cards");
                            for(int i=0; i < objects.length(); i++){
                                JSONObject item = objects.getJSONObject(i);
                                Cards items = new Cards();
                                items.setTypeCard(item.getString("card"));
                                items.setThumbCard(item.getString("thumb"));
                                cardsList.add(items);
                            }
                            listener.onSuccessRequest();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.failureRequest(error.getMessage());
            }
        });
        App.getInstance().addToRequestQueue(jsonObjReq);


    }
}
