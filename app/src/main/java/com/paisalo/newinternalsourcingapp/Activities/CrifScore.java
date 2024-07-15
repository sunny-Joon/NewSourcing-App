package com.paisalo.newinternalsourcingapp.Activities;


import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels.BorrowerListDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BreResponseModels.BREResponse;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.CheckCrifData;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.PendingESignFI;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DaoClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrifScore extends AppCompatActivity {

    ProgressBar progressBar,progressBarsmall;
    TextView textView7,textView8,textView13,textView6,text_srifScore,textView5,text_serverMessage,textView_valueEmi,text_wait;
    GifImageView gifImageView;
    String amount="0",emi="0",score,message;
    int scrifScore=0;
    DatabaseClass databaseClass;
    LinearLayout layout_design,layout_design_pending;
    Button btnTryAgain,btnSrifScore,btnSrifScoreSave;
    TextView textView_emi;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent i;
   // AdapterListRange rlaBankType;
    String ficode,creator;
    CheckCrifData checkCrifData=new CheckCrifData();
    PendingESignFI eSignerborower;
    String stateName;
    Spinner spinner;
    int attempts_left=4;
    TextView attempsTextView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crif_score);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Loan Eligibility");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

         databaseClass = DatabaseClass.getInstance(CrifScore.this);

        i=getIntent();
        Log.d("TAG", "onCreate: "+i.getStringExtra("ficode"));
        ficode=i.getStringExtra("FIcode");
        creator=i.getStringExtra("creator");
        eSignerborower = (PendingESignFI) i.getSerializableExtra("ESignerBorower");
        sharedPreferences = getSharedPreferences("KYCData",MODE_PRIVATE);
        editor = sharedPreferences.edit();
       // editor.putString("Bank",eSignerborower.BankName);
        editor.putString("Bank","SBI");
        editor.apply();

        progressBar=findViewById(R.id.circular_determinative_pb);
        attempsTextView=findViewById(R.id.attempsTextView);
        progressBarsmall=findViewById(R.id.progressBar);
        textView7=findViewById(R.id.textView7);
        textView_valueEmi=findViewById(R.id.textView_valueEmi);
        textView_emi=findViewById(R.id.textView_emi);
        textView8=findViewById(R.id.textView8);
        gifImageView=findViewById(R.id.gifImageView);
        textView6=findViewById(R.id.textView6);
        text_wait=findViewById(R.id.text_wait);
        textView13=findViewById(R.id.textView13);
        textView5=findViewById(R.id.textView5);
        text_serverMessage=findViewById(R.id.text_serverMessage);
        text_srifScore=findViewById(R.id.text_srifScore);
        layout_design=findViewById(R.id.layout_design);
        layout_design_pending=findViewById(R.id.layout_design_pending);
        btnTryAgain=findViewById(R.id.btnTryAgain);
        layout_design.setVisibility(View.GONE);
        btnTryAgain.setVisibility(View.GONE);
        layout_design_pending.setVisibility(View.VISIBLE);

       /* Log.e("LOG", eSignerborower.P_State);
        stateName= RangeCategory.getRangesByCatKeyName("state", eSignerborower.P_State, true);*/
        stateName= "Delhi";

        //  Toast.makeText(this,stateName, Toast.LENGTH_SHORT).show();

        btnSrifScoreSave=findViewById(R.id.btnSrifScoreSave);
        btnSrifScoreSave.setVisibility(View.GONE);
        btnSrifScoreSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBreEligibility();
            }
        });
        btnSrifScore=findViewById(R.id.btnSrifScore);
        attempsTextView.setText("Only "+attempts_left+" attempt to switch bank");
        attempsTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (attempts_left<1){
                    spinner.setEnabled(false);
                }else{
                    spinner.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSrifScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnSrifScore.getText().toString().equals("CLOSE")){
                    finish();
                }else{
                    text_wait.setVisibility(View.VISIBLE);
                    text_serverMessage.setText("");
                    layout_design.setVisibility(View.GONE);
                    btnTryAgain.setVisibility(View.GONE);
                    layout_design_pending.setVisibility(View.VISIBLE);
                    layout_design.setVisibility(View.GONE);

                    ProgressDialog progressDialog = new ProgressDialog(CrifScore.this);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setIndeterminate(false);
                    progressDialog.setTitle("Fetching Details");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    getDataFromBRE(progressDialog);

                }

               /* text_wait.setVisibility(View.VISIBLE);
                text_serverMessage.setText("");
                btnTryAgain.setVisibility(View.GONE);
                progressBarsmall.setVisibility(View.VISIBLE);
                checkCrifScore(borrowerdata);*/

            }
        });

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_wait.setVisibility(View.VISIBLE);
                text_serverMessage.setText("");
                btnTryAgain.setVisibility(View.GONE);
                progressBarsmall.setVisibility(View.VISIBLE);

                generateCrifScore(eSignerborower);

            }
        });

        generateCrifScore(eSignerborower);
        String[] arraySpinner = new String[] {
                "UCO", "BOB","PNB","SBI"
        };

        /*rlaBankType = new AdapterListRange(this,
                SQLite.select().from(RangeCategory.class).where(RangeCategory_Table.cat_key.eq("banks")).queryList(), false);
        spinner = (Spinner) findViewById(R.id.spinSelectBank);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(rlaBankType);
        spinner.setSelection(Utils.setSpinnerPosition(spinner,  AadharUtils.getBankCode(eSignerborower.BankName)));*/


       /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putString("Bank",((RangeCategory) spinner.getSelectedItem()).DescriptionEn);
                editor.apply();
                btnSrifScore.setText("TRY AGAIN");
                Log.d("TAG", "onItemSelected: "+sharedPreferences.getString("Bank",""));
                saveBREData(score);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


    }

    private JsonObject getJsonForCrif(String ficode, String creator, String amount, String emi,String bank) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("Ficode",ficode);
        jsonObject.addProperty("Creator",creator);
        jsonObject.addProperty("Loan_Amt",amount);
        jsonObject.addProperty("Emi",emi);
        jsonObject.addProperty("Bank",bank);
        Log.e("TAG",jsonObject.toString());
        return jsonObject;
    }

    private void AlertDialogBreEligibility(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CrifScore.this);
        builder.setMessage("Do you want to Proceed to Loan?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
            finish();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
            finish();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    private void restictBorrower() {

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.restrictBorrower(ficode,creator,"NO");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: "+response.body());
                if (response.body()!=null){
                    JsonObject jsonObject=response.body();
                    if (jsonObject.get("statusCode").getAsInt()==200){

                        AlertDialog.Builder alertD=new AlertDialog.Builder(CrifScore.this);
                        alertD.setCancelable(false);
                        alertD.setTitle("This case can not be further proceed due to our internal credit policy!!");
                        alertD.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finish();

                            }
                        });
                        alertD.show();


                    }else{
                        AlertDialog.Builder alertD=new AlertDialog.Builder(CrifScore.this);
                        alertD.setCancelable(false);
                        alertD.setTitle("Please Don't Process this case It will be failed in future!!");
                        alertD.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finish();

                            }
                        });
                        alertD.show();

                    }

                }else{
                    AlertDialog.Builder alertD=new AlertDialog.Builder(CrifScore.this);
                    alertD.setCancelable(false);
                    alertD.setTitle("Please Don't Process this case It will be failed in future!!");
                    alertD.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();

                        }
                    });
                    alertD.show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void updateSourcingStatus(){
        ApiInterface apiInterface= ApiClient.getClient4().create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.updateStatus(eSignerborower.getCode()+"",eSignerborower.getCreator());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: "+response.body());

            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());

            }
        });
    }

    private void saveBREData(String score) {
        String emiorScore = emi + "," + score;
        JsonObject jsonObject = getJsonForCrif(ficode, creator, amount, emiorScore, sharedPreferences.getString("Bank", ""));

        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        ApiInterface apiService = ApiClient.getClient4().create(ApiInterface.class);
        Call<Void> call = apiService.saveBreEligibility(requestBody);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CrifScore.this, "Data saved successfully", Toast.LENGTH_LONG).show();
                } else {
                    Log.e("TAG", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CrifScore.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void generateCrifScore(PendingESignFI eSignerborower) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Fetching Details");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        ApiInterface apiInterface=ApiClient.getClient4().create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.generateCrifForVehicle(getJsonForGenerateCrif(eSignerborower));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject=response.body();
                if (response.code()==200){
                    Log.d("TAG", "onResponse: "+jsonObject);
                    if (jsonObject.get("statusCode").getAsInt()==200 && jsonObject.get("data").getAsJsonArray().size()>=1){
                        JsonObject crifData=jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject();
                        if (response.code()==200){

                            if (response.body() != null) {
                                try {
                                    // Parse the JSON response
                                    JSONObject responseObject = new JSONObject(response.body().toString());

                                    // Get the data array
                                    JSONArray dataArray = responseObject.getJSONArray("data");

                                    // Check if the message "Crif Already Generated" is present in the data array
                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject dataObject = dataArray.getJSONObject(i);
                                        String message = dataObject.getString("Msg");
                                        if (message.contains("Crif Already Generated")) {
                                            progressDialog.hide();
                                            // Show toast message "done"
                                            Toast.makeText(CrifScore.this, "done", Toast.LENGTH_SHORT).show();
                                            break;
                                        }else{
                                            int crifscore=crifData.get("SCORE-VALUE").getAsString().length()>0?crifData.get("SCORE-VALUE").getAsInt():0;
                                            int ODAMT=crifData.get("OVERDUE-AMT").getAsString().length()>0?crifData.get("OVERDUE-AMT").getAsInt():0;
                                            int WOAMT=crifData.get("WRITE-OFF-AMT").getAsString().length()>0?crifData.get("WRITE-OFF-AMT").getAsInt():0;
                                            getDataFromBRE(progressDialog);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Utils.alert(CrifScore.this,"Sorry!! we didn't get your CRIF details \nPlease Try Again...");
            }
        });
    }
    private JsonObject getJsonForGenerateCrif(PendingESignFI eSignerborower) {
        JsonObject jsonObject=new JsonObject();
//        jsonObject.addProperty("full_name", "SHANTILAL D");
//        jsonObject.addProperty("dobs", "1992-04-08");
//        jsonObject.addProperty("emailid", "test@test.com");
//        jsonObject.addProperty("co", "MUTAN YADAV");
//        jsonObject.addProperty("address", "T 03 SANA APPARMENTS ALMAS COLONY KAUS A IN FRONT OF WAFA PARK ");
//        jsonObject.addProperty("city", "Thane");
//        jsonObject.addProperty("state", "Mumbai");
//        jsonObject.addProperty("pin", "400612");
//        jsonObject.addProperty("loan_amount", "50000");
//        jsonObject.addProperty("mobile", "6688493648");
//        jsonObject.addProperty("creator", "Thane");
//        jsonObject.addProperty("pancard", "ZDQPT2200V");
//        jsonObject.addProperty("voter_id", "83079747735704");
//        jsonObject.addProperty("AadharID", "");
//        jsonObject.addProperty("Gender", "F");

        jsonObject.addProperty("full_name", eSignerborower.getFname() + " "+eSignerborower.getMname()+" "+eSignerborower.getLname());

        String originalDateStr = eSignerborower.getDob().split("T")[0];
        String newDateStr="";
        try {
            newDateStr = new SimpleDateFormat("yyyy-MM-dd")
                    .format(new SimpleDateFormat("MMM dd yyyy hh:mma")
                            .parse(originalDateStr));
            System.out.println(newDateStr); // Output: 2024-04-30
        } catch (ParseException e) {
            e.printStackTrace();
        }
        jsonObject.addProperty(    "dobs",newDateStr);
     //   jsonObject.addProperty(    "dobs",eSignerborower.getDob().split("T")[0]);

        jsonObject.addProperty(    "emailid", "test@gamil.com");
        jsonObject.addProperty(    "co",eSignerborower.getfFname()+" "+eSignerborower.getfMname()+" "+eSignerborower.getfLname());
        jsonObject.addProperty(    "address", eSignerborower.getAddr());
        jsonObject.addProperty(    "city", eSignerborower.getpCity());
        jsonObject.addProperty(    "state", databaseClass.dao().getStateByCode("state",eSignerborower.getpState()).descriptionEn);
        jsonObject.addProperty(    "pin", String.valueOf(eSignerborower.getpPin()));
        jsonObject.addProperty(    "loan_amount", String.valueOf(eSignerborower.getLoanAmt()));
        jsonObject.addProperty(    "mobile", String.valueOf(eSignerborower.getpPh3()));
        jsonObject.addProperty(    "creator", creator);
        jsonObject.addProperty(    "FICode", String.valueOf(ficode));
        jsonObject.addProperty(    "pancard", eSignerborower.getPanNO());
        jsonObject.addProperty(    "voter_id",eSignerborower.getVoterID());
        jsonObject.addProperty(    "AadharID", eSignerborower.getAadharid());
        jsonObject.addProperty(    "Gender", eSignerborower.getGender());

        Log.d("TAG", "getJsonForGenerateCrif: "+ jsonObject);

       /* {"full_name":"RAGHVENDRA PRATAP SINGH","dobs":"2000-10-02","emailid":"test@gamil.com","co":"Jitendra Pratap Singh",
                "address":"HOUSE NO. 18Cholapur,HATHIYAR KALA,Pindra,Varanasi","city":"Varanasi","state":"Uttar Pradesh",
                "pin":"221101","loan_amount":"60000","mobile":"8757575456","creator":"HOAGRA","FICode":"250090",
                "pancard":"LAMPS2172L","voter_id":"ZXD3104692","AadharID":"541516386793","Gender":"M"}*/

        /*{"full_name":"Shivam null Savita","dobs":"Apr 30 2024 12:00AM","emailid":"test@gamil.com","co":"Akhilesh null Savita",
                "address":"124 B barasirohi kalyanpur L.LT ,Kanpur Nagar","city":"Kanpur Nagar","state":"Uttar Pradesh",
                "pin":"208016","loan_amount":"60000","mobile":"6585698454","creator":"HOAGRA","FICode":"250084",
                "pancard":"LHYPS1074C","voter_id":"ZXD3104692","AadharID":"608559509930","Gender":"M"}*/

        return  jsonObject;
    }

    public static String formatDate (String date, String initDateFormat, String endDateFormat) throws ParseException {

        Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
        String parsedDate = formatter.format(initDate);

        return parsedDate;
    }
    private void getDataFromBRE(ProgressDialog progressDialog) {

        ApiInterface apiInterface=ApiClient.getClient4().create(ApiInterface.class);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("creator",eSignerborower.getCreator());
        jsonObject.addProperty("ficode",String.valueOf(eSignerborower.getCode()));

        Log.d("TAG", "getDataFromBRE: "+jsonObject);
        Call<BREResponse> call=apiInterface.getBREStatus(eSignerborower.getCreator(),String.valueOf(eSignerborower.getCode()));
        call.enqueue(new Callback<BREResponse>() {
            @Override
            public void onResponse(Call<BREResponse> call, Response<BREResponse> response) {
                Log.d("TAG", "onResponse: "+response.body().getData());
                BREResponse breResponse=response.body();
                if(response.body() != null){
                    attempts_left--;
                    attempsTextView.setText("Only "+attempts_left+" attempt to switch bank");
                    BREResponse scrifData=response.body();

                    if(scrifData.getData() == null){
                        layout_design.setVisibility(View.GONE);
                        layout_design_pending.setVisibility(View.VISIBLE);
                        text_serverMessage.setText("Not Eligible. Please try Again!!");
                        btnTryAgain.setVisibility(View.VISIBLE);
                        text_wait.setVisibility(View.GONE);
                        progressBarsmall.setVisibility(View.GONE);
                    }else{
                        if (scrifData.getData().getData().equals("0") || scrifData.getData().getData().equals("0_0_")){
                           Toast.makeText(CrifScore.this, ""+scrifData.getMessage(), Toast.LENGTH_SHORT).show();
                            layout_design.setVisibility(View.GONE);
                            layout_design_pending.setVisibility(View.VISIBLE);
                            text_serverMessage.setText(scrifData.getMessage());
                            btnTryAgain.setVisibility(View.VISIBLE);
                            text_wait.setVisibility(View.GONE);
                            progressBarsmall.setVisibility(View.GONE);


                            message=scrifData.getData().getMessage();
                            gifImageView.setImageResource(R.drawable.crosssign);
                            textView8.setText("Sorry!!");
                            textView8.setTextColor(ContextCompat.getColor(CrifScore.this,R.color.red));
                            textView7.setText(message);
                            textView13.setVisibility(View.GONE);
                            textView6.setVisibility(View.GONE);
                            textView_valueEmi.setVisibility(View.GONE);
                            textView_emi.setVisibility(View.GONE);
                            layout_design.setVisibility(View.VISIBLE);
                            layout_design_pending.setVisibility(View.GONE);
                            text_srifScore.setText("0");
                            textView5.setText("0");
                            scrifScore=0;
                            btnSrifScoreSave.setVisibility(View.GONE);
                            btnSrifScore.setVisibility(View.VISIBLE);
                            btnSrifScore.setText("TRY AGAIN");

                        } else{
                            message=scrifData.getData().getMessage();
                            String[] dataSplitString=scrifData.getData().getData().split("_");
                            amount=dataSplitString[0];
                            if(amount.equals("")){
                                amount="0" ;
                            }
                            emi=dataSplitString[1];
                            score=dataSplitString[2];
                            if(score.equals("")){
                                score="0" ;
                            }
                            scrifScore=Integer.parseInt(score);
                            text_srifScore.setText(score);
                            textView5.setText(score);

                            if (Integer.parseInt(score)>650 || Integer.parseInt(score)<300){
                                if (Double.parseDouble(amount)>0 && response.body().getStatusCode()==200){
                                    gifImageView.setImageResource(R.drawable.checksign);
                                    textView8.setText("Congrats!!");
                                    textView8.setTextColor(ContextCompat.getColor(CrifScore.this,R.color.green));
                                    textView7.setText(message);
                                    textView13.setVisibility(View.VISIBLE);
                                    textView6.setVisibility(View.VISIBLE);
                                    textView_emi.setVisibility(View.VISIBLE);
                                    textView_valueEmi.setVisibility(View.VISIBLE);
                                    textView6.setText(amount+" ₹");
                                    textView_valueEmi.setText(emi+" ₹");
                                    btnSrifScoreSave.setVisibility(View.VISIBLE);
                                    btnSrifScore.setVisibility(View.GONE);
                                    saveBREData(score);
                                    updateSourcingStatus();
                                    //spinner.setEnabled(false);
                                }
                                else{
                                    gifImageView.setImageResource(R.drawable.crosssign);
                                    textView8.setText("Sorry!!");
                                    textView8.setTextColor(ContextCompat.getColor(CrifScore.this,R.color.red));
                                    textView7.setText(message);
                                    textView13.setVisibility(View.GONE);
                                    textView6.setVisibility(View.GONE);
                                    textView_valueEmi.setVisibility(View.GONE);
                                    textView_emi.setVisibility(View.GONE);
                                    btnSrifScoreSave.setVisibility(View.GONE);
                                    btnSrifScore.setVisibility(View.VISIBLE);
                                    btnSrifScore.setText("TRY AGAIN");

                                }
                            }else{

                                restictBorrower();
                                AlertDialog.Builder alertD=new AlertDialog.Builder(CrifScore.this);
                                alertD.setCancelable(false);
                                alertD.setTitle("This case can not be further proceed due to our internal credit policy!!");
                                alertD.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                });
                                alertD.show();
                            }
                            progressBar.setMax(1000);
                            progressBar.setProgress(0);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i=0;i<=scrifScore;i++){
                                        progressBar.setProgress(i);
                                        try {
                                            sleep(10);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }).start();
                            layout_design.setVisibility(View.VISIBLE);
                            layout_design_pending.setVisibility(View.GONE);                        }

                    }
                }else{
                    layout_design.setVisibility(View.GONE);
                    layout_design_pending.setVisibility(View.VISIBLE);
                    text_serverMessage.setText("Server Error!!");
                    btnTryAgain.setVisibility(View.VISIBLE);
                    text_wait.setVisibility(View.GONE);
                }


                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BREResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
                progressDialog.dismiss();
            }
        });
    }







    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("TAG",str);
        return str;
    }
    public static String getRandomSixNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public static String getRandomTwoNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(99);

        // this will convert any number sequence into 6 character.
        return String.format("%02d", number);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}