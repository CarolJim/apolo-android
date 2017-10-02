package com.pagatodo.apolo.activity.register._presenter;

import android.content.Context;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterInteractor;
import com.pagatodo.apolo.data.model.Cards;
import java.util.List;
import static android.text.TextUtils.isEmpty;

/**
 * Created by rvargas on 21-07-17.
 */

public class RegisterInteractorImpl implements RegisterInteractor {
    Context context = App.getInstance();

    @Override
    public void onRegisterAfiliado(String numberCelPhone, String numberPhone, String rutaCard, String rutaINEFront, String rutaINEBack, onRegisterListener listener) {
        if(!isEmpty(numberCelPhone) && rutaCard != null && rutaINEFront != null && rutaINEBack != null){
            listener.onSuccess();
        }else {
            listener.failure(context.getString(R.string.msg_field_empty));
        }
    }

    @Override
    public void onRequestData(final List<Cards> cardsList, final onRequestListener listener) {
        /*int[] cards = new int[]{R.drawable.btn_tarjeta_ap, R.drawable.btn_inefrente_ap, R.drawable.btn_inevuelta_ap};
        int[] brands = new int[]{R.drawable.ic_tarjeta_ap, R.drawable.ic_inefront_ap, R.drawable.ic_ineback_ap};
        int[] icon = new int[]{R.drawable.ic_check_ap, R.drawable.ic_check_ap, R.drawable.ic_check_ap};

        for(int i=0; i < cards.length; i++){
            Cards items = new Cards();
            items.setTypeCard(cards[i]);
            items.setThumbCard(brands[i]);
            items.setIvCheck(icon[i]);
            cardsList.add(items);
        }*/
        listener.onSuccessRequest();
    }

    @Override
    public void onLogout(logoutListener listen){
        listen.onSuccessLogout();
    }

}
