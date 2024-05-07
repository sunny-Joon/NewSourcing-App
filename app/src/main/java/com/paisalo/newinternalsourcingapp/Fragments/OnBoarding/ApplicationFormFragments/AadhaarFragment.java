package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

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
    EditText address1ET,address2ET,address3ET,stateET,cityET,pinCodeET;
    String address1,address2,address3,state,city;
    int pinCode;

    CustomProgressDialog customProgressDialog;


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
        loanAmount = view.findViewById(R.id.loanAmount);

        currentAddress = view.findViewById(R.id.currentAddress);
        addressCheckBox = view.findViewById(R.id.addressCheckBox);
        currentAddress.setVisibility(View.GONE);
        address1ET = view.findViewById(R.id.Address1);
        address2ET = view.findViewById(R.id.Address2);
        address3ET = view.findViewById(R.id.Address3);
        address1ET = view.findViewById(R.id.pinCode);
        cityET = view.findViewById(R.id.city);
        pinCodeET = view.findViewById(R.id.pinCode);

        submit = view.findViewById(R.id.aadhaarSubmitButton);
        spin_aadhaarState = view.findViewById(R.id.acspAadharState);
        loanAmount = view.findViewById(R.id.loanAmount);

        submit = view.findViewById(R.id.aadhaarSubmitButton);
        currentAddress = view.findViewById(R.id.currentAddress);
        addressCheckBox = view.findViewById(R.id.addressCheckBox);
        currentAddress.setVisibility(View.GONE);

        addressCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentAddress.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

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

       /* aadhaarName.setText(allDataAFDataModel.getFname() + " " + allDataAFDataModel.getLname());
        aadhaarid.setText(allDataAFDataModel.getAadharID());
        aadhaarAge.setText(allDataAFDataModel.getAge().toString());
        aadhaarGendre.setText(allDataAFDataModel.getGender());
        aadhaarDOB.setText(allDataAFDataModel.getDob());
        aadhaarGuardian.setText(allDataAFDataModel.getfFname());
        aadhaarmobile.setText(allDataAFDataModel.getpPh1());
        aadhaarDrivingLicense.setText(allDataAFDataModel.getDrivingLic());
        aadhaarAddress.setText(allDataAFDataModel.getpAdd1() + " " + allDataAFDataModel.getpAdd2() + " " + allDataAFDataModel.getpAdd3());
        aadhaarPincode.setText(allDataAFDataModel.getoPin().toString());
        aadhaarCity.setText(allDataAFDataModel.getpCity());
        aadhaarState.setText(allDataAFDataModel.getpState());
        loanAmount.setText(allDataAFDataModel.getLoanAmt().toString());*/



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address1 = address1ET.getText().toString();
                address2 = address2ET.getText().toString();
                address3 = address3ET.getText().toString();
                state = stateET.getText().toString();
                city = cityET.getText().toString();
                pinCode = Integer.parseInt(pinCodeET.getText().toString());

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
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("aadhaarCheckBox", true);
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
        jsonAddress.addProperty("o_Pin", pinCode);
        return jsonAddress;
    }
}
