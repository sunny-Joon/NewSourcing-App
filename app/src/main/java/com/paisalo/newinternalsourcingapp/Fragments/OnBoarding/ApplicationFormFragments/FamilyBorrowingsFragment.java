package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidName;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.Adapters.RangeCategoryAdapter;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.Modelclasses.FiJsonObject;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryDataModel;
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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DaoClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;

import java.util.ArrayList;
import java.util.List;


public class FamilyBorrowingsFragment extends Fragment {

    List<String> ReasonforloanList = new ArrayList<>();
    List<String> LoanUsedList = new ArrayList<>();
    AllDataAFDataModel allDataAFDataModel;

    Button addBorrowings, dltButton, canButton;
    private ApplicationFormActivityMenu activity;
    EditText etLenderName, etLoanamount, etEmiamount, etbalanceamount;

    String lenderName, reasonForLoan, fiCode, creator, tag, lenderType, loanUsed, loanAmount, emiAmount, balanceAmount, ismfi;

    Spinner lenderTypespin, spinnerLoanUsed, spinnerReasonforloan, spinnerisMFI;
    FloatingActionButton addBorrower;

    public FamilyBorrowingsFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel = allDataAFDataModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        DatabaseClass databaseClass = DatabaseClass.getInstance(getContext());


        View view = inflater.inflate(R.layout.fragment_family_borrowings, container, false);

        addBorrower = view.findViewById(R.id.FMIncome);


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

                fiCode = allDataAFDataModel.getCode().toString();
                creator = allDataAFDataModel.getCreator().toString();
                tag = allDataAFDataModel.getTag().toString();


                dltButton = popupView.findViewById(R.id.deleteBorrowings);
                canButton = popupView.findViewById(R.id.cancelBorrowings);
                addBorrowings = popupView.findViewById(R.id.addBorrowings);

                etLenderName = popupView.findViewById(R.id.LenderName);
                etLoanamount = popupView.findViewById(R.id.editLoanamount);
                etEmiamount = popupView.findViewById(R.id.editTextEmiamount);
                etbalanceamount = popupView.findViewById(R.id.balanceamount);

                lenderTypespin = popupView.findViewById(R.id.lenderType);
                spinnerLoanUsed = popupView.findViewById(R.id.spinnerOptions2);
                spinnerReasonforloan = popupView.findViewById(R.id.spinnerOptions3);
                spinnerisMFI = popupView.findViewById(R.id.isMFI);

