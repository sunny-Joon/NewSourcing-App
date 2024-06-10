package com.paisalo.newinternalsourcingapp.Activities;

import static com.paisalo.newinternalsourcingapp.Activities.ReferAndEanActivity.referalcode_txt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.paisalo.newinternalsourcingapp.R;

public class GetRewardActivity extends AppCompatActivity {

    Button refernowbutton;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_reward);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refernowbutton= findViewById(R.id.refernowbutton);
        refernowbutton.setOnClickListener(new View.OnClickListener() {
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
}