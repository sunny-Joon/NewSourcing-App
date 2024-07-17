package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Adapters.CustomSpinnerAdapter;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.Modelclasses.FiExtra;
import com.paisalo.newinternalsourcingapp.Modelclasses.FiJsonObject;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ProfilePicModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.SaveFiModels.SaveFiDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.SaveFiModels.SaveFiModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.SaveVerifiedInfo;
import com.paisalo.newinternalsourcingapp.Popups.FiCPopup;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;
import com.paisalo.newinternalsourcingapp.Utils.CustomProgressDialog;
import com.paisalo.newinternalsourcingapp.Utils.Utils;
import com.paisalo.newinternalsourcingapp.location.GpsTracker;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

public class KYCActivity2 extends AppCompatActivity {

    EditText monthlyincome, expensemonthly, futureincome, agricultureincome, pensionincome, interestincome, otherincome, earningmemberincome, loanAmount;
    TextView textViewTotalAnnualIncome;

    String ckycNumberExist = "";

    Spinner earningmembertype, businessdetail, loanpurpose, occuption, loanduration, selectbank;

    String earningMemberType, businessDetails, loanPurpose, occupation, selectedBank;
    int monthlyIncome, expense, futureIncome, earningMemberIncome, agricultureIncome, pensionIncome, interestIncome, loanDuration, otherIncome, loanamount;
    CustomProgressDialog customProgressDialog;

    List<String> BankList = new ArrayList<>();
    List<String> BusinessTypeList = new ArrayList<>();
    List<String> earningmembertypeList = new ArrayList<>();
    List<String> loanpurposeList = new ArrayList<>();
    List<String> occuptionList = new ArrayList<>();
    List<String> loandurationList = new ArrayList<>();
    JsonObject jsonObject2;
    private Button savedata;

