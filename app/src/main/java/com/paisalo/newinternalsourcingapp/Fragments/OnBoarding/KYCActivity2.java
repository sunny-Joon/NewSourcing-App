package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import com.paisalo.newinternalsourcingapp.location.GpsTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KYCActivity2 extends AppCompatActivity {

    EditText monthlyincome, expensemonthly, futureincome, agricultureincome, pensionincome, interestincome, otherincome, earningmemberincome, loanAmount;

    Spinner earningmembertype, businessdetail, loanpurpose, occuption, loanduration, selectbank;

    String earningMemberType,businessDetails,loanPurpose,occupation, selectedBank;
    int monthlyIncome,expense,futureIncome,earningMemberIncome,agricultureIncome,pensionIncome,interestIncome,loanDuration,otherIncome,loanamount;

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

        DatabaseClass databaseClass = DatabaseClass.getInstance(this);

        //Get in string proovide in Json
        /*Intent intent = getIntent();
        jsonObject=getIntent().getStringExtra("jsonData");
        JsonObject FirstPageObject=new JsonParser().parse(jsonObject).getAsJsonObject();
        Log.d("TAG", "FirstPageObject: "+FirstPageObject);*/

        Intent intent = getIntent();

        Serializable receivedObject = intent.getSerializableExtra("jsonData");
        Serializable fiExtras = intent.getSerializableExtra("fiExtra");
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

        Log.d("TAG", "jsonobjectstate " + villageCode +" villageCode "+ distCode +" distCode "+ subDistCode + " subDistCode " + cityCode+" cityCode " + vName + " name " + vLicName + " verifiedLicensename " + vVoterIdName + " verifiedVotername " + vPanName +" verifiedPanName ");

        if (receivedObject != null && receivedObject instanceof FiJsonObject) {
             firstPageObject = (FiJsonObject) receivedObject;
        }
        if (fiExtras != null && fiExtras instanceof FiExtra) {
            fiExtra = (FiExtra) fiExtras;
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

        BankList.add("--Select--");
        BusinessTypeList.add("--Select--");
        earningmembertypeList.add("--Select--");
        loanpurposeList.add("--Select--");
        occuptionList.add("--Select--");
        DatabaseClass.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                List<RangeCategoryDataClass> bankData = databaseClass.dao().getAllRCDataListby_catKey("banks");
                for (RangeCategoryDataClass data : bankData) {
                    String descriptionEn = data.getDescriptionEn();
                    BankList.add(descriptionEn);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(KYCActivity2.this, android.R.layout.simple_spinner_item, BankList);
                    selectbank.setAdapter(adapter1);
                }
                List<RangeCategoryDataClass> business_typeData = databaseClass.dao().getAllRCDataListby_catKey("business-type");
                for (RangeCategoryDataClass data : business_typeData) {
                    String descriptionEn = data.getDescriptionEn();
                    BusinessTypeList.add(descriptionEn);
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(KYCActivity2.this, android.R.layout.simple_spinner_item, BusinessTypeList);
                    businessdetail.setAdapter(adapter2);

                }
                List<RangeCategoryDataClass> earningmembertypeData = databaseClass.dao().getAllRCDataListby_catKey("other_income");
                for (RangeCategoryDataClass data : earningmembertypeData) {
                    String descriptionEn = data.getDescriptionEn();
                    earningmembertypeList.add(descriptionEn);
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(KYCActivity2.this, android.R.layout.simple_spinner_item, earningmembertypeList);
                    earningmembertype.setAdapter(adapter3);
                }
                List<RangeCategoryDataClass> loanpurposeData = databaseClass.dao().getAllRCDataListby_catKey("loan_purpose");
                for (RangeCategoryDataClass data : loanpurposeData) {
                    String descriptionEn = data.getDescriptionEn();
                    loanpurposeList.add(descriptionEn);
                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(KYCActivity2.this, android.R.layout.simple_spinner_item, loanpurposeList);
                    loanpurpose.setAdapter(adapter4);
                }
                List<RangeCategoryDataClass> occuptionData = databaseClass.dao().getAllRCDataListby_catKey("occupation-type");
                for (RangeCategoryDataClass data : occuptionData) {
                    String descriptionEn = data.getDescriptionEn();
                    occuptionList.add(descriptionEn);
                    ArrayAdapter<String> adapter5 = new ArrayAdapter<>(KYCActivity2.this, android.R.layout.simple_spinner_item, occuptionList);
                    occuption.setAdapter(adapter5);
                    adapter5.notifyDataSetChanged();
                }
                List<RangeCategoryDataClass> loandurationData = databaseClass.dao().getAllRCDataListby_catKey("business-type");
                for (RangeCategoryDataClass data : loandurationData) {
                    String descriptionEn = data.getDescriptionEn();
                    loandurationList.add(descriptionEn);
                      /*ArrayAdapter<String> adapter6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, BusinessTypeList);
        loanduration.setAdapter(adapter6);*/

                    String[] loandurationList = {"--Select--","12", "18", "24", "32", "40"};
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(KYCActivity2.this, android.R.layout.simple_spinner_item, loandurationList);
                    loanduration.setAdapter(adapter);
                }
            }
        });

        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loanamount = Integer.parseInt(loanAmount.getText().toString());
                loanDuration = Integer.parseInt(loanduration.getSelectedItem().toString());
                businessDetails = businessdetail.getSelectedItem().toString();
                selectedBank = selectbank.getSelectedItem().toString();
                loanPurpose = loanpurpose.getSelectedItem().toString();
                expense = Integer.parseInt(expensemonthly.getText().toString());
                monthlyIncome = Integer.parseInt(monthlyincome.getText().toString());
                futureIncome = Integer.parseInt(futureincome.getText().toString());
                agricultureIncome = Integer.parseInt(agricultureincome.getText().toString());
                pensionIncome = Integer.parseInt(pensionincome.getText().toString());
                interestIncome = Integer.parseInt(interestincome.getText().toString());
                otherIncome = Integer.parseInt(otherincome.getText().toString());
                earningMemberIncome = Integer.parseInt(earningmemberincome.getText().toString());
                earningMemberType = earningmembertype.getSelectedItem().toString();
                occupation = occuption.getSelectedItem().toString();


                firstPageObject.setLoanAmt(loanamount);
                firstPageObject.setLoanDuration(loanDuration);
                firstPageObject.setBusinessDetail(businessDetails);
                firstPageObject.setTPh3(" ");
                firstPageObject.setLoanReason(loanPurpose);
                firstPageObject.setAreaOfHouse(0);
                firstPageObject.setBankName(selectedBank);
                firstPageObject.setCast("");
                firstPageObject.setCityCode("CityCode");
                firstPageObject.setCode(0);
                firstPageObject.setCreator("HOAGRA");
                firstPageObject.setFAmilyMember(0);
                firstPageObject.setLoanEMi(12);
               /* FirstPageObject.addProperty("Latitude" );
                FirstPageObject.addProperty("Longitude" */
                firstPageObject.setLatitude(123.45);
                firstPageObject.setLongitude(123.45); ;
                firstPageObject.setTPin(0);
                firstPageObject.setTag("RTAG");
                firstPageObject.setUserID("UserID");
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

                firstPageObject.setFiExtra(fiExtra);

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(gson.toJson(firstPageObject), JsonObject.class);
                Log.d("TAG", "FirstPageObject1: " + jsonObject);

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<SaveFiModel> call1 = apiInterface.getSaveFi(GlobalClass.Token,GlobalClass.dbname, jsonObject);
                Log.d("TAG", "rrrrrrrrrrSaveFiParams: " + GlobalClass.Token+ " "+GlobalClass.dbname +" "+ jsonObject);

                call1.enqueue(new Callback<SaveFiModel>() {
                    @Override
                    public void onResponse(Call<SaveFiModel> call1, Response<SaveFiModel> response1) {
                        Log.d("TAG", "rrrrrrrrrrSaveFiRun: "+ response1.body());
                        if(response1.isSuccessful()){
                            Log.d("TAG", "rrrrrrrrrrSaveFisuccessful: "+ response1.body().getMessage());

                         SaveFiModel saveFiModel = response1.body();
                            SaveFiDataModel saveFiDataModel = saveFiModel.getData();

                            String Message1 = saveFiDataModel.getFiCode().toString();

                            if(Message1 != null){
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
                                    jsonObject2.addProperty("statE_CODE", stateCode);
                            }

                            Log.d("TAG", "jsonobjectstate: " + jsonObject2);
                            Call<SaveVerifiedInfo> call2 = apiInterface.SaveVerifiedInfo(GlobalClass.Token,GlobalClass.dbname, jsonObject2);

                            call2.enqueue(new Callback<SaveVerifiedInfo>() {
                                @Override
                                public void onResponse(Call<SaveVerifiedInfo> call2, Response<SaveVerifiedInfo> responses2) {
                                    Log.d("TAG", "rrrrrrrrrrSaveVIRun: " + responses2.body());
                                    if(responses2.isSuccessful()){
                                        Log.d("TAG", "rrrrrrrrrrSaveVISuccessful: " + responses2.body());
                                        SaveVerifiedInfo saveVerifiedInfo = responses2.body();
                                        Log.d("TAG", "jsonobjectstate: "+saveVerifiedInfo.getMessage());
                                        FiCPopup fiCPopup = new FiCPopup("Your Ficode is Here", Message1);
                                        fiCPopup.show(getSupportFragmentManager(), "CustomDialog");

                                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                                        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                                        Call<ProfilePicModel> call3 = apiInterface.updateprofilePic(GlobalClass.Token,GlobalClass.dbname, Message1.trim(),GlobalClass.Creator,"CLAR",body);
                                        call3.enqueue(new Callback<ProfilePicModel>() {
                                            @Override
                                            public void onResponse(Call<ProfilePicModel> call3, Response<ProfilePicModel> response3) {
                                                Log.d("TAG", "rrrrrrrrrrSavePICRUN: "+ response3.body());
                                                if(response3.isSuccessful()){
                                                    Log.d("TAG", "rrrrrrrrrrSavePICSuccessful: "+ response3.body().getMessage());
                                                    FiCPopup fiCPopup = new FiCPopup("Your Ficode is Here", Message1);
                                                    fiCPopup.show(getSupportFragmentManager(), "CustomDialog");

                                                }else{
                                                    Log.d("TAG", "rrrrrrrrrrSavePICUNSuccessful: "+ response3.code());


                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ProfilePicModel> call3, Throwable t) {
                                                Log.d("TAG", "rrrrrrrrrrSavePICUNSuccessful: "+ "failure");

                                            }
                                        });


                                    }else{
                                        Log.d("TAG", "rrrrrrrrrrSaveVIUnsuccessful: " + responses2.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<SaveVerifiedInfo> call2, Throwable t) {
                                    Log.d("TAG", "rrrrrrrrrrSaveVI: " + "failure");

                                }
                            });
                        }else {
                            Log.d("TAG", "rrrrrrrrrrSaveFiUnsuccessful: "+ response1.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<SaveFiModel> call1, Throwable t) {
                        Toast.makeText(KYCActivity2.this, "Failed", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "rrrrrrrrrrSaveFi" + "Failure");
                    }
                });
            }
        });




    }// onCreate Closed

}

