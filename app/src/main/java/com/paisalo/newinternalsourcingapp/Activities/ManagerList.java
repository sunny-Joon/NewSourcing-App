package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.paisalo.newinternalsourcingapp.Adapters.ManagerListAdapter;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelclassesRoom.ManagerModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BranchStatusDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BranchStatusResponce;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DaoClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.ManagerListDataClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ManagerListAdapter adapter;
    DatabaseClass database;
    DaoClass daoClass;
    List<ManagerListDataClass> listDataClasses;
    SharedPreferences sharedPreferences1;
    String branchStatus, branchDisbursement, branchCrifScore, branchFunctions;

    String moduleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_list);


        branchFunction();
        Context context;
        getSupportActionBar().hide();

        moduleName = getIntent().getStringExtra("keyName");

        recyclerView = findViewById(R.id.managerListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = DatabaseClass.getInstance(ManagerList.this);
        daoClass = database.dao();


        DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listDataClasses = daoClass.getAllManagerDataList();
                Log.d("TAG", "onCreate: " + listDataClasses.size());
                adapter = new ManagerListAdapter(ManagerList.this, listDataClasses, moduleName);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    private void branchFunction() {
        ApiInterface apiInterface = ApiClient.getClient4().create(ApiInterface.class);

        // Fetch branch status (you need to define 'foCode' and 'creator')
        Call<BranchStatusResponce> call = apiInterface.getBranchStatus("001", GlobalClass.Creator);
        call.enqueue(new Callback<BranchStatusResponce>() {
            @Override
            public void onResponse(Call<BranchStatusResponce> call, Response<BranchStatusResponce> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BranchStatusResponce branchStatusResponce = response.body();
                    if (branchStatusResponce != null && branchStatusResponce.getData() != null) {
                        BranchStatusDataModel branchStatusDataModel = branchStatusResponce.getData();

                        branchStatus = branchStatusDataModel.getStatus().toString();
                        branchDisbursement = branchStatusDataModel.getStatus().toString();
                        branchCrifScore = branchStatusDataModel.getStatus().toString();
                        branchFunctions = branchStatusDataModel.getStatus().toString();

                        sharedPreferences1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences1.edit();
                        editor.putString("branch_status", branchStatus);
                        editor.putString("branch_disbursement", branchDisbursement);
                        editor.putString("branch_crifScore", branchCrifScore);
                        editor.putString("branch_functions", branchFunctions);
                        editor.apply();

                        Log.d("TAG", "Branch Status: " + branchStatus);
                        Log.d("TAG", "Branch Disbursement: " + branchDisbursement);
                        Log.d("TAG", "Branch CrifScore: " + branchCrifScore);
                        Log.d("TAG", "Branch Functions: " + branchFunctions);

                    }else {
                        Log.d("TAG", "onResponse:rp5 No data available in response.");
                    }

                }else {
                    Log.d("TAG", "onResponse:rp5 Response not successful or body is null. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<BranchStatusResponce> call, Throwable t) {
                Log.e("TAG", "Failed to fetch branch status", t);
            }
        });
    }
}