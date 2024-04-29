package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.UpdateFiModels.KycUpdateModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinancialInfoFragment extends Fragment {

    Spinner accountTypeSpinner,accOpenDateSpinner,houseTypeSpinner,roofTypeSpinner,personalToiletSpinner;
    EditText bankAccountNumber,bankIFSCCode,rentalIncome,expenseInRent,expenseForFood,expenseForEducation,expenseForHealth,expenseForTravelling,expenseForEntertainment,anyOtherExpense;
    Button submit;
    AllDataAFDataModel allDataAFDataModel;
    public FinancialInfoFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel=allDataAFDataModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_financial_info, container, false);


        bankAccountNumber = view.findViewById(R.id.bankAccountNumber);
        bankIFSCCode = view.findViewById(R.id.bankIFSCCode);
        rentalIncome = view.findViewById(R.id.rentalIncome);
        expenseInRent = view.findViewById(R.id.expenseInRent);
        expenseForFood = view.findViewById(R.id.expenseForFood);
        expenseForEducation = view.findViewById(R.id.expenseForEducation);
        expenseForHealth = view.findViewById(R.id.expenseForHealth);
        expenseForTravelling = view.findViewById(R.id.expenseForTravelling);
        expenseForEntertainment = view.findViewById(R.id.expenseForEntertainment);
        anyOtherExpense = view.findViewById(R.id.anyOtherExpense);

        accountTypeSpinner = view.findViewById(R.id.bankAccountType);
        accOpenDateSpinner = view.findViewById(R.id.accountOpeningDate);
        houseTypeSpinner = view.findViewById(R.id.houseType);
        roofTypeSpinner = view.findViewById(R.id.roofType);
        personalToiletSpinner = view.findViewById(R.id.personalToilet);
        submit = view.findViewById(R.id.submitfinancialInfo);

       /* bankAccountNumber.setText(allDataAFDataModel.getBankAcNo());
        bankIFSCCode.setText(allDataAFDataModel.getBankAcNo());
        rentalIncome.setText(allDataAFDataModel.getBankAcNo());
        expenseInRent.setText(allDataAFDataModel.getBankAcNo());
        expenseForFood.setText(allDataAFDataModel.getBankAcNo());
        expenseForEducation.setText(allDataAFDataModel.getBankAcNo());
        expenseForHealth.setText(allDataAFDataModel.getBankAcNo());
        expenseForTravelling.setText(allDataAFDataModel.getBankAcNo());
        expenseForEntertainment.setText(allDataAFDataModel.getBankAcNo());
        anyOtherExpense.setText(allDataAFDataModel.getBankAcNo());
        bankAccountNumber.setText(allDataAFDataModel.getBankAcNo().toString());
       // bankIFSCCode.setText(allDataAFDataModel.getFiExtra().);
        rentalIncome.setText(allDataAFDataModel.getFiExtra().getRentalIncome().toString());
        expenseInRent.setText(allDataAFDataModel.getRentOfHouse());
        expenseForFood.setText(allDataAFDataModel.getFiFamExpenses().getFooding());
        expenseForEducation.setText(allDataAFDataModel.getFiFamExpenses().getEducation());
        expenseForHealth.setText(allDataAFDataModel.getFiFamExpenses().getHealth());
        expenseForTravelling.setText(allDataAFDataModel.getFiFamExpenses().getTravelling());
        expenseForEntertainment.setText(allDataAFDataModel.getFiFamExpenses().getEntertainment());
        anyOtherExpense.setText(allDataAFDataModel.getFiFamExpenses().getOthers());*/


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<KycUpdateModel> call= apiInterface.updateFinance(GlobalClass.Token,GlobalClass.dbname, financeJson());
                Log.d("TAG", "onResponseAdhaarUpdate: " + GlobalClass.Token+" "+GlobalClass.dbname+" "+ financeJson());

                call.enqueue(new Callback<KycUpdateModel>() {
                    @Override
                    public void onResponse(Call<KycUpdateModel> call, Response<KycUpdateModel> response) {
                        Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                        if(response.isSuccessful()){
                            Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                            Log.d("TAG", "onResponseAdhaarUpdatemsg: " + response.body().getMessage().toString());
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("financialInfoCheckBox", true);
                            editor.apply();

                            Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else{
                            Log.d("TAG", "onResponseAdhaarUpdate: " + response.code());

                        }
                    }

                    @Override
                    public void onFailure(Call<KycUpdateModel> call, Throwable t) {
                        Log.d("TAG", "onResponseAdhaarUpdate: " + "failure");

                    }
                });
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("financialInfoCheckBox", true);
                editor.apply();

                Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private JsonObject financeJson() {
        JsonObject jsonFinance = new JsonObject();
        jsonFinance.addProperty("fiCode", allDataAFDataModel.getCode().toString());
        jsonFinance.addProperty("creator", allDataAFDataModel.getCreator().toString());
        jsonFinance.addProperty("tag", allDataAFDataModel.getTag().toString());
        jsonFinance.addProperty("bankAccountType","as" );
        jsonFinance.addProperty("bankAccNumber", "address2");
        jsonFinance.addProperty("accOpeningDate", "2024-04-22");
        jsonFinance.addProperty("ifscCode", "state");
        jsonFinance.addProperty("rentalIncome", 5000);
        jsonFinance.addProperty("expenseInRent", 5000);
        jsonFinance.addProperty("expenseForFood", 4000);
        jsonFinance.addProperty("expenseForEducation", 5000);
        jsonFinance.addProperty("expenseForHealth", 5000);
        jsonFinance.addProperty("expenseForTravelling", 5000);
        jsonFinance.addProperty("expenseForEntertainment", 5000);
        jsonFinance.addProperty("otherExpense", 5000);
        jsonFinance.addProperty("houseType", "pucca");
        jsonFinance.addProperty("roofType", "pucca");
        jsonFinance.addProperty("personalToilet", "Y");
        jsonFinance.addProperty("livingWSpouse", "Y");
        return jsonFinance;
    }
}