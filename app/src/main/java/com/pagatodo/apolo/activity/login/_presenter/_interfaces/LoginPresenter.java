package com.pagatodo.apolo.activity.login._presenter._interfaces;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.pagatodo.apolo.data.room.entities.Iniciativa;
import com.pagatodo.apolo.data.room.entities.Tienda;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IProcessData;

import java.util.List;

/**
 * Created by rvargas on 21-07-17.
 */

public interface LoginPresenter extends IProcessData {

    void login(String numberUser, int idIniciativa, int idTienda);

    boolean promotorExists(String promotor);

    boolean isPromotorActive(String promotor);

    ArrayAdapter<String> getAdapterIniciativas(Context context);

    ArrayAdapter<String> getAdapterTiendas(Context context, int idIniciativa);

    int getIdIniciativa(int position);

    int getIdTienda(int position);
}
