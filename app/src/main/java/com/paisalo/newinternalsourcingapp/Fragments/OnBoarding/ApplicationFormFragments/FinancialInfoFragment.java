package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.UpdateFiModels.KycUpdateModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinancialInfoFragment extends Fragment {

    Spinner accountTypeSpinner,houseTypeSpinner,roofTypeSpinner,personalToiletSpinner;
    EditText bankAccountNumber,bankIFSCCode,rentalIncome,expenseInRent,expenseForFood,expenseForEducation,expenseForHealth,
            expenseForTravelling,expenseForEntertainment,anyOtherExpense,etaccount_date;
    Button submit;
    List<String> houseType_List = new ArrayList<>();
    List<String> roofType_List = new ArrayList<>();
    List<String> personalToilet_List = new ArrayList<>();
    List<String> accountType_List = new ArrayList<>();
    ImageView calender_icon;

    String fiCode,creator,tag,bankAccountType,bankAccNumber,accOpeningDate,ifscCode,rentalincome,expenseinRent
             ,expenseforFood,expenseforEducation,expenseforHealth,expenseforTravelling,expenseforEntertainment
            ,otherexpense,houseType,roofType,personalToilet;

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

        DatabaseClass databaseClass = DatabaseClass.getInstance(getContext());

        fiCode =  allDataAFDataModel.getCode().toString();
        creator =  allDataAFDataModel.getCreator().toString();
        tag =  allDataAFDataModel.getTag().toString();


        bankAccountNumber = view.findViewById(R.id.bankAccountNumber);
        etaccount_date = view.findViewById(R.id.account_date);
        bankIFSCCode = view.findViewById(R.id.bankIFSCCode);
        rentalIncome = view.findViewById(R.id.rentalIncome);
        expenseInRent = view.findViewById(R.id.expenseInRent);
        expenseForFood = view.findViewById(R.id.expenseForFood);
        expenseForEducation = view.findViewById(R.id.expenseForEducation);
        expenseForHealth = view.findViewById(R.id.expenseForHealth);
        expenseForTravelling = view.findViewById(R.id.expenseForTravelling);
        expenseForEntertainment = view.findViewById(R.id.expenseForEntertainment);
        anyOtherExpense = view.findViewById(R.id.anyOtherExpense);

        calender_icon = view.findViewById(R.id.ac_date_calender);


        accountTypeSpinner = view.findViewById(R.id.bankAccountType);

        houseTypeSpinner = view.findViewById(R.id.houseType);
        roofTypeSpinner = view.findViewById(R.id.roofType);
        personalToiletSpinner = view.findViewById(R.id.personalToilet);
        submit = view.findViewById(R.id.submitfinancialInfo);

        String selectOption = "--Select--";
        houseType_List.add(selectOption);
        roofType_List.add(selectOption);
        personalToilet_List.add(selectOption);
        accountType_List.add(selectOption);


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
        anyOtherExpense.setText(allDataAFDataModel.getFiFamExpenses().getOthers());
        etaccount_date.setText(allDataAFDataModel.getBankAcOpenDt().toString());*/



        DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                List<RangeCategoryDataClass> houseType_DataList = databaseClass.dao().getAllRCDataListby_catKey("house-type");
                for (RangeCategoryDataClass data : houseType_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    houseType_List.add(descriptionEn);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, houseType_List);
                    houseTypeSpinner.setAdapter(adapter1);
                }

                List<RangeCategoryDataClass> roofType_DataList = databaseClass.dao().getAllRCDataListby_catKey("house-roof-type");
                for (RangeCategoryDataClass data : roofType_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    roofType_List.add(descriptionEn);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, roofType_List);
                    roofTypeSpinner.setAdapter(adapter1);
                }

                List<RangeCategoryDataClass> personalToilet_DataList = databaseClass.dao().getAllRCDataListby_catKey("toilet-type");
                for (RangeCategoryDataClass data : personalToilet_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    personalToilet_List.add(descriptionEn);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, personalToilet_List);
                    personalToiletSpinner.setAdapter(adapter1);
                }
                List<RangeCategoryDataClass> accountType_DataList = databaseClass.dao().getAllRCDataListby_catKey("bank_ac_type");
                for (RangeCategoryDataClass data : accountType_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    accountType_List.add(descriptionEn);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, accountType_List);
                    accountTypeSpinner.setAdapter(adapter1);
                }


    }
});


        calender_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }

            private void showDatePickerDialog() {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        etaccount_date.setText(selectedDate);
                       // progressBar.incrementProgressBy(1);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean allConditionsSatisfied=true;

                if (accountTypeSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) accountTypeSpinner.getSelectedView()).setError("Please select a accountType");
                    allConditionsSatisfied = false;
                } else {
                    bankAccountType= accountTypeSpinner.getSelectedItem().toString();
                }


                if (bankAccountNumber.getText().toString().isEmpty()) {
                    bankAccountNumber.setError("Invalid bankAccount");
                    allConditionsSatisfied = false;
                } else {
                    bankAccNumber = bankAccountNumber.getText().toString();
                }

                if(etaccount_date.getText().toString().isEmpty()){
                    etaccount_date.setError("Select Date");
                    allConditionsSatisfied = false;
                }else{
                    accOpeningDate = etaccount_date.getText().toString();
                }


                if (bankIFSCCode.getText().toString().isEmpty()) {
                    bankIFSCCode.setError("Invalid bankIFSCCode");
                    allConditionsSatisfied = false;
                } else {
                    ifscCode = bankIFSCCode.getText().toString();
                }


                if (rentalIncome.getText().toString().isEmpty()) {
                    rentalIncome.setError("Invalid rentalIncome");
                    allConditionsSatisfied = false;
                } else {
                    rentalincome = rentalIncome.getText().toString();
                }


                if (expenseInRent.getText().toString().isEmpty()) {
                    expenseInRent.setError("Invalid expenseInRent");
                    allConditionsSatisfied = false;
                } else {
                    expenseinRent = expenseInRent.getText().toString();
                }


                if (expenseForFood.getText().toString().isEmpty()) {
                    expenseForFood.setError("Invalid expenseForFood");
                    allConditionsSatisfied = false;
                } else {
                    expenseforFood = expenseForFood.getText().toString();
                }


                if (expenseForEducation.getText().toString().isEmpty()) {
                    expenseForEducation.setError("Invalid expenseForEducation");
                    allConditionsSatisfied = false;
                } else {
                    expenseforEducation = expenseForEducation.getText().toString();
                }


                if (expenseForHealth.getText().toString().isEmpty()) {
                    expenseForHealth.setError("Invalid expenseForHealth");
                    allConditionsSatisfied = false;
                } else {
                    expenseforHealth = expenseForHealth.getText().toString();
                }


                if (expenseForTravelling.getText().toString().isEmpty()) {
                    expenseForTravelling.setError("Invalid expenseForTravelling");
                    allConditionsSatisfied = false;
                } else {
                    expenseforTravelling = expenseForTravelling.getText().toString();
                }


                if (expenseForEntertainment.getText().toString().isEmpty()) {
                    expenseForEntertainment.setError("Invalid expenseForEntertainment");
                    allConditionsSatisfied = false;
                } else {
                    expenseforEntertainment = expenseForEntertainment.getText().toString();
                }


                if (anyOtherExpense.getText().toString().isEmpty()) {
                    anyOtherExpense.setError("Invalid anyOtherExpense");
                    allConditionsSatisfied = false;
                } else {
                    otherexpense = anyOtherExpense.getText().toString();
                }

                if (houseTypeSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) houseTypeSpinner.getSelectedView()).setError("Please select a houseType");
                    allConditionsSatisfied = false;
                } else {
                    houseType= houseTypeSpinner.getSelectedItem().toString();
                }

                if (roofTypeSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) roofTypeSpinner.getSelectedView()).setError("Please select a roofType");
                    allConditionsSatisfied = false;
                } else {
                    roofType= roofTypeSpinner.getSelectedItem().toString();
                }

                if (personalToiletSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) personalToiletSpinner.getSelectedView()).setError("Please select a personalToilet");
                    allConditionsSatisfied = false;
                } else {
                    personalToilet= personalToiletSpinner.getSelectedItem().toString();
                }

                if (allConditionsSatisfied) {
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<KycUpdateModel> call = apiInterface.updateFinance(GlobalClass.Token, GlobalClass.dbname, financeJson());
                    Log.d("TAG", "onResponseAdhaarUpdate: " + GlobalClass.Token + " " + GlobalClass.dbname + " " + financeJson());

                    call.enqueue(new Callback<KycUpdateModel>() {
                        @Override
                        public void onResponse(Call<KycUpdateModel> call, Response<KycUpdateModel> response) {
                            Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                            if (response.isSuccessful()) {
                                Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                                //  Log.d("TAG", "onResponseAdhaarUpdatemsg: " + response.body().getMessage().toString());
                                SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("financialInfoCheckBox", true);
                                editor.apply();

                                Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
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
            }
        });

        return view;
    }

    private JsonObject financeJson() {
        JsonObject jsonFinance = new JsonObject();
        jsonFinance.addProperty("fiCode",fiCode);
        jsonFinance.addProperty("creator", creator);
        jsonFinance.addProperty("tag", tag);
        jsonFinance.addProperty("bankAccountType",bankAccountType );
        jsonFinance.addProperty("bankAccNumber", bankAccNumber);
        jsonFinance.addProperty("accOpeningDate", accOpeningDate);
        jsonFinance.addProperty("ifscCode", ifscCode);
        jsonFinance.addProperty("rentalIncome", rentalincome);
        jsonFinance.addProperty("expenseInRent", expenseinRent);
        jsonFinance.addProperty("expenseForFood", expenseforFood);
        jsonFinance.addProperty("expenseForEducation", expenseforEducation);
        jsonFinance.addProperty("expenseForHealth", expenseforHealth);
        jsonFinance.addProperty("expenseForTravelling", expenseforTravelling);
        jsonFinance.addProperty("expenseForEntertainment", expenseforEntertainment);
        jsonFinance.addProperty("otherExpense", otherexpense);
        jsonFinance.addProperty("houseType", houseType);
        jsonFinance.addProperty("roofType", roofType);
        jsonFinance.addProperty("personalToilet", personalToilet);
        jsonFinance.addProperty("livingWSpouse", "Y");
        return jsonFinance;
    }
}