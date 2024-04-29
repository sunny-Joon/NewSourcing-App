package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.paisalo.newinternalsourcingapp.Adapters.ViewPagerAdapter;
import com.paisalo.newinternalsourcingapp.Fragments.welcome.WelcomeFragment;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Utils.TiltTransformer;
import com.paisalo.newinternalsourcingapp.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.welcomViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        binding.welcomViewPager.setPageTransformer(true, new TiltTransformer());
        binding.welcomViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position ==1) {
                    // Create and start the new Intent here
//                    Intent intent = new Intent(WelcomeActivity.this, HomePageActivity.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.fade_out, R.anim.fade_out);  // Use the same animation for both to create a fade-out effect
                    binding.welcomViewPager.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}