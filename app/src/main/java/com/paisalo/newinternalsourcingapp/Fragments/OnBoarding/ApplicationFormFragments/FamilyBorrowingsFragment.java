package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.Adapters.RangeCategoryAdapter;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.UpdateFiModels.KycUpdateModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;

import java.util.ArrayList;
import java.util.List;


public class FamilyBorrowingsFragment extends Fragment {

    AllDataAFDataModel allDataAFDataModel;

    Button  addBorrowings,dltButton,canButton;
    DatabaseClass databaseClass;
    private ApplicationFormActivityMenu activity;
    EditText LenderName,etLoanamount,etEmiamount,etbalanceamount;

    Spinner lenderTypespin,spinnerLoanUsed,spinnerReasonforloan,spinnerisMFI;
    FloatingActionButton addBorrower;

    public FamilyBorrowingsFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel = allDataAFDataModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//            activity = (ApplicationFormActivityMenu) getActivity();
//            ArrayList<RangeCategoryDataClass> lenderTypelist = new ArrayList<>();
//        lenderTypelist.add(new RangeCategoryDataClass("--Select--", "--Select--","--Select--","--Select--","--Select--",0,"--Select--"));
//        lenderTypelist.add(new RangeCategoryDataClass("Organised Sector", "Lender Type"," ",""," ",0,""));
//        lenderTypelist.add(new RangeCategoryDataClass("Unorganised Sector", "Lender Type","","","",0,""));

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_family_borrowings,container,false);

        addBorrower = view.findViewById(R.id.FMIncome);

        addBorrowings = view.findViewById(R.id.addBorrowings);
        dltButton = view.findViewById(R.id.deleteBorrowings);
        canButton = view.findViewById(R.id.cancelBorrowings);

        addBorrower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.familyborrowingspopup, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                addBorrowings = popupView.findViewById(R.id.addBorrowings);
                LenderName = popupView.findViewById(R.id.LenderName);
                etLoanamount = popupView.findViewById(R.id.editLoanamount);
                etEmiamount = popupView.findViewById(R.id.editTextEmiamount);
                etbalanceamount = popupView.findViewById(R.id.balanceamount);

                lenderTypespin = popupView.findViewById(R.id.lenderType);
                spinnerLoanUsed = popupView.findViewById(R.id.spinnerOptions2);
                spinnerReasonforloan = popupView.findViewById(R.id.spinnerOptions2);
                spinnerisMFI = popupView.findViewById(R.id.isMFI);
                addBorrowings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        Call<KycUpdateModel> call = apiInterface.updateFamLoans(GlobalClass.Token, GlobalClass.dbname, borrowingsJson());
                        Log.d("TAG", "onResponseAdhaarUpdate: " + GlobalClass.Token + " " + GlobalClass.dbname + " " + borrowingsJson());

                        call.enqueue(new Callback<KycUpdateModel>() {
                            @Override
                            public void onResponse(Call<KycUpdateModel> call, Response<KycUpdateModel> response) {
                                Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                                if (response.isSuccessful()) {
                                    Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                                    Log.d("TAG", "onResponseAdhaarUpdatemsg: " + response.body().getMessage().toString());
                                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("borrowingsCheckBox", true);
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
                /*canButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });*/
            }
        });


//        DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                List<RangeCategoryDataClass> lenderTypeList = new ArrayList<>();
//                RangeCategoryDataClass rangeCategoryDataClass = new RangeCategoryDataClass("--Select--", "--Select--", "--Select--", "--Select--", "--Select--", 0, "99");
//                lenderTypeList.add(rangeCategoryDataClass);
//                lenderTypeList.addAll(databaseClass.dao().getAllRCDataListby_catKey("state"));
//                RangeCategoryAdapter rangeCategoryAdapter = new RangeCategoryAdapter(getActivity(), lenderTypeList);
//                lenderTypespin.setAdapter(rangeCategoryAdapter);
//
//            }
//        });



        return view;    }

    private JsonObject borrowingsJson() {
        JsonObject jsonBorrowings = new JsonObject();
        jsonBorrowings.addProperty("fiCode", allDataAFDataModel.getCode().toString());
        jsonBorrowings.addProperty("creator", allDataAFDataModel.getCreator().toString());
        jsonBorrowings.addProperty("tag", allDataAFDataModel.getTag().toString());
        jsonBorrowings.addProperty("lenderName", "sunny");
        jsonBorrowings.addProperty("lenderType", "self");
        jsonBorrowings.addProperty("loanUsed", "business");
        jsonBorrowings.addProperty("reasonForLoan", "business");
        jsonBorrowings.addProperty("loanAmount", 25000);
        jsonBorrowings.addProperty("emiAmount", 2500);
        jsonBorrowings.addProperty("balanceAmount", 12500);
        jsonBorrowings.addProperty("isMFI", "y");
        jsonBorrowings.addProperty("autoID", 12);

        return jsonBorrowings;
    }
}