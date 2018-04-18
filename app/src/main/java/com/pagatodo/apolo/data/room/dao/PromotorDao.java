package com.pagatodo.apolo.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pagatodo.apolo.data.room.entities.Promotor;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface PromotorDao {

    @Insert(onConflict = IGNORE)
    void insertPromotores(List<Promotor> promotors);

    @Query("SELECT * FROM Promotor")
    List<Promotor> selectAllPromotores();

    @Query("DELETE FROM Promotor")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM Promotor WHERE promotor=:promotor")
    int promotorExists(String promotor);

    @Query("SELECT activo FROM Promotor WHERE promotor=:promotor")
    boolean isPromotorActive(String promotor);
}
