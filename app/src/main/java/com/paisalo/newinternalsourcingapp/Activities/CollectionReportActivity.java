package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.Modelclasses.EmiCollectionModels.CollectionReportModel;
import com.paisalo.newinternalsourcingapp.Modelclasses.EmiCollectionModels.Data;
import com.paisalo.newinternalsourcingapp.Modelclasses.EmiCollectionModels.Emi;
import com.paisalo.newinternalsourcingapp.Modelclasses.EmiCollectionModels.EmiCollection;
import com.paisalo.newinternalsourcingapp.Modelclasses.InstallmentColl;
import com.paisalo.newinternalsourcingapp.Modelclasses.PaidInstallment;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectionReportActivity extends AppCompatActivity {

    EditText search;
    Button getdata;
    private List<InstallmentColl> installmentList;
    private RecyclerView recyclerView1, recyclerView2;
    private InstallmentAdapter installmentAdapter;
    private PaidInstallmentAdapter paidInstallmentAdapter;
    private List<PaidInstallment> paidInstallmentList;
    private ProgressDialog progressDialog;
    TextView itemcount, paiditemcount;
    String smcode = "";

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_report);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        search = findViewById(R.id.search);
        getdata = findViewById(R.id.getdata);
        recyclerView1 = findViewById(R.id.recycleview1);
        recyclerView2 = findViewById(R.id.recycleview2);
        installmentList = new ArrayList<>();
        paidInstallmentList = new ArrayList<>();
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        itemcount = findViewById(R.id.itemcount);
        paiditemcount = findViewById(R.id.paiditemcount);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                smcode = search.getText().toString().trim();
                if (smcode != null && !smcode.isEmpty() && smcode.length() > 0 && validatesmcode(smcode)) {

                    installmentList.clear();
                    paidInstallmentList.clear();
                    progressDialog.show();
                    getapidata(smcode);

                } else {
                    Toast.makeText(CollectionReportActivity.this, "SmCode is Incorrect", Toast.LENGTH_SHORT).show();

                }
            }

            private void getapidata(String SmCode) {

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<CollectionReportModel> call = apiInterface.getCollectionReprt(GlobalClass.Token,GlobalClass.dbname,SmCode);

                call.enqueue(new Callback<CollectionReportModel>() {
                    @Override
                    public void onResponse(Call<CollectionReportModel> call, Response<CollectionReportModel> response) {

                        if (response.isSuccessful()) {


                            int emiListSize, EmiCollectionSize;
                            Log.d("TAG", "onResponse: " + response.body());
                            CollectionReportModel collectionReportModel = response.body();
                            Data data = collectionReportModel.getData();

                            List<Emi> emiList = data.getEmis();
                            for (Emi emi : emiList) {

                                SimpleDateFormat simpleDateFormat = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                                }
                                SimpleDateFormat desiredDateFormat = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    desiredDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                                }


                                Date date = null;
                                try {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        date = simpleDateFormat.parse(emi.getPvNRCPDT());
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                String formattedDate = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    formattedDate = desiredDateFormat.format(date);
                                }

                                InstallmentColl installmentColl = new InstallmentColl(emi.getAmt().toString(), formattedDate);
                                installmentList.add(installmentColl);
                            }

                            Log.d("TAG_list", "onResponse: " + installmentList);
                            installmentAdapter = new InstallmentAdapter(installmentList);
                            List<EmiCollection> emiCollections = data.getEmiCollections();

                            for (EmiCollection emiCollection : emiCollections) {

                                SimpleDateFormat simpleDateFormat = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                                }
                                SimpleDateFormat desiredDateFormat = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    desiredDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                                }


                                Date date = null;
                                try {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        date = simpleDateFormat.parse(emiCollection.getVdate());
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                String formattedDate = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                    formattedDate = desiredDateFormat.format(date);
                                }

                                PaidInstallment paidInstallment = new PaidInstallment(emiCollection.getCr().toString(), formattedDate);
                                paidInstallmentList.add(paidInstallment);
                            }
                            Log.d("TAG_list", "onResponse: " + paidInstallmentList);
                            paidInstallmentAdapter = new PaidInstallmentAdapter(paidInstallmentList);

                            progressDialog.dismiss();
                            recyclerView1.setAdapter(installmentAdapter);
                            recyclerView2.setAdapter(paidInstallmentAdapter);
                            installmentAdapter.notifyDataSetChanged();
                            paidInstallmentAdapter.notifyDataSetChanged();

                            //itemcount
                            emiListSize = emiList.size();
                            EmiCollectionSize = emiCollections.size();
                            itemcount.setText("(" + emiListSize + ")");
                            paiditemcount.setText("(" + EmiCollectionSize + ")");


                        } else {
                            Log.d("TAG_response", "onResponse:5 " + response.code());
                        }


                    }


                    @Override
                    public void onFailure(Call<CollectionReportModel> call, Throwable t) {
                        Log.d("TAG_response", "onResponse: 4" + "fail");
                        progressDialog.dismiss();

                    }
                });

            }
        });


        progressDialog.dismiss();

    }

    public static boolean validatesmcode(String input) {
        String regex = "^[a-zA-Z]{4}\\d{6}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input).matches();
    }
}

