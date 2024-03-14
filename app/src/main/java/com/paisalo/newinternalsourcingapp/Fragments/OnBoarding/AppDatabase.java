package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// AppDatabase.java
@Database(entities = {KYCData.class,Userdetail.class,KYCData2.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract KYCDataDao kycDataDao();

}
