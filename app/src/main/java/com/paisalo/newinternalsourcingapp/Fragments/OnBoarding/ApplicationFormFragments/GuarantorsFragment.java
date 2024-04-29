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
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.FiGuarantor;
import com.paisalo.newinternalsourcingapp.R;

import java.util.List;

public class GuarantorsFragment extends Fragment {

    Button update,delete;
    EditText etTextAadhar,etTextName,etTextAge,etTextDob,etTextGuardian,etTextAddress1,etTextAddress2,
            etTextAddress3,etTextCity,etTextPincode,etTextMobile,etTextvoterid,etTextPAN,etdrivingLicense,
           etmotherfirstname,etmotherlastname,etmothermiddlename,etfatherfirstname,etfathermiddlename,
            etfatherlastname,etmaritalstatus;
    FloatingActionButton gurrantorFormButton;
    Spinner spin_gender,spin_state,spin_relationwithborr;

    AllDataAFDataModel allDataAFDataModel;
    public GuarantorsFragment(AllDataAFDataModel allDataAFDataModel) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guarantors,container,false);

        List<FiGuarantor> list = allDataAFDataModel.getFiGuarantors();
        gurrantorFormButton = view.findViewById(R.id.guarantorFormButton);

        etTextAadhar = view.findViewById(R.id.editTextAadhar);
        etTextName = view.findViewById(R.id.editTextName);
        etTextAge = view.findViewById(R.id.editTextAge);
        etTextDob = view.findViewById(R.id.editTextDob);
        etTextGuardian = view.findViewById(R.id.editTextGuardian);
        etTextAddress1 = view.findViewById(R.id.editTextAddress1);
        etTextAddress2 = view.findViewById(R.id.editTextAddress2);
        etTextAddress3 = view.findViewById(R.id.editTextAddress3);
        etTextCity = view.findViewById(R.id.editTextCity);
        etTextPincode = view.findViewById(R.id.editTextPincode);
        etTextMobile = view.findViewById(R.id.editTextMobile);
        etTextvoterid = view.findViewById(R.id.editTextvoterid);
        etTextPAN = view.findViewById(R.id.editTextPAN);
        etdrivingLicense = view.findViewById(R.id.drivingLicense);
        etmotherfirstname = view.findViewById(R.id.motherfirstname);
        etmothermiddlename = view.findViewById(R.id.mothermiddlename);
        etmotherlastname = view.findViewById(R.id.motherlastname);
        etfatherfirstname = view.findViewById(R.id.fatherfirstname);
        etfathermiddlename = view.findViewById(R.id.fathermiddlename);
        etfatherlastname = view.findViewById(R.id.fatherlastname);
        etmaritalstatus = view.findViewById(R.id.maritalstatus);

        spin_gender = view.findViewById(R.id.spinnerOptions1);
        spin_state = view.findViewById(R.id.spinnerOptions2);
        spin_relationwithborr = view.findViewById(R.id.spinnerOptions);

        if(!list.isEmpty()) {
            etTextAadhar.setText(list.get(0).getAadharID());
            etTextName.setText(list.get(0).getName());
            etTextAge.setText(list.get(0).getAge());
            etTextDob.setText(list.get(0).getDob());
            etTextGuardian.setText(list.get(0).getGurName());
            etTextAddress1.setText(list.get(0).getPerAdd1());
            etTextAddress2.setText(list.get(0).getPerAdd2());
            etTextAddress3.setText(list.get(0).getPerAdd3());
            etTextCity.setText(list.get(0).getPerCity());
            etTextPincode.setText(list.get(0).getpPin());
            etTextMobile.setText(list.get(0).getPerMob1());
            etTextvoterid.setText(list.get(0).getVoterID());
            etTextPAN.setText(list.get(0).getPanNo());
            etdrivingLicense.setText(list.get(0).getDrivingLic());
           // etmotherfirstname.setText(list.get(0).);
           // etmothermiddlename.setText(list.get(0).);
            // etmotherlastname.setText(list.get(0).);
//            etfatherfirstname.setText(list.get(0).getF);
//            etfathermiddlename.setText(list.get(0).getF);
//            etfatherlastname.setText(list.get(0).getF);
            //etmaritalstatus.setText(list.get(0).g);

           // etTextAddress3.setText(list.get(0).getPerAdd3());

        }
        gurrantorFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.guarantorspopup, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                update = popupView.findViewById(R.id.updateGuarantor);
                delete = popupView.findViewById(R.id.button2);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("checkBoxes", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("guarantorCheckBox", true);
                        editor.apply();

                        Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }
        });

        return view;    }
    }

