package com.pagatodo.apolo.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.pagatodo.apolo.data.room.dao.IniciativaDao;
import com.pagatodo.apolo.data.room.dao.PromotorDao;
import com.pagatodo.apolo.data.room.dao.TiendaDao;
import com.pagatodo.apolo.data.room.entities.Iniciativa;
import com.pagatodo.apolo.data.room.entities.Promotor;
import com.pagatodo.apolo.data.room.entities.Tienda;

import static com.pagatodo.apolo.utils.Constants.DATABASE_NAME;
import static com.pagatodo.apolo.utils.Constants.DATABASE_VERSION;

/**
 * Created by ozunigag on 15/03/2018.
 */
@Database(entities = {Promotor.class, Iniciativa.class, Tienda.class}, version = DATABASE_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract PromotorDao promotorModel();

    public abstract IniciativaDao iniciativaModel();

    public abstract TiendaDao tiendaModel();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
