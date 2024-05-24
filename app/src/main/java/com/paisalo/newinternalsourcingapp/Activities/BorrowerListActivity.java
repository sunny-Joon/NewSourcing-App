package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.paisalo.newinternalsourcingapp.Adapters.BorrowerListAdapter;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels.BorrowerListDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels.BorrowerListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels.ManagerListModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowerListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BorrowerListAdapter borrowerListAdapter;
    List<BorrowerListDataModel> borrowerListDataModel;
    String id,foCode,creator,areaCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowerlist);

        Intent intent = getIntent();
        id = intent.getStringExtra("keyName");
        foCode = intent.getStringExtra("foCode");
        creator = intent.getStringExtra("creator");
        areaCode = intent.getStringExtra("areaCode");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.esignRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if(id.equals("FEsign")){
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<BorrowerListModel> call = apiInterface.PendingFEsign(GlobalClass.Token, BuildConfig.dbname,GlobalClass.Imei,foCode,areaCode,creator);
            call.enqueue(new Callback<BorrowerListModel>() {
                @Override
                public void onResponse(Call<BorrowerListModel> call, Response<BorrowerListModel> response) {
                    //  Log.d("TAG", "onResponseFoList: "+ response.body());
                    if(response.isSuccessful()){
                        Log.d("TAG", "onResponseFoList: "+ response.body());
                        BorrowerListModel borrowerListModel = response.body();
                        borrowerListDataModel = borrowerListModel.getData();
                        if(borrowerListDataModel != null && borrowerListDataModel.size()>0) {
                            Log.d("TAG", "onResponseFoList1: "+ response.body());
                            borrowerListAdapter = new BorrowerListAdapter(BorrowerListActivity.this, borrowerListDataModel);
                            recyclerView.setAdapter(borrowerListAdapter);
                        }
                    }else{
                        Log.d("TAG", "onResponseFoList: "+ response.code());

                    }
                }

                @Override
                public void onFailure(Call<BorrowerListModel> call, Throwable t) {
                    Log.d("TAG", "onResponseFoList: "+ t.getMessage());


                }
            });
        } else if (id.equals("SEsign")) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<BorrowerListModel> call = apiInterface.PendingSEsign(GlobalClass.Token, BuildConfig.dbname,GlobalClass.Imei,foCode,areaCode,creator);
            call.enqueue(new Callback<BorrowerListModel>() {
                @Override
                public void onResponse(Call<BorrowerListModel> call, Response<BorrowerListModel> response) {
                    //  Log.d("TAG", "onResponseFoList: "+ response.body());
                    if(response.isSuccessful()){
                        Log.d("TAG", "onResponseFoList: "+ response.body());
                        BorrowerListModel borrowerListModel = response.body();
                        borrowerListDataModel = borrowerListModel.getData();
                        Log.d("TAG", "onResponseFoList: " + borrowerListDataModel.get(0).getAadharid());
                        if(borrowerListDataModel.size()>0) {
                            Log.d("TAG", "onResponseFoList1: "+ response.body());
                            borrowerListAdapter = new BorrowerListAdapter(BorrowerListActivity.this, borrowerListDataModel);
                            recyclerView.setAdapter(borrowerListAdapter);
                        }
                    }else{
                        Log.d("TAG", "onResponseFoList: "+ response.code());

                    }
                }

                @Override
                public void onFailure(Call<BorrowerListModel> call, Throwable t) {
                    Log.d("TAG", "onResponseFoList: "+ t.getMessage());


                }
            });

        } else if (id.equals("HVisit") ||id.equals("Application")) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<BorrowerListModel> call = apiInterface.PendingApplicationForms(GlobalClass.Token, BuildConfig.dbname,GlobalClass.Imei,foCode,areaCode,creator);
            call.enqueue(new Callback<BorrowerListModel>() {
                @Override
                public void onResponse(Call<BorrowerListModel> call, Response<BorrowerListModel> response) {
                    //  Log.d("TAG", "onResponseFoList: "+ response.body());
                    if(response.isSuccessful()){
                        Log.d("TAG", "onResponseFoList: "+ response.body());
                        BorrowerListModel borrowerListModel = response.body();
                        borrowerListDataModel = borrowerListModel.getData();
                        Log.d("TAG", "onResponseFoList: " + borrowerListDataModel.get(0).getAadharid());
                        if(borrowerListDataModel.size()>0) {
                            Log.d("TAG", "onResponseFoList1: "+ response.body());
                            borrowerListAdapter = new BorrowerListAdapter(BorrowerListActivity.this, borrowerListDataModel);
                            recyclerView.setAdapter(borrowerListAdapter);
                        }
                    }else{
                        Log.d("TAG", "onResponseFoList: "+ response.code());

                    }
                }

                @Override
                public void onFailure(Call<BorrowerListModel> call, Throwable t) {
                    Log.d("TAG", "onResponseFoList: "+ t.getMessage());


                }
            });
        } else if (id.equals("collection")) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<BorrowerListModel> call = apiInterface.PendingCollection(GlobalClass.Token, BuildConfig.dbname,GlobalClass.Imei,foCode,areaCode,creator);
            call.enqueue(new Callback<BorrowerListModel>() {
                @Override
                public void onResponse(Call<BorrowerListModel> call, Response<BorrowerListModel> response) {
                    //  Log.d("TAG", "onResponseFoList: "+ response.body());
                    if(response.isSuccessful()){
                        Log.d("TAG", "onResponseFoList: "+ response.body());
                        BorrowerListModel borrowerListModel = response.body();
                        borrowerListDataModel = borrowerListModel.getData();
                        Log.d("TAG", "onResponseFoList: " + borrowerListDataModel.get(0).getAadharid());
                        if(borrowerListDataModel.size()>0) {
                            Log.d("TAG", "onResponseFoList1: "+ response.body());
                            borrowerListAdapter = new BorrowerListAdapter(BorrowerListActivity.this, borrowerListDataModel);
                            recyclerView.setAdapter(borrowerListAdapter);
                        }
                    }else{
                        Log.d("TAG", "onResponseFoList: "+ response.code());

                    }
                }

                @Override
                public void onFailure(Call<BorrowerListModel> call, Throwable t) {
                    Log.d("TAG", "onResponseFoList: "+ t.getMessage());

                }
            });
        }


    }
}
