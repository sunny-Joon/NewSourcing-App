package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

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
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationFormActivityMenu extends AppCompatActivity {

    CardView aadhaar,personalDetails,borrowings,guarantors,kycScanning,financialInfo,familyIncome;

    CheckBox kycScanningCheckBox, guarantorCheckBox,borrowingsCheckBox, familyIncomeCheckBox,financialInfoCheckBox,personaldetailCheckBox,aadhaarCheckBox;
    AllDataAFDataModel allDataAFDataModel;
    FiExtra fiExtra;
    FiExtraBankBo fiExtraBankBo;
    FiFamExpenses fiFamExpenses;
    List<FiFamLoan> fiFamLoan;
    List<FiFamMem> fiFamMem;
    List<FiGuarantor> fiGuarantor;
    List<UploadedFiDocs> uploadedFiDocs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form_menu);

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

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AllDataAFModel> call= apiInterface.getAllAFData(GlobalClass.Token,GlobalClass.dbname,"250075","HOAGRA");

        call.enqueue(new Callback<AllDataAFModel>() {
            @Override
            public void onResponse(Call<AllDataAFModel> call, Response<AllDataAFModel> response) {
                Log.d("TAG", "getAllData: " + response.body());
                if(response.isSuccessful()){
                    AllDataAFModel allDataAFModel = response.body();
                    allDataAFDataModel = allDataAFModel.getData();
                    fiExtra = allDataAFDataModel.getFiExtra();
                    fiExtraBankBo = allDataAFDataModel.getFiExtraBankBo();
                    fiFamExpenses = allDataAFDataModel.getFiFamExpenses();
                    fiFamLoan = allDataAFDataModel.getFiFamLoans();
                    fiFamMem = allDataAFDataModel.getFiFamMems();
                    fiGuarantor =allDataAFDataModel.getFiGuarantors();
                    uploadedFiDocs =allDataAFDataModel.getUploadedFiDocsList();


                    Log.d("TAG", "getAllData: " + allDataAFDataModel.getAadharID());
                }else {
                    Log.d("TAG", "getAllData: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AllDataAFModel> call, Throwable t) {
                Log.d("TAG", "getAllData: " + "failure");

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!sharedPreferences.getBoolean("aadhaarCheckBox",false))
        {  editor.putBoolean("aadhaarCheckBox", false);
            editor.apply();
        } else{
            aadhaarCheckBox.setChecked(true);
        }
        if (!sharedPreferences.getBoolean("personaldetailCheckBox",false)) {
            editor.putBoolean("personaldetailCheckBox", false);
            editor.apply();
        }else {
            personaldetailCheckBox.setChecked(true);
        }
        if (!sharedPreferences.getBoolean("financialInfoCheckBox",false)) {
            editor.putBoolean("financialInfoCheckBox", false);
            editor.apply();
        }else{
            financialInfoCheckBox.setChecked(true);
        }
        if (!sharedPreferences.getBoolean("familyIncomeCheckBox",false)) {
            editor.putBoolean("familyIncomeCheckBox", false);
            editor.apply();
        }else{
            familyIncomeCheckBox.setChecked(true);
        }
        if (!sharedPreferences.getBoolean("borrowingsCheckBox",false)) {
            editor.putBoolean("borrowingsCheckBox", false);
            editor.apply();
        }else{
            borrowingsCheckBox.setChecked(true);
        }
        if (!sharedPreferences.getBoolean("guarantorCheckBox",false)) {
            editor.putBoolean("guarantorCheckBox", false);
            editor.apply();
        }else{
            guarantorCheckBox.setChecked(true);
        }
        if (!sharedPreferences.getBoolean("kycScanningCheckBox",false)) {
            editor.putBoolean("kycScanningCheckBox", false);
            editor.apply();
        }else{
            kycScanningCheckBox.setChecked(true);
        }

        aadhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","aadhaar" );
                intent.putExtra("allDataAFDataModel",allDataAFDataModel);
                startActivity(intent);
            }
        });

        personalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","personalDetails" );
                intent.putExtra("allDataAFDataModel",allDataAFDataModel);
                startActivity(intent);
            }
        });

        borrowings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","borrowings" );
                intent.putExtra("allDataAFDataModel",allDataAFDataModel);
                startActivity(intent);
            }
        });

        guarantors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","guarantors" );
                intent.putExtra("allDataAFDataModel",allDataAFDataModel);
                startActivity(intent);
            }
        });

        kycScanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","kycScanning" );
                intent.putExtra("allDataAFDataModel",allDataAFDataModel);
                startActivity(intent);
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
}