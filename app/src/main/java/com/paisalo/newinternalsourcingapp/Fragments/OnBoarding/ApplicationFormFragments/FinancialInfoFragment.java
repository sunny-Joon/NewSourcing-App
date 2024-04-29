package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.R;

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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
}