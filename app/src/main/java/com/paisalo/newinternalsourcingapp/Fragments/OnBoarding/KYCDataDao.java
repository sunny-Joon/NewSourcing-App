package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// KYCDataDao.java
@Dao
public interface KYCDataDao {
    @Insert
    void insert(KYCData kycData);

    @Update
    void update(KYCData kycData);

    @Delete
    void delete(KYCData kycData);

    @Query("SELECT * FROM kyc_data_table")
    List<KYCData> getAllKYCData();

    // You can add more queries as needed
}