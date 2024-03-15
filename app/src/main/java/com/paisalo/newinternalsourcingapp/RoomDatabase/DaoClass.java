package com.paisalo.newinternalsourcingapp.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoClass {
    @Insert
    void insertKycData(KycDataClass entity);

    @Insert
    void insertLoginData(loginDataClass entity1);

    @Insert
    void  insertRCData(List<RangeCategoryDataClass> entity2);

    @Update
    void updateKycData(KycDataClass entity);

    @Update
    void updateLoginData(loginDataClass entity1);
    @Update
    void  updateRCData(RangeCategoryDataClass entity2);

    @Delete
    void delete(KycDataClass entity);

    @Delete
    void delete(loginDataClass entity1);
    @Delete
    void  deleteRCData(RangeCategoryDataClass entity2);

    // Define asynchronous queries using LiveData or Flow
    @Query("SELECT * FROM KYC_Database")
    LiveData<List<KycDataClass>> getAllEntities();

    @Query("SELECT * FROM loginDataBase")
    LiveData<List<loginDataClass>> loginDataList();

    @Query("SELECT *  FROM RANGECATEGORY_DATABASE")
    LiveData<List<RangeCategoryDataClass>> RCDataList();

}
