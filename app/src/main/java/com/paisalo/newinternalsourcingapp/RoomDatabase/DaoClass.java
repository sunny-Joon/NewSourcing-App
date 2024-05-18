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
    void insertLoginData(loginDataClass entity1);

    @Insert
    void  insertRCData(List<RangeCategoryDataClass> entity2);

    @Insert
    void  insertManagerListData(List<ManagerListDataClass> entit3);

    @Update
    void updateLoginData(loginDataClass entity1);
    @Update
    void  updateRCData(RangeCategoryDataClass entity2);
    @Update
    void  updateManagerListData(ManagerListDataClass entity3);

   @Query("DELETE FROM ManagerListDataBase")
    void deleteAllManagerList();
    @Query("DELETE FROM RangeCategory_Database")
    void deleteAllRCList();

    @Query("SELECT * FROM loginDataBase")
    List<loginDataClass> getAllloginDataList();

    @Query("SELECT *  FROM RANGECATEGORY_DATABASE")
    List<RangeCategoryDataClass> getAllRCDataList();

    @Query("SELECT * FROM RANGECATEGORY_DATABASE WHERE cat_key = :catKey")
    List<RangeCategoryDataClass> getAllRCDataListby_catKey(String catKey);


    @Query("SELECT *  FROM ManagerListDataBase")
    List<ManagerListDataClass> getAllManagerDataList();

    @Query("Select * from RangeCategory_Database where cat_key=:catKey and code=:code")
    RangeCategoryDataClass getStateByCode(String catKey,String code);


}
