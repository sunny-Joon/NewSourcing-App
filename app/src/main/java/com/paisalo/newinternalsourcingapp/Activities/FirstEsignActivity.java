package com.paisalo.newinternalsourcingapp.Activities;

import static com.paisalo.newinternalsourcingapp.Utils.CustomProgress.customProgress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstEsignActivity extends AppCompatActivity {

    Button btnESignProcessing;

    WebView pdfview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_esign);

        pdfview = findViewById(R.id.pdfview);
        btnESignProcessing = findViewById(R.id.btnESignProcessing);
        WebSettings webSettings = pdfview.getSettings();
        webSettings.setJavaScriptEnabled(true);


        String pdfUrl = "https://www.adobe.com/support/products/enterprise/knowledgecenter/media/c4611_sample_explain.pdf";
        pdfview.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdfUrl);

        pdfview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


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

                ApiInterface apiInterface = ApiClient.getClient1().create(ApiInterface.class);
                Call<ResponseBody> call = apiInterface.getXMLforESign();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("TAG", "openpopup: " + "api run");
                        if (response.isSuccessful()) {
                            Log.d("TAG", "openpopup: " + "Api Successful");
                            Log.d("TAG", "openpopup: " + response.body());

                            try {
                                String xmlString = response.body().string();

                                Intent appStartIntent = new Intent();
                                appStartIntent.setAction("com.nsdl.egov.esign.rdservice.fp.CAPTURE");
                                appStartIntent.putExtra("msg", xmlString); // msg contains esign request xml from ASP.
                                appStartIntent.putExtra("env", "PROD"); //Possible values PREPROD or PROD (case insensative).
                                appStartIntent.putExtra("returnUrl", "https://erpservice.paisalo.in:980/EsignTest/api/DocSignIn/XMLReaponse");
                                startActivityForResult(appStartIntent, 1111);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else{
                            Log.d("TAG", "openpopup: " + "api Unsuccessful");
                            Log.d("TAG", "openpopup: " + response.code());

                        }
                        //customProgress.hideProgress();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("TAG", "onFailure: " + t.getMessage());
                        Log.d("TAG", "openpopup: " + "failure");

                        customProgress.hideProgress();
                    }
                });

            }
        });

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }
    }

