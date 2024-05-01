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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

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
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiFamMem;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;

import java.util.ArrayList;
import java.util.List;


public class FamilyMembersIncomeFragment extends Fragment {

    Button  addBtn,delBtn,canBtn;
    EditText faimlMemberName,etage,etBusiness,etTextincome;
    List<String> relationship_List = new ArrayList<>();
    List<String> gender_List = new ArrayList<>();
    List<String> Health_List = new ArrayList<>();
    List<String> Education_List = new ArrayList<>();
    List<String> schoolType_List = new ArrayList<>();
    List<String> businessType_List = new ArrayList<>();
    List<String> incomeType_List = new ArrayList<>();




    Spinner relationship_spin,gender_spin,Health_spin,Education_spin,schoolType_spin,businessType_spin,incomeType_spin;
    FloatingActionButton FMIncomeButton;

    AllDataAFDataModel allDataAFDataModel;

    public FamilyMembersIncomeFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel=allDataAFDataModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_members_income,container,false);

        DatabaseClass databaseClass = DatabaseClass.getInstance(getContext());

//        List<FiFamMem> list = allDataAFDataModel.getFiFamMems();

        FMIncomeButton = view.findViewById(R.id.addButton);


       /* if(!list.isEmpty()){
            faimlMemberName.setText(list.get(0).getMemName());
            etBusiness.setText(list.get(0).getBusiness());
            etTextincome.setText(list.get(0).getIncome());

        }
        etage.setText(allDataAFDataModel.getAge());
*/



        FMIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.familymemberincomepopup, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


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


                DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {

                        List<RangeCategoryDataClass>  relationship_DataList = databaseClass.dao().getAllRCDataListby_catKey("relationship");
                        for (RangeCategoryDataClass data : relationship_DataList) {
                            String descriptionEn = data.getDescriptionEn();
                            relationship_List.add(descriptionEn);
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, relationship_List);
                            relationship_spin.setAdapter(adapter1);
                        }

                        List<RangeCategoryDataClass>  gender_DataList = databaseClass.dao().getAllRCDataListby_catKey("gender");
                        for (RangeCategoryDataClass data : gender_DataList) {
                            String descriptionEn = data.getDescriptionEn();
                            gender_List.add(descriptionEn);
                            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, gender_List);
                            gender_spin.setAdapter(adapter2);
                        }

                        List<RangeCategoryDataClass>  Health_DataList = databaseClass.dao().getAllRCDataListby_catKey("health");
                        for (RangeCategoryDataClass data : Health_DataList) {
                            String descriptionEn = data.getDescriptionEn();
                            Health_List.add(descriptionEn);
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Health_List);
                            Health_spin.setAdapter(adapter3);
                        }

                        List<RangeCategoryDataClass>  Education_DataList = databaseClass.dao().getAllRCDataListby_catKey("health");
                        for (RangeCategoryDataClass data : Education_DataList) {
                            String descriptionEn = data.getDescriptionEn();
                            Education_List.add(descriptionEn);
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Education_List);
                            Education_spin.setAdapter(adapter3);
                        }

                        List<RangeCategoryDataClass> schoolType_DataList  = databaseClass.dao().getAllRCDataListby_catKey("health");
                        for (RangeCategoryDataClass data : schoolType_DataList) {
                            String descriptionEn = data.getDescriptionEn();
                            schoolType_List.add(descriptionEn);
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, schoolType_List);
                            schoolType_spin.setAdapter(adapter3);
                        }

                        List<RangeCategoryDataClass> businessType_DataList  = databaseClass.dao().getAllRCDataListby_catKey("health");
                        for (RangeCategoryDataClass data : businessType_DataList) {
                            String descriptionEn = data.getDescriptionEn();
                            businessType_List.add(descriptionEn);
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, businessType_List);
                            businessType_spin.setAdapter(adapter3);
                        }

                        List<RangeCategoryDataClass> incomeType_DataList  = databaseClass.dao().getAllRCDataListby_catKey("health");
                        for (RangeCategoryDataClass data : incomeType_DataList) {
                            String descriptionEn = data.getDescriptionEn();
                            incomeType_List.add(descriptionEn);
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, incomeType_List);
                            incomeType_spin.setAdapter(adapter3);
                        }




                    }
                });
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        Call<KycUpdateModel> call = apiInterface.updateFamMemIncome(GlobalClass.Token, GlobalClass.dbname, FamIncomeJson());
                        Log.d("TAG", "onResponseAdhaarUpdate: " + GlobalClass.Token + " " + GlobalClass.dbname + " " + FamIncomeJson());

                        call.enqueue(new Callback<KycUpdateModel>() {
                            @Override
                            public void onResponse(Call<KycUpdateModel> call, Response<KycUpdateModel> response) {
                                Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                                if (response.isSuccessful()) {
                                    Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                                    Log.d("TAG", "onResponseAdhaarUpdatemsg: " + response.body().getMessage().toString());
                                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("familyIncomeCheckBox", true);
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

                        addBtn = popupView.findViewById(R.id.addupdateButton);
                        delBtn = popupView.findViewById(R.id.deleteButton);
                        canBtn = popupView.findViewById(R.id.cancelButton);

                        canBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });

                        addBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("familyIncomeCheckBox", true);
                                editor.apply();

                                Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });

                    }
                });
            }
        });


        return view;
    }

    private JsonObject FamIncomeJson() {
        JsonObject jsonfamIncome = new JsonObject();
        jsonfamIncome.addProperty("fiCode", allDataAFDataModel.getCode().toString());
        jsonfamIncome.addProperty("creator", allDataAFDataModel.getCreator().toString());
        jsonfamIncome.addProperty("tag", allDataAFDataModel.getTag().toString());
        jsonfamIncome.addProperty("famMemName","address1" );
        jsonfamIncome.addProperty("relationship", "address2");
        jsonfamIncome.addProperty("age", 41);
        jsonfamIncome.addProperty("gender", "state");
        jsonfamIncome.addProperty("health", "city");
        jsonfamIncome.addProperty("education", "5");
        jsonfamIncome.addProperty("schoolType", "4");
        jsonfamIncome.addProperty("business", "pinCode");
        jsonfamIncome.addProperty("businessType", "pinCode");
        jsonfamIncome.addProperty("income", 25000);
        jsonfamIncome.addProperty("incomeType", "pinCode");
        jsonfamIncome.addProperty("autoID", 0);
        return jsonfamIncome;
    }
}