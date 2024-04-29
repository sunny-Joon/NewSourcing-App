package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivity;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.Adapters.RangeCategoryAdapter;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.KYCActivity;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;
import com.paisalo.newinternalsourcingapp.Utils.CustomProgressDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AadhaarFragment extends Fragment {

    Button submit;

    CustomProgressDialog customProgressDialog;

    CardView currentAddress;
    CheckBox addressCheckBox;
    AllDataAFDataModel allDataAFDataModel;
    Spinner spin_aadhaarState;
    TextView aadhaarName,aadhaarid,aadhaarAge,aadhaarGendre,aadhaarDOB,aadhaarGuardian,aadhaarmobile,aadhaarPAN,aadhaarDrivingLicense,  aadhaarAddress,aadhaarPincode,aadhaarCity,loanAmount;
    public AadhaarFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel=allDataAFDataModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aadhaar, container, false);

        DatabaseClass databaseClass = DatabaseClass.getInstance(getActivity());
        customProgressDialog = new CustomProgressDialog(getActivity());

        aadhaarName = view.findViewById(R.id.aadhaarName);
        aadhaarid = view.findViewById(R.id.aadhaarid);
        aadhaarAge = view.findViewById(R.id.aadhaarAge);
        aadhaarGendre = view.findViewById(R.id.aadhaarGendre);
        aadhaarDOB = view.findViewById(R.id.aadhaarDOB);
        aadhaarGuardian = view.findViewById(R.id.aadhaarGuardian);
        aadhaarmobile = view.findViewById(R.id.aadhaarmobile);
        aadhaarPAN = view.findViewById(R.id.aadhaarPAN);
        aadhaarDrivingLicense = view.findViewById(R.id.aadhaarDrivingLicense);
        aadhaarAddress = view.findViewById(R.id.aadhaarAddress);
        aadhaarPincode = view.findViewById(R.id.aadhaarPincode);
        aadhaarCity = view.findViewById(R.id.aadhaarCity);
        spin_aadhaarState = view.findViewById(R.id.acspAadharState);
        loanAmount = view.findViewById(R.id.loanAmount);

        submit = view.findViewById(R.id.aadhaarSubmitButton);
        currentAddress = view.findViewById(R.id.currentAddress);
        addressCheckBox = view.findViewById(R.id.addressCheckBox);
        currentAddress.setVisibility(View.GONE);

        addressCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentAddress.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        aadhaarName.setText(allDataAFDataModel.getFname()+" "+allDataAFDataModel.getLname());
        aadhaarid.setText(allDataAFDataModel.getAadharID());
        aadhaarAge.setText(allDataAFDataModel.getAge().toString());
        aadhaarGendre.setText(allDataAFDataModel.getGender());
        aadhaarDOB.setText(allDataAFDataModel.getDob());
        aadhaarGuardian.setText(allDataAFDataModel.getfFname());
        aadhaarmobile.setText(allDataAFDataModel.getpPh1());
        aadhaarDrivingLicense.setText(allDataAFDataModel.getDrivingLic());
        aadhaarAddress.setText(allDataAFDataModel.getpAdd1()+" "+allDataAFDataModel.getpAdd2()+" "+allDataAFDataModel.getpAdd3());
        aadhaarPincode.setText(allDataAFDataModel.getoPin().toString());
        aadhaarCity.setText(allDataAFDataModel.getpCity());
       // aadhaarState.setText(allDataAFDataModel.getpState());
        loanAmount.setText(allDataAFDataModel.getLoanAmt().toString());



        DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<RangeCategoryDataClass> stateDataList = new ArrayList<>();
                RangeCategoryDataClass rangeCategoryDataClass = new RangeCategoryDataClass("--Select--","--Select--","--Select--","--Select--","--Select--",0,"99");
                stateDataList.add(rangeCategoryDataClass);
                stateDataList.addAll(databaseClass.dao().getAllRCDataListby_catKey("state"));
                RangeCategoryAdapter rangeCategoryAdapter = new RangeCategoryAdapter(getActivity(),stateDataList );
                spin_aadhaarState.setAdapter(rangeCategoryAdapter);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("aadhaarCheckBox", true);
                editor.apply();

                Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

}
