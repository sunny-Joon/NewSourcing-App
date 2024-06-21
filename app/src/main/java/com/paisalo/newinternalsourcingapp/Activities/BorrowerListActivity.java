package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.paisalo.newinternalsourcingapp.Adapters.BorrowerListAdapter;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.HouseVisitActivity1;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels.BorrowerListDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels.BorrowerListModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowerListActivity extends AppCompatActivity implements BorrowerListAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private BorrowerListAdapter borrowerListAdapter;
    private List<BorrowerListDataModel> borrowerListDataModel;
    private String id, foCode, creator, areaCode;

    BorrowerListDataModel adapterItem;
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

        fetchBorrowerList();
    }

    private void fetchBorrowerList() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BorrowerListModel> call = null;

        switch (id) {
            case "FEsign":
                call = apiInterface.PendingFEsign(GlobalClass.Token, BuildConfig.dbname, GlobalClass.Imei, foCode, areaCode, creator);
                break;
            case "SEsign":
                call = apiInterface.PendingSEsign(GlobalClass.Token, BuildConfig.dbname, GlobalClass.Imei, foCode, areaCode, creator);
                break;
            case "HVisit":
            case "Application":
                call = apiInterface.PendingApplicationForms(GlobalClass.Token, BuildConfig.dbname, GlobalClass.Imei, foCode, areaCode, creator);
                break;
            case "collection":
                call = apiInterface.PendingCollection(GlobalClass.Token, BuildConfig.dbname, GlobalClass.Imei, foCode, areaCode, creator);
                break;
        }

        if (call != null) {
            call.enqueue(new Callback<BorrowerListModel>() {
                @Override
                public void onResponse(Call<BorrowerListModel> call, Response<BorrowerListModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        borrowerListDataModel = response.body().getData();
                        if (borrowerListDataModel != null && !borrowerListDataModel.isEmpty()) {
                            borrowerListAdapter = new BorrowerListAdapter((Context) BorrowerListActivity.this, borrowerListDataModel, (BorrowerListAdapter.OnItemClickListener) BorrowerListActivity.this);
                            recyclerView.setAdapter(borrowerListAdapter);
                        }
                    } else {
                        Log.d("BorrowerListActivity", "Response Code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<BorrowerListModel> call, Throwable t) {
                    Log.d("BorrowerListActivity", "Error: " + t.getMessage());
                }
            });
        }
    }

    public void onItemClick(BorrowerListDataModel adapterItem) {
       this.adapterItem= adapterItem;
        openActivity(adapterItem);
    }

    private void openActivity(BorrowerListDataModel adapterItem) {
        Intent intent = null;
        switch (id) {
            case "FEsign":
                intent = new Intent(this, FirstEsignActivity.class);
                intent.putExtra(GlobalClass.ESIGN_TYPE_TAG, 1);
                intent.putExtra(GlobalClass.ESIGN_BORROWER,adapterItem);
                break;
            case "SEsign":
                intent = new Intent(this, SecondEsignActivity.class);
                intent.putExtra(GlobalClass.ESIGN_TYPE_TAG, 0);
                intent.putExtra(GlobalClass.ESIGN_BORROWER,adapterItem);
                break;
            case "Application":
                intent = new Intent(this, ApplicationFormActivityMenu.class);
                intent.putExtra("fiCode", String.valueOf(adapterItem.getCode()));
                Log.d("TAG", "openActivity: "+adapterItem.getCode() );
                intent.putExtra("creator", adapterItem.getCreator());
                break;
            case "HVisit":
                intent = new Intent(this, HouseVisitActivity1.class);
                intent.putExtra("fiCode", adapterItem.getCode());
                intent.putExtra("creator", adapterItem.getCreator());
                break;
        }

        if (intent != null) {
            startActivity(intent);
        } else {
            Log.e("BorrowerListActivity", "Invalid id received: " + id);
        }
    }
}
