package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import static android.content.Intent.getIntent;
import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidAddr;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidName;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
import android.widget.Spinner;
import com.paisalo.newinternalsourcingapp.Adapters.RangeCategoryAdapter;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;
import com.paisalo.newinternalsourcingapp.Utils.CustomProgressDialog;
import java.util.ArrayList;
import java.util.List;

public class AadhaarFragment extends Fragment {

    Button submit;
    CardView currentAddress;
    CheckBox addressCheckBox;
    AllDataAFDataModel allDataAFDataModel;
    TextView aadhaarName,aadhaarid,aadhaarAge,aadhaarGendre,aadhaarDOB,aadhaarGuardian,aadhaarmobile,aadhaarPAN,aadhaarDrivingLicense,  aadhaarAddress,aadhaarPincode,aadhaarCity,aadhaarState,loanAmount;
    EditText address1ET,address2ET,address3ET,cityET,pinCodeET;
    String address1,address2,address3,state,city;

    CustomProgressDialog customProgressDialog;

    RangeCategoryAdapter rangeCategoryAdapter;
    Spinner spin_aadhaarState;

    public AadhaarFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel=allDataAFDataModel;
    }

    public AadhaarFragment() {
    }

    @SuppressLint("SetTextI18n")
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
        aadhaarState = view.findViewById(R.id.aadhaarState);

        currentAddress = view.findViewById(R.id.currentAddress);
        addressCheckBox = view.findViewById(R.id.addressCheckBox);
     //   currentAddress.setVisibility(View.GONE);
        address1ET = view.findViewById(R.id.Address1);
        address2ET = view.findViewById(R.id.Address2);
        address3ET = view.findViewById(R.id.Address3);
        cityET = view.findViewById(R.id.city);
        pinCodeET = view.findViewById(R.id.pinCode);

        submit = view.findViewById(R.id.aadhaarSubmitButton);
        spin_aadhaarState = view.findViewById(R.id.acspAadharState);
        loanAmount = view.findViewById(R.id.loanAmount);

        submit = view.findViewById(R.id.aadhaarSubmitButton);
        currentAddress = view.findViewById(R.id.currentAddress);
        addressCheckBox = view.findViewById(R.id.addressCheckBox);
        //currentAddress.setVisibility(View.GONE);

        addressCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //currentAddress.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            // Assume allDataAFDataModel is the object and address1ET, address2ET, etc. are the EditText widgets

            if (allDataAFDataModel != null) {
                if (allDataAFDataModel.getpAdd1() != null) {
                    address1ET.setText(allDataAFDataModel.getpAdd1());
                } else {
                    address1ET.setText(""); // or set a default text
                }

                if (allDataAFDataModel.getpAdd2() != null) {
                    address2ET.setText(allDataAFDataModel.getpAdd2());
                } else {
                    address2ET.setText("");
                }

                if (allDataAFDataModel.getpAdd3() != null) {
                    address3ET.setText(allDataAFDataModel.getpAdd3());
                } else {
                    address3ET.setText("");
                }

                if (allDataAFDataModel.getpPin() != null) {
                    pinCodeET.setText(String.valueOf(allDataAFDataModel.getpPin()));
                } else {
                    pinCodeET.setText("");
                }

                if (allDataAFDataModel.getpCity() != null) {
                    cityET.setText(allDataAFDataModel.getpCity());
                } else {
                    cityET.setText("");
                }
            }

        });


                List<RangeCategoryDataClass> stateDataList = new ArrayList<>();
                RangeCategoryDataClass rangeCategoryDataClass = new RangeCategoryDataClass("--Select--","--Select--","--Select--","--Select--","--Select--",0,"99");
                stateDataList.add(rangeCategoryDataClass);
                stateDataList.addAll(databaseClass.dao().getAllRCDataListby_catKey("state"));
                rangeCategoryAdapter = new RangeCategoryAdapter(getActivity(),stateDataList );
                spin_aadhaarState.setAdapter(rangeCategoryAdapter);

       if (allDataAFDataModel.getpState() != null) {
            int castePos3=-1;
           for (int i=0;i<rangeCategoryAdapter.getCount();i++){
               if (rangeCategoryAdapter.getItem(i).code.equals(databaseClass.dao().getStateByCode("state",allDataAFDataModel.getpState()).code)){
                   castePos3=i;
                   break;
               }
           }

           Log.d("TAG", "onCreateView: "+castePos3);
            spin_aadhaarState.setSelection(castePos3);
      }

        Log.d("TAG", "onCreateViewadhaar: "+"check data");
        if (allDataAFDataModel!=null) {
            Log.d("TAG", "onCreateViewadhaar: "+"confirm data");

            aadhaarName.setText(allDataAFDataModel.getFname() + " " + allDataAFDataModel.getLname());
            aadhaarid.setText(allDataAFDataModel.getAadharID());
            aadhaarAge.setText(allDataAFDataModel.getAge().toString());
            aadhaarGendre.setText(allDataAFDataModel.getGender());
            aadhaarDOB.setText(allDataAFDataModel.getDob());
            aadhaarGuardian.setText(allDataAFDataModel.getfFname());
            aadhaarmobile.setText(allDataAFDataModel.getpPh3());
            aadhaarPAN.setText(allDataAFDataModel.getPanNO());
            aadhaarDrivingLicense.setText(allDataAFDataModel.getDrivingLic());
            aadhaarAddress.setText(allDataAFDataModel.getpAdd1() + " " + allDataAFDataModel.getpAdd2() + " " + allDataAFDataModel.getpAdd3());
            aadhaarPincode.setText(allDataAFDataModel.getpPin().toString());
            aadhaarCity.setText(allDataAFDataModel.getpCity());
            aadhaarState.setText(allDataAFDataModel.getpState());
            loanAmount.setText(allDataAFDataModel.getLoanAmt().toString());
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  boolean allConditionsSatisfied = true;

                if(!isValidAddr(address1ET.getText().toString().isEmpty() ? "" : address1ET.getText().toString())){
                    address1ET.setError("Invalid Address");
                    allConditionsSatisfied = false;
                }else{
                    address1 = address1ET.getText().toString();
                }

                if(!isValidAddr(address2ET.getText().toString().isEmpty() ? "" : address2ET.getText().toString())){
                    address2ET.setError("Invalid Address");
                    allConditionsSatisfied = false;
                }else{
                    address2 = address2ET.getText().toString();
                }

                if(!isValidAddr(address3ET.getText().toString().isEmpty() ? "" : address3ET.getText().toString())){
                    address3ET.setError("Invalid Address");
                    allConditionsSatisfied = false;
                }else{
                    address3 = address3ET.getText().toString();
                }

                if (spin_aadhaarState.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) spin_aadhaarState.getSelectedView()).setError("Please select a state");
                    allConditionsSatisfied = false;
                }else{
                    state = ((RangeCategoryDataClass)spin_aadhaarState.getSelectedItem()).getCode();
                }

                if(!isValidName(cityET.getText().toString().isEmpty() ?" ": cityET.getText().toString())){
                    cityET.setError("Invalid City");
                    allConditionsSatisfied = false;
                }else{
                    city = cityET.getText().toString();
                }

             if (allConditionsSatisfied) {


                 ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                 Call<KycUpdateModel> call = apiInterface.updateAddress(GlobalClass.Token, GlobalClass.dbname, addressJson());
                 Log.d("TAG", "onResponseAdhaarUpdate: " + GlobalClass.Token + " " + GlobalClass.dbname + " " + addressJson());

                 call.enqueue(new Callback<KycUpdateModel>() {
                     @Override
                     public void onResponse(Call<KycUpdateModel> call, Response<KycUpdateModel> response) {
                         Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                         if (response.isSuccessful()) {
                             Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                             Log.d("TAG", "onResponseAdhaarUpdatemsg: " + response.body().getMessage().toString());
                             SubmitAlert(getActivity(), "success", "Data set Successfully");


                         } else {
                             Log.d("TAG", "onResponseAdhaarUpdate: " + response.code());
                             SubmitAlert(getActivity(), "unsuccessful", "Check Your Internet Connection");

                         }
                     }

                     @Override
                     public void onFailure(Call<KycUpdateModel> call, Throwable t) {
                         Log.d("TAG", "onResponseAdhaarUpdate: " + "failure");
                         SubmitAlert(getActivity(), "Network Error", "Check Your Internet Connection");

                     }
                 });
             }

            }
        });
        return view;
    }

    private JsonObject addressJson() {
        JsonObject jsonAddress = new JsonObject();
        jsonAddress.addProperty("fiCode", allDataAFDataModel.getCode().toString());
        jsonAddress.addProperty("creator", allDataAFDataModel.getCreator().toString());
        jsonAddress.addProperty("tag", allDataAFDataModel.getTag().toString());
        jsonAddress.addProperty("o_Add1",address1 );
        jsonAddress.addProperty("o_Add2", address2);
        jsonAddress.addProperty("o_Add3", address3);
        jsonAddress.addProperty("o_State", state);
        jsonAddress.addProperty("o_City", city);
        jsonAddress.addProperty("o_Pin", pinCodeET.getText().toString());
        return jsonAddress;
    }
}
