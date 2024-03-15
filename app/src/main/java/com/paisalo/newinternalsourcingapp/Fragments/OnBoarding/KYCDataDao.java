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

    @Insert
    void insert(KYCData2 kycData2);

    @Update
    void update(KYCData kycData);

    @Update
    void update(KYCData2 kycData2);

    @Delete
    void delete(KYCData kycData);

    @Delete
    void delete(KYCData2 kycData2);

    @Query("SELECT * FROM kyc_data_table")
    List<KYCData> getAllKYCData();

    @Query("SELECT * FROM kyc_data_table2")

    List<KYCData2> getAllKYCData2();



    @Insert
    void userDeatailInsert(Userdetail userdetail);

    @Insert
    void kycdatadetail(KYCData kycData);

    @Insert
    void kycdatadetail2(KYCData2 kycData2);




}

