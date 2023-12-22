package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.paisalo.newinternalsourcingapp.R;

public class KYCActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kycactivity2);

        Button savedata = findViewById(R.id.savedata);

        EditText monthlyincome = findViewById(R.id.monthlyincome);
        EditText expensemonthly = findViewById(R.id.expensemonthly);
        EditText futureincome = findViewById(R.id.futureincome);
        EditText agricultureincome = findViewById(R.id.agricultureincome);
        EditText pensionincome = findViewById(R.id.pensionincome);
        EditText interestincome = findViewById(R.id.interestincome);
        EditText otherincome = findViewById(R.id.otherincome);
        EditText earningmemberincome = findViewById(R.id.earningmemberincome);
        EditText loanincome = findViewById(R.id.loanincome);

        Spinner earningmembertype = findViewById(R.id.earningmembertype);
        Spinner businessdetail = findViewById(R.id.businessdetail);
        Spinner loanpurpose = findViewById(R.id.loanpurpose);
        Spinner occuption = findViewById(R.id.occuption);
        Spinner loanduration = findViewById(R.id.loanduration);
        Spinner selectbank = findViewById(R.id.selectbank);



    }
}
