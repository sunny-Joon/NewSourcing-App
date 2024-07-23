package com.paisalo.newinternalsourcingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paisalo.newinternalsourcingapp.Adapters.CustomerListAdapter;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.EsignListDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.EsignListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.Guarantor;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.PendingESignFI;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EsignListActivity extends AppCompatActivity implements CustomerListAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private CustomerListAdapter customerListAdapter;
    ArrayList<Guarantor> guarantorArrayList;
    private String id, foCode, creator, areaCode;

    PendingESignFI pendingESignFI;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esign_list);

        Intent intent = getIntent();
        id = intent.getStringExtra("keyName");
        foCode = intent.getStringExtra("foCode");
        creator = intent.getStringExtra("creator");
        areaCode = intent.getStringExtra("areaCode");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerViewEsign);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchBorrowerList();
    }

    private void fetchBorrowerList() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<EsignListModel> call = null;

        switch (id) {
            case "FEsign":
                call = apiInterface.PendingFEsign(GlobalClass.Token, BuildConfig.dbname, GlobalClass.Imei, foCode, areaCode, creator);
                break;
            case "SEsign":
                call = apiInterface.PendingSEsign(GlobalClass.Token, BuildConfig.dbname, GlobalClass.Imei, foCode, areaCode, creator);
                break;
        }

        if (call != null) {
            call.enqueue(new Callback<EsignListModel>() {
                @Override
                public void onResponse(Call<EsignListModel> call, Response<EsignListModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        EsignListDataModel esignListDataModel = response.body().getData();
                        if (esignListDataModel != null ) {

                            customerListAdapter = new CustomerListAdapter((Context) EsignListActivity.this, esignListDataModel, (CustomerListAdapter.OnItemClickListener) EsignListActivity.this);
                            recyclerView.setAdapter(customerListAdapter);
                        }
                    } else {
                        Log.d("BorrowerListActivity", "Response Code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<EsignListModel> call, Throwable t) {
                    Log.d("BorrowerListActivity", "Error: " + t.getMessage());
                }
            });
        }
    }

    public void onItemClick(PendingESignFI pendingESignFI, ArrayList<Guarantor> guarantorArrayList) {
        this.pendingESignFI = pendingESignFI;
        this.guarantorArrayList = guarantorArrayList;
        openActivity();
    }

    private void openActivity() {
        Intent intent = null;
        switch (id) {
            case "FEsign":
                intent = new Intent(this, FirstEsignActivity.class);
                intent.putExtra(GlobalClass.ESIGN_TYPE_TAG, 1);
                intent.putExtra(GlobalClass.ESIGN_BORROWER, pendingESignFI);
                break;
            case "SEsign":
                intent = new Intent(this, SecondEsignActivity.class);
                intent.putExtra(GlobalClass.ESIGN_TYPE_TAG, 0);
                intent.putExtra(GlobalClass.ESIGN_BORROWER, pendingESignFI);
                intent.putExtra(GlobalClass.ESIGN_GUARANTOR, guarantorArrayList);

                break;
          
        }

        if (intent != null) {
            startActivity(intent);
        } else {
            Log.e("EsignListActivity", "Invalid id received: " + id);
        }
    }

}
