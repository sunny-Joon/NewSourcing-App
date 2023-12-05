package com.paisalo.newinternalsourcingapp.Activities;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
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
    private TabLayout tabLayout;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        viewPager.setAdapter(new MyPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(MyPagerAdapter.TAB_TITLES[position])
        ).attach();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private static class MyPagerAdapter extends FragmentStateAdapter {

        private static final String[] TAB_TITLES = {
                "Aadhaar", "Personal Details", "Financial Info", "Family Income", "Borrowings",
                "Guarantors", "KYC Scanning"
        };

        public MyPagerAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new AadhaarFragment();
                case 1:
                    return new PersonalDetailsFragment();
                case 2:
                    return new FinancialInfoFragment();
                case 3:
                    return new FamilyMembersIncomeFragment();
                case 4:
                    return new FamilyBorrowingsFragment();
                case 5:
                    return new GuarantorsFragment();
                case 6:
                    return new KycScanningFragment();
                default:
                    return new AadhaarFragment();
            }
        }

        @Override
        public int getItemCount() {
            return TAB_TITLES.length;
        }
    }
}
