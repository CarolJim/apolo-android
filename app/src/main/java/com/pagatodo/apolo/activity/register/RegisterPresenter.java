package com.pagatodo.apolo.activity.register;

import com.pagatodo.apolo.data.pojo.Cards;
import java.util.List;

/**
 * Created by rvargas on 21-07-17.
 */

public interface RegisterPresenter {
    void request(List<Cards> cardsList);
    void register(String numberCelPhone, String numberPhone, String rutaCard, String rutaINEFront, String rutaINEBack);
}
