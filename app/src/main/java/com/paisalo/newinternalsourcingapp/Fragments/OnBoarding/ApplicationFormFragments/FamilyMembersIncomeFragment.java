package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiFamMem;
import com.paisalo.newinternalsourcingapp.R;

import java.util.List;


public class FamilyMembersIncomeFragment extends Fragment {

    Button  addBtn,delBtn,canBtn;
    EditText faimlMemberName,etage,etBusiness,etTextincome;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_members_income,container,false);
        List<FiFamMem> list = allDataAFDataModel.getFiFamMems();

        faimlMemberName = view.findViewById(R.id.faimlMemberName);
        etage = view.findViewById(R.id.age);
        etBusiness = view.findViewById(R.id.Business);
        etTextincome = view.findViewById(R.id.editTextincome);


        relationship_spin = view.findViewById(R.id.relationship);
        gender_spin = view.findViewById(R.id.gender);
        Health_spin = view.findViewById(R.id.Health);
        Education_spin = view.findViewById(R.id.Education);
        schoolType_spin = view.findViewById(R.id.schoolType);
        businessType_spin = view.findViewById(R.id.businessType);
        incomeType_spin = view.findViewById(R.id.incomeType);

        FMIncomeButton = view.findViewById(R.id.FMIncome);

        if(!list.isEmpty()){
            faimlMemberName.setText(list.get(0).getMemName());
            etBusiness.setText(list.get(0).getBusiness());
            etTextincome.setText(list.get(0).getIncome());

        }
        etage.setText(allDataAFDataModel.getAge());


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

        return view;


    }
}