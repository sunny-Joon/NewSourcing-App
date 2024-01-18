package com.paisalo.newinternalsourcingapp.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoClass {
    @Insert
    void insert(EntityClass entity);

    @Update
    void update(EntityClass entity);

    @Delete
    void delete(EntityClass entity);

    @Query("SELECT * FROM KYC_Database")
    List<EntityClass> getAllEntities();
}

