package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidName;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.Adapters.CustomSpinnerAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.FaimlyMemberIncomeAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.FamilyBorrowingsAdapter;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiFamMem;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.UpdateFiModels.KycUpdateModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;
import com.paisalo.newinternalsourcingapp.Utils.CustomProgress;
import com.paisalo.newinternalsourcingapp.Utils.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class FamilyMembersIncomeFragment extends Fragment {

    FaimlyMemberIncomeAdapter adapter;
    List<FiFamMem> memberincomelist;
    RecyclerView recyclerView;
    Button addBtn, delBtn, canBtn, addmemberincome;
    EditText faimlMemberName, etage, etBusiness, etTextincome;
    List<String> relationship_List = new ArrayList<>();
    List<String> gender_List = new ArrayList<>();
    List<String> Health_List = new ArrayList<>();
    List<String> Education_List = new ArrayList<>();
    List<String> schoolType_List = new ArrayList<>();
    List<String> businessType_List = new ArrayList<>();
    List<String> incomeType_List = new ArrayList<>();
    List<FiFamMem> list;

    String fiCode, creator, tag, famMemName, relationship, age, gender, health, education, schoolType, business, businessType, income, incomeType;

    Spinner relationship_spin, gender_spin, Health_spin, Education_spin, schoolType_spin, businessType_spin, incomeType_spin;
    FloatingActionButton FMIncomeButton;

    AllDataAFDataModel allDataAFDataModel;
    List<RangeCategoryDataClass> relationship_DataList, incomeType_DataList, businessType_DataList, schoolType_DataList, Education_DataList, Health_DataList, gender_DataList;

    CustomSpinnerAdapter adapter1, adapter2, adapter3, adapter4, adapter5, adapter6, adapter7;

    public FamilyMembersIncomeFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel = allDataAFDataModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_members_income, container, false);


        DatabaseClass databaseClass = DatabaseClass.getInstance(getContext());
        memberincomelist = new ArrayList<>();

        recyclerView = view.findViewById(R.id.memberincome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FMIncomeButton = view.findViewById(R.id.addButton);
        addmemberincome = view.findViewById(R.id.addmemberincome);

        if (allDataAFDataModel != null) {
            if (allDataAFDataModel.getFiFamMems().size() > 0) {
                Log.d("TAG", "allDataAFDataModel: " + " list.size()");

                list = allDataAFDataModel.getFiFamMems();
                Log.d("TAG", "allDataAFDataModel: " + list.size());

                adapter = new FaimlyMemberIncomeAdapter(getActivity(), list);
                recyclerView.setAdapter(adapter);

                addmemberincome.setVisibility(View.GONE);
                FMIncomeButton.setVisibility(View.GONE);
            }
        }

        FMIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                relationship_List.clear();
                      gender_List.clear();
                      Health_List.clear();
                   Education_List.clear();
                  schoolType_List.clear();
                businessType_List.clear();
                  incomeType_List.clear();

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.familymemberincomepopup, null);


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setView(popupView);
                dialogBuilder.setCancelable(false);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                addBtn = popupView.findViewById(R.id.addupdateButton);
                delBtn = popupView.findViewById(R.id.deleteButton);
                canBtn = popupView.findViewById(R.id.cancelButton);


                faimlMemberName = popupView.findViewById(R.id.faimlMemberName);
                etage = popupView.findViewById(R.id.age);
                etBusiness = popupView.findViewById(R.id.Business);
                etTextincome = popupView.findViewById(R.id.editTextincome);


                relationship_spin = popupView.findViewById(R.id.relationship);
                gender_spin = popupView.findViewById(R.id.gender);
                Health_spin = popupView.findViewById(R.id.Health);
                Education_spin = popupView.findViewById(R.id.Education);
                schoolType_spin = popupView.findViewById(R.id.schoolType);
                businessType_spin = popupView.findViewById(R.id.businessType);
                incomeType_spin = popupView.findViewById(R.id.incomeType);

                addBtn = popupView.findViewById(R.id.addupdateButton);

                String selectOption = "--Select--";
                relationship_List.add(selectOption);
                gender_List.add(selectOption);
                Health_List.add(selectOption);
                Education_List.add(selectOption);
                schoolType_List.add(selectOption);
                businessType_List.add(selectOption);
                incomeType_List.add(selectOption);


                relationship_DataList = databaseClass.dao().getAllRCDataListby_catKey("relationship");
                for (RangeCategoryDataClass data : relationship_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    relationship_List.add(descriptionEn);
                }
                adapter1 = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, relationship_List);
                relationship_spin.setAdapter(adapter1);


                /*gender_DataList = databaseClass.dao().getAllRCDataListby_catKey("gender");
                for (RangeCategoryDataClass data : gender_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    gender_List.add(descriptionEn);
                }*/

                gender_List.add("MALE");
                gender_List.add("FEMALE");
                gender_List.add("OTHERS");
                adapter2 = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, gender_List);
                gender_spin.setAdapter(adapter2);

                Health_DataList = databaseClass.dao().getAllRCDataListby_catKey("health");
                for (RangeCategoryDataClass data : Health_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    Health_List.add(descriptionEn);

                }
                adapter3 = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, Health_List);
                Health_spin.setAdapter(adapter3);

                Education_DataList = databaseClass.dao().getAllRCDataListby_catKey("education");
                for (RangeCategoryDataClass data : Education_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    Education_List.add(descriptionEn);
                }
                adapter4 = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, Education_List);
                Education_spin.setAdapter(adapter4);

                schoolType_DataList = databaseClass.dao().getAllRCDataListby_catKey("school-type");
                for (RangeCategoryDataClass data : schoolType_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    schoolType_List.add(descriptionEn);
                }
                adapter5 = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, schoolType_List);
                schoolType_spin.setAdapter(adapter5);

                businessType_DataList = databaseClass.dao().getAllRCDataListby_catKey("business-type");
                for (RangeCategoryDataClass data : businessType_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    businessType_List.add(descriptionEn);
                }
                adapter6 = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, businessType_List);
                businessType_spin.setAdapter(adapter6);

                incomeType_DataList = databaseClass.dao().getAllRCDataListby_catKey("income-type");
                for (RangeCategoryDataClass data : incomeType_DataList) {
                    String descriptionEn = data.getDescriptionEn();
                    incomeType_List.add(descriptionEn);
                }
                adapter7 = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, incomeType_List);
                incomeType_spin.setAdapter(adapter7);

                if (allDataAFDataModel != null) {
                    fiCode = allDataAFDataModel.getCode().toString();
                    creator = allDataAFDataModel.getCreator().toString();
                    tag = allDataAFDataModel.getTag().toString();
                    Log.d("TAG", "onCreateView222: " + fiCode + tag + creator);
                    try {
                        if (!allDataAFDataModel.getFiFamMems().isEmpty()) {
                            Log.d("TAG", "onCreateView222: " + allDataAFDataModel.getFiFamMems().get(0).getIncome());
                            Log.d("TAG", "onCreateView222: " + allDataAFDataModel.getFiFamMems().get(0).getGender());
                            Log.d("TAG", "onCreateView222: " + allDataAFDataModel.getFiFamMems().get(0).getSchoolType());

                            if (allDataAFDataModel.getFiFamMems().get(0).getMemName() != null) {
                                faimlMemberName.setText(allDataAFDataModel.getFiFamMems().get(0).getMemName());
                            }

                            if (allDataAFDataModel.getFiFamMems().get(0).getBusiness() != null) {
                                etBusiness.setText(allDataAFDataModel.getFiFamMems().get(0).getBusiness());
                            }

                            if (allDataAFDataModel.getFiFamMems().get(0).getIncome() != null) {
                                etTextincome.setText(String.valueOf(allDataAFDataModel.getFiFamMems().get(0).getIncome()));
                            }
                            if (allDataAFDataModel.getFiFamMems().get(0).getAge() != null) {
                                etage.setText(allDataAFDataModel.getFiFamMems().get(0).getAge().toString());
                            }

                            Log.d("TAG", "onCreate:view1 " + allDataAFDataModel.getFiFamMems().get(0).getRelationWBorrower());
                            if (allDataAFDataModel.getFiFamMems().get(0).getRelationWBorrower() != null) {
                                relationship_spin.setSelection(adapter1.getPosition(allDataAFDataModel.getFiFamMems().get(0).getRelationWBorrower()));

                            }

                            Log.d("TAG", "onCreate:view2 " + list.get(0).getGender());
                            if (allDataAFDataModel.getFiFamMems().get(0).getGender() != null) {
                                gender_spin.setSelection(adapter2.getPosition(allDataAFDataModel.getFiFamMems().get(0).getGender()));
                            }

                            Log.d("TAG", "onCreate:view3 " + allDataAFDataModel.getFiFamMems().get(0).getHealth());
                            if (allDataAFDataModel.getFiFamMems().get(0).getHealth() != null) {
                                Health_spin.setSelection(adapter3.getPosition(allDataAFDataModel.getFiFamMems().get(0).getHealth()));
                            }

                            Log.d("TAG", "onCreate:view4" + allDataAFDataModel.getFiFamMems().get(0).getEducatioin());
                            if (allDataAFDataModel.getFiFamMems().get(0).getEducatioin() != null) {
                                int castePos3 = adapter4.getPosition(allDataAFDataModel.getFiFamMems().get(0).getEducatioin());
                                Education_spin.setSelection(castePos3);
                            }

                            Log.d("TAG", "onCreate:view5 " + allDataAFDataModel.getFiFamMems().get(0).getSchoolType());
                            if (allDataAFDataModel.getFiFamMems().get(0).getSchoolType() != null) {
                                int castePos3 = adapter5.getPosition(allDataAFDataModel.getFiFamMems().get(0).getSchoolType());
                                schoolType_spin.setSelection(castePos3);
                            }


                            Log.d("TAG", "onCreate:view6 " + allDataAFDataModel.getFiFamMems().get(0).getBusinessType());
                            if (allDataAFDataModel.getFiFamMems().get(0).getBusinessType() != null) {
                                int castePos3 = adapter6.getPosition(allDataAFDataModel.getFiFamMems().get(0).getBusinessType());
                                businessType_spin.setSelection(castePos3);
                            }

                            Log.d("TAG", "onCreate:view7 " + allDataAFDataModel.getFiFamMems().get(0).getIncomeType());
                            if (allDataAFDataModel.getFiFamMems().get(0).getIncomeType() != null) {
                                int castePos3 = adapter7.getPosition(allDataAFDataModel.getFiFamMems().get(0).getIncomeType());
                                incomeType_spin.setSelection(castePos3);
                            }

                        }
                    } catch (Exception exception) {
                        Toast.makeText(getContext(), "Fiextra is null here", Toast.LENGTH_SHORT).show();
                    }

                }


                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        boolean allConditionsSatisfied = true;
                        if (!isValidName(faimlMemberName.getText().toString().isEmpty() ? " " : faimlMemberName.getText().toString())) {
                            faimlMemberName.setError("Invalid faimlMemberName");
                            allConditionsSatisfied = false;
                        } else {
                            famMemName = faimlMemberName.getText().toString();
                        }

                        if (relationship_spin.getSelectedItem().toString().contains("-Select-")) {
                            Toast.makeText(getContext(), "\"Please select a relationship\"", Toast.LENGTH_SHORT).show();
                            allConditionsSatisfied = false;
                        } else {
                            relationship = relationship_spin.getSelectedItem().toString();
                        }

                        if (etage.getText().toString().isEmpty()) {
                            etage.setError("Invalid Age");
                            allConditionsSatisfied = false;
                        } else {
                            age = etage.getText().toString();
                        }

                        if (gender_spin.getSelectedItem().toString().contains("-Select-")) {
                            Toast.makeText(getContext(), "\"Please select a Gender\"", Toast.LENGTH_SHORT).show();

                            allConditionsSatisfied = false;
                        } else {
                            gender = gender_spin.getSelectedItem().toString();
                        }

                        if (Health_spin.getSelectedItem().toString().contains("-Select-")) {
                            Toast.makeText(getContext(), "\"Please select a Health\"", Toast.LENGTH_SHORT).show();

                            allConditionsSatisfied = false;
                        } else {
                            health = Health_spin.getSelectedItem().toString();
                        }

                        if (Education_spin.getSelectedItem().toString().contains("-Select-")) {
                            Toast.makeText(getContext(), "\"Please select a Education\"", Toast.LENGTH_SHORT).show();

                            allConditionsSatisfied = false;
                        } else {
                            education = Education_spin.getSelectedItem().toString();
                        }

                        if (schoolType_spin.getSelectedItem().toString().contains("-Select-")) {
                            Toast.makeText(getContext(), "\"Please select a schoolType\"", Toast.LENGTH_SHORT).show();

                            allConditionsSatisfied = false;
                        } else {
                            schoolType = schoolType_spin.getSelectedItem().toString();
                        }

                        if (!isValidName(etBusiness.getText().toString().isEmpty() ? " " : etBusiness.getText().toString())) {
                            etBusiness.setError("Invalid Business");
                            allConditionsSatisfied = false;
                        } else {
                            business = etBusiness.getText().toString();
                        }

                        if (businessType_spin.getSelectedItem().toString().contains("-Select-")) {
                            Toast.makeText(getContext(), "\"Please select a Gender\"", Toast.LENGTH_SHORT).show();

                            allConditionsSatisfied = false;
                        } else {
                            businessType = businessType_spin.getSelectedItem().toString();
                        }

                        if (etTextincome.getText().toString().isEmpty()) {
                            etTextincome.setError("Invalid income");
                            allConditionsSatisfied = false;
                        } else {
                            income = etTextincome.getText().toString();
                        }


                        if (incomeType_spin.getSelectedItem().toString().contains("-Select-")) {
                            Toast.makeText(getContext(), "\"Please select a Gender\"", Toast.LENGTH_SHORT).show();

                            allConditionsSatisfied = false;
                        } else {
                            incomeType = incomeType_spin.getSelectedItem().toString();
                        }

                        if (allConditionsSatisfied) {

                            FiFamMem fiFamMem = new FiFamMem();
                            fiFamMem.setCode(Integer.valueOf(fiCode));
                            fiFamMem.setTag(tag);
                            fiFamMem.setCreator(creator);
                            fiFamMem.setGender(gender);
                            fiFamMem.setAge(Integer.valueOf(age));
                            fiFamMem.setHealth(health);
                            fiFamMem.setRelationWBorrower(relationship);
                            fiFamMem.setEducatioin(education);
                            fiFamMem.setSchoolType(schoolType);
                            fiFamMem.setBusiness(business);
                            fiFamMem.setBusinessType(businessType);
                            fiFamMem.setMemName(famMemName);
                            fiFamMem.setIncome(Integer.valueOf(income));
                            fiFamMem.setIncomeType(incomeType);


                            memberincomelist.add(fiFamMem);
                            adapter = new FaimlyMemberIncomeAdapter(getActivity(), memberincomelist);
                            recyclerView.setAdapter(adapter);
                            alertDialog.dismiss();

                        }

                    }
                });

                canBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });


        addmemberincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (FiFamMem fiFamMem : memberincomelist) {
                    JsonObject jsonborr = FamIncomeJson(fiFamMem);
                    CustomProgressDialog customProgressDialog = new CustomProgressDialog(getActivity());
                    customProgressDialog.getContext();

                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<KycUpdateModel> call = apiInterface.updateFamMemIncome(GlobalClass.Token, GlobalClass.dbname, jsonborr);
                    Log.d("TAG", "onResponseAdhaarUpdate: " + GlobalClass.Token + " " + GlobalClass.dbname + " " + jsonborr);

                    call.enqueue(new Callback<KycUpdateModel>() {
                        @Override
                        public void onResponse(Call<KycUpdateModel> call, Response<KycUpdateModel> response) {
                            Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                            if (response.isSuccessful()) {
                                customProgressDialog.dismiss();
                                Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                                Log.d("TAG", "onResponseAdhaarUpdatemsg: " + response.body().getMessage().toString());
                              //  Toast.makeText(getActivity(), "Data set Successfully", Toast.LENGTH_SHORT).show();
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

    private JsonObject FamIncomeJson(FiFamMem fiFamMem) {

        JsonObject jsonfamIncome = new JsonObject();
        jsonfamIncome.addProperty("fiCode", fiFamMem.getCode());
        jsonfamIncome.addProperty("creator", fiFamMem.getCreator());
        jsonfamIncome.addProperty("tag", fiFamMem.getTag());
        jsonfamIncome.addProperty("famMemName", fiFamMem.getMemName());
        jsonfamIncome.addProperty("relationship", fiFamMem.getRelationWBorrower());
        jsonfamIncome.addProperty("age", fiFamMem.getAge());
        jsonfamIncome.addProperty("gender", fiFamMem.getGender());
        jsonfamIncome.addProperty("health", fiFamMem.getHealth());
        jsonfamIncome.addProperty("education", fiFamMem.getEducatioin());
        jsonfamIncome.addProperty("schoolType", fiFamMem.getSchoolType());
        jsonfamIncome.addProperty("business", fiFamMem.getBusiness());
        jsonfamIncome.addProperty("businessType", fiFamMem.getBusinessType());
        jsonfamIncome.addProperty("income", fiFamMem.getIncome());
        jsonfamIncome.addProperty("incomeType", fiFamMem.getIncomeType());
        jsonfamIncome.addProperty("autoID", fiFamMem.getAutoID());
        return jsonfamIncome;
    }
}