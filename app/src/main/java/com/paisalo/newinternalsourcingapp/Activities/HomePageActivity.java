    package com.paisalo.newinternalsourcingapp.Activities;

    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.location.Address;
    import android.location.Geocoder;
    import android.os.Bundle;
    import android.preference.PreferenceManager;
    import android.provider.Settings;
    import android.util.Log;
    import android.view.View;

    import com.google.android.material.bottomnavigation.BottomNavigationView;

    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentTransaction;
    import androidx.navigation.NavController;
    import androidx.navigation.Navigation;
    import androidx.navigation.ui.AppBarConfiguration;
    import androidx.navigation.ui.NavigationUI;
    import androidx.viewpager.widget.ViewPager;

    import com.google.gson.Gson;
    import com.google.gson.JsonObject;
    import com.paisalo.newinternalsourcingapp.Adapters.ViewPagerAdapter;
    import com.paisalo.newinternalsourcingapp.BuildConfig;
    import com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.OnBoardFragment;
    import com.paisalo.newinternalsourcingapp.Fragments.Profile.ProfileFragment;
    import com.paisalo.newinternalsourcingapp.Fragments.collection.CollectionFragment;
    import com.paisalo.newinternalsourcingapp.Fragments.home.HomeFragment;
    import com.paisalo.newinternalsourcingapp.Fragments.leaderboard.LeaderBoardFragment;
    import com.paisalo.newinternalsourcingapp.Fragments.leaderboard.LeaderboardEntry;
    import com.paisalo.newinternalsourcingapp.GlobalClass;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LeaderBoardModels.LeaderboardDataModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LeaderBoardModels.LeaderboardModel;
    import com.paisalo.newinternalsourcingapp.R;
    import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
    import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
    import com.paisalo.newinternalsourcingapp.Utils.TiltTransformer;
    import com.paisalo.newinternalsourcingapp.databinding.ActivityMainBinding;
    import com.paisalo.newinternalsourcingapp.location.GpsTracker;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Locale;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;
    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;

    public class HomePageActivity extends AppCompatActivity {

        GpsTracker gpsTracker;
        ActivityMainBinding binding;
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        String Id="-1",position="-1",progressEnd;




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            gpsTracker=new GpsTracker(getApplicationContext());
            if(gpsTracker.getGPSstatus()==false){
                showSettingsAlert();
            }else{
                getLoginLocation("LOGIN","");

            }
            leaderboardList();

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


        private void leaderboardList() {
            Log.d("TAG", "onCreateView:zz1 "+ "Call Successfull");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://erpservice.paisalo.in:980/PDL.Mobile.API/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            Call<LeaderboardModel> call = apiInterface.getLeaderboardData(GlobalClass.Token,GlobalClass.dbname);
            call.enqueue(new Callback<LeaderboardModel>() {
                @Override
                public void onResponse(Call<LeaderboardModel> call, Response<LeaderboardModel> response) {

                    if(response.isSuccessful()){
                        LeaderboardModel leaderboardModel = response.body();
                        List<LeaderboardDataModel> leaderboardEntrie =leaderboardModel.getData();
                        for (LeaderboardDataModel leaderboarddata:leaderboardEntrie) {

                            if (leaderboarddata.getkOID() != null && Id != null && leaderboarddata.getkOID().equalsIgnoreCase(Id)) {
                                position = leaderboarddata.getPosition().toString();
                                if(position.equals('1')){
                                    progressEnd = position;
                                }
                            }
                            LeaderboardEntry leaderboardEntry=new LeaderboardEntry(leaderboarddata.getPosition().toString(),leaderboarddata.getFullName(),leaderboarddata.getTargetCommAmt().toString());
                            leaderboardEntries.add(leaderboardEntry);

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(HomePageActivity.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("position", position);
                            editor.putString("progressEnd", progressEnd);

                            Gson gson = new Gson();
                            String leaderboardEntriesJson = gson.toJson(leaderboardEntries);
                            editor.putString("leaderboardEntries", leaderboardEntriesJson);
                            editor.apply();

                        }

                    }else{
                        Log.d("TAB_B", "onResponse: "+response.code());

                    }
                }

                @Override
                public void onFailure(Call<LeaderboardModel> call, Throwable t) {

                }
            });

        }

        public void showSettingsAlert(){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomePageActivity.this);

            // Setting Dialog Title
            alertDialog.setTitle("GPS is settings");

            // Setting Dialog Message
            alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

            // On pressing Settings button
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });

            // on pressing cancel button
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }

        private void getLoginLocation(String login,String address){

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonObject> call=apiInterface.livetrack(GlobalClass.Token,GlobalClass.dbname,getdatalocation(login,address));
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("TAG", "onFailure: "+t.getMessage());

                }
            });
        }

        private JsonObject getdatalocation(String login, String address) {
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("userId", "??");
            jsonObject.addProperty("deviceId", "??");
            jsonObject.addProperty("Creator","??");
            jsonObject.addProperty("trackAppVersion", BuildConfig.VERSION_NAME);
            jsonObject.addProperty("latitude",gpsTracker.getLatitude()+"");
            jsonObject.addProperty("longitude", gpsTracker.getLongitude()+"");
            jsonObject.addProperty("appInBackground",login);
            jsonObject.addProperty("Address",getAddress(gpsTracker.getLatitude(),gpsTracker.getLongitude()));
            return jsonObject;
        }

        private String getAddress(double latitude, double longitude) {
            String addrerss="";
            StringBuilder result = new StringBuilder();
            if(latitude != 0.0){
                try {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        result.append(address.getAddressLine(0));
                        // result.append(address.getLocality());
                        // result.append(address.getCountryName());
                        addrerss=result.toString();
                        Log.e("tag", addrerss);
                    }
                } catch (IOException e) {
                    //  Log.e("tag", e.getMessage());
                }
            }
            return addrerss;
        }

    }

