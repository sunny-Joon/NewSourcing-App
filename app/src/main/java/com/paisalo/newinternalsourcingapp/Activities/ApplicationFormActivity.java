package com.paisalo.newinternalsourcingapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.appbar.MaterialToolbar;
import com.paisalo.newinternalsourcingapp.Fragments.ApplicationFormFragments.AadhaarFragment;
import com.paisalo.newinternalsourcingapp.Fragments.ApplicationFormFragments.FamilyBorrowingsFragment;
import com.paisalo.newinternalsourcingapp.Fragments.ApplicationFormFragments.FamilyMembersIncomeFragment;
import com.paisalo.newinternalsourcingapp.Fragments.ApplicationFormFragments.FinancialInfoFragment;
import com.paisalo.newinternalsourcingapp.Fragments.ApplicationFormFragments.GuarantorsFragment;
import com.paisalo.newinternalsourcingapp.Fragments.ApplicationFormFragments.KycScanningFragment;
import com.paisalo.newinternalsourcingapp.Fragments.ApplicationFormFragments.PersonalDetailsFragment;
import com.paisalo.newinternalsourcingapp.R;

public class ApplicationFormActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);

        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        String fragmentId = getIntent().getStringExtra("Id");

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
                return new AadhaarFragment();
            case "personalDetails":
                return new PersonalDetailsFragment();
            case "financialInfo":
                return new FinancialInfoFragment();
            case "familyIncome":
                return new FamilyMembersIncomeFragment();
            case "borrowings":
                return new FamilyBorrowingsFragment();
            case "guarantors":
                return new GuarantorsFragment();
            case "kycScanning":
                return new KycScanningFragment();

            default:
                return new AadhaarFragment();
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
            return new AadhaarFragment();
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
