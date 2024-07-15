    package com.paisalo.newinternalsourcingapp.Activities;

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
    import android.widget.Toast;

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
    import com.paisalo.newinternalsourcingapp.Fragments.home.HomeFragment;
    import com.paisalo.newinternalsourcingapp.Fragments.leaderboard.LeaderBoardFragment;
    import com.paisalo.newinternalsourcingapp.Fragments.leaderboard.LeaderboardEntry;
    import com.paisalo.newinternalsourcingapp.GlobalClass;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LeaderBoardModels.LeaderboardDataModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LeaderBoardModels.LeaderboardModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels.ManagerListDataModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels.ManagerListModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryDataModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryModel;
    import com.paisalo.newinternalsourcingapp.R;
    import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
    import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
    import com.paisalo.newinternalsourcingapp.RoomDatabase.DaoClass;
    import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
    import com.paisalo.newinternalsourcingapp.RoomDatabase.ManagerListDataClass;
    import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;
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
        DatabaseClass database;
        DaoClass daoClass ;
        GpsTracker gpsTracker;
        ActivityMainBinding binding;
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        String Id="-1",position="-1",progressEnd;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            getSupportActionBar().hide();

            database = DatabaseClass.getInstance(HomePageActivity.this);
            daoClass=database.dao();

            RangeCategoriesApi();

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
                       /* binding.onboard.setImageResource(R.drawable.addbutton_ic);
                        openFragment(new CollectionFragment());*/
                        Intent intent = new Intent(HomePageActivity.this, ManagerList.class);
                        intent.putExtra("keyName", "Collection");
                        startActivity(intent);
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

        private void RangeCategoriesApi() {
            Log.d("TAG", "MyApp: "+ "Range Category Api Run");

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<RangeCategoryModel> call = apiInterface.RangeCategory(GlobalClass.Token, BuildConfig.dbname);

            call.enqueue(new Callback<RangeCategoryModel>() {
                @Override
                public void onResponse(Call<RangeCategoryModel> call, Response<RangeCategoryModel> response) {
                    if (response.isSuccessful()) {
                        Log.d("TAG", "MyApp: "+ "Range Category Api Successful");

                        RangeCategoryModel rangeCategoryModel = response.body();
                        assert rangeCategoryModel != null;
                        List<RangeCategoryDataModel> rangeCategoryDataModelList = rangeCategoryModel.getData();

                        List<RangeCategoryDataClass> rangeCategoryDataClassList = new ArrayList<>();
                        for (RangeCategoryDataModel dataModel : rangeCategoryDataModelList) {
                            RangeCategoryDataClass dataClass = new RangeCategoryDataClass(
                                    dataModel.getCatKey(),
                                    dataModel.getGroupDescriptionEn(),
                                    dataModel.getGroupDescriptionHi(),
                                    dataModel.getDescriptionEn(),
                                    dataModel.getDescriptionHi(),
                                    dataModel.getSortOrder(),
                                    dataModel.getCode()
                            );
                            rangeCategoryDataClassList.add(dataClass);
                        }

                        DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                daoClass.deleteAllRCList();
                                daoClass.insertRCData( rangeCategoryDataClassList);
                            }
                        });
                        ManagerListApi();


                    }else{
                        GlobalClass.showToast(HomePageActivity.this,5,response.message());
                    }
                }
                @Override
                public void onFailure(Call<RangeCategoryModel> call, Throwable t) {
                    GlobalClass.showToast(HomePageActivity.this,5,t.getMessage());
                }
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

            /*Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://erpservice.paisalo.in:980/PDL.Mobile.API/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);*/
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
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

            alertDialog.setTitle("GPS is settings");
            alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });

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
            jsonObject.addProperty("userId", GlobalClass.Id);
            jsonObject.addProperty("deviceId", GlobalClass.DevId);
            jsonObject.addProperty("Creator",GlobalClass.Creator);
            jsonObject.addProperty("trackAppVersion",BuildConfig.VERSION_NAME);
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
                }
            }
            return addrerss;
        }

        private void ManagerListApi() {
            Log.d("TAG", "MyApp: "+ "ManagerList Api Run");

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ManagerListModel> call = apiInterface.ManagerListApi(GlobalClass.Token,BuildConfig.dbname,GlobalClass.Imei,GlobalClass.Id);
            Log.d("TAG", "ManagerListApi: "+GlobalClass.Token+"--"+ BuildConfig.dbname +"--"+ GlobalClass.Imei +"--"+GlobalClass.Id);
            call.enqueue(new Callback<ManagerListModel>() {
                @Override
                public void onResponse(Call<ManagerListModel> call, Response<ManagerListModel> response) {
                    Log.d("TAG", "MyApp: "+ response.body());

                    if(response.isSuccessful()){
                        Log.d("TAG", "MyApp: "+ "ManagerList Api Successful");
                        Log.d("TAG", "ManagerListApi: "+ response.body());
                        ManagerListModel managerListModel = response.body();
                        List<ManagerListDataModel> managerListDataModel=  managerListModel.getData();
                        Log.d("TAG", "MyApp: "+ "manager List Size = "+managerListDataModel.size());

                        List<ManagerListDataClass> managerListDataClasses = new ArrayList<>();
                        for (ManagerListDataModel managerListData : managerListDataModel) {
                            ManagerListDataClass managerListDataClass = new ManagerListDataClass(
                                    managerListData.getImeino(),
                                    managerListData.getFoCode(),
                                    managerListData.getFoName(),
                                    managerListData.getCreator(),
                                    managerListData.getIsActive(),
                                    managerListData.getDataBase(),
                                    managerListData.getTag(),
                                    managerListData.getAreaCd(),
                                    managerListData.getAreaName(),
                                    managerListData.getCreatorDesc(),
                                    managerListData.getFiExecCode(),
                                    managerListData.getFiExecName()
                            );
                            managerListDataClasses.add(managerListDataClass);
                        }

                        DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                daoClass.deleteAllManagerList();
                                daoClass.insertManagerListData(managerListDataClasses);
                            }
                        });

                    }else{
                        GlobalClass.showToast(HomePageActivity.this,5,response.message());
                    }
                }

                @Override
                public void onFailure(Call<ManagerListModel> call, Throwable t) {
                    GlobalClass.showToast(HomePageActivity.this,5,t.getMessage());
                }
            });
        }

    }

