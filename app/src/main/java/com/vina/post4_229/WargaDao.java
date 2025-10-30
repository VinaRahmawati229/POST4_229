package com.vina.post4_229;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WargaDao {

    @Insert
    long insertWarga(Warga warga);

    @Query("SELECT * FROM warga ORDER BY id DESC")
    List<Warga> getAllWarga();

    @Query("DELETE FROM warga")
    void deleteAllWarga();

    @Query("SELECT COUNT(*) FROM warga")
    int getWargaCount();
}