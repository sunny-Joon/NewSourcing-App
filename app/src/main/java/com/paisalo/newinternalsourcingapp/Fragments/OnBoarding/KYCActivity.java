package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isNumber;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidAddr;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidFullName;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidMName;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidName;
import static com.paisalo.newinternalsourcingapp.GlobalClass.isValidSAddr;
import static com.paisalo.newinternalsourcingapp.GlobalClass.validateVerhoeff;

import static cz.msebera.android.httpclient.client.utils.DateUtils.formatDate;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.paisalo.newinternalsourcingapp.Activities.CameraActivity;
import com.paisalo.newinternalsourcingapp.Activities.LoginActivity;
import com.paisalo.newinternalsourcingapp.Adapters.CityListAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.CustomSpinnerAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.DistrictListAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.RangeCategoryAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.SubDistrictListAdapter;
import com.paisalo.newinternalsourcingapp.Adapters.VillageListAdapter;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.Entities.CkycNoMODEL;
import com.paisalo.newinternalsourcingapp.Entities.ScanAadhaar.AadharData;
import com.paisalo.newinternalsourcingapp.Entities.CityChooseListner;
import com.paisalo.newinternalsourcingapp.Entities.DistrictChooseListner;
import com.paisalo.newinternalsourcingapp.Entities.ScanAadhaar.AadharUtils;
import com.paisalo.newinternalsourcingapp.Entities.SubDistChooseListner;
import com.paisalo.newinternalsourcingapp.Entities.VillageChooseListner;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.Modelclasses.AdharExitsModel;
import com.paisalo.newinternalsourcingapp.Modelclasses.FiExtra;
import com.paisalo.newinternalsourcingapp.Modelclasses.FiJsonObject;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.PANerificationModels.PanVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.DLVerificationModels.DLVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.IdVerificationModels.VoterIdVerificationModels.VoterIdVerificationModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.OCRScanModels.AdharDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.OCRScanModels.AdharDataResponse;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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

public class KYCActivity extends AppCompatActivity implements VillageChooseListner, DistrictChooseListner, CityChooseListner, SubDistChooseListner {

    int ckycNumberExist = 0, otpVerified = 0;
    private AlertDialog alertDialog;

    String isNameMatched = "0";

    boolean allConditionsSatisfied = true;

    EditText editTextAadhar;
    EditText editTextName;
    EditText editTextAge;
    public EditText editTextDob;
    EditText editTextGuardian;
    EditText editTextAddress1;
    EditText editTextAddress2;
    EditText editTextAddress3;
    EditText editTextCity;
    EditText editTextPincode;
    EditText editTextMobile;
    EditText editTextPAN;
    EditText editTextdrivingLicense;
    EditText editTextvoterIdKyc;
    EditText editTextmotherfirstname;
    EditText editTextmothermiddlename;
    EditText editTextmotherlastname;
    EditText editTextFatherFname;
    EditText editTextfathermiddlename;
    EditText editTextfatherlastname;
    EditText editTextspousefirstname;
    EditText editTextspousemiddlename;
    EditText editTextspouselastname;

    TextView txtVDistrictName, txtCityName, txtVillageName, txtSubDistictName;
    TextView tilPanName, tilVoterName, tilDLName;

    String AadharID, name, Fname, Mname, Lname, Age, DOB, guardian, gender, guardianRelatnWithBorrower, P_Add1, P_Add2, P_Add3, P_City,
            P_State, P_Ph3, PanNO, DrivingLic, voterId, motherName, motherMiddleName, motherLastName, fatherName, fatherMiddleName, fatherLastName,
            F_Fname, F_Mname, F_Lname, isMarried, spouseFirstName, spouseMiddleName, spouseLastName, verifiedPanName = "", verifiedLicensename = "",
            verifiedVotername = "", villageCode, subDistCode, distCode, cityCode, stateCode, currentPhotoPathBefWork;

    int P_Pin;
    Button submitButton, qrScan, adhaarBack, adhaarFront, panOcr;
    private static final int REQUEST_IMAGE_CAPTURE = 1001;
    public static final int REQUEST_ADHAARFRONT_CAPTURE = 1002;
    public static final int REQUEST_ADHAARBACK_CAPTURE = 1003;
    private static final int REQUEST_PAN_CAPTURE = 1004;

    Bitmap bitmap;
    File file, profileImageFile, adhaarFrontFile, adhaarBackFile, panFile;
    CustomProgressDialog customProgressDialog;
    ImageView calendericon;
    LottieAnimationView aadhaarScanner, profilePic;
    ;

    List<VillageData> villageDataList = new ArrayList<>();
    List<CityData> cityDataList = new ArrayList<>();
    List<DistrictData> districtDataList = new ArrayList<>();
    List<SubDistrictData> subDistrictDataList = new ArrayList<>();
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

    List<RangeCategoryDataClass> stateDataList, maritalStatusList, relatnshipList, gendreDataList;
    List<String> RelationWithBorrowerList = new ArrayList<>();
    List<String> MaritalStatusList = new ArrayList<>();
    CardView spouseCardView;
    private ProgressBar progressBar;
    private int maxProgress = 26;

    private EditText[] editTexts;

    private Calendar calendar;
    Spinner acspGender, acspAadharState, acspRelationship, isMarriedSpinner;
    EditText otpEditText;
    CheckBox dl_Checkbox, pan_Checkbox, voterId_Checkbox, mobile_checkbox;
    protected static final byte SEPARATOR_BYTE = (byte) 255;
    protected static final int VTC_INDEX = 15;
    protected int imageStartIndex, emailMobilePresent, imageEndIndex;
    boolean aadharNumberentry = false;
    protected String signature, email, mobile;
    protected ArrayList<String> decodedData;
    FiExtra fiExtra;
    FiJsonObject jsonData;
    ScrollView scrollView;

    String foCode, creator, areaCode,SCHEME_TAG;
    int aadhar_status=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kycactivity);
        Log.d("TAG", "onCreate: " + GlobalClass.Creator);

        DatabaseClass databaseClass = DatabaseClass.getInstance(this);
        customProgressDialog = new CustomProgressDialog(this);

        Intent intent = getIntent();
        SCHEME_TAG = intent.getStringExtra("SCHEME_TAG");
        foCode = intent.getStringExtra("foCode");
        creator = intent.getStringExtra("creator");
        areaCode = intent.getStringExtra("areaCode");

        progressBar = findViewById(R.id.simpleProgressBar);
        progressBar.setMax(maxProgress);
        scrollView = findViewById(R.id.ScrollView);
        aadhaarScanner = findViewById(R.id.aadhaarScannerKyc);

        tilPanName = findViewById(R.id.tilPanName);
        tilDLName = findViewById(R.id.tilDLName);
        tilVoterName = findViewById(R.id.tilVoterName);

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

        txtVDistrictName = findViewById(R.id.txtVDistrictName);
        txtSubDistictName = findViewById(R.id.txtSubDistictName);
        txtVillageName = findViewById(R.id.txtVillageName);
        txtCityName = findViewById(R.id.txtCityName);

        dl_Checkbox = findViewById(R.id.DL_checkbox);
        voterId_Checkbox = findViewById(R.id.voterID_checkbox);
        pan_Checkbox = findViewById(R.id.pan_checkbox);
        mobile_checkbox = findViewById(R.id.mobile_checkbox);


        cityChooseListner = KYCActivity.this;
        listDistictInteraction = KYCActivity.this;
        listSubDistructInteraction = KYCActivity.this;
        listVillageInteraction = KYCActivity.this;

        acspGender = findViewById(R.id.acspGender);
        acspAadharState = findViewById(R.id.acspAadharStateKyc);
        acspRelationship = findViewById(R.id.acspRelationship);

        panOcr = findViewById(R.id.panOcr);
        spouseCardView = findViewById(R.id.spouseCardView);

        editTextDob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (!input.isEmpty()) {
                    editTextAge.setText(String.valueOf(GlobalClass.calculateAge(input)));
                }
            }
        });

        editTextName.setEnabled(false);

        //  Sample data
