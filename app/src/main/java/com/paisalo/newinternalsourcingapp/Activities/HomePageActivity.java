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
    import android.view.LayoutInflater;
    import android.view.View;
    import android.webkit.WebView;
    import android.widget.ImageView;
    import android.widget.TextView;
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
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.CollectionTokenModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LeaderBoardModels.LeaderboardDataModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LeaderBoardModels.LeaderboardModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels.ManagerListDataModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels.ManagerListModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryDataModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TopAdImageModels.ImageDataModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TopAdImageModels.ImageModel;
    import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ViewStatusmodel;
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
    import java.util.concurrent.TimeUnit;

    import okhttp3.OkHttpClient;
    import okhttp3.logging.HttpLoggingInterceptor;
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
        String Id="-1",position="-1",progressEnd,msgID;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());


            getSupportActionBar().hide();

            database = DatabaseClass.getInstance(HomePageActivity.this);
            daoClass=database.dao();

            RangeCategoriesApi();
            GetBannerViewStatus();
            gpsTracker=new GpsTracker(getApplicationContext());
            if(gpsTracker.getGPSstatus()==false){
                showSettingsAlert();
            }else{
                getLoginLocation("LOGIN","");

            }
            leaderboardList();
            LiveTokenCollection();
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


        private void GetBannerViewStatus() {

            ApiInterface apiInterface= ApiClient.getClient6().create(ApiInterface.class);
            Call<Integer> call = apiInterface.getBannerExit(GlobalClass.Id);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        int status = response.body();
                        Log.d("TAG", "onResponse:banner2 " + status);
                        if (status == 0) {
                            ImageAPIBaneer();
                        }
                    } else {
                        Log.d("TAG", "onResponse:banner1 " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.d("TAG", "onFailure:banner3 "+t.getMessage());
                }
            });
        }

        private void ImageAPIBaneer() {
            Log.d("TAG", "ImageAPIBaneer:ban1 "+"run");
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ImageDataModel> call = apiInterface.getTopImage(GlobalClass.Token, GlobalClass.dbname, "S", "F");
            Log.d("TAG", "ImageAPIBaneer:ban2 "+call);
            call.enqueue(new Callback<ImageDataModel>() {
                @Override
                public void onResponse(Call<ImageDataModel> call, Response<ImageDataModel> response) {
                    if (response.isSuccessful()) {
                        Log.d("TAG", "ImageAPIBaneer:ban3 "+call);

                        ImageDataModel imageDataModel = response.body();

                        if (imageDataModel != null && imageDataModel.getData() != null) {

                            Log.d("TAG", "ImageAPIBaneer:ban4 "+imageDataModel.getData().getId().toString());

                            ImageModel imageModel = imageDataModel.getData();

                            if (imageModel != null) {
                                msgID = imageModel.getId() != null ? imageModel.getId().toString() : "";

                                Log.d("TAG", "ImageAPIBaneer:ban5" + msgID);

                                String banner = imageModel.getBanner();
                                String advertisement = imageModel.getAdvertisement();
                                Log.d("TAG", "ImageAPIBaneer:ban6" + advertisement);

                                String description = imageModel.getDescription();
                                Log.d("TAG", "ImageAPIBaneer:ban7" + description);

                                if (banner != null && !banner.isEmpty()) {
                                    String type;
                                    if (banner.endsWith(".mp4")) {
                                        type = "video";
                                    } else if (banner.endsWith(".mp3")) {
                                        type = "audio";
                                    } else if (banner.endsWith(".jpeg") || banner.endsWith(".jpg") || banner.endsWith(".png")) {
                                        type = "image";
                                    } else {
                                        Log.d("TAG", "ImageAPIBaneer:ban9" + banner);
                                        return;
                                    }

                                    Log.d("TAG", "ImageAPIBaneer:ban10" + "banner");
                                    openbanner(type, "https://erp.paisalo.in:981/LOSDOC/BannerPost/" + banner, null);

                                } else {
                                    if (advertisement != null && !advertisement.isEmpty()) {
                                        String formattedDescription = description != null ? description : "";
                                        openbanner("text", formattedDescription, advertisement);
                                    } else {
                                        Log.d("TextPopup", "No valid advertisement text found.");
                                    }
                                }
                            } else {
                                Log.d("TAG", "No image data found.");
                            }
                        } else {
                            Log.d("TAG", "ImageDataModel or its data is null.");
                        }
                    } else {
                        Log.d("TAG", "onResponse: API call unsuccessful. Response code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ImageDataModel> call, Throwable t) {
                    Log.e("TAG", "onFailure: API call failed.", t);
                }
            });
        }

        private void openbanner(String type, String content, String headText) {
            Log.d("Popup", "openbanner invoked with type: " + type);

            AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.custom_popup, null);

            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_one);

            dialog.show();

       /* int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.55);
        dialog.getWindow().setLayout(width, height);*/

            TextView addtextheader = view.findViewById(R.id.addtextheader);
            WebView contentView = view.findViewById(R.id.popup_text);
            //  VideoView videoView = view.findViewById(R.id.popup_video);
            // Button playAudioButton = view.findViewById(R.id.play_audio);
            // ImageView imageView = view.findViewById(R.id.popup_image);
            ImageView closeButton = view.findViewById(R.id.close_button);

            contentView.setVisibility(View.GONE);
            // videoView.setVisibility(View.GONE);
            // playAudioButton.setVisibility(View.GONE);
            // imageView.setVisibility(View.GONE);

            if (headText != null && !headText.isEmpty()) {
                addtextheader.setText(headText);
                addtextheader.setVisibility(View.VISIBLE);
            } else {
                addtextheader.setVisibility(View.GONE);
            }

            switch (type) {
                case "text":
                    Log.d("Popup", "Displaying text");
                    contentView.setVisibility(View.VISIBLE);
                    if (content != null && !content.isEmpty()) {
                        contentView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
                    }
                    break;

                // Comment out the rest for now

     /*   case "video":
            Log.d("Popup", "Displaying video");
            videoView.setVisibility(View.VISIBLE);
            Uri videoUri = Uri.parse(content);
            videoView.setVideoURI(videoUri);

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            videoView.setOnPreparedListener(mp -> videoView.start());

            videoView.setOnErrorListener((mp, what, extra) -> {
                Log.e("VideoView", "Error occurred while playing video. What: " + what + ", Extra: " + extra);
                return true;
            });
            break;*/
/*
        case "audio":
            Log.d("Popup", "Playing audio");
            playAudioButton.setVisibility(View.VISIBLE);
            ImageView audioImageView = view.findViewById(R.id.audio_image);
            audioImageView.setVisibility(View.VISIBLE);
            audioImageView.setImageResource(R.drawable.music);

            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(content));
            mediaPlayer.start();

            dialog.setOnDismissListener(dialog1 -> {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            });
            break;

        case "image":
            Log.d("Popup", "Displaying image");
            imageView.setVisibility(View.VISIBLE);
            Glide.with(this).load(content).into(imageView);
            break;
        */

                default:
                    Log.e("Popup", "Unknown type: " + type);
                    break;
            }


            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BannerViewStatus();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        private void BannerViewStatus() {

            ApiInterface apiInterface = ApiClient.getClient6().create(ApiInterface.class);
            Call<ViewStatusmodel> call = apiInterface.getBannerView(GlobalClass.Id, msgID);

            call.enqueue(new Callback<ViewStatusmodel>() {
                @Override
                public void onResponse(Call<ViewStatusmodel> call, Response<ViewStatusmodel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ViewStatusmodel viewStatusmodel = response.body();
                        if (viewStatusmodel != null && viewStatusmodel.getData() != null) {
                            String data = viewStatusmodel.getData().toString();
                            Log.d("TAG", "onResponse:banner11 " + data);
                        } else {
                            Log.d("TAG", "onResponse: Data is null");
                        }
                    } else {
                        Log.d("TAG", "onResponse:banner12 " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ViewStatusmodel> call, Throwable t) {
                    Log.d("TAG", "onFailure:banner13 " + t.getMessage());

        private void LiveTokenCollection() {
            Log.d("TAG", "collectionTokenModel: "+"Start");

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(1, TimeUnit.MINUTES);
            httpClient.readTimeout(1,TimeUnit.MINUTES);
            httpClient.addInterceptor(logging);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://agra.Paisalo.in:8444/PLServicev82/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            ApiInterface apiInterface=retrofit.create(ApiInterface.class);
            Call<CollectionTokenModel> call = apiInterface.LiveTokenCollection(GlobalClass.DevId,"SBIPDLCOL",
                    GlobalClass.Imei,"application/json","application/x-www-form-urlencoded","application/json","111","password",GlobalClass.Id,GlobalClass.Password);

            call.enqueue(new Callback<CollectionTokenModel>() {
                @Override
                public void onResponse(Call<CollectionTokenModel> call, Response<CollectionTokenModel> response) {
                    Log.d("TAG", "collectionTokenModel: "+"Run");

                    if(response.isSuccessful()){
                        Log.d("TAG", "collectionTokenModel: "+"Successful");
                        CollectionTokenModel collectionTokenModel = response.body();
                        GlobalClass.CollectionToken = "Bearer "+collectionTokenModel.getAccessToken().toString();

                    }else {
                        Log.d("TAG", "collectionTokenModel: "+"Unsuccessful");

                        Toast.makeText(HomePageActivity.this, response.code()+","+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CollectionTokenModel> call, Throwable t) {
                    Log.d("TAG", "collectionTokenModel: "+"failure");

                    Toast.makeText(HomePageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
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

