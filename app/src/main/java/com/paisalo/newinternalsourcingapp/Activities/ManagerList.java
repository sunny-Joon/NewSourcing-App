package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.paisalo.newinternalsourcingapp.Adapters.LeaderBoardRecyclerViewAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.ManagerListAdapter;
import com.paisalo.newinternalsourcingapp.Modelclasses.ManagerModel;
import com.paisalo.newinternalsourcingapp.R;

import java.util.ArrayList;
import java.util.List;

public class ManagerList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ManagerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_list);
        Context context;

        recyclerView = findViewById(R.id.managerListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ManagerModel> dataList = getDataList();

        adapter = new ManagerListAdapter(this,dataList);

        recyclerView.setAdapter(adapter);
    }

    private List<ManagerModel> getDataList() {
        List<ManagerModel> dataList = new ArrayList<>();
        dataList.add(new ManagerModel("Baldev", "Place1/GroupCode1", "Place2/Group"));
        dataList.add(new ManagerModel("Baldev", "Place1/GroupCode1", "Place2/Group"));
        dataList.add(new ManagerModel("Baldev", "Place1/GroupCode1", "Place2/Group"));

        return dataList;
    }
    }

