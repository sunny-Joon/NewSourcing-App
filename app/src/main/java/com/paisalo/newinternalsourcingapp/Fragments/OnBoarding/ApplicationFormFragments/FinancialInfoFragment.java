package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding.ApplicationFormFragments;

import static com.paisalo.newinternalsourcingapp.GlobalClass.SubmitAlert;
import static com.paisalo.newinternalsourcingapp.Retrofit.ApiClient.getClient;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ApplicationFormActivityMenu;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.GetAllApplicationFormDataModels.AllDataAFDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.UpdateFiModels.KycUpdateModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;
import com.paisalo.newinternalsourcingapp.Utils.MyTextWatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FinancialInfoFragment extends Fragment {

    Spinner accountTypeSpinner, houseTypeSpinner, roofTypeSpinner, personalToiletSpinner;
    EditText bankAccountNumber, bankIFSCCode, rentalIncome, expenseInRent, expenseForFood, expenseForEducation, expenseForHealth,
            expenseForTravelling, expenseForEntertainment, anyOtherExpense, etaccount_date;
    TextView tvBankBranch,tvBankName,tilBankAccountName;
    CheckBox accountVerification;
    Button submit;
    List<String> houseType_List = new ArrayList<>();
    List<String> roofType_List = new ArrayList<>();
    List<String> personalToilet_List = new ArrayList<>();
    List<String> accountType_List = new ArrayList<>();
    ImageView calender_icon;

    String fiCode, creator, tag, bankAccountType, bankAccNumber, accOpeningDate, ifscCode, houseType, roofType, personalToilet,
            requestforVerification="",ResponseforVerification="",isAccountVerify="N";

     Integer expenseforEducation,expenseforEntertainment,expenseforFood,expenseforHealth,expenseforTravelling,expenseinRent,otherexpense,rentalincome;
    AllDataAFDataModel allDataAFDataModel;

    public FinancialInfoFragment(AllDataAFDataModel allDataAFDataModel) {
        this.allDataAFDataModel = allDataAFDataModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_financial_info, container, false);

        DatabaseClass databaseClass = DatabaseClass.getInstance(getContext());



        bankAccountNumber = view.findViewById(R.id.bankAccountNumber);
        etaccount_date = view.findViewById(R.id.account_date);
        bankIFSCCode = view.findViewById(R.id.bankIFSCCode);
        rentalIncome = view.findViewById(R.id.rentalIncome);
        expenseInRent = view.findViewById(R.id.expenseInRent);
        expenseForFood = view.findViewById(R.id.expenseForFood);
        expenseForEducation = view.findViewById(R.id.expenseForEducation);
        expenseForHealth = view.findViewById(R.id.expenseForHealth);
        expenseForTravelling = view.findViewById(R.id.expenseForTravelling);
        expenseForEntertainment = view.findViewById(R.id.expenseForEntertainment);
        anyOtherExpense = view.findViewById(R.id.anyOtherExpense);

        calender_icon = view.findViewById(R.id.ac_date_calender);
        accountVerification = view.findViewById(R.id.accountVerification);
        accountTypeSpinner = view.findViewById(R.id.bankAccountType);

        houseTypeSpinner = view.findViewById(R.id.houseType);
        roofTypeSpinner = view.findViewById(R.id.roofType);
        personalToiletSpinner = view.findViewById(R.id.personalToilet);
        submit = view.findViewById(R.id.submitfinancialInfo);

        String selectOption = "--Select--";
        houseType_List.add(selectOption);
        roofType_List.add(selectOption);
        personalToilet_List.add(selectOption);
        accountType_List.add(selectOption);


        bankIFSCCode.addTextChangedListener(new MyTextWatcher(bankIFSCCode) {
            @Override
            public void validate(EditText editText, String text) {
                editText.setError(null);
                if (editText.length() != 11) {
                    editText.setError("Must be 11 Character");
                } else if (!GlobalClass.validateIFSC(text)) {
                    editText.setError("Please input a valid IFSC. " + " " + text + "is not a valid IFSC");
                } else {
                    CheckValidateIFSC(editText, text);
                }
            }
        });

        accountVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bankAccountNumber.getText().toString().equals("") && !bankIFSCCode.getText().toString().equals("") && !tvBankName.getText().toString().equals("")){
                    cardValidate(bankAccountNumber.getText().toString().trim(),bankIFSCCode.getText().toString().trim());
                }else{
                    Toast.makeText(getContext(), "Please enter account number and IFSC code Properly", Toast.LENGTH_SHORT).show();
                }
            }
        });
        List<RangeCategoryDataClass> houseType_DataList = databaseClass.dao().getAllRCDataListby_catKey("house-type");
        for (RangeCategoryDataClass data : houseType_DataList) {
            String descriptionEn = data.getDescriptionEn();
            houseType_List.add(descriptionEn);

        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, houseType_List);
        houseTypeSpinner.setAdapter(adapter1);

        List<RangeCategoryDataClass> roofType_DataList = databaseClass.dao().getAllRCDataListby_catKey("house-roof-type");
        for (RangeCategoryDataClass data : roofType_DataList) {
            String descriptionEn = data.getDescriptionEn();
            roofType_List.add(descriptionEn);

        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, roofType_List);
        roofTypeSpinner.setAdapter(adapter2);

        List<RangeCategoryDataClass> personalToilet_DataList = databaseClass.dao().getAllRCDataListby_catKey("toilet-type");
        for (RangeCategoryDataClass data : personalToilet_DataList) {
            String descriptionEn = data.getDescriptionEn();
            personalToilet_List.add(descriptionEn);

        }
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, personalToilet_List);
        personalToiletSpinner.setAdapter(adapter3);


        List<RangeCategoryDataClass> accountType_DataList = databaseClass.dao().getAllRCDataListby_catKey("bank_ac_type");
        for (RangeCategoryDataClass data : accountType_DataList) {
            String descriptionEn = data.getDescriptionEn();
            accountType_List.add(descriptionEn);
        }
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, accountType_List);
        accountTypeSpinner.setAdapter(adapter4);

        if (allDataAFDataModel != null) {
            fiCode = allDataAFDataModel.getCode().toString();
            creator = allDataAFDataModel.getCreator().toString();
            tag = allDataAFDataModel.getTag().toString();
            Log.d("TAG", "onCreateView222: " + fiCode + tag + creator);
            try {

                if (allDataAFDataModel != null) {
                    Log.d("TAG", "onCreate:view1 "+allDataAFDataModel.getBankAcNo());
                    if (allDataAFDataModel.getBankAcNo() != null) {
                        bankAccountNumber.setText(String.valueOf(allDataAFDataModel.getBankAcNo()));
                    }

                    Log.d("TAG", "onCreate:view3 "+allDataAFDataModel.getBankAcOpenDt());
                    if (allDataAFDataModel.getBankAcOpenDt() != null) {
                        etaccount_date.setText(String.valueOf(allDataAFDataModel.getBankAcOpenDt()));
                    }

                    Log.d("TAG", "onCreate:view4 "+allDataAFDataModel.getIFSCCode());
                    if (allDataAFDataModel.getIFSCCode() != null) {
                        bankIFSCCode.setText(allDataAFDataModel.getIFSCCode());
                    }

                    Log.d("TAG", "onCreate:view5 "+allDataAFDataModel.getFiExtra().getRentalIncome());
                    if (allDataAFDataModel.getFiExtra().getRentalIncome().toString() != null) {
                        rentalIncome.setText(String.valueOf(allDataAFDataModel.getFiExtra().getRentalIncome()));
                    }

                    Log.d("TAG", "onCreate:view6 "+allDataAFDataModel.getFiFamExpenses().getRent());
                    if (allDataAFDataModel.getFiFamExpenses().getRent() != null) {
                        expenseInRent.setText(String.valueOf(allDataAFDataModel.getFiFamExpenses().getRent()));
                    }

                    Log.d("TAG", "onCreate:view8 "+allDataAFDataModel.getFiFamExpenses().getFooding());
                    if (allDataAFDataModel.getFiFamExpenses().getFooding() != null) {
                        expenseForFood.setText(String.valueOf(allDataAFDataModel.getFiFamExpenses().getFooding()));
                    }

                    Log.d("TAG", "onCreate:view9 "+allDataAFDataModel.getFiFamExpenses().getEducation());
                    if (allDataAFDataModel.getFiFamExpenses().getEducation() != null) {
                        expenseForEducation.setText(String.valueOf(allDataAFDataModel.getFiFamExpenses().getEducation()));
                    }

                    Log.d("TAG", "onCreate:view10 "+allDataAFDataModel.getFiFamExpenses().getHealth());
                    if (allDataAFDataModel.getFiFamExpenses().getHealth() != null) {
                        expenseForHealth.setText(String.valueOf(allDataAFDataModel.getFiFamExpenses().getHealth()));
                    }

                    Log.d("TAG", "onCreate:view11 "+allDataAFDataModel.getFiFamExpenses().getTravelling());
                    if (allDataAFDataModel.getFiFamExpenses().getTravelling() != null) {
                        expenseForTravelling.setText(String.valueOf(allDataAFDataModel.getFiFamExpenses().getTravelling()));
                    }

                    Log.d("TAG", "onCreate:view12 "+allDataAFDataModel.getFiFamExpenses().getEntertainment());
                    if (allDataAFDataModel.getFiFamExpenses().getEntertainment() != null) {
                        expenseForEntertainment.setText(String.valueOf(allDataAFDataModel.getFiFamExpenses().getEntertainment()));
                    }

                    Log.d("TAG", "onCreate:view13 "+allDataAFDataModel.getFiFamExpenses().getOthers());
                    if (allDataAFDataModel.getFiFamExpenses().getOthers() != null) {
                        anyOtherExpense.setText(String.valueOf(allDataAFDataModel.getFiFamExpenses().getOthers()));
                    }


                    if (allDataAFDataModel.getFiFamExpenses().getHomeType() != null) {
                        int castePos = adapter1.getPosition(String.valueOf(allDataAFDataModel.getFiFamExpenses().getHomeType()));
                        houseTypeSpinner.setSelection(castePos);
                    }
                    if (allDataAFDataModel.getFiFamExpenses().getHomeRoofType() != null) {
                        int castePos1 = adapter2.getPosition(String.valueOf(allDataAFDataModel.getFiFamExpenses().getHomeRoofType()));
                        roofTypeSpinner.setSelection(castePos1);
                    }
                    if (allDataAFDataModel.getFiFamExpenses().getToiletType() != null) {
                        int castePos2 = adapter3.getPosition(String.valueOf(allDataAFDataModel.getFiFamExpenses().getToiletType()));
                        personalToiletSpinner.setSelection(castePos2);
                    }

                    Log.d("TAG", "onCreate:view7 "+allDataAFDataModel.getBankAcType());
                    if (allDataAFDataModel.getBankAcType() != null) {
                        int castePos3 = adapter4.getPosition(String.valueOf(allDataAFDataModel.getBankAcType()));
                        accountTypeSpinner.setSelection(castePos3);
                    }
                }
                } catch(Exception exception){
                    Toast.makeText(getContext(), "fifamloans is null here", Toast.LENGTH_SHORT).show();
                }
            }


        calender_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }

            private void showDatePickerDialog() {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        etaccount_date.setText(selectedDate);
                        // progressBar.incrementProgressBy(1);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean allConditionsSatisfied = true;

                if (bankAccountNumber.getText().toString().isEmpty()) {
                    bankAccountNumber.setError("Invalid bankAccount");
                    allConditionsSatisfied = false;
                } else {
                    bankAccNumber = bankAccountNumber.getText().toString();
                }

                if (etaccount_date.getText().toString().isEmpty()) {
                    etaccount_date.setError("Select Date");
                    allConditionsSatisfied = false;
                } else {
                    accOpeningDate = etaccount_date.getText().toString();
                }


                if (bankIFSCCode.getText().toString().isEmpty()) {
                    bankIFSCCode.setError("Invalid bankIFSCCode");
                    allConditionsSatisfied = false;
                } else {
                    ifscCode = bankIFSCCode.getText().toString();
                }


                if (rentalIncome.getText().toString().isEmpty()) {
                    rentalIncome.setError("Invalid rentalIncome");
                    allConditionsSatisfied = false;
                } else {
                    rentalincome = Integer.valueOf(rentalIncome.getText().toString());
                }


                if (expenseInRent.getText().toString().isEmpty()) {
                    expenseInRent.setError("Invalid expenseInRent");
                    allConditionsSatisfied = false;
                } else {
                    expenseinRent = Integer.valueOf(expenseInRent.getText().toString());
                }


                if (expenseForFood.getText().toString().isEmpty()) {
                    expenseForFood.setError("Invalid expenseForFood");
                    allConditionsSatisfied = false;
                } else {
                    expenseforFood = Integer.valueOf(expenseForFood.getText().toString());
                }


                if (expenseForEducation.getText().toString().isEmpty()) {
                    expenseForEducation.setError("Invalid expenseForEducation");
                    allConditionsSatisfied = false;
                } else {
                    expenseforEducation = Integer.valueOf(expenseForEducation.getText().toString());
                }


                if (expenseForHealth.getText().toString().isEmpty()) {
                    expenseForHealth.setError("Invalid expenseForHealth");
                    allConditionsSatisfied = false;
                } else {
                    expenseforHealth = Integer.valueOf(expenseForHealth.getText().toString());
                }


                if (expenseForTravelling.getText().toString().isEmpty()) {
                    expenseForTravelling.setError("Invalid expenseForTravelling");
                    allConditionsSatisfied = false;
                } else {
                    expenseforTravelling = Integer.valueOf(expenseForTravelling.getText().toString());
                }


                if (expenseForEntertainment.getText().toString().isEmpty()) {
                    expenseForEntertainment.setError("Invalid expenseForEntertainment");
                    allConditionsSatisfied = false;
                } else {
                    expenseforEntertainment = Integer.valueOf(expenseForEntertainment.getText().toString());
                }


                if (anyOtherExpense.getText().toString().isEmpty()) {
                    anyOtherExpense.setError("Invalid anyOtherExpense");
                    allConditionsSatisfied = false;
                } else {
                    otherexpense = Integer.valueOf(anyOtherExpense.getText().toString());
                }

                if (houseTypeSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) houseTypeSpinner.getSelectedView()).setError("Please select a houseType");
                    allConditionsSatisfied = false;
                } else {
                    houseType = houseTypeSpinner.getSelectedItem().toString();
                }

                if (roofTypeSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) roofTypeSpinner.getSelectedView()).setError("Please select a roofType");
                    allConditionsSatisfied = false;
                } else {
                    roofType = roofTypeSpinner.getSelectedItem().toString();
                }

                if (personalToiletSpinner.getSelectedItem().toString().contains("-Select-")) {
                    ((TextView) personalToiletSpinner.getSelectedView()).setError("Please select a personalToilet");
                    allConditionsSatisfied = false;
                } else {
                    personalToilet = personalToiletSpinner.getSelectedItem().toString();
                }

                if (allConditionsSatisfied) {
                    ApiInterface apiInterface = getClient().create(ApiInterface.class);
                    Call<KycUpdateModel> call = apiInterface.updateFinance(GlobalClass.Token, GlobalClass.dbname, financeJson());
                    Log.d("TAG", "onResponseAdhaarUpdate: " + GlobalClass.Token + " " + GlobalClass.dbname + " " + financeJson());

                    call.enqueue(new Callback<KycUpdateModel>() {
                        @Override
                        public void onResponse(Call<KycUpdateModel> call, Response<KycUpdateModel> response) {
                            Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                            if (response.isSuccessful()) {
                                Log.d("TAG", "onResponseAdhaarUpdate: " + response.body());
                         //  Log.d("TAG", "onResponseAdhaarUpdatemsg: " + response.body().getMessage().toString());
                                SubmitAlert(getActivity(), "success", "Data set Successfully");

//
                            } else {
                                Log.d("TAG", "onResponseAdhaarUpdate: " + response.code());
                                SubmitAlert(getActivity(), "unsuccessful", "Check Your Internet Connection");

                            }
                        }

                        @Override
                        public void onFailure(Call<KycUpdateModel> call, Throwable t) {
                            Log.d("TAG", "onResponseAdhaarUpdate: " + "failure");
                            SubmitAlert(getActivity(), "Network Error", "Check Your Internet Connection");

                        }
                    });


                    Intent intent = new Intent(getActivity(), ApplicationFormActivityMenu.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        return view;
    }

    private void cardValidate(String id,String bankIfsc) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Fetching Details");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        ApiInterface apiInterface= getClientPan("https://agra.paisalo.in:8462/creditmatrix/api/").create(ApiInterface.class);
        requestforVerification= String.valueOf(getJsonOfString(id,bankIfsc));
        Call<JsonObject> call=apiInterface.accValidate(GlobalClass.Token, BuildConfig.dbname,getJsonOfString(id,bankIfsc));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    ResponseforVerification= String.valueOf(response.body().get("data"));
                //    saveVerficationLogs(GlobalClass.getPrefString(getContext(), GlobalClass.Id, ""),"Bank Account",requestforVerification,ResponseforVerification);
                    if(response.body().get("data").getAsJsonObject().get("account_exists").getAsBoolean()){
                        tilBankAccountName.setVisibility(View.VISIBLE);
                        tilBankAccountName.setText(response.body().get("data").getAsJsonObject().get("full_name").getAsString());
                        //tilBankAccountName.setTextColor(getResources().getColor(R.color.green));
                        accountVerification.setChecked(true);
                        accountVerification.setEnabled(false);
                        isAccountVerify="V";
                        UpdatefiVerificationDocName();
                    }else {
                        showDialog(bankAccountNumber);
                        SubmitAlert(getActivity(), "success", "Data set Successfully");
                        tilBankAccountName.setVisibility(View.VISIBLE);
                        tilBankAccountName.setText("BANK not Verify");
                        //tilBankAccountName.setTextColor(getResources().getColor(R.color.green));


                    }
                }catch (Exception e){
                    showDialog(bankAccountNumber);
                    tilBankAccountName.setVisibility(View.VISIBLE);
                    tilBankAccountName.setText("BANK API not Working");

                }
                progressDialog.cancel();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                tilBankAccountName.setText(t.getMessage());
                progressDialog.cancel();
                accountVerification.setChecked(false);
            }
        });
    }

    private void UpdatefiVerificationDocName() {
        ApiInterface apiInterface= getClient().create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.getDocNameDate(getJsonOfDocName());
        call.enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                Log.d("TAG", "onResponse: "+response.body());
                if(response.body() != null){
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());

            }
        });
    }

    private JsonObject getJsonOfDocName() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("type","bank");
        jsonObject.addProperty("pan_Name","");
        jsonObject.addProperty("voterId_Name","");
        jsonObject.addProperty("aadhar_Name","");
        jsonObject.addProperty("drivingLic_Name","");
        jsonObject.addProperty("bankAcc_Name",tilBankAccountName.getText().toString());
        jsonObject.addProperty("bank_Name",tvBankName.getText().toString());
        jsonObject.addProperty("fiCode",String.valueOf(allDataAFDataModel.getCode()));
        jsonObject.addProperty("creator",GlobalClass.Creator);
        return jsonObject;
    }


    private JsonObject getJsonOfString(String id, String bankIfsc) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("type","bankaccount");
        jsonObject.addProperty("txtnumber",id);
        jsonObject.addProperty("ifsc",bankIfsc);
        jsonObject.addProperty("userdob","");
        jsonObject.addProperty("key","1");
        return  jsonObject;
    }

    public static Retrofit getClientService(String BASE_URL) {
        Retrofit retrofit=null;
        if (retrofit==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder(

            );
            httpClient.connectTimeout(1, TimeUnit.MINUTES);
            httpClient.readTimeout(1,TimeUnit.MINUTES);
            httpClient.addInterceptor(logging);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientPan(String BASE_URL) {
        Retrofit retrofit = null;
        if (retrofit==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder(
            );
            httpClient.connectTimeout(1, TimeUnit.MINUTES);
            httpClient.readTimeout(1,TimeUnit.MINUTES);
            httpClient.addInterceptor(logging);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
  /*  private void saveVerficationLogs(String id,String type,String request,String response) {
        ApiInterface apiInterface= getClientPan(SEILIGL.NEW_SERVERAPI).create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.kycVerficationlog(getJsonOfKyCLogs(id,type,request,response));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "checkCrifScore: "+response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }*/

    private JsonObject getJsonOfKyCLogs(String id, String type,String bankIfsc,String userDOB) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("Type",type);
        jsonObject.addProperty("Userid",id);
        jsonObject.addProperty("Request",bankIfsc);
        jsonObject.addProperty("Response",userDOB);
        return  jsonObject;
    }

    private void CheckValidateIFSC(final EditText editText, String text){
        ApiInterface apiInterface= getClientService("https://ifsc.razorpay.com/").create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.getIfscCode(text);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: "+response.body());
                tvBankName = (TextView) getView().findViewById(R.id.tvLoanAppFinanceBankName);
                tvBankBranch = (TextView) getView().findViewById(R.id.tvLoanAppFinanceBankBranch);
                if(response.body() != null){
                    try {
                        boolean bankNotAllowed=false;
                        tvBankName.setText("");
                        tvBankBranch.setText("");
                        JSONObject jo = new JSONObject(String.valueOf(response.body()));
                        String bankname=jo.getString("BANK");
                        String address=jo.getString("ADDRESS");
                        tvBankName.setText(bankname);
                        tvBankBranch.setText(address);
                      /*  if (borrower != null) {
                            for (String restrictBank:restrictBanks) {
                                Log.d("TAG", "onResponse: "+restrictBank+"///"+bankname);
                                if (bankname.toUpperCase().trim().contains(restrictBank)){
                                    bankNotAllowed =true;
                                    break;
                                }
                            }
                            if (bankNotAllowed){
                                Utils.alert(activity,"This bank is not allowed please try with another");
                                etIFSC.setText("");
                                etBankAccount.setText("");
                                etBankAccount.setEnabled(true);
                            }else{
                                borrower.BankName = bankname;
                                borrower.Bank_Address =address;
                                tvBankName.setText(borrower.BankName);
                                tvBankBranch.setText(borrower.Bank_Address);
                                borrower.save();
                            }

                        }*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    tvBankName.setText("");
                    tvBankBranch.setText("");
                    editText.setError("This IFSC not available");
                }


            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());

            }
        });
    }

    private JsonObject financeJson() {
        JsonObject jsonFinance = new JsonObject();
        jsonFinance.addProperty("fiCode", fiCode);
        jsonFinance.addProperty("creator", creator);
        jsonFinance.addProperty("tag", tag);
        jsonFinance.addProperty("bankAccountType", "sa");
        jsonFinance.addProperty("bankAccNumber", bankAccNumber);
        jsonFinance.addProperty("accOpeningDate", accOpeningDate);
        jsonFinance.addProperty("ifscCode", ifscCode);
        jsonFinance.addProperty("rentalIncome", rentalincome);
        jsonFinance.addProperty("expenseInRent", expenseinRent);
        jsonFinance.addProperty("expenseForFood", expenseforFood);
        jsonFinance.addProperty("expenseForEducation", expenseforEducation);
        jsonFinance.addProperty("expenseForHealth", expenseforHealth);
        jsonFinance.addProperty("expenseForTravelling", expenseforTravelling);
        jsonFinance.addProperty("expenseForEntertainment", expenseforEntertainment);
        jsonFinance.addProperty("otherExpense", otherexpense);
        jsonFinance.addProperty("houseType", houseType);
        jsonFinance.addProperty("roofType", roofType);
        jsonFinance.addProperty("personalToilet", personalToilet);
        jsonFinance.addProperty("livingWSpouse", "Y");
        return jsonFinance;
    }

    private void showDialog(EditText editText) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_input, null);
        dialogBuilder.setView(dialogView);
        EditText editTextInput = dialogView.findViewById(R.id.editTextInput);
        Button buttonSubmit = dialogView.findViewById(R.id.buttonSubmit);
        ImageButton buttonClose = dialogView.findViewById(R.id.buttonClose);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        editText.setVisibility(View.GONE);
        // Button click listener to retrieve the entered text
        buttonSubmit.setOnClickListener(view -> {
            String userInput = editTextInput.getText().toString().trim();
            if (!userInput.isEmpty()) {
                if (userInput.trim().equals(editText.getText().toString().trim())){
                    Toast.makeText(getActivity(), "Account Number Matched!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    editText.setVisibility(View.VISIBLE);
                    accountVerification.setChecked(true);
                    accountVerification.setEnabled(false);
                    isAccountVerify="V";
                    UpdatefiVerificationDocName();
                }else{
                    Toast.makeText(getActivity(), "Account Number Did not Match. Please Enter Again!!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please Enter Account number", Toast.LENGTH_SHORT).show();
            }
        });
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                editText.setVisibility(View.VISIBLE);
                isAccountVerify="N";
                accountVerification.setChecked(false);

            }
        });




        // Close button click listener to dismiss the dialog

    }

}