//        editTextAadhar.setText("123456789012");
//        editTextName.setText("John Doe");
//        editTextAge.setText("30");
//        editTextDob.setText("1999-05-23");
//        editTextGuardian.setText("Jane Doe");
//        editTextAddress1.setText("123, Street Name");
//        editTextAddress2.setText("Apartment Name");
//        editTextAddress3.setText("Area Name");
//        editTextCity.setText("City Name");
//        editTextPincode.setText("123456");
//        editTextMobile.setText("9876543210");
//        editTextPAN.setText("BKXPJ1310C");
//        editTextvoterIdKyc.setText("ZXD3104692");
//        editTextdrivingLicense.setText("DL0420170426232");
//        editTextvoterIdKyc.setText("");
//        editTextmotherfirstname.setText("Mary");
//        editTextmothermiddlename.setText("Anne");
//        editTextmotherlastname.setText("Doe");
////        editTextFatherFname.setText("Michael");
////        editTextfathermiddlename.setText("James");
////        editTextfatherlastname.setText("Doe");
////        isMarriedSpinner.setSelection(1);

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
                editTextfatherlastname, editTextspousefirstname, editTextspousemiddlename, editTextspouselastname
        };


        aadhaarScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlertDialog();
            }
        });

        panOcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (basicDetailsFound()) {
                    if (editTextPAN.getText().toString().trim().length() == 10) {

                        IsAaadharExits(new AadharStatusCallback() {
                            @Override
                            public void onResult(boolean exists) {
                                if (exists) {
                                    showKYCFailedDialog();
                                } else {
                                    Intent intent = new Intent(KYCActivity.this, CameraActivity.class);
                                    startActivityForResult(intent, REQUEST_PAN_CAPTURE);
                                }
                            }
                        });

                    } else {
                        tilPanName.setVisibility(View.GONE);
                        Toast.makeText(KYCActivity.this, "Enter PAN", Toast.LENGTH_SHORT).show();
                        tilPanName.setError("Enter PAN");
                    }

                } else {
                    Utils.alert(KYCActivity.this, "Please fill all basic details of borrower\nlike AadhaarId, Name,DOB,Gender etc then verify Voter ID");
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

                Intent intent = new Intent(KYCActivity.this, CameraActivity.class);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        GenderList.add("MALE");
        GenderList.add("FEMALE");
        GenderList.add("OTHERS");
        CustomSpinnerAdapter adapter1 = new CustomSpinnerAdapter(KYCActivity.this, android.R.layout.simple_spinner_dropdown_item, GenderList);
        acspGender.setAdapter(adapter1);

        stateDataList = new ArrayList<>();
        RangeCategoryDataClass rangeCategoryDataClass = new RangeCategoryDataClass("--Select--", "--Select--", "--Select--", "--Select--", "--Select--", 0, "99");
        stateDataList.add(rangeCategoryDataClass);
        stateDataList.addAll(databaseClass.dao().getAllRCDataListby_catKey("state"));
        for (RangeCategoryDataClass item : stateDataList) {
            Log.d("TAG", "decodeData item: " + item.getCode());
        }
        RangeCategoryAdapter rangeCategoryAdapter = new RangeCategoryAdapter(KYCActivity.this, android.R.layout.simple_spinner_dropdown_item, stateDataList);
        acspAadharState.setAdapter(rangeCategoryAdapter);

        relatnshipList = databaseClass.dao().getAllRCDataListby_catKey("relationship");
        for (RangeCategoryDataClass data : relatnshipList) {
            String descriptionEn = data.getDescriptionEn();
            RelationWithBorrowerList.add(descriptionEn);
            CustomSpinnerAdapter adapter3 = new CustomSpinnerAdapter(KYCActivity.this, android.R.layout.simple_spinner_dropdown_item, RelationWithBorrowerList);
            acspRelationship.setAdapter(adapter3);
        }

        maritalStatusList = databaseClass.dao().getAllRCDataListby_catKey("marrital_status");
        for (RangeCategoryDataClass data : maritalStatusList) {
            String descriptionEn = data.getDescriptionEn();
            MaritalStatusList.add(descriptionEn);
            CustomSpinnerAdapter adapter4 = new CustomSpinnerAdapter(KYCActivity.this, android.R.layout.simple_spinner_dropdown_item, MaritalStatusList);
            isMarriedSpinner.setAdapter(adapter4);
        }


        txtCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", "onClick: " + ((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code);
                if (((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code.equals("99")) {
                    Toast.makeText(KYCActivity.this, "Please select Valid State", Toast.LENGTH_SHORT).show();
                } else {
                    showCityDialog(txtCityName, ((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code);
                    stateCode = ((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code;
                }
            }
        });
        txtVDistrictName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code.equals("99")) {
                    Toast.makeText(KYCActivity.this, "Please select Valid State", Toast.LENGTH_SHORT).show();
                } else {
                    showDistrictDialog(txtVDistrictName, ((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code);
                }
            }
        });
        txtSubDistictName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (districtDat != null) {

                    showSubDistrictDialog(txtSubDistictName, districtDat.getDisTCODE());
                } else {
                    Toast.makeText(KYCActivity.this, "Please choose any District", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtVillageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subDistrictData != null && districtDat != null && !((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code.equals("99")) {

                    showVillageDialog(txtVillageName, ((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code, districtDat.getDisTCODE(), subDistrictData.getSuBDISTCODE());
                } else {
                    Toast.makeText(KYCActivity.this, "Please choose any valid state, district or sub district", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mobile_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpVerified = 0;
                if (editTextMobile.getText().toString().trim().length() != 10) {
                    mobile_checkbox.setChecked(false);

                    editTextMobile.setError("Please enter correct mobile number!!");
                } else {

                    mobile_checkbox.setChecked(false);

                    getMobileOTP(editTextMobile.getText().toString().trim(), mobile_checkbox);
                }

            }
        });


        dl_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl_Checkbox.setChecked(false);
                if (editTextDob.getText().toString().trim().length() > 9) {
                    if (editTextdrivingLicense.getText().toString().trim().length() < 5 || editTextdrivingLicense.length() > 15) {
                        editTextdrivingLicense.setError("Driving License Should be between 5 to 15 digits");
                        tilDLName.setVisibility(View.GONE);
                    } else {
                        IsAaadharExits(new AadharStatusCallback() {
                            @Override
                            public void onResult(boolean exists) {
                                if (exists) {
                                    showKYCFailedDialog();
                                } else {
                                    String dateOB = GlobalClass.formatDateString2(editTextDob.getText().toString(), "yyyy/MM/dd");
                                    Log.d("TAG", "drivinglicenseDOB: " + editTextDob.getText().toString());
                                    Log.d("TAG", "drivinglicenseDOB: " + dateOB);
                                    dlVerification("drivinglicense", editTextdrivingLicense.getText().toString(), dateOB);
                                }

                            }
                        });
                    }
                } else {
                    Toast.makeText(KYCActivity.this, "Please enter Date of Birth", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pan_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pan_Checkbox.setChecked(false);
                if (basicDetailsFound()) {
                    if (editTextPAN.getText().toString().trim().length() == 10) {
                        IsAaadharExits(new AadharStatusCallback() {
                            @Override
                            public void onResult(boolean exists) {
                                if (exists) {
                                    showKYCFailedDialog();
                                } else {
                                  panVerification("pancard", editTextPAN.getText().toString(), "");
                                }
                            }
                        });

                    } else {
                        tilPanName.setVisibility(View.GONE);
                        tilPanName.setError("Enter PAN");
                    }

                } else {
                    Utils.alert(KYCActivity.this, "Please fill all basic details of borrower\nlike AadhaarId, Name,DOB,Gender etc then verify Voter ID");
                }
            }
        });

        voterId_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voterId_Checkbox.setChecked(false);

                if (basicDetailsFound()) {
                    if (editTextvoterIdKyc.getText().toString().trim().length() < 5 || editTextvoterIdKyc.length() > 16) {
                        tilVoterName.setVisibility(View.GONE);
                        editTextvoterIdKyc.setError("Voter Id  Should be between 5 to 15 digits");
                    } else {


                        IsAaadharExits(new AadharStatusCallback() {
                            @Override
                            public void onResult(boolean exists) {
                                if (exists) {
                                    showKYCFailedDialog();
                                } else {
                                    voterIdVerification("voterid", editTextvoterIdKyc.getText().toString(), "");
                                }
                            }
                        });
                        if (ckycNumberExist != 1) {
                            getCkycByPanorVoter(editTextPAN, editTextvoterIdKyc, 3);
                        }
                    }
                } else {
                    Utils.alert(KYCActivity.this, "Please fill all basic details of borrower\nlike AadhaarId, Name,DOB,Gender etc then verify Voter ID");

                }


            }
        });


        editTextMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                mobile_checkbox.setEnabled(true);
//                mobile_checkbox.setChecked(false);
//                editTextMobile.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextPAN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pan_Checkbox.setEnabled(true);
                pan_Checkbox.setChecked(false);
                isNameMatched = "0";
                tilPanName.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextvoterIdKyc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                voterId_Checkbox.setEnabled(true);
                voterId_Checkbox.setChecked(false);
                isNameMatched = "0";
                tilVoterName.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextdrivingLicense.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dl_Checkbox.setEnabled(true);
                dl_Checkbox.setChecked(false);
                isNameMatched = "0";
                tilDLName.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        isMarriedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);

                if (selectedItem.equals("Unmarried")) {
                    spouseCardView.setVisibility(View.GONE);
                } else {
                    spouseCardView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        acspRelationship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);

                if (selectedItem.equals("Father")) {
                    if (!editTextGuardian.getText().toString().isEmpty()) {
                        String guardianName = editTextGuardian.getText().toString();
                        String[] parts = guardianName.split(" ");

                        if (parts.length == 1) {
                            editTextFatherFname.setText(parts[0]);
                        } else if (parts.length == 2) {
                            editTextFatherFname.setText(parts[0]);
                            editTextfatherlastname.setText(parts[1]);
                        } else if (parts.length >= 3) {
                            editTextFatherFname.setText(parts[0]);
                            editTextfathermiddlename.setText(parts[1]);
                            StringBuilder cText = new StringBuilder();
                            for (int i = 2; i < parts.length; i++) {
                                cText.append(parts[i]).append(" ");
                            }
                            editTextfatherlastname.setText(cText.toString().trim());
                        }
                    } else {
                        editTextGuardian.setError("Enter Guardian Name");
                    }
                } else if (selectedItem.equals("Husband")) {
                    if (!editTextGuardian.getText().toString().isEmpty()) {
                        String guardianName = editTextGuardian.getText().toString();
                        String[] parts = guardianName.split(" ");

                        if (parts.length == 1) {
                            editTextspousefirstname.setText(parts[0]);
                        } else if (parts.length == 2) {
                            editTextspousefirstname.setText(parts[0]);
                            editTextspouselastname.setText(parts[1]);
                        } else if (parts.length >= 3) {
                            editTextspousefirstname.setText(parts[0]);
                            editTextspousemiddlename.setText(parts[1]);
                            StringBuilder cText = new StringBuilder();
                            for (int i = 2; i < parts.length; i++) {
                                cText.append(parts[i]).append(" ");
                            }
                            editTextspouselastname.setText(cText.toString().trim());
                        }
                    } else {
                        editTextGuardian.setError("Enter Guardian Name");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customProgressDialog.show();
                Log.d("TAG", "onClickTAG: " + "clicked");
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
                isNameMatched = "0";
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
                isNameMatched = "0";
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
                isNameMatched = "0";
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }//On Create Close

    private void showKYCFailedDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(KYCActivity.this)
                .setTitle("KYC Validation Failed")
                .setMessage("Entered Aadhaar Number already taken loan or case in process. Kindly Check and re-try.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void IsAaadharExits(AadharStatusCallback callback) {
        customProgressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient4().create(ApiInterface.class);

        Call<AdharExitsModel> call = apiInterface.getadharexit(editTextAadhar.getText().toString());
        Log.d("TAG", "onResponse:rps5 " + call);
        call.enqueue(new Callback<AdharExitsModel>() {
            @Override
            public void onResponse(Call<AdharExitsModel> call, Response<AdharExitsModel> response) {
                customProgressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("TAG", "onResponse:rps4 " + response.message());
                    AdharExitsModel adharExitsModel = response.body();
                    if (adharExitsModel.getData()) {
                        Log.d("TAG", "onResponse:rps01 " + response.message());

                        callback.onResult(true); // Aadhaar exists
                    } else {
                        Log.d("TAG", "onResponse:rps00 " + response.message());

                        callback.onResult(false); // Aadhaar does not exist
                    }
                } else {
                    Log.d("TAG", "onResponse:rps2 " + response.code());
                    callback.onResult(false);
                }
            }

            @Override
            public void onFailure(Call<AdharExitsModel> call, Throwable t) {
                customProgressDialog.dismiss();
                Log.d("TAG", "onFailure:rps1 " + t.getMessage());
                callback.onResult(false);
            }
        });
    }

    public interface AadharStatusCallback {
        void onResult(boolean exists);
    }


    private void getMobileOTP(String mobileNumber, CheckBox mobileCheckBox) {
       // customProgressDialog.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(KYCActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.otp_dialog_layout, null);
        builder.setView(dialogView);

        AlertDialog dialogs = builder.create();
        dialogs.setCanceledOnTouchOutside(false);
        dialogs.setCancelable(false);
        dialogs.show();

        /*otpEditText = dialogView.findViewById(R.id.editTextOTP);
        submitButton = dialogView.findViewById(R.id.buttonSubmit);
        crossButtonDialog = dialogView.findViewById(R.id.crossButtonDialog);*/

        /*androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(KYCActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.otp_dialog_layout, null);
        builder.setView(dialogView);
        androidx.appcompat.app.AlertDialog dialogs = builder.create();
        dialogs.setCanceledOnTouchOutside(false);
        dialogs.setCancelable(false);*/
        otpEditText = dialogView.findViewById(R.id.editTextOTP);
        Button submitButton = dialogView.findViewById(R.id.buttonSubmit);
        ImageButton crossButtonDialog = dialogView.findViewById(R.id.crossButtonDialog);

        crossButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //customProgressDialog.dismiss();
                dialogs.dismiss();
            }
        });

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        Log.d("TAG", "getMobileOTP:3 ");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otpEditText.getText().toString().trim().length() != 6) {
                    otpEditText.setError("Wrong OTP");
                } else {
                    ApiInterface apiInterface = ApiClient.getClient5().create(ApiInterface.class);
                    Call<JsonObject> call = apiInterface.verifyOTP(mobileNumber, otpEditText.getText().toString().trim());
                    Log.d("TAG", "onClick:call " + call);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("TAG", "getMobileOTP:4 ");
                            if (response.isSuccessful()) {
                                Log.d("TAG", "getMobileOTP:5 " + response.code());
                                if (response.body().get("message").getAsString().equals("verified OTP")) {
                                    Log.d("TAG", "onResponseSUNNY: " + "hit");
                                    mobileCheckBox.setChecked(true);
                                    // mobile_checkbox.setChecked(true);
                                    //     mobile_checkbox.setEnabled(false);
                                    Toast.makeText(KYCActivity.this, "OTP verified", Toast.LENGTH_SHORT).show();
                                    otpVerified = 1;
                                    customProgressDialog.dismiss();
                                    dialogs.dismiss();
                                } else {
                                    otpEditText.setError("Wrong OTP");
                                    Toast.makeText(KYCActivity.this, "OTP Not verified", Toast.LENGTH_SHORT).show();
                                    mobile_checkbox.setChecked(false);
                                    // mobile_checkbox.setEnabled(true);
                                }
                            } else {
                                otpEditText.setError("Wrong OTP");
                                Toast.makeText(KYCActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                mobile_checkbox.setChecked(false);
                                mobile_checkbox.setEnabled(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(KYCActivity.this, "Please try again!!", Toast.LENGTH_SHORT).show();
                            mobile_checkbox.setChecked(false);
                            mobile_checkbox.setEnabled(true);
                        }
                    });
                }
            }
        });

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("MobileNo", mobileNumber);
        jsonObject.addProperty("Language", "English");
        jsonObject.addProperty("ContentId", "1007458689942092806");
        Log.d("TAG", "getMobileOTP:6 ");
        ApiInterface apiInterface = ApiClient.getClient5().create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.getOtp(jsonObject);
        Log.d("TAG", "getMobileOTP:call " + jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    Log.d("TAG", "getMobileOTP:7 ");
                    Log.d("TAG", "onResponse: " + response.message().toString());
                    if (response.body().get("message").getAsString().contains("Message Send Successfully")) {
                        dialogs.show();
                    } else {
                        Toast.makeText(KYCActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(KYCActivity.this, "Retry", Toast.LENGTH_SHORT).show();
                }
                customProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(KYCActivity.this, "Something went wrong " + t.getMessage(), Toast.LENGTH_SHORT).show();
                customProgressDialog.dismiss();
            }
        });
    }

    private boolean basicDetailsFound() {
        boolean retVal = true;
        retVal &= validateControls(editTextAadhar, editTextAadhar.getText().toString());
        retVal &= isValidFullName(editTextName.getText().toString());
        retVal &= validateControls(editTextDob, editTextDob.getText().toString());

        return retVal;
    }

    private boolean validateControls(EditText editText, String text) {
        boolean retVal = true;
        editText.setError(null);
        switch (editText.getId()) {
            case R.id.editTextAadhar:
                if (editText.length() != 12) {
                    editText.setError("Should be of 12 Characters");
                    Utils.alert(this, "Should be of 12 Characters");
                    retVal = false;
                } else if (!validateVerhoeff(editText.getText().toString())) {
                    editText.setError("Aadhar is not Valid");
                    Utils.alert(this, "Aadhar is not Valid");

                    retVal = false;
                }
                break;
            case R.id.editTextDob:
                if (editText.length() < 10) {
                    editText.setError("Should be more than 3 Characters");
                    Utils.alert(this, "Age should be more than 3 Characters");

                    retVal = false;
                }
                break;


            case R.id.editTextAge:
                try {
                    if (text.length() == 0) text = "0";
                    int age = Integer.parseInt(text);
                    if (age < 21) {
                        editText.setError("Age should be greater than 17");
                        retVal = false;
                    } else if (age > 57) {
                        editText.setError("Age should be less than 66");
                        retVal = false;
                    }
                    editTextDob.setEnabled(retVal);
                } catch (Exception e) {
                    editText.setError("");
                }
                break;
        }
        return retVal;
    }

    private void getCkycByPanorVoter(EditText editTextPAN, EditText editTextvoterIdKyc, int idType) {

        ApiInterface apiInterface = ApiClient.getClient4().create(ApiInterface.class);

        String dateForCKyc = null;

        // dateForCKyc = formatDate(editTextDob.getText().toString(),"dd-MMM-yyyy","yyyy-MM-dd");
        dateForCKyc = GlobalClass.formatDateString2(editTextDob.getText().toString(), "yyyy-MM-dd");

        Call<CkycNoMODEL> call = apiInterface.checkCkycData(editTextAadhar.getText().toString(), editTextPAN.getText().toString().length() < 1 ? "123" : editTextPAN.getText().toString(), editTextvoterIdKyc.getText().toString(), dateForCKyc, (acspGender.getSelectedItem().toString()), editTextName.getText().toString());


        call.enqueue(new Callback<CkycNoMODEL>() {
            @Override
            public void onResponse(Call<CkycNoMODEL> call, Response<CkycNoMODEL> response) {
                Log.d("TAG", "onResponse:ckyc3 " + call + response);
                if (response.isSuccessful() && response.code() == 200) {
                    CkycNoMODEL ckycNoMODEL = response.body();
                    if (ckycNoMODEL.getData() == -1) {
                        if (ckycNumberExist == 2) {
                            ckycNumberExist = 0;

                        } else if (ckycNumberExist == 3) {
                            ckycNumberExist = 0;
                        } else {
                            ckycNumberExist = idType;
                        }
                    } else {
                        ckycNumberExist = 1;
                    }
                    Log.d("TAG", "onResponse:ckyc6 " + ckycNumberExist);

                } else {
                    ckycNumberExist = 0;
                    Log.d("TAG", "onResponse:ckyc7 " + response.code());

                }
            }

            @Override
            public void onFailure(Call<CkycNoMODEL> call, Throwable throwable) {
                Log.d("TAG-ckyc", "onFailure: " + throwable.getMessage());
                ckycNumberExist = 0;
            }
        });
    }

    public void openAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.adhaar_scanner_popup, null);
        alertDialogBuilder.setView(dialogView);

        adhaarFront = dialogView.findViewById(R.id.adhaarFront);
        adhaarBack = dialogView.findViewById(R.id.adhaarBack);
        qrScan = dialogView.findViewById(R.id.scanQrCode);


        qrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                removeError();
                IntentIntegrator scanIntegrator = new IntentIntegrator(KYCActivity.this);
                scanIntegrator.setOrientationLocked(false);
                scanIntegrator.initiateScan(Collections.singleton("QR_CODE"));
                Log.d("TAG", "QRCODE: " + 1);

            }
        });

        adhaarFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeError();
                Intent intent = new Intent(KYCActivity.this, CameraActivity.class);
                startActivityForResult(intent, REQUEST_ADHAARFRONT_CAPTURE);
            }
        });

        adhaarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeError();
                Intent intent = new Intent(KYCActivity.this, CameraActivity.class);
                startActivityForResult(intent, REQUEST_ADHAARBACK_CAPTURE);
            }
        });

    /*    rdservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(KYCActivity.this, RdServiceActivity.class);
                startActivity(intent);

               *//* Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_ADHAARFRONT_CAPTURE);
                }*//*
            }
        });*/

        alertDialog = alertDialogBuilder.create(); // Create AlertDialog
        alertDialog.show(); // Show AlertDialog
    }

    private void setDataOfAdhar(File croppedImage, String imageData) {
        ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Data Fetching from " + imageData.toUpperCase() + " Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

        RequestBody image_befWorkBody = RequestBody.create(MediaType.parse("image/*"), croppedImage);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("file", croppedImage.getName(), image_befWorkBody);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), croppedImage);
        builder.addFormDataPart("file", croppedImage.getName(), surveyBody);
        // RequestBody requestBody = builder.build();
