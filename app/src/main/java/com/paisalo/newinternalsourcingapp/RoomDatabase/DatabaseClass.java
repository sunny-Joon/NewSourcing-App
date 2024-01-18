package com.paisalo.newinternalsourcingapp.RoomDatabase;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {EntityClass.class}, version = 1)
public abstract class DatabaseClass extends RoomDatabase {
    public abstract DaoClass dao();

    private static DatabaseClass instance;

    public static synchronized DatabaseClass getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    DatabaseClass.class,
                    "kyc_database_Class"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}