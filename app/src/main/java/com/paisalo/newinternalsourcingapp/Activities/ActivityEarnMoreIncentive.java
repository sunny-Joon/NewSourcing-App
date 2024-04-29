package com.paisalo.newinternalsourcingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.paisalo.newinternalsourcingapp.R;

public class ActivityEarnMoreIncentive extends AppCompatActivity {

    TextView incentiveTv,remainingDisbursment,On_disbursing,more_Commission,achieved;
    int targetFiles, targetIncentive,A =0, earnedIncentive = 0,doneFiles;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_more_incentive);

        incentiveTv = findViewById(R.id.incentive);
        remainingDisbursment = findViewById(R.id.remainingDisbursment);
        more_Commission = findViewById(R.id.more_Commission);
        On_disbursing = findViewById(R.id.On_disbursing);
        achieved = findViewById(R.id.achieved);

        earnedIncentive = 5100;
        doneFiles =51;
        Intent intent = getIntent();
        if (intent != null) {
            String target = intent.getStringExtra("TvTarget_Screen");

            if (target != null) {
                String cleanedTarget = target.replaceAll("[^\\d]", "");

                A = Integer.parseInt(cleanedTarget);

                if(A <= earnedIncentive) {

                    achieved.setVisibility(View.VISIBLE);
                    On_disbursing.setVisibility(View.GONE);
                    more_Commission.setVisibility(View.GONE);
                    remainingDisbursment.setVisibility(View.GONE);
                    incentiveTv.setVisibility(View.GONE);

                }else{
                    targetFiles = A / 50000;

                    if (targetFiles < 50) {
                        targetIncentive = 0;
                    } else if (targetFiles < 70 && targetFiles > 51) {
                        targetIncentive = targetFiles * 100;
                    } else if (targetFiles < 90 && targetFiles > 71) {
                        targetIncentive = targetFiles * 150;

                    } else if (targetFiles > 91) {
                        targetIncentive = targetFiles * 360;
                    }
                }
            } else {
                Toast.makeText(this, "Set target", Toast.LENGTH_SHORT).show();
            }

            String C = Integer.toString(targetIncentive - earnedIncentive);
            incentiveTv.setText("Earn Rs " + C);

            if(targetIncentive==0){
                remainingDisbursment.setText( " 0 Cases");
            } else if (targetIncentive <=7000) {
                remainingDisbursment.setText( (targetIncentive/100) - doneFiles + " Cases");
            } else if (targetIncentive <=13500) {
                remainingDisbursment.setText( (targetIncentive/150) - doneFiles + " Cases");
            } else if (targetIncentive > 13500) {
                remainingDisbursment.setText( (targetIncentive/360) - doneFiles + " Cases");
            }


        }

    }
}