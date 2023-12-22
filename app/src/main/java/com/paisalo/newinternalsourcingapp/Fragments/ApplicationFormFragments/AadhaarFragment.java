package com.paisalo.newinternalsourcingapp.Fragments.ApplicationFormFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivity;
import com.paisalo.newinternalsourcingapp.R;

public class AadhaarFragment extends Fragment {

    Button submit;
    CardView currentAddress;
    CheckBox addressCheckBox;

    public AadhaarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aadhaar, container, false);

        submit = view.findViewById(R.id.aadhaarSubmitButton);
        currentAddress = view.findViewById(R.id.currentAddress);
        addressCheckBox = view.findViewById(R.id.addressCheckBox);
        currentAddress.setVisibility(View.GONE);

        addressCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentAddress.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }

}
