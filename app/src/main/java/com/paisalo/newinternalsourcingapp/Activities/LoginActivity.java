package com.paisalo.newinternalsourcingapp.Activities;

import static com.paisalo.newinternalsourcingapp.Retrofit.ApiClient.BASE_URL;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Adapters.CreatorListAdapter;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.Entities.onListCReatorInteraction;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.CreatorListModels.CreatorListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.CreatorListModels.CreatorListModelData;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ImeiMappingModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.DataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.FoImeiModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.FoModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.LoginModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.TokenDetailsModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels.ManagerListDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ManagerListModels.ManagerListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TargetIndexModels.ResponseModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TargetIndexModels.TargetResponseModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TopAdImageModels.ImageDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TopAdImageModels.ImageModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DaoClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.ManagerListDataClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.loginDataClass;
import com.paisalo.newinternalsourcingapp.Utils.Utils;
import com.paisalo.newinternalsourcingapp.databinding.ActivityLoginBinding;
import com.paisalo.newinternalsourcingapp.location.GpsTracker;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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


public class LoginActivity extends AppCompatActivity implements onListCReatorInteraction {
    DaoClass daoClass ;
    ActivityLoginBinding binding;
    String username, password,month = "";String year = "",stTarget_Popup="",image="" ,deviceId,choosedCreator;
    DatabaseClass database;
    Dialog dialogSearch;
    CreatorListAdapter adapter;
    onListCReatorInteraction listCReatorInteraction;

    List<CreatorListModelData> list=new ArrayList<>();

    private long deviceImei;

