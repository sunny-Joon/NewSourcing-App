    package com.paisalo.newinternalsourcingapp.Activities;

    import android.os.Bundle;
    import android.view.View;

    import com.google.android.material.bottomnavigation.BottomNavigationView;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentTransaction;
    import androidx.navigation.NavController;
    import androidx.navigation.Navigation;
    import androidx.navigation.ui.AppBarConfiguration;
    import androidx.navigation.ui.NavigationUI;
    import androidx.viewpager.widget.ViewPager;

    import com.paisalo.newinternalsourcingapp.Adapters.ViewPagerAdapter;
    import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.OnBoardFragment;
    import com.paisalo.newinternalsourcingapp.Fragments.Profile.ProfileFragment;
    import com.paisalo.newinternalsourcingapp.Fragments.collection.CollectionFragment;
    import com.paisalo.newinternalsourcingapp.Fragments.home.HomeFragment;
    import com.paisalo.newinternalsourcingapp.Fragments.leaderboard.LeaderBoardFragment;
    import com.paisalo.newinternalsourcingapp.R;
    import com.paisalo.newinternalsourcingapp.Utils.TiltTransformer;
    import com.paisalo.newinternalsourcingapp.databinding.ActivityMainBinding;

    public class MainActivity extends AppCompatActivity {

        ActivityMainBinding binding;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            binding = ActivityMainBinding.inflate(getLayoutInflater());
            binding.mainActViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
            binding.mainActViewPager.setPageTransformer(true, new TiltTransformer());
            binding.mainActViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 1) {

                        binding.mainActViewPager.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            setContentView(binding.getRoot());
            overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
            BottomNavigationView navView = findViewById(R.id.nav_view);

            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_leaderboard, R.id.navigation_collection, R.id.navigation_profile)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            NavigationUI.setupWithNavController(binding.navView, navController);

            navView.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        binding.onboard.setImageResource(R.drawable.addbutton_ic);
                        openFragment(new HomeFragment());
                        break;
                    case R.id.navigation_leaderboard:
                        binding.onboard.setImageResource(R.drawable.addbutton_ic);
                        openFragment(new LeaderBoardFragment());
                        break;
                    case R.id.navigation_collection:
                        binding.onboard.setImageResource(R.drawable.addbutton_ic);
                        openFragment(new CollectionFragment());
                        break;
                    case R.id.navigation_profile:
                        binding.onboard.setImageResource(R.drawable.addbutton_ic);
                        openFragment(new ProfileFragment());
                        break;
                }
                return true;
            });

            binding.onboard.setOnClickListener(view -> {
                binding.onboard.setImageResource(R.drawable.red_addbutton_ic);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, new OnBoardFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });
        }
        private void openFragment(Fragment fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_main, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

