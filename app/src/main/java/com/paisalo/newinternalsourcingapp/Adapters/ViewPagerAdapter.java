package com.paisalo.newinternalsourcingapp.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.paisalo.newinternalsourcingapp.Fragments.TransFragment;
import com.paisalo.newinternalsourcingapp.Fragments.home.HomeFragment;
import com.paisalo.newinternalsourcingapp.Fragments.welcome.WelcomeFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 3; // Number of pages in the ViewPager

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return WelcomeFragment.newInstance(position);
        }else{
            return TransFragment.newInstance(position);
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}