    String devid = "2234514145687247",imei = "868368051227919";

    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
        //   Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            GpsTracker gpsTracker=new GpsTracker(getApplicationContext());
        }
        listCReatorInteraction=LoginActivity.this;

        database = DatabaseClass.getInstance(LoginActivity.this);
        daoClass=database.dao();
        binding.LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = binding.etLoginUsername.getText().toString();
                password = binding.etLoginPassword.getText().toString();
                GlobalClass.Id = username;


                if (isValidUsername(username) && isValidPassword(password)) {
                    LoginAPi(devid, BuildConfig.dbname, imei);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //binding.btnLoginShareDeviceId.setEnabled(false);
        binding.btnLoginShareDeviceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String A = binding.etLoginUsername.getText().toString();
                if(A != null) {
                    getDeviceID(A);
                    if (deviceId != null) {
                        DeviceMappingRequests(A);
                    } else {
                        Toast.makeText(LoginActivity.this, "Device Id Not Found", Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }
        });
    }

    private void DeviceMappingRequests(String UserID){
        Log.d("TAG", "DeviceMappingRequests: " + UserID);


        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.mappingpopup_layout, null);
        builder.setView(dialogView);

        AlertDialog dialogs = builder.create();
        dialogs.setCanceledOnTouchOutside(false);
        dialogs.setCancelable(false);

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        dialogs.show();

        EditText name = dialogView.findViewById(R.id.nameET);
        EditText mobile = dialogView.findViewById(R.id.mobileET);
        EditText imei = dialogView.findViewById(R.id.imeiET);
        EditText imeiET2 = dialogView.findViewById(R.id.imeiET2);
        TextView deviceId = dialogView.findViewById(R.id.deviceIdET);
        TextView userId = dialogView.findViewById(R.id.userIDET);
        TextView creators = dialogView.findViewById(R.id.selectcreator);
        TextView errorTextView = dialogView.findViewById(R.id.errorTextView);
        EditText branchcodes = dialogView.findViewById(R.id.branchcodes);
        Spinner spinnerReq = dialogView.findViewById(R.id.spinner);

        deviceId.setText(GlobalClass.DevId);
        userId.setText(UserID);

        creators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreatorSearchDialog(creators);
            }
        });


        Button save = dialogView.findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GpsTracker gpsTracker=new GpsTracker(LoginActivity.this);
                String Name = name.getText().toString();
                String Mobile = mobile.getText().toString();
                String Imei = imei.getText().toString();
                String Imei2 = imeiET2.getText().toString();
                String DeviceID = deviceId.getText().toString();
                String UserID = userId.getText().toString();
                String Creator = creators.getText().toString();
                String branchcode = branchcodes.getText().toString();

                String error = validateData(Name, Mobile, Imei, DeviceID, UserID,Creator,branchcode);
                if (error == null) {
                    JsonObject jsonObject=new JsonObject();
                    jsonObject.addProperty( "name", Name);
                    jsonObject.addProperty( "mobile", Mobile);
                    jsonObject.addProperty( "creator", choosedCreator);
                    jsonObject.addProperty( "compType", spinnerReq.getSelectedItem().toString());
                    jsonObject.addProperty( "deviceId", DeviceID);
                    jsonObject.addProperty( "imeI_no1", Imei);
                    jsonObject.addProperty( "imeI_no2", Imei2);
                    jsonObject.addProperty( "userId", UserID);
                    jsonObject.addProperty( "mapBranch", branchcodes.getText().toString());
                    jsonObject.addProperty( "latitude", String.valueOf(gpsTracker.getLatitude()));
                    jsonObject.addProperty( "longitude", String.valueOf(gpsTracker.getLongitude()));
                    Log.d("TAG", "onClick: "+jsonObject);

                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ImeiMappingModel> call=apiInterface.getImeiMappingReq(GlobalClass.Token,GlobalClass.dbname,jsonObject);
                    Log.d("TAG", "onResponseimei: "+jsonObject);

                    call.enqueue(new Callback<ImeiMappingModel>() {
                        @Override
                        public void onResponse(Call<ImeiMappingModel> call, Response<ImeiMappingModel> response) {
                            Log.d("TAG", "onResponseimei: "+response.body());

                            if (response.isSuccessful()){
                                Log.d("TAG", "onResponseimei: "+response.body().getMessage().toString());
                                ImeiMappingModel imeiMappingModel = response.body();
                                if(imeiMappingModel.getMessage().contains("Data inserted Successfully")) {
                                    Log.d("TAG", "onResponseimei: " + "Successfully Inserted");
                                    dialogs.dismiss();
                                }
                                else {
                                    Log.d("TAG", "onResponseimei: " + response.code());

                                    Toast.makeText(LoginActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Log.d("TAG", "onResponseimei: " + "Not Successfully Inserted");

                                Toast.makeText(LoginActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ImeiMappingModel> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    Log.d("Error","MSG" + error);
                    errorTextView.setText(error);
                }

                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        Button cancelButton =dialogView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
            }
        });
    }

    public String validateData(String name, String mobile, String imei, String deviceId, String userId,String creator,String branchcode) {
        if (name.isEmpty()) {
            return "Name is required.";
        }

        if (mobile.length() != 10) {
            return "Mobile number must be 10 digits long.";
        }

        if (imei.length() != 15) {
            return "IMEI must be 15 digits.";
        }

        if (deviceId.length() != 16) {
            return "Device ID must be 16 digit.";
        }

        if (!userId.matches("[A-Za-z]{4}\\d{6}")) {
            return "User ID should be in the format: ABCD123456";
        }

        if (creator.isEmpty()) {
            return "Creator is required.";
        }
        if (branchcode.isEmpty()) {
            return "Branch Code is required.";
        }
        return null; // No error
    }

    private void showCreatorSearchDialog(TextView creators) {

        dialogSearch=new Dialog(LoginActivity.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.getWindow().setLayout(650,800);
        EditText edit_text=dialogSearch.findViewById(R.id.edit_text);
        RecyclerView recViewOfCreator=dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.d("TAG", "onDismiss: hiiitt"+choosedCreator);

                creators.setText(choosedCreator);
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CreatorListModel> call=apiInterface.getCreatorList(GlobalClass.Token,GlobalClass.dbname);
        call.enqueue(new Callback<CreatorListModel>() {
            @Override
            public void onResponse(Call<CreatorListModel> call, Response<CreatorListModel> response) {

                if(response.isSuccessful()){
                    CreatorListModel creatorListModel = response.body();
                    List<CreatorListModelData> creatorListModelData = creatorListModel.getData();
                    list.addAll(creatorListModelData);

                    adapter=new CreatorListAdapter(LoginActivity.this,list,dialogSearch,listCReatorInteraction);
                    recViewOfCreator.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<CreatorListModel> call, Throwable t) {

            }
        });

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString().toUpperCase());
            }
        });


        dialogSearch.show();
    }

    private final ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
        boolean allPermissionsGranted = true;
        for (Boolean isGranted : permissions.values()) {
            if (!isGranted) {
                allPermissionsGranted = false;
                break;
            }
        }
        if (allPermissionsGranted) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                //    startActivity(new Intent(Splash_Screen.this,MainActivity.class));
                }
            },4000);
        } else {
            // If any permission is not granted, close the app
            showPermissionAlertDialog();
        }
    });
    private boolean checkPermissions() {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        requestPermissionLauncher.launch(PERMISSIONS);
    }

    private void showPermissionAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This app requires all permissions to function properly. Please grant all permissions to continue.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Close the app
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

  /*  private boolean requestPermissions() {
        final boolean[] perMissionFlag = {false};
        // below line is use to request permission in the current activity.
        // this method is use to handle error in runtime permissions
        Dexter.withActivity(this)
                // below line is use to request the number of permissions which are required in our app.
                .withPermissions(Manifest.permission.CAMERA,
                        // below is the list of permissions
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE)
                // after adding permissions we are calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            // do you work now
                            permissionCheck();
                            perMissionFlag[0] =true;

                        }
                        // check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permanently, we will show user a dialog message.
                            showSettingsDialog();
                            perMissionFlag[0] =false;

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        // this method is called when user grants some permission and denies some of them.
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(error -> {
                    // we are displaying a toast message for error message.
                    Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    handlePermissionException();
                    perMissionFlag[0] =false;

                })
                // below line is use to run the permissions on same thread and to check the permissions
                .onSameThread().check();
        return perMissionFlag[0];


    }*/

 /*   private void permissionCheck() {
        String[] permissions = {
                Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        String rationale = "Please provide permission so that app can work smoothly ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this*//*context*//*, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.

//                if (ActivityCompat.checkSelfPermission(ActivityLogin.this,
//                        Manifest.permission.READ_PHONE_STATE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    handlePermissionException();
//                }
//                else {
                getDeviceID();
//                }

                // If you have access to the external storage, do whatever you nee
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    if (Environment.isExternalStorageManager()){
//
//                       getDeviceID();
//
//    // If you don't have access, launch a new activity to show the user the system's dialog
//    // to allow access to the external storage
//                    }else{
//                      handlePermissionException();
//                    }
//                }

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                //permissionCheck();
                handlePermissionException();
            }
        });
    }*/

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String s) {
        Log.d("TAG", "filter: "+s);
        List<CreatorListModelData> creatorModels=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getCreator().toUpperCase().contains(s.toString())) {
                creatorModels.add(list.get(i));
                Log.d("TAG", "filter: "+list.get(i));
            }
        }
        adapter.filterList(creatorModels);
        adapter.notifyDataSetChanged();
    }
    @SuppressLint("HardwareIds")
    public void getDeviceID(String UserID) {

        String lastThreeChars = "";

        try {
            if (UserID.trim().length() > 3) {
                lastThreeChars = UserID.trim().substring(UserID.trim().length() - 3);
            } else {
                lastThreeChars = UserID;
            }
        }catch (Exception e)
        {}
        deviceId = lastThreeChars + //we make this look like a valid IMEI
                Build.BOARD.length()%10+ Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
                Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 + Build.TYPE.length()%10 +
                Build.USER.length()%10 ; //13 digits

        Log.d("TAG", "DeviceMappingRequests: " + deviceId);
        GlobalClass.DevId = deviceId;
    }
