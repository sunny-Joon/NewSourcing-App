package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.paisalo.newinternalsourcingapp.R;

public class ApplicationFormActivityMenu extends AppCompatActivity {

    CardView aadhaar,personalDetails,borrowings,guarantors,kycScanning,financialInfo,familyIncome;
    CheckBox kycScanningCheckBox,gurrantorCheckBox,borrowingsCheckBox,familyCheckBox,financialInfoCheckBox,personaldetailCheckBox,aadhaarCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form_menu);

        aadhaar = findViewById(R.id.aadhaar);
        personalDetails = findViewById(R.id.personalDetails);
        borrowings = findViewById(R.id.borrowings);
        guarantors = findViewById(R.id.guarantors);
        kycScanning = findViewById(R.id.kycScanning);
        financialInfo = findViewById(R.id.financialInfo);
        familyIncome = findViewById(R.id.familyIncome);

        aadhaarCheckBox = findViewById(R.id.aadhaarCheckBox);
        personaldetailCheckBox = findViewById(R.id.personaldetailCheckBox);
        financialInfoCheckBox = findViewById(R.id.financialInfoCheckBox);
        familyCheckBox = findViewById(R.id.familyCheckBox);
        borrowingsCheckBox = findViewById(R.id.borrowingsCheckBox);
        gurrantorCheckBox = findViewById(R.id.gurrantorCheckBox);
        kycScanningCheckBox = findViewById(R.id.kycScanningCheckBox);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
    //    String currentId = sharedPreferences.getString("CurrentId", "");

//        checkCheckboxBasedOnId(currentId);

        aadhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","aadhaar" );
                startActivity(intent);
            }
        });

        personalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","personalDetails" );
                startActivity(intent);
            }
        });

        borrowings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","borrowings" );
                startActivity(intent);
            }
        });

        guarantors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","guarantors" );
                startActivity(intent);
            }
        });

        kycScanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","kycScanning" );
                startActivity(intent);
            }
        });

        financialInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","financialInfo" );
                startActivity(intent);
            }
        });

        familyIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ApplicationFormActivity.class);
                intent.putExtra("Id","familyIncome" );
                startActivity(intent);
            }
        });

    }
    private void checkCheckboxBasedOnId(String id) {
        switch (id) {
            case "aadhaar":
                aadhaarCheckBox.setChecked(true);
                break;
            case "personalDetails":
                personaldetailCheckBox.setChecked(true);
                break;
            case "borrowings":
                borrowingsCheckBox.setChecked(true);
                break;
            case "guarantors":
                gurrantorCheckBox.setChecked(true);
                break;
            case "kycScanning":
                kycScanningCheckBox.setChecked(true);
                break;
            case "financialInfo":
                financialInfoCheckBox.setChecked(true);
                break;
            case "familyIncome":
                familyCheckBox.setChecked(true);
                break;
        }
    }
}