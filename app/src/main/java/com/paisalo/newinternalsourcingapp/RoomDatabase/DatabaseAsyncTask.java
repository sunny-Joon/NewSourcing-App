package com.paisalo.newinternalsourcingapp.RoomDatabase;

import android.os.AsyncTask;

import java.util.List;

public class DatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
    private final DaoClass dao;
    private final List<ManagerListDataClass> MLdata;
    private final List<RangeCategoryDataClass> RCdata;
    private final loginDataClass LDdata;

    public DatabaseAsyncTask(DaoClass dao,List<ManagerListDataClass> MLdata,List<RangeCategoryDataClass> RCdata,loginDataClass LDdata) {
        this.dao = dao;
        this.MLdata = MLdata;
        this.RCdata = RCdata;
        this.LDdata = LDdata;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Perform database operation in the background thread
        dao.insertManagerListData(MLdata);
        dao.insertLoginData(LDdata);
        dao.insertRCData(RCdata);
        return null;
    }
}
