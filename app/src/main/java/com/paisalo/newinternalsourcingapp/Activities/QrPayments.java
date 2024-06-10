package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paisalo.newinternalsourcingapp.Adapters.QrDataListAdapter;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.AccountDetails_Model;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.QrDataModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.Utils.CustomProgressDialog;
import com.paisalo.newinternalsourcingapp.Utils.Utils;
import java.lang.reflect.Type;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QrPayments extends AppCompatActivity {

    EditText accountNo;
    Button searchButton;
    ListView accountListView;

    QrDataListAdapter accountDataListAdapter;
    CustomProgressDialog customProgressDialog;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_payments);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        accountListView = findViewById(R.id.accountListView);
        accountNo = findViewById(R.id.accountNo);
        searchButton = findViewById(R.id.searchButton);

        customProgressDialog = new CustomProgressDialog(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customProgressDialog.show();
//              String smCode = accountNo.getText().toString();
                if (accountNo.getText().length() < 2) {
                    customProgressDialog.dismiss();
                    accountNo.setError("Please Enter Correct Account Number");
                    Utils.alert(QrPayments.this,"Please Enter Correct Account Number");
                } else {
                    String smCode = accountNo.getText().toString().trim();
                    String userId = GlobalClass.Id;
                    String type = "Mobile";
                    fetchDataFromApi(smCode, userId, type );
                }
            }
        });
    }

    private void fetchDataFromApi(String smCode, String userId, String type) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AccountDetails_Model> call=apiInterface.getQrPaymentsBySmcode(GlobalClass.Token,GlobalClass.dbname,smCode, userId, type);

        call.enqueue(new Callback<AccountDetails_Model>() {
            @Override
            public void onResponse(Call<AccountDetails_Model> call, Response<AccountDetails_Model> response) {
                Log.d("TAG", "onResponse: "+response.body());
                if (response.isSuccessful()) {
                    AccountDetails_Model accountDetailsModel = response.body();
                    Log.d("TAG", "onResponse: "+response.body());

                    if (accountDetailsModel != null && accountDetailsModel.getData() != null) {
                        if (accountDetailsModel.getData().replace(" ","").toUpperCase().contains("SMCODENOTMAPPEDGIVENUSERID"))
                        {
                            Utils.alert(QrPayments.this,"Account No. not belongs to you. \n Please check and enter correct account number");
                        }else{
                            Gson gson = new Gson();
                            Log.d("TAG", "onResponse: +"+accountDetailsModel.getData());
                            QrDataModel[] nameList = gson.fromJson(accountDetailsModel.getData(), QrDataModel[].class);
                            accountDataListAdapter = new QrDataListAdapter(QrPayments.this, nameList);
                            accountListView.setAdapter(accountDataListAdapter);
                            accountDataListAdapter.notifyDataSetChanged();
                        }

                    }
                } else {
                    Toast.makeText(QrPayments.this, "API call failed", Toast.LENGTH_SHORT).show();
                }
                customProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AccountDetails_Model> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
                customProgressDialog.dismiss();
                Toast.makeText(QrPayments.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<AccountDetails_Model> parseMyDataList(String jsonArrayString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<AccountDetails_Model>>() {}.getType();
        return gson.fromJson(jsonArrayString, listType);
    }




}