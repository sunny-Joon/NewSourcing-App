package com.paisalo.newinternalsourcingapp.RoomDatabase;

import android.os.AsyncTask;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DaoClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.KycDataClass;

public class DatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
    private final DaoClass dao;
    private final KycDataClass data;

    public DatabaseAsyncTask(DaoClass dao, KycDataClass data) {
        this.dao = dao;
        this.data = data;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Perform database operation in the background thread
        dao.insertKycData(data);
        return null;
    }
}
