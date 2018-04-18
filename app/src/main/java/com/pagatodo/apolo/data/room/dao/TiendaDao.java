package com.pagatodo.apolo.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pagatodo.apolo.data.room.entities.Tienda;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface TiendaDao {

    @Insert(onConflict = IGNORE)
    void insertTiendas(List<Tienda> tiendas);

    @Query("SELECT * FROM Tienda ORDER BY Tienda.nombre ASC")
    List<Tienda> selectAllTiendas();

    @Query("SELECT * FROM Tienda WHERE Tienda.id_iniciativa = :idIniciativa ORDER BY Tienda.nombre ASC")
    List<Tienda> selectByIdIniciativa(int idIniciativa);

    @Query("DELETE FROM Tienda")
    void deleteAll();
}