/*
    private void handlePermissionException() {
        InstalledAppDetailsActivity(this);
        System.exit(0);
    }*/

  /*  private void InstalledAppDetailsActivity(LoginActivity activityLogin) {

        if (activityLogin == null) {
            return;
        }

        Intent intent = new Intent();
        //intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);

    }*/


    private void LoginAPi(String devid, String dbname, String imeino) {
        Log.d("TAG", "MyApp: "+ "Login Api Run");
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginModel> call = apiInterface.LoginApi(devid, dbname, imeino, getJsonOfUserIdPassword());
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "MyApp: "+ "Login Api Successful");

                    LoginModel responseData = response.body();
                    if (responseData.getMessage().contains("Successfully")) {

                        DataModel dataModel = responseData.getData();
                        List<FoModel> foModel = dataModel.getFolist();
                        FoImeiModel foImeiModel = dataModel.getFoImei();
                        TokenDetailsModel tokenDetailsModel = dataModel.getTokenDetails();
                        GlobalClass.Token = "Bearer "+tokenDetailsModel.getToken().toString();
                        GlobalClass.Imei = foImeiModel.getImeino().toString();
                        GlobalClass.DevId = tokenDetailsModel.getDeviceSrNo().toString();
                        GlobalClass.UserName = foModel.get(0).getFoName().toString();
                        GlobalClass.Creator = foModel.get(0).getCreator().toString();
                        GlobalClass.AreaCode = foModel.get(0).getAreaCd().toString();
                        GlobalClass.Tag = foModel.get(0).getTag().toString();

                        ImageAPI();


                       /* if (foModel != null && foImeiModel != null && tokenDetailsModel != null) {
                            saveDataToDatabase(foModel,foImeiModel,tokenDetailsModel);
                      }*/

                    } else {
                        Toast.makeText(LoginActivity.this, "Id Password Not Matched", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("TAG", "MyApp: "+ "Login Api Unsuccessful");

                   // LoginAPi(devid, dbname, imei);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("TAG", "MyApp: "+ "Login Api Failure");
                Toast.makeText(LoginActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
    private void getTargetApi() {

        Calendar calendar = Calendar.getInstance();
        month = new SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.getTime());
        year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(calendar.getTime());

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TargetResponseModel> call = apiInterface.getTarget(GlobalClass.Token,GlobalClass.dbname,GlobalClass.Id, String.valueOf(month), String.valueOf(year));
        call.enqueue(new Callback<TargetResponseModel>() {
            @Override
            public void onResponse(Call<TargetResponseModel> call, Response<TargetResponseModel> response) {
                if (response.body().getMessage().equals("No Record Found!!")) {
                    stTarget_Popup = "000000";
                    RangeCategoriesApi();

                } else if (response.isSuccessful() && response.body() != null && response.body().getResponseModels().size() > 0) {
                    List<ResponseModel> responseModel = response.body().getResponseModels();

                    stTarget_Popup = responseModel.get(0).getTargetCommAmt().toString();


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("stTarget_Popup", stTarget_Popup);
                    editor.apply();
                    RangeCategoriesApi();

                }else{
                    Log.d("TAG", "MyApp: "+ "Get Target Api Unsuccessful");
                    Toast.makeText(LoginActivity.this, "1002", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TargetResponseModel> call, Throwable t) {
                Log.d("TAG", "MyApp: "+ "Target Api Failure");
                Toast.makeText(LoginActivity.this, "1002", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ImageAPI() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ImageDataModel> call = apiInterface.getTopImage(GlobalClass.Token,GlobalClass.dbname,"D");
        call.enqueue(new Callback<ImageDataModel>() {
            @Override
            public void onResponse(Call<ImageDataModel> call, Response<ImageDataModel> response) {

                if(response.isSuccessful() && response.body() != null){
                if (response.body().getMessage().equals("No Record Found")) {
                    getTargetApi();
                    image = "null";
                } else {

                    ImageDataModel imageDataModel = response.body();
                    Gson gson = new Gson();
               //     ImageModel[] imageModels = gson.fromJson(imageDataModel.getData(), ImageModel[].class);
                    ImageModel imageModelList = imageDataModel.getData();
                    image = "https://erp.paisalo.in:981/LOSDOC/BannerPost/" + imageModelList.getBanner();
                    Log.d("TAG", "MyApp: " + "Image = " + image);

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("image", image);
                    editor.apply();

                    getTargetApi();

                }
            }else {
                    Log.d("TAG", "MyApp: " + "Image Api Unsuccessful");
                    Toast.makeText(LoginActivity.this, "1001", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ImageDataModel> call, Throwable t) {
                Log.d("TAG", "MyApp: "+ "Image Api Failure");
                Toast.makeText(LoginActivity.this, "1001", Toast.LENGTH_SHORT).show();
            }
        });
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
                    startActivity(new Intent(LoginActivity.this, HomePageActivity.class));


                }else{
                    Log.d("TAG", "MyApp: "+ "ManagerList Api Unsuccessful");
                    Toast.makeText(LoginActivity.this, "1004", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onResponsemanager: "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<ManagerListModel> call, Throwable t) {
                Log.d("TAG", "MyApp: "+ "ManagerList Api Failure");
                Toast.makeText(LoginActivity.this, "1004", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void RangeCategoriesApi() {
        Log.d("TAG", "MyApp: "+ "Range Category Api Run");

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RangeCategoryModel> call = apiInterface.RangeCategory(GlobalClass.Token,BuildConfig.dbname);

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
                    Log.d("TAG", "RangeCartegory: "+response.code());
                    Log.d("TAG", "MyApp: "+ "Range Category Api Unsuccessful");
                    Toast.makeText(LoginActivity.this, "1003", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RangeCategoryModel> call, Throwable t) {
                Log.d("TAG", "MyApp: "+ "Range Category Api Failure");
                Toast.makeText(LoginActivity.this, "1003", Toast.LENGTH_SHORT).show();
            }
        });
    }
/*
    private void saveDataToDatabase(List<FoModel> foModel, FoImeiModel foImeiModel, TokenDetailsModel tokenDetailsModel) {
        DatabaseClass database = DatabaseClass.getInstance(LoginActivity.this);
        loginDataClass entity = new loginDataClass();

        for (FoModel fomodel : foModel) {

            entity.setImeino(fomodel.getImeino().toString());
            entity.setFoCode(fomodel.getFoCode());
            entity.setFoName(fomodel.getFoName());
            entity.setCreator(fomodel.getCreator());
            entity.setIsActive(fomodel.getIsActive());
            entity.setDataBase(fomodel.getDataBase());
            entity.setTag(fomodel.getTag());
            entity.setAreaCd(fomodel.getAreaCd());
            entity.setAreaName(fomodel.getAreaName());
            entity.setCreatorDesc(fomodel.getCreatorDesc());
            entity.setFiExecCode(fomodel.getFiExecCode());
            entity.setFiExecName(fomodel.getFiExecName());

            daoClass.insertLoginData(entity);
        }
        entity.setFoImeiimeino(foImeiModel.getImeino().toString());
        entity.setActualYN(foImeiModel.getActualYN().toString());
        entity.setFoImeiIsActive(foImeiModel.getIsActive().toString());
        entity.setNewAppVersion(foImeiModel.getNewAppVerison().toString());
        entity.setAppDownPath(foImeiModel.getAppDownPath().toString());
        entity.setRequestUrl(foImeiModel.getRequestUrl().toString());
        entity.setSimno(foImeiModel.getSimno().toString());
        entity.setTokenId(tokenDetailsModel.getId().toString());
        entity.setToken(tokenDetailsModel.getToken().toString());
        entity.setUserName(tokenDetailsModel.getUserName().toString());
        entity.setDeviceSrNo(tokenDetailsModel.getDeviceSrNo().toString());
        entity.setPassword(tokenDetailsModel.getPassword().toString());
        entity.setValidity(tokenDetailsModel.getValidaty().toString());
        entity.setRefreshToken(tokenDetailsModel.getRefreshToken().toString());
        entity.setRole(tokenDetailsModel.getRole().toString());
        entity.setGuidId(tokenDetailsModel.getGuidId().toString());
        entity.setExpiredTime(tokenDetailsModel.getExpiredTime().toString());

        DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                daoClass.insertLoginData(entity);
            }
        });


    }
*/

    private JsonObject getJsonOfUserIdPassword() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        return jsonObject;
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 1;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 5;
    }

    @Override
    public void onListCReatorInteraction(String choosedCreatora) {
        Log.d("TAG", "onListCReatorInteraction: "+choosedCreatora);
        choosedCreator=choosedCreatora;
    }
}
