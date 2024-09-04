package com.paisalo.newinternalsourcingapp.Activities;

import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Adapters.BranchCodesAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.CreatorListAdapter;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.Entities.onListCReatorInteraction;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.CreatorListModels.CreatorListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.CreatorListModels.CreatorListModelData;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ImeiMappingModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LiveToken;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.DataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.FoImeiModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.FoModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.LoginModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.LoginModels.TokenDetailsModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TargetIndexModels.ResponseModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TargetIndexModels.TargetResponseModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TopAdImageModels.ImageDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TopAdImageModels.ImageModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import com.paisalo.newinternalsourcingapp.Utils.CustomProgressDialog;

import com.paisalo.newinternalsourcingapp.databinding.ActivityLoginBinding;
import com.paisalo.newinternalsourcingapp.location.GpsTracker;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements onListCReatorInteraction {
    private static final int PHONE_CALL_REQUEST=0;

    TextView versionset,btnTermCondition;
    String token;
    CustomProgressDialog customProgressDialog;
    Spinner selectDatabase;
    ActivityLoginBinding binding;
    String username, password,month = "";String year = "",stTarget_Popup="",image="" ,choosedCreator;
    Dialog dialogSearch;
    CreatorListAdapter adapter;
    String selectDatabase1;
    onListCReatorInteraction listCReatorInteraction;

    List<CreatorListModelData> list=new ArrayList<>();
    public static final String DATABASE_NAME = BuildConfig.APPLICATION_ID + ".DBNAME";
    boolean isPasswordVisible = false;
    private boolean isFABOpen = false;
    private FloatingActionButton fabMain, fabEmail, fabWhatsapp,fabChatBot;
  // String devid = "2234514145687247";
   String deviceId;
   int imei =0 ;
  // String devid = "2234514145687247",imei = "868368051227918";//GRST000223;
 // String devid = "4547494815494248",imei = "861556040245135"; //GRST003454


    private static final ArrayList<String> PERMISSIONS = new ArrayList<String>();
    private final ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
        boolean allPermissionsGranted = true;
        for (Boolean isGranted : permissions.values()) {
            if (!isGranted) {
                allPermissionsGranted = false;
                break;
            }
        }
        if (allPermissionsGranted) {
          //  getDeviceID();
            GpsTracker gpsTracker=new GpsTracker(getApplicationContext());
        } else {
            // If any permission is not granted, close the app
            showSettingsDialog();
        }
    });

    private void showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        // below line is the title for our alert dialog.
        builder.setTitle("Need Permissions");
        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel();
            // below is the intent from which we are redirecting our user.
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // this method is called when user click on negative button.
            dialog.cancel();
            handlePermissionException();
        });
        // below line is used to display our dialog
        builder.show();
    }


    private void handlePermissionException() {
        InstalledAppDetailsActivity(this);
        System.exit(0);
    }
    private void InstalledAppDetailsActivity(LoginActivity activityLogin) {

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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PERMISSIONS.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        PERMISSIONS.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        PERMISSIONS.add(android.Manifest.permission.CAMERA);
        PERMISSIONS.add(Manifest.permission.READ_PHONE_STATE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            PERMISSIONS.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        requestFCMToken();

        versionset = findViewById(R.id.versionset);
        versionset.setText(BuildConfig.VERSION_NAME);
        selectDatabase = findViewById(R.id.selectDatabase);
        btnTermCondition = findViewById(R.id.btnTermCondition);
        LottieAnimationView view = findViewById(R.id.view);

        fabMain = findViewById(R.id.fab_main);
        fabEmail = findViewById(R.id.fab_action1);
        fabWhatsapp = findViewById(R.id.fab_action2);
        fabChatBot = findViewById(R.id.fab_action3);

        fabMain.setOnClickListener(view1 -> {
            if (isFABOpen) {
                closeFABMenu();
            } else {
                openFABMenu();
            }
        });

        fabEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", "sendEmail: ");
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "itsupport@gmail.com", null));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        fabWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog();
            }
        });
        fabChatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide the password
                    binding.etLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isPasswordVisible = false;
                } else {
                    // Show the password
                    binding.etLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    isPasswordVisible = true;
                }

                // Move the cursor to the end of the text
                binding.etLoginPassword.setSelection(binding.etLoginPassword.getText().length());
            }
        });

        //    customProgressDialog = new CustomProgressDialog(this);

        customProgressDialog = new CustomProgressDialog(LoginActivity.this);
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            // getDeviceID();
            GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        }

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(LoginActivity.this, R.array.selectDatabase, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectDatabase.setAdapter(adapter1);

        listCReatorInteraction = LoginActivity.this;

        btnTermCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ActivityTermAndCondition.class);
                startActivity(intent);
            }
        });
        selectDatabase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence selectedItem = (CharSequence) adapterView.getSelectedItem();
                Log.d("TAG", "onItemSelected: " + selectedItem);
                selectDatabase1 = selectedItem.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etLoginUsername.getText().toString().trim().length() != 10) {
                    binding.etLoginUsername.setError("Please enter correct UserName!!");
                } else if (binding.etLoginPassword.getText().toString().length() != 5) {
                    binding.etLoginPassword.setError("Please enter correct Password!!");
                } else {
                    username = binding.etLoginUsername.getText().toString();
                    password = binding.etLoginPassword.getText().toString();

                    if (!checkPermissions()) {
                        requestPermissions();
                    } else {
                        getDEVICEID(username);
                    }

                    GlobalClass.Id = username;
                    customProgressDialog.show();

                    if (selectDatabase1.equalsIgnoreCase("--Select--")) {
                        Toast.makeText(LoginActivity.this, "Select Database Name", Toast.LENGTH_SHORT).show();
                        customProgressDialog.dismiss();
                    } else if (GlobalClass.isValidUsername(username) && GlobalClass.isValidPassword(password)) {
                        LoginAPi(deviceId, BuildConfig.dbname);
                        customProgressDialog.show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        customProgressDialog.dismiss();
                        //  GlobalClass.SubmitAlert1(LoginActivity.this,"unsuccessfull","USERNAME AND PASSWORD INCORRECT !!");

                    }
                }
            }
        });

        //binding.btnLoginShareDeviceId.setEnabled(false);
        binding.btnLoginShareDeviceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", "ImeiId1: ");
                if (!checkPermissions()) {
                    Log.d("TAG", "ImeiId2: ");
                    requestPermissions();
                } else {
                    /*Log.d("TAG", "ImeiId3: ");
                    getDeviceID();*/
                    String A = binding.etLoginUsername.getText().toString();
                    if (!A.isEmpty()) {
                        Log.d("TAG", "ImeiId8: ");
                        getDEVICEID(A);
                        if (deviceId != null) {
                            Log.d("TAG", "ImeiId9: ");

                            DeviceMappingRequests(A);
                        } else {
                            Toast.makeText(LoginActivity.this, "Device Id Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_language, menu);
        return true;
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean retVal;
        switch (item.getItemId()) {
            case R.id.action_language_english:
                setLocale("en");
                Log.d("TAG", "onOptionsItemSelected: English selected");
                recreate();
                retVal = true;
                break;
            case R.id.action_language_hindi:
                setLocale("hi");
                Log.d("TAG", "onOptionsItemSelected: Hindi selected");
                recreate();
                retVal = true;
                break;
            case R.id.action_language_bengali:
                setLocale("bn");
                Log.d("TAG", "onOptionsItemSelected: Bengali selected");
                recreate();
                retVal = true;
                break;
            case R.id.action_sourcing_update:
                retVal = true;
                String url = "https://erpservice.paisalo.in:980/PDL.Mobile.Api/api/ApkApp/paisaloSourcingApp";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.action_nsdl_update:
                retVal = true;
                String url1 = "https://erpservice.paisalo.in:980/PDL.Mobile.Api/api/ApkApp/AndroidNSDL";
                Intent i1 = new Intent(Intent.ACTION_VIEW);
                i1.setData(Uri.parse(url1));
                startActivity(i1);
                break;
            case R.id.action_rdSer_update:
                retVal = true;
                String url2 = "https://erpservice.paisalo.in:980/PDL.Mobile.Api/api/ApkApp/AndroidRDService";
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse(url2));
                startActivity(i2);
                break;
            default:
                retVal = super.onOptionsItemSelected(item);
        }
        return retVal;
    }


    private void requestFCMToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                        // Log and handle the token as required (e.g., send it to your server)
                        Log.d("TAG", "FCM token: " + token);

                        // You can also send the token to your server here if needed
                        // sendTokenToServer(token);
                    }
                });
    }

    private void DeviceMappingRequests(String UserID){
        Log.d("TAG", "ImeiId10: "+ UserID);

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
        TextView deviceIds = dialogView.findViewById(R.id.deviceIdET);
        TextView userId = dialogView.findViewById(R.id.userIDET);
        TextView creators = dialogView.findViewById(R.id.selectcreator);
        TextView errorTextView = dialogView.findViewById(R.id.errorTextView);
        EditText branchcodes = dialogView.findViewById(R.id.branchcodes);
        Spinner spinnerReq = dialogView.findViewById(R.id.spinner);
        RecyclerView RecviewBranchCode = dialogView.findViewById(R.id.RecviewBranchCode);

        deviceIds.setText(deviceId);
        userId.setText(UserID);

        RecviewBranchCode.setLayoutManager(new GridLayoutManager(LoginActivity.this,3));
        List<String> branchCodeList=new ArrayList<>();


        branchcodes.addTextChangedListener(new TextWatcher() {
            String regex = "[^\\D\\W]{3}";
            Pattern pattern = Pattern.compile(regex);

            // Create a Matcher object

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Matcher matcher = pattern.matcher(charSequence);

                if (charSequence.length()==3 && matcher.matches()){
                    branchCodeList.add(branchcodes.getText().toString());
                    branchcodes.setText("");
                    if (branchCodeList.size()>0){
                        BranchCodesAdapter branchCodesAdapter=new BranchCodesAdapter(LoginActivity.this,branchCodeList);
                        RecviewBranchCode.setAdapter(branchCodesAdapter);
                        branchCodesAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

                customProgressDialog.show();
                GpsTracker gpsTracker=new GpsTracker(LoginActivity.this);
                String Name = name.getText().toString();
                String Mobile = mobile.getText().toString();
                String Imei = imei.getText().toString();
                String Imei2 = imeiET2.getText().toString();
                String DeviceID = deviceIds.getText().toString();
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
                    jsonObject.addProperty( "latitude", String.valueOf(GpsTracker.getLatitude()));
                    jsonObject.addProperty( "longitude", String.valueOf(GpsTracker.getLatitude()));
                    Log.d("TAG", "onClick: "+jsonObject);

                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ImeiMappingModel> call=apiInterface.getImeiMappingReq(GlobalClass.Token,GlobalClass.dbname,jsonObject);
                    Log.d("TAG", "onResponseimei: "+jsonObject);

                    call.enqueue(new Callback<ImeiMappingModel>() {
                        @Override
                        public void onResponse(Call<ImeiMappingModel> call, Response<ImeiMappingModel> response) {
                            Log.d("TAG", "onResponseimei: "+response.body());

                            if (response.isSuccessful()){
                                customProgressDialog.dismiss();
                                Log.d("TAG", "onResponseimei: "+response.body().getMessage().toString());
                                ImeiMappingModel imeiMappingModel = response.body();
                                if(imeiMappingModel.getMessage().contains("Get Record Successfully")) {
                                    Log.d("TAG", "onResponseimei: " + "Successfully Inserted");
                                    dialogs.dismiss();
                                }else {
                                    Log.d("TAG", "onResponseimei: " + response.code());
                                    customProgressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                SubmitAlert(LoginActivity.this, "unsuccessful", + response.code() + response.message());
                                customProgressDialog.dismiss();

                            }
                        }

                        @Override
                        public void onFailure(Call<ImeiMappingModel> call, Throwable t) {
                            customProgressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    Log.d("Error","MSG" + error);
                    customProgressDialog.dismiss();
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
        Log.d("TAG", "ImeiIdcreator: ");

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
        customProgressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CreatorListModel> call=apiInterface.getCreatorList(GlobalClass.Token,GlobalClass.dbname);
        call.enqueue(new Callback<CreatorListModel>() {
            @Override
            public void onResponse(Call<CreatorListModel> call, Response<CreatorListModel> response) {
                Log.d("TAG", "ImeiIdcreator1: ");

                if(response.isSuccessful()){
                    Log.d("TAG", "ImeiIdcreator2: ");

                    customProgressDialog.dismiss();
                    CreatorListModel creatorListModel = response.body();
                    List<CreatorListModelData> creatorListModelData = creatorListModel.getData();
                    list.addAll(creatorListModelData);
                    adapter=new CreatorListAdapter(LoginActivity.this,list,dialogSearch,listCReatorInteraction);
                    recViewOfCreator.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    SubmitAlert(LoginActivity.this, "unsuccessful", response.message()+","+response.code());
                    Log.d("TAG", "onResponse: "+response.message());

                }


            }

            @Override
            public void onFailure(Call<CreatorListModel> call, Throwable t) {
                SubmitAlert(LoginActivity.this, "unsuccessful", t.getMessage());
                customProgressDialog.dismiss();
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

    private boolean checkPermissions() {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        String[] permissionArr=new String[PERMISSIONS.size()];
        for (int i=0;i<PERMISSIONS.size();i++) {
            permissionArr[i]=PERMISSIONS.get(i);
        }
        requestPermissionLauncher.launch(permissionArr);
    }


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
  /*  @SuppressLint("HardwareIds")
    private void getDeviceID() {
        Log.d("TAG", "ImeiId4: ");
        imei = 0;
        getDEVICEID();

        Log.d("checkIDDD", deviceId + "");
        if (deviceId.length() > 8) {

            if (imei > 0) {
                GlobalClass.setSharedPref(getBaseContext(), deviceId, deviceId);
            }
            GlobalClass.setSharedPref(getBaseContext(), BASE_URL, BuildConfig.BUILD_TYPE);

        }
    }*/

    public void getDEVICEID(String username) {
        Log.d("TAG", "ImeiId5: ");

//        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String lastThreeChars = "";

        try {
            if (username.trim().length() > 3) {
                Log.d("TAG", "ImeiId5a: ");

                lastThreeChars = username.trim().substring(username.trim().length() - 3);
            } else {
                Log.d("TAG", "ImeiId5b: ");
                lastThreeChars = username;
            }
        }catch (Exception e)
        {
        }
        deviceId = lastThreeChars + //we make this look like a valid IMEI
                Build.BOARD.length()%10+ Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
                Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 + Build.TYPE.length()%10 +
                Build.USER.length()%10 ; //13 digits

       /* deviceId = "2234514145687247";//GRST000223
        deviceId = "0002496575477244";//GRST002000*/
        Log.d("TAG", "ImeiId6: "+deviceId);

        //GlobalClass.setSharedPref(getBaseContext(), deviceId, deviceId);
    }

    private void LoginAPi(String devid, String dbname) {
        Log.d("TAG", "MyApp: "+ "Login Api Run");
        customProgressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<LoginModel> call = apiInterface.LoginApi(devid, dbname, getJsonOfUserIdPassword());

        call.enqueue(new Callback<LoginModel>() {

            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "MyApp: "+ "Login Api Successful");

                    customProgressDialog.dismiss();
                    LoginModel responseData = response.body();
                    if (responseData.getMessage().contains("Successfully")) {

                        DataModel dataModel = responseData.getData();
                        List<FoModel> foModel = dataModel.getFolist();
                        FoImeiModel foImeiModel = dataModel.getFoImei();
                        TokenDetailsModel tokenDetailsModel = dataModel.getTokenDetails();
                        GlobalClass.Token = "Bearer "+tokenDetailsModel.getToken().toString();
                        GlobalClass.Imei = foImeiModel.getImeino().toString();
                      //  GlobalClass.Imei = "868368051227918";
                        GlobalClass.DevId = tokenDetailsModel.getDeviceSrNo().toString();
                        GlobalClass.UserName = foModel.get(0).getFoName().toString();
                        GlobalClass.Password = password.toString();
                        GlobalClass.Creator = foModel.get(0).getCreator().toString();
                        GlobalClass.AreaCode = foModel.get(0).getAreaCd().toString();
                        GlobalClass.Tag = foModel.get(0).getTag().toString();

                        GlobalClass.DATABASE_NAME= foModel.get(0).getDataBase().toString();
                        Log.d("TAG", "MyAppCreator: "+ foModel.get(0).getImeino().toString());

                        Log.d("TAG", "MyApp: "+ foModel.get(0).getCreator().toString());


                        ImageAPI();
                        LiveTokenApi();

                    } else {
                        customProgressDialog.dismiss();

                        //   Toast.makeText(LoginActivity.this, "USERNAME AND PASSWORD INCORRECT !!", Toast.LENGTH_SHORT).show();
                       // GlobalClass.showToast(LoginActivity.this,5,responseData.getMessage());
                    // GlobalClass.SubmitAlert1(LoginActivity.this,"unsuccessfull",responseData.getMessage());
                        GlobalClass.SubmitAlert1(LoginActivity.this,"ATTENTION (" + BuildConfig.VERSION_NAME +")","Username or Password Incorrect !!");

                    }
                } else {
                    customProgressDialog.dismiss();

                    //  GlobalClass.showToast(LoginActivity.this,5,response.message());
                    GlobalClass.SubmitAlert1(LoginActivity.this,"UNSUCCESSFUL",response.message());

                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                customProgressDialog.dismiss();

               // GlobalClass.showToast(LoginActivity.this,5,t.getMessage());
            }
        });
    }

    private void LiveTokenApi() {
        ApiInterface apiInterface1 = ApiClient.getClient2().create(ApiInterface.class);
        Call<LiveToken> tokenCall=apiInterface1.liveToken(GlobalClass.LiveToken,GlobalClass.DevId,"SBIPDLCOL",
                GlobalClass.Imei,"application/json","application/x-www-form-urlencoded","application/json","password",username,password);

        tokenCall.enqueue(new Callback<LiveToken>() {
            @Override
            public void onResponse(Call<LiveToken> call, Response<LiveToken> response) {
                Log.d("TAG", "LiveToken: "+  "Start");

                if (response.isSuccessful()){
                    Log.d("TAG", "LiveToken: "+  "Successful");

                    LiveToken liveToken = response.body();
                    if(liveToken != null){
                        GlobalClass.LiveToken = "Bearer "+liveToken.getAccessToken();
                        Log.d("TAG", "LiveToken: "+  liveToken.getAccessToken().toString());
                    }
                }
            }
            @Override
            public void onFailure(Call<LiveToken> call, Throwable t) {
                Log.d("TAG", "LiveToken: "+ t.getMessage());

            }
        });
    }

    private void ImageAPI() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ImageDataModel> call = apiInterface.getTopImage(GlobalClass.Token,GlobalClass.dbname,"s");
        call.enqueue(new Callback<ImageDataModel>() {
            @Override
            public void onResponse(Call<ImageDataModel> call, Response<ImageDataModel> response) {

                if(response.isSuccessful() && response.body() != null){
                    if (response.body().getMessage().equals("No Record Found")) {

                        getTargetApi();
                        /*GlobalClass.showToast(LoginActivity.this,2,"Login SuccessFully");
                        startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                        finish();*/

                        image = "null";
                    } else {

                        ImageDataModel imageDataModel = response.body();
                       // Gson gson = new Gson();
                        //     ImageModel[] imageModels = gson.fromJson(imageDataModel.getData(), ImageModel[].class);
                        ImageModel imageModelList = imageDataModel.getData();
                        image = "https://erp.paisalo.in:981/LOSDOC/BannerPost/" + imageModelList.getBanner();
                        Log.d("TAG", "MyApp: " + "Image = " + image);

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("image", image);
                        editor.apply();
                        getTargetApi();
                       // finish();

                    }
                }else {
                    GlobalClass.showToast(LoginActivity.this,5,response.message());
                }
            }
            @Override
            public void onFailure(Call<ImageDataModel> call, Throwable t) {
                GlobalClass.showToast(LoginActivity.this,5,t.getMessage());

            }
        });
    }
    private void getTargetApi() {

        Calendar calendar = Calendar.getInstance();
// Use a fixed locale, e.g., Locale.ENGLISH
        month = new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime());
        year = new SimpleDateFormat("yyyy", Locale.ENGLISH).format(calendar.getTime());


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TargetResponseModel> call = apiInterface.getTarget(GlobalClass.Token,GlobalClass.dbname,GlobalClass.Id, String.valueOf(month), String.valueOf(year));
        Log.d("TAG", "getTargetApi: " +GlobalClass.Token+" "+GlobalClass.dbname+" "+GlobalClass.Id+" "+ String.valueOf(month)+" "+ String.valueOf(year) );
        call.enqueue(new Callback<TargetResponseModel>() {
            @Override
            public void onResponse(Call<TargetResponseModel> call, Response<TargetResponseModel> response) {
                Log.d("TAG", "getTargetApi: " +"start" );

                if(response.isSuccessful()) {
                    Log.d("TAG", "getTargetApi: " +"not null" );

                    if (response.body().getMessage().equals("No Record Found!!")) {
                        stTarget_Popup = "000000";

                        //GlobalClass.showToast(LoginActivity.this,2,"Login SuccessFully");
                        customProgressDialog.dismiss();
                        startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                        //finish();

                    } else if (response.isSuccessful() && response.body() != null && response.body().getResponseModels().size() > 0) {
                        List<ResponseModel> responseModel = response.body().getResponseModels();

                        stTarget_Popup = responseModel.get(0).getTargetCommAmt().toString();


                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("stTarget_Popup", stTarget_Popup);
                        editor.apply();

//                        GlobalClass.dismissLottieAlertDialog();
//
//                        GlobalClass.showToast(LoginActivity.this,2,"Login SuccessFully");
                        customProgressDialog.dismiss();

                        startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                        //finish();

                    } else {
                        GlobalClass.showToast(LoginActivity.this,5,response.message());
                    }
                }else{
                    GlobalClass.showToast(LoginActivity.this,5,response.message());
                }
            }

            @Override
            public void onFailure(Call<TargetResponseModel> call, Throwable t) {
                GlobalClass.showToast(LoginActivity.this,5,t.getMessage());


            }
        });
    }

    private JsonObject getJsonOfUserIdPassword() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        return jsonObject;
    }

    @Override
    public void onListCReatorInteraction(String choosedCreatora) {
        Log.d("TAG", "onListCReatorInteraction: "+choosedCreatora);
        choosedCreator=choosedCreatora;
    }

    private void openFABMenu() {
        isFABOpen = true;
        fabEmail.show();
        fabWhatsapp.show();
        fabChatBot.show();
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabEmail.hide();
        fabWhatsapp.hide();
        fabChatBot.hide();
    }

    private void openWhatsApp(int num) {
        Log.d("TAG", "onClick2: ");
        String phoneNumberWithCountryCode ="";
        switch (num){
            case 1:
                phoneNumberWithCountryCode = "918595847059";
                break;
            case 2:
                phoneNumberWithCountryCode = "919258297452";
                break;
            case 3:
                phoneNumberWithCountryCode = "919258297453";
                break;
        }

        try {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumberWithCountryCode;
            sendIntent.setData(Uri.parse(url));
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void openCall(int num){

        String phoneNumber ="";
        switch (num){
            case 1:
                phoneNumber = "tel:8595847059";
                break;
            case 2:
                phoneNumber = "tel:9258297452";
                break;
            case 3:
                phoneNumber = "tel:9258297453";
                break;
        }

      //  String phoneNumber = "tel:9910238307";  // Replace with the predefined number
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phoneNumber));
        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        }

    }
    private void openText(int num){

        String phoneNumber ="";
        switch (num){
            case 1:
                phoneNumber = "918595847059";
                break;
            case 2:
                phoneNumber = "919258297452";
                break;
            case 3:
                phoneNumber = "919258297453";
                break;
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + Uri.encode(phoneNumber)));
        startActivity(intent);
    }
    private void OpenDialog() {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.whatsapp_popup, null);

        // Build the dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        alert.setView(dialogView);

        ImageView whatsapp1,whatsapp2,whatsapp3,text1,text2,text3,call1,call2,call3;
        // Get the dialog elements
        whatsapp1 = dialogView.findViewById(R.id.whatsapp1);
        whatsapp2 = dialogView.findViewById(R.id.whatsapp2);
        whatsapp3 = dialogView.findViewById(R.id.whatsapp3);
        text1 = dialogView.findViewById(R.id.text1);
        text2 = dialogView.findViewById(R.id.text2);
        text3 = dialogView.findViewById(R.id.text3);
        call1 = dialogView.findViewById(R.id.call1);
        call2 = dialogView.findViewById(R.id.call2);
        call3 = dialogView.findViewById(R.id.call3);

        // Create and show the dialog
        AlertDialog alertDialog = alert.create();
        alertDialog.show();

        // Set button click listener
        whatsapp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp(1);
            }
        });
        whatsapp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp(2);
            }
        });
        whatsapp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp(3);
            }
        });
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openText(1);
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openText(2);
            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openText(3);
            }
        });
        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},PHONE_CALL_REQUEST);
                }
                else
                {
                    openCall(1);
                }
            }
        });
        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},PHONE_CALL_REQUEST);
                }
                else
                {
                    openCall(2);
                }

            }
        });
        call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.CALL_PHONE},PHONE_CALL_REQUEST);
                }
                else
                {
                    openCall(3);
                }
            }
        });
    }
}