//----------------------------------------------------------------------------------------------------------//

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder(

        );
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        httpClient.readTimeout(1, TimeUnit.MINUTES);
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ocr.paisalo.in:950/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<AdharDataResponse> call = apiInterface.getAdharDataByOCR(GlobalClass.Token, BuildConfig.dbname, imageData, imagePart);
        Log.d("TAG", "QRCODE2: " + call);

        call.enqueue(new Callback<AdharDataResponse>() {
            @Override
            public void onResponse(Call<AdharDataResponse> call, Response<AdharDataResponse> response) {
                Log.d("TAG", "onResponsews: " + response.body());
                AdharDataResponse adharDataResponse = response.body();
                if (response.isSuccessful()) {
                    progressBar.dismiss();

                    if (alertDialog != null && alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }

                    if (adharDataResponse != null) {
                        AdharDataModel adharDataModel = adharDataResponse.getData();
                        if (adharDataModel != null) {
                            if (imageData.equals("aadharfront")) {
                                if (response.code() == 200) {
                                    String adharId = (String) adharDataModel.getAdharId();
                                    editTextAadhar.setText(adharId);
                                    String name = (String) adharDataModel.getName();
                                    editTextName.setText(name);

                                    if (adharDataModel.getDob() != null) {
                                        String dob = (String) adharDataModel.getDob();
                                        Log.d("TAG", "onResponse:dob " + dob);
                                        String formattedDate = GlobalClass.formatDateString(dob);

                                        if (GlobalClass.formatDateString(dob) != null) {
                                            editTextDob.setText(formattedDate);
                                        } else {
                                            editTextDob.setText(""); // Or handle the error case as needed
                                        }
                                    }

                                    //  editTextDob.setText(dob);

                                    String gender = (String) adharDataModel.getGender();

                                    if (gender != null) {
                                        if (gender.equalsIgnoreCase("FEMALE")) {
                                            acspGender.setSelection(2);
                                        } else if (gender.equalsIgnoreCase("MALE")) {
                                            acspGender.setSelection(1);
                                        } else {
                                            acspGender.setSelection(3);
                                        }
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
                                                    if (i < 2) {
                                                        address1Builder.append(trimmedPart).append(" ");
                                                    } else if (i >= 2 && i <= 5) {
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
                                    int statePosition = 0;
                                    for (int statePos = 0; statePos < stateDataList.size(); statePos++) {
                                        if (stateDataList.get(statePos).descriptionEn.equals(StateName)) {
                                            statePosition = statePos;
                                        }

                                    }
                                    acspAadharState.setSelection(statePosition);

                                    String Relation = adharDataModel.getRelation();
                                 /*   for (int i = 0; i < acspRelationship.getAdapter().getCount(); i++) {
                                        RangeCategoryDataModel rangeCategory = (RangeCategoryDataModel) acspRelationship.getAdapter().getItem(i);
                                        if (rangeCategory.getDescriptionEn().equals(Relation)) {
                                            acspRelationship.setSelection(i);
                                            break;
                                        }
                                    }*/

                                    if (Relation != null) {
                                        if (Relation.equalsIgnoreCase("Husband")) {
                                            acspRelationship.setSelection(3);
                                        } else if (Relation.equalsIgnoreCase("Father")) {
                                            acspRelationship.setSelection(2);
                                        } else {
                                            acspRelationship.setSelection(1);
                                        }
                                    }

                                    Log.d("TAG", "onResponse(relation): " + Relation);
                                    String Relation1 = adharDataModel.getRelation();
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
                                Log.d("TAG", "onResponse:panno1=> " + "panno");

                                if (response.code() == 200) {
                                    String panno = adharDataModel.getPanNo() != null ? adharDataModel.getPanNo().toString() : null;
                                    String panname = adharDataModel.getName() != null ? adharDataModel.getName().toString() : null;

                                    Log.d("TAG", "onResponse: PAN Name => " + panname + ", PAN No => " + panno);

                                    if (panno != null && panno.trim().equalsIgnoreCase(editTextPAN.getText().toString().trim())) {
                                        Log.d("TAG", "PAN number matches: " + panno);

                                        tilPanName.setVisibility(View.VISIBLE);
                                        tilPanName.setText(panname != null ? panname : "PAN number Verified");

                                        pan_Checkbox.setChecked(true);
                                        isNameMatched = "1";

                                        if (ckycNumberExist != 1) {
                                            getCkycByPanorVoter(editTextPAN, editTextvoterIdKyc, 2);
                                        }

                                        //   editTextPAN.setText(panno);

                                    } else {

                                        Log.d("TAG", "PAN number does not match. Received PAN: " + panno + ", Input PAN: " + editTextPAN.getText().toString().trim());
                                        Utils.alert(KYCActivity.this, "PAN Number Mismatch!!");

                                    }
                                } else {
                                    Toast.makeText(KYCActivity.this, "Please capture PAN Card on behalf sample", Toast.LENGTH_SHORT).show();
                                    Utils.alert(KYCActivity.this, "Please capture PAN image again!!");
                                }
                            } else {
                                Log.d("TAG", "onResponse: panno => " + "panno2");
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "QRCODE2: " + data + " // " + requestCode);

        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                Log.d("TAG", "QRCODE3: " + scanContent);

                if (scanFormat != null) {
                    try {
                        Log.d("TAG", "QRCODE4: " + scanContent);
                        setAadharContent(scanContent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("croppedImagePath")) {
                String croppedImagePath = data.getStringExtra("croppedImagePath");
                profileImageFile = new File(croppedImagePath);
                setprofileImage(profileImageFile);
            }
        } else if (requestCode == REQUEST_ADHAARFRONT_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("croppedImagePath")) {
                String croppedImagePath = data.getStringExtra("croppedImagePath");
                adhaarFrontFile = new File(croppedImagePath);
                setDataOfAdhar(adhaarFrontFile, "aadharfront");
            }
        } else if (requestCode == REQUEST_ADHAARBACK_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("croppedImagePath")) {
                String croppedImagePath = data.getStringExtra("croppedImagePath");
                adhaarBackFile = new File(croppedImagePath);
                setDataOfAdhar(adhaarBackFile, "aadharback");
            }
        } else if (requestCode == REQUEST_PAN_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("croppedImagePath")) {
                String croppedImagePath = data.getStringExtra("croppedImagePath");
                panFile = new File(croppedImagePath);
                setDataOfAdhar(panFile, "pan");
            }
        }

    }

    private void setprofileImage(File profileImageFile) {
        Bitmap bitmap = BitmapFactory.decodeFile(profileImageFile.getAbsolutePath());
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        profilePic.setImageDrawable(drawable);
    }

    private void setAadharContent(String aadharDataString) throws Exception {

        if (aadharDataString.toUpperCase().contains("XML")) {
            Log.d("TAG", "QRCODE5: " + aadharDataString);

            //AadharData aadharData = AadharUtils.getAadhar(aadharDataString);
            AadharData aadharData = AadharUtils.getAadhar(AadharUtils.ParseAadhar(aadharDataString));

            Log.d("TAG", "QRCODE5: " + aadharData.isAadharVerified);

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

        } else {

            final BigInteger bigIntScanData = new BigInteger(aadharDataString, 10);
            Log.d("TAG", "QRCODE6: " + bigIntScanData);

            // 2. Convert BigInt to Byte Array
            final byte byteScanData[] = bigIntScanData.toByteArray();

            // 3. Decompress Byte Array
            final byte[] decompByteScanData = decompressData(byteScanData);

            // 4. Split the byte array using delimiter
            List<byte[]> parts = separateData(decompByteScanData);
            // Throw error if there are no parts
            Log.d("TAG", "QRCODE7: " + parts.toString());

            Log.e("Parts======11======> ", "part data =====> " + parts.toString());
            decodeData(parts);
            decodeSignature(decompByteScanData);
            decodeMobileEmail(decompByteScanData);
            aadharNumberentry = true;
        }
    }

    protected void decodeMobileEmail(byte[] decompressedData) {
        int mobileStartIndex = 0, mobileEndIndex = 0, emailStartIndex = 0, emailEndIndex = 0;
        switch (emailMobilePresent) {
            case 3:
                mobileStartIndex = decompressedData.length - 289; // length -1 -256 -32
                mobileEndIndex = decompressedData.length - 257; // length -1 -256
                emailStartIndex = decompressedData.length - 322;// length -1 -256 -32 -1 -32
                emailEndIndex = decompressedData.length - 290;// length -1 -256 -32 -1

                mobile = bytesToHex(Arrays.copyOfRange(decompressedData, mobileStartIndex, mobileEndIndex + 1));
                email = bytesToHex(Arrays.copyOfRange(decompressedData, emailStartIndex, emailEndIndex + 1));
                imageEndIndex = decompressedData.length - 323;
                break;

            case 2:
                // only mobile
                email = "";
                mobileStartIndex = decompressedData.length - 289; // length -1 -256 -32
                mobileEndIndex = decompressedData.length - 257; // length -1 -256

                mobile = bytesToHex(Arrays.copyOfRange(decompressedData, mobileStartIndex, mobileEndIndex + 1));
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 290;

                break;

            case 1:
                // only email
                mobile = "";
                emailStartIndex = decompressedData.length - 289; // length -1 -256 -32
                emailEndIndex = decompressedData.length - 257; // length -1 -256

                email = bytesToHex(Arrays.copyOfRange(decompressedData, emailStartIndex, emailEndIndex + 1));
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

        Log.e("email mobile======> ", "Data=====>" + email + "   " + mobile);

    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();

        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    protected void decodeSignature(byte[] decompressedData) {
        // extract 256 bytes from the end of the byte array
        int startIndex = decompressedData.length - 257,
                noOfBytes = 256;
        try {
            signature = new String(decompressedData, startIndex, noOfBytes, "ISO-8859-1");
            Log.e("signature======>", "signature===> " + signature);
        } catch (UnsupportedEncodingException e) {
            Log.e("Exception", "Decoding Signature of QRcode, ISO-8859-1 not supported: " + e.toString());
            // throw new QrCodeException("Decoding Signature of QRcode, ISO-8859-1 not supported",e);
        }

    }

    protected void decodeData(List<byte[]> encodedData) throws ParseException {
        Log.d("TAG", "QRCODE8: " + "decodeData");

        Iterator<byte[]> i = encodedData.iterator();
        decodedData = new ArrayList<String>();
        while (i.hasNext()) {
            decodedData.add(new String(i.next(), StandardCharsets.ISO_8859_1));
        }
        Log.e("Parts======2======> ", "part data =====> " + decodedData.toString());

        Log.d("part data =====> ", "Parts======1======> " + decodedData.get(1));
        Log.d("part data =====> ", "Parts======2======> " + decodedData.get(2));
        Log.d("part data =====> ", "Parts======3======> " + decodedData.get(3));
        Log.d("part data =====> ", "Parts======4======> " + decodedData.get(4));
        Log.d("part data =====> ", "Parts======5======> " + decodedData.get(5));
        Log.d("part data =====> ", "Parts======6======> " + decodedData.get(6));
        Log.d("part data =====> ", "Parts======7======> " + decodedData.get(7));
        Log.d("part data =====> ", "Parts======8======> " + decodedData.get(8));
        Log.d("part data =====> ", "Parts======9=====> " + decodedData.get(9));
        Log.d("part data =====> ", "Parts======10=====> " + decodedData.get(10));
        Log.d("part data =====> ", "Parts======11=====> " + decodedData.get(11));
        Log.d("part data =====> ", "Parts======12=====> " + decodedData.get(12));
        Log.d("part data =====> ", "Parts======13=====> " + decodedData.get(13));
        Log.d("part data =====> ", "Parts======14=====> " + decodedData.get(14));
        Log.d("part data =====> ", "Parts======15=====> " + decodedData.get(15));

        int inc = 0;
        Log.d("TAG", "decodeData: " + decodedData.get(0).startsWith("V") + "/////" + decodedData.get(0));
        if (decodedData.get(0).startsWith("V")) {
            inc = 0;
        } else {
            inc = 1;
        }

        String dob = decodedData.get(4 - inc);


        String formattedDate = GlobalClass.formatDateString(dob);
        if (formattedDate != null) {
            editTextDob.setText(formattedDate);
        } else {
            editTextDob.setText(""); // Or handle the error case as needed
        }

/*
        editTextAadhar.setText(decodedData.get(2 - inc));
*/

        if (decodedData.get(3 - inc).equals("") || decodedData.get(3 - inc).equals(null)) {
            editTextName.setEnabled(true);
        } else {
            editTextName.setText(decodedData.get(3 - inc));
            Log.d("TAG", "Parts======name=====>  " + editTextName.getText().toString());
        }
        if (decodedData.get(4 - inc).equals("") || decodedData.get(4 - inc).equals(null)) {
            editTextDob.setEnabled(true);
            Log.d("TAG", "Parts======dobnull=====>  " + editTextDob.getText().toString());
        } else {
            String formattedDate3 = GlobalClass.formatDateString(decodedData.get(4 - inc));
            if (formattedDate != null) {
                editTextDob.setText(formattedDate);
            } else {
                editTextDob.setText(""); // Or handle the error case as needed
            }
            //    editTextDob.setText(decodedData.get(4 - inc));
            Log.d("TAG", "Parts======dob=====>  " + editTextDob.getText().toString());
        }

        if (decodedData.get(5 - inc).equals("") || decodedData.get(5 - inc).equals(null)) {

            Log.d("TAG", "Parts======rps=====>  " + "null");
        } else {

            Log.d("TAG", "PParts======rps=====>  " + decodedData.get(5 - inc));
        }

        int positionOfGender = 0;
        for (int genPos = 0; genPos < GenderList.size(); genPos++) {
            if (GenderList.get(genPos).toUpperCase().startsWith(decodedData.get(5 - inc))) {
                positionOfGender = genPos;
            }
        }

        acspGender.setSelection(positionOfGender);

        StringBuilder joinedStringBuilder = new StringBuilder();
        Log.d("TAG", "Value at index " + decodedData.get(8 - inc));
        Log.d("TAG", "Value at index " + decodedData.get(9 - inc));
        Log.d("TAG", "Value at index " + decodedData.get(10 - inc));
        Log.d("TAG", "Value at index " + decodedData.get(12 - inc));
        Log.d("TAG", "Value at index " + decodedData.get(14 - inc));
        Log.d("TAG", "Value at index " + decodedData.get(15 - inc));

        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(8 - inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(9 - inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(10 - inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(12 - inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(14 - inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(15 - inc));

        String joinedString = joinedStringBuilder.toString();
        Log.d("TAG", "Value at indexsss: " + joinedString);

        boolean changesMade;
        do {
            String previousString = joinedString;
            joinedString = joinedString.replaceAll(",+", ",");
            changesMade = !previousString.equals(joinedString);
        } while (changesMade);

        if (!joinedString.contains(",")) {
            editTextAddress1.setText(joinedString);
        } else {
            String[] separatedStrings = joinedString.split(",");
            int totalStrings = separatedStrings.length;
            if (totalStrings < 3) {
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

        if (decodedData.get(7 - inc).equals("") || decodedData.get(7 - inc).equals(null)) {
            editTextCity.setEnabled(true);
        } else {
            editTextCity.setText(decodedData.get(7 - inc));
        }

        if (decodedData.get(11 - inc).equals("") || decodedData.get(11 - inc).equals(null)) {
        } else {
            editTextPincode.setText(decodedData.get(11 - inc));
        }

        if (decodedData.get(13 - inc).equals("") || decodedData.get(13 - inc).equals(null)) {

            Log.d("TAG", "Parts======rps_state=====>  " + "null");
        } else {

            Log.d("TAG", "PParts======rps-state=====>  " + decodedData.get(13 - inc));
        }

        String state = decodedData.get(13 - inc);
        Log.d("TAG", "decodeData:state " + state);
        int statePosition = 0;
        for (int statePos = 0; statePos < stateDataList.size(); statePos++) {
            if (stateDataList.get(statePos).descriptionEn.equals(state)) {
                statePosition = statePos;
            }

        }
        acspAadharState.setSelection(statePosition);


        String relation = decodedData.get(6 - inc);
        Log.d("TAG", "decodeData:acspRelationship " + relation);

        if (relation.startsWith("S/O:") || relation.startsWith("S/O ") || relation.startsWith("D/O:")) {
            Utils.setSpinnerPosition1(acspRelationship, "Father", false);
            acspRelationship.setEnabled(false);

        } else if (relation.startsWith("W/O:")) {
            Utils.setSpinnerPosition1(acspRelationship, "Husband", false);
            acspRelationship.setEnabled(false);

        }


        if (decodedData.get(6 - inc).equals("") || decodedData.get(6 - inc).equals(null)) {

        } else {
            if (decodedData.get(6 - inc).startsWith("S/O:") || decodedData.get(6 - inc).startsWith("D/O:") || decodedData.get(6 - inc).startsWith("W/O:")) {
                editTextGuardian.setText(decodedData.get(6 - inc).split(":")[1].replace("S/O:", "").replace("D/O:", "").replace("W/O:", "").replace("S/O", "").replace("D/O", "").replace("\"", "").replace("W/O", "").trim());
                if (decodedData.get(6 - inc).toUpperCase().startsWith("W/O:")) {
                    Utils.setSpinnerPosition(acspRelationship, "Married", false);
                    String[] spouseName = decodedData.get(6 - inc).split(" ");
                    switch (spouseName.length) {
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
                            editTextmotherfirstname.setText(decodedData.get(6 - inc));
                            break;
                    }

                }
            } else if (decodedData.get(6 - inc).startsWith("S/O,") || decodedData.get(6 - inc).startsWith("D/O,") || decodedData.get(6 - inc).startsWith("W/O,")) {
                editTextGuardian.setText(decodedData.get(6 - inc).split(",")[1].trim());
                Log.d("TAG", "Parts======guardian=====>  " + editTextGuardian.getText().toString());

            } else {
                editTextGuardian.setText(decodedData.get(6 - inc).replace("S/O:", "").replace("D/O:", "").replace("W/O:", "").replace("S/O", "").replace("D/O", "").replace("W/O", "").replace("\"", ""));

            }
            if (decodedData.get(6 - inc).startsWith("S/O:") || decodedData.get(6 - inc).startsWith("D/O:") || decodedData.get(6 - inc).startsWith("W/O:")) {
                editTextGuardian.setText(decodedData.get(6 - inc).split(":")[1].replace("S/O:", "").replace("D/O:", "").replace("W/O:", "").replace("S/O", "").replace("D/O", "").replace("\"", "").replace("W/O", "").trim());
                if (decodedData.get(6 - inc).toUpperCase().startsWith("W/O:")) {
                    Utils.setSpinnerPosition(isMarriedSpinner, "Married", false);
                    String[] spouseName = decodedData.get(6 - inc).split(" ");
                    switch (spouseName.length) {
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
                            editTextspouselastname.setText(decodedData.get(6 - inc));
                            break;
                    }

                }
            } else if (decodedData.get(6 - inc).startsWith("S/O,") || decodedData.get(6 - inc).startsWith("D/O,") || decodedData.get(6 - inc).startsWith("W/O,")) {
                editTextGuardian.setText(decodedData.get(6 - inc).split(",")[1].trim());
            } else {
                editTextGuardian.setText(decodedData.get(6 - inc).replace("S/O:", "").replace("D/O:", "").replace("W/O:", "").replace("S/O", "").replace("D/O", "").replace("W/O", "").replace("\"", ""));

            }
            if (decodedData.get(6 - inc).startsWith("S/O") || decodedData.get(6 - inc).startsWith("D/O")) {
                Utils.setSpinnerPosition(acspRelationship, "Father", false);
                acspRelationship.setEnabled(false);
                String[] fatherNames = decodedData.get(6 - inc).contains(":") ? decodedData.get(6 - inc).split(": ") : decodedData.get(6 - inc).split("/O");
                String[] newFatherName = fatherNames[1].split(" ");
                if (newFatherName.length > 2) {
                    String fatherFirstName = "";
                    for (int a = 1; a < newFatherName.length - 1; a++) {
                        fatherFirstName = fatherFirstName + " " + newFatherName[a];
                    }
                    editTextFatherFname.setText(fatherFirstName.trim());
                    Log.d("TAG", "Parts======fatherName=====>  " + editTextFatherFname.getText().toString());

                    editTextFatherFname.setEnabled(false);
                    editTextfatherlastname.setText(newFatherName[newFatherName.length - 1].trim());
                    editTextfatherlastname.setEnabled(false);

                } else {
                    if (decodedData.get(6 - inc).contains(":")) {

                        editTextFatherFname.setText(decodedData.get(6 - inc).split(":")[1].trim());
                        editTextFatherFname.setEnabled(false);
                    } else {
                        editTextFatherFname.setText(decodedData.get(6 - inc).split("/O")[1].trim());
                        editTextFatherFname.setEnabled(false);
                    }

                }
            } else if (decodedData.get(6 - inc).startsWith("W/O")) {
                Utils.setSpinnerPosition(acspRelationship, "Husband", false);
                acspRelationship.setEnabled(false);
                String[] spouseNames = decodedData.get(6 - inc).contains(":") ? decodedData.get(6 - inc).split(":") : decodedData.get(6 - inc).split("/O");
                String[] newSpouseName = spouseNames[1].split(" ");
                if (newSpouseName.length > 2) {
                    String spouseFirstName = "";
                    for (int a = 1; a < newSpouseName.length - 1; a++) {
                        spouseFirstName = spouseFirstName + " " + newSpouseName[a];
                    }
                    editTextspousefirstname.setText(spouseFirstName.trim());
                    editTextspousefirstname.setEnabled(false);
                    editTextfatherlastname.setText(newSpouseName[newSpouseName.length - 1].trim());
                    editTextspouselastname.setEnabled(false);

                } else {

                    if (decodedData.get(6 - inc).contains(":")) {
                        editTextspousefirstname.setText(decodedData.get(6 - inc).split(":")[1].trim());
                        editTextspousefirstname.setEnabled(false);
                    } else {
                        editTextfatherlastname.setText(decodedData.get(6 - inc).split("/O")[1].trim());
                        editTextfatherlastname.setEnabled(false);
                    }

                }

            }
        }

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
                size = gis.read(buf, 0, buf.length);
                if (size > 0) {
                    bos.write(buf, 0, size);
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
            if (source[i] == SEPARATOR_BYTE) {
                if (i != 0 && i != (source.length - 1)) {
                    separatedParts.add(Arrays.copyOfRange(source, begin, i));
                }
                begin = i + 1;
                if (separatedParts.size() == (VTC_INDEX + 1)) {
                    imageStartIndex = begin;
                    break;
                }
            }
        }
        return separatedParts;
    }

    private void dlVerification(String ID, String IDno, String dob) {
        customProgressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DLVerificationModel> call = apiInterface.getDLVerified(GlobalClass.Token, BuildConfig.dbname, createJson(ID, IDno, dob));

        Log.d("TAG", "onResponseID: " + GlobalClass.Token + "  " + BuildConfig.dbname + "  " + createJson(ID, IDno, dob));
        call.enqueue(new Callback<DLVerificationModel>() {
            @Override
            public void onResponse(Call<DLVerificationModel> call, Response<DLVerificationModel> response) {
                Log.d("TAG", "onResponseID1: " + response.body());
                if (response.isSuccessful()) {
                    customProgressDialog.dismiss();
                    Log.d("TAG", "onResponseID2: " + response.body().getMessage());

                    if (response.body().getMessage().equals("Get Record Successfully !!")) {
                        Log.d("TAG", "onResponseID3: ");
                        customProgressDialog.dismiss();
                        if (response.body().getData().getSuccess()) {
                            Log.d("TAG", "onResponseID4: ");

                            if (response.body().getData().getData().getStatus().equals("success")) {
                                Log.d("TAG", "onResponseID5: ");
                                customProgressDialog.dismiss();
                                if (response.body().getData().getData().getData().getName() != null) {
                                    Log.d("TAG", "onResponseID6: ");
                                    customProgressDialog.dismiss();
                                    verifiedLicensename = response.body().getData().getData().getData().getName().toString();
                                    dl_Checkbox.setChecked(true);
                                    isNameMatched = "1";
                                    Toast.makeText(KYCActivity.this, "DL Verified", Toast.LENGTH_SHORT).show();
                                    dl_Checkbox.setClickable(false);


                                    try {
                                        tilDLName.setVisibility(View.VISIBLE);
                                        tilDLName.setText("Not Found");
                                        tilDLName.setTextColor(getResources().getColor(R.color.green));

                                    } catch (Exception e) {
                                        tilDLName.setVisibility(View.VISIBLE);
                                        tilDLName.setText(response.body().getData().getData().getData().getName().toString());
                                        tilDLName.setTextColor(getResources().getColor(R.color.green));


                                    }

                                } else {
                                    customProgressDialog.dismiss();
                                    dl_Checkbox.setChecked(false);
                                    isNameMatched = "0";
                                    Log.d("TAG", "onResponseID7: ");

                                    Toast.makeText(KYCActivity.this, "Invalid Input DL", Toast.LENGTH_SHORT).show();

                                    tilDLName.setVisibility(View.VISIBLE);
                                    tilDLName.setText("DL not Verify");
                                    tilDLName.setTextColor(getResources().getColor(R.color.black));

                                }
                            } else {
                                customProgressDialog.dismiss();
                                Log.d("TAG", "onResponseID8: ");

                                Toast.makeText(KYCActivity.this, "Invalid Input DL", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            customProgressDialog.dismiss();
                            Log.d("TAG", "onResponseID9: ");

                            Toast.makeText(KYCActivity.this, "Invalid Input DL", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        customProgressDialog.dismiss();
                        Log.d("TAG", "onResponseID10: ");

                        Toast.makeText(KYCActivity.this, "Invalid Input DL", Toast.LENGTH_SHORT).show();
                    }

                    /*DLVerificationModel dlVerificationModel = response.body();
                    if (dlVerificationModel.getData().getData().getData().getName() != null) {
                        Log.d("TAG", "onResponseID: " + dlVerificationModel.getData().getData().getData().getName());
                        Data_ data = dlVerificationModel.getData();
                        Data_1 data1 = data.getData();
                        Data_2 data2 = data1.getData();
                        verifiedLicensename = data2.getName();
                        dl_Checkbox.setChecked(true);
                        Toast.makeText(KYCActivity.this, "DL Verified", Toast.LENGTH_SHORT).show();
                        dl_Checkbox.setClickable(false);
                    } else {
                        dl_Checkbox.setChecked(false);
                        Log.d("TAG", "onResponseID: " + dlVerificationModel.getData().getData().getData().getName());
                        Toast.makeText(KYCActivity.this, "Invalid Input DL", Toast.LENGTH_SHORT).show();
                    }*/
                } else {
                    Log.d("TAG", "onResponseID: ");
                    customProgressDialog.dismiss();
                    Log.d("TAG", "onResponseID: " + response.code());
                    dl_Checkbox.setChecked(false);
                    isNameMatched = "0";
                    Toast.makeText(KYCActivity.this, "Invalid Input DL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DLVerificationModel> call, Throwable t) {
                customProgressDialog.dismiss();
                dl_Checkbox.setChecked(false);
                isNameMatched = "0";
                Toast.makeText(KYCActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void panVerification(String ID, String IDno, String dob) {
        customProgressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PanVerificationModel> call = apiInterface.getpanVerified(GlobalClass.Token, BuildConfig.dbname, createJson(ID, IDno, dob));

        Log.d("TAG", "onResponseID: " + GlobalClass.Token + "  " + BuildConfig.dbname + "  " + createJson(ID, IDno, dob));
        call.enqueue(new Callback<PanVerificationModel>() {
            @Override
            public void onResponse(Call<PanVerificationModel> call, Response<PanVerificationModel> response) {
                Log.d("TAG", "onResponseID: " + response.body());
                if (response.isSuccessful()) {
                    customProgressDialog.dismiss();
                    Log.d("TAG", "onResponseID1: " + response.body());
                    PanVerificationModel panVerificationModel = response.body();
                    if (panVerificationModel.getMessage().toString().equals("Get Record Successfully !!")) {

                        if (response.body().getData().getSuccess()) {
                            customProgressDialog.dismiss();
                            pan_Checkbox.setChecked(true);
                            isNameMatched = "1";

                            if (ckycNumberExist != 1) {
                                getCkycByPanorVoter(editTextPAN, editTextvoterIdKyc, 2);
                            }

                            Toast.makeText(KYCActivity.this, "Pan Verified", Toast.LENGTH_SHORT).show();
                            verifiedPanName = panVerificationModel.getData().getData().getData().getName();

                            tilPanName.setVisibility(View.VISIBLE);


                            try {
                                tilPanName.setText(response.body().getData().getData().getData().getName().toString());
                                tilPanName.setTextColor(getResources().getColor(R.color.green));

                                pan_Checkbox.setClickable(false);
                            } catch (Exception e) {

                                tilPanName.setText("Not Found");
                                tilPanName.setTextColor(getResources().getColor(R.color.green));
                                pan_Checkbox.setClickable(false);
                                showImageCaptureDialog();

                            }
                        } else {

                            customProgressDialog.dismiss();
                            Toast.makeText(KYCActivity.this, "Not Verified", Toast.LENGTH_SHORT).show();
                            // showDialog(editTextPAN);
                            SubmitAlert(KYCActivity.this, "success", "Data set Successfully");
                            tilPanName.setVisibility(View.VISIBLE);
                            tilPanName.setText("PAN not Verify");
                            tilPanName.setTextColor(getResources().getColor(R.color.black));
                            showImageCaptureDialog();

                        }

                    } else {
                        customProgressDialog.dismiss();
                        Toast.makeText(KYCActivity.this, "Invalid Pan", Toast.LENGTH_SHORT).show();
                        showImageCaptureDialog();

                    }
                } else {
                    showImageCaptureDialog();
                    Log.d("TAG", "onResponseID: " + response.code());
                    customProgressDialog.dismiss();
                    pan_Checkbox.setChecked(false);
                    isNameMatched = "0";
                    Toast.makeText(KYCActivity.this, "Invalid Pan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PanVerificationModel> call, Throwable t) {
                Log.d("TAG", "onResponseID: " + "Failure");
                pan_Checkbox.setChecked(false);
                isNameMatched = "0";

                Toast.makeText(KYCActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
                showImageCaptureDialog();
            }
        });

    }

    private void showImageCaptureDialog() {
        Dialog dialog = new Dialog(KYCActivity.this);
        dialog.setContentView(R.layout.dialog_capture_image);
        Button captureImageButton = dialog.findViewById(R.id.captureImageButton);
        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for camera permission
//                if (ContextCompat.checkSelfPermission(KYCActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(KYCActivity.this, new String[]{android.Manifest.permission.CAMERA}, 9000);
//                } else {

                Intent intent = new Intent(KYCActivity.this, CameraActivity.class);
                startActivityForResult(intent, REQUEST_PAN_CAPTURE);

                dialog.dismiss();
                // }
            }
        });
        dialog.show();
    }


    private void voterIdVerification(String ID, String IDno, String dob) {
        voterId_Checkbox.setChecked(false);
        customProgressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VoterIdVerificationModel> call = apiInterface.getvoterIDVerified(GlobalClass.Token, BuildConfig.dbname, createJson(ID, IDno, dob));

        Log.d("TAG", "onResponseID: " + GlobalClass.Token + "  " + BuildConfig.dbname + "  " + createJson(ID, IDno, dob));
        call.enqueue(new Callback<VoterIdVerificationModel>() {
            @Override
            public void onResponse(Call<VoterIdVerificationModel> call, Response<VoterIdVerificationModel> response) {
                Log.d("TAG", "onResponseID: " + response.body());
                if (response.isSuccessful()) {
                    customProgressDialog.dismiss();
                    Log.d("TAG", "onResponseID: " + response.body());
                    VoterIdVerificationModel voterIdVerificationModel = response.body();
                    if (voterIdVerificationModel.getMessage().toString().equals("Get Record Successfully !!")) {

                        if (response.body().getData().getSuccess()) {
                            customProgressDialog.dismiss();
                            voterId_Checkbox.setChecked(true);
                            isNameMatched = "1";
                            Toast.makeText(KYCActivity.this, "ID Verified", Toast.LENGTH_SHORT).show();
                            verifiedVotername = voterIdVerificationModel.getData().getData().getData().getName();
                            voterId_Checkbox.setClickable(false);

                            tilVoterName.setVisibility(View.VISIBLE);
                            try {
                                tilVoterName.setText(response.body().getData().getData().getData().getName().toString());
                                tilVoterName.setTextColor(getResources().getColor(R.color.green));
                            } catch (Exception e) {
                                tilVoterName.setText("Not Found");
                                voterId_Checkbox.setChecked(false);
                                tilVoterName.setTextColor(getResources().getColor(R.color.green));
                            }
                        } else {
                            customProgressDialog.dismiss();
                            Toast.makeText(KYCActivity.this, "Not Verified", Toast.LENGTH_SHORT).show();
                            // showDialog(editTextPAN);
                            SubmitAlert(KYCActivity.this, "success", "Data set Successfully");
                            tilVoterName.setVisibility(View.VISIBLE);
                            tilVoterName.setText("Voter not Verify");
                            tilVoterName.setTextColor(getResources().getColor(R.color.black));
                            voterId_Checkbox.setChecked(false);

                        }
                    } else {
                        customProgressDialog.dismiss();
                        voterId_Checkbox.setChecked(false);
                        tilVoterName.setText("Voter not Verify");
                        Toast.makeText(KYCActivity.this, "Invalid Voter ID", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customProgressDialog.dismiss();
                    Log.d("TAG", "onResponseID: " + response.code());
                    voterId_Checkbox.setChecked(false);
                    Toast.makeText(KYCActivity.this, response.code() + "," + response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<VoterIdVerificationModel> call, Throwable t) {
                customProgressDialog.dismiss();
                voterId_Checkbox.setChecked(false);
                Toast.makeText(KYCActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public static String formatDate(String date, String initDateFormat, String endDateFormat) throws ParseException {

        Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
        String parsedDate = formatter.format(initDate);

        return parsedDate;
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
        removeError();
        allConditionsSatisfied = true;

        if (editTextAadhar.getText().toString().isEmpty()) {
            editTextAadhar.setError("Invalid ID");
            Log.d("TAG", "onClickTAG1: " + "creating Json");
            allConditionsSatisfied = false;
            focusOnView(editTextAadhar);
        } else {
            AadharID = editTextAadhar.getText().toString();

        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (editTextAge.getText().toString().isEmpty()) {
            Log.d("TAG", "onClickTAG2: " + "creating Json");
            editTextAge.setError("Invalid Age");
            allConditionsSatisfied = false;
            focusOnView(editTextAge);

        } else {
            Age = editTextAge.getText().toString();

        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (!isValidFullName(editTextName.getText().toString().isEmpty() ? "" : editTextName.getText().toString())) {
            editTextName.setError("Invalid Name");
            Log.d("TAG", "onClickTAG3: " + "creating Json");
            allConditionsSatisfied = false;
            focusOnView(editTextName);

        } else {

            name = editTextName.getText().toString().isEmpty() ? "" : editTextName.getText().toString();
            String[] parts = name.split(" ");
            if (parts.length == 1) {
                Fname = parts[0];
            } else if (parts.length == 2) {
                Fname = parts[0];
                Lname = parts[1];
            } else if (parts.length > 2) {
                Fname = parts[0];
                Mname = parts[1];
                StringBuilder restBuilder = new StringBuilder();
                for (int i = 2; i < parts.length; i++) {
                    restBuilder.append(parts[i]);
                    if (i < parts.length - 1) {
                        restBuilder.append(" ");
                    }
                }
                Lname = restBuilder.toString();
            }
        }

        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (editTextDob.getText().toString().isEmpty()) {
            editTextDob.setError("Select Date");
            Log.d("TAG", "onClickTAG4: " + "creating Json");

            allConditionsSatisfied = false;
            focusOnView(editTextDob);

        } else {
            String temp = editTextDob.getText().toString();
            String formattedDate = GlobalClass.formatDateString2(temp, "yyyy-MM-dd");
            if (formattedDate != null) {
                DOB = formattedDate;

                Log.d("TAG", "drivinglicenseDOBjson: " + DOB);
                Log.d("TAG", "drivinglicenseDOBjson: " + formattedDate);
            }
        }


        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);


        if (!isValidAddr(editTextAddress1.getText().toString().isEmpty() ? "" : editTextAddress1.getText().toString())) {
            editTextAddress1.setError("Invalid Address");
            Log.d("TAG", "onClickTAG5: " + "creating Json");

            allConditionsSatisfied = false;
            focusOnView(editTextAddress1);

        } else {
            P_Add1 = editTextAddress1.getText().toString();
        }

        if (!editTextAddress2.getText().toString().isEmpty()) {
            if (!isValidSAddr(editTextAddress2.getText().toString())) {
                editTextAddress2.setError("Invalid Address");
                Log.d("TAG", "onClickTAG6: " + "creating Json");
                allConditionsSatisfied = false;
                focusOnView(editTextAddress2);
            } else {
                P_Add2 = editTextAddress2.getText().toString();
            }
        }

        if (!editTextAddress3.getText().toString().isEmpty()) {
            if (!isValidSAddr(editTextAddress3.getText().toString())) {

                editTextAddress3.setError("Invalid Address");
                Log.d("TAG", "onClickTAG7: " + "creating Json");
                focusOnView(editTextAddress3);
                allConditionsSatisfied = false;
            } else {
                P_Add3 = editTextAddress3.getText().toString();
            }
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (!isValidMName(editTextCity.getText().toString().isEmpty() ? " " : editTextCity.getText().toString())) {
            editTextCity.setError("Invalid City");

            Log.d("TAG", "onClickTAG8: " + "creating Json");
            focusOnView(editTextCity);
            allConditionsSatisfied = false;
        } else {
            P_City = editTextCity.getText().toString();
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (!isNumber(editTextPincode.getText().toString())) {
            editTextPincode.setError("Invalid PinCode");
            Log.d("TAG", "onClickTAG9: " + "creating Json");
            focusOnView(editTextPincode);
            allConditionsSatisfied = false;
        } else {
            P_Pin = Integer.parseInt(editTextPincode.getText().toString());
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (!isNumber(editTextMobile.getText().toString())) {
            editTextMobile.setError("Invalid PinCode");
            Log.d("TAG", "onClickTAG10: " + "creating Json");
            focusOnView(editTextMobile);
            allConditionsSatisfied = false;
        } else {
            P_Ph3 = editTextMobile.getText().toString();
        }

        if (!mobile_checkbox.isChecked()) {
            editTextMobile.setError("Mobile Number Not Verify !!");
            allConditionsSatisfied = false;
            focusOnView(editTextMobile);
        }

        /*if (!dl_Checkbox.isChecked() && !pan_Checkbox.isChecked() && !voterId_Checkbox.isChecked()) {
                editTextPAN.setError("Invalid Pan");
                editTextdrivingLicense.setError("Empty License");
                editTextvoterIdKyc.setError("Empty VoterId");
                Log.d("TAG", "onClickTAG11: " + "creating Json");
                allConditionsSatisfied = false;
                focusOnView(editTextPAN);

            }
            if (dl_Checkbox.isChecked() && pan_Checkbox.isChecked() && voterId_Checkbox.isChecked()) {
                voterId = editTextvoterIdKyc.getText().toString();
                PanNO = editTextPAN.getText().toString();
                DrivingLic = editTextdrivingLicense.getText().toString();
            }
            if (voterId_Checkbox.isChecked()) {
                voterId = editTextvoterIdKyc.getText().toString();
            }
            if (pan_Checkbox.isChecked() && (!dl_Checkbox.isChecked()&&!voterId_Checkbox.isChecked())) {
                editTextdrivingLicense.setError("Empty License");
                Log.d("TAG", "onClickTAG12: " + "creating Json");
                allConditionsSatisfied = false;
                focusOnView(editTextdrivingLicense);

            }
            if (dl_Checkbox.isChecked() && ( !pan_Checkbox.isChecked()&& !voterId_Checkbox.isChecked())) {
                editTextPAN.setError("Invalid Pan");
                Log.d("TAG", "onClickTAG13: " + "creating Json");
            allConditionsSatisfied = false;
            focusOnView(editTextPAN);

        }
        if (dl_Checkbox.isChecked() && pan_Checkbox.isChecked()) {
            PanNO = editTextPAN.getText().toString();
            DrivingLic = editTextdrivingLicense.getText().toString();
        }
        if (pan_Checkbox.isChecked() && voterId_Checkbox.isChecked()) {
            PanNO = editTextPAN.getText().toString();
            voterId = editTextvoterIdKyc.getText().toString();
        }
        if (dl_Checkbox.isChecked() && voterId_Checkbox.isChecked()) {
            voterId = editTextvoterIdKyc.getText().toString();
            DrivingLic = editTextdrivingLicense.getText().toString();
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);
*/
        if (validateInputs()) {
            PanNO = editTextPAN.getText().toString();
            DrivingLic = editTextdrivingLicense.getText().toString();
            voterId = editTextvoterIdKyc.getText().toString();
        } else {
            allConditionsSatisfied = false;
        }

        if (!isValidName(editTextFatherFname.getText().toString().isEmpty() ? " " : editTextFatherFname.getText().toString())) {
            editTextFatherFname.setError("Invalid Name");
            Log.d("TAG", "onClickTAG14: " + "creating Json");
            focusOnView(editTextFatherFname);

            allConditionsSatisfied = false;
        } else {
            fatherName = editTextFatherFname.getText().toString();
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (!isValidMName(editTextfathermiddlename.getText().toString().isEmpty() ? " " : editTextfathermiddlename.getText().toString())) {
            editTextfathermiddlename.setError("Invalid Name");
            Log.d("TAG", "onClickTAG15: " + "creating Json");
            focusOnView(editTextfathermiddlename);

            allConditionsSatisfied = false;
        } else {
            fatherMiddleName = editTextfathermiddlename.getText().toString();
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (!isValidMName(editTextfatherlastname.getText().toString().isEmpty() ? " " : editTextfatherlastname.getText().toString())) {
            editTextfatherlastname.setError("Invalid Name");
            Log.d("TAG", "onClickTAG16: " + "creating Json");
            focusOnView(editTextfatherlastname);

            allConditionsSatisfied = false;
        } else {
            fatherLastName = editTextfatherlastname.getText().toString();
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (!isValidName(editTextmotherfirstname.getText().toString().isEmpty() ? " " : editTextmotherfirstname.getText().toString())) {
            editTextmotherfirstname.setError("Invalid Name");
            Log.d("TAG", "onClickTAG17: " + "creating Json");
            focusOnView(editTextmotherfirstname);

            allConditionsSatisfied = false;
        } else {
            motherName = editTextmotherfirstname.getText().toString();
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (!isValidMName(editTextmothermiddlename.getText().toString().isEmpty() ? " " : editTextmothermiddlename.getText().toString())) {
            editTextmothermiddlename.setError("Invalid Name");
            Log.d("TAG", "onClickTAG18: " + "creating Json");
            focusOnView(editTextmothermiddlename);

            allConditionsSatisfied = false;
        } else {
            motherMiddleName = editTextmothermiddlename.getText().toString();
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (!isValidMName(editTextmotherlastname.getText().toString().isEmpty() ? " " : editTextmotherlastname.getText().toString())) {
            editTextmotherlastname.setError("Invalid Name");
            Log.d("TAG", "onClickTAG19: " + "creating Json");
            focusOnView(editTextmotherlastname);

            allConditionsSatisfied = false;
        } else {
            motherLastName = editTextmotherlastname.getText().toString();
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (spouseCardView.getVisibility() == View.VISIBLE) {
            Log.d("TAG", "onClickTAG: " + "spouse");


            if (!isValidName(editTextspousefirstname.getText().toString().isEmpty() ? " " : editTextspousefirstname.getText().toString())) {
                editTextspousefirstname.setError("Invalid Name");
                Log.d("TAG", "onClickTAG20: " + "creating Json");
                focusOnView(editTextspousefirstname);

                allConditionsSatisfied = false;
            } else {
                spouseFirstName = editTextspousefirstname.getText().toString();
            }
            Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

            if (!isValidMName(editTextspousemiddlename.getText().toString().isEmpty() ? " " : editTextspousemiddlename.getText().toString())) {
                editTextspousemiddlename.setError("Invalid Name");
                Log.d("TAG", "onClickTAG21: " + "creating Json");
                focusOnView(editTextspousemiddlename);

                allConditionsSatisfied = false;
            } else {
                spouseMiddleName = editTextspousemiddlename.getText().toString();
            }
            Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

            if (!isValidMName(editTextspouselastname.getText().toString().isEmpty() ? " " : editTextspouselastname.getText().toString())) {
                editTextspouselastname.setError("Invalid Name");
                Log.d("TAG", "onClickTAG22: " + "creating Json");
                focusOnView(editTextspouselastname);

                allConditionsSatisfied = false;
            } else {
                spouseLastName = editTextspouselastname.getText().toString();
            }
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (acspRelationship.getSelectedItem().toString().contains("-Select-")) {
            Toast.makeText(this, "Please select a relationship", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "onClickTAG23: " + "creating Json");
            focusOnView(acspRelationship);

            allConditionsSatisfied = false;
        } else {
            guardianRelatnWithBorrower = acspRelationship.getSelectedItem().toString();
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (acspAadharState.getSelectedItem().toString().contains("-Select-")) {
            Toast.makeText(this, "Please select a state", Toast.LENGTH_SHORT).show();

            Log.d("TAG", "onClickTAG24: " + "creating Json");
            focusOnView(acspAadharState);

            allConditionsSatisfied = false;
        } else {
            P_State = ((RangeCategoryDataClass) acspAadharState.getSelectedItem()).code;
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (acspGender.getSelectedItem().toString().contains("-Select-")) {
            Toast.makeText(this, "Please select a Gender", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "onClickTAG25: " + "creating Json");
            focusOnView(acspGender);

            allConditionsSatisfied = false;
        } else {
            gender = acspGender.getSelectedItem().toString();
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (isMarriedSpinner.getSelectedItem().toString().contains("-Select-")) {
            Toast.makeText(this, "Select Marital Status", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "onClickTAG26: " + "creating Json");
            focusOnView(isMarriedSpinner);

            allConditionsSatisfied = false;
        } else {
            isMarried = isMarriedSpinner.getSelectedItem().toString();
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (!isValidFullName(editTextGuardian.getText().toString())) {
            editTextGuardian.setError("Invalid Name");
            Log.d("TAG", "onClickTAG27: " + "creating Json");
            focusOnView(editTextGuardian);

            allConditionsSatisfied = false;
        } else {
            guardian = editTextGuardian.getText().toString();
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (!isValidAddr(txtVillageName.getText().toString())) {
            txtVillageName.setError("Select village Name");
            Log.d("TAG", "onClickTAG28: " + "creating Json");
            focusOnView(txtVillageName);

            allConditionsSatisfied = false;
        }
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (profileImageFile == null || profileImageFile.getAbsolutePath().isEmpty()) {
            Toast.makeText(this, "Capture Borrower Pic", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "onClickTAG29: " + "creating Json");
            focusOnView(profilePic);

            allConditionsSatisfied = false;
        }

        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        if (guardian != null) {
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
        Log.d("TAG", "onClickTAG1: " + allConditionsSatisfied);

        Log.d("TAG", "onClickTAG: " + "checking  conditions");


        if (allConditionsSatisfied) {
            Log.d("TAG", "onClickTAG: " + allConditionsSatisfied);

            jsonData = new FiJsonObject();
            jsonData.setgroupCode(foCode);
            jsonData.setCreator(creator);
            jsonData.setCityCode(areaCode);
            jsonData.setAadharID(AadharID);
            jsonData.setAge(Age);
            jsonData.setFname(Fname);
            Log.d("TAG", "createJsonObject:F_Fname " + Fname);
            jsonData.setMname(Mname);
            Log.d("TAG", "createJsonObject:F_Fname " + Mname);

            jsonData.setLname(Lname);
            Log.d("TAG", "createJsonObject:F_Fname " + Lname);

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
            jsonData.setGender(gender);
            jsonData.setPState(P_State);

            jsonData.setIsNameVerify(isNameMatched.toString());

            Log.d("TAG", "createJsonObject:123456 " + isNameMatched);

            jsonData.setGuardianRelatnWithBorrower(guardianRelatnWithBorrower);

            fiExtra = new FiExtra();
            fiExtra.setMotherName(motherName);
            fiExtra.setMotherMiddleName(motherMiddleName);
            fiExtra.setMotherLastName(motherLastName);
            fiExtra.setSpouseFirstName(spouseFirstName);
            fiExtra.setSpouseMiddleName(spouseMiddleName);
            fiExtra.setSpouseLastName(spouseLastName);
            fiExtra.setFatherName(fatherName);
            fiExtra.setFatherMiddleName(fatherMiddleName);
            fiExtra.setFatherLastName(fatherLastName);
            jsonData.setFiExtra(fiExtra);


            Log.d("TAG", "jsonobjectstate " + villageCode + " villageCode " + distCode + " distCode " + subDistCode + " subDistCode " + cityCode + " cityCode " + name + " name " + verifiedLicensename + " verifiedLicensename " + verifiedVotername + " verifiedVotername " + verifiedPanName + " verifiedPanName ");

            Log.d("TAG", "onResponsepp: " + "Started");
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonObject> call = apiInterface.CheckLoanByAadhar(GlobalClass.Token, BuildConfig.dbname, AadharID);
            Log.d("TAG", "onResponsepp: " + AadharID);
            Log.d("TAG", "createJsonObject: " + jsonData.toString());

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("TAG", "onResponsepp: " + response.body());
                    if (response.isSuccessful()) {
                        Log.d("TAG", "onResponsepp: " + response.body());
                        Log.d("TAG", "onResponse: " + new Gson().toJson(jsonData));
                        Intent intent = new Intent(KYCActivity.this, KYCActivity2.class);
//                        intent.putExtra("jsonData", jsonData);
                        intent.putExtra("SCHEME_TAG", SCHEME_TAG);
                        intent.putExtra("jsonData", jsonData.toString());
                        intent.putExtra("fiExtra", fiExtra);
                        intent.putExtra("vName", name);
                        intent.putExtra("vPanName", verifiedPanName);
                        intent.putExtra("vVoterIdName", verifiedVotername);
                        intent.putExtra("vLicName", verifiedLicensename);
                        intent.putExtra("cityCode", cityCode);
                        intent.putExtra("distCode", distCode);
                        intent.putExtra("subDistCode", subDistCode);
                        intent.putExtra("villageCode", villageCode);
                        intent.putExtra("stateCode", stateCode);
                        intent.putExtra("ckycNumberExist", String.valueOf(ckycNumberExist));
                        intent.putExtra("file", profileImageFile.getAbsolutePath());

                        SubmitAlert(KYCActivity.this, "Proceed", "Towards Next Page!!!");
                        customProgressDialog.dismiss();
                        startActivity(intent);

                    } else {
                        Log.d("TAG", "onResponsepp: " + response.code());
                        customProgressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("TAG", "onResponsepp: " + "failure");
                    customProgressDialog.dismiss();

                }
            });
        } else {
            Log.d("TAG", "onClickTAG: " + allConditionsSatisfied);
            customProgressDialog.dismiss();

        }


    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(KYCActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                //   String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                String selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                editTextDob.setText(selectedDate);
                //  calculateAge(selectedYear, selectedMonth, selectedDay);
                progressBar.incrementProgressBy(1);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    public void filterCity(String s) {
        Log.d("TAG", "filter: " + s);
        List<CityData> cityData1 = new ArrayList<>();
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
        Log.d("TAG", "filter: " + s);
        List<DistrictData> districtDataModel = new ArrayList<>();
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
        Log.d("TAG", "filter: " + s);
        List<SubDistrictData> subDistrictDataModel = new ArrayList<>();
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
        Log.d("TAG", "filter: " + s);
        List<VillageData> villageDataModel = new ArrayList<>();
        for (int i = 0; i < villageDataList.size(); i++) {

            if (villageDataList.get(i).getVillagENAME().toUpperCase().contains(s.toString())) {
                villageDataModel.add(villageDataList.get(i));
                //   Log.d("TAG", "filter: "+list.get(i));
            }
        }
        villageListAdapter.filterList(villageDataModel);
        villageListAdapter.notifyDataSetChanged();
    }

    private void showCityDialog(TextView txtCityName, String stateCode) {
        customProgressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Dialog dialogSearch = new Dialog(KYCActivity.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.setCancelable(true);
        dialogSearch.getWindow().setLayout(1000, 1600);
        EditText edit_text = dialogSearch.findViewById(R.id.edit_text);
        TextView dialog_name = dialogSearch.findViewById(R.id.dialog_name);
        dialog_name.setText("Select City");
        RecyclerView recViewOfCreator = dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //   Log.d("TAG", "onDismiss: hiiitt"+choosedCreator);
                try {
                    Log.d("TAG", "onDismiss: " + cityData.getCitYNAME());
                    txtCityName.setText(cityData.getCitYNAME());
                    cityCode = cityData.getCitYCODE().toString();
                    Log.d("TAG", "onDismiss: " + cityCode);

                } catch (NullPointerException e) {
                    e.printStackTrace();

                }


            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        Call<CityModelList> call = apiInterface.getCityList(GlobalClass.Token, GlobalClass.dbname, stateCode);
        call.enqueue(new Callback<CityModelList>() {
            @Override
            public void onResponse(Call<CityModelList> call, Response<CityModelList> response) {


                if (cityDataList.size() > 1)
                    cityDataList.clear();
                cityDataList.addAll(response.body().getData());
                cityListAdapter = new CityListAdapter(KYCActivity.this, cityDataList, dialogSearch, cityChooseListner);
                recViewOfCreator.setAdapter(cityListAdapter);
                cityListAdapter.notifyDataSetChanged();
                if (cityDataList.size() >= 1) {
                    dialogSearch.show();
                } else {
                    Toast.makeText(KYCActivity.this, "Village List is not coming for " + stateCode, Toast.LENGTH_SHORT).show();
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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

    private void showDistrictDialog(TextView txtVVillageName, String districtCode) {
        customProgressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Dialog dialogSearch = new Dialog(KYCActivity.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.setCancelable(false);
        dialogSearch.getWindow().setLayout(1000, 1600);
        EditText edit_text = dialogSearch.findViewById(R.id.edit_text);
        TextView dialog_name = dialogSearch.findViewById(R.id.dialog_name);
        dialog_name.setText("Select District");
        RecyclerView recViewOfCreator = dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //   Log.d("TAG", "onDismiss: hiiitt"+choosedCreator);
                try {
                    txtVVillageName.setText(districtDat.getDisTNAME());
                    distCode = districtDat.getDisTCODE().toString();
                } catch (NullPointerException e) {

                }
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        Call<DistrictListModel> call = apiInterface.getDistictList(GlobalClass.Token, GlobalClass.dbname, districtCode);
        call.enqueue(new Callback<DistrictListModel>() {
            @Override
            public void onResponse(Call<DistrictListModel> call, Response<DistrictListModel> response) {
                Log.d("TAG", "districtDataList: " + response.body());

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
                } else {
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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

    private void showSubDistrictDialog(TextView txtVVillageName, String districtCode) {
        customProgressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Dialog dialogSearch = new Dialog(KYCActivity.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.setCancelable(false);
        dialogSearch.getWindow().setLayout(1000, 1600);
        EditText edit_text = dialogSearch.findViewById(R.id.edit_text);
        TextView dialog_name = dialogSearch.findViewById(R.id.dialog_name);
        dialog_name.setText("Select Sub-District");
        RecyclerView recViewOfCreator = dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //   Log.d("TAG", "onDismiss: hiiitt"+choosedCreator);

                try {
                    txtVVillageName.setText(subDistrictData.getSuBDISTNAME());
                    subDistCode = subDistrictData.getSuBDISTCODE().toString();
                } catch (NullPointerException e) {

                }
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        Call<SubDistrictModel> call = apiInterface.getSubDistrictList(GlobalClass.Token, GlobalClass.dbname, districtCode);
        call.enqueue(new Callback<SubDistrictModel>() {
            @Override
            public void onResponse(Call<SubDistrictModel> call, Response<SubDistrictModel> response) {
                if (subDistrictDataList.size() > 1)
                    subDistrictDataList.clear();
                try {
                    subDistrictDataList.addAll(response.body().getData());
                    SubDistrictData other = new SubDistrictData("2222222222", "Other", "22222");
                    subDistrictDataList.add(other);
                    subDistrictListAdapter = new SubDistrictListAdapter(KYCActivity.this, subDistrictDataList, dialogSearch, listSubDistructInteraction);
                    recViewOfCreator.setAdapter(subDistrictListAdapter);
                    subDistrictListAdapter.notifyDataSetChanged();
                    if (subDistrictDataList.size() >= 1) {
                        dialogSearch.show();
                    } else {
                        Toast.makeText(KYCActivity.this, "district list is not coming for " + districtCode, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception c) {
                    SubDistrictData other = new SubDistrictData("2222222222", "Other", "22222");
                    subDistrictDataList.add(other);
                }
                customProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SubDistrictModel> call, Throwable t) {
                SubDistrictData other = new SubDistrictData("2222222222", "Other", "22222");
                subDistrictDataList.add(other);
                customProgressDialog.dismiss();
            }
        });

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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
        Dialog dialogSearch = new Dialog(KYCActivity.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.setCancelable(false);
        dialogSearch.getWindow().setLayout(1000, 1600);
        EditText edit_text = dialogSearch.findViewById(R.id.edit_text);
        TextView dialog_name = dialogSearch.findViewById(R.id.dialog_name);
        dialog_name.setText("Select Village");
        RecyclerView recViewOfCreator = dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                try {
                    Log.d("TAG", "onDismiss: " + villageData.getVillagENAME());
                    txtVDistrictName.setText(villageData.getVillagENAME());
                    villageCode = villageData.getVillagECODE().toString();
                } catch (NullPointerException e) {
                }
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        Call<VillageListModel> call = apiInterface.getVillageList(GlobalClass.Token, GlobalClass.dbname, stateCode, disTCODE, suBDISTCODE);
        call.enqueue(new Callback<VillageListModel>() {
            @Override
            public void onResponse(Call<VillageListModel> call, Response<VillageListModel> response) {
                if (villageDataList.size() > 1)
                    villageDataList.clear();
                try {
                    villageDataList.addAll(response.body().getData());
                    VillageData other = new VillageData("2222222222222222", "Other", "000", "0000", "0000");
                    villageDataList.add(other);
                    villageListAdapter = new VillageListAdapter(KYCActivity.this, villageDataList, dialogSearch, listVillageInteraction);
                    recViewOfCreator.setAdapter(villageListAdapter);
                    villageListAdapter.notifyDataSetChanged();
                    if (villageDataList.size() >= 1) {
                        dialogSearch.show();
                    } else {
                        Toast.makeText(KYCActivity.this, "district list is not coming", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    VillageData other = new VillageData("2222222222222222", "Other", "000", "0000", "0000");
                    villageDataList.add(other);
                }
                customProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<VillageListModel> call, Throwable t) {
                VillageData other = new VillageData("2222222222222222", "Other", "000", "0000", "0000");
                villageDataList.add(other);
                customProgressDialog.dismiss();
            }
        });

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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
        districtDat = districtDatas;
    }

    @Override
    public void VillageChooseListner(VillageData villageDatas) {
        villageData = villageDatas;
    }

    @Override
    public void CityChooseListner(CityData cityDatas) {
        cityData = cityDatas;
    }

    @Override
    public void SubDistChooseListner(SubDistrictData subDistrictDatas) {
        subDistrictData = subDistrictDatas;
    }

    private void focusOnView(View view) {
        view.requestFocus();
        scrollView.post(() -> {
            scrollView.smoothScrollTo(0, view.getTop());
            // Add highlighting animation
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0.3f, 1f);
            animator.setDuration(900);
            animator.start();
        });
    }

    private boolean validateInputs() {
        String pan = editTextPAN.getText().toString().trim();
        String voter = editTextvoterIdKyc.getText().toString().trim();
        String license = editTextdrivingLicense.getText().toString().trim();

        String panName = tilPanName.getText().toString().trim();
        String voterName = tilVoterName.getText().toString().trim();
        String licenseName = tilDLName.getText().toString().trim();

        boolean isPanVerified = !TextUtils.isEmpty(pan) && !TextUtils.isEmpty(panName);
        boolean isVoterVerified = !TextUtils.isEmpty(voter) && !TextUtils.isEmpty(voterName);
        boolean isLicenseVerified = !TextUtils.isEmpty(license) && !TextUtils.isEmpty(licenseName);

        if (!isPanVerified && !isVoterVerified && !isLicenseVerified) {
            Utils.alert(this, "Please verify at least one ID");
            return false;
        } else if (isPanVerified && (!isVoterVerified && !isLicenseVerified)) {
            if (!TextUtils.isEmpty(voter) && !isVoterVerified) {
                Utils.alert(this, "Voter ID is not verified");
            } else if (!TextUtils.isEmpty(license) && !isLicenseVerified) {
                Utils.alert(this, "Driving License is not verified");
            } else {

                Utils.alert(this, "Please enter either Voter ID or Driving License");
            }
            return false;
        }

        if (!isPanVerified && !TextUtils.isEmpty(pan)) {
            Utils.alert(this, "PAN ID is not verified");
        }
        if (!isVoterVerified && !TextUtils.isEmpty(voter)) {
            Utils.alert(this, "Voter ID is not verified");
        }
        if (!isLicenseVerified && !TextUtils.isEmpty(license)) {
            Utils.alert(this, "Driving License is not verified");
        }

        return isVoterVerified || (isLicenseVerified && isPanVerified) || (isPanVerified && (isVoterVerified || isLicenseVerified));
    }

    private void removeError() {
        editTextMobile.setError(null);
        editTextdrivingLicense.setError(null);
        tilPanName.setError(null);
        editTextvoterIdKyc.setError(null);
        editTextGuardian.setError(null);
        editTextAadhar.setError(null);
        editTextAge.setError(null);
        editTextName.setError(null);
        editTextDob.setError(null);
        editTextAddress1.setError(null);
        editTextAddress2.setError(null);
        editTextAddress3.setError(null);
        editTextCity.setError(null);
        editTextPincode.setError(null);
        editTextmotherlastname.setError(null);
        editTextmothermiddlename.setError(null);
        editTextmotherfirstname.setError(null);
        editTextfatherlastname.setError(null);
        editTextfathermiddlename.setError(null);
        editTextFatherFname.setError(null);
        editTextspousefirstname.setError(null);
        editTextspousemiddlename.setError(null);
        editTextspouselastname.setError(null);
        editTextGuardian.setError(null);
        txtVillageName.setError(null);
    }
}
