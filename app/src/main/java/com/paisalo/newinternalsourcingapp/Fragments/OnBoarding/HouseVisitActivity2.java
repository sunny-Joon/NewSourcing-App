package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;


import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.HouseVisitModels.HouseVisitSaveModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.RangeCategoryModels.RangeCategoryModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


public class HouseVisitActivity2 extends AppCompatActivity {

    public static final String CAMERA_PREF = "camera_pref";
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private static final String ALLOW_KEY = "ALLOWED";

    Uri imageUri;

    CheckBox buildType,approvedLocation,cpfCriteria,cpfAddressVerification,IdVerification,addressVerification,ageVerification,cpfRecentAddressVerification
            ,stampOnPhotocopy,lastLoanVerification,absentReason,repaymentFault,reasonVerification,isAppliedAmountAppropriate,familyAwareness,loanRelatedToBusiness,
            IsBusinessAppropriate,repayEligibility,surplusVerification,incomeVerification,businessVerification,comissionDemand,supportiveToGroup,groupReadyToVilay,groupHasBloodRelation,
            understandsInsurance,verifyExternalLoan,understandsFaultPolicy,IsLoanAmountAppropriate,toatlDebtUnderLimit,workingPlaceVerification,IsWorkingPlaceValid,workingPlacedescription,
            workExperience,seasonDependency,stockVerification,sufficientAmount;

    EditText branchName,area,centre,group,loanUsagePercentage,interviewee_name,interviewee_age,
            distance_ApplicantToDealer,time_ApplicantToDealer,approxMonthlySales,
            approxMonthlyIncome,businessExpenditure,expectedIncome,houseExpenditure,familyNetIncome,
            reference1,reference_PhoneNo1,reference2,reference_PhoneNo2,address;

    Spinner relationWithApplicant,residingWith,residence_type,residental_stability,businessExperience,neighbourhood_verification,intervieweeRelation;

    Button submit,click;
    private ProgressDialog progressDialog;
    ImageView view;
    String buildType_value="Y",approvedLocation_value="Y",cpfCriteria_value="Y",cpfAddressVerification_value="Y",IdVerification_value="Y",addressVerification_value="Y",ageVerification_value="Y",cpfRecentAddressVerification_value="Y"
            ,stampOnPhotocopy_value="Y",lastLoanVerification_value="Y",absentReason_value="Y",repaymentFault_value="Y",reasonVerification_value="Y",isAppliedAmountAppropriate_value="Y",familyAwareness_value="Y",loanRelatedToBusiness_value="Y",
            IsBusinessAppropriate_value="Y",repayEligibility_value="Y",surplusVerification_value="Y",incomeVerification_value="Y",businessVerification_value="Y",comissionDemand_value="Y",supportiveToGroup_value="Y",groupReadyToVilay_value="Y",groupHasBloodRelation_value="Y",
            understandsInsurance_value="Y",verifyExternalLoan_value="Y",understandsFaultPolicy_value="Y",IsLoanAmountAppropriate_value="Y",toatlDebtUnderLimit_value="Y",workingPlaceVerification_value="Y",IsWorkingPlaceValid_value="Y",workingPlacedescription_value="Y",
            workExperience_value="Y",seasonDependency_value="Y",stockVerification_value="Y",sufficientAmount_value="Y";

    String Sresidence_type = "",SresidingWith = "",Sresidental_stability = "",SbusinessExperience = "",Sneighbourhood_verification = "",SintervieweeRelation = "",SrelationWithApplicant = "";
    String fiNo,rentOfHouse,groupCode,cityCode,latitude,longitude,creator,empCode;

