package com.paisalo.newinternalsourcingapp.Activities;

import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiExtra;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiExtraBankBo;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiFamExpenses;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiFamLoan;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiFamMem;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiGuarantor;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.UploadedFiDocs;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TargetSetModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationFormActivityMenu extends AppCompatActivity   {

    CardView aadhaar,personalDetails,borrowings,guarantors,kycScanning,financialInfo,familyIncome;
    CheckBox kycScanningCheckBox, guarantorCheckBox,borrowingsCheckBox, familyIncomeCheckBox,financialInfoCheckBox,personaldetailCheckBox,aadhaarCheckBox;
    AllDataAFDataModel allDataAFDataModel;
    String fiCode,creator;

    @Override
    protected void onResume() {
        super.onResume();
        getFiEditDataFromServer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form_menu);

        Intent intent = getIntent();

        if(intent != null) {
            fiCode = intent.getStringExtra("fiCode");
            creator = intent.getStringExtra("creator");

            Log.d("Tag", "fiCode: " + fiCode);
            Log.d("Tag", "creator: " + creator);
        }

        aadhaar = findViewById(R.id.aadhaar);
        personalDetails = findViewById(R.id.personalDetails);
        borrowings = findViewById(R.id.borrowings);
        guarantors = findViewById(R.id.guarantors);
        kycScanning = findViewById(R.id.kycScanning);
        financialInfo = findViewById(R.id.financialInfo);
        familyIncome = findViewById(R.id.familyIncome);

        aadhaarCheckBox = findViewById(R.id.aadhaarCheckBox);
        personaldetailCheckBox = findViewById(R.id.personaldetailCheckBox);
        financialInfoCheckBox = findViewById(R.id.financialInfoCheckBox);
        familyIncomeCheckBox = findViewById(R.id.familyCheckBox);
        borrowingsCheckBox = findViewById(R.id.borrowingsCheckBox);
        guarantorCheckBox = findViewById(R.id.guarantorCheckBox);
        kycScanningCheckBox = findViewById(R.id.kycScanningCheckBox);

        aadhaarCheckBox.setChecked(false);
        aadhaarCheckBox.setClickable(false);
        personaldetailCheckBox.setChecked(false);
        personaldetailCheckBox.setClickable(false);
        financialInfoCheckBox.setChecked(false);
        financialInfoCheckBox.setClickable(false);
        familyIncomeCheckBox.setChecked(false);
        familyIncomeCheckBox.setClickable(false);
        borrowingsCheckBox.setChecked(false);
        borrowingsCheckBox.setClickable(false);
        guarantorCheckBox.setChecked(false);
        guarantorCheckBox.setClickable(false);
        kycScanningCheckBox.setChecked(false);
        kycScanningCheckBox.setClickable(false);

        getFiEditDataFromServer();

        aadhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","aadhaar" );
                intent.putExtra("fiCode",fiCode );
                intent.putExtra("creator",creator );
                intent.putExtra("allDataAFDataModel",allDataAFDataModel);
                startActivity(intent);
            }
        });

        personalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","personalDetails" );
                intent.putExtra("fiCode",fiCode );
                intent.putExtra("creator",creator );
                intent.putExtra("allDataAFDataModel",allDataAFDataModel);
                startActivity(intent);
            }
        });

        borrowings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","borrowings" );
                intent.putExtra("fiCode",fiCode );
                intent.putExtra("creator",creator );
                intent.putExtra("allDataAFDataModel",allDataAFDataModel);
                startActivity(intent);
            }
        });

        guarantors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","guarantors" );
                intent.putExtra("fiCode",fiCode );
                intent.putExtra("creator",creator );
                intent.putExtra("allDataAFDataModel",allDataAFDataModel);
                startActivity(intent);
            }
        });

        kycScanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiInterface apiInterface1 = ApiClient.getClient().create(ApiInterface.class);
                Call<TargetSetModel> call1 = apiInterface1.checkHVForm(GlobalClass.Token,GlobalClass.dbname,fiCode,creator);

                call1.enqueue(new Callback<TargetSetModel>() {
                    @Override
                    public void onResponse(Call<TargetSetModel> call, Response<TargetSetModel> response) {
                        if(response.isSuccessful()){
                            if(response.body() !=null){
                                if(response.body().getMessage().equals("Record Not Found !!")){
                                    SubmitAlert(ApplicationFormActivityMenu.this, "Note", "Please Fill House Visit Form First ");
                                }else if (!aadhaarCheckBox.isChecked() || !personaldetailCheckBox.isChecked() ||
                                        !financialInfoCheckBox.isChecked() || !familyIncomeCheckBox.isChecked() ||
                                        !borrowingsCheckBox.isChecked() || !guarantorCheckBox.isChecked()) {
                                    SubmitAlert(ApplicationFormActivityMenu.this, "Note", "Please Fill Above Forms First ");

                                }else{
                                    Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                                    intent.putExtra("Id","kycScanning" );
                                    intent.putExtra("fiCode",fiCode );
                                    intent.putExtra("creator",creator );
                                    intent.putExtra("allDataAFDataModel",allDataAFDataModel);
                                    startActivity(intent);
                                  }
                          }else{
                                SubmitAlert(ApplicationFormActivityMenu.this, "Note", "Unable To Open ");
                            }
                        }else{
                            SubmitAlert(ApplicationFormActivityMenu.this, "Note", "Unable To Open ");
                        }
                    }

                    @Override
                    public void onFailure(Call<TargetSetModel> call, Throwable t) {
                        SubmitAlert(ApplicationFormActivityMenu.this, "Error", "Network Error ");

                    }
                });

            }
        });

        financialInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","financialInfo" );
                intent.putExtra("allDataAFDataModel",allDataAFDataModel);
                startActivity(intent);
            }
        });

        familyIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","familyIncome" );
                intent.putExtra("allDataAFDataModel",allDataAFDataModel);
                startActivity(intent);
            }
        });

    }

    private void getFiEditDataFromServer() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllDataAFModel> call= apiInterface.getAllAFData(GlobalClass.Token,GlobalClass.dbname,fiCode,creator);
        Log.d("TAG", "getAllData: " + GlobalClass.Token+GlobalClass.dbname+fiCode+creator);

        call.enqueue(new Callback<AllDataAFModel>() {
            @Override
            public void onResponse(Call<AllDataAFModel> call, Response<AllDataAFModel> response) {
                Log.d("TAG", "getAllData: " + response.body());
                if(response.isSuccessful()){
                    Log.d("TAG", "getAllData: " + "response.body()");

                    AllDataAFModel allDataAFModel = response.body();
                    if (allDataAFModel != null) {
                        allDataAFDataModel = allDataAFModel.getData();

                        if (allDataAFDataModel != null) {

                            if(allDataAFDataModel.getoAdd1().isEmpty()){
                                aadhaarCheckBox.setChecked(false);
                            } else{
                                Log.d("TAG", "onResponse: "+ "address");
                                aadhaarCheckBox.setChecked(true);
                            }
                            if (allDataAFDataModel.getFiExtra() != null &&  !allDataAFDataModel.getFiExtra().getEmaiLID().isEmpty()){
                                personaldetailCheckBox.setChecked(true);
                                Log.d("TAG", "onResponse: "+ allDataAFDataModel.getFiExtra().getEmaiLID().toString());
                            }else {
                                personaldetailCheckBox.setChecked(false);
                            }
                            if (allDataAFDataModel.getBankAcNo().isEmpty()){
                                financialInfoCheckBox.setChecked(false);
                                Log.d("TAG", "onResponse: "+ "financialInfo");
                            }else {
                                financialInfoCheckBox.setChecked(true);
                            }
                            if(allDataAFDataModel.getFiFamMems() !=null && allDataAFDataModel.getFiFamMems().size() >0 && allDataAFDataModel.getFiFamMems().get(0).getMemName() !=null){
                                familyIncomeCheckBox.setChecked(true);
                            }else{
                                familyIncomeCheckBox.setChecked(false);
                            }
                            if(allDataAFDataModel.getFiFamLoans() != null && allDataAFDataModel.getFiFamLoans().size() >0 && allDataAFDataModel.getFiFamLoans().get(0).getLenderName() !=null){
                                borrowingsCheckBox.setChecked(true);
                            }else{
                                borrowingsCheckBox.setChecked(false);
                            }
                            if(allDataAFDataModel.getFiGuarantors() != null && allDataAFDataModel.getFiGuarantors().size() >0 && allDataAFDataModel.getFiGuarantors().get(0).getName() !=null){
                                Log.d("TAG", "onResponsesuny: " + allDataAFDataModel.getFiGuarantors().get(0).getName());
                                guarantorCheckBox.setChecked(true);
                            }else{
                                Log.d("TAG", "onResponsesunyn: " + allDataAFDataModel.getFiGuarantors().size());

                                guarantorCheckBox.setChecked(false);
                            }

                        } else {
                            SubmitAlert(ApplicationFormActivityMenu.this, "Error", "Data Not Found in allDataAFDataModel");

                        }
                    } else {
                        SubmitAlert(ApplicationFormActivityMenu.this, "Error", "Data Not Found in  response.body()");

                    }

                }else {
                    SubmitAlert(ApplicationFormActivityMenu.this, "Error", "Data Not Found");
                    Log.d("TAG", "getAllData: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AllDataAFModel> call, Throwable t) {
                SubmitAlert(ApplicationFormActivityMenu.this, "Error", "Data Not Found");
                Log.d("TAG", "getAllData: " + "failure"+t.getMessage());

            }
        });
    }
}