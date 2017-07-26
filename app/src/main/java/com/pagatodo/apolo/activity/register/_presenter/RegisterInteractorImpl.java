package com.pagatodo.apolo.activity.register._presenter;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterInteractor;
import com.pagatodo.apolo.data.pojo.Cards;
import java.util.List;
import static android.text.TextUtils.isEmpty;

/**
 * Created by rvargas on 21-07-17.
 */

public class RegisterInteractorImpl implements RegisterInteractor {

    @Override
    public void onRegisterAfiliado(String numberCelPhone, String numberPhone, String rutaCard, String rutaINEFront, String rutaINEBack, onRegisterListener listener) {
        if (isEmpty(numberCelPhone)){
            listener.messagesError("Número celular obligatorio");
        } else if (rutaCard == null){
            listener.messagesError("La fotografía de la tarjeta es obligatoria");
        } else if (rutaINEFront == null){
            listener.messagesError("La fotografía del INE frente es obligatoria");
        } else if (rutaINEBack == null){
            listener.messagesError("La fotografía del INE vuelta es obligatoria");
        } else if(!isEmpty(numberCelPhone) && rutaCard != null && rutaINEFront != null && rutaINEBack != null){
            listener.onSuccess();
        }else {
            listener.failure("Todos los campos son obligatorios");
        }
    }

    @Override
    public void onRequestData(final List<Cards> cardsList, final onRequestListener listener) {

        int[] cards = new int[]{R.drawable.btn_tarjeta_ap, R.drawable.btn_inefrente_ap, R.drawable.btn_inevuelta_ap};
        int[] brands = new int[]{R.drawable.ic_tarjeta_ap, R.drawable.ic_inefront_ap, R.drawable.ic_ineback_ap};
        int[] icon = new int[]{R.drawable.ic_check_ap, R.drawable.ic_check_ap, R.drawable.ic_check_ap};

        for(int i=0; i < cards.length; i++){
            Cards items = new Cards();
            items.setTypeCard(cards[i]);
            items.setThumbCard(brands[i]);
            items.setIvCheck(icon[i]);
            cardsList.add(items);
        }
        listener.onSuccessRequest();

    }
}