    static File photoFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_visit2);

        Intent intent = getIntent();
        fiNo = intent.getStringExtra("FiCode");
        rentOfHouse = intent.getStringExtra("Rent_of_House");
        groupCode = intent.getStringExtra("GroupCode");
        cityCode = intent.getStringExtra("CityCode");
        latitude = intent.getStringExtra("Latitude");
        longitude = intent.getStringExtra("Longitude");
        creator = intent.getStringExtra("Creator");
        empCode = GlobalClass.Id;

        /*Edit Text*/
        branchName = findViewById(R.id.branchName);
        area = findViewById(R.id.area);
        centre = findViewById(R.id.centre);
        group = findViewById(R.id.group);
        interviewee_name = findViewById(R.id.interviewee_name);
        interviewee_age = findViewById(R.id.interviewee_age);
        residental_stability = findViewById(R.id.residental_stability);
        distance_ApplicantToDealer = findViewById(R.id.distance_ApplicantToDealer);
        time_ApplicantToDealer = findViewById(R.id.time_ApplicantToDealer);
        approxMonthlySales = findViewById(R.id.approxMonthlySales);
        approxMonthlyIncome = findViewById(R.id.approxMonthlyIncome);
        businessExperience = findViewById(R.id.businessExperience);
        businessExpenditure = findViewById(R.id.businessExpenditure);
        expectedIncome = findViewById(R.id.expectedIncome);
        houseExpenditure = findViewById(R.id.houseExpenditure);
        familyNetIncome = findViewById(R.id.familyNetIncome);
        reference1 = findViewById(R.id.reference1);
        reference_PhoneNo1 = findViewById(R.id.reference_PhoneNo1);
        reference2 = findViewById(R.id.reference2);
        reference_PhoneNo2 = findViewById(R.id.reference_PhoneNo2);
        neighbourhood_verification = findViewById(R.id.neighbourhood_verification);
        address = findViewById(R.id.address);

        loanUsagePercentage = findViewById(R.id.loanUsagePercentage);

        CheckBox
        buildType = findViewById(R.id.buildType);
        approvedLocation = findViewById(R.id.approvedLocation);
        cpfCriteria = findViewById(R.id.cpfCriteria);
        cpfAddressVerification = findViewById(R.id.cpfAddressVerification);
        IdVerification = findViewById(R.id.IdVerification);
        addressVerification = findViewById(R.id.addressVerification);
        ageVerification = findViewById(R.id.ageVerification);
        cpfRecentAddressVerification = findViewById(R.id.cpfRecentAddressVerification);
        stampOnPhotocopy = findViewById(R.id.stampOnPhotocopy);
        lastLoanVerification = findViewById(R.id.lastLoanVerification);
        absentReason = findViewById(R.id.absentReason);
        repaymentFault = findViewById(R.id.repaymentFault);
        reasonVerification = findViewById(R.id.reasonVerification);
        isAppliedAmountAppropriate = findViewById(R.id.isAppliedAmountAppropriate);
        familyAwareness = findViewById(R.id.familyAwareness);
        loanRelatedToBusiness = findViewById(R.id.loanRelatedToBusiness);
        IsBusinessAppropriate = findViewById(R.id.IsBusinessAppropriate);
        repayEligibility = findViewById(R.id.repayEligibility);
        surplusVerification = findViewById(R.id.surplusVerification);
        incomeVerification = findViewById(R.id.incomeVerification);
        businessVerification = findViewById(R.id.businessVerification);
        comissionDemand = findViewById(R.id.comissionDemand);
        supportiveToGroup = findViewById(R.id.supportiveToGroup);
        groupReadyToVilay = findViewById(R.id.groupReadyToVilay);
        groupHasBloodRelation = findViewById(R.id.groupHasBloodRelation);
        understandsInsurance = findViewById(R.id.understandsInsurance);
        verifyExternalLoan = findViewById(R.id.verifyExternalLoan);
        understandsFaultPolicy = findViewById(R.id.understandsFaultPolicy);
        IsLoanAmountAppropriate = findViewById(R.id.IsLoanAmountAppropriate);
        toatlDebtUnderLimit = findViewById(R.id.toatlDebtUnderLimit);
        workingPlaceVerification = findViewById(R.id.workingPlaceVerification);
        IsWorkingPlaceValid = findViewById(R.id.IsWorkingPlaceValid);
        workingPlacedescription = findViewById(R.id.workingPlacedescription);
        workExperience = findViewById(R.id.workExperience);
        seasonDependency = findViewById(R.id.seasonDependency);
        stockVerification = findViewById(R.id.stockVerification);
        sufficientAmount = findViewById(R.id.sufficientAmount);

        /*Spinner*/
        relationWithApplicant = findViewById(R.id.relationWithApplicant);
        residingWith = findViewById(R.id.residingWith);
        residence_type = findViewById(R.id.residenceType);
        residental_stability = findViewById(R.id.residental_stability);
        businessExperience = findViewById(R.id.businessExperience);
        neighbourhood_verification = findViewById(R.id.neighbourhood_verification);
        intervieweeRelation = findViewById(R.id.intervieweeRelation);

        /*Button*/
        submit = findViewById(R.id.submit);
        click = findViewById(R.id.click);
        view = findViewById(R.id.view);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateFields();
                if(validateFields()){

                    progressDialog = new ProgressDialog(HouseVisitActivity2.this);
                    progressDialog.setMessage("Saving...");
                    progressDialog.setCancelable(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();

                    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), photoFile);
                    builder.addFormDataPart("Image", photoFile.getName(), surveyBody);
                    builder.addFormDataPart("HouseType", buildType_value == null ? "" : buildType_value);
                    builder.addFormDataPart("IsvalidLocation", approvedLocation_value == null ? "" : approvedLocation_value);
                    builder.addFormDataPart("CPFlifeStyle", cpfCriteria_value == null ? "" : cpfCriteria_value);
                    builder.addFormDataPart("CpfPOAddressVerify", cpfAddressVerification_value == null ? "" : cpfAddressVerification_value);
                    builder.addFormDataPart("PhotoIdVerification", IdVerification_value == null ? "" : IdVerification_value);
                    builder.addFormDataPart("CurrentAddressprof", addressVerification_value == null ? "" : addressVerification_value);
                    builder.addFormDataPart("HasbandWifeAgeverificaton", ageVerification_value == null ? "" : ageVerification_value);
                    builder.addFormDataPart("ParmanentAddressPincode", cpfRecentAddressVerification_value == null ? "" : cpfRecentAddressVerification_value);
                    builder.addFormDataPart("StampOnPhotocopy", stampOnPhotocopy_value == null ? "" : stampOnPhotocopy_value);
                    builder.addFormDataPart("LastLoanVerification", lastLoanVerification_value == null ? "" : lastLoanVerification_value);
                    builder.addFormDataPart("AbsentReasonInCentermeeting", absentReason_value == null ? "" : absentReason_value);
                    builder.addFormDataPart("RepaymentFault", repaymentFault_value == null ? "" : repaymentFault_value);
                    builder.addFormDataPart("LoanreasonVerification", reasonVerification_value == null ? "" : reasonVerification_value);
                    builder.addFormDataPart("IsAppliedAmountAppropriate", isAppliedAmountAppropriate_value == null ? "" : isAppliedAmountAppropriate_value);
                    builder.addFormDataPart("FamilyAwarenessaboutloan", familyAwareness_value == null ? "" : familyAwareness_value);
                    builder.addFormDataPart("Businessaffectedourrelation", loanRelatedToBusiness_value == null ? "" : loanRelatedToBusiness_value);
                    builder.addFormDataPart("IsloanAppropriateforBusiness", IsBusinessAppropriate_value == null ? "" : IsBusinessAppropriate_value);
                    builder.addFormDataPart("Repayeligiblity", repayEligibility_value == null ? "" : repayEligibility_value);
                    builder.addFormDataPart("Cashflowoffamily", surplusVerification_value == null ? "" : surplusVerification_value);
                    builder.addFormDataPart("IncomeMatchedwithprofile", incomeVerification_value == null ? "" : incomeVerification_value);
                    builder.addFormDataPart("BusinessVerification", businessVerification_value == null ? "" : businessVerification_value);
                    builder.addFormDataPart("ComissionDemand", comissionDemand_value == null ? "" : comissionDemand_value);
                    builder.addFormDataPart("BorrowersupportedGroup", supportiveToGroup_value == null ? "" : supportiveToGroup_value);
                    builder.addFormDataPart("GroupReadyToVilay", groupReadyToVilay_value == null ? "" : groupReadyToVilay_value);
                    builder.addFormDataPart("GroupHasBloodRelation", groupHasBloodRelation_value == null ? "" : groupHasBloodRelation_value);
                    builder.addFormDataPart("UnderstandInsaurancePolicy", understandsInsurance_value == null ? "" : understandsInsurance_value);
                    builder.addFormDataPart("VerifyExternalLoan", verifyExternalLoan_value == null ? "" : verifyExternalLoan_value);
                    builder.addFormDataPart("UnderstandsFaultPolicy", understandsFaultPolicy_value == null ? "" : understandsFaultPolicy_value);
                    builder.addFormDataPart("OverlimitLoan_borrowfromgroup", IsLoanAmountAppropriate_value == null ? "" : IsLoanAmountAppropriate_value);
                    builder.addFormDataPart("ToatlDebtUnderLimit", toatlDebtUnderLimit_value == null ? "" : toatlDebtUnderLimit_value);
                    builder.addFormDataPart("WorkingPlaceVerification", workingPlaceVerification_value == null ? "" : workingPlaceVerification_value);
                    builder.addFormDataPart("IsWorkingPlaceValid", IsWorkingPlaceValid_value == null ? "" : IsWorkingPlaceValid_value);
                    builder.addFormDataPart("WorkingPlacedescription", workingPlacedescription_value == null ? "" : workingPlacedescription_value);
                    builder.addFormDataPart("WorkExperience", workExperience_value == null ? "" : workExperience_value);
                    builder.addFormDataPart("SeasonDependency", seasonDependency_value == null ? "" : seasonDependency_value);
                    builder.addFormDataPart("StockVerification", stockVerification_value == null ? "" : stockVerification_value);
                    builder.addFormDataPart("Loansufficientwithdebt", sufficientAmount_value == null ? "" : sufficientAmount_value);
                    builder.addFormDataPart("BranchName", branchName.getText().toString());
                    builder.addFormDataPart("Center", centre.getText().toString());
                    builder.addFormDataPart("AreaName", area.getText().toString());
                    builder.addFormDataPart("GroupName", group.getText().toString());
                    builder.addFormDataPart("LoanUsagePercentage", loanUsagePercentage.getText().toString());
                    builder.addFormDataPart("AgeofInterviewed", interviewee_age.getText().toString());
                    builder.addFormDataPart("NameofInterviewed", interviewee_name.getText().toString());
                    builder.addFormDataPart("Distancetobranch", distance_ApplicantToDealer.getText().toString());
                    builder.addFormDataPart("Timetoreachbranch", time_ApplicantToDealer.getText().toString());
                    builder.addFormDataPart("MonthlySales", approxMonthlySales.getText().toString());
                    builder.addFormDataPart("Netmonthlyincome_afterproposedloan", expectedIncome.getText().toString());
                    builder.addFormDataPart("Totalmonthlyhouseholdexpenses", houseExpenditure.getText().toString());
                    builder.addFormDataPart("Totalmonthlyexpensesofoccupation", businessExpenditure.getText().toString());
                    builder.addFormDataPart("Netmonthlyincomeotherfamilymembers", familyNetIncome.getText().toString());
                    builder.addFormDataPart("MonthlyIncome", approxMonthlyIncome.getText().toString());
                    builder.addFormDataPart("Relationearningmember", SrelationWithApplicant);
                    builder.addFormDataPart("Namereferenceperson1", reference1.getText().toString());
                    builder.addFormDataPart("Mobilereferenceperson1", reference_PhoneNo1.getText().toString());
                    builder.addFormDataPart("Namereferenceperson2", reference2.getText().toString());
                    builder.addFormDataPart("Mobilereferenceperson2", reference_PhoneNo2.getText().toString());
                    builder.addFormDataPart("Residing_with", SresidingWith);
                    builder.addFormDataPart("Residence_Type", Sresidence_type);
                    builder.addFormDataPart("Residential_Stability", residental_stability.getSelectedItem().toString());
                    builder.addFormDataPart("TotalExperienceOccupation", businessExperience.getSelectedItem().toString());
                    builder.addFormDataPart("Feedbacknearbyresident", Sneighbourhood_verification);
                    builder.addFormDataPart("RelationofInterviewer", SintervieweeRelation);
                    builder.addFormDataPart("Applicant_Status", "N");
                    builder.addFormDataPart("FamilymemberfromPaisalo", "0");
                    builder.addFormDataPart("HouseMonthlyRent", rentOfHouse);
                    builder.addFormDataPart("Latitude", latitude);
                    builder.addFormDataPart("Longitude", longitude);
                    builder.addFormDataPart("FICode", fiNo);
                    builder.addFormDataPart("Creator", creator);
                    builder.addFormDataPart("AreaCode", cityCode);
                    builder.addFormDataPart("GroupCode", groupCode);
                    builder.addFormDataPart("Address", address.getText().toString());
                    builder.addFormDataPart("EmpCode", empCode);


                    RequestBody requestBody = builder.build();
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<HouseVisitSaveModel> call=apiInterface.SaveHouseVisit(GlobalClass.Token, BuildConfig.dbname,requestBody);
                    call.enqueue(new Callback<HouseVisitSaveModel>() {
                        @Override
                        public void onResponse(Call<HouseVisitSaveModel> call, Response<HouseVisitSaveModel> response) {
                            Log.d("TAG", "housevisit: "+ response.body());
                            if(response.isSuccessful()){
                                Log.d("TAG", "housevisit: "+ response.body());
                                assert response.body() != null;
                                Log.d("TAG", "housevisit: "+ response.body().getMessage().toString());

                                progressDialog.dismiss();
                            }else {
                                Log.d("TAG", "housevisit: "+ response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<HouseVisitSaveModel> call, Throwable t) {
                            Log.d("TAG", "housevisit: "+ "failure");
                        }
                    });


                }
            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(HouseVisitActivity2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (getFromPref(HouseVisitActivity2.this, ALLOW_KEY)) {
                        showSettingsAlert();
                    } else if (ContextCompat.checkSelfPermission(HouseVisitActivity2.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(HouseVisitActivity2.this, Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            ActivityCompat.requestPermissions(HouseVisitActivity2.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    openCamera();
                }
            }
        });

        this.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Intent intent = new Intent(HouseVisitActivity2.this, PreviewActivity.class);
                intent.putExtra("imageUri", imageUri.toString());
                startActivity(intent);
            }
        });

        buildType.setChecked(true);
        approvedLocation.setChecked(true);
        cpfCriteria.setChecked(true);
        cpfAddressVerification.setChecked(true);
        IdVerification.setChecked(true);
        addressVerification.setChecked(true);
        ageVerification.setChecked(true);
        cpfRecentAddressVerification.setChecked(true);
        stampOnPhotocopy.setChecked(true);
        lastLoanVerification.setChecked(true);
        absentReason.setChecked(true);
        repaymentFault.setChecked(true);
        reasonVerification.setChecked(true);
        isAppliedAmountAppropriate.setChecked(true);
        familyAwareness.setChecked(true);
        loanRelatedToBusiness.setChecked(true);
        IsBusinessAppropriate.setChecked(true);
        repayEligibility.setChecked(true);
        surplusVerification.setChecked(true);
        incomeVerification.setChecked(true);
        businessVerification.setChecked(true);
        comissionDemand.setChecked(true);
        supportiveToGroup.setChecked(true);
        groupReadyToVilay.setChecked(true);
        groupHasBloodRelation.setChecked(true);
        understandsInsurance.setChecked(true);
        verifyExternalLoan.setChecked(true);
        understandsFaultPolicy.setChecked(true);
        IsLoanAmountAppropriate.setChecked(true);
        toatlDebtUnderLimit.setChecked(true);
        workingPlaceVerification.setChecked(true);
        IsWorkingPlaceValid.setChecked(true);
        workingPlacedescription.setChecked(true);
        workExperience.setChecked(true);
        seasonDependency.setChecked(true);
        stockVerification.setChecked(true);
        sufficientAmount.setChecked(true);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.residing_with, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        residingWith.setAdapter(adapter1);

        residingWith.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) {
                    SresidingWith = "F";
                }
                if (position == 1) {
                    SresidingWith = "Single";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.residing_type, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        residence_type.setAdapter(adapter2);

        residence_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Check if the selected item is at position 0
                if (position == 0) {
                    Sresidence_type = "Pucca";
                }
                if (position == 1) {
                    Sresidence_type = "Kaccha";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.years, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        residental_stability.setAdapter(adapter3);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.years, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        businessExperience.setAdapter(adapter4);

        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this, R.array.ratings, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        neighbourhood_verification.setAdapter(adapter5);

        neighbourhood_verification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Check if the selected item is at position 0
                if (position == 0) {
                    Sneighbourhood_verification = "Good";
                }
                if (position == 1) {
                    Sneighbourhood_verification = "Bad";
                }
                if (position == 2) {
                    Sneighbourhood_verification = "Dont Know";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this, R.array.relations, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervieweeRelation.setAdapter(adapter6);

        intervieweeRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Check if the selected item is at position 0
                if (position == 0) {
                    SintervieweeRelation = "Spouse";
                }
                if (position == 1) {
                    SintervieweeRelation = "Parent";
                }
                if (position == 1) {
                    SintervieweeRelation = "Sibling";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(this, R.array.relation, android.R.layout.simple_spinner_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationWithApplicant.setAdapter(adapter7);

        relationWithApplicant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Check if the selected item is at position 0
                if (position == 0) {
                    SrelationWithApplicant = "Mother";
                }
                if (position == 1) {
                    SrelationWithApplicant = "Father";
                }
                if (position == 2) {
                    SrelationWithApplicant = "Husband";
                }
                if (position == 3) {
                    SrelationWithApplicant = "Wife";
                }
                if (position == 4) {
                    SrelationWithApplicant = "Brother";
                }
                if (position == 5) {
                    SrelationWithApplicant = "Sister";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        buildType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check==true){
                    buildType_value="Y";
                }else{
                    buildType_value="N";
                }
            }
        });
        approvedLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    approvedLocation_value="Y";
                }else{
                    approvedLocation_value="N";
                }
            }
        });
        cpfCriteria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    cpfCriteria_value="Y";
                }else{
                    cpfCriteria_value="N";
                }
            }
        });
        cpfAddressVerification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    cpfAddressVerification_value="Y";
                }else{
                    cpfAddressVerification_value="N";
                }
            }
        });
        IdVerification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    IdVerification_value="Y";
                }else{
                    IdVerification_value="N";
                }
            }
        });
        addressVerification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    addressVerification_value="Y";
                }else{
                    addressVerification_value="N";
                }
            }
        });
        ageVerification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    ageVerification_value="Y";
                }else{
                    ageVerification_value="N";
                }
            }
        });
        cpfRecentAddressVerification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    cpfRecentAddressVerification_value="Y";
                }else{
                    cpfRecentAddressVerification_value="N";
                }
            }
        });
        stampOnPhotocopy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    stampOnPhotocopy_value="Y";
                }else{
                    stampOnPhotocopy_value="N";
                }
            }
        });
        lastLoanVerification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    lastLoanVerification_value="Y";
                }else{
                    lastLoanVerification_value="N";
                }
            }
        });
        absentReason.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    absentReason_value="Y";
                }else{
                    absentReason_value="N";
                }
            }
        });
        repaymentFault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    repaymentFault_value="Y";
                }else{
                    repaymentFault_value="N";
                }
            }
        });
        reasonVerification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    reasonVerification_value="Y";
                }else{
                    reasonVerification_value="N";
                }
            }
        });
        isAppliedAmountAppropriate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    isAppliedAmountAppropriate_value="Y";
                }else{
                    isAppliedAmountAppropriate_value="N";
                }
            }
        });
        familyAwareness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    familyAwareness_value="Y";
                }else{
                    familyAwareness_value="N";
                }
            }
        });
        loanRelatedToBusiness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    loanRelatedToBusiness_value="Y";
                }else{
                    loanRelatedToBusiness_value="N";
                }
            }
        });
        IsBusinessAppropriate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    IsBusinessAppropriate_value="Y";
                }else{
                    IsBusinessAppropriate_value="N";
                }
            }
        });
        repayEligibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    repayEligibility_value="Y";
                }else{
                    repayEligibility_value="N";
                }
            }
        });
        surplusVerification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    surplusVerification_value="Y";
                }else{
                    surplusVerification_value="N";
                }
            }
        });
        incomeVerification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    incomeVerification_value="Y";
                }else{
                    incomeVerification_value="N";
                }
            }
        });
        businessVerification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    businessVerification_value="Y";
                }else{
                    businessVerification_value="N";
                }
            }
        });
        comissionDemand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    comissionDemand_value="Y";
                }else{
                    comissionDemand_value="N";
                }
            }
        });
        supportiveToGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    supportiveToGroup_value="Y";
                }else{
                    supportiveToGroup_value="N";
                }
            }
        });
        groupReadyToVilay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    groupReadyToVilay_value="Y";
                }else{
                    groupReadyToVilay_value="N";
                }
            }
        });
        groupHasBloodRelation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    groupHasBloodRelation_value="Y";
                }else{
                    groupHasBloodRelation_value="N";
                }
            }
        });
        understandsInsurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    understandsInsurance_value="Y";
                }else{
                    understandsInsurance_value="N";
                }
            }
        });
        verifyExternalLoan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    verifyExternalLoan_value="Y";
                }else{
                    verifyExternalLoan_value="N";
                }
            }
        });
        understandsFaultPolicy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    understandsFaultPolicy_value="Y";
                }else{
                    understandsFaultPolicy_value="N";
                }
            }
        });
        IsLoanAmountAppropriate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    IsLoanAmountAppropriate_value="Y";
                }else{
                    IsLoanAmountAppropriate_value="N";
                }
            }
        });
        toatlDebtUnderLimit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    toatlDebtUnderLimit_value="Y";
                }else{
                    toatlDebtUnderLimit_value="N";
                }
            }
        });
        workingPlaceVerification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    workingPlaceVerification_value="Y";
                }else{
                    workingPlaceVerification_value="N";
                }
            }
        });
        IsWorkingPlaceValid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    IsWorkingPlaceValid_value="Y";
                }else{
                    IsWorkingPlaceValid_value="N";
                }
            }
        });
        workingPlacedescription.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    workingPlacedescription_value="Y";
                }else{
                    workingPlacedescription_value="N";
                }
            }
        });
        workExperience.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    workExperience_value="Y";
                }else{
                    workExperience_value="N";
                }
            }
        });
        seasonDependency.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    seasonDependency_value="Y";
                }else{
                    seasonDependency_value="N";
                }
            }
        });
        stockVerification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    stockVerification_value="Y";
                }else{
                    stockVerification_value="N";
                }
            }
        });
        sufficientAmount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check == true){
                    sufficientAmount_value="Y";
                }else{
                    sufficientAmount_value="N";
                }
            }
        });
    }

    private boolean validateFields() {
        String branchNameText = branchName.getText().toString().trim();
        String areaText = area.getText().toString().trim();
        String centreText = centre.getText().toString().trim();
        String groupText = group.getText().toString().trim();
        String residingWithText = residingWith.getSelectedItem().toString().trim();
        String loanUsagePercentageText = loanUsagePercentage.getText().toString().trim();
        String intervieweeNameText = interviewee_name.getText().toString().trim();
        String intervieweeAgeText = interviewee_age.getText().toString().trim();
        String intervieweeRelationText = intervieweeRelation.getSelectedItem().toString().trim();
        String residenceTypeText = residence_type.getSelectedItem().toString().trim();
        String residentialStabilityText = residental_stability.getSelectedItem().toString().trim();
        String distanceApplicantToDealerText = distance_ApplicantToDealer.getText().toString().trim();
        String timeApplicantToDealerText = time_ApplicantToDealer.getText().toString().trim();
        String approxMonthlySalesText = approxMonthlySales.getText().toString().trim();
        String approxMonthlyIncomeText = approxMonthlyIncome.getText().toString().trim();
        String businessExperienceText = businessExperience.getSelectedItem().toString().trim();
        String businessExpenditureText = businessExpenditure.getText().toString().trim();
        String expectedIncomeText = expectedIncome.getText().toString().trim();
        String houseExpenditureText = houseExpenditure.getText().toString().trim();
        String familyNetIncomeText = familyNetIncome.getText().toString().trim();
        String relationWithApplicantText = relationWithApplicant.getSelectedItem().toString().trim();
        String reference1Text = reference1.getText().toString().trim();
        String referencePhoneNo1Text = reference_PhoneNo1.getText().toString().trim();
        String reference2Text = reference2.getText().toString().trim();
        String referencePhoneNo2Text = reference_PhoneNo2.getText().toString().trim();
        String addressText = address.getText().toString().trim();
        String neighbourVerificationText = neighbourhood_verification.getSelectedItem().toString().trim();

        if (branchNameText.isEmpty() || areaText.isEmpty() || centreText.isEmpty() || groupText.isEmpty()
                || residingWithText.isEmpty() || loanUsagePercentageText.isEmpty() || intervieweeNameText.isEmpty()
                || intervieweeAgeText.isEmpty() || intervieweeRelationText.isEmpty() || residenceTypeText.isEmpty()
                || residentialStabilityText.isEmpty() || distanceApplicantToDealerText.isEmpty() || timeApplicantToDealerText.isEmpty()
                || approxMonthlySalesText.isEmpty() || approxMonthlyIncomeText.isEmpty() || businessExperienceText.isEmpty()
                || businessExpenditureText.isEmpty() || expectedIncomeText.isEmpty() || houseExpenditureText.isEmpty()
                || familyNetIncomeText.isEmpty() || relationWithApplicantText.isEmpty() || reference1Text.isEmpty()
                || reference2Text.isEmpty() || neighbourVerificationText.isEmpty() || addressText.isEmpty()) {
            showToasts("Please enter all fields");
            return false;
        }
        if (!isValidMobileNumber(referencePhoneNo1Text) || !isValidMobileNumber(referencePhoneNo2Text)) {
            showToasts("Invalid mobile numbers");
            return false;
        }

        return true;
    }

    private void showToasts(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidMobileNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(HouseVisitActivity2.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(HouseVisitActivity2.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(HouseVisitActivity2.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(HouseVisitActivity2.this);
                    }
                });

        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];

                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean
                                showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                        this, permission);

                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            saveToPreferences(HouseVisitActivity2.this, ALLOW_KEY, true);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        getApplicationContext().getPackageName() + ".provider",
                        photoFile
                );

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }



    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA && resultCode == RESULT_OK) {
            // Display the captured image in the ImageView
            imageUri = Uri.fromFile(new File(currentPhotoPath));
        }
    }
}
