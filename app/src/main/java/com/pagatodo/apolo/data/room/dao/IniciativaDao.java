package com.pagatodo.apolo.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pagatodo.apolo.data.room.entities.Iniciativa;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface IniciativaDao {

    @Insert(onConflict = IGNORE)
    void insertIniciativas(List<Iniciativa> iniciativas);

    @Query("SELECT * FROM Iniciativa ORDER BY Iniciativa.descripcion ASC")
    List<Iniciativa> selectAllIniciativas();

    @Query("DELETE FROM Iniciativa")
    void deleteAll();
}
