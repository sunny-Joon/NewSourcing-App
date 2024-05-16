package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidName;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiFamLoan;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiFamMem;
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
import android.widget.Toast;

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
        List<FiFamLoan> list = allDataAFDataModel.getFiFamLoans();


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

                List<RangeCategoryDataClass> LoanUsed_DataList = databaseClass.dao().getAllRCDataListby_catKey("loan_purpose");
                for (RangeCategoryDataClass data : LoanUsed_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    LoanUsedList.add(descriptionEn);

                }
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, LoanUsedList);
                spinnerReasonforloan.setAdapter(adapter1);

                List<RangeCategoryDataClass> Reasonforloan_DataList = databaseClass.dao().getAllRCDataListby_catKey("relationship");
                for (RangeCategoryDataClass data : Reasonforloan_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    ReasonforloanList.add(descriptionEn);
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ReasonforloanList);
                spinnerLoanUsed.setAdapter(adapter2);

                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.lenderType, android.R.layout.simple_spinner_item);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lenderTypespin.setAdapter(adapter3);

                ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(getActivity(), R.array.ismfi, android.R.layout.simple_spinner_item);
                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerisMFI.setAdapter(adapter4);


                if (allDataAFDataModel != null) {
                    fiCode = allDataAFDataModel.getCode().toString();
                    creator = allDataAFDataModel.getCreator().toString();
                    tag = allDataAFDataModel.getTag().toString();
                    Log.d("TAG", "onCreateView222: " + fiCode + tag + creator);
                    try {

                        Log.d("TAG", "onClick:1 "+list.get(0).getLenderName());
                        if (!list.isEmpty() && list.get(0).getLenderName() != null) {
                            etLenderName.setText(list.get(0).getLenderName());
                        }

                        Log.d("TAG", "onClick:2 "+list.get(0).getLoanAmount());
                        if (!list.isEmpty() && list.get(0).getLoanAmount() != 0) {
                            etLoanamount.setText(list.get(0).getLoanAmount());
                        }

                        Log.d("TAG", "onClick:3 "+list.get(0).getLoanEMIAmount());
                        if (!list.isEmpty() && list.get(0).getLoanEMIAmount() != 0) {
                            etEmiamount.setText(list.get(0).getLoanEMIAmount());
                        }

                        Log.d("TAG", "onClick:4 "+list.get(0).getLoanBalanceAmount());
                        if (!list.isEmpty() && list.get(0).getLoanBalanceAmount() != 0) {
                            etbalanceamount.setText(list.get(0).getLoanBalanceAmount());
                        }

                        Log.d("TAG", "onClick:4 "+list.get(0).getLoanReason());
                        if (!list.isEmpty() && list.get(0).getLoanReason() != null) {
                            int Reasonforloan = adapter2.getPosition(list.get(0).getLoanReason());
                            spinnerReasonforloan.setSelection(Reasonforloan);
                        }

                        Log.d("TAG", "onClick:5 "+list.get(0).getLenderType());
                        if (!list.isEmpty() && list.get(0).getLenderType() != null) {
                            int Reasonforloan = adapter3.getPosition(list.get(0).getLenderType());
                            lenderTypespin.setSelection(Reasonforloan);
                        }

                        Log.d("TAG", "onClick:6 "+list.get(0).getIsMFI());
                        if (!list.isEmpty() && list.get(0).getIsMFI() != null) {
                            int Reasonforloan = adapter4.getPosition(list.get(0).getIsMFI());
                            spinnerisMFI.setSelection(Reasonforloan);
                        }

                        Log.d("TAG", "onClick:7 "+allDataAFDataModel.getRelationWBorrower());
                        if (allDataAFDataModel.getRelationWBorrower() != null) {
                            int LoanUsed = adapter1.getPosition(allDataAFDataModel.getRelationWBorrower());
                            spinnerLoanUsed.setSelection(LoanUsed);

                        }

                    } catch (Exception exception) {
                        Toast.makeText(getContext(), "fifamloans is null here", Toast.LENGTH_SHORT).show();
                    }
                }

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
                                        Log.d("TAG", "onResponseAadhaarUpdate121: " + response.body());
                                        Log.d("TAG", "onResponseAadhaarUpdatemsg121: " + response.body().getMessage().toString());

                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setMessage("Data submitted successfully.")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                                                        startActivity(intent);
                                                        getActivity().finish();
                                                    }
                                                });
                                        builder.create().show();
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