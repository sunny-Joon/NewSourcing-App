package com.paisalo.newinternalsourcingapp.Activities;

import static com.paisalo.newinternalsourcingapp.Utils.CustomProgress.customProgress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.artifex.mupdfdemo.MuPDFFragment;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.BorrowerListModels.BorrowerListDataModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.DownloadEsignXml;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels.PendingESignFI;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstEsignActivity extends AppCompatActivity {


    int whichButton;
    FragmentManager fm;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    DialogInterface dlg;
    private ParcelFileDescriptor parcelFileDescriptor;
    Button btnESignProcessing;
    String xmlString,responseUrl;
    AlertDialog alertDialog;

    private static final int APK_ESIGN_REQUEST_CODE = 404;

    TextView tvESignName,tvESignGuardian,tvESignMobile;

  //  WebView pdfview;
    private PendingESignFI borrower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_esign);


        tvESignName = findViewById(R.id.tvESignName);
        tvESignGuardian = findViewById(R.id.tvESignGuardian);
        tvESignMobile = findViewById(R.id.tvESignMobile);




        Intent intent = getIntent();
        if (intent != null) {
            borrower = (PendingESignFI) intent.getSerializableExtra(GlobalClass.ESIGN_BORROWER);
            if (borrower != null) {
                tvESignName.setText(borrower.getFname().toString());
                tvESignGuardian.setText(borrower.getfFname().toString());
                tvESignMobile.setText(borrower.getpPh3().toString());
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("DocName", "loan_application_sample");
                jsonObject.addProperty("FiCode", borrower.getCode());
                jsonObject.addProperty("FiCreator", borrower.getCreator());
                jsonObject.addProperty("UserID", GlobalClass.Id);
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder(

                );
                httpClient.connectTimeout(2, TimeUnit.MINUTES);
                httpClient.readTimeout(2,TimeUnit.MINUTES);
                httpClient.addInterceptor(logging);
               Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl("https://predeptest.paisalo.in:8084/PDL.ESign.API/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();

                ApiInterface apiInterface = retrofit2.create(ApiInterface.class);
                Call<ResponseBody> call = apiInterface.DownloadDocFirstEsign(GlobalClass.LiveToken,jsonObject,"gzip,deflate,compress","2234514145687247","SBIPDLCOL","868368051227918");

                Log.d("TAG", "onResponse0: " + GlobalClass.LiveToken+" "+ GlobalClass.dbname+" "+jsonObject.toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("TAG", "onResponse1: " + response.body()+response.code()+response.message());

                         if(response.isSuccessful()){
                             Log.d("TAG", "onResponse2: " + response.body().contentLength());
                             File written = writeResponseBodyToDisk(response.body());
                             if(written==null){
                                 Log.d("TAG", "onResponse2: " + "null");

                             }else{
                                 String path = written.getAbsolutePath();

                                 fm = getSupportFragmentManager();
                                 FragmentTransaction ft = fm.beginTransaction();
                                 //  for (String path:filePaths) {
                                 Fragment frag = MuPDFFragment.newInstance(path, false);
                                 ft.add(R.id.pdfview, frag);

                                 //}
                                 ft.commit();
                             }


                         }else{
                             Log.d("TAG", "onResponse3: " + "UnSuccessful");
                         }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("TAG", "onResponse4: " + t.getMessage());

                    }
                });

            } else {
                Log.e("FirstEsignActivity", "Borrower object is null");
            }
        }

        btnESignProcessing = findViewById(R.id.btnESignProcessing);

        btnESignProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openpopup();
            }
        });
    }

    public void openpopup() {

        Log.d("TAG", "openpopup: " + "Popup Open");

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.adhar_nsdl, null);

        Button can = popupView.findViewById(R.id.can);
        Button proceed = popupView.findViewById(R.id.proceed);
        TextView  adhaarId= popupView.findViewById(R.id.tiet_aadhar);
        CheckBox chkAadharConsent= popupView.findViewById(R.id.chkAadharConsent);

        adhaarId.setText(borrower.getAadharid());

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "openpopup: " + "Button Clicked");

                if(chkAadharConsent.isChecked()){
                JsonObject jsonObject= new JsonObject();
                jsonObject.addProperty("Creator", borrower.getCreator());
                jsonObject.addProperty("FICode", "250084");
                jsonObject.addProperty("UserID",GlobalClass.Id);
                jsonObject.addProperty("AuthMethod", "FP");
                jsonObject.addProperty("OTPBioMetricData", "NOT APPLICABLE WITH ESIGN");
                jsonObject.addProperty("CustName",borrower.getFname()+" "+borrower.getMname()+" "+borrower.getLname());
                jsonObject.addProperty("eKycID", borrower.getAadharid());
                jsonObject.addProperty("CustUUID","");
                jsonObject.addProperty("Reason", "Borrower");
                jsonObject.addProperty("Concent",getString(R.string.consent_1));

                ApiInterface apiInterface = ApiClient.getClient2().create(ApiInterface.class);
                Call<DownloadEsignXml> call = apiInterface.getXMLforESign(GlobalClass.LiveToken,"gzip,deflate,compress",GlobalClass.DevId,"SBIPDLCOL",
                        GlobalClass.Imei,"application/json","application/json;charset=utf-8","application/json",jsonObject);
                Log.d("TAG", "openpopup: " + jsonObject.toString());
                Log.d("TAG", "openpopup: " + GlobalClass.LiveToken+" "+GlobalClass.DevId+" "+GlobalClass.Imei);


                call.enqueue(new Callback<DownloadEsignXml>() {
                    @Override
                    public void onResponse(@NonNull Call<DownloadEsignXml> call, @NonNull Response<DownloadEsignXml> response) {
                        Log.d("TAG", "openpopup: " + "api run");
                        if (response.isSuccessful()) {
                            Log.d("TAG", "openpopup: " + "Api Successful");
                            Log.d("TAG", "openpopup: " + response.body());

                            assert response.body() != null;
                            xmlString = response.body().getESignXml().toString();
                            responseUrl = Utils.getESignXmlAttribute(xmlString, "responseUrl");

                            Intent appStartIntent = new Intent();
                            appStartIntent.setAction("com.nsdl.egov.esign.rdservice.fp.CAPTURE");
                            appStartIntent.putExtra("msg", xmlString); // msg contains esign request xml from ASP.
                            appStartIntent.putExtra("env", "PROD"); //Possible values PREPROD or PROD (case insensative).
                            appStartIntent.putExtra("returnUrl", responseUrl);
                            startActivityForResult(appStartIntent, APK_ESIGN_REQUEST_CODE);
                        }
                        else{
                            Log.d("TAG", "openpopup: " + "api Unsuccessful");
                            Log.d("TAG", "openpopup: " + response.code());
                            Log.d("TAG", "openpopup: " + response.message());

                        }
                    }

                    @Override
                    public void onFailure(Call<DownloadEsignXml> call, Throwable t) {
                        Log.d("TAG", "openpopup: " + t.getMessage());
                        Log.d("TAG", "openpopup: " + "failure");
                    }
                });

            }
        }
        });

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    private File writeResponseBodyToDisk(ResponseBody body) {
        Log.d("TAG", "displayPdf1: "+ "display Pdf" );

        try {
            // Define the path where the file will be saved
            File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "downloaded.pdf");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(pdfFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;

                    Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return pdfFile;
            } catch (IOException e) {

                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (currentPage != null) {
                currentPage.close();
            }
            if (pdfRenderer != null) {
                pdfRenderer.close();
            }
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: "+resultCode);
        Log.d("TAG", "onActivityResult: "+resultCode);
        Log.d("TAG", "onActivityResult:APK_ESIGN_REQUEST_CODE "+APK_ESIGN_REQUEST_CODE);


        if (requestCode == APK_ESIGN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                    String eSignResponse = data.getStringExtra("signedResponse");
                    Log.d("TAG", "onActivityResultESign: "+eSignResponse);
                    if (eSignResponse.trim().equals("Something went wrong during Esign Processing. Please contact administrator"))
                    {
                        Utils.alert(FirstEsignActivity.this,"Something went wrong during Esign Processing. Please contact administrator(NSDL)");
                    }else{
                        Log.d("TAG", "onActivityResultESign: "+"Send Esign Run");

                        sendESign(eSignResponse);
                    }

            } else {
                Toast.makeText(getBaseContext(),
                                "Nothing Selected", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void sendESign(String xml) {
        String UrlA = "";
        String UrlB = "";

        int lastSlashIndex = responseUrl.lastIndexOf('/');
        Log.d("TAG", "sendESign: "+  responseUrl);

        if (lastSlashIndex != -1) {
            UrlA = responseUrl.substring(0, lastSlashIndex + 1);
            Log.d("TAG", "sendESign: "+  UrlA);

            UrlB = responseUrl.substring(lastSlashIndex + 1);
            Log.d("TAG", "sendESign: "+  UrlB);

        }

        ApiInterface apiInterface = ApiClient.getClient3(UrlA).create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.postEntityEsign(xml, "999999999999",UrlB);
        Log.d("TAG", "sendESign: "+  "Run");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("TAG", "sendESign: "+  "Run");

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("TAG", "sendESign: "+  "successful");

                    try {
                        String responseString = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseString);
                        if (jsonObject.getBoolean("isSuccess")) {
                            showApprovalDialog(jsonObject);
                        } else {
                            Utils.alert(FirstEsignActivity.this, jsonObject.getString("ErrMsg"));
                        }
                    } catch (Exception e) {
                        Utils.alert(FirstEsignActivity.this, "Something went wrong in ESigning OR response is not correct");
                        e.printStackTrace();
                    }
                }else{
                    Log.d("TAG", "sendESign: "+  "Unsuccessful");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", "sendESign: "+  t.getMessage());

                try {
                    String errorBody = t.getMessage();
                    JSONObject jsonObject = new JSONObject(errorBody);
                    if (!jsonObject.getBoolean("isSuccess")) {
                        Utils.alert(FirstEsignActivity.this, jsonObject.getString("ErrMsg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Utils.alert(FirstEsignActivity.this, "Failed ESign Response");
                }
            }
        });
    }

    private void showApprovalDialog(final JSONObject jsonObject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("eSign Document");
        builder.setMessage("Are you sure you want to eSign the document?");

        DialogInterface.OnClickListener onDialogSubmitListner = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dlg = dialog;

                whichButton = which;
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    JSONObject jo = new JSONObject();
                    // Fill in your JSON object
                    sendESignSubmit(jo.toString());
                }
            }
        };

        builder.setCancelable(false);
        builder.setPositiveButton("Accept", onDialogSubmitListner);
        builder.setNeutralButton("Reject", onDialogSubmitListner);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void sendESignSubmit(String json) {
        ApiInterface apiInterface = ApiClient.getClient3(responseUrl).create(ApiInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Call<ResponseBody> call = apiInterface.postEntityESignSubmit(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseString = response.body().string();

                        if (response.code() == 200 && whichButton == DialogInterface.BUTTON_POSITIVE) {
                            borrower.seteSignSucceed("Y");
                            setResult(RESULT_OK);

                            Intent intent = new Intent(FirstEsignActivity.this, CrifScore.class);
                            intent.putExtra("FIcode", String.valueOf(borrower.getCode()));
                            intent.putExtra("creator", borrower.getCreator());
                            intent.putExtra("ESignerBorower", (CharSequence) borrower);
                            startActivity(intent);
                            dlg.dismiss();
                            finish();
                        }
                    } catch (Exception e) {
                        Utils.alert(FirstEsignActivity.this, "Something went wrong in ESigning OR response is not correct");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    String errorBody = t.getMessage();
                    JSONObject jsonObject = new JSONObject(errorBody);
                    if (!jsonObject.getBoolean("isSuccess")) {
                        Utils.alert(FirstEsignActivity.this, jsonObject.getString("ErrMsg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Utils.alert(FirstEsignActivity.this, "Failed ESign Response");
                }
            }
        });
    }
}

