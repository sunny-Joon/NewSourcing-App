package com.paisalo.newinternalsourcingapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ReferralCodeModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferAndEanActivity extends AppCompatActivity {

    static TextView referalcode_txt;
    String Username;
    ImageView howitswork, knowmore;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_and_ean);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button referButton;

        Username = GlobalClass.Id;
        referButton = findViewById(R.id.referbutton);
        referalcode_txt = findViewById(R.id.referalcode);

        howitswork = findViewById(R.id.howitswork);
        knowmore = findViewById(R.id.knowmore);
        GetCSOReferralCode(Username);

        howitswork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReferAndEanActivity.this, HowtoReferActivity.class);
                startActivity(intent);
            }
        });

        knowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReferAndEanActivity.this, GetRewardActivity.class);
                startActivity(intent);
            }
        });

        referButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareMessage = "Join Paisalo group with my referral code *" +referalcode_txt.getText().toString()  +"* get more benefits. Register on link https://www.paisalo.in/home/cso with my Referral code to become a CSO.";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }

    private void GetCSOReferralCode(String username) {
        ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Data Fetching Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ReferralCodeModel> call=apiInterface.getReferralCode(GlobalClass.Token,GlobalClass.dbname,username);

        call.enqueue(new Callback<ReferralCodeModel>() {
            @Override
            public void onResponse(Call<ReferralCodeModel> call, Response<ReferralCodeModel> response) {
                if (response.isSuccessful()) {
                    progressBar.dismiss();
                    ReferralCodeModel result = response.body();
                    String referalcode = result.getData();
                    referalcode_txt.setText(referalcode);
                    Log.d("TAG", "onResponse1: " + result.getData());
                    Log.d("TAG", "onResponse2: " + referalcode);
                } else {
                    Log.d("TAG", "onResponse3: " + response.code());
                    progressBar.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ReferralCodeModel> call, Throwable t) {
                progressBar.dismiss();

                Log.d("TAG", "onFailure3: " + t.getMessage());
            }
        });
    }
}
