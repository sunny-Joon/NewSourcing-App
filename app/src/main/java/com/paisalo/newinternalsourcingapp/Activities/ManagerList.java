package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.paisalo.newinternalsourcingapp.Adapters.ManagerListAdapter;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelclassesRoom.ManagerModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DaoClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.ManagerListDataClass;

import java.util.ArrayList;
import java.util.List;

public class ManagerList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ManagerListAdapter adapter;
    DatabaseClass database;
    DaoClass daoClass;
    List<ManagerListDataClass> listDataClasses;

    String moduleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_list);
        Context context;
        getSupportActionBar().hide();

        moduleName=getIntent().getStringExtra("keyName");

        recyclerView = findViewById(R.id.managerListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = DatabaseClass.getInstance(ManagerList.this);
        daoClass = database.dao();

        DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listDataClasses = daoClass.getAllManagerDataList();
                Log.d("TAG", "onCreate: " + listDataClasses.size());
                adapter = new ManagerListAdapter(ManagerList.this, listDataClasses,moduleName);
                recyclerView.setAdapter(adapter);
            }
        });

    }
}

