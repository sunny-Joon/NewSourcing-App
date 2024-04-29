package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.R;

public class PersonalDetailsFragment extends Fragment {

    Spinner casteSpinner,religionSpinner,houseOwnerSpinner,residingforSpinner,noOfFamilyMembersSpinner,landHoldSpinner,specialAbilitySpinner,specialSocialCategorySpinner,educationalCodeSpinner,isBorrowerBlindSpinner,yearsInBusinessSpinner;
    EditText emailId,placeOfBirth;
    Button submit;
    AllDataAFDataModel allDataAFDataModel;
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

        //set data
        emailId.setText(allDataAFDataModel.getFiExtra().getEmaiLID());
        placeOfBirth.setText(allDataAFDataModel.getFiExtra().getPlacEOFBIRTH());




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("personaldetailCheckBox", true);
                editor.apply();

                Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;


    }
}