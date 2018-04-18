package com.pagatodo.apolo.activity.login._presenter;

import android.content.Context;
import android.os.Handler;
import android.widget.ArrayAdapter;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginInteractor;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginPresenter;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginView;
import com.pagatodo.apolo.data.room.DatabaseManager;
import com.pagatodo.apolo.data.room.entities.Iniciativa;
import com.pagatodo.apolo.data.room.entities.Promotor;
import com.pagatodo.apolo.data.room.entities.Tienda;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.apolo.data.local.Preferences.createSession;

/**
 * Created by rvargas on 21-07-17.
 */

public class LoginPresenterImpl extends BasePresenter<LoginView> implements LoginPresenter, LoginInteractor.onLoginListener {
    private static final int TIME_TO_LOGIN = 2000;

    private LoginInteractor loginInteractor;
    private List<Iniciativa> iniciativaList = new ArrayList<>();
    private List<Tienda> tiendaList = new ArrayList<>();
    private int idIniciativa, idTienda;

    public LoginPresenterImpl(LoginView loginView) {
        super(loginView);
        loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void login(final String numberUser, final int idIniciativa, final int idTienda) {
        view.showProgress(getString(R.string.progress_login));
        this.idIniciativa = idIniciativa;
        this.idTienda = idTienda;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginInteractor.onLogin(numberUser, LoginPresenterImpl.this);
            }
        }, TIME_TO_LOGIN);
    }

    @Override
    public boolean promotorExists(String promotor) {
        boolean exists = false;
        try {
            exists = new DatabaseManager().promotorExist(promotor);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return exists;
    }

    @Override
    public boolean isPromotorActive(String promotor) {
        boolean isActive = false;
        try {
            isActive = new DatabaseManager().isPromotorActive(promotor);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return isActive;
    }

    @Override
    public ArrayAdapter<String> getAdapterIniciativas(Context context) {
        List<String> values = new ArrayList<>();
        try {
            iniciativaList = new DatabaseManager().getIniciativasList();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        for (Iniciativa iniciativa : iniciativaList) {
            values.add(iniciativa.getDescripcion());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        return adapter;
    }

    @Override
    public ArrayAdapter<String> getAdapterTiendas(Context context, int idIniciativa) {
        List<String> values = new ArrayList<>();
        try {
            tiendaList = new DatabaseManager().getTiendasByIdIniciativa(idIniciativa);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        for (Tienda tienda : tiendaList) {
            values.add(tienda.getNombre());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        return adapter;
    }

    @Override
    public int getIdIniciativa(int position) {
        return iniciativaList.get(position).getID_Iniciativa();
    }

    @Override
    public int getIdTienda(int position) {
        return tiendaList.get(position).getID_Tienda();
    }

    @Override
    public void onSuccess(Promotor promotor) {
        if (view != null) {
            createSession(pref, promotor, idIniciativa, idTienda);
            view.setNavigation();
//            view.hideProgress();
        }
    }

    @Override
    public void failure(String message) {
        if (view != null) {
            view.hideProgress();
            view.showMessage(message);
        }
    }

    @Override
    public void onUserNumberError() {
        if (view != null) {
            view.hideProgress();
            view.setUserNumberError();
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }
}
