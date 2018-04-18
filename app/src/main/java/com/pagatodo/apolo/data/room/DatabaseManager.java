package com.pagatodo.apolo.data.room;

import android.os.AsyncTask;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.data.room.entities.Iniciativa;
import com.pagatodo.apolo.data.room.entities.Promotor;
import com.pagatodo.apolo.data.room.entities.Tienda;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by ozunigag on 15/03/2018.
 */

public class DatabaseManager {

    public DatabaseManager() {
    }

    /* Inserción de la lista de promotores en la BD */
    public void insertPromotores(final List<Promotor> promotores) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                App.getAppDatabase().promotorModel().insertPromotores(promotores);
                return null;
            }
        }.execute();
    }

    /* Obtener la lista de promotores de la BD */
    public List<Promotor> getPromotorList() throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, List<Promotor>>() {
            @Override
            protected List<Promotor> doInBackground(Void... voids) {
                return App.getAppDatabase().promotorModel().selectAllPromotores();
            }
        }.execute().get();
    }

    /* Borrar todos los promotores de la BD */
    public void deleteAllPromotores() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                App.getAppDatabase().promotorModel().deleteAll();
                return null;
            }
        }.execute();
    }

    /* Validar que el promotor exista en la BD */
    public boolean promotorExist(final String promotor) throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                int exists = App.getAppDatabase().promotorModel().promotorExists(promotor);
                return exists > 0;
            }
        }.execute().get();
    }

    /* Validar que el promotor se encuentre activo en la BD */
    public boolean isPromotorActive(final String promotor) throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return App.getAppDatabase().promotorModel().isPromotorActive(promotor);
            }
        }.execute().get();
    }

    /* Inserción de la lista de iniciativas en la BD */
    public void insertIniciativas(final List<Iniciativa> iniciativas) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                App.getAppDatabase().iniciativaModel().insertIniciativas(iniciativas);
                return null;
            }
        }.execute();
    }

    /* Obtener la lista de iniciativas de la BD */
    public List<Iniciativa> getIniciativasList() throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, List<Iniciativa>>() {
            @Override
            protected List<Iniciativa> doInBackground(Void... voids) {
                return App.getAppDatabase().iniciativaModel().selectAllIniciativas();
            }
        }.execute().get();
    }

    /* Borrar todos las iniciativas de la BD */
    public void deleteAllIniciativas() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                App.getAppDatabase().iniciativaModel().deleteAll();
                return null;
            }
        }.execute();
    }

    /* Inserción de la lista de tiendas en la BD */
    public void insertTiendas(final List<Tienda> tiendas) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                App.getAppDatabase().tiendaModel().insertTiendas(tiendas);
                return null;
            }
        }.execute();
    }

    /* Obtener la lista de tiendas de la BD */
    public List<Tienda> getTiendaList() throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, List<Tienda>>() {
            @Override
            protected List<Tienda> doInBackground(Void... voids) {
                return App.getAppDatabase().tiendaModel().selectAllTiendas();
            }
        }.execute().get();
    }

    /* Obtener la lista de tiendas por iniciativa de la BD */
    public List<Tienda> getTiendasByIdIniciativa(final int idIniciativa) throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, List<Tienda>>() {
            @Override
            protected List<Tienda> doInBackground(Void... voids) {
                return App.getAppDatabase().tiendaModel().selectByIdIniciativa(idIniciativa);
            }
        }.execute().get();
    }

    /* Borrar todos las tiendas de la BD */
    public void deleteAllTiendas() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                App.getAppDatabase().tiendaModel().deleteAll();
                return null;
            }
        }.execute();
    }
}
