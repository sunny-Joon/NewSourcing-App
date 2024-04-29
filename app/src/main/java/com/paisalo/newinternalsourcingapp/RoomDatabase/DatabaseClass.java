package com.paisalo.newinternalsourcingapp.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {loginDataClass.class,RangeCategoryDataClass.class,ManagerListDataClass.class}, version = 1, exportSchema = false)
public abstract class DatabaseClass extends RoomDatabase {

    public abstract DaoClass dao();

    private static final int NUMBER_OF_THREADS = 4;

    // ExecutorService initialized after NUMBER_OF_THREADS
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile DatabaseClass INSTANCE;

    public static DatabaseClass getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseClass.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DatabaseClass.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
