package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.paisalo.newinternalsourcingapp.Adapters.CityListAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.DistrictListAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.RangeCategoryAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.SubDistrictListAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.VillageListAdapter;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.Entities.ScanAadhaar.AadharData;
import com.paisalo.newinternalsourcingapp.Entities.CityChooseListner;
import com.paisalo.newinternalsourcingapp.Entities.DistrictChooseListner;
import com.paisalo.newinternalsourcingapp.Entities.ScanAadhaar.AadharUtils;
import com.paisalo.newinternalsourcingapp.Entities.SubDistChooseListner;
import com.paisalo.newinternalsourcingapp.Entities.VillageChooseListner;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.Modelclasses.FiExtra;
import com.paisalo.newinternalsourcingapp.Modelclasses.FiJsonObject;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.DLVerificationModels.Data_;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.DLVerificationModels.Data_1;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.DLVerificationModels.Data_2;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.PANerificationModels.PanVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.DLVerificationModels.DLVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.VoterIdVerificationModels.VoterIdVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.OCRScanModels.AdharDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.OCRScanModels.AdharDataResponse;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.CityData;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.CityModelList;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.DistrictData;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.DistrictListModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.SubDistrictData;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.SubDistrictModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.VillageData;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.StateDistDataModels.VillageListModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;
import com.paisalo.newinternalsourcingapp.Utils.CustomProgressDialog;
import com.paisalo.newinternalsourcingapp.Utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KYCActivity extends AppCompatActivity implements VillageChooseListner,DistrictChooseListner,CityChooseListner,SubDistChooseListner {

    EditText editTextAadhar, editTextName, editTextAge, editTextDob, editTextGuardian,
            editTextAddress1, editTextAddress2, editTextAddress3,
            editTextCity, editTextPincode, editTextMobile, editTextPAN, editTextdrivingLicense,
            editTextvoterIdKyc,editTextmotherfirstname, editTextmothermiddlename, editTextmotherlastname, editTextFatherFname, editTextfathermiddlename,
            editTextfatherlastname, editTextmaritalstatus, editTextspousefirstname, editTextspousemiddlename, editTextspouselastname;

    TextView txtVDistrictName, txtCityName,txtVillageName,txtSubDistictName;
    String AadharID,isAadharVerified,name,Fname,Lname,Age,DOB,guardian,guardianRelatnWithBorrower,P_Add1,P_Add2,P_Add3,P_City,P_State,P_Ph3,PanNO,DrivingLic,voterId,motherName,motherMiddleName,motherLastName,fatherName,fatherMiddleName,fatherLastName,F_Fname,F_Mname,F_Lname,isMarried,spouseFirstName,spouseMiddleName,spouseLastName,verifiedPanName="",verifiedLicensename="",verifiedVotername="",villageCode,subDistCode,distCode,cityCode,stateCode;
    int P_Pin;
    Button submitButton,qrScan,adhaarBack,adhaarFront,panOcr;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_ADHAARFRONT_CAPTURE = 102;
    private static final int REQUEST_ADHAARBACK_CAPTURE = 103;
    private static final int REQUEST_PAN_CAPTURE = 104;
    private static final int REQUEST_IMAGE_CROP = 101;
    private static final int REQUEST_ADHAARFRONT_CROP = 102;
    private static final int REQUEST_ADHAARBACK_CROP = 103;
    private static final int REQUEST_PAN_CROP = 104;
    Bitmap bitmap;
    File file,profileImageFile,adhaarFrontFile,adhaarBackFile,panFile;
    CustomProgressDialog customProgressDialog;
    ImageView aadhaarScanner, calendericon,profilePic;
    List<VillageData> villageDataList =new ArrayList<>();
    List<CityData> cityDataList =new ArrayList<>();
    List<DistrictData> districtDataList =new ArrayList<>();
    List<SubDistrictData> subDistrictDataList =new ArrayList<>();
    VillageListAdapter villageListAdapter;
    DistrictListAdapter districtListAdapter;
    SubDistrictListAdapter subDistrictListAdapter;
    CityListAdapter cityListAdapter;
    VillageData villageData;
    DistrictData districtDat;
    SubDistrictData subDistrictData;
    CityData cityData;
    VillageChooseListner listVillageInteraction;
    DistrictChooseListner listDistictInteraction;
    SubDistChooseListner listSubDistructInteraction;
    CityChooseListner cityChooseListner;
    List<String> GenderList = new ArrayList<>();
    List<String> RelationWithBorrowerList = new ArrayList<>();
    List<String> MaritalStatusList = new ArrayList<>();

    private ProgressBar progressBar;
    private int maxProgress = 26;

    private EditText[] editTexts;

    private Calendar calendar;
    Spinner acspGender, acspAadharState,acspRelationship,isMarriedSpinner;

    CheckBox dl_Checkbox,pan_Checkbox,voterId_Checkbox;
    protected static final byte SEPARATOR_BYTE = (byte)255;
    protected static final int VTC_INDEX = 15;
    protected int imageStartIndex,emailMobilePresent,imageEndIndex;
    boolean aadharNumberentry=false;
    protected String signature,email,mobile;
    protected ArrayList<String> decodedData;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kycactivity);
           Log.d("TAG", "onCreate: "+ GlobalClass.Creator);

        DatabaseClass databaseClass = DatabaseClass.getInstance(this);
        customProgressDialog = new CustomProgressDialog(this);

        progressBar = findViewById(R.id.simpleProgressBar);
        progressBar.setMax(maxProgress);
        aadhaarScanner = findViewById(R.id.aadhaarScanner);
        calendericon = findViewById(R.id.calendericonKyc);
        submitButton = findViewById(R.id.submitButtonKyc);
        editTextAadhar = findViewById(R.id.editTextAadharKyc);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAgeKYC);
        editTextDob = findViewById(R.id.editTextDobKyc);
        editTextGuardian = findViewById(R.id.editTextGuardianKyc);
        editTextAddress1 = findViewById(R.id.editTextAddress1Kyc);
        editTextAddress2 = findViewById(R.id.editTextAddress2Kyc);
        editTextAddress3 = findViewById(R.id.editTextAddress3Kyc);
        editTextCity = findViewById(R.id.editTextCityKyc);
        editTextPincode = findViewById(R.id.editTextPincodeKyc);
        editTextMobile = findViewById(R.id.editTextMobileKyc);
        editTextPAN = findViewById(R.id.editTextPANKyc);
        editTextdrivingLicense = findViewById(R.id.drivingLicenseKyc);
        editTextvoterIdKyc = findViewById(R.id.voterIdKyc);
        editTextmotherfirstname = findViewById(R.id.motherfirstnameKyc);
        editTextmothermiddlename = findViewById(R.id.mothermiddlenameKyc);
        editTextmotherlastname = findViewById(R.id.motherlastnameKyc);
        editTextFatherFname = findViewById(R.id.fatherfirstnameKyc);
        editTextfathermiddlename = findViewById(R.id.fathermiddlenameKyc);
        editTextfatherlastname = findViewById(R.id.fatherlastnameKyc);
        isMarriedSpinner = findViewById(R.id.maritalstatusKyc);
        editTextspousefirstname = findViewById(R.id.spousefirstnameKyc);
        editTextspousemiddlename = findViewById(R.id.spousemiddlenameKyc);
        editTextspouselastname = findViewById(R.id.spouselastnameKyc);
        profilePic = findViewById(R.id.profilePic);

        txtVDistrictName=findViewById(R.id.txtVDistrictName);
        txtSubDistictName=findViewById(R.id.txtSubDistictName);
        txtVillageName=findViewById(R.id.txtVillageName);
        txtCityName =findViewById(R.id.txtCityName);

        dl_Checkbox = findViewById(R.id.DL_checkbox);
        voterId_Checkbox = findViewById(R.id.voterID_checkbox);
        pan_Checkbox = findViewById(R.id.pan_checkbox);

        cityChooseListner=KYCActivity.this;
        listDistictInteraction=KYCActivity.this;
        listSubDistructInteraction=KYCActivity.this;
        listVillageInteraction=KYCActivity.this;

        acspGender = findViewById(R.id.acspGender);
        acspAadharState = findViewById(R.id.acspAadharState);
        acspRelationship = findViewById(R.id.acspRelationship);

        panOcr = findViewById(R.id.panOcr);


        GenderList.add("--Select--");
        RelationWithBorrowerList.add("--Select--");
        MaritalStatusList.add("--Select--");

        // diasable
        editTextAge.setEnabled(false);
        editTextDob.setEnabled(false);

        //edittext array for Progressbar
        editTexts = new EditText[]{
                editTextAadhar, editTextName, editTextAge, editTextGuardian, editTextAddress1, editTextAddress2, editTextAddress3,
                editTextCity, editTextPincode, editTextMobile, editTextPAN, editTextdrivingLicense,
                editTextmotherfirstname, editTextmothermiddlename, editTextmotherlastname, editTextFatherFname, editTextfathermiddlename,
                editTextfatherlastname, editTextmaritalstatus, editTextspousefirstname, editTextspousemiddlename, editTextspouselastname
        };
       /* for (final EditText editText : editTexts) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        if (isEmpty(editText)) {
                            progressBar.incrementProgressBy(-1);
                        } else {
                            progressBar.incrementProgressBy(1);
                        }
                    }
                }
            });
        }*/

        aadhaarScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopup();
            }
        });

        panOcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_PAN_CAPTURE);
                }
            }
        });

        calendar = Calendar.getInstance();
        calendericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                       startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                   }
               }
           });

        DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                List<RangeCategoryDataClass> gendreDataList = databaseClass.dao().getAllRCDataListby_catKey("gender");
                for (RangeCategoryDataClass data : gendreDataList) {
                    String descriptionEn = data.getDescriptionEn();
                    GenderList.add(descriptionEn);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(KYCActivity.this, android.R.layout.simple_spinner_item, GenderList);
                    acspGender.setAdapter(adapter1);
                }

                List<RangeCategoryDataClass> stateDataList = new ArrayList<>();
                RangeCategoryDataClass rangeCategoryDataClass = new RangeCategoryDataClass("--Select--","--Select--","--Select--","--Select--","--Select--",0,"99");
                stateDataList.add(rangeCategoryDataClass);
                stateDataList.addAll(databaseClass.dao().getAllRCDataListby_catKey("state"));
                RangeCategoryAdapter rangeCategoryAdapter = new RangeCategoryAdapter(KYCActivity.this,stateDataList );
                acspAadharState.setAdapter(rangeCategoryAdapter);


                List<RangeCategoryDataClass> relatnshipList = databaseClass.dao().getAllRCDataListby_catKey("relationship");
                for (RangeCategoryDataClass data : relatnshipList) {
                    String descriptionEn = data.getDescriptionEn();
                    RelationWithBorrowerList.add(descriptionEn);
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(KYCActivity.this, android.R.layout.simple_spinner_item, RelationWithBorrowerList);
                    acspRelationship.setAdapter(adapter3);
                }

                List<RangeCategoryDataClass> maritalStatusList = databaseClass.dao().getAllRCDataListby_catKey("marrital_status");
                for (RangeCategoryDataClass data : maritalStatusList) {
                    String descriptionEn = data.getDescriptionEn();
                    MaritalStatusList.add(descriptionEn);
                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(KYCActivity.this, android.R.layout.simple_spinner_item, MaritalStatusList);
                    isMarriedSpinner.setAdapter(adapter4);
                }

            }
    });
        txtCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", "onClick: " + ((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code);
                if (((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code.equals("99")){
                    Toast.makeText(KYCActivity.this, "Please select Valid State", Toast.LENGTH_SHORT).show();
                }else{
                    showCityDialog(txtCityName,((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code);
                    stateCode = ((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code;
                }
            }
        });
        txtVDistrictName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code.equals("99")){
                    Toast.makeText(KYCActivity.this, "Please select Valid State", Toast.LENGTH_SHORT).show();
                }else{
                    showDistrictDialog(txtVDistrictName,((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code);
                }
            }
        });
        txtSubDistictName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (districtDat!=null){

                    showSubDistrictDialog(txtSubDistictName,districtDat.getDisTCODE());
                }else{
                    Toast.makeText(KYCActivity.this, "Please choose any District", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtVillageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subDistrictData!=null && districtDat!=null && !((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code.equals("99")){

                    showVillageDialog(txtVillageName,((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code,districtDat.getDisTCODE(),subDistrictData.getSuBDISTCODE());
                }else{
                    Toast.makeText(KYCActivity.this, "Please choose any valid state, district or sub district", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dl_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl_Checkbox.setChecked(false);
                dlVerification("drivinglicense",editTextdrivingLicense.getText().toString(),editTextDob.getText().toString());
            }
        });
        pan_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pan_Checkbox.setChecked(false);
                panVerification("pancard",editTextPAN.getText().toString(),"");
            }
        });
        voterId_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voterId_Checkbox.setChecked(false);
                voterIdVerification("voterid",editTextvoterIdKyc.getText().toString(),"");

            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            createJsonObject();
            }
        });
        editTextPAN.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               }
               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                   pan_Checkbox.setClickable(true);
                   Toast.makeText(KYCActivity.this, "Pan Changed", Toast.LENGTH_SHORT).show();
                   pan_Checkbox.setChecked(false);
               }
               @Override
               public void afterTextChanged(Editable s) {
               }
           });
        editTextvoterIdKyc.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               }
               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                   voterId_Checkbox.setClickable(true);
                   Toast.makeText(KYCActivity.this, "ID Changed", Toast.LENGTH_SHORT).show();
                   voterId_Checkbox.setChecked(false);
               }
               @Override
               public void afterTextChanged(Editable s) {
               }
           });
        editTextdrivingLicense.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               }
               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                   dl_Checkbox.setClickable(true);
                   Toast.makeText(KYCActivity.this, "DL Changed", Toast.LENGTH_SHORT).show();
                   dl_Checkbox.setChecked(false);
               }
               @Override
               public void afterTextChanged(Editable s) {
               }
           });

    }//On Create Close

    public void openPopup() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.adhaar_scanner_popup, null);

        adhaarFront = popupView.findViewById(R.id.adhaarFront);
        adhaarBack = popupView.findViewById(R.id.adhaarBack);
        qrScan = popupView.findViewById(R.id.scanQrCode);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        qrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                IntentIntegrator scanIntegrator = new IntentIntegrator(KYCActivity.this);
                scanIntegrator.setOrientationLocked(false);
                scanIntegrator.initiateScan(Collections.singleton("QR_CODE"));
            }
        });

        adhaarFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_ADHAARFRONT_CAPTURE);
                }
            }
        });

        adhaarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_ADHAARBACK_CAPTURE);
                }                }
        });

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }


    private void setDataOfAdhar(File croppedImage, String imageData) {
        ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Data Fetching from " + imageData.toUpperCase() + " Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

        RequestBody image_befWorkBody = RequestBody.create(MediaType.parse("image/*"),croppedImage);
        MultipartBody.Part imagePart= MultipartBody.Part.createFormData("file",croppedImage.getName(),image_befWorkBody);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), croppedImage);
        builder.addFormDataPart("file", croppedImage.getName(), surveyBody);
        RequestBody requestBody = builder.build();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AdharDataResponse> call = apiInterface.getAdharDataByOCR(GlobalClass.Token, BuildConfig.dbname,imageData, imagePart);

        call.enqueue(new Callback<AdharDataResponse>() {
            @Override
            public void onResponse(Call<AdharDataResponse> call, Response<AdharDataResponse> response) {
                Log.d("TAG", "onResponsews: " + response.body());
                AdharDataResponse adharDataResponse = response.body();
                if (response.isSuccessful()) {
                    progressBar.dismiss();
                    if (adharDataResponse != null) {
                        AdharDataModel adharDataModel = adharDataResponse.getData();
                        if (adharDataModel != null) {
                            if (imageData.equals("aadharfront")) {
                                if (response.code() == 200) {
                                    String adharId = (String) adharDataModel.getAdharId();
                                    editTextAadhar.setText(adharId);
                                    String name = (String) adharDataModel.getName();
                                    editTextName.setText(name);

                                    String dob = (String) adharDataModel.getDob();
                                    Log.d("TAG", "onResponse:dob "+dob);
                                    editTextDob.setText(dob);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                    try {
                                        Date dateOfBirth = sdf.parse(dob);
                                        Calendar dobCalendar = Calendar.getInstance();
                                        dobCalendar.setTime(dateOfBirth);
                                        Calendar todayCalendar = Calendar.getInstance();
                                        int age = todayCalendar.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);
                                        if (todayCalendar.get(Calendar.MONTH) < dobCalendar.get(Calendar.MONTH) ||
                                                (todayCalendar.get(Calendar.MONTH) == dobCalendar.get(Calendar.MONTH) &&
                                                        todayCalendar.get(Calendar.DAY_OF_MONTH) < dobCalendar.get(Calendar.DAY_OF_MONTH))) {
                                            age--;
                                        }
                                        editTextDob.setText(String.valueOf(age));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    String gender = (String) adharDataModel.getGender();

                                    if (gender.equalsIgnoreCase("Male")) {
                                        acspGender.setSelection(2);
                                    } else if (gender.equalsIgnoreCase("Female")) {
                                        acspGender.setSelection(1);
                                    } else {
                                        acspGender.setSelection(3);
                                    }
                                   // borrower.isAadharVerified = "O";

                                } else {
                                    Utils.alert(KYCActivity.this, "Please capture aadhaar front image!!");
                                }


                                progressBar.dismiss();

                            } else if (imageData.equals("aadharback")) {
                                if (response.code() == 200) {
                                    String address1 = adharDataModel.getAddress1();
                                    Log.d("TAG", "onResponse:(address1) " + address1);
                                    if (address1 != null) {
                                        String[] addressParts = address1.split("[,-]");
                                        if (addressParts.length >= 1) {
                                            StringBuilder address1Builder = new StringBuilder();
                                            StringBuilder address2Builder = new StringBuilder();
                                            StringBuilder address3Builder = new StringBuilder();

                                            for (int i = 0; i < addressParts.length; i++) {
                                                String trimmedPart = addressParts[i].trim();
                                                if (i < addressParts.length - 3) {
                                                    if (i < 3) {
                                                        address1Builder.append(trimmedPart).append(" ");
                                                    } else if (i >= 3 && i <= 5) {
                                                        address2Builder.append(trimmedPart).append(" ");
                                                    } else if (i > 5 && i < addressParts.length - 3) {
                                                        address3Builder.append(trimmedPart).append(" ");
                                                    }
                                                }
                                            }
                                            editTextAddress1.setText(address1Builder.toString().trim());
                                            editTextAddress2.setText(address2Builder.toString().trim());
                                            editTextAddress3.setText(address3Builder.toString().trim());
                                        }
                                    }

                                    String guardian = adharDataModel.getGuardianName();
                                    editTextGuardian.setText(guardian);

                                    String CityName = adharDataModel.getCityName();
                                    editTextCity.setText(CityName);

                                    String Pincode = adharDataModel.getPincode();
                                    editTextPincode.setText(Pincode);

                                    String StateName = adharDataModel.getStateName();
                                    for (int i = 0; i < acspAadharState.getAdapter().getCount(); i++) {
                                        RangeCategoryDataModel rangeCategory = (RangeCategoryDataModel) acspAadharState.getAdapter().getItem(i);
                                        if (rangeCategory.getDescriptionEn().equals(StateName)) {
                                            acspAadharState.setSelection(i);
                                            break;
                                        }
                                    }
                                    String Relation = adharDataModel.getRelation();
                                    for (int i = 0; i < acspRelationship.getAdapter().getCount(); i++) {
                                        RangeCategoryDataModel rangeCategory = (RangeCategoryDataModel) acspRelationship.getAdapter().getItem(i);

                                        if (rangeCategory.getDescriptionEn().equals(Relation)) {
                                            acspRelationship.setSelection(i);
                                            break;
                                        }
                                    }
//                                             if(Relation.equalsIgnoreCase("Husband")){
//                                                 acspRelationship.setSelection(3);
//                                             } else if (Relation.equalsIgnoreCase("Father")) {
//                                                 acspRelationship.setSelection(2);
//                                             }else {
//                                                 acspRelationship.setSelection(1);
//                                             }
                                    Log.d("TAG", "onResponse(relation): " + Relation);
                                    String Relation1 = (String) adharDataModel.getRelation();
                                    if (Relation1 != null) {
                                        if (Relation1.equals("Father")) {
                                            String guardian1 = adharDataModel.getGuardianName();

                                            editTextGuardian.setText(guardian1);

                                            if (guardian1 != null) {
                                                String[] nameParts = guardian1.split(" ");
                                                if (nameParts.length >= 1) {
                                                    editTextFatherFname.setText(nameParts[0]);
                                                }
                                                if (nameParts.length >= 2) {
                                                    if (nameParts.length == 2) {
                                                        editTextFatherFname.setText(nameParts[0]);
                                                        editTextfatherlastname.setText(nameParts[1]);
                                                    } else {
                                                        StringBuilder middleNameBuilder = new StringBuilder();
                                                        for (int i = 1; i < nameParts.length - 1; i++) {
                                                            middleNameBuilder.append(nameParts[i]).append(" ");
                                                        }
                                                        editTextfathermiddlename.setText(middleNameBuilder.toString().trim());
                                                    }
                                                }
                                                if (nameParts.length >= 3) {
                                                    editTextfatherlastname.setText(nameParts[nameParts.length - 1]);
                                                }
                                            }
                                        } else if (Relation1.equals("Husband")) {
                                            String guardian2 = adharDataModel.getGuardianName();
                                            if (guardian2 != null) {
                                                String[] nameParts1 = guardian2.split(" ");
                                                if (nameParts1.length >= 1) {
                                                    editTextspousefirstname.setText(nameParts1[0]);
                                                }
                                                if (nameParts1.length >= 2) {
                                                    if (nameParts1.length == 2) {
                                                        editTextspousefirstname.setText(nameParts1[0]);
                                                        editTextspouselastname.setText(nameParts1[1]);
                                                    } else {
                                                        StringBuilder middleNameBuilder = new StringBuilder();
                                                        for (int i = 1; i < nameParts1.length - 1; i++) {
                                                            middleNameBuilder.append(nameParts1[i]).append(" ");
                                                        }
                                                        editTextspousemiddlename.setText(middleNameBuilder.toString().trim());
                                                    }
                                                }
                                                if (nameParts1.length >= 3) {
                                                    editTextspouselastname.setText(nameParts1[nameParts1.length - 1]);
                                                }
                                            }
                                        }
                                        progressBar.dismiss();
                                    }
                            //        borrower.isAadharVerified = "O";

                                } else {
                                    Utils.alert(KYCActivity.this, "Please capture aadhaar back image!!");

                                }

                                progressBar.dismiss();

                            } else if (imageData.equals("pan")) {
                                if (response.code() == 200) {
                                    String panno = adharDataModel.getPanNo().toString();
                                    Log.d("TAG", "onResponse: panno => " + panno);
                                    editTextPAN.setText(panno);
                                //    isgetPanwithOCR = true;
                                  //  borrower.isAadharVerified = "O";
                                  //  panaadharDOBMatched = true;

                                } else {
                                    Toast.makeText(KYCActivity.this, "Please capture PAN Card on behalf sample", Toast.LENGTH_SHORT).show();
                                    Utils.alert(KYCActivity.this, "Please capture PAN image again!!");
                                }
                            }
                            progressBar.dismiss();
                        } else {
                            progressBar.dismiss();
                            if (imageData.equals("aadharfront")) {
                                Utils.alert(KYCActivity.this, "Failed to fetch Aadhaar front image data. Please make sure the image is clear and try again.");
                                Toast.makeText(KYCActivity.this, "Failed to fetch Aadhaar front image data. Please make sure the image is clear and try again.", Toast.LENGTH_SHORT).show();
                            } else if (imageData.equals("aadharback")) {
                                Utils.alert(KYCActivity.this, "Failed to fetch Aadhaar back image data. Please make sure the image is clear and try again.");
                                Toast.makeText(KYCActivity.this, "Failed to fetch Aadhaar back image data. Please make sure the image is clear and try again.", Toast.LENGTH_SHORT).show();
                            } else if (imageData.equals("pan")) {
                                Utils.alert(KYCActivity.this, "Failed to fetch pan image data. Please make sure the image is clear and try again.");
                                Toast.makeText(KYCActivity.this, "Failed to fetch pan image data. Please make sure the image is clear and try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

            }

            public void onFailure(Call<AdharDataResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                progressBar.dismiss();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: "+data+""+ requestCode);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            //Log.d("QR Scan","Executed");
            if (scanningResult != null) {
                //we have a result
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                Log.d("CheckXMLDATA3","AadharData:->" + scanContent);
                if (scanFormat != null) {
                    try {
                        setAadharContent(scanContent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }else  if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cropImage(imageBitmap);
        } else if (requestCode == REQUEST_IMAGE_CROP && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap croppedBitmap = extras.getParcelable("data");
                bitmap = croppedBitmap;
                profileImageFile=bitmapToFile(bitmap);
            }
        }else  if (requestCode == REQUEST_ADHAARFRONT_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cropImage(imageBitmap);
        } else if (requestCode == REQUEST_ADHAARFRONT_CROP && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap croppedBitmap = extras.getParcelable("data");
                bitmap = croppedBitmap;
                adhaarFrontFile=bitmapToFile(bitmap);
                setDataOfAdhar(adhaarFrontFile,"aadharfront");
            }
        }else  if (requestCode == REQUEST_ADHAARBACK_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cropImage(imageBitmap);
        } else if (requestCode == REQUEST_ADHAARBACK_CROP && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap croppedBitmap = extras.getParcelable("data");
                bitmap = croppedBitmap;
                adhaarBackFile = bitmapToFile(bitmap);
                setDataOfAdhar(adhaarBackFile,"aadharback");
            }
        }else  if (requestCode == REQUEST_PAN_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cropImage(imageBitmap);
        } else if (requestCode == REQUEST_PAN_CROP && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap croppedBitmap = extras.getParcelable("data");
                bitmap = croppedBitmap;
                panFile = bitmapToFile(bitmap);
                setDataOfAdhar(panFile,"pan");
            }
        }
    }

    public File bitmapToFile(Bitmap bitmap) {
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "profile_pictures");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = "profile_picture.png";
        File file = new File(directory, fileName);
        this.file = file;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    private void cropImage(Bitmap bitmap) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(getImageUri(bitmap), "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

    private Uri getImageUri(Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        if (path != null) {
            return Uri.parse(path);
        } else {

            return null;
        }
    }

    private void setAadharContent(String aadharDataString) throws Exception {

        if (aadharDataString.toUpperCase().contains("XML"))
        {

            Log.d("XML printing", "AadharData:->" + aadharDataString);
            //AadharData aadharData = AadharUtils.getAadhar(aadharDataString);
            AadharData aadharData = AadharUtils.getAadhar(AadharUtils.ParseAadhar(aadharDataString));

            Log.d("TAG", "setAadharContent: "+aadharData.isAadharVerified);
            if (aadharData.AadharId != null) {
            }

            if (aadharData.Address2 == null) {
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            } else if (aadharData.Address2.trim().equals("")) {
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            }
            if (aadharData.Address1 == null) {
                aadharData.Address1 = aadharData.Address2;
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            } else if (aadharData.Address1.trim().equals("")) {
                aadharData.Address1 = aadharData.Address2;
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            }

        }
        else {

            final BigInteger bigIntScanData = new BigInteger(aadharDataString, 10);
            Log.e("testbigin======", "AadharData:->" + bigIntScanData);
            // 2. Convert BigInt to Byte Array
            final byte byteScanData[] = bigIntScanData.toByteArray();

            // 3. Decompress Byte Array
            final byte[] decompByteScanData = decompressData(byteScanData);

            // 4. Split the byte array using delimiter
            List<byte[]> parts = separateData(decompByteScanData);
            // Throw error if there are no parts
            Log.e("Parts======11======> ", "part data =====> " + parts.toString());
            decodeData(parts);
            decodeSignature(decompByteScanData);
            decodeMobileEmail(decompByteScanData);
            aadharNumberentry=true;
        }
    }

    protected void decodeMobileEmail(byte[] decompressedData){
        int mobileStartIndex = 0,mobileEndIndex = 0,emailStartIndex = 0,emailEndIndex = 0;
        switch (emailMobilePresent){
            case 3:
                mobileStartIndex = decompressedData.length - 289; // length -1 -256 -32
                mobileEndIndex = decompressedData.length - 257; // length -1 -256
                emailStartIndex = decompressedData.length - 322;// length -1 -256 -32 -1 -32
                emailEndIndex = decompressedData.length - 290;// length -1 -256 -32 -1

                mobile = bytesToHex (Arrays.copyOfRange(decompressedData,mobileStartIndex,mobileEndIndex+1));
                email = bytesToHex (Arrays.copyOfRange(decompressedData,emailStartIndex,emailEndIndex+1));
                imageEndIndex = decompressedData.length - 323;
                break;

            case 2:
                // only mobile
                email = "";
                mobileStartIndex = decompressedData.length - 289; // length -1 -256 -32
                mobileEndIndex = decompressedData.length - 257; // length -1 -256

                mobile = bytesToHex (Arrays.copyOfRange(decompressedData,mobileStartIndex,mobileEndIndex+1));
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 290;

                break;

            case 1:
                // only email
                mobile = "";
                emailStartIndex = decompressedData.length - 289; // length -1 -256 -32
                emailEndIndex = decompressedData.length - 257; // length -1 -256

                email = bytesToHex (Arrays.copyOfRange(decompressedData,emailStartIndex,emailEndIndex+1));
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 290;
                break;

            default:
                // no mobile or email
                mobile = "";
                email = "";
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 257;
        }

        Log.e("email mobile======> ","Data=====>"+email +"   "+mobile);

    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();

        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    protected void decodeSignature(byte[] decompressedData){
        // extract 256 bytes from the end of the byte array
        int startIndex = decompressedData.length - 257,
                noOfBytes = 256;
        try {
            signature = new String (decompressedData,startIndex,noOfBytes,"ISO-8859-1");
            Log.e("signature======>","signature===> "+signature);
        } catch (UnsupportedEncodingException e) {
            Log.e("Exception", "Decoding Signature of QRcode, ISO-8859-1 not supported: " + e.toString());
            // throw new QrCodeException("Decoding Signature of QRcode, ISO-8859-1 not supported",e);
        }

    }

    protected void decodeData(List<byte[]> encodedData) throws ParseException {
        Iterator<byte[]> i = encodedData.iterator();
        decodedData = new ArrayList<String>();
        while(i.hasNext()){
            decodedData.add(new String(i.next(), StandardCharsets.ISO_8859_1));
        }
        Log.e("Parts======2======> ","part data =====> "+decodedData.toString());

        Log.d("part data =====> ","Parts======1======> "+decodedData.get(1));
        Log.d("part data =====> ","Parts======2======> "+decodedData.get(2));
        Log.d("part data =====> ","Parts======3======> "+decodedData.get(3));
        Log.d("part data =====> ","Parts======4======> "+decodedData.get(4));
        Log.d("part data =====> ","Parts======5======> "+decodedData.get(5));
        Log.d("part data =====> ","Parts======6======> "+decodedData.get(6));
        Log.d("part data =====> ","Parts======7======> "+decodedData.get(7));
        Log.d("part data =====> ","Parts======8======> "+decodedData.get(8));
        Log.d("part data =====> ","Parts======9=====> "+decodedData.get(9));
        Log.d("part data =====> ","Parts======10=====> "+decodedData.get(10));
        Log.d("part data =====> ","Parts======11=====> "+decodedData.get(11));
        Log.d("part data =====> ","Parts======12=====> "+decodedData.get(12));
        Log.d("part data =====> ","Parts======13=====> "+decodedData.get(13));
        Log.d("part data =====> ","Parts======14=====> "+decodedData.get(14));
        Log.d("part data =====> ","Parts======15=====> "+decodedData.get(15));

        int inc=0;
        Log.d("TAG", "decodeData: "+decodedData.get(0).startsWith("V")+"/////"+decodedData.get(0));
        if (decodedData.get(0).startsWith("V")){
            inc=0;
        }else {
            inc=1;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Log.d("TAG", "decodeData: "+decodedData.get(4-inc));
        Date date = formatter.parse(decodedData.get(4-inc));
        Log.d("TAG", "decodeData: "+date);


        isAadharVerified="Q";
        editTextAadhar.setText(decodedData.get(2-inc));

        if (decodedData.get(3-inc).equals("")||decodedData.get(3-inc).equals(null)){
            editTextName.setEnabled(true);
        }else{
            editTextName.setText(decodedData.get(3-inc));
            Log.d("TAG", "Parts======name=====>  " + editTextName.getText().toString());
        }
        if (decodedData.get(4-inc).equals("")||decodedData.get(4-inc).equals(null)){
            editTextDob.setEnabled(true);
            Log.d("TAG", "Parts======dobnull=====>  " + editTextDob.getText().toString());
        }else{
            editTextDob.setText(decodedData.get(4-inc));
            Log.d("TAG", "Parts======dob=====>  " + editTextDob.getText().toString());
        }
        if (decodedData.get(6-inc).equals("")||decodedData.get(6-inc).equals(null)){

        }else{
            if (decodedData.get(6-inc).startsWith("S/O:") ||decodedData.get(6-inc).startsWith("D/O:") ||decodedData.get(6-inc).startsWith("W/O:")){
                editTextGuardian.setText(decodedData.get(6-inc).split(":")[1].replace("S/O:","").replace("D/O:","").replace("W/O:","").replace("S/O","").replace("D/O","").replace("\"","").replace("W/O","").trim());
                if (decodedData.get(6-inc).toUpperCase().startsWith("W/O:")){
                  //  Utils.setSpinnerPosition(spinnerMarritalStatus, "Married", false);
                    String[] spouseName=decodedData.get(6-inc).split(" ");
                    switch (spouseName.length){
                        case 2:
                            editTextspousefirstname.setText(spouseName[1]);
                            break;
                        case 3:
                            editTextspousefirstname.setText(spouseName[1]);
                            editTextspouselastname.setText(spouseName[2]);
                            break;
                        case 4:
                            editTextspousefirstname.setText(spouseName[1]);
                            editTextspousemiddlename.setText(spouseName[2]);
                            editTextspouselastname.setText(spouseName[3]);
                            break;
                        default:
                            editTextmotherfirstname.setText(decodedData.get(6-inc));
                            break;
                    }

                }
            }else if (decodedData.get(6-inc).startsWith("S/O,") ||decodedData.get(6-inc).startsWith("D/O,") ||decodedData.get(6-inc).startsWith("W/O,")){
                editTextGuardian.setText(decodedData.get(6-inc).split(",")[1].trim());
                Log.d("TAG", "Parts======guardian=====>  " + editTextGuardian.getText().toString());

            }else{
                editTextGuardian.setText(decodedData.get(6-inc).replace("S/O:","").replace("D/O:","").replace("W/O:","").replace("S/O","").replace("D/O","").replace("W/O","").replace("\"",""));

            }
            if (decodedData.get(6-inc).startsWith("S/O:") ||decodedData.get(6-inc).startsWith("D/O:") ||decodedData.get(6-inc).startsWith("W/O:")){
                editTextGuardian.setText(decodedData.get(6-inc).split(":")[1].replace("S/O:","").replace("D/O:","").replace("W/O:","").replace("S/O","").replace("D/O","").replace("\"","").replace("W/O","").trim());
                if (decodedData.get(6-inc).toUpperCase().startsWith("W/O:")){
                    Utils.setSpinnerPosition(isMarriedSpinner, "Married", false);
                    String[] spouseName=decodedData.get(6-inc).split(" ");
                    switch (spouseName.length){
                        case 2:
                            editTextspousefirstname.setText(spouseName[1]);
                            break;
                        case 3:
                            editTextspousefirstname.setText(spouseName[1]);
                            editTextspouselastname.setText(spouseName[2]);
                            break;
                        case 4:
                            editTextspousefirstname.setText(spouseName[1]);
                            editTextspousemiddlename.setText(spouseName[2]);
                            editTextspouselastname.setText(spouseName[3]);
                            break;
                        default:
                            editTextspouselastname.setText(decodedData.get(6-inc));
                            break;
                    }

                }
            }else if (decodedData.get(6-inc).startsWith("S/O,") ||decodedData.get(6-inc).startsWith("D/O,") ||decodedData.get(6-inc).startsWith("W/O,")){
                editTextGuardian.setText(decodedData.get(6-inc).split(",")[1].trim());
            }else{
                editTextGuardian.setText(decodedData.get(6-inc).replace("S/O:","").replace("D/O:","").replace("W/O:","").replace("S/O","").replace("D/O","").replace("W/O","").replace("\"",""));

            }
            if (decodedData.get(6-inc).startsWith("S/O") ||decodedData.get(6-inc).startsWith("D/O")){
                Utils.setSpinnerPosition(acspRelationship, "Father", false);
                acspRelationship.setEnabled(false);
                String[] fatherNames =decodedData.get(6-inc).contains(":")?decodedData.get(6-inc).split(":"):decodedData.get(6-inc).split("/O");
                String[] newFatherName=fatherNames[1].split(" ");
                if (newFatherName.length>2){
                    String fatherFirstName="";
                    for (int a=1;a<newFatherName.length-1;a++){
                        fatherFirstName= fatherFirstName+" "+newFatherName[a];
                    }
                    editTextFatherFname.setText(fatherFirstName.trim());
                    Log.d("TAG", "Parts======fatherName=====>  " + editTextFatherFname.getText().toString());

                    editTextFatherFname.setEnabled(false);
                    editTextfatherlastname.setText(newFatherName[newFatherName.length-1].trim());
                    editTextfatherlastname.setEnabled(false);

                }else{
                    if (decodedData.get(6-inc).contains(":")){

                        editTextFatherFname.setText(decodedData.get(6-inc).split(":")[1].trim());
                        editTextFatherFname.setEnabled(false);
                    }else{
                        editTextFatherFname.setText(decodedData.get(6-inc).split("/O")[1].trim());
                        editTextFatherFname.setEnabled(false);
                    }

                }
            }else if (decodedData.get(6-inc).startsWith("W/O")){
                Utils.setSpinnerPosition(acspRelationship, "Husband", false);
                acspRelationship.setEnabled(false);
                String[] spouseNames =decodedData.get(6-inc).contains(":")?decodedData.get(6-inc).split(":"):decodedData.get(6-inc).split("/O");
                String[] newSpouseName=spouseNames[1].split(" ");
                if (newSpouseName.length>2){
                    String spouseFirstName="";
                    for (int a=1;a<newSpouseName.length-1;a++){
                        spouseFirstName= spouseFirstName+" "+newSpouseName[a];
                    }
                    editTextspousefirstname.setText(spouseFirstName.trim());
                    editTextspousefirstname.setEnabled(false);
                    editTextfatherlastname.setText(newSpouseName[newSpouseName.length-1].trim());
                    editTextspouselastname.setEnabled(false);

                }else{

                    if (decodedData.get(6-inc).contains(":")){
                        editTextspousefirstname.setText(decodedData.get(6-inc).split(":")[1].trim());
                        editTextspousefirstname.setEnabled(false);
                    }else{
                        editTextfatherlastname.setText(decodedData.get(6-inc).split("/O")[1].trim());
                        editTextfatherlastname.setEnabled(false);
                    }

                }

            }
        }

        if (decodedData.get(7-inc).equals("")||decodedData.get(7-inc).equals(null)){
             editTextCity.setEnabled(true);
        }else{
        editTextCity.setText(decodedData.get(7-inc));
        }

        if (decodedData.get(11-inc).equals("")||decodedData.get(11-inc).equals(null)){
        }else{
            editTextPincode.setText(decodedData.get(11-inc));
        }
        if (decodedData.get(13-inc).equals("")||decodedData.get(13-inc).equals(null)){
        }/*else{
            borrower.p_state = AadharUtils.getStateCode(decodedData.get(13-inc));
        }*/

        StringBuilder joinedStringBuilder = new StringBuilder();
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(8-inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(9-inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(10-inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(12-inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(14-inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(15-inc));

        String joinedString = joinedStringBuilder.toString();

        boolean changesMade;
        do {
            String previousString = joinedString;
            joinedString = joinedString.replaceAll(",+", ",");
            changesMade = !previousString.equals(joinedString);
        } while (changesMade);

        if(!joinedString.contains(",")){
            editTextAddress1.setText(joinedString);
        }else {
            String[] separatedStrings = joinedString.split(",");
            int totalStrings = separatedStrings.length;
            if(totalStrings < 3) {
                editTextAddress1.setText(concatenateStrings(separatedStrings, 0, 1));
                editTextAddress2.setText(concatenateStrings(separatedStrings, 1, 2));
            } else {
                int stringsInA = totalStrings / 3;
                int stringsInB = (totalStrings - stringsInA) / 2;
                int stringsInC = totalStrings - stringsInA - stringsInB;

                editTextAddress1.setText(concatenateStrings(separatedStrings, 0, stringsInA));
                editTextAddress2.setText(concatenateStrings(separatedStrings, stringsInA, stringsInA + stringsInB));
                editTextAddress3.setText(concatenateStrings(separatedStrings, stringsInA + stringsInB, totalStrings));
            }
        }

       /* editTextAge.setEnabled(false);
        editTextDob.setEnabled(false);
        acspAadharState.setEnabled(false);
        acspGender.setEnabled(false);*/

    }

    private String concatenateStrings(String[] strings, int startIndex, int endIndex) {
        StringBuilder result = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                result.append(",");
            }
            result.append(strings[i]);
        }
        return result.toString();
    }
    private void appendIfNotNullOrEmpty(StringBuilder stringBuilder, String str) {
        if (str != null && !str.isEmpty()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(str);
        }
    }
    protected byte[] decompressData(byte[] byteScanData) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(byteScanData.length);
        ByteArrayInputStream bin = new ByteArrayInputStream(byteScanData);
        GZIPInputStream gis = null;

        try {
            gis = new GZIPInputStream(bin);
        } catch (IOException e) {
            Log.e("Exception", "Decompressing QRcode, Opening byte stream failed: " + e.toString());
            // throw new QrCodeException("Error in opening Gzip byte stream while decompressing QRcode",e);
        }

        int size = 0;
        byte[] buf = new byte[1024];
        while (size >= 0) {
            try {
                size = gis.read(buf,0,buf.length);
                if(size > 0){
                    bos.write(buf,0,size);
                }
            } catch (IOException e) {
                Log.e("Exception", "Decompressing QRcode, writing byte stream failed: " + e.toString());
                // throw new QrCodeException("Error in writing byte stream while decompressing QRcode",e);
            }
        }

        try {
            gis.close();
            bin.close();
        } catch (IOException e) {
            Log.e("Exception", "Decompressing QRcode, closing byte stream failed: " + e.toString());
            // throw new QrCodeException("Error in closing byte stream while decompressing QRcode",e);
        }

        return bos.toByteArray();
    }
    protected List<byte[]> separateData(byte[] source) {
        List<byte[]> separatedParts = new LinkedList<>();
        int begin = 0;

        for (int i = 0; i < source.length; i++) {
            if(source[i] == SEPARATOR_BYTE){
                if(i != 0 && i != (source.length -1)){
                    separatedParts.add(Arrays.copyOfRange(source, begin, i));
                }
                begin = i + 1;
                if(separatedParts.size() == (   VTC_INDEX + 1)){
                    imageStartIndex = begin;
                    break;
                }
            }
        }
        return separatedParts;
    }
    private void dlVerification(String ID, String IDno,String dob) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DLVerificationModel> call = apiInterface.getDLVerified(GlobalClass.Token, BuildConfig.dbname,createJson(ID,IDno,dob));

        Log.d("TAG", "onResponseID: "+ GlobalClass.Token + "  "+ BuildConfig.dbname+"  "+createJson(ID,IDno,dob));
        call.enqueue(new Callback<DLVerificationModel>() {
            @Override
            public void onResponse(Call<DLVerificationModel> call, Response<DLVerificationModel> response) {
                Log.d("TAG", "onResponseID: "+response.body());
                if(response.isSuccessful()){
                    Log.d("TAG", "onResponseID: "+response.body().getData().getData().getData().getName());
                    DLVerificationModel dlVerificationModel  = response.body();
                    if(dlVerificationModel.getData().getData().getData().getName()!=null){
                        Log.d("TAG", "onResponseID: "+ dlVerificationModel.getData().getData().getData().getName());
                        Data_ data = dlVerificationModel.getData();
                        Data_1 data1= data.getData();
                        Data_2 data2= data1.getData();
                        verifiedLicensename = data2.getName();
                        dl_Checkbox.setChecked(true);
                        Toast.makeText(KYCActivity.this, "DL Verified", Toast.LENGTH_SHORT).show();
                        dl_Checkbox.setClickable(false);
                    }else{
                        dl_Checkbox.setChecked(false);
                        Log.d("TAG", "onResponseID: "+ dlVerificationModel.getData().getData().getData().getName());
                        Toast.makeText(KYCActivity.this, "Invalid Input DL", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d("TAG", "onResponseID: "+response.code());
                    dl_Checkbox.setChecked(false);
                    Toast.makeText(KYCActivity.this, "Invalid Input DL", Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void onFailure(Call<DLVerificationModel> call, Throwable t) {
                dl_Checkbox.setChecked(false);
                Toast.makeText(KYCActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void panVerification(String ID, String IDno,String dob) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PanVerificationModel> call = apiInterface.getpanVerified(GlobalClass.Token, BuildConfig.dbname,createJson(ID,IDno,dob));

        Log.d("TAG", "onResponseID: "+ GlobalClass.Token + "  "+ BuildConfig.dbname+"  "+createJson(ID,IDno,dob));
        call.enqueue(new Callback<PanVerificationModel>() {
            @Override
            public void onResponse(Call<PanVerificationModel> call, Response<PanVerificationModel> response) {
                Log.d("TAG", "onResponseID: "+response.body());
                if(response.isSuccessful()){
                    Log.d("TAG", "onResponseID: "+response.body());
                    PanVerificationModel panVerificationModel  = response.body();
                    if(panVerificationModel.getMessage().toString().equals("Get Record Successfully !!")){
                        pan_Checkbox.setChecked(true);
                        Toast.makeText(KYCActivity.this, "Pan Verified", Toast.LENGTH_SHORT).show();
                        verifiedPanName = panVerificationModel.getData().getData().getData().getName();
                        pan_Checkbox.setClickable(false);

                    }else{
                        Toast.makeText(KYCActivity.this, "Invalid Pan", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d("TAG", "onResponseID: "+response.code());
                    pan_Checkbox.setChecked(false);
                    Toast.makeText(KYCActivity.this, "Invalid Pan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PanVerificationModel> call, Throwable t) {
                Log.d("TAG", "onResponseID: "+ "Failure");
                pan_Checkbox.setChecked(false);
                Toast.makeText(KYCActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void voterIdVerification(String ID, String IDno,String dob) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VoterIdVerificationModel> call = apiInterface.getvoterIDVerified(GlobalClass.Token, BuildConfig.dbname,createJson(ID,IDno,dob));

        Log.d("TAG", "onResponseID: "+ GlobalClass.Token + "  "+ BuildConfig.dbname+"  "+createJson(ID,IDno,dob));
        call.enqueue(new Callback<VoterIdVerificationModel>() {
            @Override
            public void onResponse(Call<VoterIdVerificationModel> call, Response<VoterIdVerificationModel> response) {
                Log.d("TAG", "onResponseID: "+response.body());
                if(response.isSuccessful()){
                    Log.d("TAG", "onResponseID: "+response.body());
                    VoterIdVerificationModel voterIdVerificationModel  = response.body();
                    if(voterIdVerificationModel.getMessage().toString().equals("Get Record Successfully !!")){
                        voterId_Checkbox.setChecked(true);
                        Toast.makeText(KYCActivity.this, "ID Verified", Toast.LENGTH_SHORT).show();
                        verifiedVotername = voterIdVerificationModel.getData().getData().getData().getName();
                        voterId_Checkbox.setClickable(false);

                    }else{
                        Toast.makeText(KYCActivity.this, "Invalid Voter ID", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d("TAG", "onResponseID: "+response.code());
                    voterId_Checkbox.setChecked(false);
                    Toast.makeText(KYCActivity.this, "Invalid ID", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<VoterIdVerificationModel> call, Throwable t) {
                voterId_Checkbox.setChecked(false);
                Toast.makeText(KYCActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private JsonObject createJson(String id, String iDno, String dob) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", id);
        jsonObject.addProperty("txtnumber", iDno);
        jsonObject.addProperty("ifsc", "");
        jsonObject.addProperty("userdob", dob);
        jsonObject.addProperty("key", "1");
        return jsonObject;
    }
    private void createJsonObject() {

        AadharID = editTextAadhar.getText().toString();
        Age = editTextAge.getText().toString();
        name = editTextName.getText().toString();
        DOB = editTextDob.getText().toString();
        P_Add1 = editTextAddress1.getText().toString();
        P_Add2 = editTextAddress2.getText().toString();
        P_Add3 = editTextAddress3.getText().toString();
        P_City = editTextCity.getText().toString();
        P_Pin = Integer.parseInt(editTextPincode.getText().toString());
        P_Ph3 = editTextMobile.getText().toString();
        PanNO = editTextPAN.getText().toString();
        DrivingLic = editTextdrivingLicense.getText().toString();
        voterId = editTextvoterIdKyc.getText().toString();
        fatherName = editTextFatherFname.getText().toString();
        fatherMiddleName = editTextfathermiddlename.getText().toString();
        fatherLastName = editTextfatherlastname.getText().toString();
        motherName = editTextmotherfirstname.getText().toString();
        motherMiddleName = editTextmothermiddlename.getText().toString();
        motherLastName = editTextmotherlastname.getText().toString();
        spouseFirstName = editTextspousefirstname.getText().toString();
        spouseMiddleName = editTextspousemiddlename.getText().toString();
        spouseLastName = editTextspouselastname.getText().toString();
        isMarried = editTextmaritalstatus.getText().toString();
        guardianRelatnWithBorrower = acspRelationship.getSelectedItem().toString();
        P_State =  acspAadharState.getSelectedItem().toString();
        guardian = editTextGuardian.getText().toString();

        if(name != null) {
            String[] parts1 = name.split(" ");
            if (parts1.length == 1) {
                Fname = parts1[0];
            } else if (parts1.length == 2) {
                Fname = parts1[0];
                Lname = parts1[1];
            }
        }

        if(guardian != null) {
            String[] parts = guardian.split(" ");
            if (parts.length == 1) {
                F_Fname = parts[0];
            } else if (parts.length == 2) {
                F_Fname = parts[0];
                F_Lname = parts[1];
            } else if (parts.length > 2) {
                F_Fname = parts[0];
                F_Mname = parts[1];
                StringBuilder restBuilder = new StringBuilder();
                for (int i = 2; i < parts.length; i++) {
                    restBuilder.append(parts[i]);
                    if (i < parts.length - 1) {
                        restBuilder.append(" ");
                    }
                }
                F_Lname = restBuilder.toString();
            }
        }

        FiJsonObject jsonData = new FiJsonObject();
            jsonData.setAadharID(AadharID);
            jsonData.setAge(Age);
            jsonData.setFname(Fname);
            jsonData.setLname(Lname);
            jsonData.setDob(DOB);
            jsonData.setPAdd1(P_Add1);
            jsonData.setPAdd2(P_Add2);
            jsonData.setPAdd3(P_Add3);
            jsonData.setPCity(P_City);
            jsonData.setPPin(P_Pin);
            jsonData.setPPh3(P_Ph3);
            jsonData.setVoterId(voterId);
            jsonData.setPanNO(PanNO);
            jsonData.setDrivingLic(DrivingLic);
            jsonData.setFFname(F_Fname);
            jsonData.setFMname(F_Mname);
            jsonData.setFLname(F_Lname);
            jsonData.setIsMarried(isMarried);
            jsonData.setGender(acspGender.getSelectedItem().toString());
            jsonData.setPState(P_State);
            jsonData.setGuardianRelatnWithBorrower(guardianRelatnWithBorrower);


        FiExtra fiExtra = new FiExtra();

        fiExtra.setMotherName(motherName);
        fiExtra.setMotherMiddleName(motherMiddleName);
        fiExtra.setMotherLastName(motherLastName);
        fiExtra.setSpouseFirstName(spouseFirstName);
        fiExtra.setSpouseMiddleName(spouseMiddleName);
        fiExtra.setSpouseLastName(spouseLastName);
        fiExtra.setFatherName(fatherName);
        fiExtra.setFatherMiddleName(fatherMiddleName);
        fiExtra.setFatherLastName(fatherLastName);

        Log.d("TAG", "jsonobjectstate " + villageCode +" villageCode "+ distCode +" distCode "+ subDistCode + " subDistCode " + cityCode+" cityCode " + name + " name " + verifiedLicensename + " verifiedLicensename " + verifiedVotername + " verifiedVotername " + verifiedPanName +" verifiedPanName ");

        Log.d("TAG", "onResponsepp: "+ "Started");
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.CheckLoanByAadhar(GlobalClass.Token,BuildConfig.dbname,AadharID);
        Log.d("TAG", "onResponsepp: "+ AadharID);

        call.enqueue(new Callback<JsonObject>() {
                         @Override
                         public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                             Log.d("TAG", "onResponsepp: "+ response.body());
                             if(response.isSuccessful()){
                                 Log.d("TAG", "onResponsepp: "+ response.body());
                                 Intent intent = new Intent(KYCActivity.this, KYCActivity2.class);
                                 intent.putExtra("jsonData",jsonData);
                                 intent.putExtra("fiExtra",fiExtra);
                                 intent.putExtra("vName",name);
                                 intent.putExtra("vPanName",verifiedPanName);
                                 intent.putExtra("vVoterIdName",verifiedVotername);
                                 intent.putExtra("vLicName",verifiedLicensename);
                                 intent.putExtra("cityCode",cityCode);
                                 intent.putExtra("distCode",distCode);
                                 intent.putExtra("subDistCode",subDistCode);
                                 intent.putExtra("villageCode",villageCode);
                                 intent.putExtra("stateCode",stateCode);
                                 intent.putExtra("file",file.getAbsolutePath());

                                 startActivity(intent);
                             }else{
                                 Log.d("TAG", "onResponsepp: "+ response.code());
                             }

                         }

                         @Override
                         public void onFailure(Call<JsonObject> call, Throwable t) {
                             Log.d("TAG", "onResponsepp: "+ "failure");

                         }
                     });
    }
    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(KYCActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                editTextDob.setText(selectedDate);
                calculateAge(selectedYear, selectedMonth, selectedDay);
                progressBar.incrementProgressBy(1);
            }
        }, year, month, day);

        datePickerDialog.show();
    }
    private void calculateAge(int selectedYear, int selectedMonth, int selectedDay) {
        Calendar dobCalendar = Calendar.getInstance();
        dobCalendar.set(selectedYear, selectedMonth, selectedDay);

        Calendar currentCalendar = Calendar.getInstance();

        int age = currentCalendar.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);

        if (currentCalendar.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        EditText ageEditText = findViewById(R.id.editTextAgeKYC);
        ageEditText.setText(String.valueOf(age));
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }
    public void filterCity(String s) {
        Log.d("TAG", "filter: "+s);
        List<CityData> cityData1=new ArrayList<>();
        for (int i = 0; i < cityDataList.size(); i++) {

            if (cityDataList.get(i).getCitYNAME().toUpperCase().contains(s.toString())) {
                cityData1.add(cityDataList.get(i));
                //   Log.d("TAG", "filter: "+list.get(i));
            }
        }
        cityListAdapter.filterList(cityData1);
        cityListAdapter.notifyDataSetChanged();
    }
    public void filterDistrict(String s) {
        Log.d("TAG", "filter: "+s);
        List<DistrictData> districtDataModel=new ArrayList<>();
        for (int i = 0; i < districtDataList.size(); i++) {

            if (districtDataList.get(i).getDisTNAME().toUpperCase().contains(s.toString())) {
                districtDataModel.add(districtDataList.get(i));
                //   Log.d("TAG", "filter: "+list.get(i));
            }
        }
        districtListAdapter.filterList(districtDataModel);
        districtListAdapter.notifyDataSetChanged();
    }
    public void filterSubDistrict(String s) {
        Log.d("TAG", "filter: "+s);
        List<SubDistrictData> subDistrictDataModel=new ArrayList<>();
        for (int i = 0; i < subDistrictDataList.size(); i++) {

            if (subDistrictDataList.get(i).getSuBDISTNAME().toUpperCase().contains(s.toString())) {
                subDistrictDataModel.add(subDistrictDataList.get(i));
                //   Log.d("TAG", "filter: "+list.get(i));
            }
        }
        subDistrictListAdapter.filterList(subDistrictDataModel);
        subDistrictListAdapter.notifyDataSetChanged();
    }
    public void filterVillage(String s) {
        Log.d("TAG", "filter: "+s);
        List<VillageData> villageDataModel=new ArrayList<>();
        for (int i = 0; i < villageDataList.size(); i++) {

            if (villageDataList.get(i).getVillagENAME().toUpperCase().contains(s.toString())) {
                villageDataModel.add(villageDataList.get(i));
                //   Log.d("TAG", "filter: "+list.get(i));
            }
        }
        villageListAdapter.filterList(villageDataModel);
        villageListAdapter.notifyDataSetChanged();
    }
    private void showCityDialog(TextView txtCityName, String stateCode ) {
        customProgressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Dialog  dialogSearch=new Dialog(KYCActivity.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.setCancelable(false);
        dialogSearch.getWindow().setLayout(1000,1600);
        EditText edit_text=dialogSearch.findViewById(R.id.edit_text);
        TextView dialog_name=dialogSearch.findViewById(R.id.dialog_name);
        dialog_name.setText("Select City");
        RecyclerView recViewOfCreator=dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //   Log.d("TAG", "onDismiss: hiiitt"+choosedCreator);

                    Log.d("TAG", "onDismiss: "+cityData.getCitYNAME());
                    txtCityName.setText(cityData.getCitYNAME());
                    cityCode = cityData.getCitYCODE().toString();
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        Call<CityModelList> call=apiInterface.getCityList(GlobalClass.Token,GlobalClass.dbname,stateCode);
        call.enqueue(new Callback<CityModelList>() {
            @Override
            public void onResponse(Call<CityModelList> call, Response<CityModelList> response) {
                if (cityDataList.size()>1)
                    cityDataList.clear();
                cityDataList.addAll(response.body().getData());
                cityListAdapter=new CityListAdapter(KYCActivity.this, cityDataList,dialogSearch,cityChooseListner);
                recViewOfCreator.setAdapter(cityListAdapter);
                cityListAdapter.notifyDataSetChanged();
                if (cityDataList.size()>=1){
                    dialogSearch.show();
                }else{
                    Toast.makeText(KYCActivity.this, "Village List is not coming for "+stateCode, Toast.LENGTH_SHORT).show();
                }
                customProgressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<CityModelList> call, Throwable t) {
                customProgressDialog.dismiss();
            }
        });

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filterCity(s.toString().toUpperCase());
            }
        });

    }
    private void showDistrictDialog(TextView txtVVillageName, String districtCode ) {
        customProgressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Dialog  dialogSearch=new Dialog(KYCActivity.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.setCancelable(false);
        dialogSearch.getWindow().setLayout(1000,1600);
        EditText edit_text=dialogSearch.findViewById(R.id.edit_text);
        TextView dialog_name=dialogSearch.findViewById(R.id.dialog_name);
        dialog_name.setText("Select District");
        RecyclerView recViewOfCreator=dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //   Log.d("TAG", "onDismiss: hiiitt"+choosedCreator);
                try {
                    txtVVillageName.setText(districtDat.getDisTNAME());
                    distCode= districtDat.getDisTCODE().toString();
                }catch (NullPointerException e){

                }
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

            Call<DistrictListModel> call=apiInterface.getDistictList(GlobalClass.Token,GlobalClass.dbname,districtCode);
        call.enqueue(new Callback<DistrictListModel>() {
            @Override
            public void onResponse(Call<DistrictListModel> call, Response<DistrictListModel> response) {
                Log.d("TAG", "districtDataList: "+ response.body());
                if (response.isSuccessful()) {
                    if (districtDataList.size() > 1)
                        districtDataList.clear();
                    districtDataList.addAll(response.body().getData());
                    districtListAdapter = new DistrictListAdapter(KYCActivity.this, districtDataList, dialogSearch, listDistictInteraction);
                    recViewOfCreator.setAdapter(districtListAdapter);
                    districtListAdapter.notifyDataSetChanged();
                    if (districtDataList.size() >= 1) {
                        dialogSearch.show();
                    } else {
                        Toast.makeText(KYCActivity.this, "district list is not coming for " + districtCode, Toast.LENGTH_SHORT).show();
                    }
                    customProgressDialog.dismiss();
                }else{
                    customProgressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<DistrictListModel> call, Throwable t) {
                customProgressDialog.dismiss();
            }
        });

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filterDistrict(s.toString().toUpperCase());
            }
        });
    }
    private void showSubDistrictDialog(TextView txtVVillageName, String districtCode ) {
        customProgressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Dialog  dialogSearch=new Dialog(KYCActivity.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.setCancelable(false);
        dialogSearch.getWindow().setLayout(1000,1600);
        EditText edit_text=dialogSearch.findViewById(R.id.edit_text);
        TextView dialog_name=dialogSearch.findViewById(R.id.dialog_name);
        dialog_name.setText("Select Sub-District");
        RecyclerView recViewOfCreator=dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //   Log.d("TAG", "onDismiss: hiiitt"+choosedCreator);

                try {
                    txtVVillageName.setText(subDistrictData.getSuBDISTNAME());
                    subDistCode = subDistrictData.getSuBDISTCODE().toString();
                }catch (NullPointerException e){

                }
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        Call<SubDistrictModel> call=apiInterface.getSubDistrictList(GlobalClass.Token,GlobalClass.dbname,districtCode);
        call.enqueue(new Callback<SubDistrictModel>() {
            @Override
            public void onResponse(Call<SubDistrictModel> call, Response<SubDistrictModel> response) {
                if (subDistrictDataList.size()>1)
                    subDistrictDataList.clear();
                try {
                    subDistrictDataList.addAll(response.body().getData());
                    SubDistrictData other=   new SubDistrictData("2222222222","Other","22222");
                    subDistrictDataList.add(other);
                    subDistrictListAdapter=new SubDistrictListAdapter(KYCActivity.this, subDistrictDataList,dialogSearch,listSubDistructInteraction);
                    recViewOfCreator.setAdapter(subDistrictListAdapter);
                    subDistrictListAdapter.notifyDataSetChanged();
                    if (subDistrictDataList.size()>=1){
                        dialogSearch.show();
                    }else{
                        Toast.makeText(KYCActivity.this, "district list is not coming for "+districtCode, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception c){
                    SubDistrictData other=   new SubDistrictData("2222222222","Other","22222");
                    subDistrictDataList.add(other);
                }
                customProgressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<SubDistrictModel> call, Throwable t) {
                SubDistrictData other=   new SubDistrictData("2222222222","Other","22222");
                subDistrictDataList.add(other);
                customProgressDialog.dismiss();
            }
        });

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filterSubDistrict(s.toString().toUpperCase());
            }
        });
    }
    private void showVillageDialog(TextView txtVDistrictName, String stateCode, String disTCODE, String suBDISTCODE) {

        customProgressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Dialog  dialogSearch=new Dialog(KYCActivity.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.setCancelable(false);
        dialogSearch.getWindow().setLayout(1000,1600);
        EditText edit_text=dialogSearch.findViewById(R.id.edit_text);
        TextView dialog_name=dialogSearch.findViewById(R.id.dialog_name);
        dialog_name.setText("Select Village");
        RecyclerView recViewOfCreator=dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                try {
                    Log.d("TAG", "onDismiss: "+villageData.getVillagENAME());
                    txtVDistrictName.setText(villageData.getVillagENAME());
                    villageCode = villageData.getVillagECODE().toString();
                }catch (NullPointerException e){
                }
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        Call<VillageListModel> call=apiInterface.getVillageList(GlobalClass.Token,GlobalClass.dbname,stateCode,disTCODE,suBDISTCODE);
        call.enqueue(new Callback<VillageListModel>() {
            @Override
            public void onResponse(Call<VillageListModel> call, Response<VillageListModel> response) {
                if (villageDataList.size()>1)
                    villageDataList.clear();
                try {
                    villageDataList.addAll(response.body().getData());
                    VillageData other=new VillageData("2222222222222222","Other", "000", "0000", "0000");
                    villageDataList.add(other);
                    villageListAdapter=new VillageListAdapter(KYCActivity.this, villageDataList,dialogSearch,listVillageInteraction);
                    recViewOfCreator.setAdapter(villageListAdapter);
                    villageListAdapter.notifyDataSetChanged();
                    if (villageDataList.size()>=1){
                        dialogSearch.show();
                    }else{
                        Toast.makeText(KYCActivity.this, "district list is not coming", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    VillageData other=new VillageData("2222222222222222","Other", "000", "0000", "0000");
                    villageDataList.add(other);
                }
                customProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<VillageListModel> call, Throwable t) {
                VillageData other=new VillageData("2222222222222222","Other", "000", "0000", "0000");
                villageDataList.add(other);
                customProgressDialog.dismiss();
            }
        });

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterVillage(s.toString().toUpperCase());
            }
        });

    }
    @Override
    public void DistrictChooseListner(DistrictData districtDatas) {
        districtDat=districtDatas;
    }
    @Override
    public void VillageChooseListner(VillageData villageDatas) {
        villageData=villageDatas;
    }
    @Override
    public void CityChooseListner(CityData cityDatas) {
        cityData=cityDatas;
    }
    @Override
    public void SubDistChooseListner(SubDistrictData subDistrictDatas) {
        subDistrictData=subDistrictDatas;

    }
}
