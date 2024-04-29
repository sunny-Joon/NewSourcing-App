package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

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
import android.widget.Spinner;
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
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalDetailsFragment extends Fragment {

    Spinner casteSpinner,religionSpinner,houseOwnerSpinner,residingforSpinner,noOfFamilyMembersSpinner,landHoldSpinner,specialAbilitySpinner,specialSocialCategorySpinner,educationalCodeSpinner,isBorrowerBlindSpinner,yearsInBusinessSpinner;
    EditText emailId,placeOfBirth;
    Button submit;
    AllDataAFDataModel allDataAFDataModel;
    List<String> CasteList = new ArrayList<>();
    List<String> religionList = new ArrayList<>();
    List<String> presentHouseOwnerList = new ArrayList<>();
    List<String> residingForList = new ArrayList<>();
    List<Integer> noOfFamilyMembersList = new ArrayList<>();
    List<Double> landHoldList = new ArrayList<>();
    List<String> specialAbilityList = new ArrayList<>();
    List<String> specialSocialCategoryList = new ArrayList<>();
    List<String> educationalCodeList = new ArrayList<>();
    List<Boolean> isBorrowerBlindList = new ArrayList<>();
    List<Integer> yearsInBusinessList = new ArrayList<>();

    public PersonalDetailsFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel=allDataAFDataModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_details, container, false);
        DatabaseClass databaseClass = DatabaseClass.getInstance(getContext());

        emailId = view.findViewById(R.id.emailId);
        placeOfBirth = view.findViewById(R.id.placeOfBirth);
        casteSpinner = view.findViewById(R.id.caste);
        religionSpinner = view.findViewById(R.id.religion);
        houseOwnerSpinner = view.findViewById(R.id.presentHouseOwner);
        residingforSpinner = view.findViewById(R.id.residingfor);
        noOfFamilyMembersSpinner = view.findViewById(R.id.noOfFamilyMembers);
        landHoldSpinner = view.findViewById(R.id.landHold);
        specialAbilitySpinner = view.findViewById(R.id.specialAbility);
        specialSocialCategorySpinner = view.findViewById(R.id.specialSocialCategory);
        educationalCodeSpinner = view.findViewById(R.id.educationalCode);
        isBorrowerBlindSpinner = view.findViewById(R.id.isBorrowerBlind);
        yearsInBusinessSpinner = view.findViewById(R.id.yearsInBusiness);
        submit = view.findViewById(R.id.submitpersonalInfo);

        String selectOption = "--Select--";
        CasteList.add(selectOption);
        religionList.add(selectOption);
        presentHouseOwnerList.add(selectOption);
        educationalCodeList.add(selectOption);
        DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                List<RangeCategoryDataClass> casteDataList = databaseClass.dao().getAllRCDataListby_catKey("caste");
                for (RangeCategoryDataClass data : casteDataList) {
                    String descriptionEn = data.getDescriptionEn();
                    CasteList.add(descriptionEn);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CasteList);
                    casteSpinner.setAdapter(adapter1);
                }
                List<RangeCategoryDataClass> religionDataList = databaseClass.dao().getAllRCDataListby_catKey("religion");
                for (RangeCategoryDataClass data : religionDataList) {
                    String descriptionEn = data.getDescriptionEn();
                    religionList.add(descriptionEn);
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, religionList);
                    religionSpinner.setAdapter(adapter2);
                }
                List<RangeCategoryDataClass> presentHouseOwnerDataList = databaseClass.dao().getAllRCDataListby_catKey("land_owner");
                for (RangeCategoryDataClass data : presentHouseOwnerDataList) {
                    String descriptionEn = data.getDescriptionEn();
                    presentHouseOwnerList.add(descriptionEn);
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, presentHouseOwnerList);
                    houseOwnerSpinner.setAdapter(adapter3);
                }
                List<RangeCategoryDataClass> educationDataList = databaseClass.dao().getAllRCDataListby_catKey("caste");
                for (RangeCategoryDataClass data : educationDataList) {
                    String descriptionEn = data.getDescriptionEn();
                    educationalCodeList.add(descriptionEn);
                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, educationalCodeList);
                    educationalCodeSpinner.setAdapter(adapter4);
                }
            }
            });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<KycUpdateModel> call = apiInterface.updatePersonalInfo(GlobalClass.Token, GlobalClass.dbname, perwsonalInfoJson());
                Log.d("TAG", "onResponseAdhaarUpdate: " + GlobalClass.Token + " " + GlobalClass.dbname + " " + perwsonalInfoJson());

                call.enqueue(new Callback<KycUpdateModel>() {
                    @Override
                    public void onResponse(Call<KycUpdateModel> call, Response<KycUpdateModel> response) {
                        Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                        if (response.isSuccessful()) {
                            Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                            Log.d("TAG", "onResponseAdhaarUpdatemsg: " + response.body().getMessage().toString());
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("personaldetailCheckBox", true);
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

                emailId.setText(allDataAFDataModel.getFiExtra().getEmaiLID());
                placeOfBirth.setText(allDataAFDataModel.getFiExtra().getPlacEOFBIRTH());
            }
        });
        return view;

    }

    private JsonObject perwsonalInfoJson() {
        JsonObject jsonPersonalInfo = new JsonObject();
        jsonPersonalInfo.addProperty("fiCode", allDataAFDataModel.getCode().toString());
        jsonPersonalInfo.addProperty("creator", allDataAFDataModel.getCreator().toString());
        jsonPersonalInfo.addProperty("tag", allDataAFDataModel.getTag().toString());
        jsonPersonalInfo.addProperty("emailId","address1" );
        jsonPersonalInfo.addProperty("caste", "address2");
        jsonPersonalInfo.addProperty("religion", "address3");
        jsonPersonalInfo.addProperty("placeOfBirth", "state");
        jsonPersonalInfo.addProperty("presentHouseOwner", "city");
        jsonPersonalInfo.addProperty("residingFor", 5);
        jsonPersonalInfo.addProperty("numOfFamMember", 4);
        jsonPersonalInfo.addProperty("landHold", "50");
        jsonPersonalInfo.addProperty("specialAbility", "pinCode");
        jsonPersonalInfo.addProperty("specialSocialCategory", "pinCode");
        jsonPersonalInfo.addProperty("educationalCode", "pinCode");
        jsonPersonalInfo.addProperty("panApplied", "pinCode");
        jsonPersonalInfo.addProperty("isBorrowerBlind", "pinCode");
        jsonPersonalInfo.addProperty("yearsInBusiness", 5);
        return jsonPersonalInfo;
    }
}