    GpsTracker gpsTracker;
    FiJsonObject firstPageObject;
    FiExtra fiExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kycactivity2);
        customProgressDialog = new CustomProgressDialog(this);

        DatabaseClass databaseClass = DatabaseClass.getInstance(this);

        //Get in string proovide in Json
        /*Intent intent = getIntent();
        jsonObject=getIntent().getStringExtra("jsonData");
        JsonObject FirstPageObject=new JsonParser().parse(jsonObject).getAsJsonObject();
        Log.d("TAG", "FirstPageObject: "+FirstPageObject);*/

        Intent intent = getIntent();

        String jsonDataString = getIntent().getStringExtra("jsonData");
        Log.d("TAG", "Sunny joon: " + jsonDataString);
        FiJsonObject receivedObject = new Gson().fromJson(jsonDataString, FiJsonObject.class);
        Log.d("TAG", "onCreate: " + new Gson().toJson(receivedObject));

        //  Serializable receivedObject = intent.getSerializableExtra("jsonData");
        fiExtra = receivedObject.getFiExtra();
        Log.d("TAG", "onCreateeee: " + fiExtra.getFatherLastName());
        Log.d("TAG", "onCreateeee: " + fiExtra);

        String vName = intent.getStringExtra("vName");
        String vPanName = intent.getStringExtra("vPanName");
        String vVoterIdName = intent.getStringExtra("vVoterIdName");
        String vLicName = intent.getStringExtra("vLicName");
        String cityCode = intent.getStringExtra("cityCode");
        String distCode = intent.getStringExtra("distCode");
        String subDistCode = intent.getStringExtra("subDistCode");
        String villageCode = intent.getStringExtra("villageCode");
        String stateCode = intent.getStringExtra("stateCode");
        String filePath = getIntent().getStringExtra("file");
        File file = new File(filePath);

        ckycNumberExist = intent.getStringExtra("ckycNumberExist");
        ckycNumberExist = (ckycNumberExist != null && ckycNumberExist.length() > 0) ? ckycNumberExist : "";

        Log.d("TAG", "jsonobjectstate " + villageCode + " villageCode " + distCode + " distCode " + subDistCode + " subDistCode " + cityCode + " cityCode " + vName + " name " + vLicName + " verifiedLicensename " + vVoterIdName + " verifiedVotername " + vPanName + " verifiedPanName ");

        if (receivedObject != null && receivedObject instanceof FiJsonObject) {
            firstPageObject = (FiJsonObject) receivedObject;
        }

        savedata = findViewById(R.id.savedata);

        monthlyincome = findViewById(R.id.monthlyincome);
        expensemonthly = findViewById(R.id.expensemonthly);
        futureincome = findViewById(R.id.futureincome);
        agricultureincome = findViewById(R.id.agricultureincome);
        pensionincome = findViewById(R.id.pensionincome);
        interestincome = findViewById(R.id.interestincome);
        otherincome = findViewById(R.id.otherincome);
        earningmembertype = findViewById(R.id.earningmembertype);
        earningmemberincome = findViewById(R.id.earningmemberincome);
        loanAmount = findViewById(R.id.loanAmount);
        businessdetail = findViewById(R.id.businessdetail);
        loanpurpose = findViewById(R.id.loanpurpose);
        occuption = findViewById(R.id.occuption);
        loanduration = findViewById(R.id.loanduration);
        selectbank = findViewById(R.id.selectbank);
        textViewTotalAnnualIncome = findViewById(R.id.textViewTotalAnnualIncome);

        BankList.add("--Select--");
        BusinessTypeList.add("--Select--");
        earningmembertypeList.add("--Select--");
        loanpurposeList.add("--Select--");
        occuptionList.add("--Select--");

        earningmemberincome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (agricultureincome.getText().length() > 0 || futureincome.getText().length() > 0 || otherincome.getText().length() > 0 || monthlyincome.getText().length() > 0 || pensionincome.getText().length() > 0 || interestincome.getText().length() > 0 || earningmemberincome.getText().length() > 0) {
                        if (earningmembertype.getSelectedItem().toString().contains("Self")) {
                            int totalAnnualIncome = Integer.parseInt(String.valueOf(Integer.parseInt(agricultureincome.getText().toString()) + Integer.parseInt(futureincome.getText().toString()) + Integer.parseInt(otherincome.getText().toString()) + (12 * Integer.parseInt(monthlyincome.getText().toString()) + Integer.parseInt(pensionincome.getText().toString()) + Integer.parseInt(interestincome.getText().toString()))));
                            textViewTotalAnnualIncome.setText("Your Total Annual Income: " + String.valueOf(totalAnnualIncome) + " /-");
                            textViewTotalAnnualIncome.setVisibility(View.VISIBLE);
                        } else {
                            int totalAnnualIncome = Integer.parseInt(earningmemberincome.getText().toString()) + Integer.parseInt(agricultureincome.getText().toString()) + Integer.parseInt(futureincome.getText().toString()) + Integer.parseInt(otherincome.getText().toString()) + (12 * Integer.parseInt(monthlyincome.getText().toString()) + Integer.parseInt(pensionincome.getText().toString()) + Integer.parseInt(interestincome.getText().toString()));
                            textViewTotalAnnualIncome.setText("Your Total Annual Income: " + String.valueOf(totalAnnualIncome) + " /-");
                            textViewTotalAnnualIncome.setVisibility(View.VISIBLE);
                        }
                    }


                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        List<RangeCategoryDataClass> bankData = databaseClass.dao().getAllRCDataListby_catKey("banks");
        for (RangeCategoryDataClass data : bankData) {
            String descriptionEn = data.getDescriptionEn();
            BankList.add(descriptionEn);
            CustomSpinnerAdapter adapter1 = new CustomSpinnerAdapter(KYCActivity2.this, android.R.layout.simple_spinner_dropdown_item, BankList);
            selectbank.setAdapter(adapter1);
        }
        List<RangeCategoryDataClass> business_typeData = databaseClass.dao().getAllRCDataListby_catKey("business-type");
        for (RangeCategoryDataClass data : business_typeData) {
            String descriptionEn = data.getDescriptionEn();
            BusinessTypeList.add(descriptionEn);
            CustomSpinnerAdapter adapter2 = new CustomSpinnerAdapter(KYCActivity2.this, android.R.layout.simple_spinner_dropdown_item, BusinessTypeList);
            businessdetail.setAdapter(adapter2);

        }
        List<RangeCategoryDataClass> earningmembertypeData = databaseClass.dao().getAllRCDataListby_catKey("other_income");
        for (RangeCategoryDataClass data : earningmembertypeData) {
            String descriptionEn = data.getDescriptionEn();
            earningmembertypeList.add(descriptionEn);
            CustomSpinnerAdapter adapter3 = new CustomSpinnerAdapter(KYCActivity2.this, android.R.layout.simple_spinner_dropdown_item, earningmembertypeList);
            earningmembertype.setAdapter(adapter3);
        }
        List<RangeCategoryDataClass> loanpurposeData = databaseClass.dao().getAllRCDataListby_catKey("loan_purpose");
        for (RangeCategoryDataClass data : loanpurposeData) {
            String descriptionEn = data.getDescriptionEn();
            loanpurposeList.add(descriptionEn);
            CustomSpinnerAdapter adapter4 = new CustomSpinnerAdapter(KYCActivity2.this, android.R.layout.simple_spinner_dropdown_item, loanpurposeList);
            loanpurpose.setAdapter(adapter4);
        }
        List<RangeCategoryDataClass> occuptionData = databaseClass.dao().getAllRCDataListby_catKey("occupation-type");
        for (RangeCategoryDataClass data : occuptionData) {
            String descriptionEn = data.getDescriptionEn();
            occuptionList.add(descriptionEn);
            CustomSpinnerAdapter adapter5 = new CustomSpinnerAdapter(KYCActivity2.this, android.R.layout.simple_spinner_dropdown_item, occuptionList);
            occuption.setAdapter(adapter5);
            adapter5.notifyDataSetChanged();
        }
        List<RangeCategoryDataClass> loandurationData = databaseClass.dao().getAllRCDataListby_catKey("business-type");
        for (RangeCategoryDataClass data : loandurationData) {
            String descriptionEn = data.getDescriptionEn();
            loandurationList.add(descriptionEn);
                      /*ArrayAdapter<String> adapter6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, BusinessTypeList);
        loanduration.setAdapter(adapter6);*/

            String[] loandurationList = {"--Select--", "12", "18", "24", "32", "40"};
            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(KYCActivity2.this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(loandurationList));
            loanduration.setAdapter(adapter);
        }


        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allConditionsSatisfied = true;

                if (!calculations()) {
                    allConditionsSatisfied = false;
                } else {
                    if (loanAmount.getText().toString().isEmpty()) {
                        loanAmount.setError("Invalid loanAmount");
                        allConditionsSatisfied = false;
                    } else {
                        loanamount = Integer.parseInt(loanAmount.getText().toString());
                        if (loanduration.getSelectedItem().toString().contains("-Select-")) {
                            ((TextView) loanduration.getSelectedView()).setError("Please select a loanduration");
                            allConditionsSatisfied = false;
                        } else {
                            loanDuration = Integer.parseInt(loanduration.getSelectedItem().toString());
                            if (businessdetail.getSelectedItem().toString().contains("-Select-")) {
                                ((TextView) businessdetail.getSelectedView()).setError("Please select a businessdetail");
                                allConditionsSatisfied = false;
                            } else {
                                businessDetails = businessdetail.getSelectedItem().toString();
                                if (selectbank.getSelectedItem().toString().contains("-Select-")) {
                                    ((TextView) selectbank.getSelectedView()).setError("Please select a selectbank");
                                    allConditionsSatisfied = false;
                                } else {
                                    selectedBank = selectbank.getSelectedItem().toString();
                                    if (loanpurpose.getSelectedItem().toString().contains("-Select-")) {
                                        ((TextView) loanpurpose.getSelectedView()).setError("Please select a loanpurpose");
                                        allConditionsSatisfied = false;
                                    } else {
                                        loanPurpose = loanpurpose.getSelectedItem().toString();
                                        if (expensemonthly.getText().toString().isEmpty()) {
                                            expensemonthly.setError("Invalid expensemonthly");
                                            allConditionsSatisfied = false;
                                        } else {
                                            expense = Integer.parseInt(expensemonthly.getText().toString());
                                            if (monthlyincome.getText().toString().isEmpty()) {
                                                monthlyincome.setError("Invalid monthlyincome");
                                                allConditionsSatisfied = false;
                                            } else {
                                                monthlyIncome = Integer.parseInt(monthlyincome.getText().toString());
                                                if (futureincome.getText().toString().isEmpty()) {
                                                    futureincome.setError("Invalid futureincome");
                                                    allConditionsSatisfied = false;
                                                } else {
                                                    futureIncome = Integer.parseInt(futureincome.getText().toString());
                                                    if (agricultureincome.getText().toString().isEmpty()) {
                                                        agricultureincome.setError("Invalid agricultureincome");
                                                        allConditionsSatisfied = false;
                                                    } else {
                                                        agricultureIncome = Integer.parseInt(agricultureincome.getText().toString());
                                                        if (pensionincome.getText().toString().isEmpty()) {
                                                            pensionincome.setError("Invalid pensionincome");
                                                            allConditionsSatisfied = false;
                                                        } else {
                                                            pensionIncome = Integer.parseInt(pensionincome.getText().toString());
                                                            if (interestincome.getText().toString().isEmpty()) {
                                                                interestincome.setError("Invalid interestincome");
                                                                allConditionsSatisfied = false;
                                                            } else {
                                                                interestIncome = Integer.parseInt(interestincome.getText().toString());
                                                                if (otherincome.getText().toString().isEmpty()) {
                                                                    otherincome.setError("Invalid otherincome");
                                                                    allConditionsSatisfied = false;
                                                                } else {
                                                                    otherIncome = Integer.parseInt(otherincome.getText().toString());
                                                                    if (earningmemberincome.getText().toString().isEmpty()) {
                                                                        earningmemberincome.setError("Invalid earningmemberincome");
                                                                        allConditionsSatisfied = false;
                                                                    } else {
                                                                        earningMemberIncome = Integer.parseInt(earningmemberincome.getText().toString());
                                                                        if (earningmembertype.getSelectedItem().toString().contains("-Select-")) {
                                                                            ((TextView) earningmembertype.getSelectedView()).setError("Please select a earningmembertype");
                                                                            allConditionsSatisfied = false;
                                                                        } else {
                                                                            earningMemberType = earningmembertype.getSelectedItem().toString();
                                                                            if (occuption.getSelectedItem().toString().contains("-Select-")) {
                                                                                ((TextView) occuption.getSelectedView()).setError("Please select a occuption");
                                                                                allConditionsSatisfied = false;
                                                                            } else {
                                                                                occupation = occuption.getSelectedItem().toString();
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                if (allConditionsSatisfied) {

                    firstPageObject.setLoanAmt(loanamount);
                    firstPageObject.setLoanDuration(loanDuration);
                    firstPageObject.setBusinessDetail(businessDetails);
                    firstPageObject.setTPh3(selectedBank);
                    firstPageObject.setLoanReason(loanPurpose);
                    firstPageObject.setAreaOfHouse(0);
                    if (selectbank.equals("Sbi") || selectbank.equals("SBI")) {
                        firstPageObject.setBankName("STATE BANK OF INDIA");
                    } else {
                        firstPageObject.setBankName(selectedBank);
                    }
                    firstPageObject.setCast("");
                    firstPageObject.setCode(0);
                    firstPageObject.setFAmilyMember(0);
                    firstPageObject.setLoanEMi(12);
                    firstPageObject.setLatitude(GlobalClass.Latitude);
                    firstPageObject.setLongitude(GlobalClass.Longitude);
                    firstPageObject.setTPin(0);
                    firstPageObject.setTag(GlobalClass.Tag);
                    firstPageObject.setUserID(GlobalClass.Id);
                    firstPageObject.setExpense(expense);

                    fiExtra.setMonthlyIncome(monthlyIncome);
                    fiExtra.setFutureIncome(futureIncome);
                    fiExtra.setAgricultureIncome(agricultureIncome);
                    fiExtra.setPensionIncome(pensionIncome);
                    fiExtra.setInterestIncome(interestIncome);
                    fiExtra.setOtherIncome(otherIncome);
                    fiExtra.setEarningMemberType(earningMemberType);
                    fiExtra.setEarningMemberIncome(earningMemberIncome);
                    fiExtra.setOccupation(occupation);
                    fiExtra.setIsBorrowerHandicap("NO");

                    firstPageObject.setFiExtra(fiExtra);

                    Gson gson = new Gson();
                    //     JsonObject jsonObject = gson.fromJson(gson.toJson(firstPageObject.toString()), JsonObject.class);
                    JsonObject jsonObject = gson.fromJson(firstPageObject.toString(), JsonObject.class);

                    Log.d("TAG", "FirstPageObject1: " + jsonObject);

                    customProgressDialog.show();
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<SaveFiModel> call1 = apiInterface.getSaveFi(GlobalClass.Token, GlobalClass.dbname, jsonObject);
                    Log.d("TAG", "rrrrrrrrrrSaveFiParams: " + GlobalClass.Token + " " + GlobalClass.dbname + " " + jsonObject);

                    call1.enqueue(new Callback<SaveFiModel>() {
                        @Override
                        public void onResponse(Call<SaveFiModel> call1, Response<SaveFiModel> response1) {
                            Log.d("TAG", "rrrrrrrrrrSaveFiRun: " + response1.body());
                            if (response1.isSuccessful()) {
                                customProgressDialog.dismiss();
                                Log.d("TAG", "rrrrrrrrrrSaveFisuccessful: " + response1.body().getMessage());

                                SaveFiModel saveFiModel = response1.body();
                                SaveFiDataModel saveFiDataModel = saveFiModel.getData();

                                String Message1 = saveFiDataModel.getFiCode().toString();

                                if (Message1 != null) {
                                    jsonObject2 = new JsonObject();
                                    jsonObject2.addProperty("fiCode", Message1.trim());
                                    jsonObject2.addProperty("creator", GlobalClass.Creator);
                                    jsonObject2.addProperty("type", "Basic");
                                    jsonObject2.addProperty("pan_Name", vPanName);
                                    jsonObject2.addProperty("voterId_Name", vVoterIdName);
                                    jsonObject2.addProperty("aadhar_Name", vName);
                                    jsonObject2.addProperty("drivingLic_Name", vLicName);
                                    jsonObject2.addProperty("villagE_CODE", villageCode);
                                    jsonObject2.addProperty("suB_DIST_CODE", subDistCode);
                                    jsonObject2.addProperty("disT_CODE", distCode);
                                    jsonObject2.addProperty("statE_CODE", cityCode);
                                }

                                Log.d("TAG", "jsonobjectstate: " + jsonObject2);
                                Call<SaveVerifiedInfo> call2 = apiInterface.SaveVerifiedInfo(GlobalClass.Token, GlobalClass.dbname, jsonObject2);

                                call2.enqueue(new Callback<SaveVerifiedInfo>() {
                                    @Override
                                    public void onResponse(Call<SaveVerifiedInfo> call2, Response<SaveVerifiedInfo> responses2) {
                                        if (responses2.isSuccessful()) {
                                            customProgressDialog.dismiss();
                                            SaveVerifiedInfo saveVerifiedInfo = responses2.body();

                                            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                                            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                                            Log.d("TAG", "onResponse:apiclient " + ApiClient.getClient4().create(ApiInterface.class));

                                            Call<ProfilePicModel> call3 = apiInterface.updateprofilePic(GlobalClass.Token, GlobalClass.dbname, Message1.trim(), GlobalClass.Creator, GlobalClass.Tag, "0", body);

                                            call3.enqueue(new Callback<ProfilePicModel>() {
                                                @Override
                                                public void onResponse(Call<ProfilePicModel> call3, Response<ProfilePicModel> response3) {
                                                    if (response3.isSuccessful()) {
                                                        if (response3.body().getMessage().equals("Record Create Successfully!!")) {
//                                                            ApiInterface apiInterface4 = ApiClient.getClient4().create(ApiInterface.class);
//
//                                                            Call<CkycNoMODEL> call = apiInterface4.getCkycNo(GlobalClass.Token, GlobalClass.dbname, Message1, GlobalClass.Creator);
//                                                            Log.d("TAG", "onResponse:call "+call);
//                                                            call.enqueue(new Callback<CkycNoMODEL>() {
//                                                                @Override
//                                                                public void onResponse(Call<CkycNoMODEL> call, Response<CkycNoMODEL> response) {
//                                                                    if (response.isSuccessful()) {
//                                                                        CkycNoMODEL result = response.body();
//                                                                        if (result != null) {
//                                                                            Log.d("TAG", "onResponse1:ckyc1 " + result.getData());


                                                            customProgressDialog.dismiss();
                                                            FiCPopup fiCPopup = new FiCPopup("Your Ficode & Creator is Here", Message1 + " & " + GlobalClass.Creator);

                                                            Log.d("TAG", "onResponse: "+ckycNumberExist);

//                                                            if (ckycNumberExist.equals("1")) {
                                                                Log.d("TAG", "onResponse5: "+ckycNumberExist);
                                                                updateAdharWithCodeCreatorForCKCY(firstPageObject.getAadharID(), Message1, GlobalClass.Creator);
                                                      //      }

                                                            fiCPopup.show(getSupportFragmentManager(), "CustomDialog");
//                                                                        } else {
//                                                                            Toast.makeText(KYCActivity2.this, "Failed", Toast.LENGTH_SHORT).show();
//                                                                            Log.d("TAG", "onResponse1:ckyc2 Response body is null" + response.body());
//                                                                        }
//                                                                    } else {
//                                                                        Toast.makeText(KYCActivity2.this, "Failed", Toast.LENGTH_SHORT).show();
//                                                                        Log.d("TAG", "onResponse2:ckyc3 " + response.code());
//                                                                    }
//                                                                }
//
//                                                                @Override
//                                                                public void onFailure(Call<CkycNoMODEL> call, Throwable t) {
//                                                                    Log.d("TAG", "onFailure3: " + t.getMessage());
//                                                                }
//                                                            });


                                                        } else {
                                                            Toast.makeText(KYCActivity2.this, "Failed", Toast.LENGTH_SHORT).show();
                                                            Log.d("TAG", "onResponse3:ckyc2 Response body is null");

                                                        }
                                                    } else {
                                                        customProgressDialog.dismiss();
                                                        Toast.makeText(KYCActivity2.this, "Failed", Toast.LENGTH_SHORT).show();
                                                        Log.d("TAG", "onResponse4:ckyc2 Response body is null");

                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ProfilePicModel> call3, Throwable t) {
                                                    customProgressDialog.dismiss();
                                                    Toast.makeText(KYCActivity2.this, "Network Issue", Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                        } else {
                                            customProgressDialog.dismiss();
                                            Toast.makeText(KYCActivity2.this, "Failed", Toast.LENGTH_SHORT).show();
                                            Log.d("TAG", "onResponse5:ckyc2 Response body is null");

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SaveVerifiedInfo> call2, Throwable t) {
                                        customProgressDialog.dismiss();
                                        Toast.makeText(KYCActivity2.this, "Network Issue", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                customProgressDialog.dismiss();
                                Toast.makeText(KYCActivity2.this, "Failed", Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "onResponse6:ckyc6 Response body is null");


                            }
                        }

                        @Override
                        public void onFailure(Call<SaveFiModel> call1, Throwable t) {
                            customProgressDialog.dismiss();
                            Toast.makeText(KYCActivity2.this, "Network Issue", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }// onCreate Closed

    private void updateAdharWithCodeCreatorForCKCY(String aadharid, String fiCode, String creator) {
        Log.d("TAG", "updateAdharWithCodeCreatorForCKCY: "+aadharid+fiCode+creator);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        httpClient.readTimeout(1, TimeUnit.MINUTES);
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL4)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.updateAdharWithCodeCreator(aadharid, String.valueOf(fiCode), creator);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse:ckycrps " + response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                Log.d("TAG", "onFailure: " + throwable.getMessage());

            }
        });
    }

    private boolean calculations() {
        boolean A = true;
        int maxLoanAmt = 300000;
        String maxLoanAmtStr = "Three lacks";
        Log.d("TAG", "updateBorrower: " + GlobalClass.Creator);
        if (GlobalClass.Creator.toLowerCase().startsWith("vh")) {
            maxLoanAmt = 1000000;
            maxLoanAmtStr = "Ten lacks";
        }
        if (Objects.equals(selectedBank, "SBI") && Integer.parseInt(monthlyincome.getText().toString()) > 25000) {
            Utils.showSnakbar(findViewById(android.R.id.content), "Income is more for SBI");
            A = false;
        } else if (Integer.parseInt(loanAmount.getText().toString().trim()) > maxLoanAmt || Integer.parseInt(loanAmount.getText().toString().trim()) < 5000) {
            loanAmount.setError("Please Enter Loan Amount Less than " + maxLoanAmtStr + " and Greater than 5 thousand");
            Utils.showSnakbar(findViewById(android.R.id.content), "Please enter Loan Amount Less than " + maxLoanAmtStr + " and Greater than 5 thousand");
            A = false;
        } else if (!GlobalClass.Creator.startsWith("VH") && ((Double.parseDouble(monthlyincome.getText().toString().trim()))) < (0.15 * Double.parseDouble(loanAmount.getText().toString().trim()))) {
            monthlyincome.setError("Income should be greater than 15% of Loan Amount");
            Utils.showSnakbar(findViewById(android.R.id.content), "Income should be greater than 15% of Loan Amount");
            A = false;
        } else if (!GlobalClass.Creator.startsWith("VH") && ((Double.parseDouble(monthlyincome.getText().toString().trim())) * 0.25) > Double.parseDouble(expensemonthly.getText().toString().trim())) {
            expensemonthly.setError("Expense should be greater than 25 % of Income");
            Utils.showSnakbar(findViewById(android.R.id.content), "Expense should be greater than 25 % of Income");
            A = false;
        }
        return A;
    }
}

