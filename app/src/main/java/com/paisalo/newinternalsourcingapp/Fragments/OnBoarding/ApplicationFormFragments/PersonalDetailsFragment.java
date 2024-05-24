package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.Adapters.CustomArrayAdapter;
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

    Spinner casteSpinner, religionSpinner, houseOwnerSpinner, residingforSpinner, noOfFamilyMembersSpinner, landHoldSpinner, specialAbilitySpinner, specialSocialCategorySpinner, educationalCodeSpinner, isBorrowerBlindSpinner, yearsInBusinessSpinner;
    EditText emailId, placeOfBirth;
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
    ArrayAdapter<String> adapter1,adapter2,adapter3,adapter4;

    List<RangeCategoryDataClass> casteDataList,religionDataList,presentHouseOwnerDataList,educationDataList;
    String fiCode, creator, tag, EmailId, caste, religion, PlaceOfBirth, presentHouseOwner, residingFor, numOfFamMember,
            landHold, specialAbility, specialSocialCategory, educationalCode, isBorrowerBlind, yearsInBusiness;

    public PersonalDetailsFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel = allDataAFDataModel;
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

        casteDataList = databaseClass.dao().getAllRCDataListby_catKey("caste");
        for (RangeCategoryDataClass data : casteDataList) {
            String descriptionEn = data.getDescriptionEn();
            CasteList.add(descriptionEn);
        }
        adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CasteList);
        casteSpinner.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();


        religionDataList = databaseClass.dao().getAllRCDataListby_catKey("religion");
        for (RangeCategoryDataClass data : religionDataList) {
            String descriptionEn = data.getDescriptionEn();
            Log.d("TAG", "onCreateView: "+descriptionEn);
            religionList.add(descriptionEn);
        }
        adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, religionList);
        religionSpinner.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();


        presentHouseOwnerDataList = databaseClass.dao().getAllRCDataListby_catKey("land_owner");
        for (RangeCategoryDataClass data : presentHouseOwnerDataList) {
            String descriptionEn = data.getDescriptionEn();
            presentHouseOwnerList.add(descriptionEn);

        }
        adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, presentHouseOwnerList);
        houseOwnerSpinner.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();


        educationDataList = databaseClass.dao().getAllRCDataListby_catKey("education");
        for (RangeCategoryDataClass data : educationDataList) {
            String descriptionEn = data.getDescriptionEn();
            Log.d("TAG", "onCreateView: "+descriptionEn);
            educationalCodeList.add(descriptionEn);
        }
        adapter4 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, educationalCodeList);
        educationalCodeSpinner.setAdapter(adapter4);
        adapter4.notifyDataSetChanged();


        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(getActivity(), R.array.residingforyears, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        residingforSpinner.setAdapter(adapter5);


        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(getActivity(), R.array.numOfFamMember, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noOfFamilyMembersSpinner.setAdapter(adapter6);


        String[] landHoldArray = getResources().getStringArray(R.array.landHold);
        CustomArrayAdapter adapter7 = new CustomArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, landHoldArray);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        landHoldSpinner.setAdapter(adapter7);



        ArrayAdapter<CharSequence> adapter8 = ArrayAdapter.createFromResource(getActivity(), R.array.specialAbility, android.R.layout.simple_spinner_item);
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialAbilitySpinner.setAdapter(adapter8);

        ArrayAdapter<CharSequence> adapter9 = ArrayAdapter.createFromResource(getActivity(), R.array.specialSocialCategory, android.R.layout.simple_spinner_item);
        adapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialSocialCategorySpinner.setAdapter(adapter9);

        ArrayAdapter<CharSequence> adapter10 = ArrayAdapter.createFromResource(getActivity(), R.array.yearsInBusiness, android.R.layout.simple_spinner_item);
        adapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearsInBusinessSpinner.setAdapter(adapter10);

        ArrayAdapter<CharSequence> adapter11 = ArrayAdapter.createFromResource(getActivity(), R.array.isBorrowerBlind, android.R.layout.simple_spinner_item);
        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isBorrowerBlindSpinner.setAdapter(adapter11);


        if (allDataAFDataModel != null) {
            fiCode = allDataAFDataModel.getCode().toString();
            creator = allDataAFDataModel.getCreator().toString();
            tag = allDataAFDataModel.getTag().toString();
            Log.d("TAG", "onCreateView222: " + fiCode + tag + creator);
            try {

                if (allDataAFDataModel.getFiExtra() != null) {

                    Log.d("TAG", "onCreateView:email "+allDataAFDataModel.getFiExtra().getEmaiLID());
                    if (allDataAFDataModel.getFiExtra().getEmaiLID() != null) {
                        emailId.setText(allDataAFDataModel.getFiExtra().getEmaiLID());
                        Log.d("TAG", "onCreateView:email " + allDataAFDataModel.getFiExtra().getEmaiLID());
                    }

                    if (allDataAFDataModel.getCast() != null) {
                        int castePos = adapter1.getPosition(allDataAFDataModel.getCast());
                        casteSpinner.setSelection(castePos);
                    }

                    if (allDataAFDataModel.getReligion() != null) {
                        int religionPos = adapter2.getPosition(allDataAFDataModel.getReligion());
                        Log.d("TAG", "onCreateView: "+religionPos);
                        religionSpinner.setSelection(religionPos);
                    }

                    if (allDataAFDataModel.getHouseOwner() != null) {
                        int houseOwnerPos = adapter3.getPosition(allDataAFDataModel.getHouseOwner());
                        houseOwnerSpinner.setSelection(houseOwnerPos);
                    }


                    if (allDataAFDataModel.getFiExtra().getEducatioNCODE() != null) {
                        int EducatioNCODEPos = adapter4.getPosition(allDataAFDataModel.getFiExtra().getEducatioNCODE());
                        Log.d("TAG", "onCreateView:EducatioNCODEPos "+EducatioNCODEPos);
                        educationalCodeSpinner.setSelection(EducatioNCODEPos);
                    }

                    if (allDataAFDataModel.getLiveInPresentPlace() != null) {
                        int spinnerPosition = adapter5.getPosition(allDataAFDataModel.getLiveInPresentPlace());
                        residingforSpinner.setSelection(spinnerPosition);
                    }

                    if (allDataAFDataModel.getfAmilyMember()!= null) {
                        int spinnerPosition2 = adapter6.getPosition(allDataAFDataModel.getfAmilyMember());
                        noOfFamilyMembersSpinner.setSelection(spinnerPosition2);
                    }

                    Log.d("TAG", "onCreateView:is "+allDataAFDataModel.getLandHolding());
                    if (allDataAFDataModel.getLandHolding() != null) {
                        int spinnerPosition3 = adapter7.getPosition(allDataAFDataModel.getLandHolding() + " Acres");
                        landHoldSpinner.setSelection(spinnerPosition3);
                    }

                    Log.d("TAG", "onCreateView:is "+allDataAFDataModel.getFiExtra().getIsBorrowerHandicap());

                     if (allDataAFDataModel.getFiExtra().getIsBorrowerHandicap() != null) {
                         int spinnerPosition4 = adapter8.getPosition(allDataAFDataModel.getFiExtra().getIsBorrowerHandicap());
                        specialAbilitySpinner.setSelection(spinnerPosition4);
                    }

                     if (allDataAFDataModel.getFiExtra().getIsBorrowerHandicap() != null) {
                         int spinnerPosition6 = adapter9.getPosition(allDataAFDataModel.getFiExtra().getIsBorrowerHandicap());
                        specialAbilitySpinner.setSelection(spinnerPosition6);
                    }

                    if (allDataAFDataModel.getFiExtra().getYearsInBusiness() != null) {
                        int spinnerPosition5 = adapter10.getPosition(allDataAFDataModel.getFiExtra().getYearsInBusiness());
                        yearsInBusinessSpinner.setSelection(spinnerPosition5);
                    }

                    if (allDataAFDataModel.getFiExtra().getIsBorrowerHandicap() != null) {
                        int spinnerPosition6 = adapter11.getPosition(allDataAFDataModel.getFiExtra().getIsBorrowerHandicap());
                        isBorrowerBlindSpinner.setSelection(spinnerPosition6);
                    }

                    if (allDataAFDataModel.getFiExtra().getPlacEOFBIRTH() != null) {
                        placeOfBirth.setText(allDataAFDataModel.getFiExtra().getPlacEOFBIRTH());
                    }
                }
            } catch (Exception exception) {
                Toast.makeText(getContext(), "Fiextra is null here", Toast.LENGTH_SHORT).show();
            }
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean allConditionsSatisfied = true;
                if (emailId.getText().toString().isEmpty()) {
                    emailId.setError("Select emailId");
                    allConditionsSatisfied = false;
                } else {
                    EmailId = emailId.getText().toString();
                }

                if (placeOfBirth.getText().toString().isEmpty()) {
                    placeOfBirth.setError("Select placeOfBirth");
                    allConditionsSatisfied = false;
                } else {
                    PlaceOfBirth = placeOfBirth.getText().toString();
                }

                if (casteSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) casteSpinner.getSelectedView()).setError("Please select a Gender");
                    allConditionsSatisfied = false;
                } else {
                    caste = casteSpinner.getSelectedItem().toString();
                }

                if (religionSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) religionSpinner.getSelectedView()).setError("Please select a religion");
                    allConditionsSatisfied = false;
                } else {
                    religion = religionSpinner.getSelectedItem().toString();
                }
                if (houseOwnerSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) houseOwnerSpinner.getSelectedView()).setError("Please select a presentHouseOwner");
                    allConditionsSatisfied = false;
                } else {
                    presentHouseOwner = houseOwnerSpinner.getSelectedItem().toString();
                }

                if (residingforSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) residingforSpinner.getSelectedView()).setError("Please select a residingfor");
                    allConditionsSatisfied = false;
                } else {
                    residingFor = residingforSpinner.getSelectedItem().toString();
                }

                if (noOfFamilyMembersSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) noOfFamilyMembersSpinner.getSelectedView()).setError("Please select a noOfFamilyMembers");
                    allConditionsSatisfied = false;
                } else {
                    numOfFamMember = noOfFamilyMembersSpinner.getSelectedItem().toString();
                }

                if (landHoldSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) landHoldSpinner.getSelectedView()).setError("Please select a landHold");
                    allConditionsSatisfied = false;
                } else {
                    landHold = landHoldSpinner.getSelectedItem().toString();
                }

                if (specialAbilitySpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) specialAbilitySpinner.getSelectedView()).setError("Please select a specialAbility");
                    allConditionsSatisfied = false;
                } else {
                    specialAbility = specialAbilitySpinner.getSelectedItem().toString();
                }

                if (specialSocialCategorySpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) specialSocialCategorySpinner.getSelectedView()).setError("Please select a specialSocialCategory");
                    allConditionsSatisfied = false;
                } else {
                    specialSocialCategory = specialSocialCategorySpinner.getSelectedItem().toString();
                }

                if (educationalCodeSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) educationalCodeSpinner.getSelectedView()).setError("Please select a educationalCode");
                    allConditionsSatisfied = false;
                } else {
                    educationalCode = educationalCodeSpinner.getSelectedItem().toString();
                }

                if (isBorrowerBlindSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) isBorrowerBlindSpinner.getSelectedView()).setError("Please select a isBorrowerBlind");
                    allConditionsSatisfied = false;
                } else {
                    isBorrowerBlind = isBorrowerBlindSpinner.getSelectedItem().toString();
                }

                if (yearsInBusinessSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) yearsInBusinessSpinner.getSelectedView()).setError("Please select a yearsInBusiness");
                    allConditionsSatisfied = false;
                } else {
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
                                Log.d("TAG", "onResponseAdhaarUpdate: " + response.body().getMessage());
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
                                Log.d("TAG", "onResponseAdhaarUpdate: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<KycUpdateModel> call, Throwable t) {
                            SubmitAlert(getActivity(), "Network Error", "Check Your Internet Connection");
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
        jsonPersonalInfo.addProperty("emailId", EmailId);
        jsonPersonalInfo.addProperty("caste", caste);
        jsonPersonalInfo.addProperty("religion", religion);
        jsonPersonalInfo.addProperty("placeOfBirth", PlaceOfBirth);
        jsonPersonalInfo.addProperty("presentHouseOwner", presentHouseOwner);
        jsonPersonalInfo.addProperty("residingFor", Integer.parseInt(residingFor));
        jsonPersonalInfo.addProperty("numOfFamMember", Integer.parseInt(numOfFamMember));
        jsonPersonalInfo.addProperty("landHold", landHold);
        jsonPersonalInfo.addProperty("specialAbility", specialAbility);
        jsonPersonalInfo.addProperty("specialSocialCategory", specialSocialCategory);
        jsonPersonalInfo.addProperty("educationalCode", educationalCode);
        jsonPersonalInfo.addProperty("panApplied", "pinCode");
        jsonPersonalInfo.addProperty("isBorrowerBlind", isBorrowerBlind);
        jsonPersonalInfo.addProperty("yearsInBusiness", Integer.parseInt(yearsInBusiness));
        return jsonPersonalInfo;
    }
}