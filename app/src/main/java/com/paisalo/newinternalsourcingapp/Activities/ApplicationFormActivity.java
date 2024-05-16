package com.paisalo.newinternalsourcingapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.appbar.MaterialToolbar;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments.AadhaarFragment;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments.FamilyBorrowingsFragment;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments.FamilyMembersIncomeFragment;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments.FinancialInfoFragment;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments.GuarantorsFragment;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments.KycScanningFragment;
import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments.PersonalDetailsFragment;
import com.paisalo.newinternalsourcingapp.Modelclasses.FiJsonObject;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.R;

import java.io.Serializable;

public class ApplicationFormActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private MaterialToolbar toolbar;
    static AllDataAFDataModel allDataAFDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);

        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.toolbar);

        Intent intent = getIntent();

        Serializable afDataObject = intent.getSerializableExtra("allDataAFDataModel");

        if (afDataObject != null && afDataObject instanceof AllDataAFDataModel) {
            allDataAFDataModel = (AllDataAFDataModel) afDataObject;
            if(allDataAFDataModel!=null){
                Log.d("TAG", "sunny: " + " Not Null");

            }else{
                Log.d("TAG", "sunny: " + "Null");

            }
        }

        setSupportActionBar(toolbar);

        String fragmentId = intent.getStringExtra("Id");

        Fragment initialFragment = getInitialFragment(fragmentId);

        viewPager.setAdapter(new MyPagerAdapter(this, initialFragment));

        // Save the ID to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CurrentId", fragmentId);
        editor.apply();


    }

    private Fragment getInitialFragment(String fragmentId) {
        switch (fragmentId) {
            case "aadhaar":
                return new AadhaarFragment(allDataAFDataModel);
            case "personalDetails":
                return new PersonalDetailsFragment(allDataAFDataModel);
            case "financialInfo":
                return new FinancialInfoFragment(allDataAFDataModel);
            case "familyIncome":
                return new FamilyMembersIncomeFragment(allDataAFDataModel);
            case "borrowings":
                return new FamilyBorrowingsFragment(allDataAFDataModel);
            case "guarantors":
                return new GuarantorsFragment(allDataAFDataModel);
            case "kycScanning":
                return new KycScanningFragment(allDataAFDataModel);
            default:
                return new AadhaarFragment(allDataAFDataModel);
        }
    }

    private static class MyPagerAdapter extends FragmentStateAdapter {

        private final Fragment initialFragment;

        public MyPagerAdapter(FragmentActivity fragmentActivity, Fragment initialFragment) {
            super(fragmentActivity);
            this.initialFragment = initialFragment;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return initialFragment;
            }
            return new AadhaarFragment(allDataAFDataModel);
        }

        @Override
        public int getItemCount() {
            return 1;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