                String selectOption = "--Select--";
                ReasonforloanList.add(selectOption);
                LoanUsedList.add(selectOption);
                DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<RangeCategoryDataClass> LoanUsed_DataList = databaseClass.dao().getAllRCDataListby_catKey("loan_purpose");
                        for (RangeCategoryDataClass data : LoanUsed_DataList) {
                            String descriptionEn = data.getDescriptionEn();
                            LoanUsedList.add(descriptionEn);
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, LoanUsedList);
                            spinnerReasonforloan.setAdapter(adapter1);
                        }

                        List<RangeCategoryDataClass> Reasonforloan_DataList = databaseClass.dao().getAllRCDataListby_catKey("relationship");
                        for (RangeCategoryDataClass data : Reasonforloan_DataList) {
                            String descriptionEn = data.getDescriptionEn();
                            ReasonforloanList.add(descriptionEn);
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ReasonforloanList);
                            spinnerLoanUsed.setAdapter(adapter1);
                        }
                    }
                });

                addBorrowings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean allConditionsSatisfied = true;

                        if (!isValidName(etLenderName.getText().toString().isEmpty() ? " " : etLenderName.getText().toString())) {
                            etLenderName.setError("Invalid LenderName");
                            allConditionsSatisfied = false;
                        } else {
                            lenderName = etLenderName.getText().toString();
                        }

                        if (etLoanamount.getText().toString().isEmpty()) {
                            etLoanamount.setError("Invalid Loanamount");
                            allConditionsSatisfied = false;
                        } else {
                            loanAmount = etLoanamount.getText().toString();
                        }

                        if (etEmiamount.getText().toString().isEmpty()) {
                            etEmiamount.setError("Invalid Emiamount");
                            allConditionsSatisfied = false;
                        } else {
                            emiAmount = etEmiamount.getText().toString();
                        }

                        if (etbalanceamount.getText().toString().isEmpty()) {
                            etbalanceamount.setError("Invalid balanceamount");
                            allConditionsSatisfied = false;
                        } else {
                            balanceAmount = etbalanceamount.getText().toString();
                        }

                        if (lenderTypespin.getSelectedItem().toString().contains("-Select-")) {
                            ((TextView) lenderTypespin.getSelectedView()).setError("Please select a lenderType");
                            allConditionsSatisfied = false;
                        } else {
                            lenderType = lenderTypespin.getSelectedItem().toString();
                        }


                        if (spinnerisMFI.getSelectedItem().toString().contains("-Select-")) {
                            ((TextView) spinnerisMFI.getSelectedView()).setError("Please select a isMFI");
                            allConditionsSatisfied = false;
                        } else {
                            ismfi = spinnerisMFI.getSelectedItem().toString();
                        }


                        if (spinnerReasonforloan.getSelectedItem().toString().contains("-Select-")) {
                            ((TextView) spinnerReasonforloan.getSelectedView()).setError("Please select a Reasonforloan");
                            allConditionsSatisfied = false;
                        } else {
                            reasonForLoan = spinnerReasonforloan.getSelectedItem().toString();
                        }

                        if (spinnerLoanUsed.getSelectedItem().toString().contains("-Select-")) {
                            ((TextView) spinnerLoanUsed.getSelectedView()).setError("Please select a LoanUsed");
                            allConditionsSatisfied = false;
                        } else {
                            loanUsed = spinnerLoanUsed.getSelectedItem().toString();
                        }

                        if (allConditionsSatisfied) {

                            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                            Call<KycUpdateModel> call = apiInterface.updateFamLoans(GlobalClass.Token, GlobalClass.dbname, borrowingsJson());
                            Log.d("TAG", "onResponseAdhaarUpdate: " + GlobalClass.Token + " " + GlobalClass.dbname + " " + borrowingsJson());

                            call.enqueue(new Callback<KycUpdateModel>() {
                                @Override
                                public void onResponse(Call<KycUpdateModel> call, Response<KycUpdateModel> response) {
                                    Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                                    if (response.isSuccessful()) {
                                        Log.d("TAG", "onResponseAadhaarUpdate: " + response.body());
                                        Log.d("TAG", "onResponseAadhaarUpdatemsg: " + response.body().getMessage().toString());

                                        SubmitAlert(getActivity(), "Submit", "Form Submit Successfully ");
                                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("borrowingsCheckBox", true);
                                        editor.apply();

                                        Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        SubmitAlert(getActivity(), "Error", "Unsuccessful");

                                        Log.d("TAG", "onResponseAadhaarUpdate: " + response.code());
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

//                            canButton.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    popupWindow.dismiss();
//                                }
//                            });
            }
        });


        return view;
    }

    private JsonObject borrowingsJson() {
        JsonObject jsonBorrowings = new JsonObject();
        jsonBorrowings.addProperty("fiCode", fiCode);
        jsonBorrowings.addProperty("creator", creator);
        jsonBorrowings.addProperty("tag", tag);
        jsonBorrowings.addProperty("lenderName", lenderName);
        jsonBorrowings.addProperty("lenderType", lenderType);
        jsonBorrowings.addProperty("loanUsed", loanUsed);
        jsonBorrowings.addProperty("reasonForLoan", reasonForLoan);
        jsonBorrowings.addProperty("loanAmount", loanAmount);
        jsonBorrowings.addProperty("emiAmount", emiAmount);
        jsonBorrowings.addProperty("balanceAmount", balanceAmount);
        jsonBorrowings.addProperty("isMFI", ismfi);
        jsonBorrowings.addProperty("autoID", 0);

        return jsonBorrowings;
    }
}