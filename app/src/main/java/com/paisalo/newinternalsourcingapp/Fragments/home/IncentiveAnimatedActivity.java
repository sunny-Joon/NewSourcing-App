package com.paisalo.newinternalsourcingapp.Fragments.home;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.paisalo.newinternalsourcingapp.R;

public class IncentiveAnimatedActivity extends AppCompatActivity {

    int incentive,A = 0;
    TextView incentiveTv;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incentive_animated);

        getSupportActionBar().hide();

        incentiveTv = findViewById(R.id.incentivetrophy);
        incentiveTv.setVisibility(View.GONE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                incentiveTv.setVisibility(View.VISIBLE);
            }
        }, 3000);

        Intent intent = getIntent();
        if (intent != null) {
            String target = intent.getStringExtra("Target");
            Log.d("TAAAAAAG", "onCreate: "+ target);

            if (target != null) {
                String cleanedTarget = target.replaceAll("[^\\d]", "");

                A = Integer.parseInt(cleanedTarget);

                Log.d("TAAAAAAG", "onCreate: "+ A);
                incentive = A / 50000;
                Log.d("TAAAAAAG", "onCreate: "+ incentive);
                if (incentive < 50) {
                    incentiveTv.setText("0");
                } else if (incentive < 70 && incentive > 51) {
                    int B = incentive * 100;
                    incentiveTv.setText(Integer.toString(B));
                } else if (incentive < 90 && incentive > 71) {
                    int B = incentive * 150;
                    incentiveTv.setText(Integer.toString(B));
                } else if (incentive > 91) {
                    int B = incentive * 360;
                    incentiveTv.setText(Integer.toString(B));
                }
            }
        }
    }
}