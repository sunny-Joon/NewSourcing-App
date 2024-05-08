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
import android.widget.TextView;

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
    /*List<String> residingForList = new ArrayList<>();
    List<Integer> noOfFamilyMembersList = new ArrayList<>();
    List<Double> landHoldList = new ArrayList<>();
    List<String> specialAbilityList = new ArrayList<>();
    List<String> specialSocialCategoryList = new ArrayList<>();*/
    List<String> educationalCodeList = new ArrayList<>();


    String fiCode,creator,tag,EmailId,caste,religion,PlaceOfBirth,presentHouseOwner,residingFor,numOfFamMember,
            landHold,specialAbility,specialSocialCategory,educationalCode,isBorrowerBlind,yearsInBusiness;

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

        emailId.setText(allDataAFDataModel.getFiExtra().getEmaiLID());
        placeOfBirth.setText(allDataAFDataModel.getFiExtra().getPlacEOFBIRTH());
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
                List<RangeCategoryDataClass> educationDataList = databaseClass.dao().getAllRCDataListby_catKey("education");
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

                fiCode = allDataAFDataModel.getCode().toString();
                creator = allDataAFDataModel.getCreator().toString();
                tag = allDataAFDataModel.getTag().toString();

                boolean allConditionsSatisfied=true;
                if(emailId.getText().toString().isEmpty()){
                    emailId.setError("Select emailId");
                    allConditionsSatisfied = false;
                }else{
                    EmailId = emailId.getText().toString();
                }

                if(placeOfBirth.getText().toString().isEmpty()){
                    placeOfBirth.setError("Select placeOfBirth");
                    allConditionsSatisfied = false;
                }else{
                    PlaceOfBirth = placeOfBirth.getText().toString();
                }

                if (casteSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) casteSpinner.getSelectedView()).setError("Please select a Gender");
                    allConditionsSatisfied = false;
                }else{
                    caste = casteSpinner.getSelectedItem().toString();
                }

                if (religionSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) religionSpinner.getSelectedView()).setError("Please select a religion");
                    allConditionsSatisfied = false;
                }else{
                    religion = religionSpinner.getSelectedItem().toString();
                }

                if (residingforSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) residingforSpinner.getSelectedView()).setError("Please select a residingfor");
                    allConditionsSatisfied = false;
                }else{
                    residingFor = residingforSpinner.getSelectedItem().toString();
                }

                if (noOfFamilyMembersSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) noOfFamilyMembersSpinner.getSelectedView()).setError("Please select a noOfFamilyMembers");
                    allConditionsSatisfied = false;
                }else{
                    numOfFamMember = noOfFamilyMembersSpinner.getSelectedItem().toString();
                }

                if (landHoldSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) landHoldSpinner.getSelectedView()).setError("Please select a landHold");
                    allConditionsSatisfied = false;
                }else{
                    landHold = landHoldSpinner.getSelectedItem().toString();
                }

                if (specialAbilitySpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) specialAbilitySpinner.getSelectedView()).setError("Please select a specialAbility");
                    allConditionsSatisfied = false;
                }else{
                    specialAbility = specialAbilitySpinner.getSelectedItem().toString();
                }

                if (specialSocialCategorySpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) specialSocialCategorySpinner.getSelectedView()).setError("Please select a specialSocialCategory");
                    allConditionsSatisfied = false;
                }else{
                    specialSocialCategory = specialSocialCategorySpinner.getSelectedItem().toString();
                }

                if (educationalCodeSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) educationalCodeSpinner.getSelectedView()).setError("Please select a educationalCode");
                    allConditionsSatisfied = false;
                }else{
                    educationalCode = educationalCodeSpinner.getSelectedItem().toString();
                }

                if (isBorrowerBlindSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) isBorrowerBlindSpinner.getSelectedView()).setError("Please select a isBorrowerBlind");
                    allConditionsSatisfied = false;
                }else{
                    isBorrowerBlind = isBorrowerBlindSpinner.getSelectedItem().toString();
                }

                if (yearsInBusinessSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) yearsInBusinessSpinner.getSelectedView()).setError("Please select a yearsInBusiness");
                    allConditionsSatisfied = false;
                }else{
                    yearsInBusiness = yearsInBusinessSpinner.getSelectedItem().toString();
                }

                if (allConditionsSatisfied) {


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
                }
            }
        });
        return view;

    }

    private JsonObject perwsonalInfoJson() {
        JsonObject jsonPersonalInfo = new JsonObject();
        jsonPersonalInfo.addProperty("fiCode", fiCode);
        jsonPersonalInfo.addProperty("creator", creator);
        jsonPersonalInfo.addProperty("tag", tag);
        jsonPersonalInfo.addProperty("emailId",EmailId );
        jsonPersonalInfo.addProperty("caste", caste);
        jsonPersonalInfo.addProperty("religion", religion);
        jsonPersonalInfo.addProperty("placeOfBirth", PlaceOfBirth);
        jsonPersonalInfo.addProperty("presentHouseOwner", presentHouseOwner);
        jsonPersonalInfo.addProperty("residingFor", residingFor);
        jsonPersonalInfo.addProperty("numOfFamMember", numOfFamMember);
        jsonPersonalInfo.addProperty("landHold", landHold);
        jsonPersonalInfo.addProperty("specialAbility", specialAbility);
        jsonPersonalInfo.addProperty("specialSocialCategory", specialSocialCategory);
        jsonPersonalInfo.addProperty("educationalCode", educationalCode);
        jsonPersonalInfo.addProperty("panApplied", "pinCode");
        jsonPersonalInfo.addProperty("isBorrowerBlind", isBorrowerBlind);
        jsonPersonalInfo.addProperty("yearsInBusiness", yearsInBusiness);
        return jsonPersonalInfo;
    }
}